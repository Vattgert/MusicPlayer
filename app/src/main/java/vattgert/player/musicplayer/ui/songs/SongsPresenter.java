package vattgert.player.musicplayer.ui.songs;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import vattgert.player.musicplayer.AbstractPresenter;
import vattgert.player.musicplayer.data.MusicDataSource;
import vattgert.player.musicplayer.data.models.Song;
import vattgert.player.musicplayer.utils.SchedulerProvider;

public class SongsPresenter extends AbstractPresenter implements SongsContract.Presenter{
    private SongsContract.View view;

    protected MusicDataSource musicDataSource;
    protected SchedulerProvider schedulerProvider;

    @Inject
    public SongsPresenter(MusicDataSource musicDataSource, SchedulerProvider schedulerProvider) {
        this.musicDataSource = musicDataSource;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public void getSongs() {
        Disposable disposable = musicDataSource.getSongs()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(songs -> {
                    if(view != null){
                        populateSongs(songs);
                    }
                }, throwable -> {
                    Log.wtf("MusicPlayerExceptions", throwable.getMessage());
                });
        this.getCompositeDisposable().add(disposable);
    }

    @Override
    public void bind(SongsContract.View view) {
        this.view = view;
    }

    @Override
    public void unbind() {
        getCompositeDisposable().clear();
        view = null;
    }

    private void populateSongs(List<Song> songs){
        if(songs.size() == 0){
            view.showNoSongs();
        }
        else{
            view.showSongs(songs);
        }
    }
}
