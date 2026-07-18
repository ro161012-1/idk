package java.util.function;

@FunctionalInterface
public interface IntPredicate {
    boolean test(int value);
    default IntPredicate and(IntPredicate other) { return v -> test(v) && other.test(v); }
    default IntPredicate or(IntPredicate other) { return v -> test(v) || other.test(v); }
    default IntPredicate negate() { return v -> !test(v); }
}
