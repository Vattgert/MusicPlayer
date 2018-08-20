package vattgert.player.musicplayer.utils;

import android.content.ContentUris;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class Utils {
    public static Uri getAlbumUri(String albumId){
        return ContentUris.withAppendedId(Constant.albumArtContent, Long.parseLong(albumId));
    }

    public static void replaceFragment(@NonNull FragmentManager fragmentManager,
                                       @NonNull Fragment fragment, @NonNull int container){
        fragmentManager.beginTransaction().replace(container, fragment).commit();
    }

    public static void setImage(String albumId, int placeholder, ImageView imageView){
        Picasso.get()
                .load(Utils.getAlbumUri(albumId))
                .placeholder(placeholder).into(imageView);
    }

    public static void showThrowableMessage(Throwable throwable){
        Log.wtf("MusicPlayerError", throwable.getMessage().concat("\ncause: ").concat(throwable.getCause().toString()));
    }
}
