package com.project.test.authenticator.database;

import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
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

        Log.i("DATABASE",Integer.toString(dataDB.size()));
        List<Data> last10Elements = new ArrayList<>();
        if (dataDB.size()<10){
            return dataDB;
        }else{
            for (int i = dataDB.size()-1; i > dataDB.size()  - 10; i--) {
                Log.i("FOR",dataDB.get(i-1).toString());
                last10Elements.add(dataDB.get(i-1));
            }
            Log.i("DATABASE_",Integer.toString(last10Elements.size()));
            return last10Elements;
        }

}

    public void saveToTable(List<Long>diffPr2Pr1,List<Long>diffPr2re1, List<Long>diffRe2Re1,List<Long>period,int numOfLetters){
        if(diffPr2Pr1.toString() != "[]" && diffPr2re1.toString()!="[]" && diffRe2Re1.toString()!="[]") {

            Data data = new Data(UUID.randomUUID(), diffPr2Pr1.toString(), diffPr2re1.toString(), diffRe2Re1.toString(), period.toString(), numOfLetters);
            data.save();
        }
    }

}
