package vattgert.player.musicplayer.di;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import vattgert.player.musicplayer.ui.songs.SongsContract;
import vattgert.player.musicplayer.ui.songs.SongsPresenter;

@Module
public class PresenterModule {
    @Provides
    @NonNull
    @Singleton
    public SongsPresenter provideSongsPresenter(SongsContract.View view){
        return new SongsPresenter(view);
    }
}
