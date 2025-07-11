package com.hotel.reserv_service.dto;



public class Guest {
    private Long id;
    private String name;
    private String email;

    public Guest() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}

