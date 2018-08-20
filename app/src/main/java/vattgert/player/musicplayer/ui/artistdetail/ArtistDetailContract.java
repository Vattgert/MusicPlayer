package vattgert.player.musicplayer.ui.artistdetail;

import java.util.List;

import vattgert.player.musicplayer.BasePresenter;
import vattgert.player.musicplayer.BaseView;
import vattgert.player.musicplayer.data.models.Album;
import vattgert.player.musicplayer.data.models.Song;

public interface ArtistDetailContract {
    interface Presenter extends BasePresenter<View>{
        void getArtist();
        void setArtistId(String artistId);
    }

    interface View extends BaseView {
        void showAlbum(Album album);
        void showArtistAlbums(List<Album> albumList);
        void showArtistSongs(List<Song> songsList);
    }
}
