public class InsertionSort {
  // Method for sorting algorithm using insertion sort
	public static void insertionSort(int[] list) {
		for (int i = 1; i < list.length; i++) {
			int currentElement = list[i];
			int k;
			for (k = i - 1; k >= 0 && list[k] > currentElement; k--) {
				list[k + 1] = list[k];
				
				System.out.println("list[k + 1] is " + list[k + 1] + " and list[k] is " + list[k]);
			}
			
			// Insert the new element in k + 1
			list[k + 1] = currentElement;
		}
	}
}
