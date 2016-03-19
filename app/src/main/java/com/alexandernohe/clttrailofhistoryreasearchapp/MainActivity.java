package com.alexandernohe.clttrailofhistoryreasearchapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.client.snapshot.IndexedNode;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Firebase.setAndroidContext(this);
        Firebase cltStatueResearchDB = new Firebase("https://clttrailresearch.firebaseio.com/");

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final RecyclerView statueRecycler = (RecyclerView) findViewById(R.id.content_recycler);
        statueRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        final List<StatueInformation> statues = new ArrayList<StatueInformation>();
        /*statues.add(new StatueInformation("TONY", 1, 1));
        for(int i = 0; i < 15; i++)
        {
            statues.add(new StatueInformation());
        }*/
        cltStatueResearchDB.child("Tony");

        final StatueAdapter statueAdapter = new StatueAdapter(statues);
        statueRecycler.setAdapter(statueAdapter);

        cltStatueResearchDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<StatueInformation> statues2 = new ArrayList<StatueInformation>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.e("NOHE", child.getKey());
                    statues2.add(new StatueInformation(child.getKey().toString(), 0, 0));
                    statueAdapter.setStatueInformations(statues2);
                    statueAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddingStuffActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class StatueAdapter extends RecyclerView.Adapter<StatueHolder> {
        private List<StatueInformation> mStatueInformations;

        public StatueAdapter(List<StatueInformation> statueInformationList) {mStatueInformations = statueInformationList;}

        @Override
        public StatueHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
            View view = layoutInflater
                    .inflate(R.layout.statue_list_item, parent, false);
            return new StatueHolder(view);
        }

        @Override
        public void onBindViewHolder(StatueHolder holder, int position) {
                StatueInformation statueInformation = mStatueInformations.get(position);
                holder.bindStatue(statueInformation);
        }


        @Override
        public int getItemCount() {
            return mStatueInformations.size();
        }

        public void setStatueInformations(List<StatueInformation> statueInformationsList) {mStatueInformations = statueInformationsList;}
    }

    private class StatueHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private StatueInformation mStatue;

        private TextView mTitleView;

        public void bindStatue(StatueInformation statue)
        {
            mStatue = statue;
            mTitleView.setText(mStatue.getName());
        }

        public StatueHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleView = (TextView) itemView.findViewById(R.id.statue_title);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(MainActivity.this, ShowFirebaseData.class);
            i.putExtra("StatueTitle", mStatue.getName());
            startActivity(i);
        }
    }
}
