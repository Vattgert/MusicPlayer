package vattgert.player.musicplayer.interfaces;

public interface ItemDetails {
    interface SongItemDetails{
        void openSongDetails(String songId);
    }
    interface AlbumItemDetails{
        void openAlbumDetails(String albumId);
    }
    interface ArtistItemDetails{
        void openArtistDetails(String artistId);
    }
}
