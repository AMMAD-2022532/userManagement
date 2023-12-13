package com.dublin.manage.dto;

import java.util.Objects;

/**
 * Represents a Data Transfer Object (DTO) for user operations.
 * This class encapsulates information related to a user's operation.
 */
public class UserOperationDTO {

    // Fields representing the properties of a UserOperationDTO
    private int id;    
    private int userDetailId;
    private String name;
    private String surname;
    private String userOperation;
    
    // Default constructor
    public UserOperationDTO(){}

    /**
     * Parameterized constructor to create a UserOperationDTO without an ID.
     * @param userDetailId The ID of the user related to this operation.
     * @param name The name of the user.
     * @param surname The surname of the user.
     * @param userOperation The description of the user operation.
     */
    public UserOperationDTO(int userDetailId, String name, String surname, String userOperation) {
        this.userDetailId = userDetailId;
        this.name = name;
        this.surname = surname;
        this.userOperation = userOperation;
    }
    
    /**
     * Parameterized constructor to create a UserOperationDTO with an ID.
     * @param id The unique identifier for this user operation.
     * @param userDetailId The ID of the user related to this operation.
     * @param name The name of the user.
     * @param surname The surname of the user.
     * @param userOperation The description of the user operation.
     */
    public UserOperationDTO(int id, int userDetailId, String name, String surname, String userOperation) {
        this.id = id;
        this.userDetailId = userDetailId;
        this.name = name;
        this.surname = surname;
        this.userOperation = userOperation;
    }
    
    /**
     * Copy constructor to create a UserOperationDTO from an existing one.
     * @param userOperationDTO The UserOperationDTO to copy.
     */
    public UserOperationDTO(UserOperationDTO userOperationDTO){
        this.id = userOperationDTO.getId();
        this.userDetailId = userOperationDTO.getUserDetailId();
        this.name = userOperationDTO.getName();
        this.surname = userOperationDTO.getSurname();
        this.userOperation = userOperationDTO.getUserOperation();
    }

    // Getter and setter methods for each field

    /**
     * Gets the unique identifier of the user operation.
     * @return The unique identifier.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the user operation.
     * @param id The unique identifier to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the ID of the user related to this operation.
     * @return The user detail ID.
     */
    public int getUserDetailId() {
        return userDetailId;
    }

    /**
     * Sets the ID of the user related to this operation.
     * @param userDetailId The user detail ID to set.
     */
    public void setUserDetailId(int userDetailId) {
        this.userDetailId = userDetailId;
    }

    /**
     * Gets the name of the user.
     * @return The name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     * @param name The name of the user to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the surname of the user.
     * @return The surname of the user.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of the user.
     * @param surname The surname of the user to set.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the description of the user operation.
     * @return The user operation description.
     */
    public String getUserOperation() {
        return userOperation;
    }

    /**
     * Sets the description of the user operation.
     * @param userOperation The user operation description to set.
     */
    public void setUserOperation(String userOperation) {
        this.userOperation = userOperation;
    }

    /**
     * Returns a string representation of the UserOperationDTO object.
     * @return A string representation.
     */
    @Override
    public String toString() {
        return "UserOperationDTO{" + "id=" + id + ", userDetailId=" + userDetailId + ", name=" + name + ", surname=" + surname + ", userOperation=" + userOperation + '}';
    }

    /**
     * Computes a hash code for the UserOperationDTO object.
     * @return A hash code value.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + this.id;
        hash = 61 * hash + this.userDetailId;
        hash = 61 * hash + Objects.hashCode(this.name);
        hash = 61 * hash + Objects.hashCode(this.surname);
        hash = 61 * hash + Objects.hashCode(this.userOperation);
        return hash;
    }

    /**
     * Indicates whether some other object is equal to this one.
     * @param obj The object to compare.
     * @return True if the objects are equal, false otherwise.
     */
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
        final UserOperationDTO other = (UserOperationDTO) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.userDetailId != other.userDetailId) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.surname, other.surname)) {
            return false;
        }
        return Objects.equals(this.userOperation, other.userOperation);
    }
}
