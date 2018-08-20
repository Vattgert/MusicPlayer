package vattgert.player.musicplayer.data;

import java.util.List;
import java.util.Observable;

import io.reactivex.Flowable;
import io.reactivex.Single;
import vattgert.player.musicplayer.data.models.Album;
import vattgert.player.musicplayer.data.models.Artist;
import vattgert.player.musicplayer.data.models.Song;

public interface DataSource {
    Flowable<List<Song>> getSongs();
    Flowable<Song> getSongById(String songId);
    Flowable<List<Song>> getSongsByAlbum(Album album);

    Flowable<List<Album>> getAlbums();
    Flowable<Album> getAlbum(String albumId);
    Flowable<List<Artist>> getArtists();

    Flowable<Artist> getArtistById(String artistId);
    Flowable<List<Album>> getArtistAlbums(String artistId);
    Flowable<List<Song>> getArtistSongs(String artistId);
}
