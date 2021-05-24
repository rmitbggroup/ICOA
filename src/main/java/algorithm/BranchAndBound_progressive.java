package main.java.algorithm;

import main.java.entity.Billboard;
import main.java.entity.BillboardSet;
import main.java.entity.Tuple;
import main.java.parameter.Setting;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class BranchAndBound_progressive {

    private double lowerInf;
    private double upperInf;

    private ArrayList<Billboard> optimalSolution;       // \hat{S}
    private ArrayList<Billboard> solutionSet;           // S
    private ArrayList<Billboard> candidateSet;          // \bar{S}

    private PriorityQueue<Tuple> queue;                 // Max Heap H

    private BillboardSet finalResultSet;

    public BranchAndBound_progressive(ArrayList<Billboard> billboardDatabase) {
        this.optimalSolution = new ArrayList<>();
        this.solutionSet = new ArrayList<>();
        this.candidateSet = billboardDatabase;
        this.queue = new PriorityQueue<>();
        initialize();
    }

    private void initialize() {
        this.lowerInf = 0;
        this.upperInf = 999999999;
        Tuple tuple = new Tuple(this.solutionSet, this.candidateSet, 999999999);
        queue.add(tuple);
    }

    public void getSolution() {
        Tuple tuple;
        ArrayList<Billboard> billboardSet_a;
        ArrayList<Billboard> billboardSet_b;
        ArrayList<Billboard> candidateSet;

        ProgressiveComputeBound computeBound;

        int iter = 0;
        tuple = queue.poll();
        System.out.println(tuple.getSolutionSet().size());
        while (lowerInf < upperInf) {
            iter++;
            if (iter % 1 == 0) {
                System.out.println("(" + iter + ")Queue : " + queue.size());
                System.out.println("L " + lowerInf + "     U " + upperInf);
            }
            if (tuple.splitable()) {
                for (int i = 0; i < tuple.getCandidateSet().size(); i++) {

                    //branch a: with ith candidate
                    candidateSet = new ArrayList<>();
                    billboardSet_a = new ArrayList<>();
                    candidateSet.addAll(tuple.getCandidateSet());
                    billboardSet_a.addAll(tuple.getSolutionSet());
                    billboardSet_a.add(candidateSet.get(i));
                    candidateSet.remove(i);


                    computeBound = new ProgressiveComputeBound(billboardSet_a, candidateSet);
                    computeBound.run();

                    checkResult(computeBound, tuple, i, billboardSet_a);

                    //branch b: without ith candidate
                    candidateSet = new ArrayList<>();
                    billboardSet_b = new ArrayList<>();
                    candidateSet.addAll(tuple.getCandidateSet());
                    billboardSet_b.addAll(tuple.getSolutionSet());
                    candidateSet.remove(i);

                    computeBound = new ProgressiveComputeBound(billboardSet_b, candidateSet);
                    computeBound.run();

                    checkResult(computeBound, tuple, i, billboardSet_b);
                }
            }
            if (queue.size() == 0)
                break;
            tuple = queue.peek();
            //tuple = queue.poll();
            upperInf = tuple.getUpperInf();
        }

        finalResultSet = new BillboardSet();
        finalResultSet.addBillboard(optimalSolution);
    }

    private void checkResult(ProgressiveComputeBound computeBound, Tuple tuple, int i, ArrayList<Billboard> billboardSet) {
        if (computeBound.getLowerInf() > lowerInf) {
            lowerInf = computeBound.getLowerInf();
            optimalSolution = new ArrayList<>();
            optimalSolution.addAll(computeBound.getCompleteSet());
        }
        if (computeBound.getUpperInf() > lowerInf) {
            candidateSet = new ArrayList<>();
            candidateSet.addAll(tuple.getCandidateSet());
            candidateSet.remove(i);
            Tuple newTuple = new Tuple(billboardSet, candidateSet, computeBound.getUpperInf());
            queue.add(newTuple);
        }

    }

    public double getInf() {
        finalResultSet.resetInf();
        finalResultSet.calculateInf();
        return finalResultSet.getInf();
    }

    public double getUpperInf() {
        finalResultSet.resetInf();
        finalResultSet.calculateInf();
        return finalResultSet.getUpperInf();
    }

    public ArrayList<Billboard> getSolutionSet() {
        return this.optimalSolution;
    }


}
