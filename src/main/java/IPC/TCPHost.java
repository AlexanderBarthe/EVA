package IPC;

import controllers.TicketShop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TCPHost {
    private final int port;
    private ServerSocket serverSocket;
    private final ExecutorService threadPool;

    private final StringToCall stringToCall;

    public TCPHost(int port, TicketShop ticketShop) {
        this.port = port;
        this.threadPool = Executors.newCachedThreadPool();
        this.stringToCall = new StringToCall(ticketShop);
    }

    public void start() {
        Thread serverThread = new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("Server started. Listening on port " + port);

                while (!serverSocket.isClosed()) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("New Client connected: " + clientSocket.getRemoteSocketAddress());
                    threadPool.submit(new ClientHandler(clientSocket));
                }
            } catch (IOException ignored) {
            } finally {
                shutdown();
            }
        });
        serverThread.start();
    }

    public void shutdown() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error while shutting server down: " + e.getMessage());
        }
        threadPool.shutdown();
        System.out.println("Server shut down.");
    }

    private class ClientHandler implements Runnable {
        private final Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
            ) {
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println("Recieved message by client " + socket.getRemoteSocketAddress() + ": " + line);

                    String answer;

                    try {
                        answer = stringToCall.callMethodRemotely(line);
                    } catch (Exception e) {
                        answer = e.getMessage();
                    }

                    out.println(answer);
                }
            } catch (IOException e) {
                System.err.println("Error in ClientHandler: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Fehler while closing ClientSocket: " + e.getMessage());
                }
                System.out.println("Client disconnected: " + socket.getRemoteSocketAddress());
            }
        }
    }
}