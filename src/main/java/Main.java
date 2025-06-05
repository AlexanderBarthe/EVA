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

        System.exit(0);

    }
}
