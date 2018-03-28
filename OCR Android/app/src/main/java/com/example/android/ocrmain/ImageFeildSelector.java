package com.example.android.ocrmain;

import java.io.File;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageView;
import com.example.android.ocrmain.data.HttpHandler;
import com.example.android.ocrmain.data.FormFieldDbHelper;
import com.example.android.ocrmain.data.FormFieldsList;

import org.json.JSONException;
import org.json.JSONObject;

public class ImageFeildSelector extends Activity {
    ImageView imageResult;
    private GuestListAdapter mAdapter;
    private SQLiteDatabase mDb;
    // COMPLETED (1) Create local EditText fields for mNewGuestNameEditText and mNewPartySizeEditText
    private EditText mNewColumnNameEditText;
    private EditText mNewXCodEditText;
    private EditText mNewYCodEditText;
    String formname;
    String ipadd;
    private String url;
    private static final String TAG = "ImageFeildSelector";

    // COMPLETED (13) Create a constant string LOG_TAG that is equal to the class.getSimpleName()
    private final static String LOG_TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_feild_selector);
        ipadd = getResources().getString(R.string.ipadd);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("formname",0);
        formname=sharedPref.getString("formname",null);
        Log.e("Formname:",formname);

        Intent intent = getIntent();
        String path = intent.getStringExtra("CropedImagePath");
        String stat = intent.getStringExtra("CropStat");

        imageResult = (ImageView)findViewById(R.id.result);

        RecyclerView collistRecyclerView;

        // Set local attributes to corresponding views
        collistRecyclerView = (RecyclerView) this.findViewById(R.id.all_guests_list_view);
        // COMPLETED (2) Set the Edit texts to the corresponding views using findViewById
        mNewColumnNameEditText = (EditText) this.findViewById(R.id.column_name_edit);
        mNewXCodEditText = (EditText) this.findViewById(R.id.x_cod_edit);
        mNewYCodEditText = (EditText) this.findViewById(R.id.y_cod_edit);
        Log.e("RotStatus",stat);
        if(stat.equals("1")) {
            File imgf = new File(path);
            Bitmap myImg = BitmapFactory.decodeFile(path);
            Matrix matrix = new Matrix();
            matrix.postRotate(0);
            Bitmap rotated = Bitmap.createBitmap(myImg, 0, 0, myImg.getWidth(), myImg.getHeight(),
                    matrix, true);
            Log.e("Height:",String.valueOf(myImg.getHeight()));
            imageResult.setImageBitmap(rotated);

        }else
        {
            Bitmap bmp = BitmapFactory.decodeFile(path);
            Matrix matrix = new Matrix();

            //set image rotation value to 90 degrees in matrix.
            matrix.postRotate(90);
            matrix.postScale(2f, 2f);

            int newWidth = bmp.getWidth();
            int newHeight = bmp.getHeight();
            Log.e("Height:",String.valueOf(bmp.getHeight()));
            imageResult.setImageBitmap(bmp);
            Bitmap bMapRotate = Bitmap.createBitmap(bmp, 0, 0, newWidth, newHeight, matrix, true);

            //put rotated image in ImageView.
            imageResult.setImageBitmap(bMapRotate);
        }

        imageResult.setOnTouchListener(new OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();
                int x = (int) event.getX();
                int y = (int) event.getY();
                switch(action){
                    case MotionEvent.ACTION_DOWN:
                        mNewXCodEditText.setText(String.valueOf(x));
                        mNewYCodEditText.setText(String.valueOf(y));
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mNewXCodEditText.setText(String.valueOf(x));
                        mNewYCodEditText.setText(String.valueOf(y));
                        break;
                    case MotionEvent.ACTION_UP:
                        mNewXCodEditText.setText(String.valueOf(x));
                        mNewYCodEditText.setText(String.valueOf(y));
                        break;
                }

                return true;
            }});


        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        collistRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Create a DB helper (this will create the DB if run for the first time)
        FormFieldDbHelper dbHelper = new FormFieldDbHelper(this);

        // Keep a reference to the mDb until paused or killed. Get a writable database
        // because you will be adding restaurant customers
        mDb = dbHelper.getWritableDatabase();

        // COMPLETED (3) Remove this fake data call since we will be inserting our own data now

        // Get all guest info from the database and save in a cursor
        Cursor cursor = getAllGuests(formname);

        // Create an adapter for that cursor to display the data
        mAdapter = new GuestListAdapter(this, cursor);

        // Link the adapter to the RecyclerView
        collistRecyclerView.setAdapter(mAdapter);

    }

    private Cursor getAllGuests(String formname) {
        String[] whereArgs = new String[] {
                formname
        };
        return mDb.query(
                FormFieldsList.FormFieldsEntry.TABLE_NAME,
                null,
                FormFieldsList.FormFieldsEntry.COLUMN_FORMID+"=?",
                whereArgs,
                null,
                null,
                FormFieldsList.FormFieldsEntry.COLUMN_TIMESTAMP
        );
    }


    /**
     * This method is called when user clicks on the Add to waitlist button
     *
     * @param view The calling view (button)
     */
    public void addToWaitlist(View view) {
        // COMPLETED (9) First thing, check if any of the EditTexts are empty, return if so
        if (mNewColumnNameEditText.getText().length() == 0 ||
                mNewXCodEditText.getText().length() == 0 ||
                mNewYCodEditText.getText().length() == 0) {
            return;
        }
        // COMPLETED (10) Create an integer to store the party size and initialize to 1
        //default party size to 1
        int Xcod = 0;
        int Ycod=0;
        // COMPLETED (11) Use Integer.parseInt to parse mNewPartySizeEditText.getText to an integer
        try {
            //mNewPartyCountEditText inputType="number", so this should always work
            Xcod = Integer.parseInt(mNewXCodEditText.getText().toString());
            Ycod = Integer.parseInt(mNewYCodEditText.getText().toString());
        } catch (NumberFormatException ex) {
            // COMPLETED (12) Make sure you surround the Integer.parseInt with a try catch and log any exception
            Log.e(LOG_TAG, "Failed to parse party size text to number: " + ex.getMessage());
        }

        // COMPLETED (14) call addNewGuest with the guest name and party size
        // Add guest info to mDb
        addNewGuest(mNewColumnNameEditText.getText().toString(), Xcod,Ycod);

        // COMPLETED (19) call mAdapter.swapCursor to update the cursor by passing in getAllGuests()
        // Update the cursor in the adapter to trigger UI to display the new list
        mAdapter.swapCursor(getAllGuests(formname));

        // COMPLETED (20) To make the UI look nice, call .getText().clear() on both EditTexts, also call clearFocus() on mNewPartySizeEditText
        //clear UI text fields
        mNewYCodEditText.clearFocus();
        mNewXCodEditText.getText().clear();
        mNewColumnNameEditText.getText().clear();
        mNewYCodEditText.getText().clear();
    }
    private long addNewGuest(String name, int Xcod,int Ycod) {
        // COMPLETED (5) Inside, create a ContentValues instance to pass the values onto the insert query
        ContentValues cv = new ContentValues();
        // COMPLETED (6) call put to insert the name value with the key COLUMN_GUEST_NAME
        cv.put(FormFieldsList.FormFieldsEntry.COLUMN_NAME, name);
        // COMPLETED (7) call put to insert the party size value with the key COLUMN_PARTY_SIZE
        cv.put(FormFieldsList.FormFieldsEntry.COLUMN_X,  Xcod);
        cv.put(FormFieldsList.FormFieldsEntry.COLUMN_Y,  Ycod);
        cv.put(FormFieldsList.FormFieldsEntry.COLUMN_FORMID, formname);
        // COMPLETED (8) call insert to run an insert query on TABLE_NAME with the ContentValues created
        return mDb.insert(FormFieldsList.FormFieldsEntry.TABLE_NAME, null, cv);
    }

    public void insertSQL(View view)
    {
        new getData().execute();
    }

    private class getData extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            Cursor cursor = getAllGuests(formname);
            String data= new String();
            if (cursor.moveToFirst()){
                do{
                    data = data+cursor.getString(cursor.getColumnIndex(FormFieldsList.FormFieldsEntry.COLUMN_NAME))+","+cursor.getString(cursor.getColumnIndex(FormFieldsList.FormFieldsEntry.COLUMN_X))+","+cursor.getString(cursor.getColumnIndex(FormFieldsList.FormFieldsEntry.COLUMN_Y))+"~";

                }while(cursor.moveToNext());
            }
            ipadd = getResources().getString(R.string.ipadd);
            url = "http://"+ipadd+"/AndroidJSON/test.php";
            cursor.close();
            Log.e("Cursor: ",data);
            url=url+"?name="+formname+"&det="+data;
            Log.e("Url:",url);
            HttpHandler httpob = new HttpHandler();
            String stat= httpob.makeServiceCall(url);
            Log.e("Status:",stat);
            JSONObject jsonObj = null;
            String resp=null;
            try {
                jsonObj = new JSONObject(stat);
                Log.e("doInBackgroundl", jsonObj.getString("status"));
                resp=jsonObj.getString("status");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(resp.equals("1"))
            {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
            }

            return null;
        }
    }
}
