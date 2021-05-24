package main.java.algorithm;

import main.java.entity.Billboard;
import main.java.entity.BillboardSet;
import main.java.entity.Tuple;
import main.java.parameter.Setting;

import java.util.ArrayList;
import java.util.Date;
import java.util.PriorityQueue;

public class BranchAndBound_CELF {

    private double lowerInf;
    private double upperInf;

    private ArrayList<Billboard> optimalSolution;       // \hat{S}
    private ArrayList<Billboard> solutionSet;           // S
    private ArrayList<Billboard> candidateSet;          // \bar{S}

    private PriorityQueue<Tuple> queue;                 // Max Heap H

    private BillboardSet finalResultSet;

    public BranchAndBound_CELF(ArrayList<Billboard> billboardDatabase) {
        this.optimalSolution = new ArrayList<>();
        this.solutionSet = new ArrayList<>();
        this.candidateSet = billboardDatabase;
        this.queue = new PriorityQueue<>();
        initialize();
        BillboardSet billboardSet = new BillboardSet();
        CELF.order(billboardSet, candidateSet);
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

        CELF celf;

        int iter = 0;
        tuple = queue.poll();
        Date date1 = new Date();
        Date date2 = new Date();
        while (lowerInf < upperInf) {
            long begin = date1.getTime();
            long end = date2.getTime();
            iter++;
            if (iter % 1 == 0) {
                System.out.println("(" + iter + ")Queue : " + queue.size() + " Time : " + (double) (end - begin) / 1000.0D);
                System.out.println("S^a size : " + tuple.getSolutionSet().size());
                System.out.println("L " + lowerInf + "     U " + upperInf);
            }
            date1 = new Date();
            if (tuple.splitable()) {
                for (int i = 0; i < tuple.getCandidateSet().size(); i++) {

                    //branch a: with ith candidate
                    candidateSet = new ArrayList<>();
                    billboardSet_a = new ArrayList<>();
                    candidateSet.addAll(tuple.getCandidateSet());
                    billboardSet_a.addAll(tuple.getSolutionSet());
                    billboardSet_a.add(candidateSet.get(i));
                    candidateSet.remove(i);

                    celf = new CELF(billboardSet_a, candidateSet);
                    celf.run();

                    checkResult(celf, tuple, i, billboardSet_a);

                    //branch b: without ith candidate
                    candidateSet = new ArrayList<>();
                    billboardSet_b = new ArrayList<>();
                    candidateSet.addAll(tuple.getCandidateSet());
                    billboardSet_b.addAll(tuple.getSolutionSet());
                    candidateSet.remove(i);

                    celf = new CELF(billboardSet_b, candidateSet);
                    celf.run();

                    checkResult(celf, tuple, i, billboardSet_b);
                }
            }
            if (queue.size() == 0)
                break;
            tuple = queue.poll();
            upperInf = tuple.getUpperInf();
            date2 = new Date();
        }

        finalResultSet = new BillboardSet();
        finalResultSet.addBillboard(optimalSolution);
    }

    private void checkResult(CELF celf, Tuple tuple, int i, ArrayList<Billboard> billboardSet) {
        if (celf.getLowerInf() > lowerInf) {
            lowerInf = celf.getLowerInf();
            optimalSolution = new ArrayList<>();
            optimalSolution.addAll(celf.getCompleteSet());
        }
        if (celf.getUpperInf() > lowerInf) {
            candidateSet = new ArrayList<>();
            candidateSet.addAll(tuple.getCandidateSet());
            candidateSet.remove(i);
            Tuple newTuple = new Tuple(billboardSet, candidateSet, celf.getUpperInf());
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
