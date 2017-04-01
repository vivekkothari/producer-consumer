package com.github.vivekkothari.pc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author by vivekkothari on 01/04/17.
 */
public class Consumer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    private final SharedBuffer buffer;
    private final long delayInMilis;

    public Consumer(SharedBuffer buffer) {
        this(buffer, 105);
    }

    public Consumer(SharedBuffer buffer, long delayInMilis) {
        this.buffer = buffer;
        this.delayInMilis = delayInMilis;
    }

    @Override
    public void run() {
        while (true) {
            logger.info("Consuming..." + buffer.take());
            try {
                Thread.sleep(delayInMilis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
