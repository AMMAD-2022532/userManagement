

package com.dublin.manage.view;

import com.dublin.manage.controller.AdminController;
import com.dublin.manage.controller.Controller;
import com.dublin.manage.db.DBManager;
import com.dublin.manage.model.UserDetails;

public class UserManagement {

    public static void main(String[] args)throws Exception {
        
        UserDetails userDetails = new UserDetails("CCT", "DUBLIN", "CCT", "Dublin", true);
        
        if (!DBManager.tableExists("APP", "USER_DETAILS")) {
            DBManager.createUserDetailsTable();
        }
        
        if (!DBManager.tableExists("APP", "USER_OPERATIONS")) {
            DBManager.createUserOperationsTable();
        }
        
        if(!DBManager.hasAdminUser()){
            DBManager.addUserDetails(userDetails);
        }
        
        Controller controller = new AdminController();
        UserDetails validatedUserDetails = controller.defaultOperation();
        
        
        if(validatedUserDetails != null && validatedUserDetails.isAdmin()){
            
            controller.BasicOperation(validatedUserDetails.getId());
            
            
        }
        
        //validation checking of login and registration
    }
}
