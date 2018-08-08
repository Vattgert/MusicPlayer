package vattgert.player.musicplayer.data.lastfm;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import vattgert.player.musicplayer.BuildConfig;
import vattgert.player.musicplayer.data.lastfm.response.LastFmArtistResponse;

public interface MusicPlayerApi {
    String BASE_URL = "https://ws.audioscrobbler.com/2.0/";
    String LAST_FM_API_KEY = BuildConfig.LastFmApiKey;

    @GET("?method=artist.getinfo&format=json&api_key="+ LAST_FM_API_KEY)
    Single<LastFmArtistResponse> getArtistData(@Query("artist") String artist);

    @GET("?method=artist.getinfo&format=json&api_key="+ LAST_FM_API_KEY)
    Call<String> getArtistDataCall(@Query("artist") String artist);
}
