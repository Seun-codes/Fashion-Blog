package com.elizabeth.restblogweek9.Response;

import com.elizabeth.restblogweek9.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class SearchPostResponse {
    private String message;
    private LocalDateTime TimeStamp;
    private List<Post> post;
}
