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
    String diffPr2Pr1;

    @Column
    String diffPr2Re1;

    @Column
    String diffRe2Re1;

    @Column
    String period;


    @Column
    int numOfLetters;




    public Data(){

    }

    public Data(UUID id, String diffPr2Pr1, String diffPr2Re1, String diffRe2Re1, String period, int numOfLetters) {
        this.id = id;
        this.diffPr2Pr1 = diffPr2Pr1;
        this.diffPr2Re1 = diffPr2Re1;
        this.diffRe2Re1 = diffRe2Re1;
        this.period = period;
        this.numOfLetters = numOfLetters;
    }

    public UUID getId() {
        return id;
    }

    public String getDiffPr2Pr1() {
        return diffPr2Pr1;
    }

    public String getDiffPr2Re1() {
        return diffPr2Re1;
    }

    public String getDiffRe2Re1() {
        return diffRe2Re1;
    }

    public String getPeriod() {
        return period;
    }

    public int getNumOfLetters() {
        return numOfLetters;
    }
}
