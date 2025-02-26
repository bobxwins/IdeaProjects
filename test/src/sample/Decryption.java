package sample;
import javafx.scene.paint.Color;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.spec.IvParameterSpec; // plus the usual imports
import javax.crypto.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.Signature;
import java.io.File;
import java.util.regex.Pattern;

public class Decryption {

    private static String dir = "/Users/Public";

    public void DecryptFile(File file) {
        try {
            Encrypt encrypt = new Encrypt();

            if (encrypt.verifyHash() == true) {

                Main.FileForEncryption = file.getPath();
                String inFile = Main.FileForEncryption;

                byte[] input = FileUtils.readAllBytes(inFile);

                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
                cipher.init(Cipher.DECRYPT_MODE, getSymmetricKey(), new IvParameterSpec(getIV()));
                byte[] output = cipher.doFinal(input);

                int FileFormat = file.getName().indexOf(".", file.getName().indexOf(".") + 1);
                //  overloaded version of indexOf(), which takes the starting index (fromIndex) as 2nd parameter:
                // returns the index number of the 2nd occurance of the period(.) character.

                String result = file.getName().substring(0, FileFormat);

                //  code above removes everything after the 2nd period.
                Main.text[0].setFill(Color.GREEN);
                String outFile = dir + "/" + "decrypted" + result;
                Main.text[0].setFill(Color.GREEN);
                Main.text[0].setText("\n"+"Stored and computed Hash values are equal!" +"\n"+
               "File succesfully decrypted into decrypted:" +"\n"+ result);

                FileUtils.write(outFile, output);
                File deletefile= new File(Main.FileForEncryption);
                deletefile.delete();

                // deletes whatever file existed after its decrypted
            }


            } catch(Exception e){
            Main.text[0].setFill(Color.RED);
            Main.text[0].setText("Decryption failed!"+"\n"+ e);
            }
        }

    public static void Signature() throws Exception {

        String input = Main.SignatureText;

        if (input.length() > 30 || ! Pattern.matches("[a-zA-Z.0-9_]*",input) ) {

            // helst ik for lang signatur og kun bogsstaver og tal

            Main.text[0].setFill(Color.RED);
            Main.text[1].setFill(Color.RED);
            Main.text[0].setText("Signature failed!" +  "\n");
            Main.text[1].setText("Invalid Message! Check length and characters used!");
            // 2 tekster der viser en fejl besked
            throw new Exception();
        }
        byte [] inputbytes = input.getBytes(StandardCharsets.UTF_8);

        String inFile = "C:\\Users\\Public\\Message.txt";
        String signatureFile = "C:\\Users\\Public\\Signature.txt";

        FileUtils.write(inFile, inputbytes);

          // inputtet skrives som plaintext i Message.TXT


        KeyPairs keys = new KeyPairs();

        PrivateKey privatekey = keys.getPrivateKey();

        Signature signer =
                Signature.getInstance("SHA256withRSA", "BC");




        signer.initSign(privatekey);
         // privatekey bruges sammen med SHA-256 algoritmen

        signer.update(inputbytes);

        //  beskeden gives

       byte [] signature = signer.sign();

       // her underskrives der


        FileUtils.write(signatureFile, signature);
        // gemmes som fil
        Main.text[1].setFill(Color.ORANGERED);


        Main.text[0].setFill(Color.GREEN);

        Main.text[0].setText(("Digital signature for given text: "+ Hex.toHexString(signature).substring(0,40)+"..."));
        Main.text[1].setText("Message sent :"+ input+ "\n");
    }

     public static void VerifySignature() {

         try {
             String inFile = "C:\\Users\\Public\\Message.txt";
             String signatureFile = "C:\\Users\\Public\\Signature.txt";
             byte[] input = FileUtils.readAllBytes(inFile);
             byte[] signature = FileUtils.readAllBytes(signatureFile);

             PublicKey publickey = KeyPairs.getPublicKey();

             Signature verifier =
                     Signature.getInstance("SHA256withRSA", "BC");

             verifier.initVerify(publickey);
             // publickey gives


             verifier.update(input);

             // besked gives

             boolean verify = verifier.verify(signature);

             // her verificeres der via en boolean

             Main.text[1].setFill(Color.ORANGERED);
             Main.text[1].setText("Message received :"+ new String(input)+ "\n");

             if (verify == true) {
                 Main.text[0].setFill(Color.GREEN);
                 Main.text[0].setText("           Verification of signature succeded!" +"\n" +
                         ""+"           Sender is authenticated! ");
             } else {

                 Main.text[0].setFill(Color.RED);

                 Main.text[0].setText("           Verification of signature failed!" +"\n"+
                         "           Message received from unauthenticated sender");

             }


         }
         catch (Exception e)
         {
             Main.text[0].setFill(Color.RED);
             Main.text[0].setText(""+ e);
         }
     }
    public static SecretKey getSymmetricKey()   {

        byte[] SymmKey = FileUtils.readAllBytes("C:\\Users\\Public\\SymmetricKey.txt");

        String StringKey = new String(SymmKey);
        // bytes konverteres til en string først og decodes til bytes når en key indlæses som fil
        // hvis ik dette gøres ville bytsne have Objektets ID i stedet for dets værdi.

        byte[] EncodedString = Base64.getDecoder().decode(StringKey);
        SecretKey DecodedKey = new SecretKeySpec(EncodedString, 0, EncodedString.length, "AES");

        byte[] EncodedKey = Base64.getEncoder().encode(DecodedKey.getEncoded());



        Main.text[0].setFill(Color.GREEN);
        Main.text[0].setText("The original key is: " + Hex.toHexString(EncodedKey));

        return DecodedKey;
    }

    public static byte[] getIV () {

        byte[] genIVByte = FileUtils.readAllBytes("C:\\Users\\Public\\GenIV.txt");

         String encodedIV = new String(genIVByte);

        byte[] decodedIV = Base64.getDecoder().decode(encodedIV);


        return decodedIV ;
    }

}
