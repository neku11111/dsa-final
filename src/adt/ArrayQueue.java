package adt;

public class ArrayQueue<T> implements QueueInterface<T> {

    private T[] queue;
    private int size;
    private static final int DEFAULT_CAPACITY = 50;

    @SuppressWarnings("unchecked")
    public ArrayQueue() {
        queue = (T[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public boolean enqueue(T newEntry) {
        if (size == queue.length) {
            return false;
        }
        queue[size++] = newEntry;
        return true;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        T front = queue[0];
        for (int i = 1; i < size; i++) {
            queue[i - 1] = queue[i];
        }
        queue[--size] = null;
        return front;
    }

    @Override
    public T peek() {
        return isEmpty() ? null : queue[0];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            queue[i] = null;
        }
        size = 0;
    }

    public T getEntry(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return queue[index];
    }

    public boolean remove(T entry) {
        for (int i = 0; i < size; i++) {
            if (queue[i].equals(entry)) {
                for (int j = i + 1; j < size; j++) {
                    queue[j - 1] = queue[j];
                }
                queue[--size] = null;
                return true;
            }
        }
        return false;
    }

    public boolean insertAt(int index, T newEntry) {
        if (size == queue.length || index < 0 || index > size) {
            return false;
        }
        for (int i = size; i > index; i--) {
            queue[i] = queue[i - 1];
        }
        queue[index] = newEntry;
        size++;
        return true;
    }
}
