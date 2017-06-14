package tracker.util;

public class MathUtils {

    public static int fitToBounds(int value, int min, int max) {
        if (value < min) {
            value = min;
        }

        if (value > max) {
            value = max;
        }

        return value;
    }
}
