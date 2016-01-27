# SyzygyA100
An 8-bit CPU created in Logisim  
  
### Specifications
* Instruction width: 8
* Data width: 8
* Memory address width: 8
* Maximum integer value: 255
* Instruction memory: 256B
* System memory: 256B
* Instructions are decoded on the rising-edge of the clock tick, and are executed on the falling edge.
* Expandable with up to 16 external components.
* See asm/examples/ folder for example programs.
  
### Registers
* __R0__: The most used register. Used for ALU output, writing its value to memory, and fetching a value from memory to this register. The following operations will overwrite this register:
  * Any ALU operation
  * A read from memory (not registers)
* __R1__: Used as a general purpose register. Has no extra functions.
* __R2__: Jump and memory pointer, capable of addressing 256 registers (only 32 will likely be available in the Minecraft version for simplicity's sake). The value in this register will be the instruction index a jump branches to, and the index of memory that is written to or read from.
* __R3__: Contains the value that will be displayed on a seven-segment display when the 'disp' instruction is called. General purpose otherwise.
  
  
### Instruction Set
##### Key
```
Line 1: Assembly code mnemonic and options
  <Option>: Needed
  [Option]: Optional
Line 2: Machine Code
  0 or 1: Necessary to set these bits to perform this operation
  Letters a-d: These bits correspond to certain options. Same letters indicate multiple bits used for an option.
  Underscores: Ignored bits
```
```

push <number>
  Set R0 to <number>.
1aaa aaaa
  a: The 7-bit value (0-127) to be assigned to R0.

sys
  System-reserved operations (not yet implemented).
0000 ____

load <reg> <alu side>
  Load the value of a register to any of the ALU's inputs.
0001 aabc
  a: Register to read the value from
  b: Write to ALU input A
  c: Write to ALU input B

copy <src> <dest>
  Copy the value of one register to another.
0010 aabb
  a: Register to read value from
  b: Register to write the result to
  
<op> [shift direction]  (ALU Instructions)
  ALU operations.
0011 aabc
  a: Operation to perform (with worst-case tick counts):
    0: Pass (1 tick)
    1: Sum (19 ticks worst-case using Kogge-Stone down from 32 using Ripple-Carry)
    2: And (4 ticks)
    3: Shift (5 ticks)
  b: Negate (bitwise) ALU's output
  c: Shift direction:
    0: Left
    1: Right

jump [lt/gt/eq/lte/gte]
  Jump to an instruction number, where the number is the value stored in R2 on conditions involving R0's value.
0100 abc_
  a: Jump if R0 < 0
  b: Jump if R0 = 0
  c: Jump if R0 > 0
  (Set a, b, and c to always jump. Use both a and b for <=)

mem <get/set>
  Read from and write to system memory.
0101 a___
  a: Memory access mode:
    0: Set R0 to memory address R2 (get)
    1: Set memory address R2 to R0's value (set)

ext <id>
  External I/O (displays, hard drive, network) control (not yet implemented).
0110 aaaa
  a: The I/O channel to read/write along.
```
