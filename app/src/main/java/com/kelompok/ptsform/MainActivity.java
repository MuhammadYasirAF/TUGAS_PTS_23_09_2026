package com.kelompok.ptsform;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // ---- komponen User Interaction (Project 2) ----
    EditText editTextNama, editTextNoHp;
    RadioButton radio1, radio2, radio3;
    CheckBox checkBoxWorkshop;

    // ---- komponen User Interface (Project 1, dikembangkan) ----
    TextView textViewCount, textViewSisaKuota;
    ProgressBar progressKuota;
    ListView listViewRiwayat;

    ArrayList<String> riwayat = new ArrayList<>();
    ArrayAdapter<String> adapter;

    int jumlahPendaftar = 0;
    int jmlPelajar = 0, jmlUmum = 0, jmlGuru = 0, jmlWorkshop = 0;
    static final int KUOTA_MAKS = 50;

    // semua data disimpan permanen lewat SharedPreferences,
    // dipakai bersama oleh MainActivity & StatistikActivity
    SharedPreferences prefs;
    static final String PREF_NAME = "pts_prefs";
    static final String KEY_COUNT = "jumlah_pendaftar";
    static final String KEY_PELAJAR = "jml_pelajar";
    static final String KEY_UMUM = "jml_umum";
    static final String KEY_GURU = "jml_guru";
    static final String KEY_WORKSHOP = "jml_workshop";
    static final String KEY_RIWAYAT = "riwayat_teks";
    static final String PEMISAH = "@@@";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // inisialisasi form (Project 2)
        editTextNama = findViewById(R.id.editTextNama);
        editTextNoHp = findViewById(R.id.editTextNoHp);
        radio1 = findViewById(R.id.radioButton1);
        radio2 = findViewById(R.id.radioButton2);
        radio3 = findViewById(R.id.radioButton3);
        checkBoxWorkshop = findViewById(R.id.checkBoxWorkshop);

        // inisialisasi tampilan counter & kuota (Project 1, dikembangkan)
        textViewCount = findViewById(R.id.textViewCount);
        textViewSisaKuota = findViewById(R.id.textViewSisaKuota);
        progressKuota = findViewById(R.id.progressKuota);
        listViewRiwayat = findViewById(R.id.listViewRiwayat);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, riwayat);
        listViewRiwayat.setAdapter(adapter);

        prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        muatDataTersimpan();
        perbaruiTampilan();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // supaya kalau baru balik dari halaman Statistik, data tetap sinkron
        muatDataTersimpan();
        perbaruiTampilan();
    }

    private void muatDataTersimpan() {
        jumlahPendaftar = prefs.getInt(KEY_COUNT, 0);
        jmlPelajar = prefs.getInt(KEY_PELAJAR, 0);
        jmlUmum = prefs.getInt(KEY_UMUM, 0);
        jmlGuru = prefs.getInt(KEY_GURU, 0);
        jmlWorkshop = prefs.getInt(KEY_WORKSHOP, 0);

        String riwayatTersimpan = prefs.getString(KEY_RIWAYAT, "");
        riwayat.clear();
        if (!riwayatTersimpan.isEmpty()) {
            riwayat.addAll(Arrays.asList(riwayatTersimpan.split(PEMISAH)));
        }
        adapter.notifyDataSetChanged();
    }

    private void simpanSemuaData() {
        prefs.edit()
            .putInt(KEY_COUNT, jumlahPendaftar)
            .putInt(KEY_PELAJAR, jmlPelajar)
            .putInt(KEY_UMUM, jmlUmum)
            .putInt(KEY_GURU, jmlGuru)
            .putInt(KEY_WORKSHOP, jmlWorkshop)
            .putString(KEY_RIWAYAT, String.join(PEMISAH, riwayat))
            .apply();
    }

    private void perbaruiTampilan() {
        textViewCount.setText(String.valueOf(jumlahPendaftar));
        progressKuota.setProgress(Math.min(jumlahPendaftar, KUOTA_MAKS));
        int sisa = Math.max(KUOTA_MAKS - jumlahPendaftar, 0);
        textViewSisaKuota.setText("Sisa Kuota: " + sisa + " dari " + KUOTA_MAKS);
    }

    // animasi kecil biar angka counter kerasa "hidup" tiap bertambah/berkurang
    private void animasikanCounter() {
        textViewCount.animate().scaleX(1.3f).scaleY(1.3f).setDuration(120)
            .withEndAction(() -> textViewCount.animate().scaleX(1f).scaleY(1f).setDuration(120).start())
            .start();
    }

    // dipanggil tombol DAFTAR SEKARANG
    public void submitData(View view) {
        if (jumlahPendaftar >= KUOTA_MAKS) {
            Toast.makeText(this, "Kuota pendaftaran sudah penuh!", Toast.LENGTH_SHORT).show();
            return;
        }

        String nama = editTextNama.getText().toString().trim();
        String nohp = editTextNoHp.getText().toString().trim();

        if (nama.isEmpty() || nohp.isEmpty()) {
            Toast.makeText(this, "Nama dan Nomor HP wajib diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        String kategori = "";
        if (radio1.isChecked()) {
            kategori = "Pelajar";
            jmlPelajar++;
        } else if (radio2.isChecked()) {
            kategori = "Umum";
            jmlUmum++;
        } else if (radio3.isChecked()) {
            kategori = "Guru";
            jmlGuru++;
        }

        boolean ikutWorkshop = checkBoxWorkshop.isChecked();
        if (ikutWorkshop) jmlWorkshop++;
        String workshopTeks = ikutWorkshop ? " | Workshop" : "";

        jumlahPendaftar++;
        String entry = jumlahPendaftar + ". " + nama + " (" + kategori + ")" + " - " + nohp + workshopTeks;
        riwayat.add(0, entry);
        adapter.notifyDataSetChanged();

        perbaruiTampilan();
        animasikanCounter();
        simpanSemuaData();

        Toast.makeText(this, "Terdaftar: " + nama + " (" + kategori + ")", Toast.LENGTH_SHORT).show();

        editTextNama.setText("");
        editTextNoHp.setText("");
        checkBoxWorkshop.setChecked(false);
        radio1.setChecked(true);
    }

    // dipanggil tombol "Hapus Terakhir" -> membatalkan pendaftaran paling baru (undo)
    public void hapusTerakhir(View view) {
        if (riwayat.isEmpty()) {
            Toast.makeText(this, "Belum ada data pendaftar", Toast.LENGTH_SHORT).show();
            return;
        }

        String entryTerakhir = riwayat.remove(0);
        adapter.notifyDataSetChanged();

        // kurangi counter kategori sesuai isi entry yang dihapus
        if (entryTerakhir.contains("(Pelajar)") && jmlPelajar > 0) jmlPelajar--;
        else if (entryTerakhir.contains("(Umum)") && jmlUmum > 0) jmlUmum--;
        else if (entryTerakhir.contains("(Guru)") && jmlGuru > 0) jmlGuru--;
        if (entryTerakhir.contains("| Workshop") && jmlWorkshop > 0) jmlWorkshop--;

        if (jumlahPendaftar > 0) jumlahPendaftar--;

        perbaruiTampilan();
        simpanSemuaData();
        Toast.makeText(this, "Data terakhir dihapus", Toast.LENGTH_SHORT).show();
    }

    // dipanggil tombol "Reset Semua" -> pakai dialog konfirmasi
    public void resetData(View view) {
        new AlertDialog.Builder(this)
            .setTitle("Reset Data")
            .setMessage("Yakin ingin menghapus semua data pendaftar dan mengembalikan counter ke 0?")
            .setPositiveButton("Ya, Reset", (dialog, which) -> {
                riwayat.clear();
                adapter.notifyDataSetChanged();
                jumlahPendaftar = 0;
                jmlPelajar = 0;
                jmlUmum = 0;
                jmlGuru = 0;
                jmlWorkshop = 0;
                perbaruiTampilan();
                simpanSemuaData();
                Toast.makeText(this, "Semua data direset", Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton("Batal", null)
            .show();
    }

    // dipanggil tombol "Lihat Statistik" -> pindah Activity membawa data olahan
    public void bukaStatistik(View view) {
        Intent intent = new Intent(MainActivity.this, StatistikActivity.class);
        startActivity(intent);
    }
}
