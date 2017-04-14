package com.github.vivekkothari.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author by vivekkothari on 01/04/17.
 */
public class Consumer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    private final BlockingQueue<Integer> queue;
    private final long delayInMilis;

    public Consumer(BlockingQueue<Integer> queue) {
        this(queue, 100);
    }

    public Consumer(BlockingQueue<Integer> queue, long delayInMilis) {
        this.queue = queue;
        this.delayInMilis = delayInMilis;
    }

    @Override
    public void run() {
        while (true) {
            try {
                logger.info("Consumed... " + queue.take());
                Thread.sleep(delayInMilis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
