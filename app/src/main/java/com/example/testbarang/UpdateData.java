package com.example.testbarang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateData extends AppCompatActivity {
    private EditText kodeUpdate, namaUpdate;
    private Button updateB;
    private DatabaseReference databaseReference;
    private String ckkode, ckNama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        kodeUpdate = findViewById(R.id.editNoUpdate);
        namaUpdate = findViewById(R.id.editNamaUpdate);

        updateB = findViewById(R.id.btnOkUpdate);

        getDataforUpdate();

        updateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Barang barangUpdate = new Barang();
                barangUpdate.setKode(kodeUpdate.getText().toString());
                barangUpdate.setNama(namaUpdate.getText().toString());

                updateData(barangUpdate);

                finish();
            }
        });

    }

    private void getDataforUpdate() {
        final String getkode = getIntent().getExtras().getString("kode");
        final String getnama = getIntent().getExtras().getString("nama");

        kodeUpdate.setText(getkode);
        namaUpdate.setText(getnama);

    }

    private void updateData(Barang barang) {
        databaseReference = FirebaseDatabase.getInstance().getReference();

        final String getKey = getIntent().getExtras().getString("key");

        databaseReference.child("Barang").child(getKey).setValue(barang).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                kodeUpdate.setText("");
                namaUpdate.setText("");
                Toast.makeText(UpdateData.this, "Data Berhasil di Update", Toast.LENGTH_LONG).show();
            }
        });
    }
}