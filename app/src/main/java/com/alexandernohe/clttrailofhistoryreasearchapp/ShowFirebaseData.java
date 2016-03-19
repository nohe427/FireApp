package com.alexandernohe.clttrailofhistoryreasearchapp;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex7370 on 3/18/2016.
 */
public class ShowFirebaseData extends Activity{

    TextView mTitleTextView;
    LinearLayout mLinearLayout;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_of_statue);
        mTitleTextView = (TextView) findViewById(R.id.statue_title_in_content_view);
        mTitleTextView.setText(getIntent().getStringExtra("StatueTitle"));
        mLinearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        Firebase cltStatueResearchDB = new Firebase("https://clttrailresearch.firebaseio.com/" + getIntent().getStringExtra("StatueTitle"));
        cltStatueResearchDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mLinearLayout.removeAllViews();
                TextView titleView = new TextView(getApplicationContext());
                titleView.setText(getIntent().getStringExtra("StatueTitle"));
                titleView.setTextColor(Color.CYAN);
                mLinearLayout.addView(titleView);
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if(child.getKey().startsWith("text")) {
                        TextView additionalInformation = new TextView(getApplicationContext());
                        additionalInformation.setTextColor(Color.BLACK);
                        additionalInformation.setText(child.getKey() + " - " + child.getValue());
                        mLinearLayout.addView(additionalInformation);
                    }
                    else if (child.getKey().startsWith("image")) {
                        ImageView imageView = new ImageView(getApplicationContext());
                        byte[] imageAsBytes = Base64.decode(child.getValue().toString().getBytes(), Base64.DEFAULT);
                        imageView.setImageBitmap(
                                BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)
                        );
                        mLinearLayout.addView(imageView);
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });
    }
}
