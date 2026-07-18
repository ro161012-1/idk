package java.util.function;

@FunctionalInterface
public interface BinaryOperator<T> extends BiFunction<T, T, T> {
    static <T extends Comparable<? super T>> BinaryOperator<T> minBy(Comparator<? super T> comparator) { return (a, b) -> comparator.compare(a, b) <= 0 ? a : b; }
    static <T extends Comparable<? super T>> BinaryOperator<T> maxBy(Comparator<? super T> comparator) { return (a, b) -> comparator.compare(a, b) >= 0 ? a : b; }
}
