

package com.dublin.manage.view;

import com.dublin.manage.controller.AdminController;
import com.dublin.manage.controller.Controller;
import com.dublin.manage.controller.UserController;
import com.dublin.manage.db.DBManager;
import com.dublin.manage.model.UserDetails;

public class UserManagement {

    public static void main(String[] args)throws Exception {
        
        UserDetails admin = new UserDetails("CCT", "DUBLIN", "CCT", "Dublin", true);
        UserDetails user = new UserDetails("ooc2023", "ooc2023", "ooc2023", "ooc2023", false);
        
        if (!DBManager.tableExists("APP", "USER_DETAILS")) {
            DBManager.createUserDetailsTable();
        }
        
        if (!DBManager.tableExists("APP", "USER_OPERATIONS")) {
            DBManager.createUserOperationsTable();
        }
        
        if(!DBManager.hasAdminUser()){
            DBManager.addUserDetails(admin);
        }
        
        if(!DBManager.isUsernameExists(user.getUsername())){
            DBManager.addUserDetails(user);
        }
        
        Controller controller = new AdminController();
         
        boolean exit = false;
            
        while(!exit){
        
            UserDetails validatedUserDetails = controller.defaultOperation();
            
            if(validatedUserDetails != null){
                
                if(!validatedUserDetails.isAdmin()){
                    controller = new UserController();
                }
                
                int task = controller.BasicOperation(validatedUserDetails.getId());
                
                if(task != 0){
                       controller.taskOperation(task, validatedUserDetails.getId());
                }
                
                controller = new AdminController();
            }else{break;}
        }
    }
}
