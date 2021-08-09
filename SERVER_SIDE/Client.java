package messages;
import java.net.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.io.*;

public class Client extends Thread {


    private String serverName;
    private int port;
    private String myIp = "localhost";
    private int serverPort = 8085;
    private ServerSocket listener;

    public Client(String serverName, int serverPort, String selfName, int port){
        this.serverName = serverName;
        this.port = port;
    }

    public static String getLocalAddress() throws UnknownHostException{
        return InetAddress.getLocalHost().getHostAddress().trim();
    }


   public static void main(String [] args) {
    try{
        System.out.println("Address: " + getLocalAddress());
        Client client = new Client("localhost",8085,getLocalAddress(),Integer.parseInt(args[1]));
        client.establishConnection(args[0]);
        client.start();
        System.out.println("Client started");
        sleep(10*1000);
        System.out.println("Testing sending");
        client.send(args[0]+";Hello there!",true);
    }catch (Exception e) {
        e.printStackTrace();
    }
   }


   public void run(){

    try{
        while(true){
            listen();
        }
    }catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

   public void establishConnection(String name){
       try{
            System.out.println("Connecting to " + serverName + " on port " + port);
            Socket clientSocket = new Socket(serverName, serverPort);
            OutputStream outToServer = clientSocket.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF("C"+name+";"+myIp+";"+port);
            InputStream inFromServer = clientSocket.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            
            System.out.println("Server says " + in.readUTF());
            clientSocket.close();
        }catch (IOException e) {
            e.printStackTrace();
     }
   }

   public void listen() throws IOException{
        listener = new ServerSocket(port);
        Socket socket = listener.accept();
        DataInputStream in = new DataInputStream(socket.getInputStream());
        System.out.println("Received: " + in.readUTF());
        listener.close();
   }

    public void send(String message, boolean relisten){
        try{
            Socket clientSocket = new Socket(serverName, serverPort);
            OutputStream outToServer = clientSocket.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF("D"+message);
            clientSocket.close();
        //    if(relisten) listen();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
