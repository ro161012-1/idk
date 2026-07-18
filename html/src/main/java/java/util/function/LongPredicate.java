package java.util.function;

@FunctionalInterface
public interface LongPredicate {
    boolean test(long value);
    default LongPredicate and(LongPredicate other) { return v -> test(v) && other.test(v); }
    default LongPredicate or(LongPredicate other) { return v -> test(v) || other.test(v); }
    default LongPredicate negate() { return v -> !test(v); }
}
