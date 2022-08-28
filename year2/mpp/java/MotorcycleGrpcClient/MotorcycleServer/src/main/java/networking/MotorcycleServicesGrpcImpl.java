package networking;

import domain.User;
import dtos.UserDto;
import grpc.MotorcycleServicesGrpc;
import grpc.Request;
import grpc.Response;
import io.grpc.stub.StreamObserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import services.MotorcycleService;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MotorcycleServicesGrpcImpl extends MotorcycleServicesGrpc.MotorcycleServicesImplBase {
    private static final Logger LOGGER = LogManager.getLogger(MotorcycleServicesGrpcImpl.class.getName());
    private MotorcycleService service;

    public MotorcycleServicesGrpcImpl(MotorcycleService service) {
        this.service = service;
    }

    public MotorcycleServicesGrpcImpl() {
    }

    public void setService(MotorcycleService service) {
        this.service = service;
    }

    private ConcurrentHashMap<Integer, StreamObserver<Response>> observers = new ConcurrentHashMap<>();

    public void authenticate(Request request, StreamObserver<Response> responseObserver) {
        UserDto userDto = ProtoUtilsServer.geUserDto(request);
        LOGGER.info("authenticating user by username {} and password {}", userDto.getUsername(), userDto.getPassword());
        Response response = null;
        try {
            Optional<User> user = service.authenticate(userDto.getUsername(), userDto.getPassword());
            if (user.isEmpty()) {
                response = ProtoUtilsServer.createErrorResponse("Invalid user or password");
            }
            else {
                response = ProtoUtilsServer.createLoginResponse(user.get());
            }
        } catch (Exception e) {
            response = ProtoUtilsServer.createErrorResponse(e.getMessage());
        }
        LOGGER.info("sending response {}", response);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
