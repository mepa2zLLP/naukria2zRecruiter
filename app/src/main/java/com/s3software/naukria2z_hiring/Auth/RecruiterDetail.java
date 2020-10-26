package com.s3software.naukria2z_hiring.Auth;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonParser;
import com.s3software.naukria2z_hiring.Activity.MainActivity;
import com.s3software.naukria2z_hiring.Activity.VerificationWait;
import com.s3software.naukria2z_hiring.ErrorLogs;
import com.s3software.naukria2z_hiring.R;
import com.s3software.naukria2z_hiring.Util.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecruiterDetail extends AppCompatActivity {
    EditText fname, lname, personalemail, Mobileno, gender, Designation, Comapanyno, comemail, Companyaddress, Postalcode, Companywebsite, companyname;
    Button next, submit, previous1, previous2, next1;
    FrameLayout f1, f2, f3;
    EditText country, state, city;
    ProgressDialog progressDialog;
    ArrayList<String> arrayList_country, arrayList_conID, arrayList_stateID;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Spinner Scountry, Sstate, Scity;
    Button update, submit1, cancel, button_close;
    ErrorLogs errorLogs;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_detail);
        companyname = findViewById(R.id.Companyname);
        previous1 = findViewById(R.id.previous1);
        previous2 = findViewById(R.id.previous2);
        fname = findViewById(R.id.name);
        next1 = findViewById(R.id.next1);
        lname = findViewById(R.id.lname);
        next = findViewById(R.id.next);
        submit = findViewById(R.id.submit);
        personalemail = findViewById(R.id.Personalemail);
        Mobileno = findViewById(R.id.Mobileno);
        Designation = findViewById(R.id.Designation);
        Comapanyno = findViewById(R.id.Comapanyno);
        comemail = findViewById(R.id.comemail);
        Companyaddress = findViewById(R.id.Companyaddress);
        country = findViewById(R.id.Country);
        state = findViewById(R.id.State);
        city = findViewById(R.id.City);
        Postalcode = findViewById(R.id.Postalcode);
        Companywebsite = findViewById(R.id.Companywebsite);
        gender = findViewById(R.id.gender);
        f1 = findViewById(R.id.f1);
        f2 = findViewById(R.id.f2);
        f3 = findViewById(R.id.f3);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        errorLogs=new ErrorLogs(getApplicationContext());
        arrayList_country = new ArrayList<>();
        arrayList_conID = new ArrayList<>();
        arrayList_stateID = new ArrayList<>();

        SharedPreferences userdata = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        final String EmployeeID = userdata.getString("EmployeeID", "");
        final String First_Name = userdata.getString("First_Name", "");
        final String Last_Name = userdata.getString("Last_Name", "");
        final String User_Email = userdata.getString("User_Email", "");
        final String User_Pass = userdata.getString("User_Pass", "");
        String Gender = userdata.getString("Gender", "");
        final String Phone_No = userdata.getString("Phone_No", "");
        String Country = userdata.getString("Country", "");
        String State = userdata.getString("State", "");
        String City = userdata.getString("City", "");

        fname.setText(First_Name);
        lname.setText(Last_Name);
        personalemail.setText(User_Email);
        Mobileno.setText(Phone_No);
        gender.setText(Gender);
        country.setText(Country);
        state.setText(State);
        city.setText(City);

        country.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= country.getRight() - country.getTotalPaddingRight()) {
                        // your action for drawable click event

                        openDialog();
                        return true;
                    }
                }
                return false;
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fname.getText().toString().isEmpty()) {
                    fname.setError("Please fill it");
                } else if (lname.getText().toString().isEmpty()) {
                    lname.setError("Please fill it");
                } else if (personalemail.getText().toString().isEmpty()) {
                    personalemail.setError("Please fill it");
                } else if (Mobileno.getText().toString().isEmpty()) {
                    Mobileno.setError("Please fill it");
                } else if (Designation.getText().toString().isEmpty()) {
                    Designation.setError("Please fill it");
                } else {
                    f1.setVisibility(View.INVISIBLE);
                    f2.setVisibility(View.VISIBLE);
                }
                f3.setVisibility(View.INVISIBLE);


            }
        });
        gender.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= gender.getRight() - gender.getTotalPaddingRight()) {
                        // your action for drawable click event
                        openDialogGender();
                        return true;
                    }
                }
                return false;
            }
        });
        Mobileno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (Mobileno.getText().toString().matches("^[6-9][0-9]{9}$")) {

                } else {
                    Mobileno.setError("InValid");
                }
            }
        });


        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (companyname.getText().toString().isEmpty()) {
                    companyname.setError("Please fill it");
                } else if (Companyaddress.getText().toString().isEmpty()) {
                    Companyaddress.setError("Please fill it");
                }
                else if (!(comemail.getText().toString().isEmpty())) {
                    if (!(comemail.getText().toString().matches(emailPattern))) {
                        comemail.setError("InValid");
                    }  else if (!(Companywebsite.getText().toString().isEmpty())) {
                        String comwebsite = Companywebsite.getText().toString();
                        if (!(Patterns.WEB_URL.matcher(comwebsite).matches())) {
                            Companywebsite.setError("Invalid");
                        } else {
                            f1.setVisibility(View.INVISIBLE);
                            f2.setVisibility(View.INVISIBLE);
                            f3.setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        f1.setVisibility(View.INVISIBLE);
                        f2.setVisibility(View.INVISIBLE);
                        f3.setVisibility(View.VISIBLE);
                    }
                }else {
                    f1.setVisibility(View.INVISIBLE);
                    f2.setVisibility(View.INVISIBLE);
                    f3.setVisibility(View.VISIBLE);
                }
            }
        });

        previous1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f2.setVisibility(View.INVISIBLE);
                f1.setVisibility(View.VISIBLE);
            }
        });
        previous2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f3.setVisibility(View.INVISIBLE);
                f2.setVisibility(View.VISIBLE);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(fname.getText().toString().isEmpty() || lname.getText().toString().isEmpty() || personalemail.getText().toString().isEmpty() ||
                        Mobileno.getText().toString().isEmpty() || Designation.getText().toString().isEmpty() || companyname.getText().toString().isEmpty() ||
                        Companyaddress.getText().toString().isEmpty())) {
                    String f_name = fname.getText().toString();
                    String l_name = lname.getText().toString();
                    String pemail = personalemail.getText().toString();
                    String mob = Mobileno.getText().toString();
                    String designation = Designation.getText().toString();
                    String comname = companyname.getText().toString();
                    String commobile = Comapanyno.getText().toString();
                    String comEmail = comemail.getText().toString();
                    String comaddress = Companyaddress.getText().toString();
                    String comweb = Companywebsite.getText().toString();
                    String Country = null, State = null, City = null;
                    try {
                        Country = country.getText().toString();
                        State = state.getText().toString();
                        City = city.getText().toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String postalcode = Postalcode.getText().toString();
                    String Gender = gender.getText().toString();
                    if (Postalcode.getText().toString().isEmpty() || Postalcode.length() > 6) {
                        Postalcode.setError("Invalid");
                    } else {
                        callRecruiterAPI(EmployeeID, f_name, l_name, pemail, mob, designation, comname, commobile, comEmail, comaddress, comweb, User_Pass, Country, State, City, postalcode, Gender);

                    }
                }
            }
        });

    }

    private void openDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.input_dialog);
        Scountry = dialog.findViewById(R.id.country);
        Sstate = dialog.findViewById(R.id.state);
        Scity = dialog.findViewById(R.id.city);
        submit1 = dialog.findViewById(R.id.submit);
        cancel = dialog.findViewById(R.id.cancel);
        button_close = dialog.findViewById(R.id.button_close);
        button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        submit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Country = Scountry.getSelectedItem().toString();
                String State = Sstate.getSelectedItem().toString();
                String City = Scity.getSelectedItem().toString();

                country.setText(Country);
                state.setText(State);
                city.setText(City);
                dialog.dismiss();
            }
        });
        countryAPI();
        dialog.show();
    }

    private void openDialogGender() {
        Button cancel, button_close, submit;
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.input_gender);
        final Spinner Sgender = dialog.findViewById(R.id.gender);
        submit = dialog.findViewById(R.id.submit);
        cancel = dialog.findViewById(R.id.cancel);
        button_close = dialog.findViewById(R.id.button_close);
        button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gendervalue = Sgender.getSelectedItem().toString();
                gender.setText(gendervalue);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void callRecruiterAPI(String employeeID, String f_name, String l_name, final String pemail, String mob, String designation, String comname, String commobile, String comEmail, String comaddress, String comweb, String user_Pass, String country, String state, String city, String postalcode, String gender) {

        progressDialog.show();
        String name = f_name + l_name;
        String url = "http://api.mymakeover.club/api/MepJobs/AddRecruiter?employeeID=" + employeeID + "&fname=" + name + "&email=" + pemail + "&password=" + user_Pass + "&mob=" + mob + "&designation=" + designation + "&cname=" + comname + "&cno=" + commobile + "&cemail=" + comEmail + "&caddress=" + comaddress + "&country=" + country + "&state=" + state + "&city=" + city + "&postalcode=" + postalcode + "&cwebsite=" + comweb + "&gender=" + gender;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonParser jsonParser = new JsonParser();

                String resstring = jsonParser.parse(response).getAsString();
                Log.e("RecruiterDetail", "onResponse: " + resstring);
                try {
                    JSONObject jsonObject = new JSONObject(resstring);
                    String status = jsonObject.getString("RegistrationStatus");
                    if (status.equals("1")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("Data");
                        String name = jsonObject1.getString("Name");
                        String Personal_Email = jsonObject1.getString("Personal_Email");
                        String Mobile_No = jsonObject1.getString("Mobile_No");

                        SharedPreferences SharedPreferences = getApplicationContext().getSharedPreferences("ProfileDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = SharedPreferences.edit();

                        editor.putString("Name", name);
                        editor.putString("User_Email", Personal_Email);
                        editor.putString("Phone_No", Mobile_No);

                        editor.apply();

                        progressDialog.dismiss();
                    //    MailAPI(name, pemail);
                      //  SaveSharedPreference.setLoggedIn(getApplicationContext(), true);
                        Intent i = new Intent(RecruiterDetail.this, VerificationWait.class);
                        startActivity(i);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    progressDialog.dismiss();
                    Toast.makeText(RecruiterDetail.this, "Network Problem", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorLogs.AppErrorLog("LoginRecruiter",error);
                Log.e("RecruiterDetail", "onErrorResponse: " + error);
                progressDialog.dismiss();
             }
        });
        requestQueue.add(stringRequest);
    }

    private void MailAPI(String name, String pemail) {
        String URL = "http://api.mymakeover.club/api/MepJobs/MailAPI?fname=" + name + "&email=" + pemail + "&PStatus=1";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonParser jsonParser = new JsonParser();

                String resstring = jsonParser.parse(response).getAsString();
                Log.e("mailresponse", "onResponse: " + resstring);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        requestQueue.add(stringRequest);

    }

    private void countryAPI() {

        String URL = "http://api.mymakeover.club/api/AppTrack/CountryList";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonParser jsonParser = new JsonParser();

                String resstring = jsonParser.parse(response).getAsString();
                Log.e("response", "onResponse: " + resstring);
                try {
                    JSONObject jsonObject = new JSONObject(resstring);
                    JSONArray jsonArray = jsonObject.getJSONArray("Name");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                        String countryname = jsonObject1.getString("Name");
                        String countryID = jsonObject1.getString("ID");
                        arrayList_conID.add(countryID);
                        arrayList_country.add(countryname);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(RecruiterDetail.this, android.R.layout.simple_spinner_item, arrayList_country);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Scountry.setAdapter(arrayAdapter);
                Scountry.setSelection(100);
                Scountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String country_ = arrayList_conID.get(position).toString();
                        StateAPI(country_);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "onErrorResponse: " + error);
                Toast.makeText(RecruiterDetail.this, "Poor Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        requestQueue.add(stringRequest);

    }

    private void StateAPI(final String country_) {

        String URL = "http://api.mymakeover.club/api/AppTrack/StateList?CountryId=" + country_;
        final ArrayList<String> arrayList_state = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonParser jsonParser = new JsonParser();

                String resstring = jsonParser.parse(response).getAsString();
                Log.e("response", "onResponse: " + resstring);
                try {
                    JSONObject jsonObject = new JSONObject(resstring);
                    JSONArray jsonArray = jsonObject.getJSONArray("State");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                        String statename = jsonObject1.getString("Name");
                        String stateID = jsonObject1.getString("ID");
                        arrayList_stateID.add(stateID);
                        arrayList_state.add(statename);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(RecruiterDetail.this, android.R.layout.simple_spinner_item, arrayList_state);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Sstate.setAdapter(arrayAdapter);
                Sstate.setSelection(9);
                progressDialog.dismiss();
                Sstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String StateID = arrayList_stateID.get(position).toString();
                        CityAPI(StateID);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        requestQueue.add(stringRequest);

    }

    private void CityAPI(String stateID) {

        String URL = "http://api.mymakeover.club/api/AppTrack/CityList?StateId=" + stateID;
        final ArrayList<String> arrayList_city = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonParser jsonParser = new JsonParser();

                String resstring = jsonParser.parse(response).getAsString();
                Log.e("response", "onResponse: " + resstring);
                try {
                    JSONObject jsonObject = new JSONObject(resstring);
                    JSONArray jsonArray = jsonObject.getJSONArray("City");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                        String statename = jsonObject1.getString("Name");

                        arrayList_city.add(statename);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(RecruiterDetail.this, android.R.layout.simple_spinner_item, arrayList_city);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Scity.setAdapter(arrayAdapter);
                Scity.setSelection(1);

                Scity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        requestQueue.add(stringRequest);


    }
}