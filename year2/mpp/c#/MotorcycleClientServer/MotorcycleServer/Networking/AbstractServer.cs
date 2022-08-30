using System;
using System.Net;
using System.Net.Sockets;
using System.Runtime.Remoting;

namespace MotorcycleServer.Networking
{
    public abstract class AbstractServer
    {
        // protected static readonly ILog Logger = LogManager.GetLogger("AbstractServer");
        
        private TcpListener server;
        private String host;
        private int port;

        public AbstractServer(String host, int port)
        {
            // Logger.InfoFormat("creating server with host {0} and port {1}...", host, port);
            this.host = host;
            this.port = port;
        }

        public void Start()
        {
            try
            {
                // Logger.Info("starting server...");
                IPAddress address = IPAddress.Parse(host);
                IPEndPoint endPoint = new IPEndPoint(address, port);
                server = new TcpListener(endPoint);
                server.Start();
                while (true)
                {
                    // Logger.Info("waiting for clients ...");
                    Console.WriteLine("waiting for clients ...");
                    TcpClient client = server.AcceptTcpClient();
                    // Logger.Info("client connected ...");
                    Console.WriteLine("client connected ...");
                    ProcessRequest(client);
                }
            }
            catch (Exception exception)
            {
                // Logger.Warn("starting server failed");
                throw new ServerException(exception.Message);
            }
            finally
            {
                Stop();
            }
        }

        public void Stop()
        {
            try
            {
                // Logger.Info("stopping server...");
                server.Stop();
            }
            catch (Exception exception)
            {
                // Logger.Warn("stopping server failed");
                throw new ServerException(exception.Message);
            }
        }
        
        public abstract void ProcessRequest(TcpClient client);
    }
}