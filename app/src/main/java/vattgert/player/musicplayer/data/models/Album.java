package vattgert.player.musicplayer.data.models;

import java.util.ArrayList;
import java.util.List;

public class Album {
    private String albumId;
    private String albumTitle;
    private String albumArt;
    private String albumArtist;
    private int songsNumber;
    private int year;
    private List<Song> songs;

    public Album() {
    }

    public Album(String albumId, String albumTitle, String albumArt, String albumArtist,
                 int songsNumber, int year) {
        this.albumId = albumId;
        this.albumTitle = albumTitle;
        this.albumArt = albumArt;
        this.albumArtist = albumArtist;
        this.songsNumber = songsNumber;
        this.year = year;
        this.songs = new ArrayList<>();
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(String albumArt) {
        this.albumArt = albumArt;
    }

    public String getAlbumArtist() {
        return albumArtist;
    }

    public void setAlbumArtist(String albumArtist) {
        this.albumArtist = albumArtist;
    }

    public int getSongsNumber() {
        return songsNumber;
    }

    public void setSongsNumber(int songsNumber) {
        this.songsNumber = songsNumber;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public String toString() {
        return albumTitle;
    }
}
