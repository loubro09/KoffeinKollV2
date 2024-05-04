package KoffeinKoll.Controller;


/**
 * The CaffeineCalculator class calculates recommended caffeine doses and caffeine percentages based on user information.
 */
public class CaffeineCalculator {

    private double weight;
    private double recommendedDose;
    private UserController user;
    final double CAFFEINE1_PER_KG = 2.0;
    final double CAFFEINE2_PER_KG = 3.0;
    final double CAFFEINE3_PER_KG = 4.5;
    private String habit;
    private BeverageController beverageController;


    /**
     * Constructor for CaffeineCalculator
     * @param user The user controller instance
     * @param beverageController The beverage controller instance.
     * @author alanahColeman
     */
    public CaffeineCalculator(UserController user, BeverageController beverageController) {
        this.user = user;
        this.habit = user.getHabit();
        this.beverageController = beverageController;
        this.weight = user.getWeight();
        this.recommendedDose = calculateRecommendedDose(habit);
        System.out.println("Recommended Dose: " + recommendedDose);
    }


    /**
     * Calculates the recommended caffeine dose based on the user's habit
     * @param habit The user's caffeine habit.
     * @return The recommended caffeine dose.
     * @author alanahColeman
     */
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


    /**
     * Retrieves the recommended caffeine dose
     * @return The recommended caffeine dose
     * @author alanahColeman
     */
    public double getRecommendedDose() {
        return recommendedDose;
    }



}
