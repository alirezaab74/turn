package com.example.turn.Activity.Main.Fragment.FragmentReserve;


import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.azimolabs.maskformatter.MaskFormatter;
import com.example.turn.Activity.Main.Adapter.AdRecycPopUp;
import com.example.turn.Activity.Main.Fragment.FragmentReserve.Adapter.AdRecycResTimes;
import com.example.turn.Activity.Main.Adapter.onClickInterface;
import com.example.turn.Activity.Main.Model.ModAlerts;
import com.example.turn.Activity.Main.Fragment.FragmentReserve.Model.ModResTime;
import com.example.turn.Classes.setConnectionVolley;
import com.example.turn.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class frTab_reserve extends Fragment implements SearchView.OnQueryTextListener {

    //  linearSelectFilters
    private AlertDialog alertDialogFilter;
    private LinearLayout linearSelectFilters;
    private LinearLayout linearSelectFiltersBtn;

    private TextView txtFrRes_city;
    private TextView txtFrRes_takhasos;
    private TextView txtFrRes_darmonghah;
    private TextView txtFrRes_time;
    private TextView txtFrRes_doctor;

    private LinearLayout linearFrRes_city;
    private LinearLayout linearFrRes_takhasos;
    private LinearLayout linearFrRes_darmonghah;
    private LinearLayout linearFrRes_time;
    private LinearLayout linearFrRes_doctor;

    private Button btnFrRes_filter;
    private Button btnFrRes_next;

    private ArrayList dataCity;
    private ArrayList dataTakhasos;
    private ArrayList dataTakhasos2;
    private ArrayList dataHospital;
    private ArrayList dataHospital2;
    private ArrayList dataTime;
    private ArrayList dataDoctor;

    private ArrayList dataCityID;
    private ArrayList dataTakhasosID;
    private ArrayList dataTakhasosID2;
    private ArrayList dataHospitalID;
    private ArrayList dataHospitalID2;
    private ArrayList dataTimeID;
    private ArrayList dataDoctorID;

    private String cityId = "";
    private String takhasosId = "";
    private String hospiralId = "";
    private String timeId = "";
    private String doctorId = "";

    //------------ linearResTimes
    private AlertDialog alertDialogTavafoghName;
    private AlertDialog alertFilterRT;
    private LinearLayout linearResTimes;
    private LinearLayout linearResTimesBtn;
    private RecyclerView rcycRT;
    private Button btnRT_filter;
    private SwipeRefreshLayout refreshRT;

    private ArrayList<ModResTime> arrayListResTimes;

    private ImageView btnRT_previous;
    private String dr_prg_hsp_mdc_spc_date_id = "";
    private int positionItemRecycleView = -1;

    private Boolean isScrollingFP = false;
    private int currentItemFP = 0, totalItemsFP = 0, scrollOutItemsFP = 0;
    private LinearLayoutManager rowManager;
    private Boolean boolCheckNetFP = false;
    private int pageNumber = -1;

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
    private RadioButton radioBtnPP_female;
    private RadioButton radioBtnPP_male;
    private EditText edtFrPP_phone;
    private LinearLayout linearFrPP_City;
    private TextView txtFrPP_city;
    private EditText edtFrPP_address;
    private LinearLayout linearFrPP_bime;
    private TextView txtFrPP_bime;
    private ImageView imgPP_drPic;

    private Button btnPP_previous;

    private String bimeSamane;
    private String addressSamane;
    private String sexSamane;
    private String cityIdSamane;
    private String phoneNumSamane;
    private String fatherNameSamane;
    private String lastNameSamane;
    private String firstNameSamane;

    //------------------------------------
    private AlertDialog alertDialogLoding;

    public static frTab_reserve newInstance() {

        Bundle args = new Bundle();
        frTab_reserve fragment = new frTab_reserve();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_tab_reserve, container, false);

        loading();

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

    private void loading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.loading, null, false);

        GifImageView gifImage = layout.findViewById(R.id.gifImage);
        gifImage.setImageResource(R.drawable.loading);

        builder.setView(layout);
        alertDialogLoding = builder.create();
        alertDialogLoding.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }

    private void paziresh(View view) {
        final boolean meliOrErja = false; // default is meli and its false
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
//                Toast.makeText(getContext(), "اجرا شدن لودینگ", Toast.LENGTH_SHORT).show();
                linearPazireshPage2.setVisibility(View.VISIBLE);
//                TODO: Send id , meli and erja to link3 -----------------------------------------


                String meliOrEjra = edtFrPP_Cod.getText().toString();
                JSONObject object = new JSONObject();
                // in khat 214 chie   صاشفwhatspp
                try {
                    object.put("mdc_id", dr_prg_hsp_mdc_spc_date_id);

                    if (meliOrErja) {
                        object.put("pp_id", meliOrEjra);
                        object.put("srv_id", "");
                    } else {
                        object.put("pp_id", "");
                        object.put("srv_id", meliOrEjra);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                new setConnectionVolley(getContext(), "http://nobat.mazums.ac.ir/TurnAppApi/turn/fSearchPatByPpId?hsp_id=" + "" + "&pp_id=" + "", object
                ).connectStringRequest(new setConnectionVolley.OnResponse() {
                    @Override
                    public void OnResponse(String response) {
                        setDataFromSamane(response);
                    }
                });

//                String res = "{\"status\":\"yes\",\"message\":\"\",\"data\":{\"ins_id\":\"بیمه ی تکمیلی\",\"home_adr\":\"اراک انتهای خیابان\",\"is_sex\":\"0\",\"city_id\":\"1\",\"home_mbl\":\"09386977100\",\"father_name\":\"علی\",\"last_name\":\"حسینی\",\"first_name\":\"سیدمیثم\"}}";
//                setDataFromSamane(res);


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
        radioBtnPP_female = view.findViewById(R.id.radioBtnPP_female);
        radioBtnPP_male = view.findViewById(R.id.radioBtnPP_male);
        edtFrPP_phone = view.findViewById(R.id.edtFrPP_phone);
        linearFrPP_City = view.findViewById(R.id.linearFrPP_City);
        txtFrPP_city = view.findViewById(R.id.txtFrPP_city);
        edtFrPP_address = view.findViewById(R.id.edtFrPP_address);
        linearFrPP_bime = view.findViewById(R.id.linearFrPP_bime);
        txtFrPP_bime = view.findViewById(R.id.txtFrPP_bime);
        imgPP_drPic = view.findViewById(R.id.imgPP_drPic);

        MaskFormatter maskFormatter = new MaskFormatter("999 999 9999", edtFrPP_phone);
        edtFrPP_phone.setInputType(InputType.TYPE_CLASS_NUMBER);
        edtFrPP_phone.setHint("99 999 99999");
        edtFrPP_phone.addTextChangedListener(maskFormatter);

        btnPP_previous = view.findViewById(R.id.btnPP_previous);
        btnPP_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                previousPage(linearPazireshPage, linearPazireshPageBtn, linearResTimes, linearResTimesBtn);

            }
        });

    }

    private void setDataFromSamane(String res) {
        try {
            JSONObject object = new JSONObject(res);
            String status = object.getString("status");
            String message = object.getString("message");
            if (status.equals("yes")) {
                linearPazireshPage2.setVisibility(View.VISIBLE);
                String data = object.getString("data");
                JSONObject objectData = new JSONObject(data);

                bimeSamane = objectData.getString("ins_id");
                addressSamane = objectData.getString("home_adr");
                sexSamane = objectData.getString("is_sex"); // zero = man | one = woman
                cityIdSamane = objectData.getString("city_id");
                phoneNumSamane = objectData.getString("home_mbl");
                fatherNameSamane = objectData.getString("father_name");
                lastNameSamane = objectData.getString("last_name");
                firstNameSamane = objectData.getString("first_name");

                edtFrPP_name.setText(firstNameSamane + "");
                edtFrPP_name.setEnabled(false);
                edtFrPP_family.setText(lastNameSamane + "");
                edtFrPP_family.setEnabled(false);
                edtFrPP_fatherName.setText(fatherNameSamane + "");
                edtFrPP_phone.setText(phoneNumSamane + "");
                txtFrPP_city.setText(cityIdSamane + " =cityID");
                edtFrPP_address.setText(addressSamane + "");
                txtFrPP_bime.setText(bimeSamane + "");

                if (sexSamane.equals("0"))
                    radioBtnPP_male.setChecked(true);
                else if (sexSamane.equals("1"))
                    radioBtnPP_female.setChecked(true);


            } else
//                new ShowMessage(getContext()).ShowMessage_SnackBar(linearSelectFilters, message + "");
                Toast.makeText(getContext(), message + "", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void reservationTimes(final View view) {
        linearResTimes = view.findViewById(R.id.linearResTimes);
        linearResTimesBtn = view.findViewById(R.id.linearResTimesBtn);
        btnRT_previous = view.findViewById(R.id.btnRT_previous);
        rcycRT = view.findViewById(R.id.rcycRT);
        btnRT_filter = view.findViewById(R.id.btnRT_filter);
        refreshRT = view.findViewById(R.id.refreshRT);

        arrayListResTimes = new ArrayList();

        btnRT_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousPage(linearResTimes, linearResTimesBtn, linearSelectFilters, linearSelectFiltersBtn);

                txtFrRes_city = view.findViewById(R.id.txtFrRes_city);
                txtFrRes_takhasos = view.findViewById(R.id.txtFrRes_takhasos);
                txtFrRes_darmonghah = view.findViewById(R.id.txtFrRes_darmonghah);
                txtFrRes_time = view.findViewById(R.id.txtFrRes_time);
                txtFrRes_doctor = view.findViewById(R.id.txtFrRes_doctor);

            }
        });

        refreshRT.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });


        btnRT_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(false);
                LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.layout_select_filters, null, false);
                LinearLayout layoutBtnsRT = layout.findViewById(R.id.layoutBtnsRT);
                layoutBtnsRT.setVisibility(View.VISIBLE);

                Button btnRT_dismis = layout.findViewById(R.id.btnRT_dismis);
                Button btnRT_reset = layout.findViewById(R.id.btnRT_reset);
                Button btnRT_search = layout.findViewById(R.id.btnRT_search);

                btnRT_dismis.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertFilterRT.dismiss();
                    }
                });

                btnRT_reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        defaultDataDropDown();
                    }
                });

                btnRT_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertFilterRT.dismiss();
                        refreshData();
                    }
                });

                LinearLayout linearFrRes_city = layout.findViewById(R.id.linearFrRes_city);
                LinearLayout linearFrRes_takhasos = layout.findViewById(R.id.linearFrRes_takhasos);
                LinearLayout linearFrRes_darmonghah = layout.findViewById(R.id.linearFrRes_darmonghah);
                LinearLayout linearFrRes_time = layout.findViewById(R.id.linearFrRes_time);
                LinearLayout linearFrRes_doctor = layout.findViewById(R.id.linearFrRes_doctor);

                txtFrRes_city = layout.findViewById(R.id.txtFrRes_city);
                txtFrRes_takhasos = layout.findViewById(R.id.txtFrRes_takhasos);
                txtFrRes_darmonghah = layout.findViewById(R.id.txtFrRes_darmonghah);
                txtFrRes_time = layout.findViewById(R.id.txtFrRes_time);
                txtFrRes_doctor = layout.findViewById(R.id.txtFrRes_doctor);

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

                defaultDataDropDown();
                builder.setView(layout);
                alertFilterRT = builder.create();
                alertFilterRT.show();
                alertFilterRT.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }
        });


        rowManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcycRT.setLayoutManager(rowManager);

