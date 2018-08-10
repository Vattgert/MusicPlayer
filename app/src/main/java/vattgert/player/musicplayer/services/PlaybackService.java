package vattgert.player.musicplayer.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import vattgert.player.musicplayer.MusicPlayer;
import vattgert.player.musicplayer.data.models.Song;

public class PlaybackService extends Service implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MusicPlayer {
    private MediaPlayer mediaPlayer = null;
    private ArrayList<Song> songs;
    private int position;
    private boolean shuffle;

    public PlaybackService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        initializeMediaPlayer();
        position = 0;
    }

    private void initializeMediaPlayer(){
        if(mediaPlayer != null){
            mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
            mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build());
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnErrorListener(this);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if(mediaPlayer.getCurrentPosition() > 0){
            mediaPlayer.reset();
            playNext();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.wtf("MusicPlayer", "PlaybackService onError");
        mediaPlayer.reset();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if(mediaPlayer != null) {
            mp.start();
        }
    }

    @Override
    public void setQueue(ArrayList<Song> songs) {
        this.songs = songs;
    }

    @Override
    public void setQueueWithPosition(ArrayList<Song> songs, int position) {
        this.songs = songs;
        this.position = position;
    }

    @Override
    public ArrayList<Song> getCurrentQueue() {
        if(songs != null)
            return songs;
        else
            return new ArrayList<>();
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public void startPlayback(Song song) {

    }

    @Override
    public void startPlayback() {
        if(mediaPlayer != null){
            mediaPlayer.reset();
            Song playableSong = songs.get(position);
            Uri songUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    getSongId(playableSong));
            try {
                mediaPlayer.setDataSource(getApplicationContext(), songUri);
            }
            catch (IOException e){
                Log.wtf("MusicPlayer", "PlaybackService startPlayback() error");
            }
            mediaPlayer.prepareAsync();
        }
    }

    @Override
    public void pausePlayback() {
        if(mediaPlayer != null){
            mediaPlayer.pause();
        }
    }

    @Override
    public void stopPlayback() {
        if(mediaPlayer != null){
            mediaPlayer.pause();
        }
    }

    @Override
    public void seekPlayback(int position) {
        mediaPlayer.seekTo(position);
    }

    @Override
    public void playNext() {
        position++;
        if(position >= songs.size()){
            position = 0;
        }
        startPlayback();
    }

    @Override
    public void playPrevious() {
        position--;
        if(position < 0){
            position = songs.size() - 1;
        }
        startPlayback();
    }

    @Override
    public void setShuffle() {
        shuffle = !shuffle;
    }

    private long getSongId(Song song){
        return Long.valueOf(song.getId());
    }

    private Notification getForegroundNotification(){
        Notification.Builder builder = new Notification.Builder(this);
        return builder.build();
    }
}
