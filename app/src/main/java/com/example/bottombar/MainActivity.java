package com.example.bottombar;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final int TOTAL_NUMBER = 100;
    private RecyclerView recyclerView;
    private BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Toast.makeText(MainActivity.this, R.string.title_home, Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_dashboard:
                    Toast.makeText(MainActivity.this, R.string.title_dashboard, Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_notifications:
                    Toast.makeText(MainActivity.this, R.string.title_notifications, Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUi();
    }

    private void setupUi() {
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        NumberAdapter adapter = new NumberAdapter();
        recyclerView.setAdapter(adapter);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        CoordinatorLayout.LayoutParams params =
                (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        params.setBehavior(new BottomNavigationBehavior());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private class NumberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.view_item, parent, false);
            return new NumberViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
           TextView textView = holder.itemView.findViewById(R.id.item_text_view);
            textView.setText(String.valueOf(position));
        }

        @Override
        public int getItemCount() {
            return TOTAL_NUMBER;
        }
    }

    private class NumberViewHolder extends RecyclerView.ViewHolder {
        public NumberViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class BottomNavigationBehavior extends CoordinatorLayout.Behavior<BottomNavigationView> {
        public BottomNavigationBehavior() {
            super();
        }

        public BottomNavigationBehavior(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public boolean layoutDependsOn(CoordinatorLayout parent, BottomNavigationView child, View dependency) {
            boolean dependsOn = dependency instanceof FrameLayout;
            return dependsOn;
        }

        @Override
        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, BottomNavigationView child, View directTargetChild, View target, int nestedScrollAxes) {
            return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
        }

        @Override
        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, BottomNavigationView child, View target, int dx, int dy, int[] consumed) {
            if(dy < 0) {
                showBottomNavigationView(child);
            }
            else if(dy > 0) {
                hideBottomNavigationView(child);
            }
        }

        private void hideBottomNavigationView(BottomNavigationView view) {
            view.animate().translationY(view.getHeight());
        }

        private void showBottomNavigationView(BottomNavigationView view) {
            view.animate().translationY(0);
        }
    }
}
