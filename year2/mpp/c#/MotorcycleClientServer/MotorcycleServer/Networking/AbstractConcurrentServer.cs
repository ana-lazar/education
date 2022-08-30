using System.Net.Sockets;
using System.Threading;

namespace MotorcycleServer.Networking
{
    public abstract class AbstractConcurrentServer : AbstractServer
    {
        public AbstractConcurrentServer(string host, int port) : base(host, port) { }

        public override void ProcessRequest(TcpClient client)
        {
            // Logger.Info("processing client request");
            Thread thread = CreateWorker(client);
            thread.Start();
        }

        protected abstract Thread CreateWorker(TcpClient client);
    }
}