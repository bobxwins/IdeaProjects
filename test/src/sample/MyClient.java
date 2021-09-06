package sample;
import org.bouncycastle.util.encoders.Hex;

import java.io.*;
import java.net.*;


public class MyClient {
    public static void main(String[] args) {

    }

    public  static void Client(String input) {
        try {

            // hensigten var at public Key skulle kunne sendes over outputstreamet. men det virkede ikke
            // i stedet for at sende publickey,
            // skulle jeg have sendt det som 2 filer, med en exponent og modulus
            // med andre ord, gjort det samme som ejg gjorde i Keypairs klassen


            Socket s = new Socket("localhost", 6666);
            // ny Socket objekt

            DataOutputStream dout = new DataOutputStream(s.getOutputStream());

            // sender data  til en output stream


            dout.writeUTF(input);

            dout.flush();
            // flush tvinger bufferet data til at skrives i streamen

            dout.close();
            s.close();
           System.out.println(input +"\n");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}