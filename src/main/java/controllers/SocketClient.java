package controllers;

import IPC.StringToCall;

public class SocketClient {

    private final TicketShop ticketShop;
    private final StringToCall stringToCall;


    public SocketClient() {
        this.ticketShop = new TicketShop();
        this.stringToCall = new StringToCall(ticketShop);
    }
}
