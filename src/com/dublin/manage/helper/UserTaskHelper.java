package com.dublin.manage.helper;

import com.dublin.manage.db.DBManager;
import com.dublin.manage.model.UserOperations;

/**
 * Helper class for tasks related to regular users.
 */
public class UserTaskHelper extends AbstractHelper {

    @Override
    public boolean removeUserDetails() throws Exception {
        // Implementation not supported for regular users
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void listUserOperations() throws Exception {
        // Implementation not supported for regular users
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addOperation(int userId) throws Exception {
        // Prompt user for income and tax credits
        System.out.print("Enter your income: ");
        double income = input.nextDouble();
        System.out.print("Enter your Tax Credits: ");
        double taxCredits = input.nextDouble();

        // Calculate income tax and create a UserOperations object
        UserOperations userOperation = new UserOperations(userId,
                "(Income - taxCredits) X taxRate = " + calculateIncomeTax(income, taxCredits));

        // Add the user operation to the database
        return DBManager.addUserOperation(userOperation);
    }

    /**
     * Calculates income tax based on gross income and tax credits.
     *
     * @param grossIncome Gross income of the user.
     * @param taxCredits Tax credits of the user.
     * @return Calculated income tax.
     */
    private static double calculateIncomeTax(double grossIncome, double taxCredits) {
        // Simplified tax rate, replace with actual rates based on your country's tax laws
        double taxRate = 0.2;  // 20%
        double taxableIncome = grossIncome - taxCredits;
        return taxableIncome * taxRate;
    }

    // Additional methods for calculating USC and PRSI can be added if needed

    @Override
    public void printUserDetails() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
