package com.project.test.authenticator;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.project.test.authenticator.database.Data;

import com.project.test.authenticator.database.DataController;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

public class CKeyboard extends InputMethodService implements KeyboardView.OnKeyboardActionListener{
    private KeyboardView kv;
    private Keyboard keyboard;
    private boolean isCaps = false;

    List<Long> press = new ArrayList<>();
    List<Long> release = new ArrayList<>();
    List<Long> diffPr2Pr1 = new ArrayList<>();
    List<Long> diffPr2re1 = new ArrayList<>();
    List<Long> diffRe2Re1 = new ArrayList<>();
    List<Long> period = new ArrayList<>();

    int numOfLetters = 0;
    int failedAuthentications = 0;
    int numAllowedErrors = 4;
    int minDataTrainingRecords = 2;
    String emailAddress = "ivicelig@gmail.com";

    DataController dataController = new DataController();
    @Override
    public View onCreateInputView() {

        FlowManager.init(this);

        kv = (KeyboardView)getLayoutInflater().inflate(R.layout.keyboard,null);
        keyboard = new Keyboard(this,R.xml.keyboard);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        return kv;

    }

    public CKeyboard() {
    }


    @Override
    public void onPress(int i) {

        if (i != KeyEvent.KEYCODE_D && i != Keyboard.KEYCODE_DONE) {
            Long curretSystemTime = System.currentTimeMillis();
            press.add(curretSystemTime);
            numOfLetters++;
            Log.i("Press event", "Current time: " + Long.toString(curretSystemTime));

        }
        else {
            Log.i("KEYCODE_ON_PRESS", "SPACE OR ENTER");

        }

    }

    @Override
    public void onRelease(int i) {
        if (i != KeyEvent.KEYCODE_D && i != Keyboard.KEYCODE_DONE) {
            Long curretSystemTime = System.currentTimeMillis();
            release.add(curretSystemTime);

            Log.i("Release event", "Current time: " + Long.toString(curretSystemTime));


        }else {
            Log.i("KEYCODE_ON_RELEASE", "SPACE OR ENTER");

            if (numOfLetters >= 2 && numOfLetters <= 21) {
                for (int b = 1;b<numOfLetters;b++ ){
                    diffPr2Pr1.add(press.get(b)-press.get(b-1));
                    diffPr2re1.add(press.get(b)-release.get(b-1));
                    diffRe2Re1.add(release.get(b)-release.get(b-1));

                }
                for (int c = 0;c<numOfLetters;c++){
                    period.add(release.get(c)-press.get(c));
                }
                //Authenticate user
                if (authenticateUser(numOfLetters)) {
                    //Save to database if spacebar or done is pressed and if user is authenticated
                    dataController.saveToTable(diffPr2Pr1, diffPr2re1, diffRe2Re1, period, numOfLetters);
                    failedAuthentications = 0;
                }else {
                    if (++failedAuthentications >= numAllowedErrors){
                        //Send mail with logs
                        sendMail(emailAddress);

                        failedAuthentications = 0;
                    }

                }
            }

            cleanLists();
            numOfLetters = 0;



        }
    }

    @Override
    public void onKey(int i, int[] ints) {


        InputConnection ic = getCurrentInputConnection();
        playClick(i);
        switch (i){
            case  Keyboard.KEYCODE_DELETE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                break;
            case Keyboard.KEYCODE_SHIFT:
                isCaps = !isCaps;
                keyboard.setShifted(isCaps);
                kv.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                kv.closing();
                requestHideSelf(0);
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));

                break;


            default:
                char code = (char)i;
                if(Character.isLetter(code) && isCaps)
                    code = Character.toLowerCase(code);
                ic.commitText(String.valueOf(code),1);

        }
    }

    private void playClick(int i) {

        AudioManager am = (AudioManager)getSystemService(AUDIO_SERVICE);
        switch (i){
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:

            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
                default:
                    am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }

    }

    @Override
    public void onText(CharSequence charSequence) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    private void cleanLists() {
        press.clear();
        release.clear();
        diffPr2Pr1.clear();
        diffPr2re1.clear();
        diffRe2Re1.clear();
        period.clear();

    }

    private boolean authenticateUser(int numOfLetters){
        //Get data from table by number of letters
        List<Data> data = dataController.getDataByLetterNumber(numOfLetters);

        if (data.size()>minDataTrainingRecords){
            List<Long> dataMean = new ArrayList<>(Collections.nCopies(4*numOfLetters-3,0L));



            //Calculate mean of data in data table by columns
            for (Data row:data) {
                List<Long> tranformedData = transFormDataStringInLongArray(row.getDiffPr2Pr1(),row.getDiffPr2Re1(),row.getDiffRe2Re1(),row.getPeriod());
                for (int i = 0;i<tranformedData.size();i++){
                    Long sum = dataMean.get(i)+tranformedData.get(i);
                    dataMean.set(i,sum);
                }
            }
            for (int i = 0;i<dataMean.size();i++){
                dataMean.set(i,dataMean.get(i)/data.size());
            }
            Log.i("DATA_MEAN",dataMean.toString());
            return true;
        }
        //If there isn't any record in table data for some letter number attribute, then return true so that system trains itself
        else return true;


    }
    private void sendMail(String emailAddress){

    }

    private List<Long> transFormDataStringInLongArray(String diffPr2Pr1,String diffPr2Re1,String diffRe2Re1, String period){
        List<Long> longData = new ArrayList<>();

        String listPr2Pr1[] = diffPr2Pr1.replace("[","").replace("]","").split(", ");
        String listPr2Re1[] = diffPr2Re1.replace("[","").replace("]","").split(", ");
        String listRe2Re1[] = diffRe2Re1.replace("[","").replace("]","").split(", ");
        String listPeriod[] = period.replace("[","").replace("]","").split(", ");

        for (String value:listPr2Pr1) {
            Log.i("","");
            longData.add(Long.parseLong(value));
        }
        for (String value:listPr2Re1) {
            longData.add(Long.parseLong(value));
        }
        for (String value:listRe2Re1) {
            longData.add(Long.parseLong(value));
        }
        for (String value:listPeriod) {
            longData.add(Long.parseLong(value));
        }

        return longData;
    }

}



        /*
        List<Data> dataDB = dataController.getDataByLetterNumber(3);

        for (Data a:dataDB
                ) {
            Log.i("DATA_DATABASE_NUMBER", Integer.toString(a.getNumOfLetters()));
            Log.i("DATA_DATABAS_NUMBER",a.getDiffPr2Pr1());
            Log.i("DATA_DATABASE",a.getDiffPr2Re1());
            Log.i("DATA_DATABASE",a.getDiffPr2Pr1());
            Log.i("DATA_DATABASE",a.getPeriod());
        }
        */