package com.project.test.authenticator.database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.UUID;

/**
 * Created by Ivica on 5.2.2018..
 */
@Table(database = AppDatabase.class)
public class Settings extends BaseModel{



    @PrimaryKey // at least one primary key required
            UUID id;

    @Column (defaultValue = "4")
    int numAllowedErrors;

    @Column (defaultValue = "2")
    int minDataTrainingRecords;

    @Column (defaultValue = "ivicelig@gmail.com")
    String emailAddress;

    @Column (defaultValue = "3.0")
    double standardDeviationMultiplyer;


    public UUID getId() {
        return id;
    }

    public int getNumAllowedErrors() {
        return numAllowedErrors;
    }

    public int getMinDataTrainingRecords() {
        return minDataTrainingRecords;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public double getStandardDeviationMultiplyer() {
        return standardDeviationMultiplyer;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setNumAllowedErrors(int numAllowedErrors) {
        this.numAllowedErrors = numAllowedErrors;
    }

    public void setMinDataTrainingRecords(int minDataTrainingRecords) {
        this.minDataTrainingRecords = minDataTrainingRecords;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setStandardDeviationMultiplyer(double standardDeviationMultiplyer) {
        this.standardDeviationMultiplyer = standardDeviationMultiplyer;
    }

    public Settings(UUID id, int numAllowedErrors, int minDataTrainingRecords, String emailAddress, double standardDeviationMultiplyer) {
        this.id = id;
        this.numAllowedErrors = numAllowedErrors;
        this.minDataTrainingRecords = minDataTrainingRecords;
        this.emailAddress = emailAddress;
        this.standardDeviationMultiplyer = standardDeviationMultiplyer;
    }
    public Settings(){

    }
}