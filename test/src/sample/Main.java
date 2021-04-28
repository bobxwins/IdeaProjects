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

    static String KeyPathToWrap ;
    static String FileForEncryption;
    public static String PassText;
    public static String SignatureText;
   static TextField SignatureField = new  TextField ();
    public File EncryptFile = null;
    public static String Mode = "";

     public static Text text []  = {new Text(),new Text()};

    public void start(Stage primaryStage) throws Exception {
        Stage anotherStage = new Stage();
        primaryStage.setTitle("File Encrypter");
        FileChooser fileChooser = new FileChooser();
        String dir = "/Users/bob-w/downloads/encrypt";
        fileChooser.setInitialDirectory(new File(dir));

        Button PasswordButton = new Button("Set Password");
        Button SignatureButton = new Button("Sign");
        Button VerifySignatureButton = new Button("Verify Signature");

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
                EncryptFile = fileChooser.showOpenDialog(anotherStage);
                text[1].setFill(Color.GREEN);
                text[0].setFill(Color.PALEVIOLETRED);
               text[1].setText("Selected file is: "+ EncryptFile);
               text[0].setText("Please enter password to complete encryption!");

        });

        Button Decrypt = new Button("Decrypt file");
        Decrypt.setOnAction(e -> {


                Mode = "Decryption";
                EncryptFile = fileChooser.showOpenDialog(anotherStage);
                text[1].setFill(Color.GREEN);
                text[0].setFill(Color.PALEVIOLETRED);
                text[1].setText("Selected file is: "+ EncryptFile);
                text[0].setText("Please enter password to complete decryption!");

            });


        Button KeyGenerator = new Button("Generate Keypairs");
        KeyGenerator.setOnAction(e -> {

            try {

              KeyPairs keypairs = new KeyPairs();
             keypairs.KeyPairGenerator();
              String PubkeyPath= "C:\\Users\\bob-w\\Downloads\\encrypt\\PublicKey.txt";
                String PrivkeyPath= "C:\\Users\\bob-w\\Downloads\\encrypt\\PrivateKey.txt";
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



            passwordfield.setText("");

            if (Mode == "Encryption")

            {
                Encrypt encrypt = new Encrypt();
            encrypt.SymmetricKeyGenerator(EncryptFile);
            }
            else   if (Mode == "Decryption")
                {
                Decryption decrypt = new Decryption();
                decrypt.DecryptFile(EncryptFile);
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

        hbox [1] .setSpacing(10);



        hbox [0].setSpacing(10);


        GridPane gridpane = new GridPane();

        hbox [0].getChildren().addAll( Wrapbutton,UnWrapbutton,Encrypt,Decrypt,KeyGenerator);

        gridpane.setVgap(50);
        gridpane.add( hbox [0], 0,1 , 1, 1);
        gridpane.add(hbox [1] , 0, 0, 1, 1);
        gridpane.add( text[0], 0, 2, 1, 2);
        gridpane.add( text[1], 0, 2, 1, 1);

        ColumnConstraints col = new ColumnConstraints();
        col.setHalignment(HPos.CENTER);
        gridpane.getColumnConstraints().add(col);

        Scene scene = new Scene(gridpane, 1000, 500);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
