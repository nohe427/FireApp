package com.alexandernohe.clttrailofhistoryreasearchapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexandernohe on 3/18/16.
 */
public class AddingStuffActivity extends Activity {

    FloatingActionButton addItemFAB;
    FloatingActionButton addThingFAB;
    FloatingActionButton addPicFAB;
    LinearLayout ll;

    protected void onCreate(Bundle savedInstanceStatus) {
        super.onCreate(savedInstanceStatus);
        setContentView(R.layout.adding_stuff_layout);
        addItemFAB = (FloatingActionButton) findViewById(R.id.add_item);
        addThingFAB = (FloatingActionButton) findViewById(R.id.add_thing);
        ll = (LinearLayout) findViewById(R.id.ll_items);

        addPicFAB = (FloatingActionButton) findViewById(R.id.add_pic);
        addPicFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 1337);
            }
        });

        addThingFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = new EditText(getApplicationContext());
                editText.setHint("NEW TEXT");
                editText.setTextColor(Color.BLACK);
                ll.addView(editText);
            }
        });

        addItemFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String field = "";
                Map<String, String> allData = new HashMap<String, String>();
                Firebase cltStatueResearchDB = new Firebase("https://clttrailresearch.firebaseio.com/");
                for(int i = 0; i < ll.getChildCount(); i++)
                {
                    if (i == 0) {
                        ll.getChildAt(i);
                        field = ((TextView)ll.getChildAt(i)).getText().toString();
                    }
                    else {
                        if (ll.getChildAt(i) instanceof TextView) {
                            allData.put("text" + i, ((TextView) ll.getChildAt(i)).getText().toString());
                        }
                        else if (ll.getChildAt(i) instanceof ImageView) {
                            ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
                            Bitmap bmp = ((BitmapDrawable) ((ImageView) ll.getChildAt(i)).getDrawable()).getBitmap();
                            bmp.compress(Bitmap.CompressFormat.PNG, 100, bYtE);
                            bmp.recycle();
                            byte[] byteArray = bYtE.toByteArray();
                            String imageFile = Base64.encodeToString(byteArray, Base64.DEFAULT);
                            allData.put("image" + i, imageFile);
                        }
                    }
                }
                cltStatueResearchDB.child(field).setValue(allData);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1337 && resultCode == RESULT_OK) {
            ImageView imageView = new ImageView(getApplicationContext());
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            ll.addView(imageView);
        }
    }
}
