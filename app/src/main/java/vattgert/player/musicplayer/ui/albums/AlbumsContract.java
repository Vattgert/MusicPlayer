package vattgert.player.musicplayer.ui.albums;

import java.util.List;

import vattgert.player.musicplayer.BasePresenter;
import vattgert.player.musicplayer.BaseView;
import vattgert.player.musicplayer.data.models.Album;

public interface AlbumsContract {
    interface Presenter extends BasePresenter<View>{
        void getAlbums();
    }

    interface View extends BaseView{
        void showAlbums(List<Album> albums);
        void showNoAlbums();
    }
}
