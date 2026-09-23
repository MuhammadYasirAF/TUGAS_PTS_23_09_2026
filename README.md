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
