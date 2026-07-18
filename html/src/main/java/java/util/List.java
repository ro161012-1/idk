package java.util;

public interface List<E> extends Collection<E> {
    E get(int index);
    E set(int index, E element);
    void add(int index, E element);
    E remove(int index);
    int indexOf(Object o);
    int lastIndexOf(Object o);
    ListIterator<E> listIterator();
    ListIterator<E> listIterator(int index);
    List<E> subList(int fromIndex, int toIndex);
    @Override boolean add(E e);
    @Override boolean addAll(Collection<? extends E> c);
    @Override boolean addAll(int index, Collection<? extends E> c);
    @Override void clear();
    @Override boolean equals(Object o);
    @Override int hashCode();
    default void replaceAll(UnaryOperator<E> operator) {}
    default void sort(Comparator<? super E> c) {}
    default Spliterator<E> spliterator() { return null; }
}
