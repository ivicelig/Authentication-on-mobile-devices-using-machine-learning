package com.project.test.authenticator.tabs;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.test.authenticator.R;
import com.project.test.authenticator.database.Settings;
import com.project.test.authenticator.database.SettingsController;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class tabSettings extends Fragment {
    SettingsController sc = new SettingsController();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab_settings, container, false);
        final EditText minDataTrainingRecord = (EditText)view.findViewById(R.id.minDataTrainingRecords);
        final EditText email_address = (EditText)view.findViewById(R.id.email_address);
        final EditText numAllowedErrors = (EditText)view.findViewById(R.id.numAllowedErrors);
        final EditText standardDeviationMultiplier = (EditText)view.findViewById(R.id.standardDeviationMultiplier);
        final Button update = (Button)view.findViewById(R.id.button_update);

        // Inflate the layout for this fragment
        List<Settings> settings = sc.getAllData();
        minDataTrainingRecord.setText(Integer.toString(settings.get(0).getMinDataTrainingRecords()));
        email_address.setText(settings.get(0).getEmailAddress());
        numAllowedErrors.setText(Integer.toString(settings.get(0).getNumAllowedErrors()));
        standardDeviationMultiplier.setText(Double.toString(settings.get(0).getStandardDeviationMultiplyer()));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int minDataTrainingRecord_update;
                int numAllowedErrors_update;
                String email_address_update;
                double standardDeviationMultiplier_update;

                email_address_update = email_address.getText().toString();
                if (tryParseInt(minDataTrainingRecord.getText().toString()) && tryParseInt(numAllowedErrors.getText().toString()) && tryParseInt(standardDeviationMultiplier.getText().toString())){
                    minDataTrainingRecord_update = Integer.parseInt(minDataTrainingRecord.getText().toString());
                    numAllowedErrors_update = Integer.parseInt(numAllowedErrors.getText().toString());
                    standardDeviationMultiplier_update = Double.parseDouble(standardDeviationMultiplier.getText().toString());
                    sc.updateTable(numAllowedErrors_update,minDataTrainingRecord_update,email_address_update,standardDeviationMultiplier_update);
                }



            }
        });

        return view;
    }

    private boolean tryParseInt(String value){
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private boolean tryParseDouble(String value){
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
