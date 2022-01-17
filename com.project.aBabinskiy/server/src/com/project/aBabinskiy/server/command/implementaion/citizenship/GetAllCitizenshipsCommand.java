package com.project.aBabinskiy.server.command.implementaion.citizenship;

import com.project.aBabinskiy.data.Citizenship;
import com.project.aBabinskiy.server.command.ServerCommand;
import com.project.aBabinskiy.server.dataAccessObject.CitizenshipDao;
import com.project.aBabinskiy.server.dataAccessObject.xml.XmlCitizenshipDao;
import org.json.JSONObject;
import org.json.JSONWriter;

import java.util.List;

public class GetAllCitizenshipsCommand implements ServerCommand {
    @Override
    public void process(JSONObject requestData, JSONWriter jsonWriter) {
        CitizenshipDao citizenshipDao = new XmlCitizenshipDao();

        List<Citizenship> allCitizenships = citizenshipDao.loadAllCitizenships();

        jsonWriter.key("response-data").array();

        for (Citizenship citizenship : allCitizenships) {
            jsonWriter.object();
            jsonWriter.key("id").value(citizenship.getId());
            jsonWriter.key("name").value(citizenship.getName());
            jsonWriter.key("code").value(citizenship.getCode());
            jsonWriter.endObject();
        }

        jsonWriter.endArray();
    }
}
