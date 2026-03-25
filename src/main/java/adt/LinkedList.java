/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

/**
 *
 * @author User
 * @param <T>
 */


public class LinkedList<T> implements ListInterface<T> {
    private Node<T> head;
    private int numberOfEntries;

    private static class Node<T> {
        private T data;
        private Node<T> next; //only has next here no previous

        public Node(T data) {
            this(data, null);
        }

        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }

    // Adds to the end of the list
    @Override
    public void add(T newEntry) {
        add(numberOfEntries + 1, newEntry);
    }

    // Adds at a specific position
    @Override
    public boolean add(int newPosition, T newEntry) {
        if (newPosition < 1 || newPosition > numberOfEntries + 1) {
            return false;
        }

        Node<T> newNode = new Node<>(newEntry);

        if (newPosition == 1) {
            newNode.next = head;
            head = newNode;
        } else {
            Node<T> nodeBefore = getNodeAt(newPosition - 1);
            newNode.next = nodeBefore.next;
            nodeBefore.next = newNode;
        }

        numberOfEntries++;
        return true;
    }

    // Removes the entry at the given position
    @Override
    public T remove(int givenPosition) {
        if (givenPosition < 1 || givenPosition > numberOfEntries) {
            throw new IndexOutOfBoundsException("Invalid position: " + givenPosition);
        }

        T removedData;

        if (givenPosition == 1) {
            removedData = head.data;
            head = head.next;
        } else {
            Node<T> nodeBefore = getNodeAt(givenPosition - 1);
            removedData = nodeBefore.next.data;
            nodeBefore.next = nodeBefore.next.next;
        }

        numberOfEntries--;
        return removedData;
    }

    // Clears the list
    @Override
    public void clear() {
        head = null;
        numberOfEntries = 0;
    }

    // Replaces data at a given position
    @Override
    public boolean replace(int givenPosition, T newEntry) {
        if (givenPosition < 1 || givenPosition > numberOfEntries) {
            return false;
        }

        Node<T> node = getNodeAt(givenPosition);
        node.data = newEntry;
        return true;
    }

    // Gets the entry at a given position
    @Override
    public T getEntry(int givenPosition) {
        if (givenPosition < 1 || givenPosition > numberOfEntries) {
            throw new IndexOutOfBoundsException("Invalid position: " + givenPosition);
        }

        return getNodeAt(givenPosition).data;
    }

    // Checks if the list contains a specific entry
    @Override
    public boolean contains(T anEntry) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(anEntry)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // Returns the number of entries
    @Override
    public int getNumberOfEntries() {
        return numberOfEntries;
    }

    // Checks if the list is empty
    @Override
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    // Always returns false for linked list
    @Override
    public boolean isFull() {
        return false;
    }

    // Utility: Get node at a specific position
    private Node<T> getNodeAt(int position) {
        Node<T> current = head;
        for (int i = 1; i < position; i++) {
            current = current.next;
        }
        return current;
    }

    // Optional: for testing or traversal
    public Node<T> getHead() {
        return head;
    }
}

