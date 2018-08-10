package vattgert.player.musicplayer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import vattgert.player.musicplayer.data.models.Song;

public interface MusicPlayer extends Playback {
    void setQueue(ArrayList<Song> songs);
    void setQueueWithPosition(ArrayList<Song> songs, int position);
    ArrayList<Song> getCurrentQueue();
}
