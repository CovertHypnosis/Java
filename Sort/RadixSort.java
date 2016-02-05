import java.util.ArrayList;

public class RadixSort {
	public static void radixSort(int[] list, int numberOfDigits) {
		ArrayList<Integer>[] buckets = new ArrayList[10];
		for (int i = 0; i < buckets.length; i++) {
			buckets[i] = new ArrayList<Integer>();
		}
		
		for (int position = 0; position <= numberOfDigits; position++) {
			// Clear buckets
			for (int i = 0; i < buckets.length; i++)
				buckets[i].clear();
			
			for (int i = 0; i < list.length; i++) {
				int key = getKey(list[i], position);
				buckets[key].add(list[i]);
			}
			
			// Move the elements from the bucket back to list
			int k = 0;
			for (int i = 0; i < buckets.length; i++)  {
				for (int j = 0; j < buckets[i].size(); j++)
					list[k++] = buckets[i].get(j);
			}
		}
	}
	
	// return the digit at the specified position
	public static int getKey(int number, int position) {
		int result = 1;
		for (int i = 0; i < position; i++)
			result *= 10;
		
		return (number / result) % 10;
	}
}
