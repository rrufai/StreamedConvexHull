/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.common;

import cg.geometry.primitives.Point;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author rrufai
 */
public class Utilities {

    public static class BigNumberTools {

        private static BigDecimal ZERO = new BigDecimal("0");
        private static BigDecimal ONE = new BigDecimal("1");
        private static BigDecimal TWO = new BigDecimal("2");
        public static final int DEFAULT_MAX_ITERATIONS = 50;
        public static final int DEFAULT_SCALE = 10;
        private  static BigDecimal error;
        private  static int iterations;
        private  static boolean traceFlag;
        private  static int scale = DEFAULT_SCALE;
        private  static int maxIterations = DEFAULT_MAX_ITERATIONS;

        //---------------------------------------
        // The error is the original number minus
        // (sqrt * sqrt). If the original number
        // was a perfect square, the error is 0.
        //---------------------------------------
        public BigDecimal getError() {
            return error;
        }

        //-------------------------------------------------------------
        // Number of iterations performed when square root was computed
        //-------------------------------------------------------------
        public int getIterations() {
            return iterations;
        }

        //-----------
        // Trace flag
        //-----------
        public boolean getTraceFlag() {
            return traceFlag;
        }

        public void setTraceFlag(boolean flag) {
            traceFlag = flag;
        }

        //------
        // Scale
        //------
        public int getScale() {
            return scale;
        }

        public void setScale(int scale) {
            this.scale = scale;
        }

        //-------------------
        // Maximum iterations
        //-------------------
        public int getMaxIterations() {
            return maxIterations;
        }

        public void setMaxIterations(int maxIterations) {
            this.maxIterations = maxIterations;
        }

        //--------------------------
        // Get initial approximation
        //--------------------------
        private static BigDecimal getInitialApproximation(BigDecimal n) {
            BigInteger integerPart = n.toBigInteger();
            int length = integerPart.toString().length();
            if ((length % 2) == 0) {
                length--;
            }
            length /= 2;
            BigDecimal guess = ONE.movePointRight(length);
            return guess;
        }

        //----------------
        // Get square root
        //----------------
        public static BigDecimal sqrt(BigInteger n) {
            return sqrt(new BigDecimal(n));
        }

        public static BigDecimal sqrt(BigDecimal n) {

            // Make sure n is a positive number

            if (n.compareTo(ZERO) <= 0) {
                throw new IllegalArgumentException();
            }

            BigDecimal initialGuess = getInitialApproximation(n);
            //trace("Initial guess " + initialGuess.toString());
            BigDecimal lastGuess = ZERO;
            BigDecimal guess = new BigDecimal(initialGuess.toString());

            // Iterate

            int iterations = 0;
            boolean more = true;
            while (more) {
                lastGuess = guess;
                guess = n.divide(guess, scale, BigDecimal.ROUND_HALF_UP);
                guess = guess.add(lastGuess);
                guess = guess.divide(TWO, scale, BigDecimal.ROUND_HALF_UP);
                //trace("Next guess " + guess.toString());
                error = n.subtract(guess.multiply(guess));
                if (++iterations >= maxIterations) {
                    more = false;
                } else if (lastGuess.equals(guess)) {
                    more = error.abs().compareTo(ONE) >= 0;
                }
            }
            return guess;

        }

        //------
        // Trace
        //------
        private void trace(String s) {
            if (traceFlag) {
                System.out.println(s);
            }
        }

        //----------------------
        // Get random BigInteger
        //----------------------
        public static BigInteger getRandomBigInteger(int nDigits) {
            StringBuilder sb = new StringBuilder();
            java.util.Random r = new java.util.Random();
            for (int i = 0; i < nDigits; i++) {
                sb.append(r.nextInt(10));
            }
            return new BigInteger(sb.toString());
        }
    }
    //-----
    // Test
    //-----

    public static void main(String[] args) {

        BigInteger n;
        BigDecimal sqrt;
        //BigSquareRoot app = new BigSquareRoot();
        //app.setTraceFlag(true);

        // Generate a random big integer with a hundred digits

        n = BigNumberTools.getRandomBigInteger(100);

        // Build an array of test numbers

        String testNums[] = {"9", "30", "720", "1024", n.toString()};

        for (int i = 0; i < testNums.length; i++) {
            n = new BigInteger(testNums[i]);
            if (i > 0) {
                System.out.println("----------------------------");
            }
            System.out.println("Computing the square root of");
            System.out.println(n.toString());
            int length = n.toString().length();
//            if (length > 20) {
//                app.setScale(length / 2);
//            }
            sqrt = BigNumberTools.sqrt(n);
            //System.out.println("Iterations: " + app.getIterations());
            System.out.println("Sqrt " + sqrt.toString());
            System.out.println(sqrt.multiply(sqrt).toString());
            System.out.println(n.toString());
            //System.out.println("Error " + app.getError().toString());
        }

    }
    
    public static <K extends Point>  boolean isMonotonic(K p, K q) {
        return p != null && q != null && p.getX() < q.getX() && p.getY() < q.getY();
    }
}