// after scroll to end and get new data (next page in web)
        rcycRT.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    isScrollingFP = true;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItemFP = rowManager.getChildCount();
                totalItemsFP = rowManager.getItemCount();
                scrollOutItemsFP = rowManager.findFirstVisibleItemPosition();

                if (dx < 10) {
                    if (isScrollingFP && (currentItemFP + scrollOutItemsFP == totalItemsFP)) {
                        isScrollingFP = false;
                        if (!boolCheckNetFP) {
                            pageNumber++;
                        }
                        doSearch();
                    }
                }
            }
        });

    }

    private void setRecycViewData(String res) {
        try {
            JSONObject object = new JSONObject(res);
            String status = object.getString("status");
            String message = object.getString("message");
            if (status.equals("yes")) {
                String data = object.getString("data"); // data! nvase chi whatsapp
                JSONObject arrayData1 = new JSONObject(data);

                JSONArray arrayData = arrayData1.getJSONArray("turnList");  // chish moishkel dare
                for (int i = 0; i < arrayData.length(); i++) {
                    JSONObject objectTemp = arrayData.getJSONObject(i);
                    ModResTime time = new ModResTime();
                    //in
                    time.id = objectTemp.getString("id");
                    time.hsp_title = objectTemp.getString("hsp_title");
                    time.shift_title = objectTemp.getString("shift_title");
                    time.dr_name = objectTemp.getString("dr_name");
                    time.spc_title = objectTemp.getString("spc_title");
                    time.prg_date = objectTemp.getString("prg_date");
                    time.web_turn = objectTemp.getString("web_turn");
                    time.status_type = objectTemp.getString("status_type");
                    arrayListResTimes.add(time);
                }

                onClickInterface onclickInterface = new onClickInterface() {
                    @Override
                    public void setClick(final int position, boolean canUse, View view) {
                        if (canUse) {
//// alert tavafoghname
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.tavafoghname, null, false);
                            Button btnTavafogh_no = layout.findViewById(R.id.btnTavafogh_no);
                            final Button btnTavafogh_ok = layout.findViewById(R.id.btnTavafogh_ok);
                            btnTavafogh_no.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alertDialogTavafoghName.dismiss();
                                }
                            });
                            btnTavafogh_ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    linearPazireshPage2.setVisibility(View.GONE);


                                    //2_242_4781779718_45367_291_13990120
                                    //dr_prg_hsp_mdc_spc_date
                                    dr_prg_hsp_mdc_spc_date_id = arrayListResTimes.get(position).id;
                                    String[] temp = dr_prg_hsp_mdc_spc_date_id.split("_");
                                    JSONObject jsonObject = new JSONObject();

                                    try {
                                        jsonObject.put("dr_id", temp[0] + "");
                                        jsonObject.put("prg_id", temp[1] + "");
                                        jsonObject.put("hsp_id", temp[2] + "");
                                        jsonObject.put("mdc_id", temp[3] + "");
                                        jsonObject.put("spc_id", temp[4] + "");
                                        jsonObject.put("prg_date", temp[5] + "");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    alertDialogLoding.show();

                                    positionItemRecycleView = position;
                                    nextPage(linearResTimes, linearResTimesBtn, linearPazireshPage, linearPazireshPageBtn);
                                    alertDialogTavafoghName.dismiss();


                                    new setConnectionVolley(getContext(), "linkCheck", jsonObject).connectStringRequest(new setConnectionVolley.OnResponse() {
                                        @Override
                                        public void OnResponse(String response) {
                                            setDataInPazireshPage(response);

                                        }
                                    });


// set data in pazireshPage
                                }
                            });
                            //sabr

                            builder.setView(layout);
                            alertDialogTavafoghName = builder.create();
                            alertDialogTavafoghName.show();
