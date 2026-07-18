package java.util.function;

@FunctionalInterface
public interface BiFunction<T, U, R> {
    R apply(T t, U u);
    default <V> BiFunction<T, U, V> andThen(Function<? super R, ? extends V> after) { return (t, u) -> after.apply(apply(t, u)); }
}
