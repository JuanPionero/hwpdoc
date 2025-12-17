package ext.org.apache.poi.hhwpf.util;

import org.apache.commons.math3.util.Pair;

import java.util.Objects;

public class Tuple<T, V>  {
    final private T left;
    final private V right;
    public Tuple(T left, V right) {
        this.left = left;
        this.right = right;
    }

    public T getLeft() {
        return left;
    }

    public V getRight() {
        return right;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pair)) {
            return false;
        } else {
            Tuple<?, ?> oP = (Tuple<?, ?>) o;
            return Objects.equals(left, oP.left) &&
                    Objects.equals(right, oP.right);
        }
    }
    public String toString() {
        return "[" + left + ", " + right + "]";
    }


}
