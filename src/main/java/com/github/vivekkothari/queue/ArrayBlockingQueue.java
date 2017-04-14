package com.github.vivekkothari.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author by vivekkothari on 14/04/17.
 */
@SuppressWarnings("unchecked")
public class ArrayBlockingQueue<E> implements BlockingQueue<E> {

    private static final Logger logger = LoggerFactory.getLogger(ArrayBlockingQueue.class);
    private final Object[] buffer;
    private volatile int putIndex, takeIndex, elementCount;

    public ArrayBlockingQueue(int capacity) {
        this.buffer = new Object[capacity];
    }

    @Override
    public boolean offer(E e) {
        synchronized (buffer) {
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
        synchronized (buffer) {
            while (remainingCapacity() == 0) {
                logger.info("put is waiting....");
                buffer.wait();
                logger.info("put is waiting over...");
            }
            enqueue(e);
        }
    }

    @Override
    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        synchronized (buffer) {
            if (remainingCapacity() == 0) {
                logger.info("offer is waiting....");
                buffer.wait(TimeUnit.MILLISECONDS.toMillis(timeout));
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
        synchronized (buffer) {
            while (remainingCapacity() == buffer.length) {
                logger.info("take is waiting....");
                buffer.wait();
                logger.info("take is waiting over...");
            }
            return dequeue();
        }
    }

    @Override
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        synchronized (buffer) {
            if (remainingCapacity() == buffer.length) {
                logger.info("poll is waiting....");
                buffer.wait(TimeUnit.MILLISECONDS.toMillis(timeout));
                logger.info("poll is waiting over...");
            }
            if (remainingCapacity() == buffer.length) {
                return null;
            } else {
                return dequeue();
            }
        }
    }

    private E dequeue() {
        E e = (E) buffer[takeIndex];
        elementCount--;
        takeIndex = (takeIndex + 1) % buffer.length;
        buffer.notify();
        return e;
    }

    private void enqueue(E e) {
        buffer[putIndex] = e;
        elementCount++;
        putIndex = (putIndex + 1) % buffer.length;
        buffer.notify();
    }

    @Override
    public int remainingCapacity() {
        return buffer.length - elementCount;
    }

    @Override
    public E peek() {
        synchronized (buffer) {
            if (remainingCapacity() == buffer.length) {
                return null;
            } else {
                return (E) buffer[takeIndex];
            }
        }
    }
}
