package com.elizabeth.restblogweek9.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class PostDto {
    private String title;
    private String description;
    private String image;
    private int user_id;
}
