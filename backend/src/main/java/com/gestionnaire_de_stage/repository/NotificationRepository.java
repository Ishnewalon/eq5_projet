package com.gestionnaire_de_stage.repository;

import com.gestionnaire_de_stage.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
