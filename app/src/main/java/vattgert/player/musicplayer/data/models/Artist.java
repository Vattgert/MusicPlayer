package vattgert.player.musicplayer.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Artist {
    private String id;
    private String title;
    private int numberOfAlbums;
    private int numberOfTracks;
    @SerializedName("image")
    @Expose
    private List<Image> images;

    public Artist(String id, String title, int numberOfAlbums, int numberOfTracks) {
        this.id = id;
        this.title = title;
        this.numberOfAlbums = numberOfAlbums;
        this.numberOfTracks = numberOfTracks;
        images = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumberOfAlbums() {
        return numberOfAlbums;
    }

    public void setNumberOfAlbums(int numberOfAlbums) {
        this.numberOfAlbums = numberOfAlbums;
    }

    public int getNumberOfTracks() {
        return numberOfTracks;
    }

    public void setNumberOfTracks(int numberOfTracks) {
        this.numberOfTracks = numberOfTracks;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image>  image) {
        this.images = image;
    }

    public Image getLargeImage(){
        return images.get(4);
    }
}
