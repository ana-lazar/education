// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: motorcycle.proto

package grpc;

public final class proto {
  private proto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_User_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_User_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_UserDto_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_UserDto_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_Race_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_Race_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_RaceDto_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_RaceDto_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_Participant_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_Participant_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_ParticipantDto_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_ParticipantDto_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_CredentialsDto_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_CredentialsDto_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_Request_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_Request_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_Response_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_Response_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\020motorcycle.proto\022\004grpc\"D\n\004User\022\n\n\002id\030\001" +
      " \001(\005\022\014\n\004name\030\002 \001(\t\022\020\n\010username\030\003 \001(\t\022\020\n\010" +
      "password\030\004 \001(\t\"-\n\007UserDto\022\020\n\010username\030\001 " +
      "\001(\t\022\020\n\010password\030\002 \001(\t\"$\n\004Race\022\n\n\002id\030\001 \001(" +
      "\005\022\020\n\010capacity\030\002 \001(\005\"2\n\007RaceDto\022\030\n\004race\030\001" +
      " \001(\0132\n.grpc.Race\022\r\n\005count\030\002 \001(\005\"G\n\013Parti" +
      "cipant\022\n\n\002id\030\001 \001(\005\022\016\n\006raceId\030\002 \001(\005\022\016\n\006te" +
      "amId\030\003 \001(\005\022\014\n\004name\030\004 \001(\t\"R\n\016ParticipantD" +
      "to\022\030\n\004race\030\001 \001(\0132\n.grpc.Race\022&\n\013particip" +
      "ant\030\002 \001(\0132\021.grpc.Participant\"B\n\016Credenti" +
      "alsDto\022\014\n\004name\030\001 \001(\t\022\020\n\010teamName\030\002 \001(\t\022\020" +
      "\n\010capacity\030\003 \001(\005\"\300\002\n\007Request\022\'\n\004type\030\001 \001" +
      "(\0162\031.grpc.Request.RequestType\022 \n\007userDto" +
      "\030\002 \001(\0132\r.grpc.UserDtoH\000\022\022\n\010capacity\030\003 \001(" +
      "\005H\000\022\022\n\010teamName\030\004 \001(\tH\000\022.\n\016credentialsDt" +
      "o\030\005 \001(\0132\024.grpc.CredentialsDtoH\000\022\r\n\005error" +
      "\030\006 \001(\t\"{\n\013RequestType\022\t\n\005LOGIN\020\000\022\r\n\tGET_" +
      "RACES\020\001\022\020\n\014SEARCH_RACES\020\002\022\020\n\014FILTER_RACE" +
      "S\020\003\022\024\n\020GET_PARTICIPANTS\020\004\022\014\n\010REGISTER\020\005\022" +
      "\n\n\006LOGOUT\020\006B\006\n\004data\"\245\002\n\010Response\022)\n\004type" +
      "\030\001 \001(\0162\033.grpc.Response.ResponseType\022\030\n\004u" +
      "ser\030\002 \001(\0132\n.grpc.User\022\031\n\005races\030\003 \003(\0132\n.g" +
      "rpc.Race\022\037\n\010raceDtos\030\004 \003(\0132\r.grpc.RaceDt" +
      "o\022\036\n\007raceDto\030\005 \001(\0132\r.grpc.RaceDto\022*\n\014par" +
      "ticipants\030\006 \003(\0132\024.grpc.ParticipantDto\022\r\n" +
      "\005error\030\007 \001(\t\"=\n\014ResponseType\022\006\n\002OK\020\000\022\t\n\005" +
      "ERROR\020\001\022\032\n\026REGISTERED_PARTICIPANT\020\0022\372\002\n\022" +
      "MotorcycleServices\022/\n\014Authenticate\022\r.grp" +
      "c.Request\032\016.grpc.Response\"\000\0224\n\021GetRaceBy" +
      "Capacity\022\r.grpc.Request\032\016.grpc.Response\"" +
      "\000\022/\n\014GetRaceInfos\022\r.grpc.Request\032\016.grpc." +
      "Response\"\000\022+\n\010GetRaces\022\r.grpc.Request\032\016." +
      "grpc.Response\"\000\022<\n\031GetRaceParticipantsBy" +
      "Team\022\r.grpc.Request\032\016.grpc.Response\"\000\0226\n" +
      "\023RegisterParticipant\022\r.grpc.Request\032\016.gr" +
      "pc.Response\"\000\022)\n\006LogOut\022\r.grpc.Request\032\016" +
      ".grpc.Response\"\000B\026\n\004grpcB\005protoP\001\252\002\004grpc" +
      "b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_grpc_User_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_grpc_User_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_User_descriptor,
        new java.lang.String[] { "Id", "Name", "Username", "Password", });
    internal_static_grpc_UserDto_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_grpc_UserDto_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_UserDto_descriptor,
        new java.lang.String[] { "Username", "Password", });
    internal_static_grpc_Race_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_grpc_Race_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_Race_descriptor,
        new java.lang.String[] { "Id", "Capacity", });
    internal_static_grpc_RaceDto_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_grpc_RaceDto_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_RaceDto_descriptor,
        new java.lang.String[] { "Race", "Count", });
    internal_static_grpc_Participant_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_grpc_Participant_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_Participant_descriptor,
        new java.lang.String[] { "Id", "RaceId", "TeamId", "Name", });
    internal_static_grpc_ParticipantDto_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_grpc_ParticipantDto_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_ParticipantDto_descriptor,
        new java.lang.String[] { "Race", "Participant", });
    internal_static_grpc_CredentialsDto_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_grpc_CredentialsDto_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_CredentialsDto_descriptor,
        new java.lang.String[] { "Name", "TeamName", "Capacity", });
    internal_static_grpc_Request_descriptor =
      getDescriptor().getMessageTypes().get(7);
    internal_static_grpc_Request_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_Request_descriptor,
        new java.lang.String[] { "Type", "UserDto", "Capacity", "TeamName", "CredentialsDto", "Error", "Data", });
    internal_static_grpc_Response_descriptor =
      getDescriptor().getMessageTypes().get(8);
    internal_static_grpc_Response_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_Response_descriptor,
        new java.lang.String[] { "Type", "User", "Races", "RaceDtos", "RaceDto", "Participants", "Error", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}