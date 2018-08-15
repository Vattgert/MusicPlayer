package vattgert.player.musicplayer.ui.artists;

import java.util.List;

import vattgert.player.musicplayer.BasePresenter;
import vattgert.player.musicplayer.BaseView;
import vattgert.player.musicplayer.data.models.Artist;

public interface ArtistContract {
    interface Presenter extends BasePresenter<View>{
        void getArtists();
    }

    interface View extends BaseView{
        void showArtist(List<Artist> artists);
        void showNoArtist();
    }
}
