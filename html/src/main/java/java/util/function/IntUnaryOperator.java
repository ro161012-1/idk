package java.util.function;

@FunctionalInterface
public interface IntUnaryOperator {
    int applyAsInt(int operand);
    default IntUnaryOperator andThen(IntUnaryOperator after) { return v -> after.applyAsInt(applyAsInt(v)); }
}
