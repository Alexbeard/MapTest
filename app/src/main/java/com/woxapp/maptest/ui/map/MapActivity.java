package com.woxapp.maptest.ui.map;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.maps.model.DirectionsResult;
import com.woxapp.maptest.R;
import com.woxapp.maptest.databinding.ActivityMainBinding;
import com.woxapp.maptest.databinding.CustomAutocompleteBinding;
import com.woxapp.maptest.entity.GeoSearchResult;
import com.woxapp.maptest.ui.catalog.CatalogActivity;
import com.woxapp.maptest.ui.map.controller.GoogleMapController;
import com.woxapp.maptest.ui.map.controller.MapController;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, MapContract.View {

    ActivityMainBinding binding;
    private Dialog dialog;
    private MapContract.Presenter presenter;
    private MapController mapController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        if (googleServicesAvailable()) {
            initMap();
        }
        presenter = new MapPresenter(this);
        initViews();
    }

    private void initViews() {

        binding.addBtn.setOnClickListener(v -> {
            if (2 <= mapController.getAddresses().size() && mapController.getAddresses().size() <= 5) {
                showDialog();
            }
        });

        binding.startBtn.setOnClickListener(v -> {
            mapController.clearWay();
            presenter.getDirection(mapController.getAddresses());
        });

        binding.loadBtn.setOnClickListener(view -> {
            CatalogActivity.start(this);
        });

        binding.clearMap.setOnClickListener(view -> {
            clearAll();
        });

        setUpAutoComplete(binding.startLocation);
        setUpAutoComplete(binding.endLocation);
    }

    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
    }


    public boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            api.getErrorDialog(this, isAvailable, 0).show();
        } else {
            Toast.makeText(this, "Fail connect to play services", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapController = new GoogleMapController(googleMap);
    }

    @Override
    public void directionLoadSuccess(DirectionsResult result) {
        mapController.addPolyline(result);
        mapController.positionCamera(result.routes[0]);
        mapController.setAnimation(result, R.drawable.ic_car);
    }


    private void clearAll() {
        mapController.clearMap();
        binding.startLocation.autocomplete.setText("");
        binding.endLocation.autocomplete.setText("");
    }

    public void showDialog() {
        dialog = new MaterialDialog.Builder(this)
                .title("Additional points")
                .theme(Theme.LIGHT)
                .backgroundColor(getResources().getColor(android.R.color.darker_gray))
                .positiveColorRes(R.color.colorAccent)
                .negativeColorRes(R.color.colorAccent)
                .customView(R.layout.dialog, true)
                .positiveText(R.string.yes)
                .onPositive((dialog, which) -> {

                })
                .build();
        setUpAutoComplete(DataBindingUtil.bind(dialog.findViewById(R.id.dialogAutoComplete)));
        dialog.show();
    }

    private void setUpAutoComplete(CustomAutocompleteBinding customAutocomplete) {


        customAutocomplete.autocomplete.setThreshold(2);
        customAutocomplete.autocomplete.setAdapter(new GeoAutoCompleteAdapter(this));

        customAutocomplete.autocomplete.setOnItemClickListener((adapterView, view, position, id) -> {
            GeoSearchResult result = (GeoSearchResult) adapterView.getItemAtPosition(position);
            customAutocomplete.autocomplete.setText(result.getAddress());
            mapController.addAddress(result);
        });

        customAutocomplete.autocomplete.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    customAutocomplete.geoAutocompleteClear.setVisibility(View.VISIBLE);
                } else {
                    customAutocomplete.geoAutocompleteClear.setVisibility(View.GONE);
                }
            }
        });

        customAutocomplete.geoAutocompleteClear.setOnClickListener(v -> {
            mapController.clearMarker(customAutocomplete.autocomplete.getText().toString());
            customAutocomplete.autocomplete.setText("");
        });
    }

}
