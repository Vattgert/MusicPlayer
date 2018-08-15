package vattgert.player.musicplayer.ui.albums;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import vattgert.player.musicplayer.AbstractPresenter;
import vattgert.player.musicplayer.data.MusicDataSource;
import vattgert.player.musicplayer.data.models.Album;
import vattgert.player.musicplayer.data.models.Song;
import vattgert.player.musicplayer.utils.SchedulerProvider;

public class AlbumsPresenter extends AbstractPresenter implements AlbumsContract.Presenter {
    private AlbumsContract.View view;

    protected MusicDataSource musicDataSource;
    protected SchedulerProvider schedulerProvider;

    @Inject
    public AlbumsPresenter(MusicDataSource musicDataSource, SchedulerProvider schedulerProvider) {
        this.musicDataSource = musicDataSource;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public void getAlbums() {
        Disposable disposable = musicDataSource.getAlbums()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(albums -> {
                    if(view != null){
                        populateAlbums(albums);
                    }
                }, throwable -> {
                    Log.wtf("MusicPlayerExceptions", throwable.getMessage());
                });
        getCompositeDisposable().add(disposable);
    }

    @Override
    public void bind(AlbumsContract.View view) {
        Log.wtf("MusicPlayer", "AlbumsView bind");
        this.view = view;
    }

    @Override
    public void unbind() {
        getCompositeDisposable().clear();
        Log.wtf("MusicPlayer", "AlbumsView unbind");
        view = null;
    }

    private void populateAlbums(List<Album> albums) {
        if (albums.size() == 0) {
            view.showNoAlbums();
        } else {
            view.showAlbums(albums);
        }
    }
}
