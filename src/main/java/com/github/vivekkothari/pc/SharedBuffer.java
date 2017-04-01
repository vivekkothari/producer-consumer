package com.github.vivekkothari.pc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SharedBuffer {

    private static final Logger logger = LoggerFactory.getLogger(SharedBuffer.class);

    private final int[] buffer;
    private volatile int currentSize;

    public SharedBuffer(int size) {
        this.buffer = new int[size];
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    public boolean isFull() {
        return currentSize == buffer.length;
    }

    public synchronized void offer(int element) {
        try {
            while (isFull()) {
                logger.info("Producer is waiting....");
                this.wait();
                logger.info("Producer wait over....");
            }
            buffer[currentSize++] = element;
            this.notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized int take() {
        try {
            while (isEmpty()) {
                logger.info("Consumer is waiting....");
                this.wait();
                logger.info("Consumer wait over....");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int i = buffer[--currentSize];
        this.notifyAll();
        return i;
    }

}