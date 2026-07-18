package java.util.function;

@FunctionalInterface
public interface DoubleConsumer {
    void accept(double value);
    default DoubleConsumer andThen(DoubleConsumer after) { return v -> { accept(v); after.accept(v); }; }
}
