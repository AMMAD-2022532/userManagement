package com.dublin.manage.model;

import java.util.Objects;

/**
 * Represents user operations, including details like ID, user details ID, and the operation performed.
 */
public class UserOperations {

    private int id;
    private int userDetailsId;
    private String userOperation;

    // Default constructor
    public UserOperations() {}

    // Constructor with user details ID and user operation
    public UserOperations(int userDetailsId, String userOperation) {
        this.userDetailsId = userDetailsId;
        this.userOperation = userOperation;
    }

    // Constructor with ID, user details ID, and user operation
    public UserOperations(int id, int userDetailsId, String userOperation) {
        this.id = id;
        this.userDetailsId = userDetailsId;
        this.userOperation = userOperation;
    }

    // Copy constructor
    public UserOperations(UserOperations userOperations) {
        this.id = userOperations.getId();
        this.userDetailsId = userOperations.getUserDetailsId();
        this.userOperation = userOperations.getUserOperation();
    }

    // Getter and setter methods for all attributes

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserDetailsId() {
        return userDetailsId;
    }

    public void setUserDetailsId(int userDetailsId) {
        this.userDetailsId = userDetailsId;
    }

    public String getUserOperation() {
        return userOperation;
    }

    public void setUserOperation(String userOperation) {
        this.userOperation = userOperation;
    }

    // toString, hashCode, and equals methods for object representation and comparison

    @Override
    public String toString() {
        return "UserOperations{" +
                "id=" + id +
                ", userDetailsId=" + userDetailsId +
                ", userOperation='" + userOperation + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.id;
        hash = 29 * hash + this.userDetailsId;
        hash = 29 * hash + Objects.hashCode(this.userOperation);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UserOperations other = (UserOperations) obj;
        return this.id == other.id &&
                this.userDetailsId == other.userDetailsId &&
                Objects.equals(this.userOperation, other.userOperation);
    }
}
