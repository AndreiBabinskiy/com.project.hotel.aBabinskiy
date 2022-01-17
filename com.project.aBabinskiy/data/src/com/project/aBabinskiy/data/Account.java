package com.project.aBabinskiy.data;

import java.time.LocalDate;
import java.util.Objects;

public class Account {

    private  Integer id;

    private String login;

    private String password;

    private boolean male;

    private Integer citizenshipId;

    private String citizenshipName;

    private LocalDate arrivalDate;

    private LocalDate dateOfDeparture;

    private boolean oneRoom;

    private boolean twoRoom;

    private boolean threeRoom;

    private boolean usualComfort;

    private boolean juniorSuiteComfort;

    private boolean luxComfort;

    private boolean reservation;

    private String wishes;

    public Account() {
    }

    public Account(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isMale() {
        return male;
    }

    public String getMaleTitle() {
        if (male) {
            return "Male";
        } else {
            return "Female";
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public Integer getCitizenshipId() {
        return citizenshipId;
    }

    public void setCitizenshipId(Integer citizenshipId) {
        this.citizenshipId = citizenshipId;
    }

    public String getCitizenshipName() {
        return citizenshipName;
    }

    public void setCitizenshipName(String citizenshipName) {
        this.citizenshipName = citizenshipName;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public LocalDate getDateOfDeparture() {
        return dateOfDeparture;
    }

    public void setDateOfDeparture(LocalDate dateOfDeparture) {
        this.dateOfDeparture = dateOfDeparture;
    }

    public boolean isOneRoom() {
        return oneRoom;
    }

    public void setOneRoom(boolean oneRoom) {
        this.oneRoom = oneRoom;
    }

    public boolean isTwoRoom() {
        return twoRoom;
    }

    public void setTwoRoom(boolean twoRoom) {
        this.twoRoom = twoRoom;
    }

    public boolean isThreeRoom() {
        return threeRoom;
    }

    public void setThreeRoom(boolean threeRoom) {
        this.threeRoom = threeRoom;
    }

    public boolean isUsualComfort() {
        return usualComfort;
    }

    public void setUsualComfort(boolean usualComfort) {
        this.usualComfort = usualComfort;
    }

    public boolean isLuxComfort() {
        return luxComfort;
    }

    public void setLuxComfort(boolean luxComfort) {
        this.luxComfort = luxComfort;
    }

    public boolean isJuniorSuiteComfort() {
        return juniorSuiteComfort;
    }

    public void setJuniorSuiteComfort(boolean juniorSuite) {
        this.juniorSuiteComfort = juniorSuite;
    }

    public boolean isReservation() {
        return reservation;
    }

    public void setReservation(boolean reservation) {
        this.reservation = reservation;
    }

    public String getWishes() {
        return wishes;
    }

    public void setWishes(String wishes) {
        this.wishes = wishes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return login.equals(account.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

    @Override
    public String toString() {
        return "Account{" +
                "login='" + login + '\'' +
                '}';
    }
}
