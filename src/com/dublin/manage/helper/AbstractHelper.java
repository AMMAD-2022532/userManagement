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
        
        boolean exit = false;
        
        while(!exit){
        
             // Retrieve user details from the database
            UserDetails userDetailsFromDB = DBManager.getUserDetailsById(userId);
            UserDetails userDetails = new UserDetails(userDetailsFromDB);

            System.out.println("Here is your current record: \n"+
                    "ID: " + userDetails.getId() + "\n" +
                    "Name: " + userDetails.getName() + "\n" +
                    "Surname: " + userDetails.getSurname() + "\n" +
                    "Username: " + userDetails.getUsername() + "\n"
            );
            
            // Display options for modification
            System.out.println("\n\nWhat you want to modify: ");
            System.out.println("\n1. Name");
            System.out.println("2. Surname");
            System.out.println("3. Username");
            System.out.println("4. Password");
            System.out.println("5. Exit");
            System.out.print("Please enter your input here: ");
            
            // Read user input for modification
            String whatToModify = input.next();

            if (UtilMethods.isDigit(whatToModify)) {
                int whatToModifyNum = Integer.parseInt(whatToModify);

                if (whatToModifyNum > 0 && whatToModifyNum < 5) {
                    // Modify the corresponding field based on user input
                    switch (whatToModifyNum) {
                        case 1:
                            input.nextLine();
                            System.out.print("Enter your name: ");
                            userDetails.setName(input.nextLine());
                            break;
                        case 2:
                            System.out.print("Enter your Surname: ");
                            userDetails.setSurname(input.next());
                            break;
                        case 3:
                            System.out.print("Enter your username: ");
                            userDetails.setUsername(input.next());
                            break;
                        case 4:
                            System.out.print("Enter your password: ");
                            userDetails.setPassword(input.next());
                    }
                }
                else if(whatToModifyNum == 5){
                 break;   
                }else {
                    System.out.println("\nInvalid Input");
                }
            } else {
                System.out.println("\nInvalid Input");
            }

            // Check if any changes were made before attempting an update
            if (!userDetails.equals(userDetailsFromDB)) {
                // Update user details in the database
                return DBManager.updateUserDetails(userDetails);
            }
            
            System.out.println("User info updated successfully!");
        }
        
        return false;
    }

}
