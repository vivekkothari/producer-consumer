package com.github.vivekkothari.pc;

/**
 * @author by vivekkothari on 01/04/17.
 */
public class ProducerConsumerApp {
    public static void main(String[] args) {
        SharedBuffer buffer = new SharedBuffer(10);
        Thread tp1 = new Thread(new Producer(buffer));
        tp1.setName("Producer1");
        Thread tp2 = new Thread(new Producer(buffer));
        tp2.setName("Producer2");
        Thread tc1 = new Thread(new Consumer(buffer));
        tc1.setName("Consumer1");
        Thread tc2 = new Thread(new Consumer(buffer));
        tc2.setName("Consumer2");
        tp1.start();
        tp2.start();
        tc1.start();
        tc2.start();
    }
}
