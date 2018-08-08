package vattgert.player.musicplayer.utils;

import android.content.ContentUris;
import android.net.Uri;

public class Utils {
    public static Uri getAlbumUri(String albumId){
        return ContentUris.withAppendedId(Const.albumArtContent, Long.parseLong(albumId));
    }
}
