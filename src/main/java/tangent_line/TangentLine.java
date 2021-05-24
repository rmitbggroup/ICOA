package main.java.tangent_line;

public class TangentLine {
    private static double alpha, beta;    // parameters for logistic function
    private static double k, b;            // parameters for line function
    private static double tanPoint;       // x coordinate of tangancy point
    private final double EPSILON = 0.000000000001; // Maximal error

    public TangentLine(double alpha, double beta) {
        this.alpha = alpha;
        this.beta = beta;
        this.k = 0.2;
        this.b = -0.08;
    }

    public double getK() {
        return k;
    }

    public double getB() {
        return b;
    }

    public static double getUpperInf(double x) {
        if (x == 0)
            return 0;
        else if (x <= tanPoint)
            return tanLine(x);
        else
            return sigmoid(x);
    }

    public static double getInf(double x) {
        if (x == 0)
            return 0;
        else
            return sigmoid(x);
    }

    public void generateLine() {
        int round = 0;
        tanPoint = findTangency();
        double delta;
        while (Math.abs(distance(1.0)) > EPSILON || Math.abs(distance(tanPoint)) > EPSILON) {
            tanPoint = findTangency();
            round++;
            if (Math.abs(distance(1.0)) > EPSILON) {
                b -= distance(1.0) * k;
                continue;
            }
            if (Math.abs(distance(tanPoint)) > EPSILON) {
                delta = distance(tanPoint) / 10.0;
                k -= delta;
                continue;
            }
        }

        System.out.println("Round " + round);
        System.out.println("tanPoint x " + tanPoint);
        System.out.println("Dis1 " + Math.abs(distance(1.0)));
        System.out.println("Dis2 " + Math.abs(distance(tanPoint)));
        System.out.println("Function : k = " + this.k + "     b = " + this.b);

    }

    public void generateLine(double x) {

        double y1 = sigmoid(1.0);
        double y2 = sigmoid(x);

        this.k = (y2 - y1)/(x - 1.0);
        this.b = y1 - this.k;

        tanPoint = x;

        System.out.println("Function : k = " + this.k + "     b = " + this.b);

    }

    private double findTangency() {
        int stage = 1;
        //stage 1, distance increase, stage 2, distance decrease
        double delta = 0.1;
        double x = 1.0;
        double d1 = 0.0, d2 = EPSILON;
        boolean forward = true;
        while (delta >= EPSILON) {
            // line doesn't intersect with sigmoid function
            if (forward)
                d1 = d2;
            x += delta;
            d2 = distance(x);
            if ((d1 > d2) && stage == 1)
                stage = 2;
            if ((d2 > d1) && stage == 2) //already cross tangency point.
            {
                x -= delta * 2; // back to 2 step ago.
                d1 = distance(x);
                delta /= 2.0;
                forward = false;
            } else
                forward = true;
        }
        return x;
    }

    private static double sigmoid(double x) {
        double y = 1.0 / (1.0 + Math.exp(alpha - x * beta));
        return y;
    }

    private static double tanLine(double x) {
        double y = k * x + b;
        return y;
    }

    private static double distance(double x) {
        return tanLine(x) - sigmoid(x);
    }
}


