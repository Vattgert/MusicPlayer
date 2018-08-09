package vattgert.player.musicplayer.ui.custom;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vattgert.player.musicplayer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewLifecycleFragment extends Fragment {

    static class ViewLifecycleOwner implements LifecycleOwner{
        private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

        @NonNull
        @Override
        public LifecycleRegistry getLifecycle() {
            return lifecycleRegistry;
        }
    }

    @Nullable ViewLifecycleOwner viewLifecycleOwner;

    @Nullable LifecycleOwner getLifecycleOwner(){
        return viewLifecycleOwner;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.wtf("MusicPlayer", "ViewLifecycleFragment onViewCreated");
        viewLifecycleOwner = new ViewLifecycleOwner();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(viewLifecycleOwner != null){
            Log.wtf("MusicPlayer", "ViewLifecycleFragment onStart");
            viewLifecycleOwner.getLifecycle().handleLifecycleEvent(Lifecycle.Event.ON_START);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(viewLifecycleOwner != null){
            Log.wtf("MusicPlayer", "ViewLifecycleFragment onResume");
            viewLifecycleOwner.getLifecycle().handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        }
    }

    @Override
    public void onPause() {
        if(viewLifecycleOwner != null){
            Log.wtf("MusicPlayer", "ViewLifecycleFragment onPause");
            viewLifecycleOwner.getLifecycle().handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
        }
        super.onPause();
    }

    @Override
    public void onStop() {
        if(viewLifecycleOwner != null){
            Log.wtf("MusicPlayer", "ViewLifecycleFragment onStop");
            viewLifecycleOwner.getLifecycle().handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        }
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        if(viewLifecycleOwner != null){
            Log.wtf("MusicPlayer", "ViewLifecycleFragment onDestroy");
            viewLifecycleOwner.getLifecycle().handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
            viewLifecycleOwner = null;
        }
        super.onDestroyView();
    }


}
