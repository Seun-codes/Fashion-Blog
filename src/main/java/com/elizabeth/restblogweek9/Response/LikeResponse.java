package com.elizabeth.restblogweek9.Response;

import com.elizabeth.restblogweek9.model.Likes;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class LikeResponse {
    private String message;
    private LocalDateTime timeStamp;
    private Likes like;
    private int totalLikes;

}
