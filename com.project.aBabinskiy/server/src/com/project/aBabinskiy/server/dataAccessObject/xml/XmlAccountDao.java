package com.project.aBabinskiy.server.dataAccessObject.xml;

import com.project.aBabinskiy.data.Account;
import com.project.aBabinskiy.server.dateBase.DataBase;
import com.project.aBabinskiy.server.dateBase.DataBaseTable;
import com.project.aBabinskiy.server.dateBase.DomUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class XmlAccountDao {

    public List<Account> loadAllAccounts() {
        DataBase dataBase = DataBase.getDataBase();
        DataBaseTable accountsTable = dataBase.getDbTable("accounts");
        Document document = accountsTable.getDbTableDocument();

        Element rootElement = document.getDocumentElement();

        List<Account> allAccounts = new ArrayList<>();
        NodeList accountList = rootElement.getElementsByTagName("account");
        for (int i = 0; i < accountList.getLength(); i++) {
            Element accountTag = (Element) accountList.item(i);

            Integer id = DomUtils.getChildTagIntegerData(accountTag, "id");
            String login = DomUtils.getChildTagData(accountTag, "login");
            String password = DomUtils.getChildTagData(accountTag, "password");
            boolean reservation = DomUtils.getChildTagBooleanData(accountTag, "reservation");
            boolean oneRoom = DomUtils.getChildTagBooleanData(accountTag,"oneRoom");
            boolean twoRoom = DomUtils.getChildTagBooleanData(accountTag,"twoRoom");
            boolean threeRoom = DomUtils.getChildTagBooleanData(accountTag,"threeRoom");
            boolean usualComfort = DomUtils.getChildTagBooleanData(accountTag,"usualComfort");
            boolean juniorSuiteComfort = DomUtils.getChildTagBooleanData(accountTag,"juniorSuiteComfort");
            boolean luxComfort = DomUtils.getChildTagBooleanData(accountTag,"luxComfort");
            LocalDate arrivalDate = DomUtils.getChildTagLocalDateData(accountTag, "arrivalDate");
            LocalDate dateOfDeparture = DomUtils.getChildTagLocalDateData(accountTag, "dateOfDeparture");
            Integer citizenshipId = DomUtils.getChildTagIntegerData(accountTag, "fk_citizenship_id");
            boolean male = DomUtils.getChildTagBooleanData(accountTag, "male");
            String wishes = DomUtils.getChildTagData(accountTag, "wishes");

            Account account = new Account();
            account.setId(id);
            account.setLogin(login);
            account.setPassword(password);
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
            account.setMale(male);
            account.setWishes(wishes);

            allAccounts.add(account);

        }

        return allAccounts;
    }

    public void saveAccount(Account account) throws Exception {
        DataBase dataBase = DataBase.getDataBase();
        DataBaseTable accountsTable = dataBase.getDbTable("accounts");
        Document document = accountsTable.getDbTableDocument();

        Element rootElement = document.getDocumentElement();
        Element accountTag = document.createElement("account");
        rootElement.appendChild(accountTag);

        String idStr = dataBase.getNextId("accounts").toString();
        DomUtils.appendTagToParentTag(document, "id", idStr, accountTag);
        DomUtils.appendTagToParentTag(document, "login", account.getLogin(), accountTag);
        DomUtils.appendTagToParentTag(document, "password", account.getPassword(), accountTag);
        String reservationStr = Boolean.toString(account.isReservation());
        DomUtils.appendTagToParentTag(document, "reservation", reservationStr, accountTag);
        String oneRoomStr = Boolean.toString(account.isOneRoom());
        DomUtils.appendTagToParentTag(document, "oneRoom", oneRoomStr, accountTag);
        String  twoRoomStr = Boolean.toString(account.isTwoRoom());
        DomUtils.appendTagToParentTag(document, "twoRoom", twoRoomStr, accountTag);
        String threeRoomStr = Boolean.toString(account.isThreeRoom());
        DomUtils.appendTagToParentTag(document, "threeRoom", threeRoomStr, accountTag);
        String  usualComfort = Boolean.toString(account.isUsualComfort());
        DomUtils.appendTagToParentTag(document, "usualComfort", usualComfort, accountTag);
        String  juniorSuiteComfortStr = Boolean.toString(account.isJuniorSuiteComfort());
        DomUtils.appendTagToParentTag(document, "juniorSuiteComfort", juniorSuiteComfortStr, accountTag);
        String luxComfortStr = Boolean.toString(account.isLuxComfort());
        DomUtils.appendTagToParentTag(document, "luxComfort", luxComfortStr, accountTag);
        String arrivalDateStr = account.getArrivalDate().toString();
        DomUtils.appendTagToParentTag(document, "arrivalDate", arrivalDateStr, accountTag);
        String dateOfDepartureStr = account.getDateOfDeparture().toString();
        DomUtils.appendTagToParentTag(document, "dateOfDeparture", dateOfDepartureStr, accountTag);
        DomUtils.appendTagToParentTag(document, "fk_citizenship_id", account.getCitizenshipId().toString(), accountTag);
        String maleStr = Boolean.toString(account.isMale());
        DomUtils.appendTagToParentTag(document, "male", maleStr, accountTag);
        DomUtils.appendTagToParentTag(document, "wishes", account.getWishes(), accountTag);

        DomUtils.saveDocument(document, accountsTable.getXmlTableFilePath());
    }

    public static void main(String[] args) throws Exception {
        String login = "login-data";
        String password = "password-data";
        boolean reservation = true;
        boolean oneRoom = true;
        boolean twoRoom = true;
        boolean threeRoom = true;
        boolean usualComfort = true;
        boolean juniorSuiteComfort = true;
        boolean luxComfort = true;
        String arrivalDateStr = "2021-06-21";
        LocalDate arrivalDate = LocalDate.parse(arrivalDateStr);
        String dateOfDepartureStr = "2021-07-24";
        LocalDate dateOfDeparture = LocalDate.parse(dateOfDepartureStr);
        Integer citizenshipId = 1;
        boolean male = true;
        String wishes = "Hi";

        Account account = new Account();
        account.setLogin(login);
        account.setPassword(password);
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
        account.setMale(male);
        account.setWishes(wishes);

        XmlAccountDao accountDao = new XmlAccountDao();

        accountDao.saveAccount(account);
        accountDao.saveAccount(account);
        accountDao.saveAccount(account);

    }
}
