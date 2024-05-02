package KoffeinKoll.Controller;

public class AlgorithmController {
    private  int beverageId;
    private final double beverageAmount;
    private DatabaseConnection databaseConnection;

    private double beverageConcentration;
    double halfLife = 5.7; // hours


    public AlgorithmController(int bevarageId, double beverageAmount) {
        this.beverageId = bevarageId;
        this.beverageAmount = beverageAmount;
        this.databaseConnection = DatabaseConnection.getInstance();

        calculateCaffeineAmount();
    }

    public double calculateTime(double cF, double cO) {
        return halfLife * Math.log(cF / cO) / Math.log(2);
    }


    public void calculateCaffeineAmount() {

        beverageConcentration = databaseConnection.getBeverageConcentration(beverageId);

        double cF = 1; // mg
        double c0 = beverageAmount * beverageConcentration;

        double time = -calculateTime(cF, c0);

        System.out.println("Time required: " + time + " hours");

    }

    public double getBeverageConcentration() {
        return beverageConcentration;
    }
}
