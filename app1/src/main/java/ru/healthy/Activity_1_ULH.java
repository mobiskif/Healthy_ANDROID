package ru.healthy;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class Activity_1_ULH extends ActivityBase {
    private DrawerLayout mDrawerLayout;

    public Activity_1_ULH() {
        super();
        content_view = R.layout.activity_1_ulh;
        spinner_arr = "GetLPUList";
        card_arr = "GetPatientHistory";
        list_arr = "GetPatientHistory";
        button_text = "Талоны и расписание";
    }

    @Override
    void config_ToolbarAndMenu() {
        super.config_ToolbarAndMenu();
        if (getSupportActionBar() != null) {
            VectorDrawableCompat indicator = VectorDrawableCompat.create(getResources(), R.drawable.ic_menu, getTheme());
            indicator.setTint(ResourcesCompat.getColor(getResources(), R.color.white, getTheme()));
            getSupportActionBar().setHomeAsUpIndicator(indicator);
        }

        mDrawerLayout = findViewById(R.id.drawer);
        NavigationView navigationView = findViewById(R.id.nav_view);
        onCreateOptionsMenu(navigationView.getMenu());
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        doItem(menuItem);
                        return true;
                    }
                });
    }

    @Override
    void set_Visiblity() {
        super.set_Visiblity();
        findViewById(R.id.label1).setVisibility(View.VISIBLE);
    }

    @Override
    void restore_Values() {
        super.restore_Values();
        label1_text = Storage.restore(this, "GetDistrictList_str") + " (" + Storage.restore(this, "CheckPatient") +")";
        //label1_text = Storage.restore(this, "CheckPatient");
        textview_text = Storage.restore(this, "FIO");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        if (v.getId() == R.id.textview) startActivity(new Intent(getApplicationContext(), Activity_0_UA.class));
        else if (v.getId() == R.id.button) startActivity(new Intent(getApplicationContext(), Activity_2_LSD.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_drawer, menu);

        String currentUser = Storage.getCurrentUser(this);
        int id = Integer.valueOf(currentUser);
        MenuItem item = menu.getItem(id);
        item.setIcon(R.drawable.redcross_small);
        /**/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) mDrawerLayout.openDrawer(GravityCompat.START);
        else doItem(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent,view,position,id);
        Intent intent = new Intent(this, Activity_5_YN.class);
        intent.putExtra("message", getString(R.string.cancel_talon));
        startActivityForResult(intent, 1);
    }

    public void doItem(MenuItem menuItem) {
        //menuItem.setChecked(true);
        String s = menuItem.getTitle().toString();
        if (s.equals(getString(R.string.menu0))) startActivity(new Intent(getApplicationContext(), Activity_0_UA.class));
        else if (s.equals(getString(R.string.umenu0))) Storage.setCurrentUser(this, "0");
        else if (s.equals(getString(R.string.umenu1))) Storage.setCurrentUser(this, "1");
        else if (s.equals(getString(R.string.umenu2))) Storage.setCurrentUser(this, "2");
        mDrawerLayout.closeDrawers();

        restore_Values();
        show_Values();

        NavigationView navigationView = findViewById(R.id.nav_view);
        onCreateOptionsMenu(navigationView.getMenu());
    }

}
