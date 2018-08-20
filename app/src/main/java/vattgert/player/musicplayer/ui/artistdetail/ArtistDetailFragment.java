package vattgert.player.musicplayer.ui.artistdetail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import vattgert.player.musicplayer.MusicPlayerApplication;
import vattgert.player.musicplayer.R;
import vattgert.player.musicplayer.data.models.Album;
import vattgert.player.musicplayer.data.models.Song;
import vattgert.player.musicplayer.utils.Constant;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArtistDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArtistDetailFragment extends Fragment implements ArtistDetailContract.View {
    protected ArtistDetailPresenter presenter;

    @BindView(R.id.recyclerViewArtistAlbums) RecyclerView recyclerViewArtistAlbums;
    @BindView(R.id.recyclerViewArtistSongs) RecyclerView recyclerViewAlbumSongs;
    @BindView(R.id.toolbarArtistDetail) Toolbar toolbarArtistDetail;


    public ArtistDetailFragment() {

    }

    public static ArtistDetailFragment newInstance(String artistId) {
        ArtistDetailFragment fragment = new ArtistDetailFragment();
        Bundle args = new Bundle();
        args.putString(Constant.EXTRA_ARTIST_ID, artistId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MusicPlayerApplication.getComponent().inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getArguments() != null){
            String albumId = getArguments().getString(Constant.EXTRA_ARTIST_ID);
            if(albumId != null && !albumId.equals(Constant.EMPTY_STRING)){
                presenter.bind(this);
                presenter.setArtistId(albumId);
                presenter.getArtist();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artist_detail, container, false);
        ButterKnife.bind(this, view);

        RecyclerView.LayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        RecyclerView.LayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        return view;
    }

    @Override
    public void showAlbum(Album album) {

    }

    @Override
    public void showArtistAlbums(List<Album> albumList) {

    }

    @Override
    public void showArtistSongs(List<Song> songsList) {

    }

    @Inject
    public void setPresenter(ArtistDetailPresenter presenter){
        this.presenter = presenter;
    }
}
