package vattgert.player.musicplayer;

import java.util.concurrent.ThreadPoolExecutor;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public interface BasePresenter<V extends BaseView> {
    void bind(V view);
    void unbind();
}
