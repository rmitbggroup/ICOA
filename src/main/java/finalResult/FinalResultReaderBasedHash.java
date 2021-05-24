package main.java.finalResult;

import main.java.fileIO.FilePath;
import main.java.fileIO.MyFileReader;
import main.java.entity.Billboard;
import main.java.entity.Route;
import main.java.parameter.Setting;

import java.util.*;


public class FinalResultReaderBasedHash {

    public static final int dupTimes = 5;

    private static final int lengths[] = {37000, 74000, 108000, 140000, 177000};

    private static final int length = lengths[0];

    public static List<String> panelIDs = new ArrayList<>();
    public static List<Integer> weeklyImpressions = new ArrayList<>();
    public static Set<Integer> routeIDSet = new TreeSet<>();
    public static Map routeIDMap = new HashMap<Integer, Integer>();
    public static List<List<Integer>> routeIDsOfBillboards = new ArrayList<>();   // { {routeID1, routeID2}, {routeID1, routeID2} }
    public static List<List<Integer>> routeIndexesOfBillboards = new ArrayList<>();   // { {routeIndex1, routeIndex2}, {routeIndex1, routeIndex2} }

    private List<Route> routes = null;  // use routeIndex1 to retrieve route object
    private List<Billboard> billboards = null;


    public FinalResultReaderBasedHash() {

        init();
        routes = new ArrayList<>();
        billboards = new ArrayList<>();
    }

    // initialize panelIDs, weeklyImpressions, routeIDSet, routeIDsOfBillboards, routeIndexesOfBillboards

    private void init() {

        MyFileReader finalResultReader = null;

        finalResultReader = new MyFileReader(FilePath.billboardCombineResultPath);

        String line = "";

        while (true) {

            line = finalResultReader.getNextLine();
            if (line == null)
                break;

            String[] elements = line.split(" ");
            if (elements.length == 1)
                continue;    // skip those billboard which can not influence any route

            String panelID = elements[0].split("~")[0]; // panelID~weeklyImpression
            int weeklyImpression = Integer.parseInt(elements[0].split("~")[1]);


            List<Integer> routeIDs = new ArrayList<>();

            for (int i = 1; i < elements.length; i++) {

                int routeID = Integer.parseInt(elements[i]);
                //if (routeID < length) {
                    routeIDs.add(routeID);
                //}
            }

            panelIDs.add(panelID);
            weeklyImpressions.add(weeklyImpression);
            routeIDsOfBillboards.add(routeIDs);

        }
        setUpRouteIDsAndIndexes();
        finalResultReader.close();
    }


    // set up routeIDSet and routeIndexesOfBillboards

    private void setUpRouteIDsAndIndexes() {


        for (int i = 0; i < routeIDsOfBillboards.size(); i++) {

            List<Integer> routeIDs = routeIDsOfBillboards.get(i);

            for (int j = 0; j < routeIDs.size(); j++) {

                int routeID = routeIDs.get(j);
                routeIDSet.add(routeID);
            }
        }

        buildHash();

        for (int i = 0; i < routeIDsOfBillboards.size(); i++) {

            List<Integer> routeIndexes = new ArrayList<>();
            List<Integer> routeIDs = routeIDsOfBillboards.get(i);
            for (int j = 0; j < routeIDs.size(); j++) {

                int routeID = routeIDs.get(j);
                int routeIndex = (int) routeIDMap.get(routeID);
                routeIndexes.add(routeIndex);
            }
            routeIndexesOfBillboards.add(routeIndexes);
        }
    }


    private void buildHash() {

        List<Integer> routeIDs = new ArrayList<>();
        routeIDs.addAll(routeIDSet);

        for (int i = 0; i < routeIDs.size(); i++) {

            int id = routeIDs.get(i);

            routeIDMap.put(id, i);
        }

    }


    public List<Billboard> getBillboards() {

        newRoutes();

        //List<Integer> DeleteRoute = new ArrayList<>();
//        HashMap<Integer, Integer> DeleteRoute = new HashMap<>();
//        HashMap<Integer, Integer> Route = readCombineRoute(DeleteRoute);

        for (int i = 0; i < panelIDs.size(); i++) {

            Billboard billboard = new Billboard();

            billboard.panelID = panelIDs.get(i);
            //billboard.charge = (weeklyImpressions.get(i) / 30000 + 1) * 15000;

            List<Integer> routeIndexes = routeIndexesOfBillboards.get(i);

            for (int j = 0; j < routeIndexes.size(); j++) {

                int routeIndex = routeIndexes.get(j);
                Route route = routes.get(routeIndex);

//                if (DeleteRoute.containsKey(route.getRouteID()))//删除路径
//                    continue;
//
//                if (Route.containsKey(route.getRouteID())) {
//                    route.routeNum = Route.get(route.getRouteID()) + 1;
//                } else {
//                    route.routeNum = 1;
//                }
                billboard.routes.add(route);
            }
            billboard.influence = 0;

//            for (Route route : billboard.routes) {
//                billboard.influence += route.routeNum;
//            }
            //billboard.charge = Math.ceil(Math.sqrt(billboard.influence));
            billboard.influence = billboard.routes.size();
            int num =(int)(Math.random() * 10) + 1;
            billboard.charge = billboard.influence * num;
            billboard.influencePerCharge = billboard.influence / billboard.charge;

            if (billboard.charge <= Setting.getBudget() && billboard.charge > 0)
                billboards.add(billboard);
        }
        return billboards;
    }


    private void newRoutes() {

        List<Integer> routeIDs = new ArrayList<>();
        routeIDs.addAll(routeIDSet);

        for (int routeID : routeIDs) {

            Route route = new Route(routeID);

            route.influenced = false;

            routes.add(route);
        }
    }

    public HashMap<Integer, Integer> readCombineRoute(HashMap<Integer, Integer> DeleteRoute) {

        HashMap<Integer, Integer> map = new HashMap<>();

        MyFileReader combineRouteReader = new MyFileReader("./combineRoute.txt");

        String line = combineRouteReader.getNextLine();

        while (line != null) {
            String[] elements = line.split(":");

            if (elements.length == 2) {
                int count = 0;
                String[] RouteIds = elements[1].split(" ");

                for (int i = 0; i < RouteIds.length; i++) {
                    if (RouteIds[i] != null && Integer.parseInt(RouteIds[i]) < length) {
                        count++;
                        DeleteRoute.put(Integer.parseInt(RouteIds[i]), i);
                        //DeleteRoute.put(Integer.parseInt(RouteIds[i]),Integer.parseInt(RouteIds[i]));
                    }
                }

                map.put(Integer.parseInt(elements[0]), count);

            }

            line = combineRouteReader.getNextLine();
        }

        return map;
    }
}
