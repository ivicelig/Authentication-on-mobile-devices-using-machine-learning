package com.project.test.authenticator.database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;
import java.util.UUID;

/**
 * Created by Ivica on 4.2.2018..
 */

@Table(database = AppDatabase.class)
public class Data extends BaseModel{

    @PrimaryKey // at least one primary key required
            UUID id;

    @Column
    String dataArray;

    @Column
    int numOfLetters;


    public Data(UUID id, String dataArray, int numOfLetters) {
        this.id = id;
        this.dataArray = dataArray;
        this.numOfLetters = numOfLetters;
    }


    public Data(){

    }

    public UUID getId() {
        return id;
    }

    public String getDataArray() {
        return dataArray;
    }

    public int getNumOfLetters() {
        return numOfLetters;
    }
}
