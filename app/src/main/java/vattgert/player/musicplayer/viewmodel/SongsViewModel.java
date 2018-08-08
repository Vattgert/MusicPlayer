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
import vattgert.player.musicplayer.data.models.Song;
import vattgert.player.musicplayer.utils.SchedulerProvider;

public class SongsViewModel extends ViewModel{
    @Inject
    protected MusicDataSource musicDataSource;

    @Inject
    protected SchedulerProvider schedulerProvider;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private MutableLiveData<List<Song>> songs;

    public SongsViewModel() {
        MusicPlayerApplication.getComponent().inject(this);
    }

    public MutableLiveData<List<Song>> getSongs() {
        if(songs == null) {
            songs = new MutableLiveData<>();
            loadSongs();
        }
        return songs;
    }

    private void loadSongs(){
        Disposable disposable = musicDataSource.getSongs()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(songsList -> songs.setValue(songsList));
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        Log.wtf("MusicPlayer", "On cleared called");
        compositeDisposable.clear();
    }
}
