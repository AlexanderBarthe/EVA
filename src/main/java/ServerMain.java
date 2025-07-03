import IPC.TCPHost;
import controllers.Client;
import com.tickets.TicketShop;
import org.springframework.boot.SpringApplication;

public class ServerMain {

    public static void main(String[] args){

        TicketShop ticketShop = new TicketShop();

        SpringApplication.run(ServerMain.class, args);

        TCPHost server = new TCPHost(12345, ticketShop);
        server.start();

        Client consoleClient = new Client(ticketShop);
        consoleClient.run();
    }
}
