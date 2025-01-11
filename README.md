# TerapiniSeç

**TerapiniSeç**, bireylerin ruhsal sağlıklarını yönetmelerine yardımcı olmayı amaçlayan, kullanıcı dostu bir mental sağlık platformudur. Ruh hali takibi, terapist desteği, grup terapileri ve daha birçok özellik ile kullanıcıların bireysel gelişimlerine destek olmayı hedefler.

---

## 🚀 Proje Özellikleri

### Kullanıcı Yönetimi
- **Kayıt ve Giriş:** Kullanıcılar kolayca kayıt olabilir ve giriş yapabilir.
- **Profil Yönetimi:** Kullanıcılar bilgilerini güncelleyebilir, özel alanlarını yönetebilir.
- **Premium Üyelik:** Kullanıcılar Premium statüsüne yükselerek özel özelliklere erişim sağlayabilir.

### Terapist Yönetimi
- **Uzmanlık Alanları:** Terapistler uzmanlıklarını belirtebilir (ör. depresyon, anksiyete).
- **Çalışma Saatleri:** Terapistler uygun oldukları zaman aralıklarını kullanıcılarla paylaşabilir.

### Grup Terapileri
- Grup seansları oluşturulabilir ve yönetilebilir.
- Kullanıcılar belirli bir temaya odaklanmış seanslara katılabilir.

### Ruh Hali Takibi
- Kullanıcılar günlük ruh hali kayıtları oluşturabilir.
- Geçmiş ruh halleri analiz edilebilir ve grafiksel olarak görüntülenebilir.

### Bildirimler ve Hatırlatmalar
- Görev hatırlatıcıları ve yaklaşan etkinliklerle ilgili otomatik bildirimler.
- Kullanıcıların günlük hedeflerini tamamlamalarına yardımcı olur.

### Güvenlik ve Veri Koruma
- **Şifreleme:** Kullanıcı şifreleri güçlü bir şekilde korunur (BCrypt).
- **JWT:** Güvenli oturum yönetimi.

---

## 📚 Teknolojiler

| **Bileşen**      | **Kullanılan Teknolojiler**          |
|-------------------|--------------------------------------|
| **Backend**       | Spring Boot, Spring Security, JPA   |
| **Frontend**      | React Native                        |
| **Veritabanı**    | PostgreSQL                          |
| **Mesajlaşma**    | Apache Kafka                        |
| **Güvenlik**      | JWT, BCrypt                         |
| **Test**          | JUnit, Mockito                      |
| **Diğer**         | Docker, Lombok                      |

---

## 🌟 Başlarken

### Gereksinimler
- **Java 17+**
- **Maven** (Yapı aracı)
- **PostgreSQL** (Veritabanı)
- **Docker ve Docker Compose** (Opsiyonel, Kafka ve Zookeeper için)

---

## ⚙️ Kurulum

### 1. Kaynak Kodunu Klonlayın
```bash
git clone https://github.com/ecemoz/terapinisec.git
cd terapinisec
```

### 2. Bağımlılıkları Yükleyin
```bash
mvn clean install
```

### 3. Veritabanı Yapılandırması
`src/main/resources/application.properties` dosyasını açarak aşağıdaki gibi düzenleyin:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/terapinisec
spring.datasource.username=postgres
spring.datasource.password=your_password
```

### 4. Kafka ve Zookeeper Kurulumu (Opsiyonel)
Kafka'yı Docker ile başlatmak için:
```bash
docker-compose up -d
```

### 5. Uygulamayı Çalıştırın
```bash
mvn spring-boot:run
```

---

## 📌 Örnek API Kullanımları

### Kullanıcı Kayıt
**Endpoint:** `POST /api/users`  
**Body:**
```json
{
  "userName": "ecemnur",
  "firstName": "Ecem",
  "lastName": "Özen",
  "email": "ecemnurozen@gmail.com",
  "birthday": "2000-03-03",
  "phoneNumber": "+905551234567",
  "password": "securePassword123",
  "userRole": "USER"
}
```

---

### Kullanıcı Güncelleme
**Endpoint:** `PUT /api/users/{id}`  
**Body:**
```json
{
  "userName": "updatedUsername",
  "firstName": "UpdatedName",
  "lastName": "UpdatedLastName",
  "email": "updatedemail@gmail.com",
  "phoneNumber": "+905551234567",
  "birthday": "1990-01-01",
  "specialization": ["DEPRESSION", "ANXIETY"],
  "yearsOfExperience": 5,
  "availableTimes": ["2025-01-15T10:00:00", "2025-01-16T15:00:00"]
}
```

---

### Kullanıcı Giriş
**Endpoint:** `POST /api/authenticate`  
**Body:**
```json
{
  "userNameOrEmail": "ecemnurozen",
  "password": "securePassword123"
}
```

---

## 🏗️ Proje Yapısı

```plaintext
src
├── main
│   ├── java/com/yildiz/terapinisec
│   │   ├── controller     # API Endpoint'leri
│   │   ├── service        # İş mantığı
│   │   ├── repository     # Veritabanı erişimi
│   │   ├── dto            # Veri transfer nesneleri
│   │   ├── model          # Veritabanı modelleri
│   │   └── util           # Yardımcı araçlar
│   └── resources
│       ├── application.properties  # Uygulama ayarları
│       └── data.sql                # Başlangıç verileri
└── test
    └── java/com/yildiz/terapinisec  # Testler
```

---

## 🔒 Güvenlik

- Kullanıcı şifreleri **BCrypt** kullanılarak güçlü bir şekilde korunur.
- Oturumlar ve yetkilendirme işlemleri için **JWT** kullanılır.
- Tüm API uç noktaları için erişim kontrolü sağlanır.

---

## 📄 Lisans / License
 * Bu proje tüm hakları saklıdır.
 * This project is All Rights Reserved.
---

## 👥 İletişim

Sorularınız için bizimle iletişime geçebilirsiniz:
- **E-posta:** [ecemnurozen03@gmail.com](mailto:ecemnurozen03@gmail.com)
- **GitHub:** [GitHub Profili](https://github.com/ecemoz)

--- 

# **TerapiniSeç** platformu ile insanların hayatına dokunmak için bir adım atın. ❤️ 
