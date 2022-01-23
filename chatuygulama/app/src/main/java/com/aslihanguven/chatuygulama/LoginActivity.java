package com.aslihanguven.chatuygulama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {

    private Button girisButonu, telefonlaGirisButonu;
    private EditText KullaniciMail, KullaniciSifre;
    private TextView YeniHesapAlma,SifreUnutmaBaglanti;



    private FirebaseAuth mevcutKullanici;
    private DatabaseReference kullaniciYolu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //KONTROL TANIMLAMALARI

        girisButonu=findViewById(R.id.giris_butonu);
        telefonlaGirisButonu=findViewById(R.id.telefonla_giris_butonu);

        KullaniciMail=findViewById(R.id.giris_email);
        KullaniciSifre=findViewById(R.id.giris_sifre);

        YeniHesapAlma=findViewById(R.id.yeni_hesap_alma);
        SifreUnutmaBaglanti=findViewById(R.id.sifre_unutma_baglantisi);

        ProgressDialog girisDialog = new ProgressDialog(this);

        //Firebase
        FirebaseAuth mYetki = FirebaseAuth.getInstance();
        kullaniciYolu= FirebaseDatabase.getInstance().getReference().child("Kullanicilar");



        YeniHesapAlma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent kayitAktivityIntent=new Intent(LoginActivity.this,KayitActivity.class);
                startActivity(kayitAktivityIntent);
            }
            //Progress

            });
        girisButonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                KullaniciyaGirisİzniVer();
            }
        });

    }

    private void KullaniciyaGirisİzniVer()
    {
        String email = KullaniciMail.getText().toString();
        String sifre = KullaniciSifre.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Email boş olamaz!", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(sifre))
        {
            Toast.makeText(this, "Şifre boş olamaz!", Toast.LENGTH_SHORT).show();
        }

        else
        {
            //Progress
            AlertDialog girisDialog = null;
            girisDialog.setTitle("Giriş yapılıyor");
            girisDialog.setMessage("Lütfen bekleyin...");
            girisDialog.setCanceledOnTouchOutside(true);
            girisDialog.show();


            //Giriş
            FirebaseAuth mYetki = null;
            mYetki.signInWithEmailAndPassword(email,sifre)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        private Calendar FirebaseInstanceId;

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful())
                            {
                                String aktifKullaniciId=mYetki.getCurrentUser().getUid();

                                Object cihatToken = null;
                                kullaniciYolu.child(aktifKullaniciId).child("cihaz_token").setValue(cihatToken)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful())
                                                {
                                                    Intent anaSayfa = new Intent(LoginActivity.this,MainActivity.class);
                                                    anaSayfa.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(anaSayfa);
                                                    finish();
                                                    Toast.makeText(LoginActivity.this, "Giriş Başarılı", Toast.LENGTH_SHORT).show();
                                                    girisDialog.dismiss();

                                                }

                                            }
                                        });
                            }

                            else
                            {
                                String mesaj = task.getException().toString();
                                Toast.makeText(LoginActivity.this, "Hata: "+mesaj+" bilgileri kontrol edin!", Toast.LENGTH_SHORT).show();
                                girisDialog.dismiss();
                            }


                        }
                    });

        }
    }

    protected void onStart(){
        super.onStart();

        if (mevcutKullanici != null)
        {
            KullaniciyiAnaAktiviteyeGonder();
        }
    }

    private void KullaniciyiAnaAktiviteyeGonder() {
        Intent AnaAktiviteIntent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(AnaAktiviteIntent);
    }
}