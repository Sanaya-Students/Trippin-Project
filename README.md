# Tentang-Trippin - tes 
Trippin adalah sebuah produk aplikasi sistem informasi pariwisata yang bertujuan untuk memudahkan banyak orang dalam mengakses semua informasi destinasi wisata alam yang ada di beberapa kota di Indonesia, semua informasi kami kumpulkan di satu tempat sehingga orang yang ingin berlibur tidak perlu lagi membuang waktu mereka untuk membuka banyak aplikasi maupun website demi sebatas mencari beberapa keping informasi tempat yang akan dituju, sekaligus aplikasi ini juga ditujukkan untuk memenuhi tugas besar matakuliah Bahasa Pemrograman II dan target pencapaian jangka panjang kami adalah aplikasi ini dapat diunduh melalui Google Play Store.

## Tim
- Muchamad Sanaya Almatin (22.12.2604)
- Alya Wahyuning Syahbani (22.12.2595)
- Alfian Ramadhan (22.12.2570)

## Fitur Pada Aplikasi
- Login
- SignUp
- Logout
- Search
- History Search & Delete History Search
- Change Password
- Pencatatan Log Pengguna
- 1 Kali Proses Login

## Fitur Kunci
- 1 Kali Proses Login: Kami menggunakan library java UUID untuk membuat token unik setiap pengguna yang telah melakukan proses login, sehingga setiap kali aplikasi digunakan atau dijalankan, pengguna tidak perlu melakukan proses login kembali.
- Search History: Pada fitur pencarian ini kami menerapkan beberapa metode antara lain pencatatan hasil telusur pengguna, delete histori berdasarkan id histori dan delete keseluruhan, serta matching kata yang ada pada histori dengan kata baru yang diinputkan pengguna.
- Change Password: Fitur ini memungkinkan pengguna untuk mengubah password akun yang dimiliki untuk tetap menjaga keamanan akun pribadi.

## Requirment Device Emulator
Project ini dibuat dengan menggunakan compileSdk 34, minSdk 26 dan targetSdk 33, agar semua fitur dan animasi dapat berjalan sesuai dengan program yang telah dibuat maka gunakan device dengan versi android minimum 8.0 (Code Name: Oreo), namun lebih baik lagi jika menggunakan versi android 13 agar sesuai dengan targetSdk yang ditentukan yaitu 33.

## Keterangan Tambahan
Pada repository ini telah dicantumkan juga file database yang digunakan, nama filenya adalah "trippin" kemudian jika repository ini di clone atau di download dengan format .zip ubahlah nama parent foldernya dengan nama "FP" agar meminimalisir kemungkinan error pada program atau aplikasi tidak dapat dijalankan. Untuk username dan password yang digunakan saat proses login dapat dilihat pada file database "trippin" dengan cara membukanya melalui aplikasi SQLiteStudio.

## Demo Aplikasi
![Demo_gif](https://github.com/SanayaAlmatin/Trippin-Project/assets/131599314/6ca384b1-79ac-4e19-9faf-771aba2bd96a)
