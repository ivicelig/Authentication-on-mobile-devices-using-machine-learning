package com.project.test.authenticator.database;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;
import java.util.UUID;

/**
 * Created by Ivica on 5.2.2018..
 */

public class SettingsController {
    public List<Settings> getAllData(){
        List<Settings> dataDB = SQLite.select()
                .from(Settings.class)

                .queryList();

        return dataDB;
    }
    public void saveToTable(int numOfAllowedErrors,int minDataTrainingRecords,String email, double standardDeviationMultyplier){

            Settings settings = new Settings(UUID.randomUUID(),numOfAllowedErrors, minDataTrainingRecords,email, standardDeviationMultyplier);
            settings.save();
        }

    public void updateTable(int numOfAllowedErrors,int minDataTrainingRecords,String email, double standardDeviationMultyplier){
        List<Settings> settings = getAllData();
        SQLite.update(Settings.class)
                .set(Settings_Table.numAllowedErrors.eq(numOfAllowedErrors))
                .where(Settings_Table.numAllowedErrors.is(settings.get(0).getNumAllowedErrors()))
                .async()
                .execute();
        SQLite.update(Settings.class)
                .set(Settings_Table.minDataTrainingRecords.eq(minDataTrainingRecords))
                .where(Settings_Table.minDataTrainingRecords.is(settings.get(0).getMinDataTrainingRecords()))
                .async()
                .execute();
        SQLite.update(Settings.class)
                .set(Settings_Table.emailAddress.eq(email))
                .where(Settings_Table.emailAddress.is(settings.get(0).getEmailAddress()))
                .async()
                .execute();
        SQLite.update(Settings.class)
                .set(Settings_Table.standardDeviationMultiplyer.eq(standardDeviationMultyplier))
                .where(Settings_Table.standardDeviationMultiplyer.is(settings.get(0).getStandardDeviationMultiplyer()))
                .async()
                .execute();

    }

}
