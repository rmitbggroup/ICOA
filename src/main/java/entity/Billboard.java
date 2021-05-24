package main.java.entity;

import java.util.HashSet;
import java.util.Set;

public class Billboard implements Comparable {

    public String panelID;  // panelID = billboardID

    public double upperInf;   // maximal influence

    public double charge;  //  cost  weeklyImpression / 3000

    public double influencePerCharge;   // initialized as routes.size() / charge

    public double unitMargInf;

    public int CELFIteration;

    public int influence; // old version

    public Set<Route> routes;  // googleRouteID (used to store routes influenced by this billboard)

    public Billboard() {
        routes = new HashSet<>();
    }

    public Billboard(String panelID, double charge) {
        this.panelID = panelID;
        this.charge = charge;
        routes = new HashSet<>();
    }

    public void initialize() {
        this.upperInf = getUpperInf();
        this.influencePerCharge = upperInf / charge;
    }

    public double getInf() {
        double influence = 0.0;
        for (Route route : routes) {
            influence += route.getInf();
        }
        return influence;
    }

    public double getUpperInf() {
        double influence = 0.0;
        for (Route route : routes) {
            influence += route.getUpperInf();
        }
        return influence;
    }

    @Override
    public String toString() {

        String result = "";

        result += "panelID : " + panelID + "\n";
        result += "influence : " + influence + "\n";
        result += "charge : " + charge + "\n";
        result += "influence per charge : " + influencePerCharge + "\n";
        result += "routes : ";

        for (Route route : routes)
            result += (route.routeID + ", ");

        return result;
    }

    @Override
    public int compareTo(Object o) {

        Billboard billboard = (Billboard) o;

        double difference =  billboard.influence - influence;

        if (difference > 0)
            return 1;
        else if (difference < 0)
            return -1;
        else
            return 0;
    }

}
