package com.github.vivekkothari.queue;


/**
 * @author by vivekkothari on 14/04/17.
 */
public class QueueApp {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(5);
        Thread tp1 = new Thread(new Producer(queue));
        tp1.setName("Producer1");
        Thread tp2 = new Thread(new Producer(queue));
        tp2.setName("Producer2");
        Thread tc1 = new Thread(new Consumer(queue));
        tc1.setName("Consumer1");
        Thread tc2 = new Thread(new Consumer(queue));
        Thread sizeThread = new Thread(() -> {
            while (true) {
                System.out.println("Capacity Left: " + queue.remainingCapacity());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
            }
        }
        );
        sizeThread.setName("SizeThread");
        sizeThread.start();
        tc2.setName("Consumer2");
        tp1.start();
        tp2.start();
        tc1.start();
        tc2.start();
    }
}
