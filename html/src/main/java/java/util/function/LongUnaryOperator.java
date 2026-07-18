package java.util.function;

@FunctionalInterface
public interface LongUnaryOperator {
    long applyAsLong(long operand);
    default LongUnaryOperator andThen(LongUnaryOperator after) { return v -> after.applyAsLong(applyAsLong(v)); }
}
