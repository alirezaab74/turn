package com.example.turn.Activity.Main.Fragment;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.turn.Activity.Main.Adapter.AdRecycResTimes;
import com.example.turn.Activity.Main.Model.ModAlerts;
import com.example.turn.Activity.Main.Adapter.ListViewAdapter;
import com.example.turn.Activity.Main.Model.ModResTime;
import com.example.turn.R;

import java.util.ArrayList;

public class frTab_reserve extends Fragment implements SearchView.OnQueryTextListener {

    //  linearSelectFilters
    private AlertDialog alertDialog;
    private LinearLayout linearSelectFilters;
    private LinearLayout linearSelectFiltersBtn;

    private TextView txtFrResTitle_city;
    private TextView txtFrRes_city;
    private TextView txtFrResTitle_takhasos;
    private TextView txtFrRes_takhasos;
    private TextView txtFrResTitle_darmonghah;
    private TextView txtFrRes_darmonghah;
    private TextView txtFrResTitle_time;
    private TextView txtFrRes_time;
    private TextView txtFrResTitle_doctor;
    private TextView txtFrRes_doctor;

    private LinearLayout linearFrRes_city;
    private LinearLayout linearFrRes_takhasos;
    private LinearLayout linearFrRes_darmonghah;
    private LinearLayout linearFrRes_time;
    private LinearLayout linearFrRes_doctor;

    private Button btnFrRes_filter;
    private Button btnFrRes_next;

    //------------ linearResTimes
    private LinearLayout linearResTimes;
    private LinearLayout linearResTimesBtn;
    private RecyclerView rcycRT;

    private ArrayList<ModResTime> arrayListResTimes;

    private Button btnRT_next;
    private Button btnRT_previous;

    //------------ linearPazireshPage
    private LinearLayout linearPazireshPage;
    private LinearLayout linearPazireshPageBtn;
    private TextView txtPP_markazName;
    private TextView txtPP_doctorName;
    private TextView txtPP_motakhasesName;
    private TextView txtPP_datePP;
    private RadioGroup radioGPPP_CodMeli;
    private EditText edtFrPP_Cod;
    private Button btnPP_search;

    private LinearLayout linearPazireshPage2;
    private EditText edtFrPP_name;
    private EditText edtFrPP_family;
    private EditText edtFrPP_fatherName;
    private RadioGroup radioGPPP_sex;
    private EditText edtFrPP_phone;
    private LinearLayout linearFrPP_City;
    private TextView txtFrPP_city;
    private EditText edtFrPP_address;
    private LinearLayout linearFrPP_bime;
    private TextView txtFrPP_bime;

    private Button btnPP_previous;

    public static frTab_reserve newInstance() {

        Bundle args = new Bundle();
        frTab_reserve fragment = new frTab_reserve();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_tab_reserve, container, false);
        selectFilters(view);
        reservationTimes(view);
        paziresh(view);

        linearSelectFilters.setVisibility(View.VISIBLE);
        linearSelectFiltersBtn.setVisibility(View.VISIBLE);

        linearResTimes.setVisibility(View.GONE);
        linearResTimesBtn.setVisibility(View.GONE);

        linearPazireshPage.setVisibility(View.GONE);
        linearPazireshPageBtn.setVisibility(View.GONE);
        linearPazireshPage2.setVisibility(View.GONE);

