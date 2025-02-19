package com.yildiz.terapinisec.model;

import com.yildiz.terapinisec.util.PhoneNumberUtil;
import com.yildiz.terapinisec.util.Specialization;
import com.yildiz.terapinisec.util.UserRole;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private LocalDateTime birthday;

    @Column(nullable = false)
    private String phoneNumber;

    public void setPhoneNumber(String phoneNumber) {
        if (!PhoneNumberUtil.isValidPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("Invalid phone number");
        }
        this.phoneNumber = phoneNumber;
    }

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.USER;

    @Column(nullable = false, updatable = false)
    private LocalDateTime registrationDateTime;

    @Column(nullable = false)
    private LocalDateTime lastLoginDateTime;

    @Column
    private LocalDateTime premiumStartDateTime;

    @Column
    private LocalDateTime premiumEndDateTime;

    @Column(nullable = false)
    private boolean isPremium = false;

    public boolean isPremium() {
        return isPremium;
    }

    @PrePersist
    protected void onCreate() {
        this.registrationDateTime = LocalDateTime.now();
        this.lastLoginDateTime = this.registrationDateTime;
    }

    @ElementCollection(targetClass = Specialization.class)
    @CollectionTable(name = "user_specializations", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private List<Specialization> specializations;

    @Column
    private Integer yearsOfExperience;

    @ElementCollection
    @CollectionTable(name = "user_available_times", joinColumns = @JoinColumn(name = "user_id"))
    private List<LocalDateTime> availableTimes;

    @OneToMany(mappedBy = "moodOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MoodLog> moodLogs;

    @OneToMany(mappedBy = "assignees", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<StoryView> storyViews;

    @OneToMany(mappedBy = "reportOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reports;

    @OneToMany(mappedBy = "goalOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Goal> goals;

    @OneToMany(mappedBy = "joinedUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participant> participants;

    @OneToMany(mappedBy = "responsedBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SurveyResponse> surveyResponses;

    @OneToMany(mappedBy = "sleeper", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SleepLog> sleepLogs;

    @OneToMany(mappedBy = "appointmentClients", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "meditator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MeditationSession> meditationSessions;

    @OneToMany(mappedBy = "speaker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoiceMessage> voiceMessages;

    @OneToMany(mappedBy = "listener", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoiceMessage> listenerVoiceMessages;

    @OneToMany(mappedBy = "fileUploader", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LibraryDocument> libraryDocuments;

    @OneToMany(mappedBy = "documentUploader", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileStorage> documents;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Survey> surveys;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Story> stories;

    public UserRole getUserRole() {
        return userRole;
    }
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
//o	OneToMany – FileStorage: Kullanıcının yüklediği dosyalar.