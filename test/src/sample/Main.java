package sample;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import javafx.scene.text.Text;


// This program is based on a program from http://tutorials.jenkov.com/javafx/filechooser.html
public class Main extends Application {


    static String FileForEncryption;
    public static String PassText;
    public static String SignatureText;
   static TextField SignatureField = new  TextField ();
    public File SelectedFile = null;
    // Den fil som kryptering eller dekrypterings funktionen vælger

    public static String Mode = "";
   // String som afgører om password skal kryptere eller dekryptere

     public static Text text []  = {new Text(),new Text()};
 // text er public og er et static array af tekst object,
    // således at 2 tekster med forskelige farver kan bruges samtidigt.
    // den er static således at den tidligere tekst slettes hver gang der kommer
    // en ny besked fra programmet som fx en fejl

    
    public void start(Stage primaryStage) throws Exception {
        Stage anotherStage = new Stage();
        primaryStage.setTitle("File Encrypter");
        FileChooser fileChooser = new FileChooser();
        String dir = "/Users/Public";

        fileChooser.setInitialDirectory(new File(dir));

        Button PasswordButton = new Button("Set Password");
        Button SignatureButton = new Button("Sign");
        Button VerifySignatureButton = new Button("Verify Signature");


        // setonAction er en event handler der håndtere buttons

        VerifySignatureButton.setOnAction(e -> {
  try{
             Decryption.VerifySignature();
  } catch (Exception E) {

  }
        });

        SignatureButton.setOnAction(e -> {
            try{

                SignatureText = String.valueOf(SignatureField.getText());
                SignatureField.setText("");
                System.out.println(SignatureText);
                Decryption.Signature();
            } catch (Exception E) {

            }
        });


        Button Wrapbutton = new Button("Wrap key");


        Wrapbutton.setOnAction(e -> {
            try {

                File keyToWrap = null;

                keyToWrap = fileChooser.showOpenDialog(anotherStage);
                // en filechoser object generes hver gang en fil skal vælges

                KeyWrap.Wrap(keyToWrap);

            }
 
            catch (Exception E) {

            }


        });

        Button UnWrapbutton = new Button("Unwrap Key");
        UnWrapbutton.setOnAction(e -> {

            try {
                File keyToUnWrap = null;
                keyToUnWrap = fileChooser.showOpenDialog(anotherStage);

                KeyWrap.UnWrap(keyToUnWrap);

            }

            catch (Exception E) {

            }

        });

        Button Encrypt = new Button("Encrypt file");
        Encrypt.setOnAction(e -> {


                  Mode = "Encryption";
                SelectedFile = fileChooser.showOpenDialog(anotherStage);
                text[1].setFill(Color.GREEN);
                text[0].setFill(Color.PALEVIOLETRED);
               text[1].setText("Selected file is: "+ SelectedFile);
               text[0].setText("Please enter password to complete encryption!");

        });

        Button Decrypt = new Button("Decrypt file");
        Decrypt.setOnAction(e -> {


                Mode = "Decryption";
                SelectedFile = fileChooser.showOpenDialog(anotherStage);
                text[1].setFill(Color.GREEN);
                text[0].setFill(Color.PALEVIOLETRED);
                text[1].setText("Selected file is: "+ SelectedFile);
                text[0].setText("Please enter password to complete decryption!");

            });


        Button KeyGenerator = new Button("Generate Keypairs");
        KeyGenerator.setOnAction(e -> {

            try {

              KeyPairs keypairs = new KeyPairs();
             keypairs.KeyPairGenerator();
              String PubkeyPath= "C:\\Users\\Public\\PublicKey.txt";
                String PrivkeyPath= "C:\\Users\\Public\\PrivateKey.txt";
                text[1].setFill(Color.GREEN);
                text[0].setFill(Color.ORANGERED);
                text[1].setText("Keys succesfully generated at: ");
                text[0].setText("\n"+PubkeyPath+"\n"+PrivkeyPath);


            } catch (Exception E) {


            }
        });


        PasswordField passwordfield = new  PasswordField ();

        PasswordButton.setOnAction(e -> {
            PassText = String.valueOf(passwordfield.getText());
            // Det der indtastest i password field er PassText's værdi, starter med at være tom

            passwordfield.setText("");


            // kryptering og dekryptering aktiveres først når password knappen klikkes på
            // så  dette sørger for det muligt at skifte mellem dem uden at genstarte programmet
            // med andre ord kalder password knappen både på krypterings funktionen og dekryptering
            // men aldrig begge på samme tid


            if (Mode == "Encryption")

            {
                Encrypt encrypt = new Encrypt();
            encrypt.SymmetricKeyGenerator(SelectedFile);
            // genere en key, IV,sletter den valgte fil efter kryptering
            }
            else   if (Mode == "Decryption")
                {
                Decryption decrypt = new Decryption();
                decrypt.DecryptFile(SelectedFile);
            }



        });

        //Setting font to the text
        text[0].setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 16));


        //Setting the text to be added.
        text[0].setText("");


        text[1].setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 16));


        text[1].setText("");

        Label Passwordlabel = new Label("Password:");
        Label SignatureLabel = new Label("Signature:");

        HBox hbox []  = {new HBox(),new HBox()};


        hbox [1] .getChildren().addAll(SignatureLabel,SignatureField,SignatureButton,VerifySignatureButton,Passwordlabel,passwordfield,PasswordButton);
// der laves 2 hbox, hvori at password og signatur elementer er øverst og resten er nederst
        hbox [1] .setSpacing(10);



        hbox [0].setSpacing(10);


        GridPane gridpane = new GridPane();

        hbox [0].getChildren().addAll( Wrapbutton,UnWrapbutton,Encrypt,Decrypt,KeyGenerator);

        // de resterende knapper nederst


        gridpane.setVgap(50);

        // den lodrette afstand mellem den øverste og nederste HBOX

        gridpane.add( hbox [0], 0,1 , 1, 1);
        gridpane.add(hbox [1] , 0, 0, 1, 1);
        gridpane.add( text[0], 0, 2, 1, 2);
        gridpane.add( text[1], 0, 2, 1, 1);

        ColumnConstraints col = new ColumnConstraints();
        col.setHalignment(HPos.CENTER);


        gridpane.getColumnConstraints().add(col);

        // sørger for al Tekst i Gridpanen  er centreret


        Scene scene = new Scene(gridpane, 1000, 500);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
