package KoffeinKoll.Controller;


public class AlgorithmController {
    private double halfLife; // Half-life in hours

    public AlgorithmController(double halfLife) {
        this.halfLife = halfLife;
    }

    public double calculateTime(double cF, double cO) {
        return halfLife * Math.log(cF / cO) / Math.log(2);
    }

    public static void main(String[] args) {
        double halfLife = 5.7; // hours
        double cF = 1; // mg


       /* double cO = mängd dryck * koffein-koncentration

        AlgorithmController controller = new AlgorithmController(halfLife);
        double time = controller.calculateTime(cF, cO);

        System.out.println("Time required: " + time + " hours");
        
        */
    }
}
