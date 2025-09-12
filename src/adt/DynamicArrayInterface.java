/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

/**
 *
 * @author LENOVO LOQ 15APH8
 */
public interface DynamicArrayInterface<T> {

    boolean add(T anEntry);

    T remove(int givenPosition);

    T getEntry(int givenPosition);

    boolean isEmpty();

    void clear();

    int indexOf(T anEntry);

    int getSize();

    T replace(int givenPosition, T newEntry);
    int size();
}

