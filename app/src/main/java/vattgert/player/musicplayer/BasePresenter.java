package vattgert.player.musicplayer;

import java.util.concurrent.ThreadPoolExecutor;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<V> {

    protected final V view;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected BasePresenter(V view){
        this.view = view;
    }

    public void start(){};

    public void stop(){
        compositeDisposable.clear();
    }

    public void addDisposable(Disposable disposable){
        compositeDisposable.add(disposable);
    }
}
