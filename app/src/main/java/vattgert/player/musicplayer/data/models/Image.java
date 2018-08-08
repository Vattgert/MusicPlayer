package vattgert.player.musicplayer.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("#text")
    @Expose
    private String imageUrl;
    @SerializedName("size")
    private String size;

    public Image(String imageUrl, String size) {
        this.imageUrl = imageUrl;
        this.size = size;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String text) {
        this.imageUrl = text;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
