# PTS - Event Registration App (Gabungan Project 1 & Project 2, Versi 3 - Dikembangkan)

Project ini menggabungkan dan terus mengembangkan:
- **Project 1 (User Interface):** counter, progress bar kuota, header gradient, card, ListView riwayat, halaman Statistik dengan bar visual.
- **Project 2 (User Interaction):** form (EditText, RadioButton, CheckBox) dengan validasi, navigasi antar-Activity, dan fitur share.

Fitur hasil pengembangan tahap 3:
- **Kuota pendaftaran** (maks 50) ditampilkan lewat ProgressBar; form otomatis terkunci kalau kuota penuh.
- **Halaman Statistik baru** (`StatistikActivity`, dibuka lewat `Intent`): breakdown jumlah per kategori (Pelajar/Umum/Guru) dengan bar visual proporsional, plus jumlah peserta workshop.
- **Bagikan Ringkasan**: tombol share di halaman Statistik memakai `Intent.ACTION_SEND` untuk kirim ringkasan ke WhatsApp/aplikasi lain.
- **Riwayat pendaftar kini benar-benar permanen** (sebelumnya cuma angka totalnya yang tersimpan) — disimpan sebagai teks di SharedPreferences dan dimuat ulang tiap app dibuka.
- Animasi kecil (`.animate()`) saat angka counter bertambah/berkurang.

## Cara pakai di Android Studio (CARA PALING MUDAH)
1. Extract (unzip) folder `PTS_Project` dari file zip ini.
2. Buka Android Studio → pilih **Open** (bukan New Project).
3. Arahkan ke folder `PTS_Project` hasil extract, lalu klik **OK**.
4. Tunggu proses **Gradle Sync** selesai (ada progress bar di bawah). Kalau muncul dialog "Gradle wrapper not found, create one?" → klik **OK/Yes**.
5. Klik **Run ▶** untuk langsung mencoba di emulator/HP. Tidak perlu copy-paste file apa pun — semua source code, layout, dan konfigurasi sudah lengkap di dalam folder ini.
6. Untuk ambil file APK: **Build → Build Bundle(s)/APK(s) → Build APK(s)**.
   File APK akan muncul di `app/build/outputs/apk/debug/app-debug.apk`.
7. Push project ke GitHub (New Repository → upload folder ini / git push).

<!-- Cara manual (copy-paste per file) tidak lagi diperlukan karena project ini sudah lengkap dengan build.gradle, settings.gradle, dan gradle.properties, sehingga bisa langsung dibuka Android Studio seperti project biasa. -->

## Yang perlu dikumpulkan (sesuai instruksi guru)
- File project (folder ini / hasil export Android Studio)
- Link GitHub
- Screenshot aplikasi (.jpg)
- File .apk
- Jobsheet/Laporan (lihat file Laporan_PTS.docx)
