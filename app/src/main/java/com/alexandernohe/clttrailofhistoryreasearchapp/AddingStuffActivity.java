package com.alexandernohe.clttrailofhistoryreasearchapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;

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
                        allData.put("text" + i, ((TextView)ll.getChildAt(i)).getText().toString());
                    }
                }
                cltStatueResearchDB.child(field).setValue(allData);
            }
        });
    }
}
