package KoffeinKoll.Controller;

public class AlgorithmController {
    private  int beverageId;
    private double beverageAmount;
    private DatabaseConnection databaseConnection;
    private double beverageConcentration;
    double halfLife = 5.7; // hours


    public AlgorithmController() {
        this.databaseConnection = DatabaseConnection.getInstance();
    }

    public double calculateTime() {
        double cF = 1; // mg
        double c0 = beverageAmount * beverageConcentration;
        return -halfLife * Math.log(cF / c0) / Math.log(2);
    }


    public void calculateCaffeineAmount(int beverageId, double beverageAmount) {
        this.beverageId = beverageId;
        this.beverageAmount = beverageAmount;
        beverageConcentration = databaseConnection.getBeverageConcentration(beverageId);
        System.out.println("Time required: " + calculateTime() + " hours");
    }

    public double getBeverageConcentration() {
        return beverageConcentration;
    }
}
