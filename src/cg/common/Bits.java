/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.common;

import java.util.BitSet;

/**
 * source:  http://stackoverflow.com/questions/2473597/bitset-to-and-from-integer-long
 */
public class Bits {

    /**
     * BitSet stores value in Little-Endian format, i.e. the lowest index points 
     * to the most significant bit.
     * 
     * @param value
     * @param length
     * @return 
     */
  public static BitSet convert(long value, int length) {
    BitSet bits = new BitSet();
    int index = 0;
    while (value != 0 && index < length + 1) {
      if (value % 2L != 0) {
        bits.set(index);
      }
      ++index;
      value = value >>> 1;
    }
    
    return bits;
  }

  public static long convert(BitSet bits) {
    return bits.toLongArray()[0];
  }
}