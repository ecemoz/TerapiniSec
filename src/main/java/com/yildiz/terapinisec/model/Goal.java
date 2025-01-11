package com.yildiz.terapinisec.model;

import com.yildiz.terapinisec.util.GoalType;
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
@Table(name = "goals")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private  GoalType  goalType;

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