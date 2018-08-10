package vattgert.player.musicplayer;

import vattgert.player.musicplayer.data.models.Song;

public interface Playback {
    int getCurrentPosition();
    int getDuration();
    void startPlayback(Song song);
    void startPlayback();
    void pausePlayback();
    void stopPlayback();
    void seekPlayback(int position);
    void playNext();
    void playPrevious();
    void setShuffle();
}
