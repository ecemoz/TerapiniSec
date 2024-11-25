package com.yildiz.terapinisec.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name ="storyviews")
public class StoryView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime viewedAt;

    @ManyToOne
    @JoinColumn(name ="story_id",nullable = false)
    private Story story;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
}