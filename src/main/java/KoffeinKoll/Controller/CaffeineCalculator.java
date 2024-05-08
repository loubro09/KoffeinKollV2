package KoffeinKoll.Controller;


/**
 * The CaffeineCalculator class calculates recommended caffeine doses and caffeine percentages based on user information.
 */
public class CaffeineCalculator {

    private double weight;
    private double recommendedDose;
    final double CAFFEINE1_PER_KG = 2.0;
    final double CAFFEINE2_PER_KG = 3.0;
    final double CAFFEINE3_PER_KG = 4.5;
    private String habit;


    /**
     * Constructor for CaffeineCalculator
     * @author Alanah Coleman
     */
    public CaffeineCalculator() {
        this.habit = UserController.getInstance().getHabit();
        this.weight = UserController.getInstance().getWeight();
        this.recommendedDose = calculateRecommendedDose(habit);
        System.out.println("Recommended Dose: " + recommendedDose);
    }

    /**
     * Retrieves the recommended caffeine dose
     *
     * @return The recommended caffeine dose
     * @author Alanah Coleman
     */
    public double getRecommendedDose() {
        return recommendedDose;
    }

    /**
     * Calculates the recommended caffeine dose based on the user's habit
     *
     * @param habit The user's caffeine habit.
     * @return The recommended caffeine dose.
     * @author Alanah Coleman
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

}
