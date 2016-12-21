
package com.rath.syzbbck;

/**
 * This class provides methods to encode assembly to raw binary, and vice-versa.
 * 
 * @author Tim Backus tbackus127@gmail.com
 *
 */
public class SyzInstr {
  
  /**
   * Transforms an assembly instruction into machine code.
   * 
   * @param instr the instruction String.
   * @return the byte code of the instruction.
   */
  public static final byte encode(String instr) {
    
    // Tokenize instruction
    final String[] tokens = instr.split("\\s+", 2);
    String[] argTokens = null;
    switch (tokens[0].toLowerCase()) {
      
      // push instruction
      case "push":
        return (byte) ((byte) -128 | (byte) Byte.parseByte(tokens[1]));
      
      // load instruction
      case "load":
        byte result = 16;
        argTokens = tokens[1].split(",+\\s+");
        result |= Byte.parseByte(argTokens[0]) << 2;
        result |= (tokens[1].toUpperCase().contains("A")) ? 2 : 0;
        result |= (tokens[1].toUpperCase().contains("B")) ? 1 : 0;
        return result;
      
      // copy instruction
      case "copy":
        result = 32;
        argTokens = tokens[1].split(",+\\s+");
        result |= Byte.parseByte(argTokens[0]) << 2;
        result |= Byte.parseByte(argTokens[1]);
        return result;
      
      // memory I/O
      case "mem":
        result = 80;
        result |= (tokens[1].contains("set")) ? 8 : 0;
        return result;
      
      // jump instructions
      case "jmp":
        return 78;
      case "jump":
        return 78;
      case "jlt":
        return 72;
      case "jgt":
        return 66;
      case "jeq":
        return 68;
      case "jle":
        return 76;
      case "jge":
        return 70;
      case "jne":
        return 74;
      
      // ALU instructions
      case "add":
        return 52;
      case "sum":
        return 52;
      case "neg":
        return 50;
      case "addn":
        return 54;
      case "sumn":
        return 54;
      case "and":
        return 56;
      case "andn":
        return 58;
      case "nand":
        return 58;
      case "lsl":
        return 60;
      case "lsln":
        return 62;
      case "lsr":
        return 61;
      case "lsrn":
        return 63;
      default:
        return 0;
    }
  }
  
  /**
   * Turns machine code into human-readable SYZ assembly code.
   * 
   * @param b the byte to decode.
   * @return a String representation of the passed machine code.
   */
  public static final String decode(byte b) {
    
    // push instruction
    if (SyzOps.getBit(b, 7)) {
      return "push " + (b & 0x7f);
    }
    
    // other instructions
    final byte op = SyzOps.getBitRange(b, 4, 6);
    switch (op) {
      
      // system instructions (WIP)
      case 0:
        return "NO OP";
      
      // load instructions
      case 1:
        if (SyzOps.getBitRange(b, 0, 1) == 0) return "NO OP";
        return "load " + SyzOps.getBitRange(b, 2, 3) + ", " + (SyzOps.getBit(b, 1) ? "A" : "")
            + (SyzOps.getBit(b, 0) ? "B" : "");
      
      // copy instructions
      case 2:
        return "copy " + SyzOps.getBitRange(b, 2, 3) + ", " + SyzOps.getBitRange(b, 0, 1);
      
      // ALU instructions
      case 3:
        final byte aluOp = SyzOps.getBitRange(b, 2, 3);
        String aluRes = "";
        switch (aluOp) {
          
          // raw negation
          case 0:
            if (SyzOps.getBit(b, 1)) {
              return "neg";
            }
            return "NO OP";
          
          // addition
          case 1:
            aluRes += "add";
          break;
        
          // logical AND
          case 2:
            aluRes += "and";
          break;
        
          // shifts
          case 3:
            aluRes += "ls";
          break;
          default:
            return "NO OP";
        }
        
        // choose shift direction
        if (aluOp == 3) {
          if (SyzOps.getBit(b, 0)) {
            aluRes += "r";
          } else {
            aluRes += "l";
          }
        }
        
        // check negation
        if (SyzOps.getBit(b, 1)) {
          aluRes += "n";
        }
        return aluRes;
      
      // jump instructions
      case 4:
        final byte jump = SyzOps.getBitRange(b, 1, 3);
        switch (jump) {
          case 0:
            return "NO OP";
          case 1:
            return "jgt";
          case 2:
            return "jeq";
          case 3:
            return "jge";
          case 4:
            return "jlt";
          case 5:
            return "jne";
          case 6:
            return "jle";
          case 7:
            return "jmp";
          default:
            return "NO OP";
        }
        
        // memory I/O
      case 5:
        return "mem " + (SyzOps.getBit(b, 3) ? "set" : "get");
      default:
        return "NO OP";
      
    }
  }
}
