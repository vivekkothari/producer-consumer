package com.github.vivekkothari.pc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * @author by vivekkothari on 01/04/17.
 */
public class Producer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    private final SharedBuffer buffer;
    private final long delayInMilis;
    private final Random random = new Random();

    public Producer(SharedBuffer buffer) {
        this(buffer, 100);
    }

    public Producer(SharedBuffer buffer, long delayInMilis) {
        this.buffer = buffer;
        this.delayInMilis = delayInMilis;
    }

    @Override
    public void run() {
        while (true) {
            int element = random.nextInt(100);
            logger.info("Producing... " + element);
            buffer.offer(element);
            try {
                Thread.sleep(delayInMilis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
