package by.vironit.training.basumatarau.instantMessengerApp.util;

import java.util.Objects;

public class TupleOfTwo<A, B > {
    public final A one;
    public final B two;

    public TupleOfTwo(A one,
                      B two) {
        this.one = one;
        this.two = two;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TupleOfTwo<?, ?> tupleTwo = (TupleOfTwo<?, ?>) o;
        return Objects.equals(one, tupleTwo.one) &&
                Objects.equals(two, tupleTwo.two);
    }

    @Override
    public int hashCode() {
        return Objects.hash(one, two);
    }
}