package com.rotarywebsite.backend.dto;

public class RegisterRequest {
    private String name;
    private String phone;
    private String occupation;
    private String email;
    private String password;

    // Constructors
    public RegisterRequest() {}
    
    public RegisterRequest(String name, String phone, String occupation, String email, String password) {
        this.name = name;
        this.phone = phone;
        this.occupation = occupation;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getOccupation() { return occupation; }
    public void setOccupation(String occupation) { this.occupation = occupation; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}