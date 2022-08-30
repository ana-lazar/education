using System.Net.Sockets;
using System.Threading;
using MotorcycleCommon.Services;
using MotorcycleContest.Domain.Dtos;

namespace MotorcycleServer.Networking
{
    public class RpcConcurrentServer : AbstractConcurrentServer
    {
        private IMotorcycleService service;

        public RpcConcurrentServer(string host, int port, IMotorcycleService service) : base(host, port)
        {
            // Logger.Info("creating rpc concurrent server");
            this.service = service;
        }

        protected override Thread CreateWorker(TcpClient client)
        {
            // Logger.Info("creating worker for client " + client.ToString());
            MotorcycleClientRpcWorker worker = new MotorcycleClientRpcWorker(service, client);
            return new Thread(worker.Run);
        }
    }
}