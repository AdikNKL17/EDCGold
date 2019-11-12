package id.dev.birifqa.edcgold.fragment_user;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.dev.birifqa.edcgold.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTopupConfirmation extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private View view;
    private ImageView btnCamera;

    private static final int PERMISSION_REQUEST_CODE = 1100;
    String[] appPermission = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public FragmentTopupConfirmation() {
        // Required empty public constructor
    }

    public static FragmentTopupConfirmation newInstance(String param1, String param2) {
        FragmentTopupConfirmation fragment = new FragmentTopupConfirmation();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_topup_confirmation, container, false);

        btnCamera = view.findViewById(R.id.btn_camera);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAndRequestPermission()){

                }
            }
        });

        return view;
    }

    public boolean checkAndRequestPermission(){
        List<String> listPermissionNeeded = new ArrayList<>();
        for (String perm: appPermission){
            if (ContextCompat.checkSelfPermission(getActivity(), perm) != PackageManager.PERMISSION_GRANTED){
                listPermissionNeeded.add(perm);
            }
        }
        if (!listPermissionNeeded.isEmpty()){
            ActivityCompat.requestPermissions(getActivity(), listPermissionNeeded.toArray(new String[listPermissionNeeded.size()]), PERMISSION_REQUEST_CODE);
            return false;
        }

        return true;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE){
            HashMap<String, Integer> permissionResults = new HashMap<>();
            int deniedCount = 0;

            for (int i=0; i<grantResults.length;i++){
                if (grantResults[i] == PackageManager.PERMISSION_DENIED){
                    permissionResults.put(permissions[i], grantResults[i]);
                    deniedCount++;
                }
            }

            if (deniedCount == 0){

            } else {
                for (Map.Entry<String, Integer> entry : permissionResults.entrySet()){
                    String permName = entry.getKey();
                    int permResult = entry.getValue();

                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permName)){
                        AlertDialog.Builder dialog= new AlertDialog.Builder(getActivity());
                        dialog.setTitle("Alert");
                        dialog.setMessage("This App need Camera Permission to work without and problems");
                        dialog.setPositiveButton("YES, Granted permission", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                checkAndRequestPermission();
                            }
                        });
                        dialog.setNegativeButton("NO, Cancel Taking Picture", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                System.exit(0);
                            }
                        });
                        dialog.setCancelable(false);
                        AlertDialog alert = dialog.create();
                        alert.show();

                    }
                }
            }
        }
    }

}
