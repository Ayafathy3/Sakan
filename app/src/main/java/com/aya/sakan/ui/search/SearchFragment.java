package com.aya.sakan.ui.search;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.aya.sakan.R;
import com.aya.sakan.ui.home.adapters.Post;
import com.aya.sakan.util.Data;
import com.reginald.editspinner.EditSpinner;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private Button search;
    private int townId;
    private EditSpinner townEditSpinner, cityEditSpinner, homeTypeEditSpinner,
            contractTypeEditSpinner, lowPriceEditSpinner, highPriceEditSpinner;

    private ArrayList<Long> priceListRent, priceListSale;
    private String townString, cityString, homeTypeString,
            contractTypeString;
    private Long lowPriceString, highPriceString;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initiViews(view);
        setUpSpinners();
        setListeners();
    }

    private void initiViews(View view) {
        townEditSpinner = view.findViewById(R.id.town_name_spinner);
        cityEditSpinner = view.findViewById(R.id.city_name_spinner);
        homeTypeEditSpinner = view.findViewById(R.id.home_type_spinner);
        contractTypeEditSpinner = view.findViewById(R.id.contract_spinner);
        lowPriceEditSpinner = view.findViewById(R.id.less_price_spinner);
        highPriceEditSpinner = view.findViewById(R.id.most_price_spinner);
        search = view.findViewById(R.id.button_search);
    }

    private void setUpSpinners() {

        //town
        List<String> townList = new ArrayList<>();
        for (int i = 0; i < Data.getTowns().size(); i++) {
            townList.add(Data.getTowns().get(i).getTown_ar());
        }
        ArrayAdapter<String> townAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, townList);
        townEditSpinner.setAdapter(townAdapter);

        //homeType (فيلا .. شاليه .. منزل)
        ArrayAdapter<String> homeTypesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Data.getHomeTypes());
        homeTypeEditSpinner.setAdapter(homeTypesAdapter);

        // contract type
        ArrayAdapter<String> contractTypeAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Data.getContractType());
        contractTypeEditSpinner.setAdapter(contractTypeAdapter);

        fillPriceList();

    }

    private void fillPriceList() {
        // price rent
        priceListRent = new ArrayList<>();
        priceListRent.add(100L);
        priceListRent.add(150L);
        priceListRent.add(200L);
        priceListRent.add(250L);
        priceListRent.add(300L);
        priceListRent.add(350L);
        priceListRent.add(400L);
        priceListRent.add(450L);
        priceListRent.add(500L);
        priceListRent.add(550L);
        priceListRent.add(750L);
        priceListRent.add(1000L);
        priceListRent.add(2000L);
        priceListRent.add(3000L);
        priceListRent.add(4000L);
        priceListRent.add(5000L);
        priceListRent.add(7500L);
        priceListRent.add(10000L);
        priceListRent.add(15000L);
        priceListRent.add(20000L);

        // price sale
        priceListSale = new ArrayList<>();
        priceListSale.add(1000L);
        priceListSale.add(10000L);
        priceListSale.add(50000L);
        priceListSale.add(100000L);
        priceListSale.add(250000L);
        priceListSale.add(500000L);
        priceListSale.add(750000L);
        priceListSale.add(1000000L);
        priceListSale.add(5000000L);
        priceListSale.add(10000000L);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setListeners() {
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

                ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, cityList);
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
                        Toast.makeText(getActivity(), "Please choose town first", Toast.LENGTH_SHORT).show();
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

                if (contractTypeString.equals("بيع")) {
                    // low price
                    ArrayAdapter<Long> lowPriceAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, priceListSale);
                    lowPriceEditSpinner.setAdapter(lowPriceAdapter);

                    // high price
                    ArrayAdapter<Long> highPriceAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, priceListSale);
                    highPriceEditSpinner.setAdapter(highPriceAdapter);

                } else if (contractTypeString.equals("ايجار")) {
                    // low price
                    ArrayAdapter<Long> lowPriceAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, priceListRent);
                    lowPriceEditSpinner.setAdapter(lowPriceAdapter);

                    // high price
                    ArrayAdapter<Long> highPriceAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, priceListRent);
                    highPriceEditSpinner.setAdapter(highPriceAdapter);
                }
            }
        });

        lowPriceEditSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (contractTypeString == null) {
                        Toast.makeText(getActivity(), "Please choose contract type first", Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }
        });

        lowPriceEditSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (contractTypeString.equals("بيع")) {
                    lowPriceString = priceListSale.get(i);
                } else if (contractTypeString.equals("ايجار")) {
                    lowPriceString = priceListRent.get(i);
                }
                Log.i("lowPriceString", String.valueOf(lowPriceString));
            }
        });

        highPriceEditSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (contractTypeString == null) {
                        Toast.makeText(getActivity(), "Please choose contract type first", Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }
        });

        highPriceEditSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (contractTypeString.equals("بيع")) {
                    highPriceString = priceListSale.get(i);
                } else if (contractTypeString.equals("ايجار")) {
                    highPriceString = priceListRent.get(i);
                }
                Log.i("highPriceString", String.valueOf(highPriceString));
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();

                if (townString != null && cityString != null && homeTypeString != null &&
                        contractTypeString != null && lowPriceString != null &&
                        highPriceString != null) {

                    bundle.putString("town", townString);
                    bundle.putString("city", cityString);
                    bundle.putString("homeType", homeTypeString);
                    bundle.putString("contractType", contractTypeString);
                    bundle.putLong("lowPrice", lowPriceString);
                    bundle.putLong("highPrice", highPriceString);
                    ResultSearchFragment resultSearchFragment = new ResultSearchFragment();
                    resultSearchFragment.setArguments(bundle);

                    addFragment(resultSearchFragment);
                } else {
                    Toast.makeText(getActivity(), "Please choose all filters first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void addFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
