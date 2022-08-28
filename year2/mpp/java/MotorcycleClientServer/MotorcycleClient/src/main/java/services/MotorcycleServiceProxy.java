package services;

import domain.Participant;
import domain.Race;
import domain.User;
import dtos.CredentialsDto;
import dtos.ParticipantDto;
import dtos.RaceDto;
import dtos.UserDto;
import networking.Request;
import networking.RequestType;
import networking.Response;
import networking.ResponseType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MotorcycleServiceProxy extends BaseServiceProxy implements MotorcycleService {
    private static final Logger LOGGER = LogManager.getLogger(BaseServiceProxy.class);
    private MotorcycleObserver observer;

    public MotorcycleServiceProxy(String host, int port) {
        super(host, port);
    }

    public MotorcycleServiceProxy() {
    }

    @Override
    public Optional<User> authenticate(String username, String password) throws Exception {
        LOGGER.traceEntry("authenticating account by name " + username + " and password " + password);
        this.getConnection();
        UserDto userDto = new UserDto(username, password);
        Request request = new Request.Builder().type(RequestType.LOGIN).data(userDto).build();
        LOGGER.info("sending login request {}", request);
        this.sendRequest(request);
        Response response = readResponse();
        LOGGER.info("got login response {}", response);
        if (response.type() == ResponseType.OK){
            User user = (User) response.data();
            if (user == null) {
                LOGGER.info("user not found");
                return Optional.empty();
            }
            LOGGER.info("found user {}", user);
            return Optional.of(user);
        }
        if (response.type() == ResponseType.ERROR){
            String exception = response.data().toString();
            closeConnection();
            this.observer = null;
            LOGGER.traceExit("exception getting races");
            throw new ServerException(exception);
        }
        return Optional.empty();
    }

    @Override
    public RaceDto getRaceByCapacity(Integer capacity) throws Exception {
        LOGGER.traceEntry("entering get race by capacity {}", capacity);
        this.getConnection();
        Request request = new Request.Builder().type(RequestType.FILTER_RACES).data(capacity).build();
        LOGGER.info("sending get race by capacity {} request", capacity);
        sendRequest(request);
        Response response = readResponse();
        LOGGER.info("get race by capacity response {}", response);
        if (response.type() == ResponseType.OK){
            RaceDto race = (RaceDto) response.data();
            LOGGER.info("get race by capacity found {}", race);
            return race;
        }
        if (response.type() == ResponseType.ERROR){
            String exception = response.data().toString();
            LOGGER.traceExit("exception getting race by capacity {}", capacity);
            throw new ServerException(exception);
        }
        return null;
    }

    @Override
    public List<RaceDto> getRaceInfos() throws Exception {
        LOGGER.traceEntry("entering get races information");
        this.getConnection();
        Request request = new Request.Builder().type(RequestType.SEARCH_RACES).build();
        LOGGER.info("sending get races information request {}", request);
        sendRequest(request);
        Response response = readResponse();
        LOGGER.info("get races information response {}", response);
        if (response.type() == ResponseType.OK){
            List<RaceDto> races = (ArrayList<RaceDto>) response.data();
            LOGGER.info("get races information found {}", races);
            return races;
        }
        if (response.type() == ResponseType.ERROR){
            String exception = response.data().toString();
            LOGGER.traceExit("exception getting races information");
            throw new ServerException(exception);
        }
        return null;
    }

    @Override
    public Iterable<Race> getRaces() throws Exception {
        LOGGER.traceEntry("entering get all races");
        this.getConnection();
        Request request = new Request.Builder().type(RequestType.GET_RACES).build();
        LOGGER.info("sending get races request {}", request);
        sendRequest(request);
        Response response = readResponse();
        LOGGER.info("get races response {}", response);
        if (response.type() == ResponseType.OK){
            List<Race> races = (ArrayList<Race>) response.data();
            LOGGER.info("get races found {}", races);
            return races;
        }
        if (response.type() == ResponseType.ERROR){
            String exception = response.data().toString();
            LOGGER.traceExit("exception finding user");
            throw new ServerException(exception);
        }
        return null;
    }

    @Override
    public List<ParticipantDto> getRaceParticipantsByTeam(String teamName) throws Exception {
        LOGGER.traceEntry("entering get participants by team {}", teamName);
        this.getConnection();
        Request request = new Request.Builder().type(RequestType.GET_PARTICIPANTS).data(teamName).build();
        LOGGER.info("sending get participants by team {} request", teamName);
        sendRequest(request);
        Response response = readResponse();
        LOGGER.info("get get participants by team response {}", response);
        if (response.type() == ResponseType.OK){
            List<ParticipantDto> participants = (List<ParticipantDto>) response.data();
            LOGGER.info("get get participants by team found {}", participants);
            return participants;
        }
        if (response.type() == ResponseType.ERROR){
            String exception = response.data().toString();
            LOGGER.traceExit("exception getting participants by team {}", teamName);
            throw new ServerException(exception);
        }
        return null;
    }

    @Override
    public void registerParticipant(String name, String teamName, Integer capacity) throws Exception {
        LOGGER.traceEntry("entering register participant {}, {}, {}", name, teamName, capacity);
        this.getConnection();
        Request request = new Request.Builder()
                .type(RequestType.REGISTER)
                .data(new CredentialsDto(name, teamName, capacity))
                .build();
        LOGGER.info("sending register participant request");
        sendRequest(request);
        Response response = readResponse();
        LOGGER.info("register participant response {}", response);
        if (response.type() == ResponseType.OK){
            LOGGER.info("register participant successful");
        }
        else if (response.type() == ResponseType.ERROR){
            String exception = response.data().toString();
            LOGGER.traceExit("exception during register participant");
            throw new ServerException(exception);
        }
    }

    @Override
    public void logOut(User user) {
        if (connection == null){
            LOGGER.info("connection already closed");
            return;
        }
        LOGGER.traceEntry("logging out account with name " + user.getName());
        UserDto userDto = new UserDto(user.getUsername(), user.getPassword());
        Request request = new Request.Builder().type(RequestType.LOGOUT).data(userDto).build();
        LOGGER.info("sending logout request {}", request);
        sendRequest(request);
        Response response = readResponse();
        closeConnection();
        this.observer = null;
        if (response.type() == ResponseType.ERROR){
            String exception = response.data().toString();
            closeConnection();
            LOGGER.traceExit("exception closing connection");
            throw new ServerException(exception);
        }
    }

    protected void handleRegisteredParticipant(Response response) {
        Participant participant = (Participant) response.data();
        observer.registeredParticipant(participant);
    }

    @Override
    public void addMotorcycleObserver(MotorcycleObserver observer) {
        this.observer = observer;
    }

    @Override
    public void removeMotorcycleObserver(MotorcycleObserver observer) {
        this.observer = null;
    }
}
