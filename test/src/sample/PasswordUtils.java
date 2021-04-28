package sample;

import java.util.regex.Pattern;

public class PasswordUtils {

  public static char[] getUserPassword() throws Exception {
    char[] password =  Main.PassText.toCharArray();
    // initiliases an array of character of unspecified length and value
    if (new String(password).length() < 10 || ! Pattern.matches("[a-zA-Z.0-9_]*",new String(password)) )
      throw new Exception("invalid Password");
      return password;
    }

  }




