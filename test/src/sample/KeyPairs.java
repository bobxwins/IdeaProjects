package sample;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.math.BigInteger;

import javafx.scene.paint.Color;
import org.bouncycastle.util.encoders.Hex;

import java.security.spec.*;

import java.security.KeyFactory;
import java.security.spec.RSAPrivateKeySpec;

public class KeyPairs {



    public void KeyPairGenerator() {

        try {

            KeyPairGenerator generator =
                    KeyPairGenerator.getInstance("RSA", "BC");
            generator.initialize(3072);

            KeyPair keyPair =
                    generator.generateKeyPair();

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
            RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;


            BigInteger publicExponent = rsaPublicKey.getPublicExponent();
            BigInteger  privateExponent = rsaPrivateKey.getPrivateExponent();
            BigInteger  modulus = rsaPrivateKey.getModulus();

            byte [] ModulusBytes = String.valueOf(modulus).getBytes(StandardCharsets.UTF_8);
            byte [] PublicExponentBytes = String.valueOf(publicExponent).getBytes(StandardCharsets.UTF_8);
            byte [] PrivateExponentBytes = String.valueOf(privateExponent).getBytes(StandardCharsets.UTF_8);

            FileUtils.write("C:\\Users\\bob-w\\Downloads\\encrypt\\Modulus.txt",
                    ModulusBytes);



            FileUtils.write("C:\\Users\\bob-w\\Downloads\\encrypt\\PublicExponent.txt",
                    PublicExponentBytes);

            FileUtils.write("C:\\Users\\bob-w\\Downloads\\encrypt\\PrivateExponent.txt",
                    PrivateExponentBytes);

            // First the public and private key exponents are generated, along with modulus.
            // then the getPrivateKey + getPublicKey creates a keypair, using those exponents + modulus.
            getPrivateKey();
            getPublicKey();

        } catch (Exception e) {
            Main.text[0].setFill(Color.RED);
            Main.text[0].setText("Generating Keys failed");
        }
    }

    public  static PublicKey getPublicKey() throws Exception  {
//Generates the Public Key and stores it, and returns public key when called
      byte[] ModulusBytes = FileUtils.readAllBytes("C:\\Users\\bob-w\\Downloads\\encrypt\\Modulus.txt");
         String ModulusString = new String(ModulusBytes);
        BigInteger ModulusInt= new BigInteger (ModulusString);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(ModulusInt, BigInteger.valueOf(65537));
        PublicKey publicKey = fact.generatePublic(keySpec);

        byte [] PublicKeyBytes = String.valueOf(publicKey).getBytes(StandardCharsets.UTF_8);

        sample.FileUtils.write("C:\\Users\\bob-w\\Downloads\\encrypt\\PublicKey.txt",
                PublicKeyBytes);

        MyClient.Client(new String(PublicKeyBytes));
        return publicKey;
    }

    public static PrivateKey getPrivateKey() throws Exception  {
//Generates the Private Key , stores it,  returns the private key when called

        byte[] PKFileBytes = FileUtils.readAllBytes("C:\\Users\\bob-w\\Downloads\\encrypt\\PrivateExponent.txt");
        byte[] ModulusBytes = FileUtils.readAllBytes("C:\\Users\\bob-w\\Downloads\\encrypt\\Modulus.txt");

        String PrivateString = new String(PKFileBytes);
       String ModulusString = new String(ModulusBytes);

        BigInteger PrivateInt = new BigInteger (PrivateString);
        BigInteger ModulusInt = new BigInteger (ModulusString);

        KeyFactory fact = KeyFactory.getInstance("RSA");

        RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(ModulusInt,PrivateInt);

        PrivateKey privateKey = fact.generatePrivate(keySpec);

        byte [] PrivateKeyBytes = String.valueOf(privateKey).getBytes(StandardCharsets.UTF_8);

        sample.FileUtils.write("C:\\Users\\bob-w\\Downloads\\encrypt\\PrivateKey.txt",
                PrivateKeyBytes);

            return privateKey;}

}


