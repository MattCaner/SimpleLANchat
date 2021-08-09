package messages;

import java.net.SocketAddress;

public class Identifier {
    private String address;
    private int port;
    public int getPort(){
        return port;
    }
    public String getAddress(){
        return address;
    }
    public Identifier(String socketAddress, int port){
        this.address = socketAddress;
        this.port = port;
    }
}
