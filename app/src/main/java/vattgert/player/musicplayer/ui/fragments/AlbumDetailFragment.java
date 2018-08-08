package vattgert.player.musicplayer.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vattgert.player.musicplayer.R;


public class AlbumDetailFragment extends Fragment {

    public AlbumDetailFragment() {
        // Required empty public constructor
    }

    public static AlbumDetailFragment newInstance() {
        return new AlbumDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_album_detail, container, false);
    }
}
