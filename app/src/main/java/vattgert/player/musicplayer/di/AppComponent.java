package vattgert.player.musicplayer.di;

import javax.inject.Singleton;

import dagger.Component;
import vattgert.player.musicplayer.data.MusicDataSource;
import vattgert.player.musicplayer.ui.albums.AlbumsFragment;
import vattgert.player.musicplayer.ui.artists.ArtistsFragment;
import vattgert.player.musicplayer.ui.songs.SongsFragment;

@Component (modules = {AppModule.class, DataModule.class, SchedulerModule.class, LastFmApiModule.class})
@Singleton
public interface AppComponent {
    //Injection to classes
    void inject(MusicDataSource musicDataSource);

    //Injection to Views
    void inject(SongsFragment fragment);
    void inject(AlbumsFragment fragment);
    void inject(ArtistsFragment fragment);
}
