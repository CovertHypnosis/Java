import java.util.Scanner;

public class TowerOfHanoi {
	/** Main method  */
	public static void main(String[] args) {
		// creating a scanner using try-with-resources Statement
		try (Scanner input = new Scanner(System.in)) {
			System.out.print("Enter number of disks: ");
			int n = input.nextInt();
			
			// Find the solution using recursion
			System.out.println("The moves are: ");
			moveDisks(n, 'A', 'B', 'C');
		}
	}
	
	// Finding a solution
	public static void moveDisks(int n, char fromTower, char toTower, char auxTower) {
		if (n == 1) // Stopping condition
			System.out.println("Move disk " + n + " from " + fromTower + " to " + toTower);
		else {
			moveDisks(n - 1, fromTower, auxTower, toTower);
			System.out.println("Move disk " + n + " from " + fromTower + " to " + toTower);
			moveDisks(n - 1, auxTower, toTower, fromTower);
		}
	}
}
