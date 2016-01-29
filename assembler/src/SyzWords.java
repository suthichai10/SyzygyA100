import java.util.HashMap;
import java.util.Arrays;

public class SyzWords {
  
  private static final String[] RESERVED_WORDS = new String[] {
    "sys", "push", "load", "copy", "alu", "jump", "mem", "ext"
  }
  
  private static final String[] SYSTEM_COMMANDS = new String[] {
    "halt"
  }
  
  private static final String[] ALU_COMMANDS = new String[] {
    "sum", "shft", "neg", "and"
  }
  
  private static final String[] JUMP_COMMANDS = new String[] {
    "jeq", "jne", "jlt", "jgt", "jle", "jge"
  }
  
  public static final HashSet<String> VALID_OPS = new HashSet<String>(Arrays.toList(RESERVED_WORDS));
  public static final HashSet<String> VALID_SYS = new HashSet<String>(Arrays.toList(SYSTEM_COMMANDS));
  public static final HashSet<String> VALID_ALU = new HashSet<String>(Arrays.toList(ALU_COMMANDS));
  public static final HashSet<String> VALID_JMP = new HashSet<String>(Arrays.toList(JUMP_COMMANDS));
  
  public static final String TOKEN_REGEX = "";
}