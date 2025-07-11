package com.hotel.staff_service.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
    private String department;
    private String contact;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	@Override
	public String toString() {
		return "Staff [id=" + id + ", name=" + name + ", code=" + code + ", department=" + department + ", contact="
				+ contact + "]";
	}
	public Staff() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Staff(Long id, String name, String code, String department, String contact) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.department = department;
		this.contact = contact;
	}
	
    
    
}

