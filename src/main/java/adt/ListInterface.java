package adt;

public interface ListInterface<T> {
    void add(T newEntry);
    boolean add(int newPosition, T newEntry);
    T remove(int givenPosition);
    void clear();
    boolean replace(int givenPosition, T newEntry);
    T getEntry(int givenPosition);
    boolean contains(T anEntry);
    int getNumberOfEntries();
    boolean isEmpty();
    boolean isFull();
}