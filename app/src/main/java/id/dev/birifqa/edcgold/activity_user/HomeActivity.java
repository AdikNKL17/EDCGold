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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
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

import android.view.Menu;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.adapter.ExpandableListAdapter;
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
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tvName, tvEmail;
    private Toolbar toolbar;
    private Session session;
    private Callback<ResponseBody> cBack;
    private NavigationView navigationView;
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
        anyChartView = findViewById(R.id.any_chart_view);
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        expandableListView = findViewById(R.id.expandableListView);
        headerView = navigationView.getHeaderView(0);
        tvName = headerView.findViewById(R.id.tv_name);
        tvEmail = headerView.findViewById(R.id.tv_email);
    }

    private void onAction(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        prepareMenuData();
        populateExpandableList();

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "YJHeVKv_W5Q";
                youTubePlayer.loadVideo(videoId, 0);
                youTubePlayer.pause();
            }
        });

        anyChartSetup();
    }

    private void anyChartSetup(){
        Cartesian cartesian = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Litecoin", 80540));
        data.add(new ValueDataEntry("Ethereum", 94190));
        data.add(new ValueDataEntry("Zcash", 102610));
        data.add(new ValueDataEntry("Dash", 110430));
        data.add(new ValueDataEntry("Ripple", 128000));
        data.add(new ValueDataEntry("Monero", 143760));
        data.add(new ValueDataEntry("Bitcoin Cash", 170670));
        data.add(new ValueDataEntry("NEO", 213210));
        data.add(new ValueDataEntry("Cardano", 249980));
        data.add(new ValueDataEntry("EOS ", 149980));

        Column column = cartesian.column(data);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("${%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("Top 10 Cryptocurrency Value");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Product");
        cartesian.yAxis(0).title("Revenue");

        anyChartView.setChart(cartesian);
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
        MenuModel menuModel = new MenuModel("Profil Saya", getResources().getDrawable(R.drawable.ic_account_circle_white_24dp), true, false); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Wallet", getResources().getDrawable(R.drawable.ic_wallet_white_24dp), true, true);
        headerList.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();
        MenuModel childModel = new MenuModel("Mining", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("Aging", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("Pembayaran", false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("Transaksi", false, false);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            Log.d("API123","here");
            childList.put(menuModel, childModelsList);
        }

        menuModel = new MenuModel("History", getResources().getDrawable(R.drawable.ic_history_white_24dp), false, false); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Komunitas",getResources().getDrawable(R.drawable.ic_community_white_24dp) , false, false); //Menu of Android Tutorial. No sub menus
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
                    if (headerList.get(groupPosition).menuName.equals("Profil Saya")) {
                        startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                    }

                    if (headerList.get(groupPosition).menuName.equals("History")) {
                        startActivity(new Intent(HomeActivity.this, HistoryActivity.class));
                    }

                    if (headerList.get(groupPosition).menuName.equals("Keluar Aplikasi")) {
                        logout();
                    }
                }

                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (childList.get(headerList.get(groupPosition)) != null) {
                    MenuModel model = childList.get(headerList.get(groupPosition)).get(childPosition);

                    if (model.menuName.equals("Mining")){
                        startActivity(new Intent(HomeActivity.this, MiningActivity.class));
                    } else if (model.menuName.equals("Aging")){
                        startActivity(new Intent(HomeActivity.this, AgingActivity.class));
                    } else if (model.menuName.equals("Pembayaran")){
                        startActivity(new Intent(HomeActivity.this, PembayaranActivity.class));
                    } else if (model.menuName.equals("Transaksi")){
                        startActivity(new Intent(HomeActivity.this, TransferActivity.class));
                    } else {
                        Toast.makeText(HomeActivity.this, "Error, Please contact the developer!!", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_home) {
//            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
//            startActivity(intent);
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_tools) {
//
//        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
