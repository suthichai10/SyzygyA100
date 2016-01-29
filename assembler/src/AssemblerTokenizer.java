import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;

import static SyzWords;

public class AssemblerTokenizer {
  
  public AssemblerTokenizer(File f) {
    try {
      Scanner fscan = new Scanner(f);
      parseTokens(f, fscan);
    } catch (FileNotFoundException e) {
      System.err.println("AssemblerTokenizer: File not found.");
      e.printStackTrace();
    }
  }
  
  private void parseTokens(File f, Scanner sc) {
    while(sc.hasNextLine) {
      
    }
  }
  
}