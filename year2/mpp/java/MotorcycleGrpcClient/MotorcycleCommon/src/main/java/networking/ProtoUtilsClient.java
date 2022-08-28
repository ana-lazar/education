package networking;

import domain.User;
import grpc.Request;
import grpc.Response;

public class ProtoUtilsClient {
    public static Request createLoginRequest(String username, String password){
        grpc.UserDto userDto = grpc.UserDto.newBuilder().setUsername(username).setPassword(password).build();
        return Request.newBuilder().setUserDto(userDto).build();
    }

    public static User getUser(Response response) {
        grpc.User user = response.getUser();
        User u = new User(user.getName(), user.getUsername(), user.getPassword());
        u.setId(user.getId());
        return u;
    }
}
