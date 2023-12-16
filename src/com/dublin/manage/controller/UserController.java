package com.dublin.manage.controller;

import com.dublin.manage.db.DBManager;
import com.dublin.manage.helper.AdminTaskHelper;
import com.dublin.manage.helper.Helper;
import com.dublin.manage.helper.UserTaskHelper;
import com.dublin.manage.model.UserDetails;
import com.dublin.manage.utility.UtilMethods;

/**
 * UserController extends AbstractController and provides
 * specific operations for a regular user.
 */
public class UserController extends AbstractController {

    /**
     * Performs basic operations for a regular user.
     *
     * @param userId user identifier
     * @return an integer representing the selected operation
     * @throws Exception if an error occurs during the operation
     */
    @Override
    public int BasicOperation(int userId) throws Exception {
        
        boolean exit = false;
        
        while(!exit){
        
                System.out.println("\n\n1. Modify Profile");
                System.out.println("2. Add an operation");
                System.out.println("3. exit");
                System.out.print("Please enter your input here: ");
                String op = input.next();

                if (UtilMethods.isDigit(op)) {

                    int opNum = Integer.parseInt(op);

                    if (opNum > 0 && opNum < 3) {
                        return opNum;
                    }else if(opNum == 3){
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
     * Performs a specific task operation based on the provided task number
     * and user identifier.
     *
     * @param task   task number representing the desired operation
     * @param userId user identifier
     * @throws Exception if an error occurs during the task operation
     */
    @Override
    public boolean taskOperation(int task, int userId) throws Exception {
        Helper userTaskHelper = new UserTaskHelper();

        switch (task) {
            case 1:
                // Modify Profile
                userTaskHelper.updateUserDetails(userId);
                break;

            case 2:
                // Add an operation
                userTaskHelper.addOperation(userId);
                break;
        }
        
        return false;
    }
}
