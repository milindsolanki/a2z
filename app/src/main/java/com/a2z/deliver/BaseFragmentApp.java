package com.a2z.deliver;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.a2z.deliver.deps.DaggerDeps;
import com.a2z.deliver.deps.Deps;
import com.a2z.deliver.networking.NetworkModule;

import java.io.File;

public class BaseFragmentApp extends Fragment {
    Deps deps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File cacheFile = new File(getActivity().getCacheDir(), "responses");
        deps = DaggerDeps.builder().networkModule(new NetworkModule(cacheFile)).build();
    }

    public Deps getDeps() {
        return deps;
    }
}
