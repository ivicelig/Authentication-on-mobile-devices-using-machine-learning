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

import java.util.Vector;

public class CKeyboard extends InputMethodService implements KeyboardView.OnKeyboardActionListener{
    private KeyboardView kv;
    private Keyboard keyboard;
    private boolean isCaps = false;

    Vector<Long> press = new Vector<>();
    Vector<Long> release = new Vector<>();
    Vector<Long> diffPr2Pr1 = new Vector<>();
    Vector<Long> diffPr2re1 = new Vector<>();
    Vector<Long> diffRe2Re1 = new Vector<>();
    Vector<Long> period = new Vector<>();

    @Override
    public View onCreateInputView() {
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
        Long curretSystemTime = System.currentTimeMillis();
        press.add(curretSystemTime);
        Log.i("Press event","Current time: "+Long.toString(curretSystemTime));
    }


    @Override
    public void onRelease(int i) {
        Long curretSystemTime = System.currentTimeMillis();
        release.add(curretSystemTime);
        Log.i("Release event","Current time: "+Long.toString(curretSystemTime));
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
}
