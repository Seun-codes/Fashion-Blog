package com.elizabeth.restblogweek9.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
@Entity @Table(name = "Likes")
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int likes_id;
    private boolean isLiked;

    @CreationTimestamp
    private LocalDateTime created;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name ="user_id", referencedColumnName = "user_id")
    private User user;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post post;


    public Likes(int i, boolean b, LocalDateTime localDateTime, LocalDateTime localDateTime1, User user, Post post) {
    }
}
