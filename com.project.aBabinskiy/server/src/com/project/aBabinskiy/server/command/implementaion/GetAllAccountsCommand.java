package com.project.aBabinskiy.server.command.implementaion;

import com.project.aBabinskiy.data.Account;
import com.project.aBabinskiy.data.Citizenship;
import com.project.aBabinskiy.server.command.ServerCommand;
import com.project.aBabinskiy.server.dataAccessObject.CitizenshipDao;
import com.project.aBabinskiy.server.dataAccessObject.xml.XmlAccountDao;
import com.project.aBabinskiy.server.dataAccessObject.xml.XmlCitizenshipDao;
import org.json.JSONObject;
import org.json.JSONWriter;

import java.util.List;

public class GetAllAccountsCommand implements ServerCommand {
    @Override
    public void process(JSONObject requestData, JSONWriter jsonWriter) {

        XmlAccountDao accountDao = new XmlAccountDao();
        CitizenshipDao citizenshipDao = new XmlCitizenshipDao();

        List<Account> allAccounts =  accountDao.loadAllAccounts();
        for (Account account : allAccounts) {
            Integer citizenshipId = account.getCitizenshipId();
            Citizenship citizenship = citizenshipDao.loadCitizenshipById(citizenshipId);
            String citizenshipName = citizenship.getName();
            account.setCitizenshipName(citizenshipName);
        }

        jsonWriter.key("response-data").array();

        for (Account account : allAccounts) {
            jsonWriter.object();
                jsonWriter.key("login").value(account.getLogin());
                jsonWriter.key("password").value(account.getPassword());
                jsonWriter.key("reservation").value(account.isReservation());
                jsonWriter.key("oneRoom").value(account.isOneRoom());
                jsonWriter.key("twoRoom").value(account.isTwoRoom());
                jsonWriter.key("threeRoom").value(account.isThreeRoom());
                jsonWriter.key("usualComfort").value(account.isUsualComfort());
                jsonWriter.key("juniorSuiteComfort").value(account.isJuniorSuiteComfort());
                jsonWriter.key("luxComfort").value(account.isLuxComfort());
                jsonWriter.key("arrivalDate").value(account.getArrivalDate());
                jsonWriter.key("dateOfDeparture").value(account.getDateOfDeparture());
                jsonWriter.key("citizenship-id").value(account.getCitizenshipId());
                jsonWriter.key("citizenship-name").value(account.getCitizenshipName());
                jsonWriter.key("male").value((account.isMale()));
                jsonWriter.key("wishes").value(account.getWishes());
            jsonWriter.endObject();
        }

        jsonWriter.endArray();
    }
}
