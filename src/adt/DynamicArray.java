/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package adt;

/**
 *
 * @author LENOVO LOQ 15APH8
 */
public class DynamicArray<T> implements DynamicArrayInterface<T>{
    private T[] array;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public DynamicArray() {
        array = (T[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @SuppressWarnings("unchecked")
    private void ensureCapacity() {
        if (size >= array.length) {
            T[] newArray = (T[]) new Object[array.length * 2];
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
        }
    }

    public boolean add(T anEntry) {
        if (anEntry == null) return false;
        ensureCapacity();
        array[size++] = anEntry;
        return true;
    }

    public T remove(int givenPosition) {
        if (isEmpty() || givenPosition < 0 || givenPosition >= size) {
            return null;
        }
        T removed = array[givenPosition];
        for (int i = givenPosition; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        array[--size] = null; // clear last reference
        return removed;
    }

    public T getEntry(int givenPosition) {
        if (isEmpty() || givenPosition < 0 || givenPosition >= size) {
            return null;
        }
        return array[givenPosition];
    }

    public boolean contains(T anEntry) {
        if (anEntry == null) return false;
        for (int i = 0; i < size; i++) {
            if (array[i].equals(anEntry)) return true;
        }
        return false;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    public void clear() {
        array = (T[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public int indexOf(T anEntry) {
        if (anEntry == null) return -1;
        for (int i = 0; i < size; i++) {
            if (array[i].equals(anEntry)) return i;
        }
        return -1;
    }

    public int getSize() {
        return size;
    }

    public T replace(int givenPosition, T newEntry) {
        if (isEmpty() || newEntry == null || givenPosition < 0 || givenPosition >= size) {
            return null;
        }
        T old = array[givenPosition];
        array[givenPosition] = newEntry;
        return old;
    }
    
    public int size() {
        return size;
    }
    
}

