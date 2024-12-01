package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.Notification;
import com.yildiz.terapinisec.util.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification>findByIsReadFalse();
    List<Notification>findByUserId(Long userId);
    List<Notification>findByUserIdAndIsReadFalseOrderByCreatedAtAsc(Long userId);
    List<Notification>findByNotificationType(NotificationType notificationType);
    List<Notification>findByUserIdAndNotificationTypeAndIsReadFalseOrderByCreatedAtAsc(Long userId, NotificationType notificationType);
    List<Notification>findAllByOrderByCreatedAtAsc();
}
