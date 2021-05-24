package main.java.algorithm;

import main.java.entity.Billboard;
import main.java.entity.BillboardSet;
import main.java.parameter.Setting;

import java.util.ArrayList;
import java.util.Set;

public class ComputeBound {

    private double B;
    private double upperInf;
    private double lowerInf;

    private ArrayList<Billboard> solutionSet;           // S^a
    private ArrayList<Billboard> candidateSet;          // \bar{S}
    private ArrayList<Billboard> CompleteSet;          // \hat{S}


    public ComputeBound(ArrayList<Billboard> solutionSet, ArrayList<Billboard> candidateSet) {
        this.solutionSet = solutionSet;
        this.candidateSet = candidateSet;
        this.CompleteSet = new ArrayList<>();
        this.B = Setting.getBudget();
    }

    public void run() {
        if (Setting.test) testPrint1();

        BillboardSet billboardSet = new BillboardSet();
        for (Billboard billboard : solutionSet) {
            billboardSet.addBillboard(billboard);
        }
        if (billboardSet.getCost() >= B)
            return;
        //billboardSet.calculateInf();

        while (billboardSet.getCost() < B) {
            Billboard candidate = null;
            double maxUnitMargInf = 0; //  maximal unit marginal influence
            for (int i = 0; i < candidateSet.size(); i++) {
                Billboard billboard = candidateSet.get(i);
                if (billboardSet.getCost() + billboard.charge <= B) { // if budget is enough
                    double canUnitMargInf = billboardSet.getMargInf(billboard) / billboard.charge;
                    if (canUnitMargInf > maxUnitMargInf) {
                        maxUnitMargInf = canUnitMargInf;
                        candidate = billboard;
                    }
                } else {
                    candidateSet.remove(i);
                    i--;
                }
            }
            if (candidate == null)
                break;
            else {
                billboardSet.addBillboard(candidate);
                candidateSet.remove(candidate);
            }
        }

        //billboardSet.calculateInf();
        lowerInf = billboardSet.getInf();
        upperInf = billboardSet.getUpperInf();
        //System.out.println(billboardSet);
        //System.out.println("lowerInf ="+lowerInf+"         upperInf ="+upperInf);
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
}
