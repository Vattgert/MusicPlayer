package vattgert.player.musicplayer;

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
}
