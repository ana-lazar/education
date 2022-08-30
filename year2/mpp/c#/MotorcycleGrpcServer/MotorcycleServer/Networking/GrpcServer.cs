using System;
using grpc;
using Grpc.Core;

namespace MotorcycleServer.Networking
{
    public class GrpcServer
    {
        private int port;
        private string host;
        private Server server;
        private MotorcycleServices.MotorcycleServicesBase service;

        public GrpcServer(int port, string host, MotorcycleServices.MotorcycleServicesBase service)
        {
            this.port = port;
            this.host = host;
            this.service = service;
        }

        public void Start()
        {
            this.server = new Server
            {
                Services = {MotorcycleServices.BindService(service)},
                Ports = {new ServerPort(host, port, ServerCredentials.Insecure)}
            };
            server.Start();
            Console.WriteLine("server started ...");
            Console.WriteLine("press any key to stop the server");
            Console.ReadKey();
            server.ShutdownAsync().Wait();
        }
    }
}