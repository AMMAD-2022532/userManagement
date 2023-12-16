package com.dublin.manage.helper;

import com.dublin.manage.model.UserDetails;
import java.util.List;

/**
 * Interface defining methods for user management operations.
 */
public interface Helper {

    /**
     * Show details of a user based on the provided user ID.
     *
     * @param userId The ID of the user.
     * @throws Exception if a database access error occurs.
     */
    public void showUserDetails(int userId) throws Exception;

    /**
     * Update details of a user based on the provided user ID.
     *
     * @param userId The ID of the user.
     * @return true if the update is successful, false otherwise.
     * @throws Exception if a database access error occurs.
     */
    public boolean updateUserDetails(int userId) throws Exception;

    /**
     * Remove details of a user.
     *
     * @return true if the removal is successful, false otherwise.
     * @throws Exception if a database access error occurs.
     */
    public boolean removeUserDetails() throws Exception;

    /**
     * List user operations.
     *
     * @throws Exception if a database access error occurs.
     */
    public void listUserOperations() throws Exception;

    /**
     * Add an operation for a user based on the provided user ID.
     *
     * @param userId The ID of the user.
     * @return true if the addition is successful, false otherwise.
     * @throws Exception if a database access error occurs.
     */
    public boolean addOperation(int userId) throws Exception;
    
    /**
     * Print details of non-admin users.
     * @throws Exception if a database access error occurs.
     */
    public void printUserDetails() throws Exception;
}
