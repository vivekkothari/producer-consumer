package com.github.vivekkothari.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author by vivekkothari on 14/04/17.
 */
public class LinkedBlockingQueue<E> implements BlockingQueue<E> {

    private static final Logger logger = LoggerFactory.getLogger(LinkedBlockingQueue.class);
    private final int capacity;
    private final Object mutex = new Object();
    private volatile int size;
    private Node<E> head;

    public LinkedBlockingQueue() {
        this(Integer.MAX_VALUE);
    }

    public LinkedBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean offer(E e) {
        synchronized (mutex) {
            if (remainingCapacity() == 0) {
                return false;
            } else {
                enqueue(e);
                return true;
            }
        }
    }

    @Override
    public void put(E e) throws InterruptedException {
        synchronized (mutex) {
            while (remainingCapacity() == 0) {
                logger.info("put is waiting....");
                mutex.wait();
                logger.info("put is waiting over...");
            }
            enqueue(e);
        }
    }

    @Override
    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        synchronized (mutex) {
            if (remainingCapacity() == 0) {
                logger.info("offer is waiting....");
                mutex.wait(TimeUnit.MILLISECONDS.toMillis(timeout));
                logger.info("offer is waiting over...");
            }
            if (remainingCapacity() == 0) {
                return false;
            } else {
                enqueue(e);
                return true;
            }
        }
    }

    @Override
    public E take() throws InterruptedException {
        synchronized (mutex) {
            while (remainingCapacity() == capacity) {
                logger.info("take is waiting....");
                mutex.wait();
                logger.info("take is waiting over...");
            }
            return dequeue();
        }
    }

    @Override
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        synchronized (mutex) {
            if (remainingCapacity() == capacity) {
                logger.info("poll is waiting....");
                mutex.wait(TimeUnit.MILLISECONDS.toMillis(timeout));
                logger.info("poll is waiting over...");
            }
            if (remainingCapacity() == capacity) {
                return null;
            } else {
                return dequeue();
            }
        }
    }

    @Override
    public int remainingCapacity() {
        return capacity - size;
    }

    @Override
    public E peek() {
        return head == null ? null : head.data;
    }

    private E dequeue() {
        E e = head.data;
        head = head.next;
        size--;
        mutex.notify();
        return e;
    }

    private void enqueue(E e) {
        head = new Node<>(e, head);
        size++;
        mutex.notify();
    }

    private static class Node<E> {
        final E data;
        final Node<E> next;

        private Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }
}
