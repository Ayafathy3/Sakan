package com.aya.sakan.ui.addPost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aya.sakan.R;
import com.aya.sakan.ui.addPost.postImages.ImageAdapter;
import com.aya.sakan.ui.home.HomeActivity;
import com.aya.sakan.util.LoadingDialog;
import com.reginald.editspinner.EditSpinner;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddPostActivity extends AppCompatActivity implements IAddPostPresenterContact.View {
    private RecyclerView recyclerViewImages;
    private EditText locationEditText, titleEditText, areaEditText,
            priceEditText, roomsNumEditText, bathroomNumEditText;
    private CircleImageView addImages;
    private Button uploadPost;
    private int PICK_IMAGE_MULTIPLE = 0;
    private static final String TAG = "AddPostActivity";
    private ArrayList<Uri> mArrayUri;
    private AddPostPresenterImp addPostPresenterImp;
    private ImageAdapter imageAdapter;
    private EditSpinner homeTypeEditSpinner;
    private ArrayList<String> homeTypeList;
    private String homeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        intiViews();
        setUpSpinner();
        createInstance();
        setListeners();
    }

    private void setUpSpinner() {
        homeTypeList = new ArrayList<>();
        homeTypeList.add("Sale");
        homeTypeList.add("Rent");
        ArrayAdapter<String> accountTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, homeTypeList);
        homeTypeEditSpinner.setAdapter(accountTypeAdapter);
    }
    private void createInstance() {
        mArrayUri = new ArrayList<>();
    }

    private void setListeners() {

        addImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImagesFromGallery();
            }
        });

        uploadPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPostAction();
            }
        });

        homeTypeEditSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                homeType = homeTypeList.get(i).toString();
                Log.i("homeType", homeType);
            }
        });
    }

    private void uploadPostAction() {
        String location = locationEditText.getText().toString();
        String price = priceEditText.getText().toString();
        String area = areaEditText.getText().toString();
        String desc = titleEditText.getText().toString();
        String roomsNum = roomsNumEditText.getText().toString();
        String bathroomNum = bathroomNumEditText.getText().toString();

        if (mArrayUri.size() == 0) {
            Toast.makeText(AddPostActivity.this, "Please add images first", Toast.LENGTH_SHORT).show();
        } else if (location.isEmpty() || price.isEmpty() || area.isEmpty() || desc.isEmpty()
                || roomsNum.isEmpty() || bathroomNum.isEmpty()) {
            Toast.makeText(AddPostActivity.this, "Please fill this data first", Toast.LENGTH_SHORT).show();
        } else {
            LoadingDialog.showProgress(this);
            addPostPresenterImp = new AddPostPresenterImp(this, AddPostActivity.this);
            addPostPresenterImp.uploadPostAndImages(mArrayUri, desc, location, area, price, roomsNum + " غرف ", bathroomNum + " حمام ", homeType);
        }
    }

    private void getImagesFromGallery() {
        if (ActivityCompat.checkSelfPermission(AddPostActivity.this, Manifest.permission
                .READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddPostActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            return;
        }

        Intent galleryIntent = new Intent();
        galleryIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), PICK_IMAGE_MULTIPLE);
    }

    private void intiViews() {
        recyclerViewImages = findViewById(R.id.recycler_images);
        locationEditText = findViewById(R.id.location_edit);
        titleEditText = findViewById(R.id.title_edit);
        areaEditText = findViewById(R.id.area_edit);
        priceEditText = findViewById(R.id.price_edit);
        roomsNumEditText = findViewById(R.id.rooms_num_edit);
        bathroomNumEditText = findViewById(R.id.bathroom_num_edit);
        addImages = findViewById(R.id.new_post_image);
        uploadPost = findViewById(R.id.upload_button);
        homeTypeEditSpinner = findViewById(R.id.home_type_edit);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK) {

            //If Single image selected then it will fetch from Gallery
            if (data.getData() != null) {
                android.net.Uri mImageUri = data.getData();
                mArrayUri.add(mImageUri);
                Log.i(TAG, " Select one image: " + mImageUri.toString());
            } else {
                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        Uri uri = mClipData.getItemAt(i).getUri();
                        mArrayUri.add(uri);
                    }
                    Log.i(TAG, "Selected Images" + mArrayUri.size());
                }
            }

            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL);
            recyclerViewImages.setLayoutManager(staggeredGridLayoutManager); // set LayoutManager to RecyclerView

            imageAdapter = new ImageAdapter(mArrayUri, AddPostActivity.this, addImages, recyclerViewImages);
            recyclerViewImages.setAdapter(imageAdapter);
            recyclerViewImages.setVisibility(View.VISIBLE);
            addImages.setVisibility(View.GONE);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void goToHome(String message) {
        LoadingDialog.hideProgress();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        if (message.equals("Upload Successful")) {
            startActivity(new Intent(AddPostActivity.this, HomeActivity.class));
            finish();
        }
    }
}