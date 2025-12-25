package ru.samsung.gamestudio;


import static com.badlogic.gdx.scenes.scene2d.ui.Table.Debug.table;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;


public class MemoryManager {
    private static final Preferences preferences = Gdx.app.getPreferences("User saves");
    public static void saveSoundSetting(boolean isOn){
        preferences.putBoolean("isSoundOn", isOn);
        preferences.flush();
    }
    public static boolean loadIsSound(){
        return preferences.getBoolean("isSoundOn", true);
    }


    public static void saveMusicSetting(boolean isOn){
        preferences.putBoolean("isMusicOn",isOn);
        preferences.flush();
    }



    public static ArrayList<Integer> loadRecordsTable() {

        if (!preferences.contains("recordTable")){
            return null;
        }

        String scores = preferences.getString("recordTable");
        Json json = new Json();
        ArrayList<Integer> table = json.fromJson(ArrayList.class, scores);
        return table;
    }

    public static void saveTableOfRecords(ArrayList<Integer> table) {
        Json json = new Json();
        String tableInString = json.toJson(table);
        preferences.putString("recordTable", tableInString);
        preferences.flush();
    }

    public static boolean loadIsMusic(){
        return preferences.getBoolean("isMusicOn",true);
    }

}
