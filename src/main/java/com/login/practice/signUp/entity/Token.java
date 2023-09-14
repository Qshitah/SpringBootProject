package com.login.practice.signUp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "token")
public class Token  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "token_value")
    private String token;

    @Column(name = "expiration_datetime")
    private LocalDateTime expirationDateTime;

    @Column(name = "tokentype")
    private String toketype;

    @OneToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH,CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    private User user;

    public Token(){

    }

    public Token(String token, LocalDateTime expirationDateTime, String toketype) {
        this.token = token;
        this.expirationDateTime = expirationDateTime;
        this.toketype = toketype;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpirationDateTime() {
        return expirationDateTime;
    }

    public void setExpirationDateTime(LocalDateTime expirationDateTime) {
        this.expirationDateTime = expirationDateTime;
    }

    public String getToketype() {
        return toketype;
    }

    public void setToketype(String toketype) {
        this.toketype = toketype;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", expirationDateTime=" + expirationDateTime +
                ", toketype=" + toketype +
                '}';
    }
}
