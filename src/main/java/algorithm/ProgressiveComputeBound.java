package main.java.algorithm;

import main.java.entity.Billboard;
import main.java.entity.BillboardSet;
import main.java.parameter.Setting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;

public class ProgressiveComputeBound {

    private double B;
    private double upperInf;
    private double lowerInf;
    private double epsilon;

    private ArrayList<Billboard> solutionSet;           // S^a
    private ArrayList<Billboard> candidateSet;          // \bar{S}
    private ArrayList<Billboard> CompleteSet;          // \hat{S}


    public ProgressiveComputeBound(ArrayList<Billboard> solutionSet, ArrayList<Billboard> candidateSet) {
        this.solutionSet = solutionSet;
        this.candidateSet = candidateSet;
        this.CompleteSet = new ArrayList<>();
        this.B = Setting.getBudget();
        this.epsilon = Setting.getEpsilon();
    }

    public void run() {
        double r; // the rest of budget
        double h; // threshold
        double margInf = 0; // the marginal influence of Delta(S^*|S^a)
        double delta; //  maximal unit marginal influence
        if (Setting.test) testPrint1();

        BillboardSet billboardSet = new BillboardSet();
        for (Billboard billboard : solutionSet) {
            billboardSet.addBillboard(billboard);
        }
        if (billboardSet.getCost() >= B)
            return;
        billboardSet.calculateInf();

        order(billboardSet, candidateSet);
        r = B - billboardSet.getCost();
        h = candidateSet.get(0).unitMargInf;

        while (billboardSet.getCost() < B) {

            for (int i = 0; i < candidateSet.size(); i++) {
                Billboard billboard = candidateSet.get(i);
                if (billboardSet.getCost() + billboard.charge <= B) { // if budget is enough
                    delta = billboardSet.getMargInf(billboard);
                    if (delta / billboard.charge > h) {
                        billboardSet.addBillboard(billboard);
                        margInf += delta;
                        candidateSet.remove(i);
                        i--;
                    } else
                        break;
                } else {
                    candidateSet.remove(i);
                    i--;
                }
            }
            h /= (1 + epsilon);
            if (h <= (margInf / r) * (Math.exp(-1) * (1 - Math.exp(-1)))) {
                if (Setting.test)
                    System.out.println("early terminate");
                break;
            }
            if (candidateSet.size() == 0)
                break;
        }

        //billboardSet.calculateInf();
        lowerInf = billboardSet.getInf();
        upperInf = billboardSet.getUpperInf();
        CompleteSet.addAll(billboardSet.billboards);
        billboardSet.resetInf();

        if (Setting.test) {
            testPrint2();
            System.out.println("-----------------------------------------------------");
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

    private void testPrint1() {
        System.out.println("ComputeBound");
        System.out.println("Sa");
        for (Billboard billboard : solutionSet)
            System.out.println("    Billboard-" + billboard.panelID);
        System.out.println("S_");
        for (Billboard billboard : candidateSet)
            System.out.println("    Billboard-" + billboard.panelID);
    }

    private void testPrint2() {
        System.out.println("S^");
        for (Billboard billboard : CompleteSet)
            System.out.println("    Billboard-" + billboard.panelID);
    }

    private void order(BillboardSet billboardSet, ArrayList<Billboard> candidateSet) {
        if (candidateSet.size() <= 1)
            return;

        for (int i = 0; i < candidateSet.size(); i++) {
            candidateSet.get(i).unitMargInf = billboardSet.getMargInf(candidateSet.get(i)) / candidateSet.get(i).charge;
        }

        Billboard billboard;
        int j;
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
}