        return view;
    }

    private void paziresh(View view) {

        linearPazireshPage = view.findViewById(R.id.linearPazireshPage);
        linearPazireshPageBtn = view.findViewById(R.id.linearPazireshPageBtn);
        txtPP_markazName = view.findViewById(R.id.txtPP_markazName);
        txtPP_doctorName = view.findViewById(R.id.txtPP_doctorName);
        txtPP_motakhasesName = view.findViewById(R.id.txtPP_motakhasesName);
        txtPP_datePP = view.findViewById(R.id.txtPP_datePP);
        radioGPPP_CodMeli = view.findViewById(R.id.radioGPPP_CodMeli);
        edtFrPP_Cod = view.findViewById(R.id.edtFrPP_Cod);
        btnPP_search = view.findViewById(R.id.btnPP_search);


        btnPP_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "اجرا شدن لودینگ", Toast.LENGTH_SHORT).show();
                linearPazireshPage2.setVisibility(View.VISIBLE);
            }
        });

        radioGPPP_CodMeli.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioBtnPP_CodMeli:
                        edtFrPP_Cod.setHint("کد ملی");
                        break;
                    case R.id.radioBtnPP_NumErja:
                        edtFrPP_Cod.setHint("کد ارجا");
                        break;
                }
            }
        });

        linearPazireshPage2 = view.findViewById(R.id.linearPazireshPage2);
        edtFrPP_name = view.findViewById(R.id.edtFrPP_name);
        edtFrPP_family = view.findViewById(R.id.edtFrPP_family);
        edtFrPP_fatherName = view.findViewById(R.id.edtFrPP_fatherName);
        radioGPPP_sex = view.findViewById(R.id.radioGPPP_sex); // radioBtnPP_female - radioBtnPP_male
        edtFrPP_phone = view.findViewById(R.id.edtFrPP_phone);
        linearFrPP_City = view.findViewById(R.id.linearFrPP_City);
        txtFrPP_city = view.findViewById(R.id.txtFrPP_city);
        edtFrPP_address = view.findViewById(R.id.edtFrPP_address);
        linearFrPP_bime = view.findViewById(R.id.linearFrPP_bime);
        txtFrPP_bime = view.findViewById(R.id.txtFrPP_bime);

        btnPP_previous = view.findViewById(R.id.btnPP_previous);
        btnPP_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                linearResTimes.setVisibility(View.VISIBLE);
                linearResTimesBtn.setVisibility(View.VISIBLE);
                linearPazireshPage.setVisibility(View.GONE);
                linearPazireshPageBtn.setVisibility(View.GONE);

            }
        });

    }

    private void reservationTimes(View view) {
        linearResTimes = view.findViewById(R.id.linearResTimes);
        linearResTimesBtn = view.findViewById(R.id.linearResTimesBtn);
        rcycRT = view.findViewById(R.id.rcycRT);
        arrayListResTimes = new ArrayList();

        for (int i = 0; i <= 10; i++) tempData(i);
        AdRecycResTimes adapterResTimes = new AdRecycResTimes(getContext(),arrayListResTimes);
        rcycRT.setAdapter(adapterResTimes);


        btnRT_next = view.findViewById(R.id.btnRT_next);
        btnRT_previous = view.findViewById(R.id.btnRT_previous);
        btnRT_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearPazireshPage.setVisibility(View.VISIBLE);
                linearPazireshPageBtn.setVisibility(View.VISIBLE);
                linearResTimes.setVisibility(View.GONE);
                linearResTimesBtn.setVisibility(View.GONE);
            }
        });
        btnRT_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                linearSelectFilters.setVisibility(View.VISIBLE);
                linearSelectFiltersBtn.setVisibility(View.VISIBLE);
                linearResTimes.setVisibility(View.GONE);
                linearResTimesBtn.setVisibility(View.GONE);

            }
        });


    }

    private void tempData(int i) {
        ModResTime time = new ModResTime();
        time.hospitalName = "بیمارستان دی " + i;
        time.shift = "صبح";
        time.doctorName = "دکتر چیزی";
        time.takhasos = "فک و صورت";
        time.date = "1399/01/0" + i;
        time.num = "" + i;
        time.status = "ok";
        arrayListResTimes.add(time);
    }

    //    SearchView
    private void alertDialogShow(final String tag) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.item_list_first_fr, null, false);
        Button btnFr = layout.findViewById(R.id.btnFr);
        btnFr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        if (tag.equals("city"))
            animalNameListSearchView = new String[]{"اون شهر", "این شهر", "اراک", "چیز"};
        else if (tag.equals("takhasos"))
            animalNameListSearchView = new String[]{"سر", "سندرم دست بی قرار", "یه سندرم دیگه", "چیز"};
        else if (tag.equals("darmonghah"))
            animalNameListSearchView = new String[]{"درمانگاه سر کوچه", "درمانگاه سر خیابون", "درمانگاه بغل دستمون", "چیز"};
        else if (tag.equals("time"))
            animalNameListSearchView = new String[]{"دیروز :|", "هفته ی بعد", "ماه بعد", "چیز"};
        else if (tag.equals("doctor"))
            animalNameListSearchView = new String[]{"دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر چیز زاده", "چیز"};

        if (arraylistSearchView.size() != 0)
            arraylistSearchView.clear();

        listSearchView = layout.findViewById(R.id.listviewFr);
        for (int i = 0; i < animalNameListSearchView.length; i++) {
            ModAlerts modAlerts = new ModAlerts(animalNameListSearchView[i]);
            arraylistSearchView.add(modAlerts);
        }
        adapterSearchView = new ListViewAdapter(getContext(), arraylistSearchView);
        listSearchView.setAdapter(adapterSearchView);

        editsearchSearchView = layout.findViewById(R.id.searchFr);
        editsearchSearchView.setOnQueryTextListener(this);


        listSearchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String txt = animalNameListSearchView[i] + "";
                if (tag.equals("city"))
                    txtFrRes_city.setText(txt + "");
                else if (tag.equals("takhasos"))
                    txtFrRes_takhasos.setText(txt + "");
                else if (tag.equals("darmonghah"))
                    txtFrRes_darmonghah.setText(txt + "");
                else if (tag.equals("time"))
                    txtFrRes_time.setText(txt + "");
                else if (tag.equals("doctor"))
                    txtFrRes_doctor.setText(txt + "");
                alertDialog.dismiss();
            }
        });


        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void selectFilters(View view) {
        linearSelectFilters = view.findViewById(R.id.linearSelectFilters);
        linearSelectFiltersBtn = view.findViewById(R.id.linearSelectFiltersBtn);
        txtFrResTitle_city = view.findViewById(R.id.txtFrResTitle_city);
        txtFrRes_city = view.findViewById(R.id.txtFrRes_city);
        txtFrResTitle_takhasos = view.findViewById(R.id.txtFrResTitle_takhasos);
        txtFrRes_takhasos = view.findViewById(R.id.txtFrRes_takhasos);
        txtFrResTitle_darmonghah = view.findViewById(R.id.txtFrResTitle_darmonghah);
        txtFrRes_darmonghah = view.findViewById(R.id.txtFrRes_darmonghah);
        txtFrResTitle_time = view.findViewById(R.id.txtFrResTitle_time);
        txtFrRes_time = view.findViewById(R.id.txtFrRes_time);
        txtFrResTitle_doctor = view.findViewById(R.id.txtFrResTitle_doctor);
        txtFrRes_doctor = view.findViewById(R.id.txtFrRes_doctor);

        linearFrRes_city = view.findViewById(R.id.linearFrRes_city);
        linearFrRes_takhasos = view.findViewById(R.id.linearFrRes_takhasos);
        linearFrRes_darmonghah = view.findViewById(R.id.linearFrRes_darmonghah);
        linearFrRes_time = view.findViewById(R.id.linearFrRes_time);
        linearFrRes_doctor = view.findViewById(R.id.linearFrRes_doctor);

        btnFrRes_filter = view.findViewById(R.id.btnFrRes_filter);
        btnFrRes_next = view.findViewById(R.id.btnFrRes_next);

//  Clicks
        linearFrRes_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogShow("city");
            }
        });
        linearFrRes_takhasos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogShow("takhasos");
            }
        });
        linearFrRes_darmonghah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogShow("darmonghah");
            }
        });
        linearFrRes_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogShow("time");
            }
        });

        linearFrRes_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogShow("doctor");
            }
        });

        btnFrRes_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: clear all

                txtFrRes_city.setText("\t" + "تمام شهرها");
                txtFrRes_takhasos.setText("\t" + "تمام تخصص ها");
                txtFrRes_darmonghah.setText("\t" + "تمام درمانگاه ها");
                txtFrRes_time.setText("\t" + "تمام زمان ها");
                txtFrRes_doctor.setText("\t" + "تمام دکتر ها");

            }
        });

        btnFrRes_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearSelectFilters.setVisibility(View.GONE);
                linearSelectFiltersBtn.setVisibility(View.GONE);
                linearResTimes.setVisibility(View.VISIBLE);
                linearResTimesBtn.setVisibility(View.VISIBLE);

            }
        });


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    //  SearchView -------------------------------------------------------------
    private ListView listSearchView;
    private ListViewAdapter adapterSearchView;
    private SearchView editsearchSearchView;
    private String[] animalNameListSearchView;
    private ArrayList<ModAlerts> arraylistSearchView = new ArrayList<ModAlerts>();

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapterSearchView.filter(text);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


}