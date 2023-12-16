package com.dublin.manage.db;

import com.dublin.manage.dto.UserOperationDTO;
import com.dublin.manage.model.UserDetails;
import com.dublin.manage.model.UserOperations;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    
    private static  Connection conn;
    
    /**
     * Before every operation through this class static block will run,
     * therefore wrote database connection inside static block so that 
     * before every operation database connection should be available.
     */
    static{
        try{
            String jdbcUrl = "jdbc:derby://localhost:1527/user_management;create=true;";
            conn = DriverManager.getConnection(jdbcUrl, "APP", "123");
        }catch(Exception e){
            System.out.println("Could not establish database connection");
            e.printStackTrace();
        }
    }
    
    /**
     * check for table exists?
     * @param schema          give schema name
     * @param tableName       table name
     * @return                returns the boolean is exists or not
     * @throws SQLException   sql exception throw by this method
     */
    public static boolean tableExists(String schema, String tableName) throws SQLException {
        ResultSet tables = conn.getMetaData().getTables(null, schema, tableName, null);
        return tables.next();
    }
    
     // Method to create the USER_DETAILS table in the APP schema
    /**
     * Creates the USER_DETAILS table in the APP schema.
     * @return true if the table creation is successful, false otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public static boolean createUserDetailsTable() throws SQLException {
        // SQL query to create the USER_DETAILS table
        String createTableQuery = "CREATE TABLE USER_DETAILS (" +
                "id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY, " +
                "name VARCHAR(255), " +
                "surname VARCHAR(255), " +
                "username VARCHAR(255), " +
                "password CLOB, " +
                "admin BOOLEAN)";
        
        // PreparedStatement object for executing the SQL query
        PreparedStatement createTableStmt = null;

        try {
            // Execute the SQL query to create the table
            createTableStmt = conn.prepareStatement(createTableQuery);
            createTableStmt.executeUpdate();

            // If execution is successful, return true
            return true;
        } finally {
            // Close the PreparedStatement
            if (createTableStmt != null) {
                createTableStmt.close();
            }
        }
    }
    
    /**
     * 
     * @param userDetails Bean object containing the details of user which need to be inserted
     * @return            return Boolean to let this method caller to know that user details inserted or not  
     * @throws Exception throws Exception which need to be handled by the caller
     */
    public static UserDetails addUserDetails(UserDetails userDetails)throws Exception{
        
        String query = "INSERT INTO USER_DETAILS(name, surname, username, password, admin) values(?,?,?,?,?)";
        PreparedStatement stmt = null;
        try{
            stmt = conn.prepareStatement(query);
            
            stmt.setString(1,userDetails.getName());
            stmt.setString(2,userDetails.getSurname());
            stmt.setString(3,userDetails.getUsername());
            stmt.setString(4,UserDetails.getHashPassword(userDetails.getPassword()));
            stmt.setBoolean(5,userDetails.isAdmin());
            
            int i = stmt.executeUpdate();
            
            if(i > 0)
                return userDetails;
            else return null;
        }finally{
            if(stmt != null)
                stmt.close();
        }
    }
    
    // Method to retrieve user details where admin is true
