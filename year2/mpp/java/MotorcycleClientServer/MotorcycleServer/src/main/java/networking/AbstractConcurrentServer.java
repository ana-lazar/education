package networking;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.Socket;

public abstract class AbstractConcurrentServer extends AbstractServer {
    private static final Logger LOGGER = LogManager.getLogger(AbstractConcurrentServer.class.getName());

    public AbstractConcurrentServer(int port) {
        super(port);
    }

    public AbstractConcurrentServer() {
    }

    protected void processRequest(Socket client) {
        LOGGER.info("processing request");
        Thread workerThread = createWorker(client);
        LOGGER.info("starting processing request thread");
        workerThread.start();
    }

    protected abstract Thread createWorker(Socket client);
}
