package sample;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;

public class MyServer {

public static void main(String[] args){
MyServer server = new MyServer();
 server.Server();
}
public  void Server () {
    try {

        while (true) {
            ServerSocket ss = new ServerSocket(6666);
            Socket s = ss.accept();//establishes connection

            DataInputStream dis = new DataInputStream(s.getInputStream());
            String str = dis.readUTF();

           System.out.println("message= " + str);

            ss.close();
          //  byte[] input = str.getBytes(StandardCharsets.UTF_8);
        }
    }
    catch (Exception e) {
        System.out.println(e);

    }

}

}