/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

/**
 *
 * @author Jun Yat
 */
public interface QueueInterface<T> {

    boolean enqueue(T newEntry);

    T dequeue();

    T peek();

    boolean isEmpty();

    int size();

    
}
