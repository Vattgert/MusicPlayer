package vattgert.player.musicplayer.ui.albumdetails;

import java.util.List;

import vattgert.player.musicplayer.BasePresenter;
import vattgert.player.musicplayer.BaseView;
import vattgert.player.musicplayer.data.models.Album;
import vattgert.player.musicplayer.data.models.Song;

public interface AlbumDetailContract {
    interface Presenter extends BasePresenter<View>{
        void getAlbumById();
        void setAlbumId(String albumId);
    }

    interface View extends BaseView{
        void showAlbumData(Album album);
        void showAlbumSongs(List<Song> songs);
    }
}
