package org.example;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {


        long startTime = System.nanoTime();

        List<MSLS> solutions = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
          solutions.add(new MSLS());
        }


        long endTime   = System.nanoTime();
        long totalTime = (endTime - startTime)/1000000;
        System.out.println(totalTime);
        int best = 0;
        double cost = 999999999;
        for (int i = 0; i < solutions.size(); i++) {
            if (solutions.get(i).bestCyclesLength < cost) {
                best = i;
                cost = solutions.get(i).bestCyclesLength;
            }
        }

        List<Double> critterValues=new ArrayList<>();
        for (MSLS solution: solutions) {
            critterValues.add(solution.getSolution());
        }
        System.out.println(critterValues.stream().max(Double::compareTo));
        System.out.println(critterValues.stream().min(Double::compareTo));
        if(critterValues.stream().reduce(Double::sum).isPresent()){
            System.out.println(critterValues.stream().reduce(Double::sum).get()/Double.parseDouble(String.valueOf(critterValues.size())));
        }

        solutions.get(best).solutionToCsv("best.csv");

    }
}