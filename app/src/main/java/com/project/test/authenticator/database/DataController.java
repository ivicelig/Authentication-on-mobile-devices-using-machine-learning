package com.project.test.authenticator.database;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;
import java.util.UUID;

/**
 * Created by Ivica on 4.2.2018..
 */

public class DataController {

    public DataController(){

    }

    public List<Data> getAllData(){
        List<Data> dataDB = SQLite.select()
                .from(Data.class)

                .queryList();

        return dataDB;
    }
    public List<Data> getDataByLetterNumber(int numLetter){
        List<Data> dataDB = SQLite.select()
                .from(Data.class)
                .where(Data_Table.numOfLetters.is(numLetter))
                .queryList();

        return dataDB;
    }

    public void saveToTable(List<Long>diffPr2Pr1,List<Long>diffPr2re1, List<Long>diffRe2Re1,List<Long>period,int numOfLetters){
        if(diffPr2Pr1.toString() != "[]" && diffPr2re1.toString()!="[]" && diffRe2Re1.toString()!="[]") {

            Data data = new Data(UUID.randomUUID(), diffPr2Pr1.toString(), diffPr2re1.toString(), diffRe2Re1.toString(), period.toString(), numOfLetters);
            data.save();
        }
    }

}
