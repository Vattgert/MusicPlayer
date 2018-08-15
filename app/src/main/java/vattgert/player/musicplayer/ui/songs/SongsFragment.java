package vattgert.player.musicplayer.ui.songs;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import vattgert.player.musicplayer.data.models.Song;

public class SongsFragment extends Fragment implements SongsContract.View {
    private SongsPresenter presenter;
    private SongAdapter songAdapter;

    @BindView(R.id.recyclerViewSongs) RecyclerView songRecyclerView;
    @BindView(R.id.noSongsView) View noSongsView;

    public SongsFragment() {

    }

    public static SongsFragment newInstance() {
        return new SongsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MusicPlayerApplication.getComponent().inject(this);

        songAdapter = new SongAdapter(new ArrayList<>(0));
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.bind(this);
        presenter.getSongs();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_songs, container, false);
        ButterKnife.bind(this, view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        songRecyclerView.setLayoutManager(layoutManager);
        songRecyclerView.setAdapter(songAdapter);
        return view;
    }

    @Inject
    public void setPresenter(SongsPresenter presenter){
        this.presenter = presenter;
    }

    @Override
    public void showSongs(List<Song> songs) {
        noSongsView.setVisibility(View.GONE);
        songAdapter.setData(songs);
    }

    @Override
    public void showNoSongs() {
        noSongsView.setVisibility(View.VISIBLE);
    }

    public static class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder>{
        private List<Song> songs;

        public SongAdapter(List<Song> songs){
            this.songs = songs;
        }

        @NonNull
        @Override
        public SongAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_song, parent, false);
            return new SongAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SongAdapter.ViewHolder holder, int position) {
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
            Song song;

            @BindView(R.id.imageViewSongCover) ImageView songCoverImageView;
            @BindView(R.id.imageViewMenu) ImageView menuImageView;
            @BindView(R.id.textViewSongTitle) TextView songTitleTextView;
            @BindView(R.id.textViewSongArtist) TextView songArtistTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void bind(Song song){
                this.song = song;
                songTitleTextView.setText(song.getTitle());
                songArtistTextView.setText(song.getAlbum());
            }
        }
    }

}
