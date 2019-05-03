public class Kadane {
    public static int evaluate(int[] A) {
        int maximum = Integer.MIN_VALUE;
        int temp = 0;
        for (int value : A) {
            temp += value;
            maximum = maximum < temp ? temp : maximum;
            temp = temp < 0 ? 0 : temp;
        }

        return maximum;
    }
}
