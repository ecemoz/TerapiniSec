package com.yildiz.terapinisec.model;

import com.yildiz.terapinisec.util.UserMoods;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name= "moodlogs")

public class MoodLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @ElementCollection
    @CollectionTable(name= "user_moods",joinColumns = @JoinColumn(name ="mood_log"))
    @Enumerated(EnumType.STRING)
    private List<UserMoods> userMoods;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime logDateTime;

    @PrePersist
    protected void onCreate() {
        logDateTime = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User moodOwner;
}
