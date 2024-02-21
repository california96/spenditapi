package com.cmd.spenditapi.models
import com.fasterxml.jackson.annotation.JsonInclude
@JsonInclude(JsonInclude.Include.NON_EMPTY)

class User {
    private int userID //use this for URL parameters only!
    private String lastName
    private String firstName
    private String userName
    private String email
    private String password
    private String image

    User(String lastName, String firstName, String userName, String email, String password, String image) {
        this.lastName = lastName
        this.firstName = firstName
        this.userName = userName
        this.email = email
        this.password = password
        this.image = image
    }

    User(int userId, String userName, String email, String firstName, String lastName, String image){
        this.userID = userId
        this.userName = userName
        this.email = email
        this.firstName = firstName
        this.lastName = lastName
        this.image = image
    }
    //TODO: for session tracking?
    /*
    public User(int userID, String lastName, String firstName, String userName, String email, String image) {
        this.userID = userID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.userName = userName;
        this.email = email;
        this.image = image;
    }*/

    int getUserID() {
        return userID
    }

    void setUserID(int userID) {
        this.userID = userID
    }

    String getLastName() {
        return lastName
    }

    void setLastName(String lastName) {
        this.lastName = lastName
    }

    String getFirstName() {
        return firstName
    }

    void setFirstName(String firstName) {
        this.firstName = firstName
    }

    String getUserName() {
        return userName
    }

    void setUserName(String userName) {
        this.userName = userName
    }

    String getEmail() {
        return email
    }

    void setEmail(String email) {
        this.email = email
    }

    String getPassword() {
        return password
    }

    void setPassword(String password) {
        this.password = password
    }

    String getImage() {
        return image
    }

    void setImage(String image) {
        this.image = image
    }
}