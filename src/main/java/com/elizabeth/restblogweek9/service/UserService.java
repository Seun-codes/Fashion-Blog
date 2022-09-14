package com.elizabeth.restblogweek9.service;

import com.elizabeth.restblogweek9.DTO.*;
import com.elizabeth.restblogweek9.Response.*;
import com.elizabeth.restblogweek9.model.Post;

public interface UserService {
    RegisterResponse registerUser(UserDto userDto);
    LoginResponse userLogin(LoginDto loginDto);
    CreatePostResponse createPost(PostDto postDto);
    CommentResponse comments(int user_id, int post_id, CommentDto commentDto);
    LikeResponse like(int user_id, int post_id, LikedDto likedDto);
    SearchPostResponse postSearch(String Keyword);

    Post findPostById(int id);
}
