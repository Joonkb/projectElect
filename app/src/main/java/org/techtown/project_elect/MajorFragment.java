package org.techtown.project_elect;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.ArrayList;


public class MajorFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    public MajorFragment() {
        // Required empty public constructor
    }

    private ArrayAdapter univAdapter;
    private ArrayAdapter majorAdapter;
    private Spinner univSpinner;
    private Spinner majorSpinner;
    private RadioButton a;
    private RadioButton b;
    private Button map;
    private Button search;
    private Button people;
    private int[] Uni = {R.array.major_management, R.array.major_agriculture, R.array.major_animal,
            R.array.major_edu, R.array.major_social, R.array.major_it, R.array.major_veterinary, R.array.major_natural, R.array.major_humanities, R.array.major_caluture, R.array.major_dendrology};
    private double[] x = {37.869749, 37.870427, 37.868519, 37.870469, 37.866289, 37.868237, 37.868351, 37.871738, 37.867861, 37.869734, 37.866795 };
    private double[] y = {127.745718, 127.746029, 127.748935, 127.741641, 127.740511, 127.738837, 127.750912, 127.742541, 127.741216, 127.747822, 127.747425};
    private String[] lo = {"경영대학", "농업생명과학대학", "동물생명과학대학", "사범대학", "사회과학대학", "IT대학", "수의과학대학", "자연과학대학", "인문대학", "문화예술공과대학", "산림환경과학대학"};
    private String[] inf = {"경엉 1호관", "농생대 1호관", "동생대 1호관", "교육2호관", "사회과학관", "공학 4호관", "의학 1호관", "자연대 1호관", "인문대 1호관", "예슐 1호관", "산림과학대학 1호관"};
    private int[] major = {R.layout.major_1, R.layout.major_2, R.layout.major_3,R.layout.major_4};


    private int index;
    private int index_one;
    private int index_two;
    private Fragment fg;
    private ArrayList<ArrayList<int[]>> data;
    private ArrayList<String[]> strdata;
    private boolean radio = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        data = maker.make();
        strdata = maker.strmake();

        univSpinner = (Spinner) getView().findViewById(R.id.univSpinner);
        majorSpinner = (Spinner) getView().findViewById(R.id.majorSpinner);
        a = getView().findViewById(R.id.radioButton1);
        b = getView().findViewById(R.id.radioButton2);
        map = getView().findViewById(R.id.mapbutton);
        people = getView().findViewById(R.id.people);
        final Button searching_btn = (Button) getView().findViewById(R.id.search_btn);
        univAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.University, android.R.layout.simple_selectable_list_item);
        univSpinner.setAdapter(univAdapter);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio = false;
                univSpinner.setVisibility(View.VISIBLE);
                majorSpinner.setVisibility(View.VISIBLE);
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio = true;
                univSpinner.setVisibility(View.GONE);
                majorSpinner.setVisibility(View.GONE);
            }
        });
        univSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                index = position;
                index_one = position;
                majorAdapter = ArrayAdapter.createFromResource(getActivity(), Uni[position], android.R.layout.simple_spinner_item);
                majorSpinner.setAdapter(majorAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        majorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                index_two = position;
                if (radio)
                    fg = SelectFragment.newInstance(major, "major");
                else
                    fg = SelectFragment.newInstance(data.get(index_one).get(index_two), strdata.get(index_one)[index_two]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searching_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radio)
                    fg = SelectFragment.newInstance(major, "major");
                else
                    fg = SelectFragment.newInstance(data.get(index_one).get(index_two), strdata.get(index_one)[index_two]);
                setChildFragment(fg);
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (radio)
                    fg = MapFragment.newInstance(37.870759, 127.743571, "총 학생회" + " 투표장소", "대학본부");
                else

                    fg = MapFragment.newInstance(x[index], y[index], lo[index] + " 투표장소", inf[index]);
                setChildFragment(fg);
            }
        });
        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fg = SelectFragment.newInstance(data.get(index_one).get(index_two), strdata.get(index_one)[index_two]);
                setChildFragment(fg);


            }
        });

    }

    private void setChildFragment(Fragment child) {
        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();

        if (!child.isAdded()) {
            childFt.replace(R.id.child, child);
            childFt.addToBackStack(null);
            childFt.commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_major, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