//----- end of alert
                        } else
                            Toast.makeText(getContext(), "این نوبت به پایین رسیده است.", Toast.LENGTH_SHORT).show();

                    }
                };
                AdRecycResTimes adapterResTimes = new AdRecycResTimes(getContext(), arrayListResTimes, onclickInterface);
                rcycRT.setAdapter(adapterResTimes);

            } else
//                new ShowMessage(getContext()).ShowMessage_SnackBar(linearSelectFilters, message + "");
                Toast.makeText(getContext(), message + "", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDataInPazireshPage(String res) {
        try {

            alertDialogLoding.show();

            JSONObject object = new JSONObject(res);
            String status = object.getString("status");
            String message = object.getString("message");

            if (status.equals("yes")) {
                String data = object.getString("data");
                JSONObject objData = new JSONObject(data);

// turnSpecification:{
//sec_id:0,srv_id:0, spc_level_title:"",dr_name:"", dr_image:"", spc_title:""},
//hspInfo:{hsp_title}
//}
                //get  f
                JSONObject jsonTSF = objData.getJSONObject("turnSpecification");
                String dr_name = jsonTSF.getString("dr_name");
                String dr_image = jsonTSF.getString("dr_image");
                String sec_id = jsonTSF.getString("sec_id");
                String srv_id = jsonTSF.getString("srv_id");
                String spc_title = jsonTSF.getString("spc_title");
                String spc_level_title = jsonTSF.getString("spc_level_title");
                // get hspInfo
                JSONObject jsonhspInfo = objData.getJSONObject("hspInfo");
                String hsp_id = jsonhspInfo.getString("hsp_id");
                String hsp_title = jsonhspInfo.getString("hsp_title");

                hsp_title = hsp_title.replace("بیمارستان", "");
                txtPP_markazName.setText("" + hsp_title);
                txtPP_doctorName.setText("دکتر " + dr_name);
                txtPP_motakhasesName.setText("تخصص " + spc_title);
                txtPP_datePP.setText("تاریخ " + spc_level_title);

                try {
                    byte[] decodeString = Base64.decode(dr_image, Base64.NO_WRAP);
                    InputStream inputStream = new ByteArrayInputStream(decodeString);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    imgPP_drPic.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else
//                new ShowMessage(getContext()).ShowMessage_SnackBar(linearSelectFilters, message + "");
                Toast.makeText(getContext(), message + "", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }


// in jasho nemidonam jash kojas
        try {
            JSONObject object = new JSONObject(res);
            String status = object.getString("status");
            String message = object.getString("message");
            if (status.equals("yes")) {
                String data = object.getString("data");
                JSONObject objData = new JSONObject(data);
                //get  patient
                JSONObject jsonPatient = objData.getJSONObject("patient");
                String first_name = jsonPatient.getString("first_name");
                String last_name = jsonPatient.getString("last_name");
                String father_name = jsonPatient.getString("father_name");
                String is_sex = jsonPatient.getString("is_sex");
                String home_adr = jsonPatient.getString("home_adr");
                String home_mbl = jsonPatient.getString("home_mbl");

                // get insList
                JSONObject jsoninsList = objData.getJSONObject("insList");
                String ins_id = jsoninsList.getString("id");
                String ins_title = jsoninsList.getString("title");

                // get orgs
                JSONObject jsonorgs = objData.getJSONObject("orgs");
                String org_id = jsonorgs.getString("id");
                String org_title = jsonorgs.getString("title");

                // get cities
                JSONObject jsoncities = objData.getJSONObject("cities");
                String city_id = jsoncities.getString("id");
                String city_title = jsoncities.getString("title");

                // get ostans
                JSONObject jsonostans = objData.getJSONObject("ostans");
                String ost_id = jsonostans.getString("id");
                String ost_title = jsonostans.getString("title");


                // hsp_title = hsp_title.replace("بیمارستان", "");
            /*    tx.setText("" + first_name);
                txtPP_doctorName.setText("دکتر " + dr_name);
                txtPP_motakhasesName.setText("تخصص " + spc_title);
                txtPP_datePP.setText("تاریخ " +spc_level_title);*/


            } else
//                new ShowMessage(getContext()).ShowMessage_SnackBar(linearSelectFilters, message + "");
                Toast.makeText(getContext(), message + "", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //    SearchView
    private void alertDialogShow(final String tag) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.item_listview_chooser, null, false);

        Button btnFr = layout.findViewById(R.id.btnFr);
        btnFr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogFilter.dismiss();
            }
        });

        ArrayList arrayList = new ArrayList();
        ArrayList arrayListID = new ArrayList();

        if (tag.equals("city")) {
            arrayList = new ArrayList(dataCity);
            arrayListID = new ArrayList(dataCityID);
        } else if (tag.equals("takhasos")) {
            arrayList = new ArrayList(dataTakhasos);
            arrayListID = new ArrayList(dataTakhasosID);
        } else if (tag.equals("darmonghah")) {
            arrayList = new ArrayList(dataHospital);
            arrayListID = new ArrayList(dataHospitalID);
        } else if (tag.equals("time")) {
            arrayList = new ArrayList(dataTime);
            arrayListID = new ArrayList(dataTimeID);
        } else if (tag.equals("doctor")) {
            arrayList = new ArrayList(dataDoctor);
            arrayListID = new ArrayList(dataDoctorID);
        }

        if (arraylistSearchView.size() != 0)
            arraylistSearchView.clear();

        for (int i = 0; i < arrayList.size(); i++) {
            ModAlerts modAlerts = new ModAlerts(arrayList.get(i) + "", arrayListID.get(i) + "");
            arraylistSearchView.add(modAlerts);
        }

        RecyclerView recycFitler = layout.findViewById(R.id.recycFitler);
        AdRecycPopUp = new AdRecycPopUp(getContext(), arraylistSearchView, new onClickInterface() {
            @Override
            public void setClick(int position, boolean canUse, View view) {

                TextView txttitle = ((LinearLayout) view).findViewById(R.id.txtTitle);
                TextView txtId = ((LinearLayout) view).findViewById(R.id.txtId);
                String title = txttitle.getText().toString();
                String id = txtId.getText().toString();

                if (tag.equals("city")) {
                    txtFrRes_city.setText(title + "");
                    cityId = id;

                    if (id.equals("0")) {
                        if (dataHospital.size() != 0) {
                            dataHospital.clear();
                            dataHospitalID.clear();
                        }

                        dataHospital = new ArrayList(dataHospital2);
                        dataHospitalID = new ArrayList(dataHospitalID2);

                    } else {
                        // TODO: Sent new id to link1 and change HOSPITAL NAMES --------------------------------------------------------

                        JSONObject object = new JSONObject();
                        try {
                            //    object.put("id", id);
                            object.put("hsp_id", "");
                            object.put("spc_id", "0");
                            object.put("dr_id", "0");
                            object.put("prg_date", "0");
                            object.put("city_id", cityId + "");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        alertDialogLoding.show();

                        new setConnectionVolley(getContext(), "http://nobat.mazums.ac.ir/TurnAppApi/Search?city_id=" + cityId, object
                        ).connectStringRequest(new setConnectionVolley.OnResponse() {
                            @Override
                            public void OnResponse(String response) {
                                setDropDownsData(response, "updateHospital");
                            }
                        });
                    }
                    //  String result = "{\"status\":\"yes\",\"message\":\"\",\"data\":{ \"hospital\":[{\"id\":\"0\",\"title\":\"تمامی بیمارستان ها\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید حسینی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید حسینی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید حسینی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید حسینی\"},{\"id\":\"1\",\"title\":\"بیمارستان عمومی ماساچوست\"}],\"doctor\":[],\"specialty\":[],\"time\":[],\"city\":[]}}";
                    //  setDropDownsData(result, "update");
                } else if (tag.equals("takhasos")) {
                    txtFrRes_takhasos.setText(title + "");
                    takhasosId = id;
                    if (id.equals("0")) {
                        if (dataTakhasos2.size() != 0) {
                            dataTakhasos2.clear();
                            dataTakhasosID2.clear();
                        }
                        dataTakhasos = new ArrayList(dataTakhasos2);
                        dataTakhasosID2 = new ArrayList(dataTakhasosID2);
                    } else {
                        // TODO: Sent new id to link1 and change TAKASOS  --------------------------------------------------------

                        JSONObject object = new JSONObject();
                        try {
                            //    object.put("id", id);
                            object.put("hsp_id", hospiralId + "");
                            object.put("spc_id", takhasosId + "");
                            object.put("dr_id", "0");
                            object.put("prg_date", "0");
                            object.put("city_id", cityId);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        alertDialogLoding.show();

                        new setConnectionVolley(getContext(), "http://nobat.mazums.ac.ir/TurnAppApi/Search?hsp_id=" + hospiralId + "&spc_id=" + takhasosId, object  // inja chi bayad gozasht?  takhasos va hospital
                        ).connectStringRequest(new setConnectionVolley.OnResponse() {
                            @Override
                            public void OnResponse(String response) {
                                setDropDownsData(response, "updateDoctor");
                            }
                        });
                    }

                } else if (tag.equals("darmonghah")) {
                    txtFrRes_darmonghah.setText(title + "");
                    hospiralId = id;  // the last hsp_id save in here
                    if (id.equals("0")) {
                        if (dataHospitalID2.size() != 0) {
                            dataHospital2.clear();
                            dataHospitalID2.clear();
                        }
                        dataHospital = new ArrayList(dataHospital2);
                        dataHospitalID = new ArrayList(dataTakhasosID2);
                    } else {
                        // TODO: Sent new id to link1 and change TAKASOS  --------------------------------------------------------

                        JSONObject object = new JSONObject();
                        try {
                            //    object.put("id", id);
                            object.put("hsp_id", hospiralId + "");
                            object.put("spc_id", takhasosId + "");
                            object.put("dr_id", "0");
                            object.put("prg_date", "0");
                            object.put("city_id", "0"); // ؟
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        alertDialogLoding.show();

                        new setConnectionVolley(getContext(), "http://nobat.mazums.ac.ir/TurnAppApi/Search?hsp_id=" + hospiralId + "&spc_id=" + takhasosId, object  // inja chi bayad gozasht?  takhasos va hospital
                        ).connectStringRequest(new setConnectionVolley.OnResponse() {
                            @Override
                            public void OnResponse(String response) {
                                setDropDownsData(response, "updateDoctor");
                            }
                        });
                    }
                } else if (tag.equals("time")) {
                    txtFrRes_time.setText(title + "");
                    timeId = id;
                } else if (tag.equals("doctor")) {
                    txtFrRes_doctor.setText(title + "");
                    doctorId = id;
                }

                alertDialogFilter.dismiss();
            }
        });
        recycFitler.setAdapter(AdRecycPopUp);

        editsearchSearchView = layout.findViewById(R.id.searchFr);
        editsearchSearchView.setOnQueryTextListener(this);

        builder.setView(layout);
        alertDialogFilter = builder.create();
        alertDialogFilter.show();
        alertDialogFilter.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }

    private void selectFilters(View view) {

//      this layout is for linearResTimes
        LinearLayout layoutBtnsRT = view.findViewById(R.id.layoutBtnsRT);
        layoutBtnsRT.setVisibility(View.GONE);


        linearSelectFilters = view.findViewById(R.id.linearSelectFilters);
        linearSelectFiltersBtn = view.findViewById(R.id.linearSelectFiltersBtn);
        txtFrRes_city = view.findViewById(R.id.txtFrRes_city);
        txtFrRes_takhasos = view.findViewById(R.id.txtFrRes_takhasos);
        txtFrRes_darmonghah = view.findViewById(R.id.txtFrRes_darmonghah);
        txtFrRes_time = view.findViewById(R.id.txtFrRes_time);
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
                defaultDataDropDown();
            }
        });

        btnFrRes_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(), "اجرا شدن لودینگ 2 ثانیه", Toast.LENGTH_SHORT).show();
                alertDialogLoding.show();
                nextPage(linearSelectFilters, linearSelectFiltersBtn, linearResTimes, linearResTimesBtn);
                refreshData();
            }
        });

        dataCity = new ArrayList();
        dataTakhasos = new ArrayList();
        dataTakhasos2 = new ArrayList();
        dataHospital = new ArrayList();
        dataHospital2 = new ArrayList();
        dataTime = new ArrayList();
        dataDoctor = new ArrayList();
        dataCityID = new ArrayList();
        dataTakhasosID = new ArrayList();
        dataTakhasosID2 = new ArrayList();
        dataHospitalID = new ArrayList();
        dataHospitalID2 = new ArrayList();
        dataTimeID = new ArrayList();
        dataDoctorID = new ArrayList();

