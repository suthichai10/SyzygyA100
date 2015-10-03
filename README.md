# SyzygyA100
An 8-bit CPU created in Logisim  
  
### Registers
* __R0__: The most used register. Used for branch comparison (for jumps), ALU output, writing its value to memory, and fetching a value from memory to this register. The following operations will overwrite this register:
  * Any ALU operation
  * A read from memory (not registers)
* __R1__: Used as a general purpose register. Has no extra functions.
* __R2__: Jump and memory pointer. The value in this register will be the instruction index a jump branches to, and the index of memory that is written to or read from.
* __R3__: Contains the value that will be displayed on a seven-segment display when the \'disp\' instruction is called. General purpose otherwise.
  
  
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
1aaa aaaa
  a: The 7-bit value (0-127 unsigned, or -64-63 signed) to be assigned to R0.

load <reg> <alu side>
0001 aab_
  a: Register to read the value from
  b: ALU register to write to:
    0: A
    1: B

copy <src> <dest>
0010 aabb
  a: Register to read value from
  b: Register to write the result to
  
<op> [shift direction]  (ALU Instructions)
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
0100 abc_
  a: Jump if R0 < 0
  b: Jump if R0 = 0
  c: Jump if R0 > 0
  (Set a, b, and c to always jump. Use both a and b for <=)

mem <get/set>
0101 a___
  a: Memory access mode:
    0: Set R0 to memory address R2 (get)
    1: Set memory address R2 to R0's value (set)

disp
0110 ____
  Set the display to the value in R3.
```

### Example Programs  
  
##### Addition (3 + 5 = 8)
```
00: push 3
01: load 0, 0
02: push 5
03: load 0, 1
04: sum
05: copy 0, 3
06: disp  
```
##### Integer Division  
(yeah, it's long; it's a minimal CPU)
```
00: push 12
01: copy 0, 1
02: push 0
03: copy 0, 2
04: push 7
05: mem set
06: load 0, 0
07: neg
08: load 0, 0
09: push 1
0A: load 0, 1
0B: push 1
0C: copy 0, 2
0D: sum
0E: mem set
0F: push 1
10: copy 0, 2
11: mem get
12: load 1, 0
13: load 0, 1
14: push 0x19
15: copy 0, 2
16: sum
17: jump lt
18: copy 0, 3
19: push 2
1A: copy 0, 2
1B: mem get
1C: load 0, 0
1D: push 1
1E: load 0, 1
1F: sum
20: mem set
21: copy 3, 1
22: push 0x0F
23: copy 0, 2
24: jump
25: push 2
26: copy 0, 2
27: mem get
28: copy 0, 3
29: disp
```
