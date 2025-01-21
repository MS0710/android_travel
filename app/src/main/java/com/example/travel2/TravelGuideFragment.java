package com.example.travel2;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TravelGuideFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TravelGuideFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String TAG = "TravelGuideFragment";
    private CardView cv_inOut,cv_internationality,cv_domestic;

    public TravelGuideFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TravelGuideFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TravelGuideFragment newInstance(String param1, String param2) {
        TravelGuideFragment fragment = new TravelGuideFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_travel_guide, container, false);

        cv_inOut = (CardView) view.findViewById(R.id.cv_inOut);
        cv_internationality = (CardView) view.findViewById(R.id.cv_internationality);
        cv_domestic = (CardView) view.findViewById(R.id.cv_domestic);
        cv_inOut.setOnClickListener(onClick);
        cv_internationality.setOnClickListener(onClick);
        cv_domestic.setOnClickListener(onClick);
        return view;
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.cv_inOut){
                Log.d(TAG, "onClick: cv_inOut");
                Intent intent = new Intent(getContext(),InOutActivity.class);
                startActivity(intent);
            }else if (v.getId() == R.id.cv_internationality){
                Log.d(TAG, "onClick: cv_internationality");
                Intent intent = new Intent(getContext(),InternationalityActivity.class);
                startActivity(intent);
            }else if (v.getId() == R.id.cv_domestic){
                Log.d(TAG, "onClick: cv_domestic");
                Intent intent = new Intent(getContext(),DomesticActivity.class);
                startActivity(intent);
            }
        }
    };
}