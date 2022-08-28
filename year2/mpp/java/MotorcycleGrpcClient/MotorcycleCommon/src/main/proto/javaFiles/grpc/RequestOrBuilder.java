// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: motorcycle.proto

package grpc;

public interface RequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:grpc.Request)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.grpc.Request.RequestType type = 1;</code>
   * @return The enum numeric value on the wire for type.
   */
  int getTypeValue();
  /**
   * <code>.grpc.Request.RequestType type = 1;</code>
   * @return The type.
   */
  grpc.Request.RequestType getType();

  /**
   * <code>.grpc.UserDto userDto = 2;</code>
   * @return Whether the userDto field is set.
   */
  boolean hasUserDto();
  /**
   * <code>.grpc.UserDto userDto = 2;</code>
   * @return The userDto.
   */
  grpc.UserDto getUserDto();
  /**
   * <code>.grpc.UserDto userDto = 2;</code>
   */
  grpc.UserDtoOrBuilder getUserDtoOrBuilder();

  /**
   * <code>int32 capacity = 3;</code>
   * @return Whether the capacity field is set.
   */
  boolean hasCapacity();
  /**
   * <code>int32 capacity = 3;</code>
   * @return The capacity.
   */
  int getCapacity();

  /**
   * <code>string teamName = 4;</code>
   * @return Whether the teamName field is set.
   */
  boolean hasTeamName();
  /**
   * <code>string teamName = 4;</code>
   * @return The teamName.
   */
  java.lang.String getTeamName();
  /**
   * <code>string teamName = 4;</code>
   * @return The bytes for teamName.
   */
  com.google.protobuf.ByteString
      getTeamNameBytes();

  /**
   * <code>.grpc.CredentialsDto credentialsDto = 5;</code>
   * @return Whether the credentialsDto field is set.
   */
  boolean hasCredentialsDto();
  /**
   * <code>.grpc.CredentialsDto credentialsDto = 5;</code>
   * @return The credentialsDto.
   */
  grpc.CredentialsDto getCredentialsDto();
  /**
   * <code>.grpc.CredentialsDto credentialsDto = 5;</code>
   */
  grpc.CredentialsDtoOrBuilder getCredentialsDtoOrBuilder();

  /**
   * <code>string error = 6;</code>
   * @return The error.
   */
  java.lang.String getError();
  /**
   * <code>string error = 6;</code>
   * @return The bytes for error.
   */
  com.google.protobuf.ByteString
      getErrorBytes();

  public grpc.Request.DataCase getDataCase();
}