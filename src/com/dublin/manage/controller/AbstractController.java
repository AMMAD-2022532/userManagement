package com.dublin.manage.controller;

import com.dublin.manage.db.DBManager;
import com.dublin.manage.model.UserDetails;
import com.dublin.manage.utility.UtilMethods;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * AbstractController provides a base implementation for common operations
 * and serves as the parent class for specific controllers.
 */
public abstract class AbstractController implements Controller {

    protected Scanner input = new Scanner(System.in);
    protected String defaultInput;
    private static final String PASSWORD_PATTERN = "^(?=.*\\d).{7,}$";

    protected AbstractController() {}

    /**
     * The default operation that executes first in the program.
     *
     * @return UserDetails acquired from the input
     * @throws Exception exception
     */
    @Override
    public UserDetails defaultOperation() throws Exception {
        
        boolean exit = true;
        
        while(exit){
        
            System.out.println("\n\n1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Enter your choice here: ");

            defaultInput = input.next();

            if (UtilMethods.isDigit(defaultInput)) {
                int op = Integer.parseInt(defaultInput);
                
                
                switch(op){
                    
                    case 1:
                        return login();
                    case 2:
                        return register();
                    case 3:
                        exit = false;
                        break;
                    default:
                        System.out.println("\n\nInvalid Input");
                        System.out.println("\nThere are only 2 choices, 1 OR 2");
                        System.out.println("If you want to end the app please type 3");
                }
                
            } else {
                System.out.println("\n\nInvalid Input");
                System.out.println("The choice can't be non-numeric");
            }

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
          
        boolean exitLogin = true;
        UserDetails userDetails = null;
        
        while(exitLogin){
        
            System.out.print("\n\nEnter your username: ");
            String username = input.next();

            System.out.print("Enter your password: ");
            String password = input.next();
            
           
            userDetails = DBManager.getUserDetailsByCredentials(username, password);

            if(userDetails != null)
                return userDetails;
            else System.out.println("\n\nInvalid username or password");
        
        }
        
        return null;
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
        
        boolean exitRegister = true;
        
        boolean fromChoice = true;
        
        while(exitRegister){

            System.out.print("\n\nEnter your name: ");
            if(fromChoice){
                input.nextLine();
                fromChoice = !fromChoice;
            }    
            userDetails.setName(input.nextLine());

            System.out.print("Enter your surname: ");
            userDetails.setSurname(input.nextLine());

            System.out.print("Enter your username: ");
            userDetails.setUsername(input.nextLine());

            System.out.print("Enter your password: ");
            userDetails.setPassword(input.nextLine());
            
            userDetails.setAdmin(false);
            
            String validationResult = validateUserRegistration(userDetails);
            
            if(!validationResult.equals("success")){
                System.out.println("\n\n"+validationResult);
            }else{
               return DBManager.addUserDetails(userDetails);
            }

        }
        
        return null;
    }
    
    private String validateUserRegistration(UserDetails userDetails)throws Exception{
            
        if(userDetails.getName() == null || 
                userDetails.getName().equals(""))
            return "Name can't be empty";
        else if(UtilMethods.isDigit(userDetails.getName()))
          return "Name can't be a number";

        else if(userDetails.getSurname() == null || 
                userDetails.getSurname().equals(""))
            return "Surname can't be empty";
        else if(UtilMethods.isDigit(userDetails.getSurname()))
          return "Surname can't be a number";

        else if(userDetails.getUsername() == null || 
                userDetails.getUsername().equals(""))
            return "Username can't be empty";
        else if(UtilMethods.isDigit(userDetails.getUsername()))
          return "Username can't be a number";
        else if(userDetails.getUsername().contains(" "))
          return "username can't have space";
        else if(DBManager.isUsernameExists(userDetails.getUsername()))
          return "username already exists";

        else if(validatePassword(userDetails.getPassword()) != null)
            return "Password must be at least 7 characters long and "
                    + "Password must contain at least one number.";
         else if(userDetails.getUsername().contains(" "))
          return "username can't have space"; 

        return "success";
       
    }
    
    private String validatePassword(String password){
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        
        if(!matcher.matches())
            return "Password must be at least 7 characters long and "
                + "Password must contain at least one number.";
        return null;
    }
    
    private String validateUsername(String username){
        if(username == null || 
                username.equals(""))
            return "Username can't be empty";
        else if(UtilMethods.isDigit(username))
          return "Username can't be a number";
         
        return null; 
    }
}
