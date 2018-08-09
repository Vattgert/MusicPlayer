package vattgert.player.musicplayer.ui.songs;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import vattgert.player.musicplayer.BasePresenter;
import vattgert.player.musicplayer.BaseView;
import vattgert.player.musicplayer.MusicPlayerApplication;
import vattgert.player.musicplayer.data.MusicDataSource;
import vattgert.player.musicplayer.data.models.Song;
import vattgert.player.musicplayer.utils.SchedulerProvider;

public class SongsPresenter extends BasePresenter{
    @Inject
    protected MusicDataSource musicDataSource;

    @Inject
    protected SchedulerProvider schedulerProvider;

    private SongsContract.View view;

    public SongsPresenter(SongsContract.View view) {
        super(view);
        MusicPlayerApplication.getComponent().inject(this);
    }
}
