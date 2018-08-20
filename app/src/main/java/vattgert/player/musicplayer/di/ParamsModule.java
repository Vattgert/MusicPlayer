package vattgert.player.musicplayer.di;

import dagger.Module;
import dagger.Provides;
import vattgert.player.musicplayer.ui.activities.AlbumDetailActivity;
import vattgert.player.musicplayer.utils.Constant;

@Module
public class ParamsModule {
    static String provideAlbumId(AlbumDetailActivity activity){
        return activity.getIntent().getStringExtra(Constant.EXTRA_ALBUM_ID);
    }

}
