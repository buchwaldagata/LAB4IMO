package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

class ILS1 {

    List<List<Integer>> cycles_X;
    List<List<Integer>> cycles_Y;
    List<List<Integer>> distanceMatrix;
    Instance kro200;
    double bestCyclesLength = 0;
    public ILS1(Instance kro200 ) {
        this.kro200 = kro200;
        distanceMatrix = kro200.getDistanceMatrix();
        solve();
    }

    private List<List<Integer>> solve() {
        cycles_X = new RandomStart().getCycles();
        cycles_X = new HillClimbing(kro200, cycles_X).cycles;
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < 2000) {
            cycles_Y = new ArrayList<>(cycles_X.size());
            for (List<Integer> list : cycles_X) {
                List<Integer> tmp = new ArrayList<>();
                for (Integer element: list){
                    tmp.add(element);
                }
                cycles_Y.add(tmp);
            }
            cycles_Y = new HillClimbing(kro200, cycles_Y).cycles;

            if (calcCycleLength(cycles_Y.get(0)) + calcCycleLength(cycles_Y.get(1)) < calcCycleLength(cycles_X.get(0)) + calcCycleLength(cycles_X.get(1))){
                cycles_X = cycles_Y;
            }


        }
        bestCyclesLength = calcCycleLength(cycles_X.get(0)) + calcCycleLength(cycles_X.get(1));
        return cycles_X;

    }
    private double calcCycleLength(List<Integer> solution){
        double length = 0;
        for(int i= 0; i<solution.size()-1; i++){
            length += distanceMatrix.get(solution.get(i)).get(solution.get(i+1));
        }
        length += distanceMatrix.get(solution.get(solution.size() - 1)).get(solution.get(0));
        return length;
    }

    public void solutionToCsv(String path, Instance instance) throws IOException {
        FileWriter fileWriter = new FileWriter(path);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print("cycle,x,y\n");
        for (Integer a : cycles_X.get(0)) {
            printWriter.printf("%s,%d,%d\n","a", instance.coordinates.get(a).getKey(), instance.coordinates.get(a).getValue());
        }
        for (Integer a : cycles_X.get(1)) {
            printWriter.printf("%s,%d,%d\n","b", instance.coordinates.get(a).getKey(), instance.coordinates.get(a).getValue());
        }
        printWriter.close();
    }

}
