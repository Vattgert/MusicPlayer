package vattgert.player.musicplayer.di;

import javax.inject.Singleton;

import dagger.Component;
import vattgert.player.musicplayer.data.MusicDataSource;
import vattgert.player.musicplayer.ui.songs.SongsPresenter;
import vattgert.player.musicplayer.ui.albums.AlbumsFragment;
import vattgert.player.musicplayer.ui.artists.ArtistsFragment;
import vattgert.player.musicplayer.ui.songs.SongsFragment;
import vattgert.player.musicplayer.viewmodel.AlbumsViewModel;
import vattgert.player.musicplayer.viewmodel.ArtistsViewModel;

@Component (modules = {AppModule.class, DataModule.class, SchedulerModule.class, LastFmApiModule.class})
@Singleton
public interface AppComponent {
    //Injection to classes
    void inject(MusicDataSource musicDataSource);

    //Injection to fragments
    void inject(SongsFragment fragment);
    void inject(AlbumsFragment fragment);
    void inject(ArtistsFragment fragment);

    //Injection to ViewModels
    void inject(SongsPresenter songsPresenter);
    void inject(AlbumsViewModel albumsViewModel);
    void inject(ArtistsViewModel artistsViewModel);
}
