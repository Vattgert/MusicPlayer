package vattgert.player.musicplayer.utils;

import android.net.Uri;

public class Constant {
    final public static Uri albumArtContent = Uri
            .parse("content://media/external/audio/albumart");

    final public static String EMPTY_STRING = "";

    //Intent keys
    final public static String EXTRA_ALBUM_ID = "albumId";
    final public static String EXTRA_ARTIST_ID = "artistId";
}
