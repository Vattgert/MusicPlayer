package vattgert.player.musicplayer.ui.albums;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import vattgert.player.musicplayer.MusicPlayerApplication;
import vattgert.player.musicplayer.R;
import vattgert.player.musicplayer.data.models.Album;
import vattgert.player.musicplayer.ui.activities.AlbumDetailActivity;
import vattgert.player.musicplayer.ui.albumdetails.AlbumDetailFragment;
import vattgert.player.musicplayer.utils.Constant;
import vattgert.player.musicplayer.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlbumsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlbumsFragment extends Fragment implements AlbumsContract.View {
    private AlbumAdapter albumAdapter;
    private AlbumsPresenter presenter;

    @BindView(R.id.gridViewAlbums) GridView gridView;
    @BindView(R.id.noAlbumsView) View noAlbumsView;

    public AlbumsFragment() {

    }

    public static AlbumsFragment newInstance() {
        return new AlbumsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MusicPlayerApplication.getComponent().inject(this);

        albumAdapter = new AlbumAdapter(new ArrayList<>(0), this.getContext(),
                albumListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.bind(this);
        presenter.getAlbums();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unbind();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_albums, container, false);
        ButterKnife.bind(this, view);
        gridView.setAdapter(albumAdapter);
        return view;
    }

    @Override
    public void showAlbums(List<Album> albums) {
        noAlbumsView.setVisibility(View.GONE);
        albumAdapter.setData(albums);
    }

    @Override
    public void showNoAlbums() {
        noAlbumsView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showAlbumDetail(String albumId) {
        Intent intent = new Intent(getActivity(), AlbumDetailActivity.class);
        intent.putExtra(Constant.EXTRA_ALBUM_ID, albumId);
        startActivity(intent);
    }

    @Inject
    public void setPresenter(AlbumsPresenter albumsPresenter){
        presenter = albumsPresenter;
    }

    public static class AlbumAdapter extends BaseAdapter{
        private List<Album> albumList;
        private LayoutInflater layoutInflater;
        private AlbumListener albumListener;

        AlbumAdapter(List<Album> albumList, Context context, AlbumListener albumListener){
            this.albumList = albumList;
            layoutInflater = LayoutInflater.from(context);
            this.albumListener = albumListener;
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

        public class ViewHolder{
            private View itemView;
            @BindView(R.id.imageViewAlbumArt) ImageView albumArtImageView;
            @BindView(R.id.linearLayoutAlbum) LinearLayout linearLayoutAlbum;
            @BindView(R.id.textViewAlbumTitle) TextView albumTitleTextView;
            @BindView(R.id.textViewAlbumArtist) TextView albumArtistTextView;

            ViewHolder(View view){
                this.itemView = view;
                ButterKnife.bind(this, view);
            }

            void bind(Album album){
                albumTitleTextView.setText(album.getAlbumTitle());
                albumArtistTextView.setText(album.getAlbumArtist());
                Picasso.get()
                        .load(Utils.getAlbumUri(album.getAlbumId()))
                        .placeholder(R.drawable.no_cover).into(albumArtImageView);

                itemView.setOnClickListener(__ -> albumListener.openAlbumDetails(album));
            }
        }
    }

    interface AlbumListener{
        void openAlbumDetails(Album album);
    }

    AlbumListener albumListener = new AlbumListener() {
        @Override
        public void openAlbumDetails(Album album) {
            presenter.openAlbumDetail(album);
        }
    };
}


