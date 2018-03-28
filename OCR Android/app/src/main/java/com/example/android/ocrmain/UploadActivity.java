package com.example.android.ocrmain;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.android.ocrmain.data.HttpHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class UploadActivity extends AppCompatActivity {

    String ipadd,tempname,formname;
    private String uploadUrl;
    private String cropUrl,cvalUrl,insUrl;
    private String ocrUrl;
    private Bitmap bitmap;
    int fieldid[],valid[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        ipadd = getResources().getString(R.string.ipadd);
        uploadUrl = "http://"+ipadd+"/AndroidUpload/updateinfo.php";
        cropUrl="http://"+ipadd+"/AndroidUpload/uploads/connector.php";
        ocrUrl="http://"+ipadd+"/AndroidUpload/uploads/GVision/vendor/authen.php";


        Intent intentget = getIntent();
        String path = intentget.getStringExtra("CropedImagePath");
        tempname = intentget.getStringExtra("tempname");
        Log.e("tempname",tempname);
        formname = intentget.getStringExtra("formname");
        Log.e("formname",formname);
        cvalUrl="http://"+ipadd+"/AndroidJSON/selectco.php?name="+tempname;
        insUrl="http://"+ipadd+"/AndroidJSON/insert.php?name="+tempname+"&formname"+formname;
        bitmap = BitmapFactory.decodeFile(path);
        fieldid= new int[20];
        valid=new int[20];
        uploadImage();
    }

    private void uploadImage()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uploadUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String Response = jsonObject.getString("response");
                            Toast.makeText(UploadActivity.this,Response,Toast.LENGTH_LONG).show();

                            if(Response.equals("Image Uploaded Successfully"))
                            {
                                new GetCrop().execute();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name","tempimg");
                params.put("image",imageToString(bitmap));
                return params;
            }
        };
        MySingleton.getmInstance(UploadActivity.this).addToRequestQue(stringRequest);
    }

    private class GetCrop extends AsyncTask<Void, Void, Void> {


        String resp=null,fnames=null,coordvalues=null;
        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler httpob = new HttpHandler();
            String stat= httpob.makeServiceCall(cropUrl);
            Log.e("Status:",stat);
            resp=null;
            fnames=null;
            JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject(stat);
                Log.e("doInBackgroundl", jsonObj.getString("status"));
                resp=jsonObj.getString("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(!resp.equals("0"))
            {
                Log.e("Crop","done");
                HttpHandler httpob2 = new HttpHandler();
                String stat2= httpob2.makeServiceCall(ocrUrl);
                Log.e("Status2", stat2);
                try {
                    jsonObj = new JSONObject(stat2);
                    resp=resp+"/"+jsonObj.getString("status");
                    fnames=jsonObj.getString("fnames");
                    Log.e("Final",resp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String coordvaluesjson= httpob.makeServiceCall(cvalUrl);
                try {
                    jsonObj = new JSONObject(coordvaluesjson);
                    coordvalues=jsonObj.getString("detail");
                    Log.e("coord",coordvalues);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            takeValue(resp,fnames,coordvalues);
        }
    }

    private void takeValue(String vals,String fnames,String coordvalues)
    {
        Log.e("Building","true");
        String[] splitval = vals.split("/");
        String[] fname = fnames.split("/");
        String[] coordval = coordvalues.split("~");
        LinearLayout.LayoutParams paraedi = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout lm=((LinearLayout) findViewById(R.id.activity_uploadlay));
        for(int i=0;i<Integer.parseInt(splitval[0]);i++)
        {
            LinearLayout limg = new LinearLayout(this);
            limg.setOrientation(LinearLayout.HORIZONTAL);
            ImageView fieldimg = new ImageView(this);
            String url = "http://"+ipadd+"/AndroidUpload/uploads/GVision/vendor/cimg/"+fname[i];
            //Log.e("urlimg",url);
            ImageView iv = new ImageView(this);
            LinearLayout.LayoutParams imglay = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,100);
            iv.setLayoutParams(imglay);
            Picasso.with(this.getApplicationContext()).load(url).into(iv);
            limg.setLayoutParams(imglay);
            limg.addView(iv);
            lm.addView(limg);

            Log.e("Coordval",coordval[0]);
            String xy[]= fname[i].substring(0,fname[i].length()-4).split("_");
            Log.e("X-Y",xy[0]+" "+xy[1]);

            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            // Create TextView
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            Spinner tname = new Spinner(this);
            fieldid[i]=tname.generateViewId();
            tname.setId(fieldid[i]);
            String dbxy[]=coordval[0].split(",");
            int min=Math.abs(Integer.parseInt(dbxy[2])-Integer.parseInt(xy[1])),pos=0;
            Log.e("min",String.valueOf(min));
            String fieldname=dbxy[0];
            String[] items = new String[coordval.length];
            items[0]=dbxy[0];
            for(int j=1;j<coordval.length;j++)
            {
                dbxy=coordval[j].split(",");
                items[j]=dbxy[0];
                Log.e("coordval",coordval[j]);
                if(Math.abs(Integer.parseInt(dbxy[2])-Integer.parseInt(xy[1]))<min)
                {
                    min=Math.abs(Integer.parseInt(dbxy[2])-Integer.parseInt(xy[1]));
                    pos=j;
                    fieldname=dbxy[0];
                    Log.e("min",String.valueOf(min));
                }
            }
            //insUrl=insUrl+"&"+fieldname+"="+splitval[i+1];
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
            tname.setAdapter(adapter);
            tname.setSelection(pos);
            tname.setLayoutParams(lparams);
            ll.addView(tname);

            // Create TextView
            EditText name = new EditText(this);
            valid[i]=name.generateViewId();
            name.setId(valid[i]);
            name.setHint("Field No."+String.valueOf(i));
            name.setPadding(40,0,0,5);
            Drawable bginp = ContextCompat.getDrawable(getApplicationContext(),R.drawable.text);
            name.setBackground(bginp);
            name.setText(splitval[i+1]);
            name.setTextSize(22);
            name.setLayoutParams(paraedi);
            ll.addView(name);
            ll.setPadding(0,30,0,30);
            lm.addView(ll);
        }
        LinearLayout.LayoutParams buttlay = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        Button but = new Button(this);
        but.setText("Insert Data");
        but.setBackgroundColor(Color.parseColor("#eb197019"));
        but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                insertSQL(v);
            }
        });
        but.setTextColor(Color.parseColor("#ffffff"));
        but.setLayoutParams(buttlay);
        lm.addView(but);
        Log.e("insUrl",insUrl);
    }

    private void insertSQL(View view)
    {
        insUrl="http://"+ipadd+"/AndroidJSON/insert.php?name="+tempname+"&formname="+formname;
        for(int i=0;i<fieldid.length;i++)
        {
            if(fieldid[i]!=0)
            {
                Spinner s= (Spinner)findViewById(fieldid[i]);
                String fname=s.getSelectedItem().toString();
                EditText e =(EditText)findViewById(valid[i]);
                String vname = e.getText().toString();
                insUrl=insUrl+"&"+fname+"="+vname;
            }
            else
            {
                break;
            }
        }
        Log.e("insUrl",insUrl);
        new Ins().execute();
    }

    private class Ins extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            HttpHandler httpob = new HttpHandler();
            String stat= httpob.makeServiceCall(insUrl);
            String resp=null;
            Log.e("stat",stat);
            JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject(stat);
                Log.e("doInBackgroundl", jsonObj.getString("status"));
                resp=jsonObj.getString("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(!resp.equals("0"))
            {
                Intent intent = new Intent(getApplicationContext(), TemplateActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                intent.putExtra("formname",tempname);
                startActivity(intent);
            }
            return null;
        }
    }

        private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes =  byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }
}
