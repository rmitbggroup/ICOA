package main.java;

import main.java.algorithm.BranchAndBound;
import main.java.algorithm.BranchAndBound_CELF;
import main.java.algorithm.BranchAndBound_progressive;
import main.java.entity.Billboard;
import main.java.entity.BillboardSet;
import main.java.entity.Route;
import main.java.io.Initialization;
import main.java.tangent_line.TangentLine;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class test {

    private static ArrayList<Billboard> billboardList;

    public static void main(String[] arg) {
        initial(); //initialize all setting
        testBranchAndBound_progressive();
//        testBranchAndBound_CELF();
//        initial(); //initialize all setting
//        testBranchAndBound();
        //test2();
    }

    public static void initial() {
        generateTangentLine();
        Initialization initialization = new Initialization();
        billboardList = initialization.getBillboardList();
    }

    public static void testBranchAndBound() {
        BranchAndBound branchAndBound = new BranchAndBound(billboardList);

        System.out.println("********************Start Branch-And-Bound Basic ***************");
        Date date1 = new Date();
        branchAndBound.getSolution();
        Date date2 = new Date();
        long begin = date1.getTime();
        long end = date2.getTime();

        double cost = 0;
        System.out.println("-------------------------Result-----------------------------");

        System.out.println("Solution set:");
        for (Billboard billboard : branchAndBound.getSolutionSet()) {
            System.out.println("billboard  " + billboard.panelID + " " + billboard.charge);
            cost += billboard.charge;
            for (Route route : billboard.routes) {
                if (route.numOfBillboard > 1)
                    System.out.println(route.routeID + " " + route.numOfBillboard);
            }
        }
        System.out.println("Total cost:" + cost);

        System.out.println("runtime: " + (double) (end - begin) / 1000.0D);

        System.out.println("real influence " + branchAndBound.getInf());
        System.out.println("upper influence " + branchAndBound.getUpperInf());
        System.out.println("getSolutionSet.size " + branchAndBound.getSolutionSet().size());

        System.out.println("------------------------------------------------------");
    }

    public static void testBranchAndBound_progressive() {
        BranchAndBound_progressive branchAndBound = new BranchAndBound_progressive(billboardList);

        System.out.println("********************Start Branch-And-Bound Progressive ***************");
        Date date1 = new Date();
        branchAndBound.getSolution();
        Date date2 = new Date();
        long begin = date1.getTime();
        long end = date2.getTime();
        System.out.println("runtime: " + (double) (end - begin) / 1000.0D);

        double cost = 0;
        System.out.println("-------------------------Result-----------------------------");

        System.out.println("Solution set:");
        for (Billboard billboard : branchAndBound.getSolutionSet()) {
            System.out.println("billboard  " + billboard.panelID + " " + billboard.charge);
            cost += billboard.charge;
            for (Route route : billboard.routes) {
                if (route.numOfBillboard > 1)
                    System.out.println(route.routeID + " " + route.numOfBillboard);
            }
        }
        System.out.println("Total cost:" + cost);

        System.out.println("runtime: " + (double) (end - begin) / 1000.0D);

        System.out.println("real influence " + branchAndBound.getInf());
        System.out.println("upper influence " + branchAndBound.getUpperInf());
        System.out.println("getSolutionSet.size " + branchAndBound.getSolutionSet().size());

        System.out.println("------------------------------------------------------");
    }

    public static void testBranchAndBound_CELF() {
        BranchAndBound_CELF branchAndBound_celf = new BranchAndBound_CELF(billboardList);
        System.out.println("********************Start Branch-And-Bound CELF ***************");
        Date date1 = new Date();
        branchAndBound_celf.getSolution();
        Date date2 = new Date();
        long begin = date1.getTime();
        long end = date2.getTime();
        System.out.println("runtime: " + (double) (end - begin) / 1000.0D);

        double cost = 0;
        System.out.println("-------------------------Result-----------------------------");

        System.out.println("Solution set:");
        for (Billboard billboard : branchAndBound_celf.getSolutionSet()) {
            System.out.println("billboard  " + billboard.panelID + " " + billboard.charge);
            cost += billboard.charge;
            for (Route route : billboard.routes) {
                if (route.numOfBillboard > 1)
                    System.out.println(route.routeID + " " + route.numOfBillboard);
            }
        }
        System.out.println("Total cost:" + cost);

        System.out.println("runtime: " + (double) (end - begin) / 1000.0D);

        System.out.println("real influence " + branchAndBound_celf.getInf());
        System.out.println("upper influence " + branchAndBound_celf.getUpperInf());
        System.out.println("getSolutionSet.size " + branchAndBound_celf.getSolutionSet().size());

        System.out.println("------------------------------------------------------");
    }

    public static void testBillboardSet() {
        Initialization initialization = new Initialization();
        List<Billboard> billboardList = initialization.getBillboardList();
        BillboardSet billboardSet = new BillboardSet();
        billboardSet.addBillboard(billboardList.get(0));
        billboardSet.addBillboard(billboardList.get(1));
        billboardSet.addBillboard(billboardList.get(2));
        billboardSet.addBillboard(billboardList.get(3));
    }

    public static void generateTangentLine() {
        TangentLine tangentLine = new TangentLine(3, 1);
        tangentLine.generateLine(3);
    }


    public static void testTangentLine() {
        TangentLine tangentLine = new TangentLine(3, 1);
        tangentLine.generateLine();
        for (int i = 1; i < 10; i++) {
            System.out.println(tangentLine.getInf(i));
            System.out.println(tangentLine.getUpperInf(i));
            System.out.println("--------------");
        }
    }

    public static void test2() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            list.add(i);
        for (int n = 0; n < list.size(); n++) {
            System.out.println("check " + list.get(n));
            if (list.get(n) == 2) {
                list.remove(n);
                n--;
            }
        }
    }

}
