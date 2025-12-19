package bencit.com.restaurantmanager.entities;
import bencit.com.restaurantmanager.enums.EmployeeRoles;

public class Employee {
    private Integer id;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String dateOfBirth;
    private EmployeeRoles role;
    private String roleDescription;
    public Employee(){}
    public Employee(Integer id, String name, String lastName, String phoneNumber, String dateOfBirth, EmployeeRoles role, String roleDescription) {
        this.id=id;
        this.name=name;
        this.lastName=lastName;
        this.phoneNumber=phoneNumber;
        this.dateOfBirth=dateOfBirth;
        this.role=role;
        this.roleDescription=roleDescription;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public EmployeeRoles getRole() {
        return role;
    }

    public void setRole(EmployeeRoles role) {
        this.role = role;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
