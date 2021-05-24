package main.java.parameter;

public class Setting {

    public static boolean test = false;

    private static int budget;

    private static double epsilon;

    //sigmoid
    private static double alpha;
    private static double beta;

    //line
    private static double b;
    private static double k;

    //tanPoint
    private static double tanPoint;


    public static int getBudget() {
        return budget;
    }

    public static void setBudget(int budget) {
        Setting.budget = budget;
    }

    public static double getEpsilon() {
        return epsilon;
    }

    public static void setEpsilon(double epsilon) {
        Setting.epsilon = epsilon;
    }
}
