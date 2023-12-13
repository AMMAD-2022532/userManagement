
package com.dublin.manage.utility;

public class UtilMethods {
    
     /**
     * checks the string is digit or not
     * @param digit is the string can be number or not
     * @return boolean according to the given string is digit or not
     */
    public static boolean isDigit(String digit){
        try{
            
            int isNum = Integer.parseInt(digit);
            return true;
            
        }catch(Exception e){
            return false;
        }
    }
}
