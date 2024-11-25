package com.yildiz.terapinisec.model;

import com.yildiz.terapinisec.util.GoalType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "goals")

public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "goal_types",joinColumns = @JoinColumn(name = "goal_logs"))
    @Enumerated(EnumType.STRING)
    private List< GoalType > goalType;

    @Column(nullable = false)
    private String goalDescription;

    @Column(nullable = false, updatable = false)
    private LocalDateTime goalStartDate;

    @Column(nullable = false)
    private LocalDateTime goalEndDate;

    @Column(nullable = false)
    private boolean goalComplete = false;

    @ManyToOne
    @JoinColumn(name = "user_id" ,nullable = false)
    private User goalOwner;

    @PrePersist
    protected void onCreate() {
        if (this.goalStartDate == null) {
            this.goalStartDate = LocalDateTime.now();
        }
        if (this.goalEndDate == null) {
            this.goalEndDate = this.goalEndDate.plusDays(30);
        }
    }
}