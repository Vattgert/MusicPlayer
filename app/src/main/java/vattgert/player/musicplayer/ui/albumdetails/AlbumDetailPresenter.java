package vattgert.player.musicplayer.ui.albumdetails;

import android.util.Log;

import org.reactivestreams.Publisher;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import vattgert.player.musicplayer.AbstractPresenter;
import vattgert.player.musicplayer.BaseView;
import vattgert.player.musicplayer.data.MusicDataSource;
import vattgert.player.musicplayer.data.models.Album;
import vattgert.player.musicplayer.data.models.Song;
import vattgert.player.musicplayer.utils.Constant;
import vattgert.player.musicplayer.utils.SchedulerProvider;

public class AlbumDetailPresenter extends AbstractPresenter implements AlbumDetailContract.Presenter {
    private AlbumDetailContract.View view;

    protected String albumId;
    protected MusicDataSource musicDataSource;
    protected SchedulerProvider schedulerProvider;

    @Inject
    public AlbumDetailPresenter(MusicDataSource musicDataSource,
                                SchedulerProvider schedulerProvider) {
        this.musicDataSource = musicDataSource;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public void getAlbumById() {
        getAlbum();
        getAlbumSongs();
    }

    private void getAlbum(){
        Log.wtf("MusicPlayer", "map enter");
        Disposable disposable = musicDataSource.getAlbum(this.albumId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::populateAlbum, throwable -> Log.wtf("MusicPlayer", throwable.getMessage()));
        getCompositeDisposable().add(disposable);
    }

    private void getAlbumSongs(){
        Disposable disposable = musicDataSource.getSongsByAlbumTest(this.albumId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::populateAlbumSongs, throwable -> Log.wtf("MusicPlayer", throwable.getMessage()));
        getCompositeDisposable().add(disposable);
    }

    public void test(){
        Disposable disposable = musicDataSource.getSongsByAlbumTest(this.albumId)
                .flatMap(songs -> musicDataSource.getAlbum(this.albumId)
                                .subscribeOn(schedulerProvider.newThread())
                                .doAfterNext(album -> album.setSongs(songs))
                )
                .observeOn(schedulerProvider.ui())
                .subscribe(album -> {
                    populateAlbum(album);
                    populateAlbumSongs(album.getSongs());
                });
        getCompositeDisposable().add(disposable);
    }

    @Override
    public void setAlbumId(String albumId) {
        this.albumId = albumId;
        Log.wtf("MusicPlayer", "Presenter " + this.albumId);
    }

    @Override
    public void bind(AlbumDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void unbind() {
        getCompositeDisposable().clear();
        view = null;
    }

    private void populateAlbum(Album album){
        if(view != null){
            view.showAlbumData(album);
        }
    }

    private void populateAlbumSongs(List<Song> songs){
        if(view != null){
            view.showAlbumSongs(songs);
        }
    }
}
