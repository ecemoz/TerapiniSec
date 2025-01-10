package com.yildiz.terapinisec.model;

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
@Table(name = "stories")
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String storyUrl ;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime storyCreatedAt;

    @Column(nullable = false)
    private boolean isActive = true;

    @Column(nullable = false)
    private int viewCount = 0;

    @PrePersist
    protected void onCreate() {
        storyCreatedAt = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<StoryView> storyViews;

    public void incrementViewCount() {
        this.viewCount++;
    }
}