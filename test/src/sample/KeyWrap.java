package sample;

import javafx.scene.paint.Color;
import org.bouncycastle.util.encoders.Hex;


import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.PrivateKey;

import java.io.File;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

import static sample.KeyPairs.GeneratedPubExpo;
import static sample.KeyPairs.Generatedmodulus;

public class KeyWrap {

    static String KeyPathToWrap ;



    public static byte[] Wrap(File file)  {

        try {


           KeyPathToWrap = file.getPath();

            PublicKey publicKey = KeyFilesReader.getPublicKeyFile();

            Decryption decryption = new Decryption();
            SecretKey OriginalKey = decryption.getSymmetricKey();

            byte[] SymmKey = FileUtils.readAllBytes(  KeyPathToWrap);

            Cipher cipher =
                    Cipher.getInstance("RSA/NONE/OAEPwithSHA256andMGF1Padding", "BC");


            // der bruges ikke noget blockmode.
            // returner et signatur objekt som implementer specificeret algoritmer


            cipher.init(Cipher.WRAP_MODE, publicKey);
            byte[] wrappedBytes = cipher.wrap(OriginalKey);
            SecretKey WrappedKey = new SecretKeySpec(wrappedBytes, 0, wrappedBytes.length, "AES");

            byte[] KeyEncoded = WrappedKey.getEncoded();
            byte[] WrapEncoded = Base64.getEncoder().encode(KeyEncoded);

            FileUtils.write(KeyPathToWrap, WrapEncoded);



            Main.text[1].setFill(Color.GREEN);

            Main.text[0].setFill(Color.ORANGERED);

            Main.text[1].setText("Key filepath " + KeyPathToWrap);
            Main.text[0].setText("Key before wrapping:" + Hex.toHexString(SymmKey)+ "\n"  + "Key after  wrapping: "
                    + Hex.toHexString(WrapEncoded).substring(0,49)+"...");

           MyClient.Client("Wrapped Key: " +new String(Hex.toHexString(WrapEncoded)));

           MyClient.Client(KeyFilesReader.getPublicKeyFile().toString());

            return WrapEncoded;

        } catch (Exception e) {

            Main.text[0].setFill(Color.RED);

            Main.text[0].setText("Key wrapping failed, make sure you're wrapping an appropiate key " +
                    "with an appropiate wrapper" + "\n"+e);


        }

        byte[] byteArrray = "Failed".getBytes();
        return byteArrray;
    }

    public static SecretKey UnWrap(File file) {

        try{

        KeyPathToWrap = file.getPath();
            PrivateKey privateKey =  KeyFilesReader.getPrivateKeyFile();
            byte[] SymmKey = FileUtils.readAllBytes(KeyPathToWrap);

            String encodedKey = new String(SymmKey);

            byte[] decodedKey = Base64.getDecoder().decode(encodedKey);

            Cipher cipher =
                    Cipher.getInstance("RSA/NONE/OAEPwithSHA256andMGF1Padding", "BC");
            cipher.init(Cipher.UNWRAP_MODE, privateKey);

            SecretKey secretKey =
                    (SecretKey) cipher.unwrap(decodedKey, "AES", Cipher.SECRET_KEY);

            byte[] UnWrapEncoded = Base64.getEncoder().encode(secretKey.getEncoded());

        FileUtils.write(KeyPathToWrap,
                UnWrapEncoded);
            Main.text[1].setFill(Color.ORANGERED);
            Main.text[0].setFill(Color.GREEN);
            Main.text[1].setText ("Key filepath: " +KeyPathToWrap);
            Main.text[0].setText ("Key before unwrapping:"+ Hex.toHexString(SymmKey).substring(0,47)+"..."+ "\n"+
                "Key after unwrapping:  "+ Hex.toHexString(UnWrapEncoded).substring(0,47)+"...");

            MyClient.Client("Unwrapped Key: "+ new String(Hex.toHexString(UnWrapEncoded)));
            return secretKey;
        }
catch (Exception e)
{
    Main.text[0].setFill(Color.RED);

    Main.text[0].setText("Key unwrapping failed, make sure you're unwrapping an appropiate key " +
            "with an appropiate unwrapper" + "\n"+e);
}
        SecretKey nullkey = null;
        return nullkey;
    }

    }



