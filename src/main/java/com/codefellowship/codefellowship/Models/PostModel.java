package com.codefellowship.codefellowship.Models;


import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class PostModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String body;
    String createdAt = new Timestamp(System.currentTimeMillis()).toString();

    @ManyToOne
    ApplicationUser user;

    public PostModel(String body, ApplicationUser user) {
        this.body = body;
        this.createdAt = createdAt;
        this.user = user;
    }

    public ApplicationUser getOwner() {
        return user;
    }


    public PostModel() {
    }

    public long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }
}
