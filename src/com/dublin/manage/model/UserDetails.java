package com.dublin.manage.model;

import com.dublin.manage.db.DBManager;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * Represents user details including basic information and authentication data.
 */
public class UserDetails {

    private int id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private boolean admin;

    // Default constructor
    public UserDetails() {}

    // Constructor with all attributes including hashed password
    public UserDetails(int id, String name, String surname, String username, String password, boolean admin) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    // Constructor without ID (assumes ID will be assigned by the database)
    public UserDetails(String name, String surname, String username, String password, boolean admin) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    // Copy constructor
    public UserDetails(UserDetails userDetails) {
        this.id = userDetails.getId();
        this.name = userDetails.getName();
        this.surname = userDetails.getSurname();
        this.username = userDetails.getUsername();
        this.password = userDetails.getPassword();
        this.admin = userDetails.isAdmin();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }
    
     // Getter and setter methods for all attributes

    /**
     * Sets the username after checking if it already exists in the database.
     *
     * @param username The new username to set.
     * @return True if the username is set successfully, false otherwise.
     */

    public void setUsername(String username) {
        
       this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    
     // Static method to get the hashed password

    /**
     * Gets the hashed version of the given password using SHA-256.
     *
     * @param password The plain text password.
     * @return The hashed password.
     */
    
    public static String getHashPassword(String password){
        return hashPassword(password);
    }
    
      // Method to hash the password using SHA-256

    /**
     * Hashes the given plain text password using SHA-256.
     *
     * @param plainTextPassword The plain text password to hash.
     * @return The hashed password.
     */
    private static String hashPassword(String plainTextPassword) {
        try {
            if(plainTextPassword == null)
                return plainTextPassword;
            
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(plainTextPassword.getBytes());

            // Convert the byte array to a hexadecimal string
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            // Handle the exception or throw a runtime exception
            throw new RuntimeException("Error hashing password.", e);
        }
    }

    // toString, hashCode, and equals methods for object representation and comparison

    @Override
    public String toString() {
        return "UserDetails{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", admin=" + admin +
                '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.id;
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + Objects.hashCode(this.surname);
        hash = 47 * hash + Objects.hashCode(this.username);
        hash = 47 * hash + Objects.hashCode(this.password);
        hash = 47 * hash + (this.admin ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserDetails other = (UserDetails) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.admin != other.admin) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.surname, other.surname)) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        return Objects.equals(this.password, other.password);
    }
    
    

}