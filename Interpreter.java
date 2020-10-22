import java.util.*;

public class Interpreter {
  private static String[] doc;
  private HashMap<String, Integer> variables = new HashMap<>();
  private int arrayLength = 0;

  public Interpreter(String[] docText) {
    doc = docText;

  }

  //splits the line from the string into each key part
  //includes trimming excess whitespace
  private String[] parseLine(int i,  String[] document) {
    String trimDoc = document[i].trim();
    String[] output = trimDoc.split("\s|;");
    return output;
  }

  //Selects an operation and calls the correct method
  //the inputs determine the line itself, split by spaces
  //the line number of the line relative to the code block
  //and the code block used
  private void operation(String[] line, int lineNum, String[] document) {
    switch(line[0]) {
      case "clear":
        clearVar(line[1]);
        break;

      case "incr":
        addVar(line[1]);
        break;

      case "decr":
        subVar(line[1]);
        break;

      case "while":

        whileVar(line, lineNum, document);



    }


  }
  private void clearVar(String var) {
    variables.put(var, 0);

  }

  private void addVar(String var) {
    Integer value = variables.get(var);
    if(value == null){
      variables.put(var, 1);

    } else {
      variables.put(var, value + 1);

    }
  }

  private void subVar(String var) {
    Integer value = variables.get(var);
    if(value == null || value <= 1) {
      variables.put(var, 0);

    } else {
      variables.put(var, value - 1);

    }
  }

  //Sets a variable and end point, lineCount determines startpoint
  private void whileVar(String[] line, int lineCount, String[] doc) {

    //block all giving the while information, including
    //where the loop starts in the code block
    //so the code doesn't reread it after the loop is over
    String var = line[1];                //Variable number
    List<String> loopCode = new ArrayList<String>();
    int end = Integer.parseInt(line[3]); //End of while
    int startPos = lineCount;

    //block is all about moving into the loop, then parsing lines
    //of the loop itself
    lineCount++;                         //Selects next line
    String lineCheck = doc[lineCount];
    String[] parsedLine = (lineCheck.trim()).split("\s|;");
    int whileCount = 0;

    //Generates a loop array, filling up a code block to be
    //worked on by the operator method
    while(!parsedLine[0].equals("end") || whileCount != 0) {

      loopCode.add(lineCheck);


      if(parsedLine[0].equals("while")) {
        whileCount++;
      } else if( parsedLine[0].equals("end")) {
        whileCount--;
      }
      lineCount++;
      lineCheck = doc[lineCount];
      parsedLine = (lineCheck.trim()).split("\s|;");

    }

    String[] array = loopCode.toArray(new String[0]);


    while(variables.get(var) != end) {
      calculate(array, startPos);

    }

    arrayLength = array.length + 1;

  }

  //takes in a block of code, and calculates it
  //also pushes the block of code forward for further
  //looping if necessary
  public void calculate(String[] documents, int start) {

    for(int i = 0; i<documents.length; i++) {

      operation(parseLine(i, documents), i, documents);
      if(arrayLength != 0) {
        i += arrayLength;
        arrayLength = 0;
      }

    }
  }

  //same as above, but will start the code block
  // from the imported doc and parse
  public void calculate() {

    for (int i = 0; i < doc.length; i++) {

      operation(parseLine(i, doc), i, doc);
      if(arrayLength != 0) {
        i += arrayLength;

        arrayLength = 0;
      }



    }
  }

  public HashMap getVars() {
    return variables;

  }

}