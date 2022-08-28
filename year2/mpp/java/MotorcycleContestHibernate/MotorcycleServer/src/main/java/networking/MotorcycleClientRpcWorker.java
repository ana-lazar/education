package networking;

import domain.Participant;
import domain.Race;
import domain.User;
import dtos.CredentialsDto;
import dtos.ParticipantDto;
import dtos.RaceDto;
import dtos.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.MotorcycleObserver;
import services.MotorcycleService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MotorcycleClientRpcWorker implements Runnable, MotorcycleObserver {
    private static final Logger LOGGER = LogManager.getLogger(MotorcycleClientRpcWorker.class.getName());
    private final MotorcycleService service;
    private final Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public MotorcycleClientRpcWorker(MotorcycleService service, Socket connection) {
        LOGGER.traceEntry("creating worker for server " + service + " and connection " + connection);
        this.service = service;
        this.connection = connection;
        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e) {
            LOGGER.warn("output stream failed");
        }
        LOGGER.traceExit("worker successfully created");
    }

    public void run() {
        while (connected) {
            try {
                Object request = input.readObject();
                LOGGER.info("received request " + request);
                Response response = handleRequest((Request) request);
                LOGGER.info("response for request " + response);
                if (response != null) {
                    LOGGER.info("sending response " + response);
                    sendResponse(response);
                }
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.warn("request failed " + e);
            }
        }
        try {
            LOGGER.info("closing connection");
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            LOGGER.warn("closing connection failed " + e);
        }
    }

    private static final Response okResponse = new Response.Builder().type(ResponseType.OK).build();

    private static final Response errorResponse = new Response.Builder().type(ResponseType.ERROR).build();

    private Response handleRequest(Request request){
        LOGGER.traceEntry("entering handleRequest with " + request);
        Response response = null;
        if (request.type() == RequestType.LOGIN){
            LOGGER.traceEntry("resolving login request ");
            UserDto userDto = (UserDto) request.data();
            try {
                LOGGER.info("finding user with username {} and password {}", userDto.getUsername(), userDto.getPassword());
                Optional<User> user = service.authenticate(userDto.getUsername(), userDto.getPassword());
                if (user.isEmpty()) {
                    LOGGER.traceExit("user not found");
                    return okResponse;
                }
                service.addMotorcycleObserver(this);
                LOGGER.traceExit("exiting with user " + user);
                return new Response.Builder()
                        .type(ResponseType.OK)
                        .data(user.get())
                        .build();
            } catch (Exception e) {
                LOGGER.warn("finding user failed");
                connected = false;
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        else if (request.type() == RequestType.GET_RACES){
            LOGGER.traceEntry("resolving get races request ");
            try {
                LOGGER.info("finding races");
                List<Race> races = StreamSupport
                        .stream(service.getRaces().spliterator(), false)
                        .collect(Collectors.toList());
                LOGGER.traceExit("exiting with races {}", races);
                return new Response.Builder()
                        .type(ResponseType.OK)
                        .data(races)
                        .build();
            } catch (Exception e) {
                LOGGER.warn("finding races failed");
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        else if (request.type() == RequestType.SEARCH_RACES){
            LOGGER.traceEntry("resolving get races information request ");
            try {
                LOGGER.info("finding races information");
                List<RaceDto> races = service.getRaceInfos();
                LOGGER.traceExit("exiting with races dtos {}", races);
                return new Response.Builder()
                        .type(ResponseType.OK)
                        .data(races)
                        .build();
            } catch (Exception e) {
                LOGGER.warn("finding races failed");
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        else if (request.type() == RequestType.FILTER_RACES){
            LOGGER.traceEntry("resolving get race by capacity request ");
            try {
                int capacity = (int) request.data();
                LOGGER.info("finding race by capacity {}", capacity);
                RaceDto race = service.getRaceByCapacity(capacity);
                LOGGER.traceExit("exiting with race dto {}", race);
                return new Response.Builder()
                        .type(ResponseType.OK)
                        .data(race)
                        .build();
            } catch (Exception e) {
                LOGGER.warn("finding race by capacity failed");
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        else if (request.type() == RequestType.GET_PARTICIPANTS){
            LOGGER.traceEntry("resolving get participants by team request ");
            try {
                String teamName = (String) request.data();
                LOGGER.info("finding participants by team {}", teamName);
                List<ParticipantDto> participants = service.getRaceParticipantsByTeam(teamName);
                LOGGER.traceExit("exiting with participant dtos {}", participants);
                return new Response.Builder()
                        .type(ResponseType.OK)
                        .data(participants)
                        .build();
            } catch (Exception e) {
                LOGGER.warn("finding participants by team failed");
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        else if (request.type() == RequestType.REGISTER){
            LOGGER.traceEntry("resolving register participant request ");
            try {
                CredentialsDto credentials = (CredentialsDto) request.data();
                service.registerParticipant(credentials.getName(), credentials.getTeamName(), credentials.getCapacity());
                LOGGER.traceExit("register participant successful");
                return okResponse;
            } catch (Exception e) {
                LOGGER.warn("finding participants by team failed");
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        else if (request.type() == RequestType.LOGOUT){
            UserDto userDto = (UserDto) request.data();
            try {
                connected = false;
                service.removeMotorcycleObserver(this);
                return okResponse;

            } catch (Exception e) {
                LOGGER.warn("logout failed");
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        return response;
    }

    private void sendResponse(Response response) throws IOException{
        LOGGER.traceEntry("sending response {} ", response);
        output.writeObject(response);
        output.flush();
        LOGGER.traceExit("sending response {} successful", response);
    }

    @Override
    public void registeredParticipant(Participant participant) {
        LOGGER.info("sending registered participant response");
        Response response = new Response.Builder().type(ResponseType.REGISTERED_PARTICIPANT).data(participant).build();
        try {
            sendResponse(response);
        } catch (IOException e) {
            LOGGER.info("sending registered participant failed");
            e.printStackTrace();
        }
    }
}
