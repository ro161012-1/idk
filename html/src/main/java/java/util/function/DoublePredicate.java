package java.util.function;

@FunctionalInterface
public interface DoublePredicate {
    boolean test(double value);
    default DoublePredicate and(DoublePredicate other) { return v -> test(v) && other.test(v); }
    default DoublePredicate or(DoublePredicate other) { return v -> test(v) || other.test(v); }
    default DoublePredicate negate() { return v -> !test(v); }
}
