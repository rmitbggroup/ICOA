package main.java.io;

import main.java.entity.Billboard;
import main.java.entity.Route;
import main.java.finalResult.FinalResultReader;
import main.java.finalResult.FinalResultReaderBasedHash;
import main.java.parameter.Setting;


import java.util.ArrayList;
import java.util.List;

public class Initialization {
    private ArrayList<Billboard> billboardList;

    public Initialization() {
        billboardList = new ArrayList<>();
        loadSetting();
        loadBillboard();
        //testData();
    }

    public ArrayList<Billboard> getBillboardList() {
        return billboardList;
    }

    private void loadSetting() {
        Setting.setBudget(500);
        Setting.setEpsilon(0.5);
    }

    private void loadBillboard() {
        FinalResultReader finalResultReader = new FinalResultReader();
        System.out.println("FinalResultReader init success");
        List<Billboard> billboards = finalResultReader.getBillboards();

//        for(int i=0;i<billboards.size();i++){
//            System.out.println(billboards.get(i));
//        }

        int routeNum = 0;

        for(Billboard billboard : billboards){
            routeNum += billboard.influence;
        }

        this.billboardList.addAll(billboards);
        System.out.println("total number of billboards: " + billboards.size());
        System.out.println("total number of route num: " + routeNum);
    }

    private void testData() {

        Billboard o1 = new Billboard("1", 30);
        Billboard o2 = new Billboard("2", 20);
        Billboard o3 = new Billboard("3", 10);
        Billboard o4 = new Billboard("4", 50);

        Route t1 = new Route(1);
        Route t2 = new Route(2);
        Route t3 = new Route(3);
        Route t4 = new Route(4);

        o1.routes.add(t1);
        o1.routes.add(t2);
        o1.routes.add(t3);

        o2.routes.add(t2);
        o2.routes.add(t3);

        o3.routes.add(t4);

        o4.routes.add(t1);
        o4.routes.add(t4);

        billboardList.add(o1);
        billboardList.add(o2);
        billboardList.add(o3);
        billboardList.add(o4);

    }
}
