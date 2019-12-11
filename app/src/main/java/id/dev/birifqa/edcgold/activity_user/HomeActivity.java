package id.dev.birifqa.edcgold.activity_user;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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

import net.glxn.qrgen.android.QRCode;

import org.json.JSONObject;

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
import id.dev.birifqa.edcgold.fragment_user.FragmentUserMining;
import id.dev.birifqa.edcgold.fragment_user.FragmentUserNotification;
import id.dev.birifqa.edcgold.fragment_user.FragmentUserProfile;
import id.dev.birifqa.edcgold.fragment_user.FragmentUserWallet;
import id.dev.birifqa.edcgold.fragment_user.FragmentWithdraw;
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

    private TextView tvName, tvCoin, tvNameHeader, tvEmailHeader;
    private ImageView imgFoto;
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
        tvName = findViewById(R.id.tv_name);
        tvCoin = findViewById(R.id.tv_coin);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        expandableListView = findViewById(R.id.expandableListView);
        headerView = navigationView.getHeaderView(0);
        tvNameHeader = headerView.findViewById(R.id.tv_name_header);
        tvEmailHeader = headerView.findViewById(R.id.tv_email_header);
        imgFoto = headerView.findViewById(R.id.img_foto);
    }

    private void onAction(){
        loadFragment(new FragmentUserProfile());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        prepareMenuData();
        populateExpandableList();

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        Session.save("wallet_id_penerima", "");
        Session.save("wallet_nama_penerima", "");
        Session.save("wallet_sale_rate", "");
        Session.save("wallet_buy_rate", "");
        Session.save("wallet_fee", "");

    }

    private void getUserDetail(){
        Call<ResponseBody> call = ParamReq.requestUserDetail(Session.get("token"), HomeActivity.this);
        cBack = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONObject coinObject = dataObject.getJSONObject("coin");
                    JSONObject referralObject = dataObject.getJSONObject("referral");
                    Session.save("referral", referralObject.getString("referral_code"));
                    tvNameHeader.setText(dataObject.getString("name") + " "+dataObject.getString("lastname"));
                    tvEmailHeader.setText(dataObject.getString("email"));
                    Glide.with(imgFoto).load(dataObject.getString("avatar"))
                            .apply(RequestOptions.circleCropTransform()).into(imgFoto);
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
        MenuModel menuModel = new MenuModel("Top Up", getResources().getDrawable(R.drawable.ic_wallet_white_24dp), true, false); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

//        menuModel = new MenuModel("Withdraw", getResources().getDrawable(R.drawable.ic_icons8_wallet_copy_2), true, false); //Menu of Android Tutorial. No sub menus
//        headerList.add(menuModel);
//
//        if (!menuModel.hasChildren) {
//            childList.put(menuModel, null);
//        }

        menuModel = new MenuModel("Pengaturan", getResources().getDrawable(R.drawable.ic_icons8_settings), true, false); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Komunitas", getResources().getDrawable(R.drawable.ic_community_white_24dp), false, false); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Lock",getResources().getDrawable(R.drawable.ic_icons8_lock_2) , false, false); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Information",getResources().getDrawable(R.drawable.ic_icons8_info) , false, false); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Logout", getResources().getDrawable(R.drawable.ic_exit_white_24dp), false, false); //Menu of Android Tutorial. No sub menus
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

                    if (headerList.get(groupPosition).menuName.equals("Withdraw")) {
                        loadFragment(new FragmentWithdraw());
                        drawer.closeDrawer(GravityCompat.START, true);
                    }

                    if (headerList.get(groupPosition).menuName.equals("Pengaturan")) {
                        startActivity(new Intent(v.getContext(), SettingActivity.class));
                    }

                    if (headerList.get(groupPosition).menuName.equals("Komunitas")) {
                        startActivity(new Intent(v.getContext(), KomunitasActivity.class));
                    }

                    if (headerList.get(groupPosition).menuName.equals("Lock")) {

                    }

                    if (headerList.get(groupPosition).menuName.equals("Logout")) {
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
        ImageView btnCopy = dialogView.findViewById(R.id.btn_copy);
        ImageView imgQrCode = dialogView.findViewById(R.id.img_qr_code);
        TextView tvReferral = dialogView.findViewById(R.id.tv_referral);

        tvReferral.setText(Session.get("referral"));

        Bitmap qrBitmap = QRCode.from(Session.get("referral")).bitmap();
        imgQrCode.setImageBitmap(qrBitmap);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("referral", Session.get("referral"));
                clipboard.setPrimaryClip(clip);

                Toast.makeText(HomeActivity.this, "Referral code is copied", Toast.LENGTH_SHORT).show();
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


        if (id == R.id.action_notif) {
            loadFragment(new FragmentUserNotification());
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
        } else if (id == R.id.action_user) {
            loadFragment(new FragmentUserProfile());
        } else if (id == R.id.action_mining) {
            loadFragment(new FragmentUserMining());
        } else if (id == R.id.action_qr){
            showQR();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
