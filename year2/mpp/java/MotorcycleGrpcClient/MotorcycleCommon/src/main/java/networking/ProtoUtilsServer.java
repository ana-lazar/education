package networking;

import domain.User;
import dtos.UserDto;
import grpc.Request;
import grpc.Response;

public class ProtoUtilsServer {
    public static Response createLoginResponse(User user) {
        Response response = null;
        if (user == null) {
            response = Response.newBuilder().setType(Response.ResponseType.ERROR).setError("Invalid name or password").build();
        } else {
            grpc.User u = grpc.User.newBuilder().setId(user.getId()).setName(user.getName())
                    .setUsername(user.getUsername()).setPassword(user.getPassword())
                    .build();
            response = Response.newBuilder().setType(Response.ResponseType.OK).setUser(u).build();
        }
        return response;
    }

    public static UserDto geUserDto(Request request) {
        grpc.UserDto user = request.getUserDto();
        return new UserDto(user.getUsername(), user.getPassword());
    }

    public static Response createErrorResponse(String message) {
        Response response = Response.newBuilder().setType(Response.ResponseType.ERROR).setError(message).build();
        return response;
    }
}