//TODO------ Connection data for dropDowns link1

        JSONObject object = new JSONObject();
        try {
            //     object.put("hospital", "-1");
            //  object.put("city", "-1");
            // object.put("specialty", "-1");
            object.put("hsp_id", "0");
            object.put("spc_id", "0");
            object.put("dr_id", "0");
            object.put("prg_date", "0");
            object.put("city_id", "0");
        } catch (Exception e) {
            e.printStackTrace();
        }

        alertDialogLoding.show();

        new setConnectionVolley(getContext(), "http://nobat.mazums.ac.ir/turnappApi/Search?spc_id=0&dr_id=0&city_id=0&hsp_id=0&prg_date=0", object
        ).connectStringRequest(new setConnectionVolley.OnResponse() {
            @Override
            public void OnResponse(String response) {
                setDropDownsData(response, "new");
            }
        });

        //  String response = "{\"status\":\"yes\",\"message\":\"\",\"data\":{ \"hospital\":[{\"id\":\"0\",\"title\":\"تمامی بیمارستان ها\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"1\",\"title\":\"بیمارستان عمومی ماساچوست\"}],\"doctor\":[{\"id\":\"0\",\"title\":\"تمامی دکترها\"},{\"id\":\"4\",\"title\":\"علی علیزاده\"},{\"id\":\"1\",\"title\":\"حسین زارعی\"}],\"specialty\":[{\"id\":\"0\",\"title\":\"تمامی تخصص ها\"},{\"id\":\"6\",\"title\":\"اطفال\"},{\"id\":\"1\",\"title\":\"عفونی\"}],\"time\":[{\"id\":\"0\",\"title\":\"تمامی زمان ها\"},{\"id\":\"4\",\"title\":\"فردا\"},{\"id\":\"5\",\"title\":\"هفته ی جاری\"},{\"id\":\"4\",\"title\":\"ماه جاری\"}],\"city\":[{\"id\":\"0\",\"title\":\"تمامی شهرها\"},{\"id\":\"1\",\"title\":\"اراک\"},{\"id\":\"2\",\"title\":\"امل\"},{\"id\":\"41\",\"title\":\"تهران\"}]}}";
        //setDropDownsData(response, "new");

    }

    private void refreshData() {

        refreshRT.setRefreshing(true);

        refreshRT.setColorSchemeResources(
                R.color.colorPurple6,
                R.color.colorPink,
                R.color.colorLogo,
                R.color.colorIndigo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doSearch();
                refreshRT.setRefreshing(false);
            }
        }, 2000);

    }

    private void doSearch() {

        if (alertDialogLoding.isShowing())
            alertDialogLoding.dismiss();

        pageNumber++;
        if (arrayListResTimes.size() != 0)
            arrayListResTimes.clear();
        // here
        // TODO: Sent new id to link2 and change HOSPITAL NAMES --------------------------------------------------------
        //city_id:0,hsp_id:0,spc_id:0,first_name:'',last_name:'', date_period:0,page_number:0,item_per_page:10}
        // if (doctorId  0){
        //     String[] temp = txtFrRes_doctor.getText().toString().split(" ");
        //   }

        JSONObject object = new JSONObject();
        try {
            //    object.put("id", id);
            object.put("hsp_id", hospiralId + "");
            object.put("city_id", cityId + "");
            object.put("spc_id", takhasosId + "");
            object.put("first_name", txtFrRes_doctor.getText().toString() + "");
            object.put("last_name", "0");
            object.put("date_period", timeId + "");
            object.put("page_number", pageNumber);
            object.put("item_per_page", "10");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // + "&first_name=" +txtFrRes_doctor.getText().toString()+""
        new setConnectionVolley(getContext(), "http://nobat.mazums.ac.ir/turnappApi/search/TurnList?hsp_id=" +
                hospiralId + "&city_id=" + cityId + "&spc_id=" + takhasosId + "&date_period="
                + timeId + "&page_number=" + pageNumber + "&item_per_page=" + "10", object
        ).connectStringRequest(new setConnectionVolley.OnResponse() {
            @Override
            public void OnResponse(String response) {
                setRecycViewData(response);
            }
        });

        // TODO: Sent data and get ResTimes data (RecycleView) | link2--------------------------------------------------------
        //inja chi bayad respons bedim b setRecycViewData
        // String res = "{\"status\":\"yes\",\"message\":\"\",\"data\":[{\"dr_prg_hsp_mdc_spc_date_id\":\"1\",\"hsp_title\":\"بیمارستان شهید بهشتی\",\"shift_title\":\"صبح\",\"dr_name\":\"مهسا طاهری\",\"spc_title\":\"اطفال\",\"prg_date\":\"1399/01/06\",\"web_turn\":\"1\",\"status_type\":\"دریافت نوبت\"},{\"dr_prg_hsp_mdc_spc_date_id\":\"12\",\"hsp_title\":\"بیمارستان شهید حسینی\",\"shift_title\":\"عصر\",\"dr_name\":\"مهدی منصوری\",\"spc_title\":\"داخلی\",\"prg_date\":\"1399/02/07\",\"web_turn\":\"0\",\"status_type\":\"اتمام\"},{\"dr_prg_hsp_mdc_spc_date_id\":\"13\",\"hsp_title\":\"بیمارستان شهید صالحی\",\"shift_title\":\"شب\",\"dr_name\":\"فرزاد اکبری\",\"spc_title\":\"عمومی\",\"prg_date\":\"1399/03/09\",\"web_turn\":\"5\",\"status_type\":\"دریافت نوبت\"},{\"dr_prg_hsp_mdc_spc_date_id\":\"14\",\"hsp_title\":\"بیمارستان شهید عابدزاده\",\"shift_title\":\"عصر\",\"dr_name\":\"علی ملکی\",\"spc_title\":\"داخلی\",\"prg_date\":\"1399/04/19\",\"web_turn\":\"3\",\"status_type\":\"دریافت نوبت\"},{\"dr_prg_hsp_mdc_spc_date_id\":\"15\",\"hsp_title\":\"بیمارستان  بهشتی\",\"shift_title\":\"صبح\",\"dr_name\":\"نجمه مقدم\",\"spc_title\":\"داخلی\",\"prg_date\":\"1399/09/05\",\"web_turn\":\"0\",\"status_type\":\"اتمام\"}]}";
        // setRecycViewData(res);
    }

    private void setDropDownsData(String res, String tag) {
        try {
            alertDialogLoding.dismiss();

            JSONObject object = new JSONObject(res);
            String status = object.getString("status");
            String message = object.getString("message");

            if (status.equals("yes")) {
                String data = object.getString("data");
                JSONObject objData = new JSONObject(data);
                JSONArray arrayHospital = objData.getJSONArray("hospital");
                JSONArray arrayDoctor = objData.getJSONArray("doctor");
                JSONArray arraySpecialty = objData.getJSONArray("specialty");
                JSONArray arrayTime = objData.getJSONArray("time");
                JSONArray arrayCity = objData.getJSONArray("city");

                if (tag.equals("new")) {

                    for (int i = 0; i < arrayHospital.length(); i++) {
                        JSONObject object1 = arrayHospital.getJSONObject(i);
                        dataHospitalID.add(object1.getString("id"));
                        dataHospital.add(object1.getString("title"));
                        dataHospitalID2.add(object1.getString("id"));
                        dataHospital2.add(object1.getString("title"));
                    }
                    for (int i = 0; i < arrayDoctor.length(); i++) {
                        JSONObject object1 = arrayDoctor.getJSONObject(i);
                        dataDoctorID.add(object1.getString("id"));
                        dataDoctor.add(object1.getString("title"));
                    }
                    for (int i = 0; i < arraySpecialty.length(); i++) {
                        JSONObject object1 = arraySpecialty.getJSONObject(i);
                        dataTakhasosID.add(object1.getString("id"));
                        dataTakhasos.add(object1.getString("title"));
                        dataTakhasosID2.add(object1.getString("id"));
                        dataTakhasos2.add(object1.getString("title"));
                    }
                    for (int i = 0; i < arrayTime.length(); i++) {
                        JSONObject object1 = arrayTime.getJSONObject(i);
                        dataTimeID.add(object1.getString("id"));
                        dataTime.add(object1.getString("title"));
                    }
                    for (int i = 0; i < arrayCity.length(); i++) {
                        JSONObject object1 = arrayCity.getJSONObject(i);
                        dataCityID.add(object1.getString("id"));
                        dataCity.add(object1.getString("title"));
                    }
                    defaultDataDropDown();

                } else if (tag.equals("updateHospital")) {

                    if (dataHospital.size() != 0)
                        dataHospital.clear();
                    if (dataHospitalID.size() != 0)
                        dataHospitalID.clear();
                    for (int i = 0; i < arrayHospital.length(); i++) {
                        JSONObject object1 = arrayHospital.getJSONObject(i);
                        dataHospitalID.add(object1.getString("id"));
                        dataHospital.add(object1.getString("title"));

                        txtFrRes_darmonghah.setText(dataHospital.get(0) + "");
                        hospiralId = dataHospitalID.get(0) + "";

                    }

                } else if (tag.equals("updateTakasos")) {

                    if (dataTakhasos.size() != 0) {
                        dataTakhasos.clear();
                        dataTakhasosID.clear();
                    }
                    for (int i = 0; i < arrayHospital.length(); i++) {
                        JSONObject object1 = arrayHospital.getJSONObject(i);
                        dataTakhasosID.add(object1.getString("id"));
                        dataTakhasos.add(object1.getString("title"));

                        txtFrRes_takhasos.setText(dataTakhasos.get(0) + "");
                        takhasosId = dataTakhasosID.get(0) + "";

                    }

                } else if (tag.equals("updateDoctor")) {

                    if (dataDoctor.size() != 0) {
                        dataDoctor.clear();
                        dataDoctorID.clear();
                    }
                    for (int i = 0; i < arrayDoctor.length(); i++) {
                        JSONObject object1 = arrayDoctor.getJSONObject(i);
                        dataDoctorID.add(object1.getString("id"));
                        dataDoctor.add(object1.getString("title"));

                        txtFrRes_doctor.setText(dataDoctor.get(0) + "");
                        doctorId = dataDoctorID.get(0) + "";

                    }

                }
            } else
//                new ShowMessage(getContext()).ShowMessage_SnackBar(linearSelectFilters, message + "");
                Toast.makeText(getContext(), message + "", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //  SearchView -------------------------------------------------------------
    private SearchView editsearchSearchView;
    private AdRecycPopUp AdRecycPopUp;
    private ArrayList<ModAlerts> arraylistSearchView = new ArrayList<ModAlerts>();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        AdRecycPopUp.filter(text);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private void defaultDataDropDown() {
        dataHospital = new ArrayList(dataHospital2);
        dataHospitalID = new ArrayList(dataHospitalID2);
        dataTakhasos2 = new ArrayList(dataTakhasos2);
        dataTakhasosID2 = new ArrayList(dataTakhasosID2);


        txtFrRes_city.setText(dataCity.get(0) + "");
        txtFrRes_takhasos.setText(dataTakhasos.get(0) + "");
        txtFrRes_darmonghah.setText(dataHospital.get(0) + "");
        txtFrRes_time.setText(dataTime.get(0) + "");
        txtFrRes_doctor.setText(dataDoctor.get(0) + "");

        cityId = dataCityID.get(0) + "";
        takhasosId = dataTakhasosID.get(0) + "";
        hospiralId = dataHospitalID.get(0) + "";
        timeId = dataTimeID.get(0) + "";
        doctorId = dataDoctorID.get(0) + "";
    }

    private void nextPage(final LinearLayout first, final LinearLayout firstBtn,
                          final LinearLayout second, final LinearLayout secondBtn) {
// anim next - center to right (forFirstLinear) | left to center (forSecondLinear)
        Animation animCenterToRight = AnimationUtils.loadAnimation(getContext(), R.anim.center_to_right);
        final Animation animLeftToCenter = AnimationUtils.loadAnimation(getContext(), R.anim.left_to_center);

        first.startAnimation(animCenterToRight);
        firstBtn.startAnimation(animCenterToRight);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                second.setVisibility(View.VISIBLE);
                secondBtn.setVisibility(View.VISIBLE);
                second.startAnimation(animLeftToCenter);
                secondBtn.startAnimation(animLeftToCenter);
            }
        }, 150);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                first.setVisibility(View.GONE);
                firstBtn.setVisibility(View.GONE);

            }
        }, 300);
    }

    private void previousPage(final LinearLayout first, final LinearLayout firstBtn,
                              final LinearLayout second, final LinearLayout secondBtn) {

// anim previous - center to left (First) | right to center (Second)
        final Animation animRightToCenter = AnimationUtils.loadAnimation(getContext(), R.anim.right_to_center);
        Animation animCenterToLeft = AnimationUtils.loadAnimation(getContext(), R.anim.center_to_left);

        first.startAnimation(animCenterToLeft);
        firstBtn.startAnimation(animCenterToLeft);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                second.setVisibility(View.VISIBLE);
                secondBtn.setVisibility(View.VISIBLE);
                second.startAnimation(animRightToCenter);
                secondBtn.startAnimation(animRightToCenter);
            }
        }, 150);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                first.setVisibility(View.GONE);
                firstBtn.setVisibility(View.GONE);
            }
        }, 300);

    }

}


