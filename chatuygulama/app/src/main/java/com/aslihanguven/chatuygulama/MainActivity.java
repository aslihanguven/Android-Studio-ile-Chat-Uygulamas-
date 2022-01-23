package com.aslihanguven.chatuygulama;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.internal.api.FirebaseNoSignedInUserException;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager myViewPager;
    private TabLayout myTabLayout;
    private SekmeErisim mySekmeErisim;

    //FİREBASE
    private FirebaseAuth mYetki;
    private DatabaseReference kullanicilarReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar=findViewById(R.id.ana_sayfa_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Chatting");

        myViewPager=findViewById(R.id.ana_sekmeler_pager);
        mySekmeErisim=new SekmeErisim(getSupportFragmentManager());

        myTabLayout=findViewById(R.id.ana_sekmeler);
        myTabLayout.setupWithViewPager(myViewPager);

        mYetki=FirebaseAuth.getInstance();
        kullanicilarReference= FirebaseDatabase.getInstance().getReference();
        

    }

    protected void onStart() {
        super.onStart();

        FirebaseUser mevcutKullanici=mYetki.getCurrentUser();

        if (mevcutKullanici ==null)
        {
            KullaniciyiLoginActivityeGonder();
        }

        else
        {
            kullaniciDurumuGuncelle("çevrimiçi");
            KullanicininVarliginiDogrula();
        }
    }

    private void KullanicininVarliginiDogrula() {
    }

    private void kullaniciDurumuGuncelle(String çevrimiçi) {
    }

    private void KullaniciyiLoginActivityeGonder() {
        Intent loginIntent = new Intent(MainActivity.this,LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();

    }

    @Override
    protected void onStop() {
        super.onStop();

        FirebaseUser mevcutKullanici=mYetki.getCurrentUser();


        if (mevcutKullanici != null)
        {
            kullaniciDurumuGuncelle("çevrimdışı");
        }
    }




}
