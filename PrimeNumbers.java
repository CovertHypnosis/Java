import java.io.*;

public class PrimeNumbers {
	final static int ARRAY_SIZE = 100000;
	
	public static void main(String[] args) throws Exception {
		// List that holds prime numbers
		final long N = 100000002;
		long[] primeNumbers = new long[ARRAY_SIZE];
		
		long number; // number to be tested for primeness
		RandomAccessFile inout = new RandomAccessFile("PrimeNumbers.dat", "rw");
		if (inout.length() == 0) {
			number =1;
		} else {
			inout.seek(inout.length() - 8);
			number = inout.readLong(); // get the last prime number in the file
		}
		
		int squareRoot = 1;
		
		// Repeatedly find prime numbers with labeled break statement
		newNumber:while (number <= N) {
			number++;
			inout.seek(0);
			
			if (squareRoot * squareRoot < number) {
				squareRoot++;
			}
			
			while (inout.getFilePointer() < inout.length()) {
				int size = readNextBatch(primeNumbers, inout);
				
				for (int k = 0; k < size && primeNumbers[k] <= squareRoot; k++) {
					if (number % primeNumbers[k] == 0) { // if this is true, exit
						continue newNumber;
					}
				}
			}
			
			// Append a new prime number to the end of the file
			inout.seek(inout.length());
			inout.writeLong(number);
			
		}
		
		inout.close();
	}
	
	public static int readNextBatch(long[] primeNumbers, RandomAccessFile inout) {
		int size = 0;
		try {
			while (inout.getFilePointer() < inout.length() && size < ARRAY_SIZE) {
				primeNumbers[size++] = inout.readLong();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		return size;
	}
}
