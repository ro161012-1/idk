package java.util.function;

@FunctionalInterface
public interface DoubleUnaryOperator {
    double applyAsDouble(double operand);
    default DoubleUnaryOperator andThen(DoubleUnaryOperator after) { return v -> after.applyAsDouble(applyAsDouble(v)); }
}
