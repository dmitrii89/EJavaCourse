package lesson151008;

import java.util.Random;

/**
 * Created by dmitr on 02.10.2017.
 */
public class HomeWork {
    public static void main(String[] args) {
        long startgen = System.nanoTime();
        double[][] matrix = generate();
        long stopgen = System.nanoTime();
        System.out.println("Generation = " + (stopgen - startgen));

        long start = System.nanoTime();
        process(matrix);
        long stop = System.nanoTime();

        System.out.println("Process = " + (stop - start));
    }

    private static void process(double[][] matrix) {
        Thread[] threads = new Thread[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            double[] row = matrix[i];
            threads[i] = new Thread() {
                @Override
                public void run() {
                    processRow(row);
                }
            };
            threads[i].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void processRow(double[] ds) {
        for (int i = 0; i < ds.length; i++) {
            Math.pow(ds[i], ds[i]);
        }
    }

    private static double[][] generate() {
        Random random = new Random();
        double[][] matrix = new double[10][1_000_000];
        for (int i=0; i < matrix.length; i++) {
            for (int j=0; j < matrix[i].length; j++) {
                matrix[i][j] = random.nextDouble();
            }
        }
        return matrix;
    }
}
