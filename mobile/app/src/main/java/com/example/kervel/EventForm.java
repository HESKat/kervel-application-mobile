package com.example.kervel;

import android.app.DatePickerDialog;
        import android.content.Intent;
        import android.os.Bundle;
import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
import android.widget.Button;
        import android.widget.DatePicker;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.NumberPicker;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.appcompat.app.AppCompatActivity;

import com.example.kervel.modele.EventResponse;
import com.google.gson.JsonSyntaxException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

public class EventForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener ,DatePickerDialog.OnDateSetListener, View.OnClickListener{
Spinner spinner;
TextView nameUsr, viewDate, textV;
ImageView mapImg;
EditText numParcel,param;
Button btnInsert;
NumberPicker numParcelle;
LinearLayout li,li2;

protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.eventform);
                //recuperer l'intent
    Intent intentRecu = getIntent();
    spinner = findViewById(R.id.spinner);
    spinner.setOnItemSelectedListener(this);
    btnInsert = findViewById(R.id.btnInsert);
    numParcelle = findViewById(R.id.numParcelle);
    param = findViewById(R.id.param);
               // textV = findViewById(R.id.textView);
    li = findViewById(R.id.li1);
    li2 = findViewById(R.id.li2);

    numParcelle.setMinValue(0);
    numParcelle.setMaxValue(200);
    numParcelle.setFormatter(new NumberPicker.Formatter() {
        @Override
        public String format(int value) {
            return String.format("%02d",value);

        }
    });

String name = intentRecu.getStringExtra("name");
     nameUsr = (TextView)findViewById(R.id.viewNameU);
     nameUsr.setText("Bienvenu "+ name);


     mapImg = findViewById(R.id.map);
     mapImg.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(EventForm.this, LocalisationMap.class);
               startActivity(intent);
                    }
                });

     viewDate = findViewById(R.id.date);
     viewDate.setOnClickListener(new View.OnClickListener() {
     public void onClick(View v) {

             dateJour();
         }
      });

      btnInsert.setOnClickListener(new View.OnClickListener() {
              @Override
      public void onClick(View v) {

              insert();
        }
                });
  }

      private void insert()  {
               // int numPar = Integer.parseInt(numParcel.getText().toString());
      int numPar = numParcelle.getValue();
      viewDate.getText().toString().trim();
      Date date = new Date(System.currentTimeMillis());

      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      String s = formatter.format(date);


                /*DateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                Date date = (Date) simpleDateFormat.parse((String) viewDate.getText());*/

//                Date date = (Date) viewDate.getText();
      String event = spinner.getSelectedItem().toString().trim();
      String parame = param.getText().toString().trim();
      System.out.println("numeroooooo parcelllllle est "+ numPar);
           /* if (date == null) {
                viewDate.setError("la date est obligatoire");
                viewDate.requestFocus();
                return;
            }*/
             //EventInsert eventInsert = new EventInsert(date, event,parame, numPar);

      Call<ResponseBody> call = new RetrofitClient()
                              .getService()
                              //.getApi()
                              .createEvent(s, event,parame, numPar);
      call.enqueue(new Callback<ResponseBody>() {
           @Override
       public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
           ResponseBody eventResponse = response.body();
                    //param.getText().clear();
                    numParcelle.setDisplayedValues(null);
                    spinner.setSelection(0);
                    viewDate.setText(R.string.default_name);
                    System.out.println("messsage est "+ eventResponse.toString());
                    Toast.makeText(EventForm.this, "bien enregistré", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    //Toast.makeText(EventForm.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println("onFailure "+t.getLocalizedMessage() +
                            t.getStackTrace() + t.getCause() + " messageee est "+ t.getMessage()) ;
                    Log.e(
                            "call Error", t.getMessage());
                }
            });


              }

            public void dateJour(){

                DatePickerDialog dialog = new DatePickerDialog(this,this, Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                );
                dialog.show();
            }

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                String dateDay =dayOfMonth +"/"+month +"/"+year;
                viewDate.setText(dateDay);
            }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        li.setVisibility(View.INVISIBLE);
                        li2.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        li.setVisibility(View.VISIBLE);
                        li2.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        li2.setVisibility(View.VISIBLE);
                        li.setVisibility(View.INVISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

            @Override
            public void onClick(View v) {

            }
        }