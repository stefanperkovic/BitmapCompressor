/******************************************************************************
 *  Compilation:  javac BitmapCompressor.java
 *  Execution:    java BitmapCompressor - < input.bin   (compress)
 *  Execution:    java BitmapCompressor + < input.bin   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   q32x48.bin
 *                q64x96.bin
 *                mystery.bin
 *
 *  Compress or expand binary input from standard input.
 *
 *  % java DumpBinary 0 < mystery.bin
 *  8000 bits
 *
 *  % java BitmapCompressor - < mystery.bin | java DumpBinary 0
 *  1240 bits
 ******************************************************************************/

/**
 *  The {@code BitmapCompressor} class provides static methods for compressing
 *  and expanding a binary bitmap input.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author Zach Blick
 *  @author Stefan Perkovic
 */
public class BitmapCompressor {

    /**
     * Reads a sequence of bits from standard input, compresses them,
     * and writes the results to standard output.
     */
    public static void compress() {
        String bits = BinaryStdIn.readString();
        int length = bits.length();

        int consecutive_seq = 0;
        char current_bit = bits.charAt(0);

        for(int i = 0; i < length; i++){
            char bit = bits.charAt(i);
            if (bit == current_bit){
                consecutive_seq ++;
            }
            else{
                BinaryStdOut.write((byte) consecutive_seq);
                BinaryStdOut.write(current_bit == '1');
                consecutive_seq = 1;
                current_bit = bit;
            }
        }
        BinaryStdOut.write((byte) consecutive_seq);
        BinaryStdOut.write(current_bit == '1');



        BinaryStdOut.close();
    }

    /**
     * Reads a sequence of bits from standard input, decodes it,
     * and writes the results to standard output.
     */
    public static void expand() {

        while(!BinaryStdIn.isEmpty()){
            int runLength = BinaryStdIn.readByte();
            boolean bit = BinaryStdIn.readBoolean();
            for (int i = 0; i < runLength; i++) {
                BinaryStdOut.write(bit);
            }

        }


        BinaryStdOut.close();
    }

    /**
     * When executed at the command-line, run {@code compress()} if the command-line
     * argument is "-" and {@code expand()} if it is "+".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}