package vattgert.player.musicplayer.ui.songs;

import java.util.List;

import vattgert.player.musicplayer.BasePresenter;
import vattgert.player.musicplayer.BaseView;
import vattgert.player.musicplayer.Playback;
import vattgert.player.musicplayer.data.models.Song;

public interface SongsContract {
    interface Presenter extends BasePresenter<View>{
        void getSongs();
    }

    interface View extends BaseView {
        void showSongs(List<Song> songs);
        void showNoSongs();
    }
}
