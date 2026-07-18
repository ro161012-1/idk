package java.util;

@FunctionalInterface
public interface Comparator<T> {
    int compare(T o1, T o2);
    boolean equals(Object obj);
    default Comparator<T> reversed() { return (a, b) -> compare(b, a); }
    default Comparator<T> thenComparing(Comparator<? super T> other) { return (a, b) -> { int res = compare(a, b); return res != 0 ? res : other.compare(a, b); }; }
    default <U> Comparator<T> thenComparing(Function<? super T, ? extends U> keyExtractor, Comparator<? super U> keyComparator) { return thenComparing(comparing(keyExtractor, keyComparator)); }
    default <U extends Comparable<? super U>> Comparator<T> thenComparing(Function<? super T, ? extends U> keyExtractor) { return thenComparing(comparing(keyExtractor)); }
    static <T extends Comparable<? super T>> Comparator<T> naturalOrder() { return (a, b) -> a.compareTo(b); }
    static <T> Comparator<T> nullsFirst(Comparator<? super T> comparator) { return (a, b) -> { if (a == null) return b == null ? 0 : -1; if (b == null) return 1; return comparator.compare(a, b); }; }
    static <T> Comparator<T> nullsLast(Comparator<? super T> comparator) { return (a, b) -> { if (a == null) return b == null ? 0 : 1; if (b == null) return -1; return comparator.compare(a, b); }; }
    static <T, U> Comparator<T> comparing(Function<? super T, ? extends U> keyExtractor) { return (a, b) -> ((Comparable<U>)keyExtractor.apply(a)).compareTo(keyExtractor.apply(b)); }
    static <T, U> Comparator<T> comparing(Function<? super T, ? extends U> keyExtractor, Comparator<? super U> keyComparator) { return (a, b) -> keyComparator.compare(keyExtractor.apply(a), keyExtractor.apply(b)); }
    static <T> Comparator<T> comparingInt(ToIntFunction<? super T> keyExtractor) { return (a, b) -> Integer.compare(keyExtractor.applyAsInt(a), keyExtractor.applyAsInt(b)); }
    static <T> Comparator<T> comparingLong(ToLongFunction<? super T> keyExtractor) { return (a, b) -> Long.compare(keyExtractor.applyAsLong(a), keyExtractor.applyAsLong(b)); }
    static <T> Comparator<T> comparingDouble(ToDoubleFunction<? super T> keyExtractor) { return (a, b) -> Double.compare(keyExtractor.applyAsDouble(a), keyExtractor.applyAsDouble(b)); }
}
