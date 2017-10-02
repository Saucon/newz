# newz app
Android dengan MVP dan MVP - Clean

Aplikasi ini menggunakan API https://newsapi.org/.

Belajar membuat aplikasi android dengan arsitektur MVP (Model View Presenter). Branch terbagi menjadi MVP, MVP-Clean (OTW), MVVM (Coming Soon).

### MVP
Pada branch ini berisi proyek yang menerapkan MVP secara sederhana. Presenter digunakan untuk mengatur jalannya data ke atau dari tampilan pada Android (Activity, Fragment, Adapter). Lalu terdapat Repository untuk mengatur sumber data apakah dari cloud/api/remote atau dari sqlite/local.

### MVP Clean
Merupakan pengembangan dari MVP Basic. Dibagi dalam 3 layer yaitu Data Layer, Domain Layer, dan Presentation Layer. Disini proses bisnis utama aplikasi dibagi kedalam use case. 
