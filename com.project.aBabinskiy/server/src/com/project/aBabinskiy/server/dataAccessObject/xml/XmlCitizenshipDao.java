package com.project.aBabinskiy.server.dataAccessObject.xml;

import com.project.aBabinskiy.data.Citizenship;
import com.project.aBabinskiy.server.dataAccessObject.CitizenshipDao;
import com.project.aBabinskiy.server.dateBase.DataBase;
import com.project.aBabinskiy.server.dateBase.DataBaseTable;
import com.project.aBabinskiy.server.dateBase.DomUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class XmlCitizenshipDao implements CitizenshipDao {
    @Override
    public List<Citizenship> loadAllCitizenships() {

        DataBase dataBase = DataBase.getDataBase();
        DataBaseTable citizenshipsTable = dataBase.getDbTable("citizenships");
        Document citizenshipsDocument = citizenshipsTable.getDbTableDocument();

        List<Citizenship> allCitizenships = new ArrayList<>();
        NodeList citizenshipNodeList = citizenshipsDocument.getElementsByTagName("citizenship");
        for (int i = 0; i < citizenshipNodeList.getLength(); i++) {
            Element countryTag = (Element) citizenshipNodeList.item(i);

            Integer id = DomUtils.getChildTagIntegerData(countryTag, "id");
            String name = DomUtils.getChildTagData(countryTag, "name");
            String code = DomUtils.getChildTagData(countryTag, "code");

            Citizenship citizenship = new Citizenship(id, name, code);
            allCitizenships.add(citizenship);
        }
        return allCitizenships;
    }

    @Override
    public Citizenship loadCitizenshipById(Integer citizenshipId) {

        List<Citizenship> allCitizenships = loadAllCitizenships();
        for (Citizenship citizenship : allCitizenships) {
            if (citizenship.getId().equals(citizenshipId)) {
                return citizenship;
            }
        }
        return null;
    }

    @Override
    public Integer saveCitizenship(Citizenship citizenship) {
        DataBase dataBase = DataBase.getDataBase();
        DataBaseTable citizenshipsTable = dataBase.getDbTable("citizenships");
        Document document = citizenshipsTable.getDbTableDocument();

        Element rootElement = document.getDocumentElement();
        Element countryTag = document.createElement("citizenship");
        rootElement.appendChild(countryTag);

        Integer nextId = dataBase.getNextId("citizenships");
        String idStr = nextId.toString();
        DomUtils.appendTagToParentTag(document, "id", idStr, countryTag);
        DomUtils.appendTagToParentTag(document, "name", citizenship.getName(), countryTag);
        DomUtils.appendTagToParentTag(document, "code", citizenship.getCode(), countryTag);

        DomUtils.saveDocument(document, citizenshipsTable.getXmlTableFilePath());

        return nextId;
    }

    @Override
    public void updateCitizenship(Citizenship citizenship) {

        DataBase dataBase = DataBase.getDataBase();
        DataBaseTable citizenshipsTable = dataBase.getDbTable("citizenships");
        Document document = citizenshipsTable.getDbTableDocument();

        NodeList citizenshipNodeList = document.getElementsByTagName("citizenship");
        for (int i = 0; i < citizenshipNodeList.getLength(); i++) {
            Element citizenshipTag = (Element) citizenshipNodeList.item(i);
            Integer id = DomUtils.getChildTagIntegerData(citizenshipTag, "id");
            if (id.equals(citizenship.getId())) {
                Element nameTag = (Element) citizenshipTag.getElementsByTagName("name").item(0);
                nameTag.setTextContent(citizenship.getName());

                Element codeTag = (Element) citizenshipTag.getElementsByTagName("code").item(0);
                codeTag.setTextContent(citizenship.getCode());
                DomUtils.saveDocument(document, citizenshipsTable.getXmlTableFilePath());

                return;
            }
        }
    }

    @Override
    public void deleteCitizenship(Integer citizenshipId) {

        DataBase dataBase = DataBase.getDataBase();
        DataBaseTable citizenshipsTable = dataBase.getDbTable("citizenships");
        Document document = citizenshipsTable.getDbTableDocument();
        Element allCitizenshipsTag = document.getDocumentElement();

        NodeList citizenshipNodeList = allCitizenshipsTag.getElementsByTagName("citizenship");
        for (int i = 0; i < citizenshipNodeList.getLength(); i++) {
            Element citizenshipTag = (Element) citizenshipNodeList.item(i);
            Integer id = DomUtils.getChildTagIntegerData(citizenshipTag, "id");
            if (id.equals(citizenshipId)) {
                allCitizenshipsTag.removeChild(citizenshipTag);
                DomUtils.saveDocument(document, citizenshipsTable.getXmlTableFilePath());

                return;
            }
        }
    }
}
