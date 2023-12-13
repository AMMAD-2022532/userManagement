package com.dublin.manage.controller;

import com.dublin.manage.db.DBManager;
import com.dublin.manage.model.UserDetails;
import com.dublin.manage.utility.UtilMethods;

import java.util.Scanner;

/**
 * AbstractController provides a base implementation for common operations
 * and serves as the parent class for specific controllers.
 */
public abstract class AbstractController implements Controller {

    Scanner input = new Scanner(System.in);
    String defaultInput;

    protected AbstractController() {}

    /**
     * The default operation that executes first in the program.
     *
     * @return UserDetails acquired from the input
     * @throws Exception exception
     */
    @Override
    public UserDetails defaultOperation() throws Exception {
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.print("Enter your choice here: ");

        defaultInput = input.next();

        if (UtilMethods.isDigit(defaultInput)) {
            int op = Integer.parseInt(defaultInput);

            if (op == 1 || op == 2) {
                if (op == 1) {
                    return login();
                }
                if (op == 2) {
                    return register();
                }
            } else {
                System.out.println("Invalid Input");
            }
        } else {
            System.out.println("Invalid Input");
        }

        return null;
    }

    /**
     * Returns the corresponding object according to the user.
     *
     * @param isAdmin boolean to tell getOperation if the user is admin or a simple user
     * @return returns the Controller object wrapped in ControllerInterface
     */
    @Override
    public Controller getOperation(boolean isAdmin) {
        if (isAdmin) {
            return new AdminController();
        } else {
            return new UserController();
        }
    }

    /**
     * Checks user credentials for login.
     *
     * @return UserDetails if login fails then null
     * @throws Exception
     */
    @Override
    public UserDetails login() throws Exception {
        
        System.out.print("Enter your username: ");
        String username = input.next();

        System.out.print("Enter your password: ");
        String password = input.next();

        UserDetails userDetails = DBManager.getUserDetailsByCredentials(username, password);

        return userDetails;
    }

    /**
     * Registers a new user.
     *
     * @return UserDetails of the registered user
     * @throws Exception
     */
    @Override
    public UserDetails register() throws Exception {
        UserDetails userDetails = new UserDetails();

        System.out.print("Enter your name: ");
        userDetails.setName(input.nextLine());

        System.out.print("Enter your surname: ");
        userDetails.setSurname(input.nextLine());

        System.out.print("Enter your username: ");
        userDetails.setUsername(input.next());

        System.out.print("Enter your password: ");
        userDetails.setPassword(input.next());
        userDetails.setAdmin(false);

        return DBManager.addUserDetails(userDetails);
    }
}
