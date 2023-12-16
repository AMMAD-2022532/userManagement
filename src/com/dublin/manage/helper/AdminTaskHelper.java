package com.dublin.manage.helper;

import com.dublin.manage.db.DBManager;
import com.dublin.manage.dto.UserOperationDTO;
import com.dublin.manage.model.UserDetails;
import com.dublin.manage.utility.UtilMethods;
import java.util.List;

/**
 * Helper class for performing admin tasks.
 */
public class AdminTaskHelper extends AbstractHelper {

    /**
     * Remove user details based on user input.
     *
     * @return true if the removal is successful, false otherwise.
     * @throws Exception if a database access error occurs.
     */
    @Override
    public boolean removeUserDetails() throws Exception {
        // Display user details for selection
        printUserDetails();

        // Get user input for the user ID to delete
        System.out.print("\n Enter the id of the user you want to delete: ");
        String id = input.next();

        if (UtilMethods.isDigit(id)) {
            int userId = Integer.parseInt(id);
            return DBManager.deleteUserById(userId);
        } else {
            System.out.println("Invalid Input");
            return false;
        }
    }

    /**
     * List user operations for a selected user.
     *
     * @throws Exception if a database access error occurs.
     */
    @Override
    public void listUserOperations() throws Exception {
//        // Display user details for selection
//        printUserDetails();

        // Get user input for the user ID to list operations
        System.out.print("\nEnter User ID to list its operations: ");
        String id = input.next();

        if (UtilMethods.isDigit(id)) {
            int userId = Integer.parseInt(id);
            List<UserOperationDTO> userOperations = DBManager.listUserOperations();

            int count = 1;
            for (UserOperationDTO userOperation : userOperations) {
                System.out.println("\nOperation " + count + ": ");
                System.out.println("\nID: " + userOperation.getId());
                System.out.println("Name: " + userOperation.getName());
                System.out.println("Surname: " + userOperation.getName()); // NOTE: Should be "Surname: " + userOperation.getSurname()
                System.out.println("Operation: " + userOperation.getName()); // NOTE: Should be "Operation: " + userOperation.getUserOperation()

                // Increment count for the next operation
                count++;
            }
        } else {
            System.out.println("Invalid Input");
        }
    }

    /**
     * Add an operation for a selected user.
     *
     * @param userId The ID of the user to add the operation for.
     * @return true if the addition is successful, false otherwise.
     * @throws Exception if a database access error occurs.
     */
    @Override
    public boolean addOperation(int userId) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Placeholder for the method body
    }
    
       
    /**
     * Print details of non-admin users.
     * @throws Exception if a database access error occurs.
     */
    public void printUserDetails() throws Exception {
        // Retrieve details of non-admin users from the database
        List<UserDetails> userDetails = DBManager.getNonAdminUsers();
        int count = 1;

        // Print details for each non-admin user
        for (UserDetails userDetail : userDetails) {
            System.out.println("\n" + count + ". User: ");
            System.out.println("User ID: " + userDetail.getId());
            System.out.println("Name: " + userDetail.getName());
            System.out.println("Surname: " + userDetail.getSurname());
            System.out.println("Username: " + userDetail.getUsername());
            System.out.println("Password: " + userDetail.getPassword());
           
            count++;
        }
    }
}
