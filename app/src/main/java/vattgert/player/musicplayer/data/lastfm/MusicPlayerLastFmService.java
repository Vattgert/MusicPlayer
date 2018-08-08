package vattgert.player.musicplayer.data.lastfm;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import vattgert.player.musicplayer.data.lastfm.MusicPlayerApi;
import vattgert.player.musicplayer.data.lastfm.response.LastFmArtistResponse;

public class MusicPlayerLastFmService {
    private MusicPlayerApi musicPlayerApi;
    private Retrofit retrofit;

    public MusicPlayerLastFmService(){
        retrofit = new Retrofit.Builder()
                .baseUrl(MusicPlayerApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        musicPlayerApi = retrofit.create(MusicPlayerApi.class);
    }

    public Single<LastFmArtistResponse> getArtistInfo(String artist){
        return musicPlayerApi.getArtistData(artist);
    }

    public Call<String> getArtistInfoCall(String artist){
        return musicPlayerApi.getArtistDataCall(artist);
    }

}
