package vattgert.player.musicplayer.ui.artistdetail;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import vattgert.player.musicplayer.AbstractPresenter;
import vattgert.player.musicplayer.data.MusicDataSource;
import vattgert.player.musicplayer.data.models.Album;
import vattgert.player.musicplayer.utils.SchedulerProvider;
import vattgert.player.musicplayer.utils.Utils;

public class ArtistDetailPresenter extends AbstractPresenter implements ArtistDetailContract.Presenter {
    protected MusicDataSource musicDataSource;
    protected SchedulerProvider schedulerProvider;

    private ArtistDetailContract.View view;
    private String artistId;

    @Inject
    public ArtistDetailPresenter(MusicDataSource musicDataSource,
                                 SchedulerProvider schedulerProvider) {
        this.musicDataSource = musicDataSource;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public void getArtist() {
        getArtistAlbums();
    }

    @Override
    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    private void getArtistById(){
        Disposable disposable = musicDataSource.getArtistById(this.artistId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe();
        getCompositeDisposable().add(disposable);
    }

    private void getArtistAlbums(){
        Disposable disposable = musicDataSource.getArtistAlbums(this.artistId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::populateAlbums, Utils::showThrowableMessage);
        getCompositeDisposable().add(disposable);
    }

    private void getArtistSongs(){
        Disposable disposable = musicDataSource.getArtistSongs(this.artistId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe();
        getCompositeDisposable().add(disposable);
    }

    @Override
    public void bind(ArtistDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void unbind() {
        getCompositeDisposable().clear();
        view = null;
    }

    private void populateAlbums(List<Album> albumList){
        for (Album album : albumList){
            Log.wtf("MusicPlayer", album.getAlbumTitle());
        }
    }
}
