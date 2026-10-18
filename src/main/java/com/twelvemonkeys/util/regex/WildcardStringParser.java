package com.twelvemonkeys.util.regex;

import java.io.PrintStream;

@Deprecated
public class WildcardStringParser {
   public static final char[] ALPHABET = new char[]{
      'a',
      'b',
      'c',
      'd',
      'e',
      'f',
      'g',
      'h',
      'i',
      'j',
      'k',
      'l',
      'm',
      'n',
      'o',
      'p',
      'q',
      'r',
      's',
      't',
      'u',
      'v',
      'w',
      'x',
      'y',
      'z',
      'æ',
      'ø',
      'å',
      'A',
      'B',
      'C',
      'D',
      'E',
      'F',
      'G',
      'H',
      'I',
      'J',
      'K',
      'L',
      'N',
      'M',
      'O',
      'P',
      'Q',
      'R',
      'S',
      'T',
      'U',
      'V',
      'W',
      'X',
      'Y',
      'Z',
      'Æ',
      'Ø',
      'Å',
      '0',
      '1',
      '2',
      '3',
      '4',
      '5',
      '6',
      '7',
      '8',
      '9',
      '.',
      '_',
      '-'
   };
   public static final char FREE_RANGE_CHARACTER = '*';
   public static final char FREE_PASS_CHARACTER = '?';
   boolean initialized;
   String stringMask;
   WildcardStringParser.WildcardStringParserState initialState;
   int totalNumberOfStringsParsed;
   boolean debugging;
   PrintStream out;

   public WildcardStringParser(String var1) {
      this(var1, false);
   }

   public WildcardStringParser(String var1, boolean var2) {
      this(var1, var2, System.out);
   }

   public WildcardStringParser(String var1, boolean var2, PrintStream var3) {
      this.stringMask = var1;
      this.debugging = var2;
      this.out = var3;
      this.initialized = this.buildAutomaton();
   }

   private boolean checkIfStateInWildcardRange(WildcardStringParser.WildcardStringParserState var1) {
      WildcardStringParser.WildcardStringParserState var2 = var1;

      while (var2.previousState != null) {
         var2 = var2.previousState;
         if (isFreeRangeCharacter(var2.character)) {
            return true;
         }

         if (!isFreePassCharacter(var2.character)) {
            return false;
         }
      }

      return false;
   }

   private boolean checkIfLastFreeRangeState(WildcardStringParser.WildcardStringParserState var1) {
      return isFreeRangeCharacter(var1.character) ? true : isFreePassCharacter(var1.character) && this.checkIfStateInWildcardRange(var1);
   }

   private boolean isTrivialAutomaton() {
      for (int var1 = 0; var1 < this.stringMask.length(); var1++) {
         if (!isFreeRangeCharacter(this.stringMask.charAt(var1))) {
            return false;
         }
      }

      return true;
   }

   private boolean buildAutomaton() {
      WildcardStringParser.WildcardStringParserState var2 = null;
      WildcardStringParser.WildcardStringParserState var3 = null;
      WildcardStringParser.WildcardStringParserState var4 = null;
      if (this.stringMask != null && this.stringMask.length() > 0) {
         var3 = new WildcardStringParser.WildcardStringParserState(this.stringMask.charAt(0));
         var3.automatonStateNumber = 0;
         var3.previousState = null;
         if (this.checkIfLastFreeRangeState(var3)) {
            var4 = var3;
         }

         var2 = var3;
         this.initialState = var3;
         this.initialState.automatonStateNumber = 0;

         for (int var5 = 1; var5 < this.stringMask.length(); var5++) {
            char var1 = this.stringMask.charAt(var5);
            if (!isInAlphabet(var1) && !isWildcardCharacter(var1)) {
               System.err.println("one or more characters in string mask are not legal characters - aborting!");
               return false;
            }

            var2.lastFreeRangeState = var4;
            var3 = new WildcardStringParser.WildcardStringParserState(var1);
            var3.automatonStateNumber = var5;
            var3.previousState = var2;
            if (this.checkIfLastFreeRangeState(var3)) {
               var4 = var3;
            }

            var2.nextState = var3;
            var2 = var3;
            if (var3.automatonStateNumber == this.stringMask.length() - 1) {
               var3.lastFreeRangeState = var4;
            }
         }

         this.totalNumberOfStringsParsed = 0;
         return true;
      } else {
         System.err.println("string mask provided are null or empty - aborting!");
         return false;
      }
   }

   public static boolean isInAlphabet(char var0) {
      for (int var1 = 0; var1 < ALPHABET.length; var1++) {
         if (var0 == ALPHABET[var1]) {
            return true;
         }
      }

      return false;
   }

   public static boolean isFreeRangeCharacter(char var0) {
      return var0 == '*';
   }

   public static boolean isFreePassCharacter(char var0) {
      return var0 == '?';
   }

   public static boolean isWildcardCharacter(char var0) {
      return isFreeRangeCharacter(var0) || isFreePassCharacter(var0);
   }

   public String getStringMask() {
      return this.stringMask;
   }

