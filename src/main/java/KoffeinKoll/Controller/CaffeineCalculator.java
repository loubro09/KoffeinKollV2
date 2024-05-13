package KoffeinKoll.Controller;


/**
 * The CaffeineCalculator class calculates recommended caffeine doses and caffeine percentages based on user information.
 */
public class CaffeineCalculator {

    private double weight;
    private int recommendedDose;
    final double CAFFEINE1_PER_KG = 2.0;
    final double CAFFEINE2_PER_KG = 3.0;
    final double CAFFEINE3_PER_KG = 4.5;
    private String habit;
    private int currentDose;
    private AlgorithmController algorithmController;


    /**
     * Constructor for CaffeineCalculator
     *
     * @author Alanah Coleman
     */
    public CaffeineCalculator() {
        this.habit = UserController.getInstance().getHabit();
        this.weight = UserController.getInstance().getWeight();
        this.recommendedDose = calculateRecommendedDose(habit);
        this.algorithmController = new AlgorithmController();
        this.currentDose = (int) algorithmController.getTotalCaffeineForDay(UserController.getInstance().getId());
        System.out.println("Recommended Dose: " + recommendedDose);
    }

    /**
     * Retrieves the recommended caffeine dose
     *
     * @return The recommended caffeine dose
     * @author Alanah Coleman
     */
    public int getRecommendedDose() {
        return recommendedDose;
    }


    public int calculateExcessConsumption() {
        int excess = recommendedDose - currentDose;
        return excess;
    }


    /**
     * Calculates the recommended caffeine dose based on the user's habit
     *
     * @param habit The user's caffeine habit.
     * @return The recommended caffeine dose.
     * @author Alanah Coleman
     */
    private int calculateRecommendedDose(String habit) {
        switch (habit) {
            case "0-1":
                recommendedDose = (int) (weight * CAFFEINE1_PER_KG);
                return recommendedDose;
            case "1-2":
                recommendedDose = (int) (weight * CAFFEINE2_PER_KG);
                return recommendedDose;
            case "2-5":
                recommendedDose = (int) (weight * CAFFEINE3_PER_KG);
                return recommendedDose;
        }
        return recommendedDose;
    }


}
