package KoffeinKoll.Controller;


import KoffeinKoll.View.BeverageMenuPage;

import java.util.Map;

public class AlgorithmController {
    private final int bevaregeId;
    private final double beverageAmount;
    private DatabaseConnection databaseConnection;
    private double halfLife; // Half-life in hours

    public AlgorithmController(int bevaregeId, double beverageAmount) {
        this.bevaregeId = bevaregeId;
        this.beverageAmount = beverageAmount;
    }

    public double calculateTime(double cF, double cO) {
        return halfLife * Math.log(cF / cO) / Math.log(2);
    }


    public static void main(String[] args) {
        int beverageId = 1;
        double halfLife = 5.7; // hours
        double cF = 1; // mg


        //for (beverageId){

       /* double cO = (beverageAmount * koffein-koncentration)

        AlgorithmController controller = new AlgorithmController(beverageId, beverageAmount);
        double time = controller.calculateTime(cF, cO);
        System.out.println("Time required: " + time + " hours");

        } */
    }
}
