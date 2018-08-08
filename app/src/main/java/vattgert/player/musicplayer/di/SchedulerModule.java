package vattgert.player.musicplayer.di;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import vattgert.player.musicplayer.data.MusicDataSource;
import vattgert.player.musicplayer.utils.SchedulerProvider;

@Module
public class SchedulerModule {
    @Provides
    @NonNull
    @Singleton
    public SchedulerProvider provideSchedulerProvider(){
        return new SchedulerProvider();
    }
}
