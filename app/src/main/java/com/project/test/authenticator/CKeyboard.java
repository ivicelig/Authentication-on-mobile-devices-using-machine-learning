package com.project.test.authenticator;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.Toast;

import com.project.test.authenticator.database.Data;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
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
    List<Long> listData = new ArrayList<>();

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
        if (i != KeyEvent.KEYCODE_D && i != KeyEvent.KEYCODE_C) {
            Long curretSystemTime = System.currentTimeMillis();
            press.add(curretSystemTime);

            Log.i("Press event", "Current time: " + Long.toString(curretSystemTime));



        }
        else {
            Log.i("KEYCODE_ON_PRESS", "SPACE OR ENTER");


        }

    }




    @Override
    public void onRelease(int i) {
        if (i != KeyEvent.KEYCODE_D && i != KeyEvent.KEYCODE_C) {
            Long curretSystemTime = System.currentTimeMillis();
            release.add(curretSystemTime);

            Log.i("Release event", "Current time: " + Long.toString(curretSystemTime));


        }else {
            Log.i("KEYCODE_ON_RELEASE", "SPACE OR ENTER");
            fillDataListWithData();
            Data data = new Data(UUID.randomUUID(), listData.toString(), listData.size());
            data.save();
            cleanLists();
            List<Data> dataDB = SQLite.select()
                    .from(Data.class)

                    .queryList();

            for (Data a:dataDB
                 ) {
                Log.i("DATA_DATABASE_NUMBER", Integer.toString(a.getNumOfLetters()));
                Log.i("DATA_DATABASE", a.getDataArray());
            }

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
    private double[] normalizeData(double[] data){
        return null;
    }
    private void cleanLists() {
        press.clear();
        release.clear();
        diffPr2Pr1.clear();
        diffPr2re1.clear();
        diffRe2Re1.clear();
        period.clear();
        listData.clear();
    }

    private void fillDataListWithData() {
        listData.addAll(press);
        listData.addAll(release);
        listData.addAll(diffPr2Pr1);
        listData.addAll(diffPr2re1);
        listData.addAll(diffRe2Re1);
        listData.addAll(period);
    }
}

