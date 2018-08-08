package vattgert.player.musicplayer.data.lastfm.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import vattgert.player.musicplayer.data.models.Artist;
import vattgert.player.musicplayer.data.models.Image;

public class LastFmArtistResponse {
    @SerializedName("artist")
    public Artist artistImage;
}
