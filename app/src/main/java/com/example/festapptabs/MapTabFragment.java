package com.example.festapptabs;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapTabFragment extends Fragment implements GoogleMap.OnMarkerClickListener {

    public final static String MARKER_ID = "com.example.festlistdb.MARKER_ID";
    private static final LatLng BERLINSUMRAVE = new LatLng(52.482968, 13.388202);
    private static final LatLng BOARDMASTERS = new LatLng(50.440226, -5.043999);
    private static final LatLng FORBIDDENFRUIT = new LatLng(53.343119, -6.300048);
    private static final LatLng HIPHOPKEMPT = new LatLng(50.243542, 15.835805);
    private static final LatLng HURRICANE = new LatLng(53.159595, 9.522237);
    private static final LatLng ONELOVE = new LatLng(51.107051, 17.077328);
    private static final LatLng OUTLOOK = new LatLng(44.862887, 13.846340);
    private static final LatLng PALEO = new LatLng(46.402385, 6.211910);
    private static final LatLng REGGAELAND = new LatLng(52.546435, 19.671862);
    private static final LatLng SONAR = new LatLng(41.354528, 2.128107);
    private static final LatLng SUMMERJAM = new LatLng(51.023905, 6.921462);
    private static final LatLng SWEDENROCKFEST = new LatLng(56.047567, 14.579819);
    private static final LatLng TOMORROWLAND = new LatLng(51.091273, 4.385476);
    private static final LatLng WIRELESS = new LatLng(51.564080, -0.107043);
    private static final LatLng WOODSTOCK = new LatLng(52.610088, 14.668109);
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    MapView mapView;
    GoogleMap mMap;
    GoogleMap.OnInfoWindowClickListener MyOnInfoWindowClickListener
            = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            String markerId = marker.getId();
            // When touch InfoWindow on the market, display another screen.
            Intent i = new Intent(getActivity(), FestDesc.class);
            i.putExtra(MARKER_ID, markerId);
            startActivity(i);
        }
    };
    private Marker mBerlinSummerRave;
    private Marker mBoardmasters;
    private Marker mForbiddenfruit;
    private Marker mHipHopKemp;
    private Marker mHurricane;
    private Marker mOnelove;
    private Marker mOutlook;
    private Marker mPaleo;
    private Marker mReggaeland;
    private Marker mSonar;
    private Marker mSummerjam;
    private Marker mSwedenRockFest;
    private Marker mTommorowland;
    private Marker mWireless;
    private Marker mWoodstock;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map_tab_frag_3, container, false);

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        mMap = mapView.getMap();
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            //request permission
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_ASK_PERMISSIONS);
        }
        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        try {
            MapsInitializer.initialize(this.getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(50.440226, -5.043999), 2);
        mMap.animateCamera(cameraUpdate);
        return v;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addMarkersToMap();
        mMap.setOnInfoWindowClickListener(MyOnInfoWindowClickListener);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    private void addMarkersToMap() {
        mBerlinSummerRave = mMap.addMarker(new MarkerOptions()
                .position(BERLINSUMRAVE)
                .title("Berlin Summer Rave")
                .snippet("Flughafen Tempelhof, Platz der Luftbrücke, 12101 Berlin, Germany")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mForbiddenfruit = mMap.addMarker(new MarkerOptions()
                .position(FORBIDDENFRUIT)
                .title("Forbidden Fruit")
                .snippet("Irish Museum of Modern Art, Royal Hospital Kilmainham, Dublin 8")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        mSwedenRockFest = mMap.addMarker(new MarkerOptions()
                .position(SWEDENROCKFEST)
                .title("Sweden Rock Festival")
                .snippet("Nygatan 27 294 34 Solvesborg, Sweden")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        mSonar = mMap.addMarker(new MarkerOptions()
                .position(SONAR)
                .title("Sonar")
                .snippet("Fira Gran Via L'Hospitalet, Barcelona, Spain")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mHurricane = mMap.addMarker(new MarkerOptions()
                .position(HURRICANE)
                .title("Hurricane")
                .snippet("Eichenring, Scheeßel, Germany")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        mWireless = mMap.addMarker(new MarkerOptions()
                .position(WIRELESS)
                .title("Wireless")
                .snippet("Finsbury Park, London")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        mReggaeland = mMap.addMarker(new MarkerOptions()
                .position(REGGAELAND)
                .title("Reggaeland")
                .snippet("Plaza nad Wisla, Plock, Poland")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mWoodstock = mMap.addMarker(new MarkerOptions()
                .position(WOODSTOCK)
                .title("Woodstock")
                .snippet("Północna 4, 66-470 Kostrzyn nad Odrą, Poland")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        mSummerjam = mMap.addMarker(new MarkerOptions()
                .position(SUMMERJAM)
                .title("Summerjam")
                .snippet("Fühlinger See, Cologne, Germany")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mPaleo = mMap.addMarker(new MarkerOptions()
                .position(PALEO)
                .title("Paleo")
                .snippet("Route de Saint-Cergue 312, 1260 Nyon, Suisse")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        mTommorowland = mMap.addMarker(new MarkerOptions()
                .position(TOMORROWLAND)
                .title("Tommorowland")
                .snippet("PRC de Schorre, Schommelei, 2850 Boom, Belgium")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mBoardmasters = mMap.addMarker(new MarkerOptions()
                .position(BOARDMASTERS)
                .title("Boardmasters")
                .snippet("Trebelsue Farm Watergate Bay, Newquay, Cornwall TR8 4AN")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mHipHopKemp = mMap.addMarker(new MarkerOptions()
                .position(HIPHOPKEMPT)
                .title("Hip Hop Kemp")
                .snippet("Hradec Králové - Rusek, Czech Republic")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        mOutlook = mMap.addMarker(new MarkerOptions()
                .position(OUTLOOK)
                .title("Outlook")
                .snippet("Pula, Croatia")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mOnelove = mMap.addMarker(new MarkerOptions()
                .position(ONELOVE)
                .title("One Love")
                .snippet("Hala Stulecia, Wroclaw, Poland")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
    }
}