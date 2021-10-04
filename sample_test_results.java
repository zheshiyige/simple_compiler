int nums();
  Code:
     0: iconst_5
     1: istore_1
     2: iload_1
     3: bipush 6
     5: imul
     6: iconst_1
     7: iadd
     8: istore_2
     9: iload_2
     10: iconst_2
     11: imul
     12: iload_1
     13: iconst_1
     14: idiv
     15: isub
     16: istore 5
     18: iload_1
     19: iconst_1
     20: iadd
     21: iload_2
     22: iconst_2
     23: isub
     24: imul
     25: iload_2
     26: iconst_2
     27: isub
     28: idiv
     29: istore_3
     30: iload_3
     31: iload_3
     32: imul
     33: istore 4
     35: iload 4
     37: ireturn

int max2(int,int);
  Code:
     0: iload_1
     1: iload_2
     2: if_icmple 10
     5: iload_1
     6: ireturn
     7: goto 12
     10: iload_2
     11: ireturn

int max4(int,int,int,int);
  Code:
     0: aload_0
     1: iload_1
     2: iload_2
     3: invokevirtual #1
     6: istore 5
     8: aload_0
     9: iload_3
     10: iload 4
     12: invokevirtual #1
     15: istore 6
     17: aload_0
     18: iload 5
     20: iload 6
     22: invokevirtual #1
     25: ireturn

int fact(int);
  Code:
     0: iconst_1
     1: istore_2
     2: iconst_1
     3: istore_3
     4: iload_2
     5: iload_1
     6: if_icmpge 20
     9: iload_2
     10: iconst_1
     11: iadd
     12: istore_2
     13: iload_3
     14: iload_2
     15: imul
     16: istore_3
     17: goto 4
     20: iload_3
     21: ireturn

int rgcd(int,int);
  Code:
     0: iload_1
     1: iload_2
     2: if_icmple 14
     5: aload_0
     6: iload_1
     7: iload_2
     8: isub
     9: iload_2
     10: invokevirtual #4
     13: ireturn
     14: iload_1
     15: iload_2
     16: if_icmpge 28
     19: aload_0
     20: iload_1
     21: iload_2
     22: iload_1
     23: isub
     24: invokevirtual #4
     27: ireturn
     28: iload_1
     29: ireturn

int f91(int);
  Code:
     0: iload_1
     1: bipush 100
     3: if_icmple 14
     6: iload_1
     7: bipush 10
     9: isub
     10: istore_2
     11: goto 29
     14: aload_0
     15: iload_1
     16: bipush 11
     18: iadd
     19: invokevirtual #5
     22: istore_3
     23: aload_0
     24: iload_3
     25: invokevirtual #5
     28: istore_2
     29: iload_2
     30: ireturn