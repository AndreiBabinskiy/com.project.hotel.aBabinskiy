package com.project.aBabinskiy.server.dateBase;

import com.project.aBabinskiy.data.Account;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBase {

    private static final String DB_STORADGE_PATH = "d:/JSE/hotel-db/";

    private static final String DB_METADATA_FILE = "d:/JSE/hotel-db/xml-db-tables.xml";

    private static final DataBase dataBase;

    static {
        try {
            dataBase = new DataBase();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Document dbTablesDocument;

    private Map<String, DataBaseTable> dbTablesMap = new HashMap<>();

    private DataBase() throws Exception {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        dbTablesDocument = dBuilder.parse(DB_METADATA_FILE);

        Element rootElement = dbTablesDocument.getDocumentElement();
        Element allTablesTag = (Element) rootElement.getElementsByTagName("db-tables").item(0);

        NodeList dbTableList = allTablesTag.getElementsByTagName("db-table");
        for (int i = 0; i < dbTableList.getLength(); i++) {
            Element tableTag = (Element) dbTableList.item(i);
            String tableName = tableTag.getAttribute("name");
            String tableFile = tableTag.getAttribute("table-file");
            String tableFilePath = DB_STORADGE_PATH +tableFile;

            Element maxIdTag = (Element) tableTag.getElementsByTagName("max-id-value").item(0);
            String maxIdStr = maxIdTag.getTextContent();
            Integer maxId = Integer.parseInt(maxIdStr);

            DocumentBuilder dbTableDocBuilder = dbFactory.newDocumentBuilder();
            Document dbTableDocument = dbTableDocBuilder.parse(tableFilePath);

            DataBaseTable dbTable = new DataBaseTable(tableName, tableFilePath, maxId, maxIdTag, dbTableDocument);

            dbTablesMap.put(tableName, dbTable);
        }
    }

    public DataBaseTable getDbTable(String tableName) {
        DataBaseTable dbTable = dbTablesMap.get(tableName);
        return dbTable;
    }

    public Integer getNextId(String tableName) {
        DataBaseTable dbTable = dbTablesMap.get(tableName);
        Integer maxId = dbTable.getMaxId();

        try {
            rewriteMaxId(dbTable, maxId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return maxId;
    }

    private void rewriteMaxId(DataBaseTable table, Integer newMaxId) throws TransformerException {
        Element maxIdTag = table.getMaxIdTag();
        maxIdTag.setTextContent(newMaxId.toString());
        DomUtils.saveDocument(dbTablesDocument, DB_METADATA_FILE);
    }

    public static DataBase getDataBase() {
        return dataBase;
    }
}
