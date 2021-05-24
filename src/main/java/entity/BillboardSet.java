package main.java.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BillboardSet {

    public Set<Billboard> billboards;
    public Set<Route> routes; // the set of routes that this set of billboards can influence
    private int cost;

    public BillboardSet() {
        billboards = new HashSet<>();
        routes = new HashSet<>();
        cost = 0;
    }

    public void addBillboard(Billboard billboard) {
        billboards.add(billboard);
        for (Route route : billboard.routes) {
            route.addBillboard();
            if (!this.routes.contains(route))
                this.routes.add(route);
        }
        this.cost += billboard.charge;
    }

    public void addBillboard(ArrayList<Billboard> billboards) {
        for (Billboard billboard : billboards)
            addBillboard(billboard);
    }

    public void calculateInf() {
        for (Billboard billboard : this.billboards) {
            for (Route route : billboard.routes) {
                route.addBillboard();
            }
        }
    }

    public double getInf() {
        double influence = 0;
        for (Route route : this.routes) {
            influence += route.getInf();
        }
        return influence;
    }

    public double getUpperInf() {
        double influence = 0;
        for (Route route : this.routes) {
            influence += route.getUpperInf();
        }
        return influence;
    }

    public double getMargInf(Billboard billboard) {
        double influence = 0;
        for (Route route : billboard.routes) {
            influence += route.getMargUpperInf();
        }
        return influence;
    }

    public void resetInf() {
        for (Route route : this.routes) {
            route.resetInf();
        }
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {

        String result = "";
        result += "billboard id : ";

        for (Billboard billboard : billboards)
            result += billboard.panelID + ", ";

        result += "\nnumber of billboards: " + billboards.size();
        result += "\nnumber of routes get influenced: " + routes.size();

        result += "\n-----------------------------------------------------";

        return result;
    }
}
