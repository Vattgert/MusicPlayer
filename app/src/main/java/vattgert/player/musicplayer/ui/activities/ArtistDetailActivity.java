package vattgert.player.musicplayer.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vattgert.player.musicplayer.R;
import vattgert.player.musicplayer.ui.artistdetail.ArtistDetailFragment;
import vattgert.player.musicplayer.utils.Constant;
import vattgert.player.musicplayer.utils.Utils;

public class ArtistDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_detail);

        if(getIntent().getExtras() != null){
            String artistId =  getIntent().getExtras().getString(Constant.EXTRA_ARTIST_ID);
            ArtistDetailFragment artistDetailFragment = ArtistDetailFragment.newInstance(artistId);
            Utils.replaceFragment(getSupportFragmentManager(), artistDetailFragment, R.id.container);
        }
    }
}
