package com.esgi.ecole.bestway.models;

/**
 * Created by mohsen raeisi on 01/11/2016.
 */
public class Session {
   private String userId;
   private String token;

    public Session() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
