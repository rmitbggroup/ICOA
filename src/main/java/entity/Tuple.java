package main.java.entity;

import java.util.ArrayList;

public class Tuple implements Comparable {

    private ArrayList<Billboard> solutionSet;
    private ArrayList<Billboard> candidateSet;
    private double upperInf;

    public ArrayList<Billboard> getSolutionSet() {
        return solutionSet;
    }

    public void setSolutionSet(ArrayList<Billboard> solutionSet) {
        this.solutionSet = solutionSet;
    }

    public ArrayList<Billboard> getCandidateSet() {
        return candidateSet;
    }

    public void setCandidateSet(ArrayList<Billboard> candidateSet) {
        this.candidateSet = candidateSet;
    }

    public double getUpperInf() {
        return upperInf;
    }

    public void setUpperInf(double upperInf) {
        this.upperInf = upperInf;
    }

    public Tuple(ArrayList<Billboard> solutionSet, ArrayList<Billboard> candidateSet, double upperInf) {
        this.solutionSet = solutionSet;
        this.candidateSet = candidateSet;
        this.upperInf = upperInf;
    }

    public Billboard getOneBillboard() {
        Billboard o = candidateSet.get(0);
        candidateSet.remove(0);
        return o;
    }

    public boolean splitable() {
        if (candidateSet.size() > 1)
            return true;
        else
            return false;
    }

    @Override
    public int compareTo(Object o) {
        Tuple tuple = (Tuple) o;
        if (tuple.upperInf > this.upperInf)
            return 1;
        else
            return -1;
    }
}
