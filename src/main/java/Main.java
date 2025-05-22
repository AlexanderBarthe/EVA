import controllers.Client;

import java.io.IOException;

//Class definition
public class Main {

    //Main function, start of program
    public static void main(String[] args) {

        //Client client = new Client();
        //client.run();

        PerformanceClient performanceClient = new PerformanceClient();
        performanceClient.run();


        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
