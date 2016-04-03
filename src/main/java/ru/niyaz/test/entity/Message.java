package ru.niyaz.test.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import javax.persistence.*;

/**
 * Created by user on 03.04.16.
 */

@Entity
@Table(name = "messages")
public class Message {

    public Message() {

    }

    public Message(String message) {
        this.message = message;
        this.date = new Date();
    }

    @Id
    @SequenceGenerator(name = "messages_seq", sequenceName = "messages_id_seq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "messages_seq")
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "place_id")
    private String placeID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }
}
