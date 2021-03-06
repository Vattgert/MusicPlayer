package vattgert.player.musicplayer.ui.artists;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import vattgert.player.musicplayer.MusicPlayerApplication;
import vattgert.player.musicplayer.R;
import vattgert.player.musicplayer.data.models.Artist;
import vattgert.player.musicplayer.ui.activities.ArtistDetailActivity;
import vattgert.player.musicplayer.utils.Constant;

public class ArtistsFragment extends Fragment implements ArtistContract.View{
    private ArtistsPresenter presenter;

    @BindView(R.id.gridViewArtists)
    GridView artistGridView;

    ArtistAdapter artistAdapter;

    public ArtistsFragment() {

    }

    public static ArtistsFragment newInstance() {
        return new ArtistsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MusicPlayerApplication.getComponent().inject(this);

        artistAdapter = new ArtistAdapter(new ArrayList<>(0), this.getContext(), artistListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.bind(this);
        presenter.getArtists();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_artists, container, false);
        ButterKnife.bind(this, view);

        artistGridView.setAdapter(artistAdapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void showArtist(List<Artist> artists) {
        artistAdapter.setData(artists);
    }

    @Override
    public void showNoArtist() {

    }

    @Override
    public void showArtistDetail(String artistId) {
        Intent intent = new Intent(this.getContext(), ArtistDetailActivity.class);
        intent.putExtra(Constant.EXTRA_ARTIST_ID, artistId);
        startActivity(intent);
    }

    @Inject
    public void setPresenter(ArtistsPresenter presenter){
        this.presenter = presenter;
    }

    public class ArtistAdapter extends BaseAdapter {
        private List<Artist> artistList;
        private LayoutInflater layoutInflater;
        private ArtistListener artistListener;

        ArtistAdapter(List<Artist> artistList, Context context, ArtistListener artistListener){
            this.artistList = artistList;
            layoutInflater = LayoutInflater.from(context);
            this.artistListener = artistListener;
        }

        @Override
        public int getCount() {
            return artistList.size();
        }

        @Override
        public Artist getItem(int position) {
            return artistList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void setData(List<Artist> artistList){
            this.artistList = artistList;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null) {
                convertView = layoutInflater.inflate(R.layout.list_item_artist, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            else{
                viewHolder = (ViewHolder)convertView.getTag();
            }
            Artist artist = getItem(position);
            viewHolder.bind(artist);

            return convertView;
        }

        public class ViewHolder{
            private View itemView;
            @BindView(R.id.imageViewArtistArt) ImageView artistArtImageView;
            @BindView(R.id.linearLayoutArtist) LinearLayout linearLayoutArtist;
            @BindView(R.id.textViewArtistName) TextView artistNameTextView;
            @BindView(R.id.textViewArtistMedia) TextView artistMediaTextView;

            Resources resources;

            public ViewHolder(View view){
                this.itemView = view;
                ButterKnife.bind(this, view);
                resources = view.getResources();
            }

            public void bind(Artist artist){
                artistNameTextView.setText(artist.getTitle());
                artistMediaTextView.setText(resources.getString(
                        R.string.artist_media_info,
                        artist.getNumberOfAlbums(),
                        artist.getNumberOfTracks()));
                Picasso.get().load(R.drawable.no_artist).into(artistArtImageView);
                itemView.setOnClickListener(__ -> artistListener.openArtistDetails(artist));
            }
        }
    }

    interface ArtistListener{
        void openArtistDetails(Artist artist);
    }

    ArtistListener artistListener = new ArtistListener() {
        @Override
        public void openArtistDetails(Artist artist) {
            presenter.openArtistDetails(artist);
        }
    };
}
