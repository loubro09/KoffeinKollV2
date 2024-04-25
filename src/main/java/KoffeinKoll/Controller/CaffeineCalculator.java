package KoffeinKoll.Controller;

import KoffeinKoll.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CaffeineCalculator {

    private double weight;
    private double recommendedDose;
    private LoginController loginController;
    private int userId;
    private User user;

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




}
