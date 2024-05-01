package KoffeinKoll.Controller;

public class CaffeineCalculator {

    private double weight;
    private double recommendedDose;
    private UserController user;
    private int loggedCaffeine;
    final double CAFFEINE_PER_KG = 3.0;
    private BeverageController beverageController;

    public CaffeineCalculator(UserController user, BeverageController beverageController) {
        this.user = user;
        this.beverageController = beverageController;
        this.weight = user.getWeight(); // Set the weight first
        this.recommendedDose = calculateRecommendedDose(); // Then calculate recommended dose
        System.out.println("Recommended Dose: " + recommendedDose);
    }

    private double calculateRecommendedDose() {
        recommendedDose = weight * CAFFEINE_PER_KG;
        return recommendedDose;
    }

    public double getCaffeinePercentage() {
        int loggedCaffeine = beverageController.getDailyCaffeineIntake(user.getId());
        return (loggedCaffeine / recommendedDose) * 100;
    }

    public double getRecommendedDose() {
        return recommendedDose;
    }
}
