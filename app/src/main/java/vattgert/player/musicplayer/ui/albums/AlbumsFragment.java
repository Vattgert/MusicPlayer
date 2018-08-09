package vattgert.player.musicplayer.ui.albums;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import vattgert.player.musicplayer.data.MusicDataSource;
import vattgert.player.musicplayer.data.models.Album;
import vattgert.player.musicplayer.interfaces.ItemDetails;
import vattgert.player.musicplayer.ui.custom.ViewLifecycleFragment;
import vattgert.player.musicplayer.ui.albumdetails.AlbumDetailFragment;
import vattgert.player.musicplayer.utils.Utils;
import vattgert.player.musicplayer.viewmodel.AlbumsViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlbumsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlbumsFragment extends ViewLifecycleFragment implements ItemDetails.AlbumItemDetails {
    @BindView(R.id.gridViewAlbums)
    GridView gridView;

    @Inject
    MusicDataSource musicDataSource;

    private AlbumAdapter albumAdapter;

    public AlbumsFragment() {

    }

    public static AlbumsFragment newInstance() {
        return new AlbumsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MusicPlayerApplication.getComponent().inject(this);

        albumAdapter = new AlbumAdapter(new ArrayList<>(0), this.getContext());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_albums, container, false);
        ButterKnife.bind(this, view);
        gridView.setAdapter(albumAdapter);
        return view;
    }

    @Override
    public void openAlbumDetails(String albumId) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().add(AlbumDetailFragment.newInstance(), "AlbumDetailFragment").
                addToBackStack(null).
                commit();
    }

    public static class AlbumAdapter extends BaseAdapter{
        private List<Album> albumList;
        private LayoutInflater layoutInflater;

        public AlbumAdapter(List<Album> albumList, Context context){
            this.albumList = albumList;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return albumList.size();
        }

        @Override
        public Album getItem(int position) {
            return albumList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void setData(List<Album> albumList){
            this.albumList = albumList;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null) {
                convertView = layoutInflater.inflate(R.layout.list_item_album, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            else{
                viewHolder = (ViewHolder)convertView.getTag();
            }
            Album album = getItem(position);
            viewHolder.bind(album);

            return convertView;
        }

        public static class ViewHolder{
            @BindView(R.id.imageViewAlbumArt) ImageView albumArtImageView;
            @BindView(R.id.linearLayoutAlbum) LinearLayout linearLayoutAlbum;
            @BindView(R.id.textViewAlbumTitle) TextView albumTitleTextView;
            @BindView(R.id.textViewAlbumArtist) TextView albumArtistTextView;

            public ViewHolder(View view){
                ButterKnife.bind(this, view);
            }

            public void bind(Album album){
                albumTitleTextView.setText(album.getAlbumTitle());
                albumArtistTextView.setText(album.getAlbumArtist());
                Picasso.get()
                        .load(Utils.getAlbumUri(album.getAlbumId()))
                        .placeholder(R.drawable.no_cover).into(albumArtImageView);
            }
        }
    }

}


