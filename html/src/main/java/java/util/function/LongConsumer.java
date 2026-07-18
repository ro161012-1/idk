package java.util.function;

@FunctionalInterface
public interface LongConsumer {
    void accept(long value);
    default LongConsumer andThen(LongConsumer after) { return v -> { accept(v); after.accept(v); }; }
}
