package com.github.vivekkothari.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * @author by vivekkothari on 01/04/17.
 */
public class Producer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    private final BlockingQueue<Integer> queue;
    private final long delayInMilis;
    private final Random random = new Random();

    public Producer(BlockingQueue<Integer> queue) {
        this(queue, 100);
    }

    public Producer(BlockingQueue<Integer> queue, long delayInMilis) {
        this.queue = queue;
        this.delayInMilis = delayInMilis;
    }

    @Override
    public void run() {
        while (true) {
            int element = random.nextInt(100);
            try {
                queue.put(element);
                logger.info("Produced... " + element);
                Thread.sleep(delayInMilis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