/**
 * Retrieves user details for administrators from the USER_DETAILS table.
 *
 * @throws SQLException if a database access error occurs.
 */
    public static UserDetails retrieveAdminUserDetails() throws SQLException {
        // SQL query to select user details for administrators
        String query = "SELECT * FROM USER_DETAILS WHERE admin = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Set the parameter for the WHERE clause (admin = true)
            stmt.setBoolean(1, true);

            try (ResultSet resultSet = stmt.executeQuery()) {
                // Iterate over the result set to retrieve and handle user details
                while (resultSet.next()) {

                    UserDetails userDetails = new UserDetails(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getBoolean("admin")
                    );
                    
                    // Extract user details from the result set
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String surname = resultSet.getString("surname");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    boolean isAdmin = resultSet.getBoolean("admin");
                    
                    return userDetails;
                }
                
                return null;
            }
        }
    }
    
     // Method to delete admin users from the USER_DETAILS table
    /**
     * Deletes admin users from the USER_DETAILS table.
     *
     * @return the number of rows affected by the delete operation.
     * @throws SQLException if a database access error occurs.
     */
    public static int deleteAdminUsers() throws SQLException {
        // SQL query to delete admin users
        String query = "DELETE FROM USER_DETAILS WHERE admin = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Set the parameter for the WHERE clause (admin = true)
            stmt.setBoolean(1, true);

            // Execute the delete operation
            return stmt.executeUpdate();
        }
    }
    
    // Method to check if there is at least one admin user in the USER_DETAILS table
    /**
     * Checks if there is at least one admin user in the USER_DETAILS table.
     *
     * @return true if at least one admin user exists, false otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public static boolean hasAdminUser() throws SQLException {
        // SQL query to check if there is at least one admin user
        String query = "SELECT 1 FROM USER_DETAILS WHERE admin = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Set the parameter for the WHERE clause (admin = true)
            stmt.setBoolean(1, true);

            // Execute the query
            try (ResultSet resultSet = stmt.executeQuery()) {
                // Check if the result set has at least one row
                return resultSet.next();
            }
        }
    }
    
     // Method to retrieve UserDetails based on username and password
    /**
     * Retrieves UserDetails for a given username and password.
     *
     * @param username the username of the user.
     * @param password the plain text password of the user.
     * @return UserDetails object if a matching user is found, or null if no match.
     * @throws SQLException if a database access error occurs.
     */
    public static UserDetails getUserDetailsByCredentials(String username, String password) throws SQLException {
        // SQL query to check if there is a matching user
        String query = "SELECT * FROM USER_DETAILS WHERE username = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Set parameters for the WHERE clause
            
            stmt.setString(1, username);

            // Execute the query
            try (ResultSet resultSet = stmt.executeQuery()) {
                // Check if the result set has at least one row
                if (resultSet.next()) {
                    // Retrieve user details from the result set
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String surname = resultSet.getString("surname");
                    boolean isAdmin = resultSet.getBoolean("admin");

                    if(!UserDetails.getHashPassword(password).equals(resultSet.getString("password")))
                        return null;
                    
                    // Create and return a UserDetails object for the matching user
                    return new UserDetails(id, name, surname, username, null, isAdmin);
                } else {
                    // No matching user found
                    return null;
                }
            }
        }
    }
    
     // Method to delete the USER_DETAILS table
    /**
     * Deletes the entire USER_DETAILS table.
     *
     * @return true if the table is successfully deleted, false otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public static boolean deleteTable(String tableName) throws SQLException {
        // SQL query to delete the entire table
        String query = "DROP TABLE " + tableName;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Execute the drop table operation
            stmt.executeUpdate();

            // If no exception is thrown, assume the table is deleted successfully
            return true;
        }
    }
    
     /**
     * Checks if a username already exists in the user database.
     *
     * @param username The username to check for existence.
     * @return true if the username already exists, false otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public static boolean isUsernameExists(String username) throws SQLException {
        // SQL query to check if the username exists
        String query = "SELECT 1 FROM USER_DETAILS WHERE username = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Set the parameter for the WHERE clause
            stmt.setString(1, username);

            // Execute the query
            try (ResultSet resultSet = stmt.executeQuery()) {
                // Check if the result set has at least one row
                return resultSet.next();
            }
        }
    }
    
     // Other methods and properties...

    /**
     * Retrieves user details by ID from the user database.
     *
     * @param userId The ID of the user to retrieve.
     * @return UserDetails object representing the user, or null if not found.
     * @throws SQLException if a database access error occurs.
     */
    public static UserDetails getUserDetailsById(int userId) throws SQLException {
        // SQL query to retrieve user details by ID
        String query = "SELECT * FROM USER_DETAILS WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Set parameter for the WHERE clause
            stmt.setInt(1, userId);

            // Execute the query
            try (ResultSet resultSet = stmt.executeQuery()) {
                // Check if the result set has at least one row
                if (resultSet.next()) {
                    // Retrieve user details from the result set
                    String name = resultSet.getString("name");
                    String surname = resultSet.getString("surname");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    boolean isAdmin = resultSet.getBoolean("admin");

                    // Create and return a UserDetails object for the matching user
                    return new UserDetails(userId, name, surname, username, password, isAdmin);
                } else {
                    // No matching user found
                    return null;
                }
            }
        }
    }
    
     /**
     * Updates user details in the user database.
     *
     * @param userDetails The UserDetails object containing the updated details.
     * @return true if the update is successful, false otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public static boolean updateUserDetails(UserDetails userDetails) throws SQLException {
        // SQL query to update user details by ID
        String query = "UPDATE USER_DETAILS SET name=?, surname=?, username=?, password=? WHERE id=?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Set parameters for the UPDATE statement
            stmt.setString(1, userDetails.getName());
            stmt.setString(2, userDetails.getSurname());
            stmt.setString(3, userDetails.getUsername());
            stmt.setString(4, userDetails.getPassword());
            stmt.setInt(5, userDetails.getId());

            // Execute the update
            int rowsUpdated = stmt.executeUpdate();

            // Check if the update was successful (at least one row updated)
            return rowsUpdated > 0;
        }
    }

    /**
     * Retrieves a list of non-admin users from the user database.
     *
     * @return List of UserDetails objects representing non-admin users.
     * @throws SQLException if a database access error occurs.
     */
    public static List<UserDetails> getNonAdminUsers() throws SQLException {
        // SQL query to retrieve non-admin users
        String query = "SELECT * FROM USER_DETAILS WHERE admin = ?";

        // List to store non-admin users
        List<UserDetails> nonAdminUsers = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Set parameter for the WHERE clause
            stmt.setBoolean(1, false);

            // Execute the query
            try (ResultSet resultSet = stmt.executeQuery()) {
                // Iterate through the result set
                
                UserDetails userDetails = null;
                
                while (resultSet.next()) {
                    // Retrieve user details from the result set
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String surname = resultSet.getString("surname");
                    String username = resultSet.getString("username");

                    // Create UserDetails object for the non-admin user
                    userDetails = new UserDetails(id, name, surname, username, null, false);

                    // Add the user to the list
                    nonAdminUsers.add(userDetails);
                }
            }
        }

        // Return the list of non-admin users
        return nonAdminUsers;
    }
    
    /**
     * Deletes a user from the user database based on the user's ID.
     *
     * @param userId The ID of the user to be deleted.
     * @return true if the deletion is successful, false otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public static boolean deleteUserById(int userId) throws SQLException {
        // SQL query to delete a user by ID
        String query = "DELETE FROM USER_DETAILS WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Set parameter for the WHERE clause
            stmt.setInt(1, userId);

            // Execute the delete operation
            int rowsDeleted = stmt.executeUpdate();

            // Check if the deletion was successful (at least one row deleted)
            return rowsDeleted > 0;
        }
    }
    
     /**
     * Creates the USER_OPERATIONS table with a foreign key reference to USER_DETAILS.
     *
     * @return true if the table creation is successful, false otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public static boolean createUserOperationsTable() throws SQLException {
        // SQL query to create USER_OPERATIONS table with user_operation as CLOB
        String query = "CREATE TABLE USER_OPERATIONS ("
                + "user_operation_id INT PRIMARY KEY,"
                + "user_operation CLOB,"
                + "user_detail_id INT,"
                + "FOREIGN KEY (user_detail_id) REFERENCES USER_DETAILS(id)"
                + ")";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Execute the table creation
            int rowsCreated = stmt.executeUpdate();

            // Check if the table creation was successful (zero or more rows created)
            return rowsCreated >= 0;
        }
    }
    
     /**
     * Retrieves and fills a list of UserOperationDTO from the USER_OPERATIONS table.
     *
     * @return List of UserOperationDTO.
     * @throws SQLException if a database access error occurs.
     */
    public static List<UserOperationDTO> listUserOperations() throws SQLException {
        List<UserOperationDTO> userOperationsList = new ArrayList<>();

        // SQL query to select data from USER_OPERATIONS table
       String query = "SELECT\n" +
                        "    id AS user_operation_id,\n" +
                        "    user_detail_id,\n" +
                        "    name,\n" +
                        "    surname,\n" +
                        "    user_operation\n" +
                        "FROM\n" +
                        "    USER_OPERATIONS, USER_DETAILS\n" +
                        "WHERE\n" +
                        "    user_detail_id = id";

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                // Retrieve data from the result set
                int id = resultSet.getInt("user_operation_id");
                int userDetailId = resultSet.getInt("user_detail_id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String userOperation = resultSet.getString("user_operation");

                // Create UserOperationDTO and add it to the list
                UserOperationDTO userOperationDTO = new UserOperationDTO(id, userDetailId, name, surname, userOperation);
                userOperationsList.add(userOperationDTO);
            }
        }

        return userOperationsList;
    }
    
     /**
     * Adds a user operation to the USER_OPERATIONS table.
     *
     * @param userOperation UserOperationDTO containing the details of the user operation.
     * @return true if the addition is successful, false otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public static boolean addUserOperation(UserOperations userOperation) throws SQLException {
        // SQL query to insert data into USER_OPERATIONS table
        String query = "INSERT INTO USER_OPERATIONS (user_operation_id, user_detail_id, user_operation) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Set parameters for the INSERT statement
            stmt.setInt(1, userOperation.getId());  // Assuming user_operation_id is auto-incremented
            stmt.setInt(2, userOperation.getUserDetailsId());
            stmt.setString(3, userOperation.getUserOperation());

            // Execute the insert operation
            int rowsInserted = stmt.executeUpdate();

            // Check if the insertion was successful (one row inserted)
            return rowsInserted > 0;
        }
    }

}