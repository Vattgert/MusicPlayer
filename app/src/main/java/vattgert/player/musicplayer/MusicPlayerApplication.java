package vattgert.player.musicplayer;

import android.app.Application;

import vattgert.player.musicplayer.di.AppComponent;
import vattgert.player.musicplayer.di.AppModule;
import vattgert.player.musicplayer.di.DaggerAppComponent;
import vattgert.player.musicplayer.di.DataModule;
import vattgert.player.musicplayer.di.LastFmApiModule;
import vattgert.player.musicplayer.di.SchedulerModule;


public class MusicPlayerApplication extends Application {
    private static AppComponent component;

    public static AppComponent getComponent(){
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = buildComponent();
    }

    protected AppComponent buildComponent(){
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .dataModule(new DataModule())
                .schedulerModule(new SchedulerModule())
                .lastFmApiModule(new LastFmApiModule())
                .build();
    }
}
