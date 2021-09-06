package sample;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.math.BigInteger;

import javafx.scene.paint.Color;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.jce.provider.JCERSAPublicKey;
import org.bouncycastle.util.encoders.Hex;

import java.security.spec.*;

import java.security.KeyFactory;
import java.security.spec.RSAPrivateKeySpec;
import java.util.ArrayList;
import java.util.List;

public class KeyPairs {


    public static BigInteger GeneratedPrivExpo ;
    public static BigInteger GeneratedPubExpo ;
    public static BigInteger Generatedmodulus ;

    public void KeyPairGenerator() {

        try {
            KeyPairGenerator generator =
                    KeyPairGenerator.getInstance("RSA", "BC");
            generator.initialize(3072);



            KeyPair keyPair =
                    generator.generateKeyPair();

            //simple holder for en key pair

            PublicKey publicKey = keyPair.getPublic();

            // Key interfaces
            PrivateKey privateKey = keyPair.getPrivate();



            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
            //RSA privateKey interface

            RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
            //downcasting dvs  det bruger abstraktion, så når kvante computer opfindes
            //skal programmet ikke ændres fuldstændigt
            // RSA er en "detalje" som Keypair objektet ikke kender


            GeneratedPubExpo = rsaPublicKey.getPublicExponent();

            GeneratedPrivExpo  = rsaPrivateKey.getPrivateExponent();
            Generatedmodulus = rsaPublicKey.getModulus();


             getPrivateKey();

            getPublicKey();


        } catch (Exception e) {
            Main.text[0].setFill(Color.RED);
            Main.text[0].setText("Generating Keys failed");
        }
    }

    public static PublicKey getPublicKey() throws Exception {
//Generates the Private Key , stores it,  returns the private key when called

        KeyFactory fact = KeyFactory.getInstance("RSA");

        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(Generatedmodulus ,GeneratedPubExpo  );

        PublicKey publicKey = fact.generatePublic(keySpec);

        byte [] PublicKeyBytes = String.valueOf(publicKey).getBytes(StandardCharsets.UTF_8);

        sample.FileUtils.write("C:\\Users\\Public\\PublicKey.txt",
                PublicKeyBytes);

        // gemmes som en fil så den samme keys kan bruges efter programmet lukkes ned.

        return publicKey;
                }

    public static PrivateKey getPrivateKey() throws Exception {
//Generates the Private Key , stores it,  returns the private key when called

        KeyFactory fact = KeyFactory.getInstance("RSA");

        RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(Generatedmodulus ,GeneratedPrivExpo  );


        PrivateKey privateKey = fact.generatePrivate(keySpec);

        byte [] PrivateKeyBytes = String.valueOf(privateKey).getBytes(StandardCharsets.UTF_8);

        sample.FileUtils.write("C:\\Users\\Public\\PrivateKey.txt",
                PrivateKeyBytes);



        // gemmes som en fil så den samme keys kan bruges efter programmet lukkes ned.

        return privateKey;}


    public static void getpublicKeyFile () {


    }



}


