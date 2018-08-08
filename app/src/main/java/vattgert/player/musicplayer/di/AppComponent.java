package vattgert.player.musicplayer.di;

import javax.inject.Singleton;

import dagger.Component;
import vattgert.player.musicplayer.data.MusicDataSource;
import vattgert.player.musicplayer.ui.fragments.AlbumsFragment;
import vattgert.player.musicplayer.ui.fragments.ArtistsFragment;
import vattgert.player.musicplayer.ui.fragments.SongsFragment;
import vattgert.player.musicplayer.viewmodel.AlbumsViewModel;
import vattgert.player.musicplayer.viewmodel.ArtistsViewModel;
import vattgert.player.musicplayer.viewmodel.SongsViewModel;

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
    void inject(SongsViewModel songsViewModel);
    void inject(AlbumsViewModel albumsViewModel);
    void inject(ArtistsViewModel artistsViewModel);
}
