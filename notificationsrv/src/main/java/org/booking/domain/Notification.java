package org.booking.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


/**
 * A Notification.
 */
@Entity
@Table(name="notification")
public class Notification implements Serializable {

    @Id
//    @SequenceGenerator(name="notification_id_seq", sequenceName="notification_id_seq", initialValue=1, allocationSize=1)
    @GeneratedValue(strategy = GenerationType.AUTO/*, generator="notification_id_seq"*/)
    @Column(name="id")
    private Long id;

    @NotNull
    @Column(name="bookingid")
    private Long bookingId;

    @NotNull
    @Column(name="notifieduserid")
    private Long notifiedUserId;

    @NotNull
    @Column(name="creationdate")
    private Date creationDate;

    @Column(name="sentdate")
    private Date sentDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getNotifiedUserId() {
        return notifiedUserId;
    }

    public void setNotifiedUserId(Long notifiedUserId) {
        this.notifiedUserId = notifiedUserId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Notification notification = (Notification) o;

        if ( ! Objects.equals(id, notification.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return (new StringBuffer())
        		.append("Notification{")
        			.append("id=").append(id)
        			.append(", bookingId='").append(bookingId).append("'")
        			.append(", notifiedUserId='").append(notifiedUserId).append("'")
        			.append(", creationDate='").append(creationDate).append("'")
        			.append(", sentDate='").append(sentDate).append("'")
        		.append('}').toString();
    }
}
