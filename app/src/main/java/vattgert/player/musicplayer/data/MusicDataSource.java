package vattgert.player.musicplayer.data;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.squareup.sqlbrite3.BriteContentResolver;
import com.squareup.sqlbrite3.SqlBrite;

import java.util.List;
import java.util.concurrent.Future;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import vattgert.player.musicplayer.MusicPlayerApplication;
import vattgert.player.musicplayer.data.lastfm.MusicPlayerLastFmService;
import vattgert.player.musicplayer.data.models.Album;
import vattgert.player.musicplayer.data.models.Artist;
import vattgert.player.musicplayer.data.models.Song;
import vattgert.player.musicplayer.utils.SchedulerProvider;

public class MusicDataSource implements DataSource{
    private static Uri musicExternalUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    private static Uri albumExternalUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
    private static Uri artistExternalUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;

    private Uri musicInternalUri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;

    private  BriteContentResolver contentResolver;

    private Function<Cursor, Song> songMapperFunction;
    private Function<Cursor, Album> albumMapperFunction;
    private Function<Cursor, Artist> artistMapperFunction;

    protected @Inject Context context;
    protected @Inject SchedulerProvider schedulerProvider;

    public MusicDataSource() {
        MusicPlayerApplication.getComponent().inject(this);

        ContentResolver musicContentResolver = musicContentResolver = context.getContentResolver();
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        contentResolver = sqlBrite.wrapContentProvider(musicContentResolver, schedulerProvider.io());

        songMapperFunction = this::getSong;
        albumMapperFunction = this::getAlbum;
        artistMapperFunction = this::getArtist;
    }

    @Override
    public Flowable<List<Song>> getSongs() {
        return contentResolver.createQuery( musicExternalUri,
                getSongProjection(),MediaStore.Audio.Media.IS_MUSIC + " != 0",
                null, null, false)
                .mapToList(songMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<Song> getSongById(String songId) {
        return contentResolver.createQuery(musicExternalUri, getSongProjection(),
                MediaStore.Audio.Media.IS_MUSIC + " != 0 AND " + MediaStore.Audio.Media._ID + " = " + songId,
                null, null, false)
                .mapToOne(songMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<List<Album>> getAlbums(){
        return contentResolver.createQuery( albumExternalUri,
                getAlbumProjection(),null,
                null, null, false)
                .mapToList(albumMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<Album> getAlbum(String albumId) {
        return contentResolver.createQuery(albumExternalUri, getAlbumProjection(), null,
                null, null, false)
                .mapToOne(albumMapperFunction).toFlowable(BackpressureStrategy.ERROR);
    }

    @Override
    public Flowable<List<Artist>> getArtists() {
        return contentResolver.createQuery( artistExternalUri,
                getArtistProjection(),null,
                null, null, false)
                .mapToList(artistMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    private Song getSong(Cursor cursor){
        String id = cursor.getString(cursor.getColumnIndexOrThrow(getSongProjection()[0]));
        String title = cursor.getString(cursor.getColumnIndexOrThrow(getSongProjection()[1]));
        String artist = cursor.getString(cursor.getColumnIndexOrThrow(getSongProjection()[2]));
        String album = cursor.getString(cursor.getColumnIndexOrThrow(getSongProjection()[3]));
        long duration = cursor.getLong(cursor.getColumnIndexOrThrow(getSongProjection()[4]));
        int year = cursor.getInt(cursor.getColumnIndexOrThrow(getSongProjection()[5]));
        int track = cursor.getInt(cursor.getColumnIndexOrThrow(getSongProjection()[6]));

        return new Song(id, title, artist, album, duration, year, track);
    }

    private Album getAlbum(Cursor cursor){
        String id = cursor.getString(cursor.getColumnIndexOrThrow(getAlbumProjection()[0]));
        String title = cursor.getString(cursor.getColumnIndexOrThrow(getAlbumProjection()[1]));
        String art = cursor.getString(cursor.getColumnIndexOrThrow(getAlbumProjection()[2]));
        String artist = cursor.getString(cursor.getColumnIndexOrThrow(getAlbumProjection()[3]));
        int songsNumber = cursor.getInt(cursor.getColumnIndexOrThrow(getAlbumProjection()[4]));
        int year = cursor.getInt(cursor.getColumnIndexOrThrow(getAlbumProjection()[5]));
        return new Album(id, title, art, artist, songsNumber, year);
    }

    private Artist getArtist(Cursor cursor){
        String id = cursor.getString(cursor.getColumnIndexOrThrow(getArtistProjection()[0]));
        String artist = cursor.getString(cursor.getColumnIndexOrThrow(getArtistProjection()[1]));
        int numberOfAlbums = cursor.getInt(cursor.getColumnIndexOrThrow(getArtistProjection()[2]));
        int numberOfTracks = cursor.getInt(cursor.getColumnIndexOrThrow(getArtistProjection()[3]));
        return new Artist(id, artist, numberOfAlbums, numberOfTracks);
    }

    private String[] getSongProjection(){
        return new String[]{
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.YEAR,
            MediaStore.Audio.Media.TRACK
        };
    }

    private String[] getAlbumProjection(){
        return new String[]{
            MediaStore.Audio.Albums._ID,
            MediaStore.Audio.Albums.ALBUM,
            MediaStore.Audio.Albums.ALBUM_ART,
            MediaStore.Audio.Albums.ARTIST,
            MediaStore.Audio.Albums.NUMBER_OF_SONGS,
            MediaStore.Audio.Albums.LAST_YEAR
        };
    }

    private String[] getArtistProjection(){
        return new String[]{
                MediaStore.Audio.Artists._ID,
                MediaStore.Audio.Artists.ARTIST,
                MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,
                MediaStore.Audio.Artists.NUMBER_OF_TRACKS,
        };
    }
}