   public boolean parseString(String var1) {
      if (this.debugging) {
         this.out.println("parsing \"" + var1 + "\"...");
      }

      this.totalNumberOfStringsParsed++;
      if (var1 == null) {
         if (this.debugging) {
            this.out.println("string to be parsed is null - rejection!");
         }

         return false;
      } else {
         WildcardStringParser.ParsableString var2 = new WildcardStringParser.ParsableString(var1);
         if (!var2.checkString()) {
            if (this.debugging) {
               this.out.println("one or more characters in string to be parsed are not legal characters - rejection!");
            }

            return false;
         } else if (!this.initialized) {
            System.err.println("automaton is not initialized - rejection!");
            return false;
         } else if (this.isTrivialAutomaton()) {
            if (this.debugging) {
               this.out.println("automaton represents a trivial string mask (accepts all strings) - acceptance!");
            }

            return true;
         } else if (var2.isEmpty()) {
            if (this.debugging) {
               this.out.println("string to be parsed is empty and not trivial automaton - rejection!");
            }

            return false;
         } else {
            boolean var3 = false;
            int var4 = 0;
            int var5 = 0;
            WildcardStringParser.WildcardStringParserState var6 = null;
            if (var2.charArray[0] != this.initialState.character && !isWildcardCharacter(this.initialState.character)) {
               if (this.debugging) {
                  this.out.println("cannot enter first automaton state - rejection!");
               }

               return false;
            } else {
               var6 = this.initialState;
               var2.index = 0;
               if (isFreePassCharacter(var6.character)) {
                  var4++;
               }

               for (int var7 = 0; var7 < var2.length(); var7++) {
                  if (this.debugging) {
                     this.out.println();
                  }

                  if (this.debugging) {
                     this.out
                        .println(
                           "parsing - index number "
                              + var7
                              + ", active char: '"
                              + var2.getActiveChar()
                              + "' char string index: "
                              + var2.index
                              + " number of chars since last free-range state: "
                              + var5
                        );
                  }

                  if (this.debugging) {
                     this.out.println("parsing - state: " + var6.automatonStateNumber + " '" + var6.character + "' - no of free-pass chars read: " + var4);
                  }

                  if (this.debugging) {
                     this.out.println("parsing - hasPerformedFreeRangeMovement: " + var3);
                  }

                  if (var6.nextState == null) {
                     if (this.debugging) {
                        this.out.println("parsing - runnerState.nextState == null");
                     }

                     if (isFreeRangeCharacter(var6.character)) {
                        if (!var3) {
                           if (this.debugging) {
                              this.out.println("no subsequent state (final state) and the state represents '*' - no skipping performed - acceptance!");
                           }

                           return true;
                        }

                        if (var2.reachedEndOfString()) {
                           if (var4 > var5) {
                              if (this.debugging) {
                                 this.out
                                    .println(
                                       "no subsequent state (final state) and the state represents '*' - end of parsing string, but not enough characters read - rejection!"
                                    );
                              }

                              return false;
                           }

                           if (this.debugging) {
                              this.out
                                 .println(
                                    "no subsequent state (final state) and the state represents '*' - end of parsing string and enough characters read - acceptance!"
                                 );
                           }

                           return true;
                        }

                        if (var4 <= var5) {
                           if (this.debugging) {
                              this.out
                                 .println(
                                    "no subsequent state (final state) and the state represents '*' - not the end of parsing string, but enough characters read - acceptance!"
                                 );
                           }

                           return true;
                        }

                        if (this.debugging) {
                           this.out
                              .println(
                                 "no subsequent state (final state) and the state represents '*' - not the end of parsing string and not enough characters read - read next character"
                              );
                        }

                        var2.index++;
                        var5++;
                     } else {
                        if (var2.reachedEndOfString()) {
                           if (var3 && var4 > var5) {
                              if (this.debugging) {
                                 this.out
                                    .println(
                                       "no subsequent state (final state) and skipping has been performed and end of parsing string, but not enough characters read - rejection!"
                                    );
                              }

                              return false;
                           }

                           if (this.debugging) {
                              this.out.println("no subsequent state (final state) and the end of the string to test is reached - acceptance!");
                           }

                           return true;
                        }

                        if (this.debugging) {
                           this.out.println("parsing - escaping process...");
                        }
                     }
                  } else {
                     if (this.debugging) {
                        this.out.println("parsing - runnerState.nextState != null");
                     }

                     if (isFreeRangeCharacter(var6.character)) {
                        var4 = 0;
                        var5 = 0;

                        for (WildcardStringParser.WildcardStringParserState var8 = var6.nextState;
                           var8 != null && isFreePassCharacter(var8.character);
                           var8 = var8.nextState
                        ) {
                           var6 = var8;
                           var3 = true;
                           var4++;
                        }

                        if (var6.nextState == null) {
                           if (this.debugging) {
                              this.out.println();
                           }

                           if (this.debugging) {
                              this.out
                                 .println(
                                    "parsing - index number "
                                       + var7
                                       + ", active char: '"
                                       + var2.getActiveChar()
                                       + "' char string index: "
                                       + var2.index
                                       + " number of chars since last free-range state: "
                                       + var5
                                 );
                           }

                           if (this.debugging) {
                              this.out
                                 .println("parsing - state: " + var6.automatonStateNumber + " '" + var6.character + "' - no of free-pass chars read: " + var4);
                           }

                           if (this.debugging) {
                              this.out.println("parsing - hasPerformedFreeRangeMovement: " + var3);
                           }

                           if (var3 && var4 >= var5) {
                              return true;
                           }

                           return false;
                        }
                     }

                     if (isFreeRangeCharacter(var6.nextState.character)) {
                        var6 = var6.nextState;
                        var2.index++;
                        var5++;
                     } else if (isFreePassCharacter(var6.nextState.character)) {
                        var6 = var6.nextState;
                        var2.index++;
                        var4++;
                        var5++;
                     } else if (!var2.reachedEndOfString() && var6.nextState.character == var2.getSubsequentChar()) {
                        var6 = var6.nextState;
                        var2.index++;
                        var5++;
                     } else {
                        if (var6.lastFreeRangeState == null) {
                           if (this.debugging) {
                              this.out
                                 .println(
                                    "the next state does not represent the same character as the next character in the string to test, and there are no last-free-range-state - rejection!"
                                 );
                           }

                           return false;
                        }

                        var6 = var6.lastFreeRangeState;
                        var2.index++;
                        var5++;
                     }
                  }
               }

               if (this.debugging) {
                  this.out.println("finished reading parsing string and not at any final state - rejection!");
               }

               return false;
            }
         }
      }
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder();
      if (!this.initialized) {
         var1.append(this.getClass().getName());
         var1.append(":  Not initialized properly!");
         var1.append("\n");
         var1.append("\n");
      } else {
         WildcardStringParser.WildcardStringParserState var2 = this.initialState;
         var1.append(this.getClass().getName());
         var1.append(":  String mask ");
         var1.append(this.stringMask);
         var1.append("\n");
         var1.append("\n");
         var1.append("      Automaton: ");

         for (; var2 != null; var2 = var2.nextState) {
            var1.append(var2.automatonStateNumber);
            var1.append(": ");
            var1.append(var2.character);
            var1.append(" (");
            if (var2.lastFreeRangeState != null) {
               var1.append(var2.lastFreeRangeState.automatonStateNumber);
            } else {
               var1.append("-");
            }

            var1.append(")");
            if (var2.nextState != null) {
               var1.append("   -->   ");
            }
         }

         var1.append("\n");
         var1.append("      Format: <state index>: <character> (<last free state>)");
         var1.append("\n");
         var1.append("      Number of strings parsed: ").append(this.totalNumberOfStringsParsed);
         var1.append("\n");
      }

      return var1.toString();
   }

