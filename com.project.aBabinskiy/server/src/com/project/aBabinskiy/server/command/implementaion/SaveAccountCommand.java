package com.project.aBabinskiy.server.command.implementaion;

import com.project.aBabinskiy.data.Account;
import com.project.aBabinskiy.server.command.ServerCommand;
import com.project.aBabinskiy.server.dataAccessObject.xml.XmlAccountDao;
import org.json.JSONObject;
import org.json.JSONWriter;

import java.time.LocalDate;

public class SaveAccountCommand implements ServerCommand {
    @Override
    public void process(JSONObject requestData, JSONWriter jsonWriter) {

        String login = requestData.getString("login");
        String password = requestData.getString("password");
        boolean male = requestData.getBoolean("male");
        boolean reservation = requestData.getBoolean("reservation");
        boolean oneRoom = requestData.getBoolean("oneRoom");
        boolean twoRoom = requestData.getBoolean("twoRoom");
        boolean threeRoom = requestData.getBoolean("threeRoom");
        boolean usualComfort = requestData.getBoolean("usualComfort");
        boolean juniorSuiteComfort = requestData.getBoolean("juniorSuiteComfort");
        boolean luxComfort = requestData.getBoolean("luxComfort");
        String arrivalDateStr = requestData.getString("arrivalDate");
        LocalDate arrivalDate = LocalDate.parse(arrivalDateStr);
        String dateOfDepartureStr = requestData.getString("dateOfDeparture");
        LocalDate dateOfDeparture = LocalDate.parse(dateOfDepartureStr);
        Integer citizenshipId = requestData.getInt("citizenship-id");
        String wishes = requestData.getString("wishes");

        Account account = new Account();
        account.setLogin(login);
        account.setPassword(password);
        account.setMale(male);
        account.setReservation(reservation);
        account.setOneRoom(oneRoom);
        account.setTwoRoom(twoRoom);
        account.setThreeRoom(threeRoom);
        account.setUsualComfort(usualComfort);
        account.setJuniorSuiteComfort(juniorSuiteComfort);
        account.setLuxComfort(luxComfort);
        account.setArrivalDate(arrivalDate);
        account.setDateOfDeparture(dateOfDeparture);
        account.setCitizenshipId(citizenshipId);
        account.setWishes(wishes);

        XmlAccountDao accountDao = new XmlAccountDao();

        try {
            accountDao.saveAccount(account);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
