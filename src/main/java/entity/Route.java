package main.java.entity;

import main.java.parameter.Setting;
import main.java.tangent_line.TangentLine;

import java.util.ArrayList;
import java.util.List;

public class Route {

    public int routeNum;  // old version
    public boolean influenced;  // old version
    public int routeID;
    public int numOfBillboard;
    private List<Billboard> billboardIDList = new ArrayList<>();

    public Route(int routeID) {
        this.routeID = routeID;
        this.numOfBillboard = 0;
    }

    public void addBillboard() {
        this.numOfBillboard++;
    }

    public void resetInf() {
        this.numOfBillboard = 0;
    }

    public void addBillboard(Billboard billboard) {
        this.billboardIDList.add(billboard);
        numOfBillboard++;
    }

    public double getUpperInf() {
        return TangentLine.getUpperInf(numOfBillboard);
    }

    public double getInf() {
        return TangentLine.getInf(numOfBillboard);
    }

    public double getMargUpperInf() {
        return TangentLine.getUpperInf(numOfBillboard + 1) - TangentLine.getUpperInf(numOfBillboard);

    }

    public int getRouteID() {
        return routeID;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

    public List<Billboard> getBillboardIDList() {
        return billboardIDList;
    }

    public void setBillboardIDList(List<Billboard> billboardIDList) {
        this.billboardIDList = billboardIDList;
    }
}
