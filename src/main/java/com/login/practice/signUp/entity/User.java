package com.login.practice.signUp.entity;

import com.login.practice.Entity.Address;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Username is required")
    @Size(min = 3,max = 20,message = "Username length must be between 3 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,20}$",message = "Username must contain only letters, numbers, underscores, and hyphens")
    @Column(name = "username")
    private String username;

    @NotNull(message = "Email is required")
    @Size(min = 1, message = "Email is required")
    @Email(message = "Email is not valid", regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    @Column(name = "email")
    private String email;

    @NotNull(message = "Password is required")
    @Size(min = 6, message = "Password Length more than 6 characters")
    @Column(name = "password")
    private String password;

    @NotNull(message = "First Name is required")
    @Size(min = 1, message = "First Name is required")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "Last Name is required")
    @Size(min = 1, message = "Last Name is required")
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,
            CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id" ),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @Column(name = "enabled")
    private int enabled;

    @Column(name = "phone")
    private String phone;

    @Column(name = "job_title")
    private String job_title;

    @Column(name = "website")
    private String website;


    @Column(name = "github")
    private String github;

    @Column(name = "twitter")
    private String twitter;

    @Column(name = "instagram")
    private String instagram;

    @Column(name = "facebook")
    private String facebook;

    @OneToOne(mappedBy = "user",cascade = {CascadeType.ALL})
    private Token token;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "smtp_config_id") // Define a join column
    private SmtpConfig smtpConfig;

    @OneToOne(mappedBy = "user",cascade = {CascadeType.ALL})
    private Address address;



    // Constructors, getters, and setters

    // Default constructor
    public User(){

    }


    public User(Long id, String username, String email, String password,
                String firstName, String lastName, Date createdAt, int enabled,
                String phone, String job_title, String website,
                String github, String twitter, String instagram, String facebook) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
        this.enabled = enabled;
        this.phone = phone;
        this.job_title = job_title;
        this.website = website;
        this.github = github;
        this.twitter = twitter;
        this.instagram = instagram;
        this.facebook = facebook;
    }

    public SmtpConfig getSmtpConfig() {
        return smtpConfig;
    }

    public void setSmtpConfig(SmtpConfig smtpConfig) {
        this.smtpConfig = smtpConfig;
    }

    public int getEnabled() {
        return enabled;
    }


    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", createdAt=" + createdAt +
                ", enabled=" + enabled +
                ", phone='" + phone + '\'' +
                ", job_title='" + job_title + '\'' +
                ", website='" + website + '\'' +
                ", github='" + github + '\'' +
                ", twitter='" + twitter + '\'' +
                ", instagram='" + instagram + '\'' +
                ", facebook='" + facebook + '\'' +
                '}';
    }
}
