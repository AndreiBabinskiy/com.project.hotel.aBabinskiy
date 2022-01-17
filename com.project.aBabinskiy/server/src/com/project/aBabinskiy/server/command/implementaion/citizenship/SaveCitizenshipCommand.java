package com.project.aBabinskiy.server.command.implementaion.citizenship;

import com.project.aBabinskiy.data.Citizenship;
import com.project.aBabinskiy.server.command.ServerCommand;
import com.project.aBabinskiy.server.dataAccessObject.CitizenshipDao;
import com.project.aBabinskiy.server.dataAccessObject.xml.XmlCitizenshipDao;
import org.json.JSONObject;
import org.json.JSONWriter;

public class SaveCitizenshipCommand implements ServerCommand {

    @Override
    public void process(JSONObject requestData, JSONWriter jsonWriter) {
        String code = requestData.getString("citizenship-code");
        String name = requestData.getString("citizenship-name");

        Citizenship citizenship = new Citizenship(name, code);
        CitizenshipDao citizenshipDao = new XmlCitizenshipDao();
        citizenshipDao.saveCitizenship(citizenship);
    }
}
