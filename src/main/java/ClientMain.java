import IPC.TCPClient;

import java.io.IOException;

public class ClientMain {

    public static void main(String[] args) {

        TCPClient client = new TCPClient("172.24.166.211",12345);

        PerformanceClient performanceClient = new PerformanceClient(client);
        performanceClient.run();

        //client.startInteractive();

        client.close();





    }


}
