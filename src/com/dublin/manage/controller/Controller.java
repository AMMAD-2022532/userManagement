package com.dublin.manage.controller;

import com.dublin.manage.model.UserDetails;

/**
 * The Controller interface defines the contract for various controllers
 * in the Dublin Management System.
 */
public interface Controller {

    /**
     * Performs the default operation for the controller.
     *
     * @return UserDetails acquired from the default operation
     * @throws Exception if an error occurs during the operation
     */
    public UserDetails defaultOperation() throws Exception;

    /**
     * Performs a basic operation based on the provided user identifier.
     *
     * @param userId user identifier
     * @return an integer representing the operation result or choice
     * @throws Exception if an error occurs during the operation
     */
    public int BasicOperation(int userId) throws Exception;

    /**
     * Performs a specific task operation based on the provided task number
     * and user identifier.
     *
     * @param task   task number representing the desired operation
     * @param userId user identifier
     * @throws Exception if an error occurs during the task operation
     */
    public boolean taskOperation(int task, int userId) throws Exception;

    /**
     * Registers a new user and returns the user details.
     *
     * @return UserDetails of the registered user
     * @throws Exception if an error occurs during the registration
     */
    public UserDetails register() throws Exception;

    /**
     * Authenticates a user and returns the user details if successful.
     *
     * @return UserDetails if login is successful, otherwise null
     * @throws Exception if an error occurs during the login process
     */
    public UserDetails login() throws Exception;

    /**
     * Returns a specific controller based on the provided boolean parameter.
     *
     * @param isAdmin true if the user is an administrator, false otherwise
     * @return Controller instance based on the isAdmin parameter
     */
    public Controller getOperation(boolean isAdmin);
}
