package com.project.aBabinskiy.server.dateBase;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Objects;

public class DataBaseTable {

    private String tableName;

    private String xmlTableFilePath;

    private Integer maxId;

    private Element maxIdTag;

    private Document dbTableDocument;

    public DataBaseTable(String tableName,
                   String xmlTableFilePath,
                   Integer maxId,
                   Element maxIdTag,
                   Document dbTableDocument) {
        this.tableName = tableName;
        this.xmlTableFilePath = xmlTableFilePath;
        this.maxId = maxId;
        this.maxIdTag = maxIdTag;
        this.dbTableDocument = dbTableDocument;
    }

    public String getTableName() {
        return tableName;
    }

    public String getXmlTableFilePath() {
        return xmlTableFilePath;
    }

    public Integer getMaxId() {
        maxId++;
        return maxId;
    }

    public Element getMaxIdTag() {
        return maxIdTag;
    }

    public Document getDbTableDocument() {
        return dbTableDocument;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataBaseTable dbTable = (DataBaseTable) o;
        return Objects.equals(tableName, dbTable.tableName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableName);
    }
}
