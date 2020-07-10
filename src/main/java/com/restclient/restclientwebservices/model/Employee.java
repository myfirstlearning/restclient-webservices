package com.restclient.restclientwebservices.model;


import java.util.Objects;

public class Employee {

    private long id;
    private String firstName;
    private String lastName;
    private long yearlyIncome;

    public Employee() {
    }

    public Employee(String firstName, String lastName, long yearlyIncome) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearlyIncome = yearlyIncome;
    }

    public Employee(long id, String firstName, String lastName, long yearlyIncome) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearlyIncome = yearlyIncome;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getYearlyIncome() {
        return yearlyIncome;
    }

    public void setYearlyIncome(long yearlyIncome) {
        this.yearlyIncome = yearlyIncome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id &&
                yearlyIncome == employee.yearlyIncome &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(lastName, employee.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, yearlyIncome);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", yearlyIncome=" + yearlyIncome +
                '}';
    }
}
