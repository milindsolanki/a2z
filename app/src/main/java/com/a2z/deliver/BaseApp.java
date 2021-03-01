package com.a2z.deliver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.a2z.deliver.deps.DaggerDeps;
import com.a2z.deliver.deps.Deps;
import com.a2z.deliver.networking.NetworkModule;

import java.io.File;

/**
 * Created by ennur on 6/28/16.
 */
public class BaseApp  extends AppCompatActivity {
    Deps deps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File cacheFile = new File(getCacheDir(), "responses");
        deps = DaggerDeps.builder().networkModule(new NetworkModule(cacheFile)).build();

    }

    public Deps getDeps() {
        return deps;
    }
}
