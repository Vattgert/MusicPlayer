package vattgert.player.musicplayer.di;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import vattgert.player.musicplayer.data.lastfm.MusicPlayerLastFmService;

@Module
public class LastFmApiModule {
    @Provides
    @NonNull
    @Singleton
    MusicPlayerLastFmService provideLastFmService(){
        return new MusicPlayerLastFmService();
    }
}
