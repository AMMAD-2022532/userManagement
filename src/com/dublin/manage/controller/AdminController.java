package com.dublin.manage.controller;

import com.dublin.manage.helper.AdminTaskHelper;
import com.dublin.manage.helper.Helper;
import com.dublin.manage.utility.UtilMethods;

/**
 * AdminController extends AbstractController and provides
 * specific operations for an administrator user.
 */
public class AdminController extends AbstractController {

    /**
     * Provides basic operations for an administrator user.
     *
     * @param userId user identifier
     * @return operation number selected by the administrator
     */
    @Override
    public int BasicOperation(int userId) {

        boolean exit = true;
        
        while(exit){
        
            System.out.println("\n\n1. Modify Profile");
            System.out.println("2. List of users");
            System.out.println("3. Remove a user");
            System.out.println("4. Review the user's operations");
            System.out.println("5. exit");
            System.out.print("\n\nPlease enter your input here: ");
            String op = input.next();

            if (UtilMethods.isDigit(op)) {

                int opNum = Integer.parseInt(op);

                if (opNum > 0 && opNum < 5) {

                    return opNum;

                } else if(opNum == 5){
                    
                    break;
                    
                } else {
                    System.out.println("Invalid input");

                }

            } else {
                System.out.println("Invalid input");
            }
        }
        
        return 0;
    }

    /**
     * Executes a task operation based on the selected task number.
     *
     * @param task   task number selected by the administrator
     * @param userId user identifier
     * @throws Exception if an error occurs during the task operation
     */
    @Override
    public boolean taskOperation(int task, int userId) throws Exception {

        // Instantiate AdminTaskHelper for admin-specific tasks
        Helper adminHelper = new AdminTaskHelper();

        switch (task) {

            case 1:
                // Modify Profile
                adminHelper.updateUserDetails(userId);
                return true;

            case 2:
                // List of users
                adminHelper.printUserDetails();
                return true;

            case 3:
                // Remove a user
                adminHelper.removeUserDetails();
                return true;

            case 4:
                // Review the user's operations
                adminHelper.listUserOperations();
                return true;
        }
        
        return false;
    }
}
