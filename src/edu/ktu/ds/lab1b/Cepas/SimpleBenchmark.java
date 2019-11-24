/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ktu.ds.lab1b.Cepas;

import edu.ktu.ds.lab1b.util.Ks;
import edu.ktu.ds.lab1b.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

/**
 *
 * @author Marty
 */

public class SimpleBenchmark {

     int[] counts = {10_000, 20_000, 30_000};
    ArrayList<Double> valuesX = new ArrayList<>();
    ArrayList<Double> valuesY = new ArrayList<>();
    ArrayList<Integer> arrayInt = new ArrayList<>();
    java.util.LinkedList<Integer> linkedInt = new java.util.LinkedList<>();
    Random random = new Random();
    int memUsed = 0;
    
    void generateValues(int count) {
        random.setSeed(2000);
        for (int i = 0; i < count; i++) {
            valuesX.add(random.nextDouble());
        }
        random.setSeed(2000);
        for (int i = 0; i < count; i++) {
            valuesY.add(random.nextDouble());
        }
        random.setSeed(2000);
        for (int i = 0; i < count; i++) {
            arrayInt.add(random.nextInt());
        }
        random.setSeed(2000);
        for (int i = 0; i < count; i++) {
            linkedInt.add(random.nextInt());
        }
    }

    void generateAndExecute(int elementCount) {
        long t0 = System.nanoTime();
        generateValues(elementCount);
        long t1 = System.nanoTime();
        System.gc();
        long t2 = System.nanoTime();
        //  Greitaveikos bandymai ir laiko matavimai
        benchmarkMathSqrt();
        long t3 = System.nanoTime();
        benchmarkMathHypot();
        long t4 = System.nanoTime();
        benchmarkArrayList();
        long t5 = System.nanoTime();
        benchmarkLinkedList();
        long t6 = System.nanoTime();
        
        Ks.ouf("%7d %7.4f %7.4f %7.4f %7.4f %7.4f %7.4f\n", elementCount,
                (t1 - t0) / 1e9, (t2 - t1) / 1e9, (t3 - t2) / 1e9,
                (t4 - t3) / 1e9, (t5 - t4) / 1e9, (t6 - t5) / 1e9);

    }

    void benchmarkMathSqrt() {
        for (int i = 0; i < valuesX.size(); i++) {
            Math.sqrt(valuesX.get(i)*valuesX.get(i) + valuesY.get(i)*valuesY.get(i));
        }
    }

    void benchmarkMathHypot() {
        for (int i = 0; i < valuesX.size(); i++) {
            Math.hypot(valuesX.get(i), valuesY.get(i));
        }
    }
    
    void benchmarkArrayList(){
        for(int i = 0; i < arrayInt.size(); i++){
            arrayInt.contains(i);
        }
    }
    
    void benchmarkLinkedList(){
        for(int i = 0; i < arrayInt.size(); i++){
            linkedInt.contains(i);
        }
    }

    void execute() {
        long memTotal = Runtime.getRuntime().totalMemory();
        Ks.oun("memTotal= " + memTotal);
        // Pasižiūrime kaip generuoja automobilius (20) vienetų)
        generateValues(100);
        for (double value : valuesX) {
            Ks.oun(value);
        }
        Ks.oun("1 - Pasiruošimas tyrimui - duomenų generavimas");
        Ks.oun("2 - Pasiruošimas tyrimui - šiukšlių surinkimas");
        Ks.oun("3 - Math.sqrt(x*x + y*y)");
        Ks.oun("4 - Math.hypot(x, y)");
        Ks.oun("5 - ArrayList<Integer> metodas contains(Object o)");
        Ks.oun("6 - LinkedList<Integer> metodas contains(Object o)");

        Ks.ouf("%6d %7d %7d %7d %7d %7d %7d\n", 0, 1, 2, 3, 4, 5, 6);
        for (int n : counts) {
            generateAndExecute(n);
        }
        
        memoryUsage();
    }
    
    private void currentMemUse(){
        System.gc();
        System.gc();
        System.gc();
        long memTotal = Runtime.getRuntime().totalMemory();
        long memFree = Runtime.getRuntime().freeMemory();
        memUsed = (int) (memTotal - memFree);
    }
    
    private void memDifference(String rem){
        System.gc();
        System.gc();
        System.gc();
        long memTotal = Runtime.getRuntime().totalMemory();
        long memFree = Runtime.getRuntime().freeMemory();
        int memUsed1 = (int) (memTotal - memFree);
        System.out.println(String.format(rem + " uzima =\t%,6d ", (memUsed1 - memUsed)));
        memUsed = memUsed1;
    }
    
    void memoryUsage(){
        currentMemUse();
        System.gc();
        System.gc();
        System.gc();
        memDifference("Tuscia:");
        memDifference("Tuscia:");
        memDifference("Tuscia:");
        byte[] b1 = new byte[1000];
        memDifference("b1");
        byte[] b2 = new byte[10000];
        memDifference("b2");
        byte[] b3 = new byte[100000];
        memDifference("b3");
        Ks.oun("");
    }

    public static void main(String... args) {
        new SimpleBenchmark().execute();
    }
}