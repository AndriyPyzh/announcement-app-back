package com.announcement.entity;

import lombok.Data;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@Entity
@Indexed
@Table(name = "announcements")
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Field
    private String title;

    @Field
    private String description;

    private LocalDate dateAdded;

    @PrePersist
    public void prePersist() {
        if (dateAdded == null) {
            dateAdded = LocalDate.now();
        }
    }

}
