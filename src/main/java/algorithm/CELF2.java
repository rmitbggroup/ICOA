package main.java.algorithm;

import main.java.entity.Billboard;
import main.java.entity.BillboardSet;
import main.java.parameter.Setting;

import java.util.ArrayList;

public class CELF2 {

    private double B;
    private double upperInf;
    private double lowerInf;

    private ArrayList<Billboard> solutionSet;           // S^a
    private ArrayList<Billboard> candidateSet;          // \bar{S}
    private ArrayList<Billboard> CompleteSet;          // \hat{S}
    private ArrayList<Billboard> selectedSet;           // S*

    public CELF2(ArrayList<Billboard> solutionSet, ArrayList<Billboard> candidateSet) {
        this.solutionSet = solutionSet;
        this.candidateSet = candidateSet;
        this.CompleteSet = new ArrayList<>();
        this.selectedSet = new ArrayList<>();
        this.B = Setting.getBudget();
    }

    public void run() {
        int iteration = -1;
        BillboardSet billboardSet = new BillboardSet();
        for (Billboard billboard : solutionSet) {
            billboardSet.addBillboard(billboard);
        }
        if (billboardSet.getCost() > B)
            return;

        order(billboardSet, candidateSet);
        while (billboardSet.getCost() < B && candidateSet.size() > 0) {
            iteration++;
            while (candidateSet.size() > 0) {
                Billboard billboard = candidateSet.get(0);
                if (billboard.charge > B - billboardSet.getCost()) {
                    candidateSet.remove(0);
                    continue;
                }
                if (billboard.CELFIteration != iteration) { // hasn't been updated
                    billboard.CELFIteration = iteration;
                    billboard.unitMargInf = billboardSet.getMargInf(billboard) / billboard.charge;

                    if (candidateSet.size() == 1) { // it's the only one
                        billboardSet.addBillboard(billboard);
                        candidateSet.remove(0);
                        break;
                    } else {
                        //more than one candidate
                        if (billboard.unitMargInf > candidateSet.get(1).unitMargInf) {
                            // if it's better than the second one, then no need to update the following candidates
                            billboardSet.addBillboard(billboard);
                            candidateSet.remove(0);
                            break;
                        } else {
                            // worse than the second, then move it to the corresponding location
                            for (int i = 1; i < candidateSet.size(); i++) {
                                if (billboard.unitMargInf > candidateSet.get(i).unitMargInf) {
                                    candidateSet.add(i, billboard);
                                    candidateSet.remove(0);
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    // it has been updated, and the above candidates have been moved.
                    billboardSet.addBillboard(billboard);
                    candidateSet.remove(0);
                    break;
                }
            }
        }

        lowerInf = billboardSet.getInf();
        upperInf = billboardSet.getUpperInf();
        CompleteSet.addAll(billboardSet.billboards);
        billboardSet.resetInf();
    }

    private void order(BillboardSet billboardSet, ArrayList<Billboard> candidateSet) {
        if (candidateSet.size() <= 1)
            return;

        for (int i = 0; i < candidateSet.size(); i++) {
            candidateSet.get(i).unitMargInf = billboardSet.getMargInf(candidateSet.get(i)) / candidateSet.get(i).charge;
        }

        Billboard billboard;
        int j, change;
        for (int i = 1; i < candidateSet.size(); i++) {
            billboard = candidateSet.get(i);
            j = i;
            while (j > 0 && billboard.unitMargInf > candidateSet.get(j - 1).unitMargInf)
                j--;
            if (j < i) {
                candidateSet.remove(i);
                candidateSet.add(j, billboard);
            }
        }
    }

    public double getUpperInf() {
        return upperInf;
    }

    public double getLowerInf() {
        return lowerInf;
    }

    public ArrayList<Billboard> getCompleteSet() {
        return CompleteSet;
    }
}
