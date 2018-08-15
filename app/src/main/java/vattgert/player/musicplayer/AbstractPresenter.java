package vattgert.player.musicplayer;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class AbstractPresenter {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public CompositeDisposable getCompositeDisposable(){
        return  compositeDisposable;
    }

    public void addDisposable(Disposable disposable){
        compositeDisposable.add(disposable);
    }
}
