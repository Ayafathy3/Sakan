package com.aya.sakan.ui.addPost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aya.sakan.R;
import com.aya.sakan.ui.addPost.postImages.ImageAdapter;
import com.aya.sakan.ui.home.HomeActivity;
import com.aya.sakan.ui.home.adapters.Post;
import com.aya.sakan.ui.search.SearchActivity;
import com.aya.sakan.ui.search.Town;
import com.aya.sakan.util.Data;
import com.aya.sakan.util.LoadingDialog;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.reginald.editspinner.EditSpinner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddPostActivity extends AppCompatActivity implements IAddPostPresenterContact.View {
    private RecyclerView recyclerViewImages;
    private EditText locationEditText, titleEditText, areaEditText,
            priceEditText;
    private CircleImageView addImages;
    private Button uploadPost;
    private ArrayList<Uri> mArrayUri;
    private AddPostPresenterImp addPostPresenterImp;
    private ImageAdapter imageAdapter;

    private int townId;
    private EditSpinner townEditSpinner, cityEditSpinner, homeTypeEditSpinner,
            contractTypeEditSpinner, roomsNumEditSpinner, bathroomNumEditSpinner;

    private ArrayList<String> roomsNumList, bathroomNumList;
    private String townString, cityString, homeTypeString,
            contractTypeString, roomsNumString, bathroomNumString;

    private ArrayAdapter<String> townAdapter, homeTypesAdapter, roomsNumAdapter, bathroomNumAdapter,
            contractTypeAdapter, cityAdapter;

    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        intiViews();
        setUpSpinners();
        getData();
        setListeners();
    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            post = (Post) bundle.getSerializable("post");
            uploadPost.setText(R.string.update_post);

            //edit text
            locationEditText.setText(post.getLocation());
            titleEditText.setText(post.getTitle());
            areaEditText.setText(post.getArea());
            priceEditText.setText(String.valueOf(post.getPrice()));

            //edit spinners

            homeTypeString = post.getHome_type();
            contractTypeString = post.getContractType();
            roomsNumString = post.getRoomsNum();
            bathroomNumString = post.getBathroomNum();
            townString = post.getTown();
            cityString = post.getCity();

            homeTypeEditSpinner.selectItem(homeTypesAdapter.getPosition(post.getHome_type()));
            contractTypeEditSpinner.selectItem(contractTypeAdapter.getPosition(post.getContractType()));
            roomsNumEditSpinner.selectItem(roomsNumAdapter.getPosition(post.getRoomsNum()));
            bathroomNumEditSpinner.selectItem(bathroomNumAdapter.getPosition(post.getBathroomNum()));

            townId = townAdapter.getPosition(post.getTown());
            townEditSpinner.selectItem(townId);


            //city
            Town town = Data.getTowns().get(townId);
            townId = town.getId();
            List<String> cityList = new ArrayList<>();
            for (int j = 0; j < Data.getCities(townId).size(); j++) {
                cityList.add(Data.getCities(townId).get(j).getTown_ar());
            }
            cityAdapter = new ArrayAdapter<>(AddPostActivity.this, android.R.layout.simple_spinner_dropdown_item, cityList);
            cityEditSpinner.setAdapter(cityAdapter);

            cityEditSpinner.selectItem(cityAdapter.getPosition(post.getCity()));

            // images
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL);
            recyclerViewImages.setLayoutManager(staggeredGridLayoutManager); // set LayoutManager to RecyclerView

            mArrayUri = new ArrayList<>();
            for (int i = 0; i < post.getImagesURL().size(); i++) {
                mArrayUri.add(Uri.parse(post.getImagesURL().get(i)));
            }
            imageAdapter = new ImageAdapter(mArrayUri, AddPostActivity.this, addImages, recyclerViewImages);
            recyclerViewImages.setAdapter(imageAdapter);
            recyclerViewImages.setVisibility(View.VISIBLE);
            addImages.setVisibility(View.GONE);

        } else {
            uploadPost.setText(R.string.upload);
            mArrayUri = new ArrayList<>();
        }
    }

    private void setUpSpinners() {
        //town
        List<String> townList = new ArrayList<>();
        for (int i = 0; i < Data.getTowns().size(); i++) {
            townList.add(Data.getTowns().get(i).getTown_ar());
        }
        townAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, townList);
        townEditSpinner.setAdapter(townAdapter);

        //homeType (فيلا .. شاليه .. منزل)
        homeTypesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Data.getHomeTypes());
        homeTypeEditSpinner.setAdapter(homeTypesAdapter);

        // contract type
        contractTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Data.getContractType());
        contractTypeEditSpinner.setAdapter(contractTypeAdapter);

        // rooms Number and bathroom num
        roomsNumList = new ArrayList<>();
        bathroomNumList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            roomsNumList.add(String.valueOf(i));
            bathroomNumList.add(String.valueOf(i));
        }
        roomsNumAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, roomsNumList);
        roomsNumEditSpinner.setAdapter(roomsNumAdapter);

        bathroomNumAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, bathroomNumList);
        bathroomNumEditSpinner.setAdapter(bathroomNumAdapter);

    }

    @SuppressLint("ClickableViewAccessibility")
    private void setListeners() {

        addImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.create(AddPostActivity.this)
                        .theme(R.style.ImagePickerTheme)
                        .limit(10).start();
            }
        });

        uploadPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadPostAction();
            }
        });

        townEditSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Town town = Data.getTowns().get(i);
                townString = town.getTown_ar();
                Log.i("townString", townString);

                //city
                townId = town.getId();
                List<String> cityList = new ArrayList<>();
                for (int j = 0; j < Data.getCities(townId).size(); j++) {
                    cityList.add(Data.getCities(townId).get(j).getTown_ar());
                }


                cityAdapter = new ArrayAdapter<>(AddPostActivity.this, android.R.layout.simple_spinner_dropdown_item, cityList);
                cityEditSpinner.setAdapter(cityAdapter);
            }
        });

        cityEditSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Town town = Data.getCities(townId).get(i);
                cityString = town.getTown_ar();
                Log.i("cityString", cityString);
            }
        });

        cityEditSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (townId == 0) {
                        Toast.makeText(AddPostActivity.this, "Please choose town first", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });


        homeTypeEditSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                homeTypeString = Data.getHomeTypes().get(i);
                Log.i("homeTypeString", homeTypeString);
            }
        });

        contractTypeEditSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                contractTypeString = Data.getContractType().get(i);
                Log.i("contractTypeString", contractTypeString);
            }
        });

        roomsNumEditSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                roomsNumString = roomsNumList.get(i);
                Log.i("roomsNumString", roomsNumString);
            }
        });

        bathroomNumEditSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                bathroomNumString = bathroomNumList.get(i);
                Log.i("bathroomNumString", bathroomNumString);
            }
        });
    }

    private void uploadPostAction() {
        String location = locationEditText.getText().toString();
        String price = priceEditText.getText().toString();
        String area = areaEditText.getText().toString();
        String desc = titleEditText.getText().toString();

        if (mArrayUri.size() == 0) {
            Toast.makeText(AddPostActivity.this, "Please add images first", Toast.LENGTH_SHORT).show();
        } else if (location.isEmpty() || price.isEmpty() || area.isEmpty() || desc.isEmpty()
                || townString == null || cityString == null || contractTypeString == null || homeTypeString == null
                || roomsNumString == null || bathroomNumString == null) {
            Toast.makeText(AddPostActivity.this, "Please fill this data first", Toast.LENGTH_SHORT).show();
        } else {
            LoadingDialog.showProgress(this);
            addPostPresenterImp = new AddPostPresenterImp(this, AddPostActivity.this);

            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();

            if (bundle != null) {

                List<String> imagesList = new ArrayList<>();
                for (int i = 0; i < mArrayUri.size(); i++) {
                    imagesList.add(String.valueOf(mArrayUri.get(i)));
                }
                addPostPresenterImp.updatePost(imagesList, desc, location, area, Long.valueOf(price),
                        roomsNumString, bathroomNumString,
                        homeTypeString, contractTypeString, townString, cityString, post.getPostId());
            } else {
                addPostPresenterImp.uploadPostAndImages(mArrayUri, desc, location, area, Long.valueOf(price),
                        roomsNumString, bathroomNumString,
                        homeTypeString, contractTypeString, townString, cityString);
            }
        }
    }

    private void intiViews() {
        recyclerViewImages = findViewById(R.id.recycler_images);
        locationEditText = findViewById(R.id.location_edit);
        titleEditText = findViewById(R.id.title_edit);
        areaEditText = findViewById(R.id.area_edit);
        priceEditText = findViewById(R.id.price_edit);
        bathroomNumEditSpinner = findViewById(R.id.bathroom_num_edit);
        addImages = findViewById(R.id.new_post_image);
        uploadPost = findViewById(R.id.upload_button);
        homeTypeEditSpinner = findViewById(R.id.home_type_edit);

        townEditSpinner = findViewById(R.id.town_name_spinner);
        cityEditSpinner = findViewById(R.id.city_name_spinner);
        contractTypeEditSpinner = findViewById(R.id.contract_spinner);
        roomsNumEditSpinner = findViewById(R.id.rooms_number_spinner);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            // Get a list of picked images
            List<Image> images = ImagePicker.getImages(data);
            // or get a single image only
            //Image image = ImagePicker.getFirstImageOrNull(data);

            for (int i = 0; i < images.size(); i++) {
                String path = images.get(i).getPath();
                Uri uri = Uri.fromFile(new File(path));
                mArrayUri.add(uri);
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
        if (message.equals("Upload Successful") || message.equals("Update Successful")) {
            startActivity(new Intent(AddPostActivity.this, HomeActivity.class));
            finish();
        }
    }
}