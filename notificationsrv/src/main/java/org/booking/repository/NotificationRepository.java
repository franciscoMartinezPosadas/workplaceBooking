package org.booking.repository;

import org.booking.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Notification entity.
 */
public interface NotificationRepository extends JpaRepository<Notification,Long> {

}
