package messages;

import java.net.*;
import java.util.concurrent.ConcurrentHashMap;


import java.io.*;

public class Server extends Thread {

   private ServerSocket serverSocket;
   private ConcurrentHashMap<String, Identifier> activeClients;
   
   public Server(int port) throws IOException {
       activeClients = new ConcurrentHashMap<String,Identifier>();
      serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(60*60*000);
   }

   public void run() {
      while(true) {
         try {
            Socket server = serverSocket.accept();
            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            DataInputStream in = new DataInputStream(server.getInputStream());
            
            String messageGet = in.readUTF();
            System.out.println(messageGet);
            if(messageGet.charAt(0) == 'C'){
                String message = messageGet.substring(1);
                String[] data = message.split(";", 3);
                System.out.println("Received name: "+data[0]);
                activeClients.putIfAbsent(data[0],new Identifier(data[1],Integer.parseInt(data[2])));
                OutputStream outToServer = server.getOutputStream();
                DataOutputStream out = new DataOutputStream(outToServer);
                out.writeUTF("Accepted");
                server.close();
            }else if(messageGet.charAt(0) == 'D'){
                String message = messageGet.substring(1);
                System.out.println("Received message: "+message);
                for(String key : activeClients.keySet()){
                    System.out.println("Sending message to "+key);
                    Identifier i = activeClients.get(key);
                    System.out.println("Sending message to "+i.getAddress()+":"+i.getPort());

                    server.close();
                    try{
                        Socket sender = new Socket(i.getAddress(),i.getPort());
                        OutputStream outToServer = sender.getOutputStream();
                        DataOutputStream out = new DataOutputStream(outToServer);
                        out.writeUTF(message);
                        sender.close();
                    } catch (ConnectException ce){
                        activeClients.remove(key);
                        System.out.println("Due to error in connectin, removing "+key);
                    }

                }

            }

            server.close();
            
         } catch (SocketTimeoutException s) {
            System.out.println("Socket timed out!");
            break;
         } catch (IOException e) {
            e.printStackTrace();
            break;
         }
      }
   }
   
   public static void main(String [] args) {
      int port = Integer.parseInt(args[0]);
      try {
         Thread t = new Server(port);
         t.start();
         System.out.println("Server started at port "+port);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}