package id.dev.birifqa.edcgold;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText etNamaDepan, etNamaBelakang, etBOD, etPhone, etEmail, etPassword, etReferral;
    AppCompatButton btnDaftar;
    RadioGroup groupJK;
    RadioButton radioJK;
    String namaDepan, namaBelakang, jk, bod, phone, email, password, referral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNamaDepan = findViewById(R.id.et_nama_depan);
        etNamaBelakang = findViewById(R.id.et_nama_belakang);
        etBOD = findViewById(R.id.et_bod);
        etPhone = findViewById(R.id.et_phone);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etReferral = findViewById(R.id.et_referral);
        groupJK = findViewById(R.id.radioJK);
        btnDaftar = findViewById(R.id.btn_daftar);

        namaDepan = etNamaDepan.getText().toString();
        namaBelakang = etNamaBelakang.getText().toString();
        int selectedJK = groupJK.getCheckedRadioButtonId();
        radioJK = findViewById(selectedJK);
        jk = radioJK.getText().toString();
        bod = etBOD.getText().toString();
        phone = etPhone.getText().toString();
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        referral = etReferral.getText().toString();

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!namaDepan.isEmpty() && !namaBelakang.isEmpty() && !jk.isEmpty() && !bod.isEmpty() && !phone.isEmpty() && !email.isEmpty() && !password.isEmpty() && !referral.isEmpty()){

                }
            }
        });
    }
}
