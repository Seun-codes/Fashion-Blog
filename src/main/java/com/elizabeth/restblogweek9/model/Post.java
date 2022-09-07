package com.elizabeth.restblogweek9.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
 @Getter @Setter @Entity @Table(name ="post")
@AllArgsConstructor @NoArgsConstructor
public class Post {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int post_id;
    private String title;
    private String description;
    private String slug;
    private String image;
    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
    @JsonManagedReference
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
    @JsonManagedReference
    @OneToMany(mappedBy = "post")
    private List<Likes> likes;


}
