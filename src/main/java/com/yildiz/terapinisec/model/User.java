package com.yildiz.terapinisec.model;

import com.yildiz.terapinisec.util.PhoneNumberUtil;
import com.yildiz.terapinisec.util.Specialization;
import com.yildiz.terapinisec.util.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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

    @Column(nullable = false, updatable = false)
    private LocalDateTime registrationDateTime;

    @PrePersist
    protected void onCreate() {
        this.registrationDateTime = LocalDateTime.now();
    }

    @Column(nullable = false)
    private LocalDateTime lastLoginDateTime ;

    @PreUpdate
    protected void onLogin() {
        this.lastLoginDateTime = LocalDateTime.now();
    }

    @Column(nullable = false)
    private boolean isPremium = false;

    @Column(nullable = false)
    private LocalDateTime premiumStartDateTime;

    @PrePersist
    protected void onPremiumStart() {
        this.premiumStartDateTime = LocalDateTime.now();
    }

    @Column(nullable = false)
    private LocalDateTime premiumEndDateTime;

    @PreUpdate
    protected void onPremiumEnd() {
        this.premiumEndDateTime = LocalDateTime.now();
    }

    @ElementCollection(targetClass = Specialization.class)
    @CollectionTable(name= "user_specializations", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private List<Specialization> specializations;


    








}



























//o	id: Benzersiz kullanıcı kimliği.
//o	username: Kullanıcı adı.
//o firstName: ad
//o lastName:soyad
//o	password: Şifre (şifrelenmiş).
//o phoneNumber: telefon numarası.
//o	email: Kullanıcının e-posta adresi.
//o	role: Kullanıcının rolü (örneğin, USER, PSYCHOLOGIST, ADMIN). seçilebilir
//o	registrationDateTime: Kullanıcının kayıt tarihi.
//o	lastLoginDateTime: Son giriş tarihi.
//o	isPremium: Kullanıcının premium üyelik durumu.
//o	premiumStartDateTime: Premium üyeliğin başlangıç tarihi.
//o	premiumEndDateTime: Premium üyeliğin bitiş tarihi.
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