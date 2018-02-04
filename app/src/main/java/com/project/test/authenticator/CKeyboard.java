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
    int numOfLetters = 0;


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

            if (numOfLetters >= 2) {
                for (int b = 1;b<numOfLetters;b++ ){
                    diffPr2Pr1.add(press.get(b)-press.get(b-1));
                    diffPr2re1.add(press.get(b)-release.get(b-1));
                    diffRe2Re1.add(release.get(b)-release.get(b-1));
                    period.add(release.get(b-1)-press.get(b-1));
                }
                //Save to database if spacebar or done is pressed
                Data data = new Data(UUID.randomUUID(), diffPr2Pr1.toString(), diffPr2re1.toString(), diffPr2Pr1.toString(), period.toString(), press.size());
                data.save();
            }

            cleanLists();
            numOfLetters = 0;

            //Logs
            List<Data> dataDB = SQLite.select()
                    .from(Data.class)

                    .queryList();

            for (Data a:dataDB
                 ) {
                Log.i("DATA_DATABASE_NUMBER", Integer.toString(a.getNumOfLetters()));
                Log.i("DATA_DATABAS_NUMBER",a.getDiffPr2Pr1());
                Log.i("DATA_DATABASE",a.getDiffPr2Re1());
                Log.i("DATA_DATABASE",a.getDiffPr2Pr1());
                Log.i("DATA_DATABASE",a.getPeriod());
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

    }


        }

