package sample;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class KeyFilesReader {


    public static PrivateKey getPrivateKeyFile()  throws Exception
    {

        KeyFactory fact = KeyFactory.getInstance("RSA");

        String modulusString = Files.readAllLines(Paths.get("C:\\Users\\Public\\PrivateKey.txt")).get(2);

        // only reads the 3rd line from the file privateKey.txt, which is the value of modulus

        String privExpoString= Files.readAllLines(Paths.get("C:\\Users\\Public\\PrivateKey.txt")).get(3);
        // only reads the 4th line from the file privateKey.txt, which is the value of the private exponent D

        String privExpoSubString=privExpoString.replaceAll("  private exponent: ","");
        // removes all occurances of the string "private exponent:" by repacing them with an empty string,
        // so only the number value of the privat exponent is returned from the funtion replaceAll



        String modulusSubString=modulusString.replaceAll("  modulus: ","");

        BigInteger modulus = new BigInteger(modulusSubString);

        // converts the substring to a BigInteger

        BigInteger PrivateExpo= new BigInteger(privExpoSubString);

        RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(modulus ,PrivateExpo );

        // specifications for a privatekey from Modulus and PrivteExpo Big Integers

        PrivateKey privateKey = fact.generatePrivate(keySpec);

        byte [] PrivateKeyBytes = String.valueOf(privateKey).getBytes(StandardCharsets.UTF_8);

        sample.FileUtils.write("C:\\Users\\Public\\PrivateKey.txt",
                PrivateKeyBytes);

        // gemmes som en fil så den samme keys kan bruges efter programmet lukkes ned.

        return privateKey;
    }

    public static PublicKey getPublicKeyFile()  throws Exception
    {

        KeyFactory fact = KeyFactory.getInstance("RSA");

        String modulusString = Files.readAllLines(Paths.get("C:\\Users\\Public\\PublicKey.txt")).get(2);
        String publicExpoString= Files.readAllLines(Paths.get("C:\\Users\\Public\\PublicKey.txt")).get(3);
        String publicExpoSubString=publicExpoString.replaceAll("  public exponent: ","");

        //replaces all occurrences of "private exponenet:"

        String modulusSubString=modulusString.replaceAll("  modulus: ","");

        BigInteger modulus = new BigInteger(modulusSubString);

        BigInteger PublicExpo= new BigInteger(publicExpoSubString);

        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus ,PublicExpo );

        PublicKey publicKey = fact.generatePublic(keySpec);

        byte [] PublicKeyBytes = String.valueOf(publicKey).getBytes(StandardCharsets.UTF_8);

        sample.FileUtils.write("C:\\Users\\Public\\PublicKey.txt",
                PublicKeyBytes);

        // gemmes som en fil så den samme keys kan bruges efter programmet lukkes ned.

        return publicKey;
    }
}
