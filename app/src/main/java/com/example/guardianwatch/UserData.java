package com.example.guardianwatch;

public class UserData {
    private static UserData instance;
    private String userId;
    private String password;

    private UserData() {}

    public static UserData getInstance() {
        if (instance == null) {
            instance = new UserData();
        }
        return instance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static void setInstance(UserData instance) {
        UserData.instance = instance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserData(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
