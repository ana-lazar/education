package services;

import com.google.protobuf.ServiceException;
import domain.Race;
import domain.User;
import dtos.ParticipantDto;
import dtos.RaceDto;

import grpc.MotorcycleServicesGrpc;
import grpc.Request;
import grpc.Response;
import io.grpc.Channel;
import networking.ProtoUtilsClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import validators.ValidationException;

import java.util.List;
import java.util.Optional;

public class MotorcycleServiceGrpcProxy implements MotorcycleService {
    private static final Logger LOGGER = LogManager.getLogger(MotorcycleServiceGrpcProxy.class);

    private MotorcycleObserver observer;
    private MotorcycleServicesGrpc.MotorcycleServicesBlockingStub blockingStub;
    private MotorcycleServicesGrpc.MotorcycleServicesStub asyncStub;

    public MotorcycleServiceGrpcProxy(Channel channel) {
        blockingStub = MotorcycleServicesGrpc.newBlockingStub(channel);
        asyncStub = MotorcycleServicesGrpc.newStub(channel);
    }

    public void setBlockingStub(MotorcycleServicesGrpc.MotorcycleServicesBlockingStub blockingStub) {
        this.blockingStub = blockingStub;
    }

    public void setAsyncStub(MotorcycleServicesGrpc.MotorcycleServicesStub asyncStub) {
        this.asyncStub = asyncStub;
    }

    public Optional<User> authenticate(String username, String password) throws Exception {
        LOGGER.traceEntry("authenticating account by name " + username + " and password " + password);
        Request request = ProtoUtilsClient.createLoginRequest(username, password);
        Response response;
        try {
            response = blockingStub.authenticate(request);
        } catch (Exception exception) {
            LOGGER.warn("GRPC failed: {0}", exception);
            throw new Exception(exception);
        }
        if (response.getType() == Response.ResponseType.ERROR) {
            LOGGER.warn("getting account failed: {}", response.getError());
            if (response.getError().equals("Invalid username or password")) {
                throw new ValidationException(response.getError());
            }
            throw new ServiceException(response.getError());
        }
        User user = ProtoUtilsClient.getUser(response);
        LOGGER.info("found user {}", user);
        return Optional.of(user);
    }

    public RaceDto getRaceByCapacity(Integer capacity) throws Exception {
        LOGGER.traceEntry("entering get race by capacity {}", capacity);
//        this.getConnection();
//        Request request = new Request.Builder().type(RequestType.FILTER_RACES).data(capacity).build();
//        LOGGER.info("sending get race by capacity {} request", capacity);
//        sendRequest(request);
//        Response response = readResponse();
//        LOGGER.info("get race by capacity response {}", response);
//        if (response.type() == ResponseType.OK){
//            RaceDto race = (RaceDto) response.data();
//            LOGGER.info("get race by capacity found {}", race);
//            return race;
//        }
//        if (response.type() == ResponseType.ERROR){
//            String exception = response.data().toString();
//            LOGGER.traceExit("exception getting race by capacity {}", capacity);
//            throw new ServerException(exception);
//        }
        return null;
    }

    public List<RaceDto> getRaceInfos() throws Exception {
        LOGGER.traceEntry("entering get races information");
//        this.getConnection();
//        Request request = new Request.Builder().type(RequestType.SEARCH_RACES).build();
//        LOGGER.info("sending get races information request {}", request);
//        sendRequest(request);
//        Response response = readResponse();
//        LOGGER.info("get races information response {}", response);
//        if (response.type() == ResponseType.OK){
//            List<RaceDto> races = (ArrayList<RaceDto>) response.data();
//            LOGGER.info("get races information found {}", races);
//            return races;
//        }
//        if (response.type() == ResponseType.ERROR){
//            String exception = response.data().toString();
//            LOGGER.traceExit("exception getting races information");
//            throw new ServerException(exception);
//        }
        return null;
    }

    public Iterable<Race> getRaces() throws Exception {
        LOGGER.traceEntry("entering get all races");
//        this.getConnection();
//        Request request = new Request.Builder().type(RequestType.GET_RACES).build();
//        LOGGER.info("sending get races request {}", request);
//        sendRequest(request);
//        Response response = readResponse();
//        LOGGER.info("get races response {}", response);
//        if (response.type() == ResponseType.OK){
//            List<Race> races = (ArrayList<Race>) response.data();
//            LOGGER.info("get races found {}", races);
//            return races;
//        }
//        if (response.type() == ResponseType.ERROR){
//            String exception = response.data().toString();
//            LOGGER.traceExit("exception finding user");
//            throw new ServerException(exception);
//        }
        return null;
    }

    public List<ParticipantDto> getRaceParticipantsByTeam(String teamName) throws Exception {
        LOGGER.traceEntry("entering get participants by team {}", teamName);
//        this.getConnection();
//        Request request = new Request.Builder().type(RequestType.GET_PARTICIPANTS).data(teamName).build();
//        LOGGER.info("sending get participants by team {} request", teamName);
//        sendRequest(request);
//        Response response = readResponse();
//        LOGGER.info("get get participants by team response {}", response);
//        if (response.type() == ResponseType.OK){
//            List<ParticipantDto> participants = (List<ParticipantDto>) response.data();
//            LOGGER.info("get get participants by team found {}", participants);
//            return participants;
//        }
//        if (response.type() == ResponseType.ERROR){
//            String exception = response.data().toString();
//            LOGGER.traceExit("exception getting participants by team {}", teamName);
//            throw new ServerException(exception);
//        }
        return null;
    }

    public void registerParticipant(String name, String teamName, Integer capacity) throws Exception {
        LOGGER.traceEntry("entering register participant {}, {}, {}", name, teamName, capacity);
//        this.getConnection();
//        Request request = new Request.Builder()
//                .type(RequestType.REGISTER)
//                .data(new CredentialsDto(name, teamName, capacity))
//                .build();
//        LOGGER.info("sending register participant request");
//        sendRequest(request);
//        Response response = readResponse();
//        LOGGER.info("register participant response {}", response);
//        if (response.type() == ResponseType.OK){
//            LOGGER.info("register participant successful");
//        }
//        else if (response.type() == ResponseType.ERROR){
//            String exception = response.data().toString();
//            LOGGER.traceExit("exception during register participant");
//            throw new ServerException(exception);
//        }
    }

    public void logOut(User user) {
//        if (connection == null){
//            LOGGER.info("connection already closed");
//            return;
//        }
//        LOGGER.traceEntry("logging out account with name " + user.getName());
//        UserDto userDto = new UserDto(user.getUsername(), user.getPassword());
//        Request request = new Request.Builder().type(RequestType.LOGOUT).data(userDto).build();
//        LOGGER.info("sending logout request {}", request);
//        sendRequest(request);
//        Response response = readResponse();
//        closeConnection();
//        this.observer = null;
//        if (response.type() == ResponseType.ERROR){
//            String exception = response.data().toString();
//            closeConnection();
//            LOGGER.traceExit("exception closing connection");
//            throw new ServerException(exception);
//        }
    }

    protected void handleRegisteredParticipant(Response response) {
//        Participant participant = (Participant) response.data();
//        observer.registeredParticipant(participant);
    }

    public void addMotorcycleObserver(MotorcycleObserver observer) {
        this.observer = observer;
    }

    public void removeMotorcycleObserver(MotorcycleObserver observer) {
        this.observer = null;
    }
}
