package dev.doyeong.theillogical.music;

import android.annotation.SuppressLint;
import android.content.Context;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import dev.doyeong.theillogical.models.SongModel;

public class MusicManager {
    private static List<Music> musicQueue = new ArrayList<>();
    private static List<OnInformationReceivedListener> listeners = new ArrayList<>();
    private static List<OnTimeUpdateListener> updateListeners = new ArrayList<>();
    private static int position = 0;
    private static boolean isPlaying = false;
    private static SongModel songInfo;

    public static void startMusic(Context context, Music music) {
        if (!musicQueue.isEmpty()) {
            musicQueue.get(position).stopPlay();
        }

        songInfo = null;

        music.createMediaPlayer(context);
        music.getData(context);
        music.setOnInformationReceivedListener(song -> {
            for (OnInformationReceivedListener listener : listeners) {
                listener.onInformationReceived(song);
                songInfo = song;
            }
        });
        music.getMediaPlayer().setOnPreparedListener(player -> {
            for (OnInformationReceivedListener listener : listeners) {
                listener.onDurationReceived(player.getDuration());
                listener.onIsPlayingChanged(true);
            }

            setIsPlaying(true);
        });
        music.setOnTimeUpdateListener(time -> {
            for (OnTimeUpdateListener listener : updateListeners) {
                listener.onTimeUpdate(time);
            }
        });

        musicQueue.add(music);
        position = musicQueue.size() - 1;
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

    public static void setIsPlaying(boolean isPlaying) {
        MusicManager.isPlaying = isPlaying;

        if (isPlaying && getCurrentMusic() != null) {
            getCurrentMusic().getMediaPlayer().start();
        } else if (getCurrentMusic() != null) {
            getCurrentMusic().getMediaPlayer().pause();
        }

        for (OnInformationReceivedListener listener : listeners) {
            listener.onIsPlayingChanged(isPlaying);
        }
    }
    public static boolean getIsPlaying() {
        return isPlaying;
    }
    public static Music getCurrentMusic() {
        return musicQueue.size() > 0 ? musicQueue.get(position) : null;
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
