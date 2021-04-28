package sample;
import javax.crypto.spec.IvParameterSpec; // plus the usual imports

import java.security.MessageDigest;
import javax.crypto.*;

import javafx.scene.paint.Color;
import org.bouncycastle.util.encoders.Hex;

import java.security.SecureRandom;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKey;

import java.util.Base64;

import java.lang.*;

import java.io.File;
import java.nio.charset.StandardCharsets;

public class Encrypt {

   private static byte[]   generatedIV = new byte[16];
   private static byte[]   salt = new byte[32];
    private static PBEKeySpec KeySpecif (char[] password, byte [] generatedIV,byte [] salt) throws  Exception
    {
        PBEKeySpec keySpec = new PBEKeySpec(password,salt, 5000, 128);
        return keySpec;
    }


    public static void SymmetricKeyGenerator(File file)  {

        try {

            SecureRandom secureRandom = SecureRandom.getInstance("DEFAULT","BC");
            secureRandom.nextBytes(generatedIV);
            secureRandom.nextBytes(salt);
            char[] password = PasswordUtils.getUserPassword();
            Main.FileForEncryption = file.getPath();

            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance("PBKDF2WITHHMACSHA256", "BC");

           SecretKey key = factory.generateSecret(KeySpecif (password,generatedIV,salt));

            Main .text[0].setFill(Color.GREEN);
            Main.text[0].setText("Encrypting file..");

            String inFile = Main.FileForEncryption;
            byte[] input = FileUtils.readAllBytes(inFile);

          Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(generatedIV));
            byte[] output = cipher.doFinal(input);

            String outFile = inFile +"." +"Encrypted" + "." + "aes";
            FileUtils.write(outFile, output);

            byte[] EncodedKey = Base64.getEncoder().encode(key.getEncoded());

            byte[] EncodedIV = Base64.getEncoder().encode(generatedIV);

            FileUtils.write("C:\\Users\\bob-w\\Downloads\\encrypt\\SymmetricKey.txt",
                    EncodedKey);

            FileUtils.write("C:\\Users\\bob-w\\Downloads\\encrypt\\GenIV.txt",
                    EncodedIV);
            Main.text[0].setFill(Color.GREEN);
            Main.text[0].setText("Encryption succesful!");
            Passwordhash();

            File deletefile= new File(Main.FileForEncryption);
            deletefile.delete();
        } catch (Exception e) {
            Main.text[0].setFill(Color.RED);
            Main.text[0].setText("Encryption failed!" +  "\n"+   e);
        }
    }


   public static void Passwordhash() {
        try {


            var inFile = Main.PassText;
            byte [] inFileEncoded = inFile.getBytes(StandardCharsets.UTF_8);


            MessageDigest Digest =
                    MessageDigest.getInstance("SHA-256", "BC");
            Digest.update(inFileEncoded);
            byte[] hashValue = Digest.digest();

            // writing hash
            String outputFile = "C:\\Users\\bob-w\\Downloads\\encrypt\\PassHash.txt";

           String HexValue = Hex.toHexString(hashValue);

            byte [] hexBytes = HexValue.getBytes(StandardCharsets.UTF_8);
            FileUtils.write(outputFile, hexBytes);



        } catch (Exception e) {
            Main.text[0].setFill(Color.RED);
            Main.text[0].setText("Password hashing failed" + "\n" +e);


        }
    }


    public Boolean verifyHash() {
        try {

            var inFile = Main.PassText;
            byte [] inFileEncoded = inFile.getBytes(StandardCharsets.UTF_8);

            Main.text[0].setFill(Color.GREEN);
            Main.text[0].setText("Verifying hash of password..");

            MessageDigest Digest =
                    MessageDigest.getInstance("SHA-256", "BC");
            Digest.update(inFileEncoded);
            byte[] computedHashValue = Digest.digest();

            byte[] storedHashValue =
          FileUtils.readAllBytes("C:\\Users\\bob-w\\Downloads\\encrypt\\PassHash.txt");

            String StoredHexValue = new String(storedHashValue);

            String ComputedHexValue = Hex.toHexString(computedHashValue);
            Main.text[1].setFill(Color.DARKBLUE);
            Main.text[1].setText("Expected Hashvalue: " + StoredHexValue.substring(0,40)+"..." +  "\n"+
                    "Actual Computed HashValue: " + ComputedHexValue.substring(0,40)+ "..."+  "\n");

             if (ComputedHexValue.equals( StoredHexValue))
             {
                 // the succesful message is written in Decryption.DecryptFile(File)
                 return true;
             }
             else {

                 Main.text[0].setFill(Color.DARKRED);


                 Main.text[0].setText("Password HashValues are not equal"+ "\n"+
              "Password Verification failed!");
                 return false;
             }

        } catch (Exception e) {
            Main.text[0].setFill(Color.RED);
            Main.text[0].setText("verification failed " + "\n" +e);
        }


   return false; }
}
