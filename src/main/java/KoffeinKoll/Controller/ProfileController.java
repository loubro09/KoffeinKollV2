package KoffeinKoll.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

/**
 * The ProfileController class manages user profile updates.
 */
public class ProfileController {

    private DatabaseConnection databaseConnection;

    /**
     * Constructs a new ProfileController object.
     * @author                                                                                          //AUTHOR
     */
    public ProfileController() {
        this.databaseConnection = databaseConnection.getInstance();
    }

    /**
     * Updates the user profile with the provided information.
     * @param userId         The ID of the user to update.
     * @param newHabit       The new habit of the user.
     * @param newWeight      The new weight of the user.
     * @param newDateOfBirth The new date of birth of the user.
     * @return True if the update is successful, otherwise false.
     * @author                                                                                          //AUTHOR
     */
    public boolean updateUser(int userId, String newHabit, double newWeight, String newDateOfBirth) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseConnection.getConnection();

            //Construct the SQL update statement dynamically based on the provided input
            StringBuilder updateStatement = new StringBuilder("UPDATE users SET ");
            boolean hasUpdates = false;

            //Check and update height if provided
            if (newHabit != null && !newHabit.isEmpty()) {
                updateStatement.append("habit = ?, ");
                hasUpdates = true;
            }

            //Check and update weight if provided
            if (newWeight!=0) {
                updateStatement.append("weight = ?, ");
                hasUpdates = true;
            }

            //Check and update birthdate if provided
            if (newDateOfBirth != null && !newDateOfBirth.isEmpty()) {
                updateStatement.append("birthdate = ?, ");
                hasUpdates = true;
            }

            if (hasUpdates) {
                updateStatement.setLength(updateStatement.length() - 2);
                updateStatement.append(" WHERE user_id = ?");

                preparedStatement = connection.prepareStatement(updateStatement.toString());
                int parameterIndex = 1;

                if (newHabit != null && !newHabit.isEmpty()) {
                    preparedStatement.setObject(parameterIndex++, newHabit);
                }
                if (newWeight!=0) {
                    preparedStatement.setObject(parameterIndex++, (newWeight));
                }
                if (newDateOfBirth != null && !newDateOfBirth.isEmpty()) {
                    preparedStatement.setDate(parameterIndex++, Date.valueOf(newDateOfBirth));
                }
                preparedStatement.setInt(parameterIndex, userId);

                //Execute the update
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    return true;
                } else {
                    if (!hasUpdates) {
                        System.out.println("No changes were made.");
                    } else {
                        System.out.println("Failed to update user information.");
                    }
                    return false;
                }
            } else {
                System.out.println("No updates provided.");
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            //closeResources(connection, preparedStatement, null);
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}