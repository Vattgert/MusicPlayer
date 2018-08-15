package vattgert.player.musicplayer.ui.artists;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import vattgert.player.musicplayer.AbstractPresenter;
import vattgert.player.musicplayer.data.MusicDataSource;
import vattgert.player.musicplayer.data.models.Artist;
import vattgert.player.musicplayer.utils.SchedulerProvider;

public class ArtistsPresenter extends AbstractPresenter implements ArtistContract.Presenter{
    private ArtistContract.View view;

    protected MusicDataSource musicDataSource;
    protected SchedulerProvider schedulerProvider;

    @Inject
    public ArtistsPresenter(MusicDataSource musicDataSource, SchedulerProvider schedulerProvider) {
        this.musicDataSource = musicDataSource;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public void getArtists() {
        Disposable disposable = musicDataSource.getArtists()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(artists -> {
                    if(view != null){
                        Log.wtf("MusicPlayer", "Retrieve artist");
                        populateArtists(artists);
                    }
                }, throwable -> {
                    Log.wtf("MusicPlayerExceptions", throwable.getMessage());
                });
        getCompositeDisposable().add(disposable);
    }

    @Override
    public void bind(ArtistContract.View view) {
        Log.wtf("MusicPlayer", "ArtistView unbind");
        this.view = view;
    }

    @Override
    public void unbind() {
        getCompositeDisposable().clear();
        Log.wtf("MusicPlayer", "ArtistView unbind");
        view = null;
    }

    private void populateArtists(List<Artist> artists){
        if (artists.size() == 0) {
            view.showNoArtist();
        } else {
            view.showArtist(artists);
        }
    }
}
