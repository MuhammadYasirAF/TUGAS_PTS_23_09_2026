package com.kelompok.ptsform;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class StatistikActivity extends AppCompatActivity {

    int total, pelajar, umum, guru, workshop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistik);

        // baca data dari SharedPreferences yang sama dengan MainActivity
        // (inilah bagian "penggabungan": statistik ini murni hasil olahan
        // dari data yang dimasukkan lewat form User Interaction)
        SharedPreferences prefs = getSharedPreferences(MainActivity.PREF_NAME, MODE_PRIVATE);
        total = prefs.getInt(MainActivity.KEY_COUNT, 0);
        pelajar = prefs.getInt(MainActivity.KEY_PELAJAR, 0);
        umum = prefs.getInt(MainActivity.KEY_UMUM, 0);
        guru = prefs.getInt(MainActivity.KEY_GURU, 0);
        workshop = prefs.getInt(MainActivity.KEY_WORKSHOP, 0);

        TextView textViewTotal = findViewById(R.id.textViewTotal);
        TextView labelPelajar = findViewById(R.id.labelPelajar);
        TextView labelUmum = findViewById(R.id.labelUmum);
        TextView labelGuru = findViewById(R.id.labelGuru);
        TextView textViewWorkshop = findViewById(R.id.textViewWorkshop);

        View barPelajar = findViewById(R.id.barPelajar);
        View barUmum = findViewById(R.id.barUmum);
        View barGuru = findViewById(R.id.barGuru);

        textViewTotal.setText("Total Peserta: " + total);
        labelPelajar.setText("Pelajar: " + pelajar);
        labelUmum.setText("Umum: " + umum);
        labelGuru.setText("Guru: " + guru);
        textViewWorkshop.setText("Ikut Workshop: " + workshop + " dari " + total + " peserta");

        // panjang bar dibuat proporsional terhadap total (weight),
        // minimal 0 supaya kategori kosong tetap terlihat sebagai garis tipis
        setBarWeight(barPelajar, pelajar, total);
        setBarWeight(barUmum, umum, total);
        setBarWeight(barGuru, guru, total);
    }

    private void setBarWeight(View bar, int nilai, int totalSemua) {
        android.widget.LinearLayout.LayoutParams params =
                (android.widget.LinearLayout.LayoutParams) bar.getLayoutParams();
        // kalau belum ada data sama sekali, semua bar kosong (weight 0)
        params.weight = totalSemua == 0 ? 0f : (float) nilai;
        bar.setLayoutParams(params);
    }

    // dipanggil tombol "Bagikan Ringkasan"
    public void bagikanRingkasan(View view) {
        String ringkasan = "📋 Ringkasan Pendaftaran Event\n"
                + "Total Peserta: " + total + "\n"
                + "- Pelajar: " + pelajar + "\n"
                + "- Umum: " + umum + "\n"
                + "- Guru: " + guru + "\n"
                + "Ikut Workshop: " + workshop + " peserta";

        Intent intentBagikan = new Intent(Intent.ACTION_SEND);
        intentBagikan.setType("text/plain");
        intentBagikan.putExtra(Intent.EXTRA_TEXT, ringkasan);
        startActivity(Intent.createChooser(intentBagikan, "Bagikan ringkasan lewat"));
    }

    // dipanggil tombol "Kembali"
    public void kembali(View view) {
        finish();
    }
}
