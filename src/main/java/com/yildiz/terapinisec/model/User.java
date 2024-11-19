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
    private LocalDateTime birthday ;

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

    @Column(nullable = false)
    private String yearsOfExperience;

    @ElementCollection
    @CollectionTable(name = "user_available_times", joinColumns = @JoinColumn(name = "user_id"))
    private List <LocalDateTime> availableTimes;


    @OneToMany( mappedBy = "moodOwner")
    private List<MoodLog> moodLogs;

    @OneToMany( mappedBy = "assignees")
    private List<Task> tasks;

    @ManyToMany ( mappedBy = "viewedBy")
    private List<StoryView> storyViews;

    @OneToMany( mappedBy = "reportOwner")
    private List<Report> reports;

    @OneToMany( mappedBy = "goalOwner")
    private List<Goal> goals;

    @ManyToMany( mappedBy = "joinedUser")
    private List<Participant> participants;

    @OneToMany( mappedBy = "responsedBy")
    private List<SurveyResponse> surveyResponses;

    @OneToMany( mappedBy = "sleeper")
    private List<SleepLog> sleepLogs;

    @OneToMany( mappedBy = "appointmentClients")
    private List<Appointment> appointments;

    @OneToMany( mappedBy = "meditator")
    private List<MedidationSession> medidationSessions;

    @OneToMany( mappedBy = "speaker")
    private List<VoiceMessage> voiceMessages;

    @OneToMany( mappedBy = "fileUploader")
    private List<LibraryDocument> libraryDocuments;

    @OneToMany( mappedBy = "notiRecievier")
    private List<Notification> notifications;

    @OneToMany( mappedBy = "documentUploader")
    private List<FileStorage> documents;
}



//o	id: Benzersiz kullanıcı kimliği.
//o	username: Kullanıcı adı.
//o firstName: ad
//o lastName:soyad
//o birthday: Kullanıcı doğum günü.
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
//o	ManyToMany – StoryView: Kullanıcının izlediği hikaye kayıtları.
//o	OneToMany – Report: Kullanıcının haftalık veya aylık raporları.
//o	OneToMany – Goal: Kullanıcının belirlediği kişisel gelişim hedefleri.
//o	ManyToMany – Participant: Kullanıcının katıldığı grup terapileri.
//o	OneToMany – SurveyResponse: Kullanıcının verdiği anket yanıtları.
//o	OneToMany – SleepLog: Kullanıcının uyku günlüğü kayıtları.
//o	OneToMany – Appointment: Kullanıcının terapist ile randevuları.
//o	OneToMany – MeditationSession: Kullanıcının meditasyon ve mindfulness seansları.
//o	OneToMany – VoiceMessage: Kullanıcı tarafından gönderilen sesli mesajlar.
//o	OneToMany – LibraryDocument: Psikologların yüklediği belgeler.
//o	OneToMany – Notification: Kullanıcının aldığı bildirimler.
//o	OneToMany – FileStorage: Kullanıcının yüklediği dosyalar.