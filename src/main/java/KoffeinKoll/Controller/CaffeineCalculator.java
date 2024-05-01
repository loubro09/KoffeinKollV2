package KoffeinKoll.Controller;

public class CaffeineCalculator {

    private double weight;
    private double recommendedDose;
    private UserController user;
    private int loggedCaffeine;
    private BeverageController beverageController;


    public CaffeineCalculator() {
        this.weight = 0;
        this.recommendedDose = 0;
    }

    public double calculateRecommendedDose() {
        weight = user.getWeight();
        final double CAFFEINE_PER_KG = 3.0;
        recommendedDose = weight * CAFFEINE_PER_KG;
        return recommendedDose;
    }

    public double getCaffeinePercentage() {
        double recommendedDose = calculateRecommendedDose();
        int loggedCaffeine = beverageController.getDailyCaffeineIntake(user.getId());
        return (loggedCaffeine / recommendedDose) * 100;
    }


}
