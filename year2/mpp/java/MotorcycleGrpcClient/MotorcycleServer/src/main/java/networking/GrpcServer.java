package networking;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import grpc.MotorcycleServicesGrpc;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GrpcServer {
    private static final Logger LOGGER = LogManager.getLogger(GrpcServer.class.getName());

    private int port;
    private Server server;
    private MotorcycleServicesGrpc.MotorcycleServicesImplBase motorcycleService;

    public GrpcServer(int port, MotorcycleServicesGrpc.MotorcycleServicesImplBase service) {
        LOGGER.info("creating gRPC server");
        this.port = port;
        this.motorcycleService = service;
    }

    public GrpcServer() {
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setMotorcycleService(MotorcycleServicesGrpc.MotorcycleServicesImplBase motorcycleService) {
        this.motorcycleService = motorcycleService;
    }

    public void start() throws IOException {
        int port = this.port;
        this.server = ServerBuilder.forPort(port)
                .addService(motorcycleService)
                .build()
                .start();
        LOGGER.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                try {
                    GrpcServer.this.stop();
                } catch (InterruptedException e) {
                    e.printStackTrace(System.err);
                }
                System.err.println("*** server shut down");
            }
        });
    }

    public void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
}
