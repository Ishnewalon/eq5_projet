package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByTargetedUser_Id(Long userId);
}
