package com.harmis.imagepicker.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.harmis.imagepicker.R;
import com.harmis.imagepicker.adapter.CropImageAdapter;
import com.harmis.imagepicker.adapter.ImageListAdapter;
import com.harmis.imagepicker.interfaces.OnImageClickPosition;
import com.harmis.imagepicker.model.Images;
import com.harmis.imagepicker.utils.CommonKeyword;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CropImageActivity extends AppCompatActivity implements OnImageClickPosition {

    List<String> imageList = new ArrayList<>();
    List<Images> imageLists = new ArrayList<>();
    ViewPager viewPager;
    CropImageAdapter cropImageAdapter;
    ImageListAdapter imagesAdapter;
    RecyclerView recyclerImages;
    int REQ_CODE_CROP_PHOTO = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        recyclerImages = (RecyclerView) findViewById(R.id.recyclerImages);

        imageList = (List<String>) getIntent().getSerializableExtra(CommonKeyword.RESULT);

        imageLists.clear();
        for(int i=0;i<imageList.size();i++){
            Images images = new Images();
            images.setChecked(false);
            if(i==0)
                images.setChecked(true);
            images.setImageUrl(imageList.get(i));

            imageLists.add(images);
        }

        cropImageAdapter = new CropImageAdapter(this, imageLists, this);
        viewPager.setAdapter(cropImageAdapter);
        viewPager.setOffscreenPageLimit(0);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        imagesAdapter = new ImageListAdapter(this, imageLists, this);
        recyclerImages.setLayoutManager(layoutManager);
        recyclerImages.setHasFixedSize(false);
        recyclerImages.setAdapter(imagesAdapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setImageList();
                imageLists.get(position).setChecked(true);
                imagesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    void setImageList(){
        for(int i=0;i<imageList.size();i++){
            imageLists.get(i).setChecked(false);
        }
    }

    @Override
    public void onImageClickPosition(int position, boolean isChecked) {
        setImageList();
        viewPager.setCurrentItem(position);
        imageLists.get(position).setChecked(isChecked);
        cropImageAdapter.notifyDataSetChanged();
        imagesAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQ_CODE_CROP_PHOTO) {
            if (resultCode == CropActivity.RESULT_OK) {
                imageLists.get(viewPager.getCurrentItem()).setImageUrl(data.getData().toString());
                cropImageAdapter.notifyDataSetChanged();
                imagesAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_crop, menu);
        // return true so that the menu pop up is opened
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_crop) {
            Log.e("log", "path : " + imageList.get(viewPager.getCurrentItem()));
            Intent cropImageIntent = new Intent(null, Uri.fromFile(new File(imageList.get(viewPager.getCurrentItem()))), this, CropActivity.class);
            startActivityForResult(cropImageIntent, REQ_CODE_CROP_PHOTO);
            // Toast.makeText(getApplicationContext(), "Crop", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.menu_done) {
            Intent intent = new Intent();
            intent.putExtra(CommonKeyword.RESULT, (Serializable) imageLists);
            setResult(CommonKeyword.RESULT_CODE_CROP_IMAGE, intent);
            finish();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}
