package com.example.urlshortenerwithspringboot.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "url")
@Getter
@Setter
@NoArgsConstructor
public class URL {

    @Id
    @SequenceGenerator(name = "url_seq",
            sequenceName = "url_sequence",
            initialValue = 1000000000, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "url_seq")
    @Column
    private Long id;
    @Column(name = "original_url")
    private String originalURL;
    @Column(name = "short_url")
    private String shortURL;
    @Column(name = "creation_date")
    private String creationDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
}
