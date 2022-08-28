package networking;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import services.ServerException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class AbstractServer {
    private int port;
    private ServerSocket server = null;
    private static final Logger LOGGER = LogManager.getLogger(AbstractServer.class.getName());

    public AbstractServer(int port){
        LOGGER.traceEntry("setting server port " + port);
        this.port = port;
    }

    public AbstractServer() {
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setServer(ServerSocket server) {
        this.server = server;
    }

    public void start() throws ServerException {
        try {
            LOGGER.info("starting server");
            server = new ServerSocket(port);
            while (true) {
                LOGGER.info("waiting for clients");
                Socket client = server.accept();
                LOGGER.info("client connected");
                processRequest(client);
            }
        } catch (IOException e) {
            LOGGER.warn("starting server error {}", e.getMessage());
            throw new ServerException("starting server error " + e);
        } finally {
            stop();
        }
    }

    protected abstract void processRequest(Socket client);

    public void stop() throws ServerException {
        try {
            LOGGER.info("closing server");
            server.close();
        } catch (IOException e) {
            LOGGER.warn("closing server error");
            throw new ServerException("closing server error " + e);
        }
        LOGGER.traceExit();
    }
}
