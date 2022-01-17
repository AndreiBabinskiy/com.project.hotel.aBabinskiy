package com.project.aBabinskiy.server.dataAccessObject;

import com.project.aBabinskiy.data.Citizenship;

import java.util.List;

public interface CitizenshipDao {

    List<Citizenship> loadAllCitizenships();

    Citizenship loadCitizenshipById(Integer citizenshipId);

    Integer saveCitizenship(Citizenship citizenship);

    void updateCitizenship(Citizenship citizenship);

    void deleteCitizenship(Integer citizenshipId);
}
