package KoffeinKoll.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CaffeineCalculator {

    private double weight;
    private double recommendedDose;
    private UserController user;
    private int loggedCaffeine;
    final double CAFFEINE1_PER_KG = 2.0;
    final double CAFFEINE2_PER_KG = 3.0;
    final double CAFFEINE3_PER_KG = 4.5;
    private String habit;
    private BeverageController beverageController;


    public CaffeineCalculator(UserController user, BeverageController beverageController) {
        this.user = user;
        this.habit = user.getHabit();
        this.beverageController = beverageController;
        this.weight = user.getWeight(); // Set the weight first
        this.recommendedDose = calculateRecommendedDose(habit); // Then calculate recommended dose
        System.out.println("Recommended Dose: " + recommendedDose);
    }


    private double calculateRecommendedDose(String habit) {
        switch (habit) {
            case "0-1":
                recommendedDose = weight * CAFFEINE1_PER_KG;
                return recommendedDose;
            case "1-2":
                recommendedDose = weight * CAFFEINE2_PER_KG;
                return recommendedDose;
            case "2-5":
                recommendedDose = weight * CAFFEINE3_PER_KG;
                return recommendedDose;
        }
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
