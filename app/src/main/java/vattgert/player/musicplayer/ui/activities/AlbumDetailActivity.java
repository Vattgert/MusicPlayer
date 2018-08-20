package vattgert.player.musicplayer.ui.activities;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import vattgert.player.musicplayer.R;
import vattgert.player.musicplayer.ui.albumdetails.AlbumDetailFragment;
import vattgert.player.musicplayer.utils.Constant;
import vattgert.player.musicplayer.utils.Utils;

public class AlbumDetailActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);

        if(getIntent().getExtras() != null){
            String albumId =  getIntent().getExtras().getString(Constant.EXTRA_ALBUM_ID);
            AlbumDetailFragment albumDetailFragment = AlbumDetailFragment.newInstance(albumId);
            Utils.replaceFragment(getSupportFragmentManager(), albumDetailFragment, R.id.container);
        }
    }
}
