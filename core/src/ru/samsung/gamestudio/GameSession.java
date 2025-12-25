package ru.samsung.gamestudio;

import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

public class GameSession {

    long t1 = TimeUtils.millis();
    public GameState state;
    private int score;
    int destructedTrashNumber;

    long nextTrashSpawnTime;
    long sessionStartTime, sessionStopTime;

    public GameSession() {

    }

    public void destructionRegistration() {
        destructedTrashNumber += 1;
    }

    public void updateScore() {
        score = (int) (TimeUtils.millis() - sessionStartTime) / 100 + destructedTrashNumber * 100;
    }public int disScore() {
        return score =0;
    }

    public int getScore() {
        return score;
    }

    public void endGame() {
        updateScore();
        state = GameState.ENDED;
        ArrayList<Integer> recordsTable = MemoryManager.loadRecordsTable();

        if (recordsTable == null) {
            recordsTable = new ArrayList<>();
        }
        int foundIdx = 0;
        for (; foundIdx < recordsTable.size(); foundIdx++) {
            if (recordsTable.get(foundIdx) < getScore()) break;
        }
        recordsTable.add(foundIdx, getScore());
        MemoryManager.saveTableOfRecords(recordsTable);

    }

    public void startGame() {
        state = GameState.PLAYING;
        sessionStartTime = TimeUtils.millis();
        nextTrashSpawnTime = sessionStartTime + (long) (GameSetting.STARTING_MYSOR_APPEARANCE_COOL_DOWN
                * getTrashPeriodCoolDown());

    }

    public boolean shouldSpawnMysor() {
        if (nextTrashSpawnTime <= TimeUtils.millis()) {
            nextTrashSpawnTime = TimeUtils.millis() + (long) (GameSetting.STARTING_MYSOR_APPEARANCE_COOL_DOWN
                    * getTrashPeriodCoolDown());
            return true;
        }
        return false;
    }

    private float getTrashPeriodCoolDown() {
        return (float) Math.exp(-0.001 * (TimeUtils.millis() - sessionStartTime + 1) / 1000);
    }

    public void pauseGame() {
        sessionStopTime = TimeUtils.millis();
        state = GameState.PAUSED;

    }

    public void resumeGame() {
        state = GameState.PLAYING;

        sessionStartTime += TimeUtils.millis() - sessionStopTime;
    }

    long t2 = TimeUtils.millis();
    long deltaT = t2 - t1;
}
