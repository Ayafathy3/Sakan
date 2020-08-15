package com.aya.sakan.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

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
            contractTypeEditSpinner, lowPriceEditSpinner, highPriceEditSpinner, roomsNumEditSpinner;

    private ArrayList<String> priceListRent, priceListSale, roomsNumList;
    private String townString, cityString, homeTypeString,
            contractTypeString, lowPriceString, highPriceString, roomsNumString;

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
        roomsNumEditSpinner = view.findViewById(R.id.rooms_number_spinner);
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

        // rooms Number
        roomsNumList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            roomsNumList.add(String.valueOf(i));
        }
        ArrayAdapter<String> roomsNumAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, roomsNumList);
        roomsNumEditSpinner.setAdapter(roomsNumAdapter);

        fillPriceList();

    }

    private void fillPriceList() {
        // price rent
        priceListRent = new ArrayList<>();
        priceListRent.add("100");
        priceListRent.add("150");
        priceListRent.add("200");
        priceListRent.add("250");
        priceListRent.add("300");
        priceListRent.add("350");
        priceListRent.add("400");
        priceListRent.add("450");
        priceListRent.add("500");
        priceListRent.add("550");
        priceListRent.add("750");
        priceListRent.add("1000");
        priceListRent.add("2000");
        priceListRent.add("3000");
        priceListRent.add("4000");
        priceListRent.add("5000");
        priceListRent.add("7500");
        priceListRent.add("10000");
        priceListRent.add("15000");
        priceListRent.add("20000");

        // price sale
        priceListSale = new ArrayList<>();
        priceListSale.add("1.000");
        priceListSale.add("10.000");
        priceListSale.add("50.000");
        priceListSale.add("100.000");
        priceListSale.add("250.000");
        priceListSale.add("500.000");
        priceListSale.add("750.000");
        priceListSale.add("1.000.000");
        priceListSale.add("5.000.000");
        priceListSale.add("10.000.000");
    }

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
                    ArrayAdapter<String> lowPriceAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, priceListSale);
                    lowPriceEditSpinner.setAdapter(lowPriceAdapter);

                    // high price
                    ArrayAdapter<String> highPriceAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, priceListSale);
                    highPriceEditSpinner.setAdapter(highPriceAdapter);

                } else if (contractTypeString.equals("ايجار")) {
                    // low price
                    ArrayAdapter<String> lowPriceAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, priceListRent);
                    lowPriceEditSpinner.setAdapter(lowPriceAdapter);

                    // high price
                    ArrayAdapter<String> highPriceAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, priceListRent);
                    highPriceEditSpinner.setAdapter(highPriceAdapter);
                }
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
                Log.i("lowPriceString", lowPriceString);
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
                Log.i("highPriceString", highPriceString);
            }
        });

        roomsNumEditSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                roomsNumString = roomsNumList.get(i);
                Log.i("roomsNumString", roomsNumString);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();

                bundle.putString("town", townString);
                bundle.putString("city", cityString);
                bundle.putString("homeType", homeTypeString);
                bundle.putString("contractType", contractTypeString);
                bundle.putString("lowPrice", lowPriceString);
                bundle.putString("highPrice", highPriceString);
                bundle.putString("roomsNum", roomsNumString);
                ResultSearchFragment resultSearchFragment = new ResultSearchFragment();
                resultSearchFragment.setArguments(bundle);

                addFragment(resultSearchFragment);
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