   @Override
   public boolean equals(Object var1) {
      if (!(var1 instanceof WildcardStringParser)) {
         return super.equals(var1);
      } else {
         WildcardStringParser var2 = (WildcardStringParser)var1;
         return var2.initialized == this.initialized && var2.stringMask == this.stringMask;
      }
   }

   @Override
   public int hashCode() {
      return super.hashCode();
   }

   @Override
   protected Object clone() throws CloneNotSupportedException {
      return this.initialized ? new WildcardStringParser(this.stringMask) : null;
   }

   @Override
   protected void finalize() throws Throwable {
   }

   class ParsableString {
      char[] charArray;
      int index;

      ParsableString(String var2) {
         if (var2 != null) {
            this.charArray = var2.toCharArray();
         }

         this.index = -1;
      }

      boolean reachedEndOfString() {
         return this.index == this.charArray.length - 1;
      }

      int length() {
         return this.charArray.length;
      }

      char getActiveChar() {
         if (this.index > -1 && this.index < this.charArray.length) {
            return this.charArray[this.index];
         } else {
            System.err.println(this.getClass().getName() + ": trying to access character outside character array!");
            return ' ';
         }
      }

      char getSubsequentChar() {
         if (this.index > -1 && this.index + 1 < this.charArray.length) {
            return this.charArray[this.index + 1];
         } else {
            System.err.println(this.getClass().getName() + ": trying to access character outside character array!");
            return ' ';
         }
      }

      boolean checkString() {
         if (!this.isEmpty()) {
            for (int var1 = 0; var1 < this.charArray.length; var1++) {
               if (!WildcardStringParser.isInAlphabet(this.charArray[var1])) {
                  return false;
               }
            }
         }

         return true;
      }

      boolean isEmpty() {
         return this.charArray == null || this.charArray.length == 0;
      }

      @Override
      public String toString() {
         return new String(this.charArray);
      }
   }

   class WildcardStringParserState {
      int automatonStateNumber;
      char character;
      WildcardStringParser.WildcardStringParserState previousState;
      WildcardStringParser.WildcardStringParserState nextState;
      WildcardStringParser.WildcardStringParserState lastFreeRangeState;

      public WildcardStringParserState(char var2) {
         this.character = var2;
      }
   }
}
