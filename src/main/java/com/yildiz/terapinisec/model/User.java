package com.yildiz.terapinisec.model;

import com.yildiz.terapinisec.util.PhoneNumberUtil;
import com.yildiz.terapinisec.util.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(unique = true, nullable = false)
    private String username ;

    @Column(nullable = false)
    private String firstName ;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String phoneNumber;

    public String setPhoneNumber(String phoneNumber) {
        if (!PhoneNumberUtil.isValidPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("Invalid phone number");
        }
        this.phoneNumber = phoneNumber;
        return this.phoneNumber;
    }

    @Column(nullable = false, unique = true)
    private String email ;

    @Column(nullable = false)
    private String password ;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.USER ;

    @Column(nullable = false)






}



























//o	id: Benzersiz kullanıcı kimliği.
//o	username: Kullanıcı adı.
//o firstName: ad
//o lastName:soyad
//o	password: Şifre (şifrelenmiş).
//o phoneNumber: telefon numarası.
//o	email: Kullanıcının e-posta adresi.
//o	role: Kullanıcının rolü (örneğin, USER, PSYCHOLOGIST, ADMIN). seçilebilir
//o	registrationDate: Kullanıcının kayıt tarihi.
//o	lastLogin: Son giriş tarihi.
//o	isPremium: Kullanıcının premium üyelik durumu.
//o	premiumStartDate: Premium üyeliğin başlangıç tarihi.
//o	premiumEndDate: Premium üyeliğin bitiş tarihi.
//o	specialization: Psikologlar için uzmanlık alanı. seçilebilir
//o	yearsOfExperience: Psikologlar için deneyim yılı.
//o	availableTimes: Psikologların uygun olduğu zaman dilimleri.
//•	İlişkiler:
//o	OneToMany – MoodLog: Kullanıcı birden fazla ruh hali kaydına sahip olabilir.
//o	OneToMany – Task: Kullanıcı birden fazla görev ve öneriye sahip olabilir.
//o	OneToMany – StoryView: Kullanıcının izlediği hikaye kayıtları.
//o	OneToMany – Report: Kullanıcının haftalık veya aylık raporları.
//o	OneToMany – Goal: Kullanıcının belirlediği kişisel gelişim hedefleri.
//o	OneToMany – Participant: Kullanıcının katıldığı grup terapileri.
//o	OneToMany – SurveyResponse: Kullanıcının verdiği anket yanıtları.
//o	OneToMany – SleepLog: Kullanıcının uyku günlüğü kayıtları.
//o	OneToMany – Appointment: Kullanıcının terapist ile randevuları.
//o	OneToMany – MeditationSession: Kullanıcının meditasyon ve mindfulness seansları.
//o	OneToMany – VoiceMessage: Kullanıcı tarafından gönderilen sesli mesajlar.
//o	OneToMany – LibraryDocument: Psikologların yüklediği belgeler.
//o	OneToMany – Notification: Kullanıcının aldığı bildirimler.
//o	OneToMany – FileStorage: Kullanıcının yüklediği dosyalar.
//o	OneToMany – Review: Kullanıcının yaptığı değerlendirmeler.