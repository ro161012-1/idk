package java.util;

public final class Optional<T> implements Serializable {
    private static final Optional<?> EMPTY = new Optional<>();
    private final T value;
    
    private Optional() { this.value = null; }
    private Optional(T value) { this.value = value; }
    
    public static <T> Optional<T> empty() { @SuppressWarnings("unchecked") Optional<T> t = (Optional<T>) EMPTY; return t; }
    public static <T> Optional<T> of(T value) { return new Optional<>(value); }
    public static <T> Optional<T> ofNullable(T value) { return value == null ? empty() : of(value); }
    
    public T get() { if (value == null) throw new NoSuchElementException("No value present"); return value; }
    public boolean isPresent() { return value != null; }
    public void ifPresent(Consumer<? super T> consumer) { if (value != null) consumer.accept(value); }
    public Optional<T> filter(Predicate<? super T> predicate) { return isPresent() && predicate.test(value) ? this : empty(); }
    public <U> Optional<U> map(Function<? super T, ? extends U> mapper) { return isPresent() ? Optional.ofNullable(mapper.apply(value)) : empty(); }
    public <U> Optional<U> flatMap(Function<? super T, Optional<U>> mapper) { return isPresent() ? mapper.apply(value) : empty(); }
    public T orElse(T other) { return value != null ? value : other; }
    public T orElseGet(Supplier<? extends T> other) { return value != null ? value : other.get(); }
    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X { if (value != null) return value; throw exceptionSupplier.get(); }
    
    @Override public boolean equals(Object obj) { if (this == obj) return true; if (!(obj instanceof Optional)) return false; Optional<?> other = (Optional<?>) obj; return Objects.equals(value, other.value); }
    @Override public int hashCode() { return value == null ? 0 : value.hashCode(); }
    @Override public String toString() { return value != null ? "Optional[" + value + "]" : "Optional.empty"; }
}