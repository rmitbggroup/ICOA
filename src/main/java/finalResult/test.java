package main.java.finalResult;

import main.java.fileIO.MyFileReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class test {
    public static void main(String[] args) {

        List<Integer> DeleteRoute = new ArrayList<>();
        HashMap<Integer,Integer> Route = readCombineRoute(DeleteRoute);

        System.out.println(DeleteRoute);
        System.out.println(Route);

    }

    public static HashMap<Integer,Integer> readCombineRoute(List<Integer> DeleteRoute) {

        HashMap<Integer,Integer> map = new HashMap<>();

        MyFileReader combineRouteReader = new MyFileReader("./combineRoute.txt");

        String line = combineRouteReader.getNextLine();

        while(line != null){
            String[] elements = line.split(":");

            if(elements.length == 2){
                int count = 0;
                String[] RouteIds = elements[1].split(" ");

                for(int i=0;i<RouteIds.length;i++){
                    if(RouteIds[i] != null){
                        count++;
                        DeleteRoute.add(Integer.parseInt(RouteIds[i]));
                    }
                }

                map.put(Integer.parseInt(elements[0]),count);
                System.out.println(Integer.parseInt(elements[0])+"  "+count);

            }

            line = combineRouteReader.getNextLine();
        }

        return map;
    }
}
