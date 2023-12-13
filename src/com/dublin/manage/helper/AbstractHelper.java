package com.dublin.manage.helper;

import com.dublin.manage.db.DBManager;
import com.dublin.manage.model.UserDetails;
import com.dublin.manage.utility.UtilMethods;
import java.util.List;
import java.util.Scanner;

/**
 * An abstract class that implements the Helper interface and provides common helper methods.
 */
public abstract class AbstractHelper implements Helper {
    
    // Scanner object for user input
    Scanner input = new Scanner(System.in);
    
    /**
     * Display user details for a given user ID.
     * @param userId The ID of the user whose details to display.
     * @throws Exception if a database access error occurs.
     */
    public void showUserDetails(int userId) throws Exception {
        // Retrieve user details by ID
        UserDetails userDetails = DBManager.getUserDetailsById(userId);

        // Display user details
        System.out.println("Name: " + userDetails.getName());
        System.out.println("Surname: " + userDetails.getSurname());
        System.out.println("Username: " + userDetails.getUsername());
    }
    
    /**
     * Update user details for a given user ID based on user input.
     * @param userId The ID of the user whose details to update.
     * @return true if the update is successful, false otherwise.
     * @throws Exception if a database access error occurs.
     */
    public boolean updateUserDetails(int userId) throws Exception {
        // Display options for modification
        System.out.println("What you want to modify: ");
        System.out.println("1. Name");
        System.out.println("2. Surname");
        System.out.println("3. Username");
        System.out.println("4. Password");
        System.out.print("Please enter your input here: ");
        
        // Retrieve user details from the database
        UserDetails userDetailsFromDB = DBManager.getUserDetailsById(userId);
        UserDetails userDetails = new UserDetails(userDetailsFromDB);
       
        // Read user input for modification
        String whatToModify = input.next();
        
        if (UtilMethods.isDigit(whatToModify)) {
            int whatToModifyNum = Integer.parseInt(whatToModify);
            
            if (whatToModifyNum > 0 && whatToModifyNum < 5) {
                // Modify the corresponding field based on user input
                switch (whatToModifyNum) {
                    case 1:
                        userDetails.setName(input.nextLine());
                        break;
                    case 2:
                        userDetails.setSurname(input.next());
                        break;
                    case 3:
                        userDetails.setUsername(input.next());
                        break;
                    case 4:
                        userDetails.setPassword(input.next());
                        break;
                }
            } else {
                System.out.println("Invalid Input");
            }
        } else {
            System.out.println("Invalid Input");
        }
     
        // Check if any changes were made before attempting an update
        if (!userDetails.equals(userDetailsFromDB)) {
            // Update user details in the database
            return DBManager.updateUserDetails(userDetails);
        } else {
            return false; // No changes were made
        }
    }
    
    /**
     * Print details of non-admin users.
     * @throws Exception if a database access error occurs.
     */
    protected void printUserDetails() throws Exception {
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
