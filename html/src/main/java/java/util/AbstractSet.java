package java.util;

public abstract class AbstractSet<E> extends AbstractCollection<E> implements Set<E> {
    protected AbstractSet() {}
    @Override public boolean equals(Object o) { return false; }
    @Override public int hashCode() { return 0; }
    @Override public boolean removeAll(Collection<?> c) { return false; }
}
