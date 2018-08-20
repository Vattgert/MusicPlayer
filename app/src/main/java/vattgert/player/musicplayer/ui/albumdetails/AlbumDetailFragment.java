package vattgert.player.musicplayer.ui.albumdetails;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import vattgert.player.musicplayer.MusicPlayerApplication;
import vattgert.player.musicplayer.R;
import vattgert.player.musicplayer.data.models.Album;
import vattgert.player.musicplayer.data.models.Song;
import vattgert.player.musicplayer.ui.activities.MainActivity;
import vattgert.player.musicplayer.ui.songs.SongsFragment;
import vattgert.player.musicplayer.utils.Constant;
import vattgert.player.musicplayer.utils.Utils;


public class AlbumDetailFragment extends Fragment implements AlbumDetailContract.View {
    protected AlbumDetailPresenter presenter;

    @BindView(R.id.toolbarAlbumDetail) Toolbar albumDetailToolbar;
    @BindView(R.id.imageViewAlbumArt) ImageView albumCoverImageView;
    @BindView(R.id.recyclerViewAlbumSongs) RecyclerView albumSongsRecyclerView;

    private AlbumSongsAdapter albumSongsAdapter;

    public AlbumDetailFragment() {
        // Required empty public constructor
    }

    public static AlbumDetailFragment newInstance(@NonNull String albumId) {
        Bundle args = new Bundle();
        args.putString(Constant.EXTRA_ALBUM_ID, albumId);
        AlbumDetailFragment albumDetailFragment = new AlbumDetailFragment();
        albumDetailFragment.setArguments(args);
        return albumDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MusicPlayerApplication.getComponent().inject(this);

        albumSongsAdapter = new AlbumSongsAdapter(new ArrayList<>());
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getArguments() != null){
            String albumId = getArguments().getString(Constant.EXTRA_ALBUM_ID);
            if(albumId != null && !albumId.equals(Constant.EMPTY_STRING)){
                presenter.bind(this);
                presenter.setAlbumId(albumId);
                presenter.getAlbumById();
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
        View view = inflater.inflate(R.layout.fragment_album_detail, container, false);
        ButterKnife.bind(this, view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        albumSongsRecyclerView.setLayoutManager(layoutManager);
        albumSongsRecyclerView.setAdapter(albumSongsAdapter);

        return view;
    }

    @Override
    public void showAlbumData(Album album) {
        setAlbumCover(album.getAlbumId());
        setAlbumTitle(album.getAlbumTitle());
        setAlbumArtist(album.getAlbumArtist());
    }

    @Override
    public void showAlbumSongs(List<Song> songs) {
        albumSongsAdapter.setData(songs);
    }

    private void setAlbumTitle(String title){
        albumDetailToolbar.setTitle(title);
    }

    private void setAlbumArtist(String title){
        albumDetailToolbar.setSubtitle(title);
    }

    private void setAlbumCover(String albumId){
        Utils.setImage(albumId, R.drawable.no_cover, albumCoverImageView);
    }

    @Inject
    public void setPresenter(AlbumDetailPresenter presenter){
        this.presenter = presenter;
    }

    public static class AlbumSongsAdapter extends RecyclerView.Adapter<AlbumSongsAdapter.ViewHolder>{
        private List<Song> songs;

        public AlbumSongsAdapter(List<Song> songs){
            this.songs = songs;
        }

        @NonNull
        @Override
        public AlbumSongsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_album_song, parent, false);
            return new AlbumSongsAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AlbumSongsAdapter.ViewHolder holder, int position) {
            Song song = songs.get(position);
            holder.bind(song);
        }

        @Override
        public int getItemCount() {
            return songs.size();
        }

        public void setData(List<Song> songs){
            this.songs = songs;
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            @BindView(R.id.textViewOrderNumber) TextView songOrderTextView;
            @BindView(R.id.imageViewMenu) ImageView menuImageView;
            @BindView(R.id.textViewSongTitle) TextView songTitleTextView;
            @BindView(R.id.textViewSongDuration) TextView songDurationTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            void bind(Song song){
                songOrderTextView.setText(String.valueOf(song.getTrack()));
                songTitleTextView.setText(song.getTitle());
                songDurationTextView.setText(song.getFormattedDuration());
            }
        }
    }
}
