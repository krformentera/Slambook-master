package com.example.slambook;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.slambook.R;

public class SpinnersActivity implements AdapterView.OnItemSelectedListener {

    public Activity activity;
    private String barangay;
    private String municipality;
    private String province;
    private String secQ1;
    private String secQ2;
    private String secQ3;
    private Spinner spinnerQ1;
    private Spinner spinnerQ2;
    private Spinner spinnerQ3;

    public SpinnersActivity(Activity _activity){
        this.activity = _activity;
    }

    public void MySpinner(){
//        Spinner spinner1 = this.activity.findViewById(R.id.spnr_rgion);
//        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this.activity,
//                R.array.r1brgy, android.R.layout.simple_spinner_item);
//
//        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner1.setAdapter(adapter1);
//        spinner1.setOnItemSelectedListener(this);
//
//        Spinner spinner2 = this.activity.findViewById(R.id.spnr_municipal);
//        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this.activity,
//                R.array.r1prvnc, android.R.layout.simple_spinner_item);
//
//        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner2.setAdapter(adapter2);
//        spinner2.setOnItemSelectedListener(this);
//
//        Spinner spinner3 = this.activity.findViewById(R.id.spnr_province);
//        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this.activity,
//                R.array.province, android.R.layout.simple_spinner_item);
//
//        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner3.setAdapter(adapter3);
//        spinner3.setOnItemSelectedListener(this);
//
//        //Securty Question 1
//        spinnerQ1 = this.activity.findViewById(R.id.id_SQ1spinner);
//        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this.activity,
//                R.array.securityQuestions, android.R.layout.simple_spinner_item);
//
//        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerQ1.setAdapter(adapter4);
//        spinnerQ1.setOnItemSelectedListener(this);
//
//        //Securty Question 2
//        spinnerQ2 = this.activity.findViewById(R.id.id_SQ2spinner);
//        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(this.activity,
//                R.array.securityQuestions, android.R.layout.simple_spinner_item);
//
//
//        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerQ2.setAdapter(adapter5);
//        spinnerQ2.setOnItemSelectedListener(this);
//        spinnerQ2.setSelection(1);
//
//        //Securty Question 3;
//        spinnerQ3 = this.activity.findViewById(R.id.id_SQ3spinner);
//        ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(this.activity,
//                R.array.securityQuestions, android.R.layout.simple_spinner_item);
//
//        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerQ3.setAdapter(adapter6);
//        spinnerQ3.setOnItemSelectedListener(this);
//        spinnerQ3.setSelection(2);

    }

    public String Barangay(){
        return barangay;
    }

    public String Municipality(){
        return municipality;
    }

    public String Province(){
        return province;
    }

    public String SecQ1Method() { return secQ1; }
    public String SecQ2Method() { return secQ2; }
    public String SecQ3Method() { return secQ3; }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String select = parent.getItemAtPosition(position).toString();
        boolean sameSelected = position == parent.getSelectedItemPosition();

        switch (parent.getId()){
//            case R.id.spnr_rgion:
//                barangay = select;
//                break;
//            case R.id.spnr_municipal:
//                municipality = select;
//                break;
//            case R.id.spnr_province:
//                province = select;
//                break;
//            case R.id.id_SQ1spinner:
//                secQ1 = select;
//                if(select.equals(secQ2) || select.equals(secQ3)){
//                    SecurityQuestionError();
//                    spinnerQ1.setSelection(0);
//                }
//                break;
//            case R.id.id_SQ2spinner:
//                secQ2 = select;
//                if(select.equals(secQ1) || select.equals(secQ3)){
//                    SecurityQuestionError();
//                    spinnerQ2.setSelection(1);
//                }
//                break;
//            case R.id.id_SQ3spinner:
//                secQ3 = select;
//                if(select.equals(secQ1) || select.equals(secQ2)){
//                    SecurityQuestionError();
//                    spinnerQ3.setSelection(2);
//                }
//                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void SecurityQuestionError(){
        AlertDialog.Builder error = new AlertDialog.Builder(this.activity);
        error.setTitle("Security Question Error/s!");
        error.setMessage("The question is already used ❌");
        error.setPositiveButton("Okay",null);
        error.show();
    }


}
