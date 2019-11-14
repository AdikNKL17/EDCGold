package id.dev.birifqa.edcgold.activity_user;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.MarkerType;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.Menu;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.ExpandableListAdapter;
import id.dev.birifqa.edcgold.fragment_user.AgingAgingFragment;
import id.dev.birifqa.edcgold.fragment_user.FragmentProfile;
import id.dev.birifqa.edcgold.fragment_user.FragmentTopup;
import id.dev.birifqa.edcgold.fragment_user.FragmentTopupNominal;
import id.dev.birifqa.edcgold.fragment_user.FragmentUserHistory;
import id.dev.birifqa.edcgold.fragment_user.FragmentUserInfo;
import id.dev.birifqa.edcgold.fragment_user.FragmentUserProfile;
import id.dev.birifqa.edcgold.fragment_user.FragmentUserWallet;
import id.dev.birifqa.edcgold.fragment_user.MiningAktifFragment;
import id.dev.birifqa.edcgold.fragment_user.MiningNonaktifFragment;
import id.dev.birifqa.edcgold.fragment_user.PembayaranTopupFragment;
import id.dev.birifqa.edcgold.model.nav_drawer.MenuModel;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Handle;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private TextView tvName, tvEmail;
    private Toolbar toolbar;
    private Session session;
    private Callback<ResponseBody> cBack;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private DrawerLayout drawer;
    private View headerView;
    private AnyChartView anyChartView;
    private YouTubePlayerView youTubePlayerView;
    private ExpandableListAdapter expandableListAdapter;
    private ExpandableListView expandableListView;
    private List<MenuModel> headerList = new ArrayList<>();
    private HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    private boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViewById();
        onAction();

        getUserDetail();
    }

    private void findViewById(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        frameLayout = findViewById(R.id.frame_layout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        /*anyChartView = findViewById(R.id.any_chart_view);
        youTubePlayerView = findViewById(R.id.youtube_player_view);*/
        expandableListView = findViewById(R.id.expandableListView);
        headerView = navigationView.getHeaderView(0);
        tvName = headerView.findViewById(R.id.tv_name);
        tvEmail = headerView.findViewById(R.id.tv_email);
    }

    private void onAction(){
        loadFragment(new FragmentUserWallet());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        prepareMenuData();
        populateExpandableList();

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

    }

    private void getUserDetail(){
        Call<ResponseBody> call = ParamReq.requestUserDetail(session.get("token"), HomeActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    boolean handle = Handle.handleHome(response.body().string(), tvName, tvEmail, HomeActivity.this);
                    if (handle) {

                    } else {
                        Api.mProgressDialog.dismiss();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Api.retryDialog(HomeActivity.this, call, cBack, 1, false);
            }
        };
        Api.enqueueWithRetry(HomeActivity.this, call, cBack, false, "Loading");
    }

    private void prepareMenuData(){
        MenuModel menuModel = new MenuModel("Top Up", getResources().getDrawable(R.drawable.ic_file_upload_white_24dp), true, false); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Setting", getResources().getDrawable(R.drawable.ic_icons8_settings), false, false); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Komunitas",getResources().getDrawable(R.drawable.ic_community_white_24dp) , false, false); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Lock",getResources().getDrawable(R.drawable.ic_icons8_lock_2) , false, false); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Keluar Aplikasi", getResources().getDrawable(R.drawable.ic_exit_white_24dp), false, false); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }
    }



    private void populateExpandableList(){
        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (!headerList.get(groupPosition).isGroup) {
                    if (headerList.get(groupPosition).menuName.equals("Top Up")) {
                        loadFragment(new FragmentTopup());
                        drawer.closeDrawer(GravityCompat.START, true);
                    }

                    if (headerList.get(groupPosition).menuName.equals("Setting")) {

                    }

                    if (headerList.get(groupPosition).menuName.equals("Komunitas")) {

                    }

                    if (headerList.get(groupPosition).menuName.equals("Lock")) {

                    }

                    if (headerList.get(groupPosition).menuName.equals("Keluar Aplikasi")) {
                        logout();
                    }
                }

                return false;
            }
        });
    }

    private void logout(){
        final Dialog dialog1 = new Dialog(HomeActivity.this);
        dialog1.setContentView(R.layout.dialog_logout);
        dialog1.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnYes, btnCancel;

        btnYes = dialog1.findViewById(R.id.button_yes);
        btnCancel = dialog1.findViewById(R.id.button_cancel);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.clear();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                HomeActivity.this.finish();
                dialog1.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });

        dialog1.show();
    }

    private void showQR(){
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dialog_refferral, viewGroup, false);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView btnClose = dialogView.findViewById(R.id.btn_close);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                HomeActivity.this.finish();
                moveTaskToBack(true);
                System.exit(0);
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_tentang) {
            return true;
        } else if (id == R.id.action_faq){
            return true;
        } else if (id == R.id.action_feedback){
            startActivity(new Intent(HomeActivity.this, FeedbackActivity.class));
            return true;
        } else if (id == R.id.action_petunjuk){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_wallet) {
            loadFragment(new FragmentUserWallet());
        } else if (id == R.id.action_history) {
            loadFragment(new FragmentUserHistory());
        } else if (id == R.id.action_profile) {
            loadFragment(new FragmentUserProfile());
        } else if (id == R.id.action_info) {
            loadFragment(new FragmentUserInfo());
        } else if (id == R.id.action_qr){
            showQR();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
