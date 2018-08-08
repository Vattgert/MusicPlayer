package vattgert.player.musicplayer.di;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import vattgert.player.musicplayer.data.DataSource;
import vattgert.player.musicplayer.data.MusicDataSource;

@Module
public class DataModule {
    @Provides
    @NonNull
    @Singleton
    public MusicDataSource provideMusicDataSource(){
        return new MusicDataSource();
    }
}
