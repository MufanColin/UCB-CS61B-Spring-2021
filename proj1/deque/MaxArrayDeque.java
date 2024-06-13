package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private final Comparator<T> defaultComparator;

    public MaxArrayDeque(Comparator<T> c) {
        defaultComparator = c;
    }

    public T max() {
        T maxValue = this.get(0);
        for (T current: this) {
            if (defaultComparator.compare(current, maxValue) > 0) {
                maxValue = current;
            }
        }
        return maxValue;
    }

    public T max(Comparator<T> c) {
        T maxValue = this.get(0);
        for (T current: this) {
            if (c.compare(current, maxValue) > 0) {
                maxValue = current;
            }
        }
        return maxValue;
    }
}
