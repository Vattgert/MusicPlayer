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
import vattgert.player.musicplayer.data.models.Artist;
import vattgert.player.musicplayer.utils.SchedulerProvider;

public class ArtistsViewModel extends ViewModel {
    @Inject
    protected MusicDataSource musicDataSource;

    @Inject
    protected SchedulerProvider schedulerProvider;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private MutableLiveData<List<Artist>> artists;

    public ArtistsViewModel() {
        MusicPlayerApplication.getComponent().inject(this);
    }

    public MutableLiveData<List<Artist>> getArtists() {
        if(artists == null) {
            Log.wtf("MusicPlayer", "ArtistLiveData is null. Retrieve from repository");
            artists = new MutableLiveData<>();
            loadArtists();
        }
        Log.wtf("MusicPlayer", "Return ArtistLiveData");
        return artists;
    }

    private void loadArtists(){
        Disposable disposable = musicDataSource.getArtists()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(artistList -> artists.setValue(artistList));
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        Log.wtf("MusicPlayer", "Artist BaseView Model onClear");
        compositeDisposable.clear();
    }
}
