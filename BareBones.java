import javax.swing.*;
import java.io.*;
import java.nio.file.Path;
import java.util.*;

public class BareBones {

  public static void main(String[] args) {
    String[] reader = parseText(fileSelect());
    Interpreter calculator = new Interpreter(reader);

    calculator.calculate();
    HashMap<String, Integer> varList = calculator.getVars();
    String[] keyList = new ArrayList<>(varList.keySet()).toArray(new String[0]);

    for(int i = 0; i < keyList.length; i++ ) {
      System.out.println(keyList[i] + " = " + varList.get(keyList[i]));

    }
  }




  //Tries to create a buffered reader from a selected file
  //Closes the program should the file no exist
  public static String[] parseText(Path filePath ) {
    List<String> docToString = new ArrayList<>();

    try(BufferedReader reader = new BufferedReader(new FileReader(filePath.toString()))) {
      String line = reader.readLine();

      while(line != null) {
        docToString.add(line);
        line = reader.readLine();

      }

      return docToString.toArray(new String[0]);
    }
    catch(IOException e) {
      System.exit(0);
    }

    return null;
  }

  //Opens a JFileChooser in the directory of the program
  //Returns the file that the user selects
  public static Path fileSelect() {
    JFileChooser openText;
    String path = new File(".").getAbsolutePath();

    openText = new JFileChooser(path);

    int returnVal = openText.showOpenDialog(null);
    if(returnVal == JFileChooser.APPROVE_OPTION){
      return openText.getSelectedFile().toPath();

    }
    else {
      return null;
    }
  }

}
