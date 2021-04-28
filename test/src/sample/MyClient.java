package sample;
import org.bouncycastle.util.encoders.Hex;

import java.io.*;
import java.net.*;


public class MyClient {
    public static void main(String[] args) {

    }

    public  static void Client(String input) {
        try {

            Socket s = new Socket("localhost", 6666);

            DataOutputStream dout = new DataOutputStream(s.getOutputStream());


            dout.writeUTF(input);

            dout.flush();

            dout.close();
            s.close();
            System.out.println(input +"\n");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}