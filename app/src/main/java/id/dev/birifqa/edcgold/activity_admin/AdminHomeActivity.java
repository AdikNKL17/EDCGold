package id.dev.birifqa.edcgold.activity_admin;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.util.Pair;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.Menu;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import id.dev.birifqa.edcgold.R;
import id.dev.birifqa.edcgold.activity_user.AgingActivity;
import id.dev.birifqa.edcgold.activity_user.HomeActivity;
import id.dev.birifqa.edcgold.activity_user.LoginActivity;
import id.dev.birifqa.edcgold.activity_user.MiningActivity;
import id.dev.birifqa.edcgold.activity_user.PembayaranActivity;
import id.dev.birifqa.edcgold.activity_user.ProfileActivity;
import id.dev.birifqa.edcgold.adapter.ExpandableListAdapter;
import id.dev.birifqa.edcgold.fragment_admin.FragmentAdminHistory;
import id.dev.birifqa.edcgold.fragment_admin.FragmentAdminMining;
import id.dev.birifqa.edcgold.fragment_admin.FragmentAdminNotification;
import id.dev.birifqa.edcgold.fragment_admin.FragmentAdminProfile;
import id.dev.birifqa.edcgold.fragment_admin.FragmentAdminReport;
import id.dev.birifqa.edcgold.fragment_admin.FragmentAdminTopup;
import id.dev.birifqa.edcgold.fragment_admin.FragmentAdminUser;
import id.dev.birifqa.edcgold.fragment_admin.FragmentAdminWithdraw;
import id.dev.birifqa.edcgold.fragment_user.FragmentUserHistory;
import id.dev.birifqa.edcgold.fragment_user.FragmentUserInfo;
import id.dev.birifqa.edcgold.fragment_user.FragmentUserProfile;
import id.dev.birifqa.edcgold.fragment_user.FragmentUserWallet;
import id.dev.birifqa.edcgold.model.nav_drawer.MenuModel;
import id.dev.birifqa.edcgold.utils.Api;
import id.dev.birifqa.edcgold.utils.Handle;
import id.dev.birifqa.edcgold.utils.ParamReq;
import id.dev.birifqa.edcgold.utils.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {


    private TextView tvName, tvEmail;
    private Toolbar toolbar;
    private Session session;
    private Callback<ResponseBody> cBack;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View headerView;
    private ExpandableListAdapter expandableListAdapter;
    private ExpandableListView expandableListView;
    private ConstraintLayout btnQuickMenu, btnNotification;

    private List<MenuModel> headerList = new ArrayList<>();
    private HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        findViewById();
        onAction();
    }

    private void findViewById(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        frameLayout = findViewById(R.id.frame_layout);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        expandableListView = findViewById(R.id.expandableListView);
        headerView = navigationView.getHeaderView(0);
        tvName = headerView.findViewById(R.id.tv_name);
        tvEmail = headerView.findViewById(R.id.tv_email);
        btnQuickMenu = toolbar.findViewById(R.id.btn_quick_menu);
        btnNotification = toolbar.findViewById(R.id.btn_notification);
    }

    private void onAction(){
//        getUserDetail();
        loadFragment(new FragmentAdminUser());

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        prepareMenuData();
        populateExpandableList();

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        btnQuickMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quickMenu();
            }
        });

        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new FragmentAdminNotification());
            }
        });
    }

    private void quickMenu(){
        final Dialog dialog1 = new Dialog(AdminHomeActivity.this);
        dialog1.setContentView(R.layout.dialog_quick_menu);
        dialog1.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dialog1.show();
    }

//    private void getUserDetail(){
//        Call<ResponseBody> call = ParamReq.requestUserDetail(session.get("token"), AdminHomeActivity.this);
//        cBack = new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    boolean handle = Handle.handleHome(response.body().string(), tvName, tvEmail, AdminHomeActivity.this);
//                    if (handle) {
//
//
//                    } else {
//                        Api.mProgressDialog.dismiss();
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Api.retryDialog(AdminHomeActivity.this, call, cBack, 1, false);
//            }
//        };
//        Api.enqueueWithRetry(AdminHomeActivity.this, call, cBack, false, "Loading");
//    }

    private void prepareMenuData(){
        MenuModel menuModel = new MenuModel("Profil Saya", getResources().getDrawable(R.drawable.ic_account_circle_white_24dp), true, false);
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Report", getResources().getDrawable(R.drawable.ic_history_white_24dp), false, false);
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Post",getResources().getDrawable(R.drawable.ic_community_white_24dp) , true, true);
        headerList.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();
        MenuModel childModel = new MenuModel("Tentang", false, true);
        childModelsList.add(childModel);

        childModel = new MenuModel("Komunitas", false, true);
        childModelsList.add(childModel);

        childModel = new MenuModel("FAQ", false, true);
        childModelsList.add(childModel);

        childModel = new MenuModel("Feedback", false, true);
        childModelsList.add(childModel);

        childModel = new MenuModel("Petunjuk", false, true);
        childModelsList.add(childModel);


        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        menuModel = new MenuModel("Update",getResources().getDrawable(R.drawable.ic_community_white_24dp) , false, false); //Menu of Android Tutorial. No sub menus
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

    private void logout(){
        final Dialog dialog1 = new Dialog(AdminHomeActivity.this);
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
                startActivity(new Intent(AdminHomeActivity.this, LoginActivity.class));
                AdminHomeActivity.this.finish();
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

    private void populateExpandableList(){
        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (!headerList.get(groupPosition).isGroup) {
                    if (headerList.get(groupPosition).menuName.equals("Profil Saya")) {
                        loadFragment(new FragmentAdminProfile());
                        drawer.closeDrawer(GravityCompat.START, true);
                    }

                    if (headerList.get(groupPosition).menuName.equals("Report")) {
                        loadFragment(new FragmentAdminReport());
                        drawer.closeDrawer(GravityCompat.START, true);
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
                        startActivity(new Intent(AdminHomeActivity.this, MiningActivity.class));
                    } else if (model.menuName.equals("Aging")){
                        startActivity(new Intent(AdminHomeActivity.this, AgingActivity.class));
                    } else if (model.menuName.equals("Pembayaran")){
                        startActivity(new Intent(AdminHomeActivity.this, PembayaranActivity.class));
                    } else if (model.menuName.equals("Transaksi")){
//                        startActivity(new Intent(HomeActivity.this, Transaks));
                    } else {
                        Toast.makeText(AdminHomeActivity.this, "Error, Please contact the developer!!", Toast.LENGTH_SHORT).show();
                    }
                }

                return false;
            }
        });
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
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_user) {
            loadFragment(new FragmentAdminUser());
        } else if (id == R.id.action_history) {
            loadFragment(new FragmentAdminHistory());
        } else if (id == R.id.action_mining) {
            loadFragment(new FragmentAdminMining());
        } else if (id == R.id.action_topup) {
            loadFragment(new FragmentAdminTopup());
        } else if (id == R.id.action_withdraw) {
            loadFragment(new FragmentAdminWithdraw());
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
