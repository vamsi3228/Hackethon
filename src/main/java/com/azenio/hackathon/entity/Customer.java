package com.azenio.hackathon.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @Column(name = "customer_id", nullable = false, length = 50)
    private String customerId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    private Integer age;

    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String city;

    private String state;

    private String country;

    @Column(name = "postal_code")
    private String postalCode;

    private String occupation;

    @Column(name = "annual_income")
    private Double annualIncome;

    @Column(name = "marital_status")
    private String maritalStatus;

    @Column(name = "education_level")
    private String educationLevel;

    @Column(name = "employment_status")
    private String employmentStatus;

    @Column(name = "customer_since")
    private LocalDate customerSince;

    @Column(name = "customer_segment")
    private String customerSegment;

    @Column(name = "kyc_status")
    private String kycStatus;

    @Column(name = "risk_rating")
    private String riskRating;

    @Column(name = "is_politically_exposed")
    private Boolean politicallyExposed;

    @Column(name = "preferred_channel")
    private String preferredChannel;

    @Column(name = "email_verified")
    private String emailVerified;

    @Column(name = "phone_verified")
    private String phoneVerified;

    @Column(name = "num_complaints_last_year")
    private Integer numComplaintsLastYear;

    // Getters and Setters

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Double getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(Double annualIncome) {
        this.annualIncome = annualIncome;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public LocalDate getCustomerSince() {
        return customerSince;
    }

    public void setCustomerSince(LocalDate customerSince) {
        this.customerSince = customerSince;
    }

    public String getCustomerSegment() {
        return customerSegment;
    }

    public void setCustomerSegment(String customerSegment) {
        this.customerSegment = customerSegment;
    }

    public String getKycStatus() {
        return kycStatus;
    }

    public void setKycStatus(String kycStatus) {
        this.kycStatus = kycStatus;
    }

    public String getRiskRating() {
        return riskRating;
    }

    public void setRiskRating(String riskRating) {
        this.riskRating = riskRating;
    }

    public Boolean getPoliticallyExposed() {
        return politicallyExposed;
    }

    public void setPoliticallyExposed(Boolean politicallyExposed) {
        this.politicallyExposed = politicallyExposed;
    }

    public String getPreferredChannel() {
        return preferredChannel;
    }

    public void setPreferredChannel(String preferredChannel) {
        this.preferredChannel = preferredChannel;
    }

    public String getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(String emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(String phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public Integer getNumComplaintsLastYear() {
        return numComplaintsLastYear;
    }

    public void setNumComplaintsLastYear(Integer numComplaintsLastYear) {
        this.numComplaintsLastYear = numComplaintsLastYear;
    }
}
