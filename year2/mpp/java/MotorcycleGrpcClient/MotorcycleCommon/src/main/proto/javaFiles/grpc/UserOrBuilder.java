// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: motorcycle.proto

package grpc;

public interface UserOrBuilder extends
    // @@protoc_insertion_point(interface_extends:grpc.User)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int32 id = 1;</code>
   * @return The id.
   */
  int getId();

  /**
   * <code>string name = 2;</code>
   * @return The name.
   */
  java.lang.String getName();
  /**
   * <code>string name = 2;</code>
   * @return The bytes for name.
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <code>string username = 3;</code>
   * @return The username.
   */
  java.lang.String getUsername();
  /**
   * <code>string username = 3;</code>
   * @return The bytes for username.
   */
  com.google.protobuf.ByteString
      getUsernameBytes();

  /**
   * <code>string password = 4;</code>
   * @return The password.
   */
  java.lang.String getPassword();
  /**
   * <code>string password = 4;</code>
   * @return The bytes for password.
   */
  com.google.protobuf.ByteString
      getPasswordBytes();
}