package com.elizabeth.restblogweek9.DTO;

import lombok.Data;

@Data
public class PostDto {
    private String title;
    private String description;
    private String image;
    private int user_id;
}
