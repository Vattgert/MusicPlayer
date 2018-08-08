package vattgert.player.musicplayer.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import vattgert.player.musicplayer.MusicPlayerApplication;
import vattgert.player.musicplayer.data.MusicDataSource;
import vattgert.player.musicplayer.data.models.Album;
import vattgert.player.musicplayer.utils.SchedulerProvider;

public class AlbumsViewModel extends ViewModel{
    @Inject
    protected MusicDataSource musicDataSource;

    @Inject
    protected SchedulerProvider schedulerProvider;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private MutableLiveData<List<Album>> albums;
    private MutableLiveData<Album> album;

    public AlbumsViewModel() {
        MusicPlayerApplication.getComponent().inject(this);
    }

    public MutableLiveData<List<Album>> getAlbums() {
        if(albums == null) {
            albums = new MutableLiveData<>();
            loadAlbums();
        }
        return albums;
    }

    public MutableLiveData<Album> getAlbum(String albumId) {
        if(album == null) {
            album = new MutableLiveData<>();
            Log.wtf("MusicPlayer", "AlbumLiveData is null. Retrieve from repository");
            loadAlbum(albumId);
        }
        Log.wtf("MusicPlayer", "Return AlbumLiveData");
        return album;
    }

    private void loadAlbums(){
        Disposable disposable = musicDataSource.getAlbums()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(albumList -> albums.setValue(albumList));
        compositeDisposable.add(disposable);
    }

    private void loadAlbum(String albumId){
        Disposable disposable = musicDataSource.getAlbum(albumId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(currentAlbum -> album.setValue(currentAlbum));
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        Log.wtf("MusicPlayer", "Albums ViewModel onClear");
        compositeDisposable.clear();
    }
}
