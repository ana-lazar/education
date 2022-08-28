package networking;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.MotorcycleService;

import java.net.Socket;

public class RpcConcurrentServer extends AbstractConcurrentServer {
    private static final Logger LOGGER = LogManager.getLogger(RpcConcurrentServer.class.getName());
    private MotorcycleService motorcycleService;

    public RpcConcurrentServer(int port, MotorcycleService service) {
        super(port);
        LOGGER.info("creating rpc server");
        this.motorcycleService = service;
    }

    public RpcConcurrentServer() {
    }

    public void setMotorcycleService(MotorcycleService motorcycleService) {
        this.motorcycleService = motorcycleService;
    }

    @Override
    protected Thread createWorker(Socket client) {
        LOGGER.info("creating worker for client " + client);
        MotorcycleClientRpcWorker worker = new MotorcycleClientRpcWorker(motorcycleService, client);
        Thread workerThread = new Thread(worker);
        LOGGER.traceExit();
        return workerThread;
    }
}
