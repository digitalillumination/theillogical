package dev.doyeong.theillogical.music;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.databinding.ObservableArrayList;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import dev.doyeong.theillogical.models.SongModel;

public class MusicManager {
    private static ObservableArrayList<Music> musicQueue = new ObservableArrayList<>();
    private static List<OnInformationReceivedListener> listeners = new ArrayList<>();
    private static List<OnTimeUpdateListener> updateListeners = new ArrayList<>();
    private static int position = 0;
    private static boolean isPlaying = false;
    private static SongModel songInfo;
    private static boolean isPrepared;

    public static void startMusic(Context context, Music music) {
        addQueue(context, music);
        stopPlay();
        position = musicQueue.size() - 1;
        startPlay(context);
    }

    public static void addQueue(Context context, Music music) {
        music.getData(context);
        musicQueue.add(music);
    }
    public static void stopPlay() {
        if (!musicQueue.isEmpty()) {
            musicQueue.get(position).stopPlay();
        }
    }
    public static void startPlay(Context context) {
        Music music = musicQueue.get(position);
        songInfo = null;
        isPrepared = false;

        music.createMediaPlayer(context);
        music.getData(context);
        music.setOnInformationReceivedListener(song -> {
            for (OnInformationReceivedListener listener : listeners) {
                listener.onInformationReceived(song);
                songInfo = song;
            }
        });
        music.getMediaPlayer().setOnPreparedListener(player -> {
            isPrepared = true;
            for (OnInformationReceivedListener listener : listeners) {
                listener.onDurationReceived(player.getDuration());
                listener.onIsPlayingChanged(true);
            }

            setIsPlaying(context, true);
        });
        music.getMediaPlayer().setOnCompletionListener(player -> {
            stopPlay();
            if (musicQueue.size() > position + 1) {
                position++;
                startPlay(context);
            }
        });
        music.setOnTimeUpdateListener(time -> {
            for (OnTimeUpdateListener listener : updateListeners) {
                listener.onTimeUpdate(time);
            }
        });
    }
    public static void setOnInformationReceivedListener(OnInformationReceivedListener listener) {
        if (musicQueue.size() > 0 && musicQueue.get(position) != null && musicQueue.get(position).getMediaPlayer() != null) {
            listener.onDurationReceived(musicQueue.get(position).getMediaPlayer().getDuration());
        }
        if (songInfo != null) {
            listener.onInformationReceived(songInfo);
        }
        listener.onIsPlayingChanged(isPlaying);
        listeners.add(listener);
    }
    public static void setOnTimeUpdateListener(OnTimeUpdateListener listener) {
        updateListeners.add(listener);
    }

    @SuppressLint("DefaultLocale")
    public static String getTimeString(int time) {
        int sec = time / 1000;

        return String.format("%02d:%02d", sec / 60, sec % 60);
    }

    public static void setIsPlaying(Context context, boolean isPlaying) {
        if (getCurrentMusic() == null || getCurrentMusic().getMediaPlayer() == null || !isPrepared) {
            MusicManager.startPlay(context);
            return;
        };
        MusicManager.isPlaying = isPlaying;

        if (isPlaying) {
            getCurrentMusic().getMediaPlayer().start();
        } else  {
            getCurrentMusic().getMediaPlayer().pause();
        }

        for (OnInformationReceivedListener listener : listeners) {
            listener.onIsPlayingChanged(isPlaying);
        }
    }
    public static void nextSong(Context context) {
        if (position + 1 >= musicQueue.size()) return;
        stopPlay();
        position++;
        startPlay(context);
    }
    public static void prevSong(Context context) {
        if (position - 1 < 0) return;
        stopPlay();
        position--;
        startPlay(context);
    }
    public static void setPositionAndPlay(Context context, int position) {
        stopPlay();
        MusicManager.position = position;
        startPlay(context);
    }
    public static boolean getIsPlaying() {
        return isPlaying;
    }
    public static Music getCurrentMusic() {
        return musicQueue.size() > 0 ? musicQueue.get(position) : null;
    }

    public static ObservableArrayList<Music> getMusicQueue() {
        return musicQueue;
    }

    public static int getPosition() {
        return position;
    }

    public static interface OnInformationReceivedListener extends EventListener {
        public void onInformationReceived(SongModel song);
        public void onDurationReceived(int duration);
        public void onIsPlayingChanged(boolean isPlaying);
    }

    public static interface OnTimeUpdateListener extends EventListener {
        public void onTimeUpdate(int time);
    }
}
