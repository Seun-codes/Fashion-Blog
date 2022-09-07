package com.elizabeth.restblogweek9.Controller;

import com.elizabeth.restblogweek9.DTO.CommentDto;
import com.elizabeth.restblogweek9.DTO.LikedDto;
import com.elizabeth.restblogweek9.DTO.PostDto;
import com.elizabeth.restblogweek9.DTO.UserDto;
import com.elizabeth.restblogweek9.Response.CommentResponse;
import com.elizabeth.restblogweek9.Response.CreatePostResponse;
import com.elizabeth.restblogweek9.Response.LikeResponse;
import com.elizabeth.restblogweek9.Response.RegisterResponse;
import com.elizabeth.restblogweek9.ServiceImpl.UserServiceImpl;
import com.elizabeth.restblogweek9.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController @Slf4j
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
@Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
@PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody UserDto userDto){
        log.info("Successfully Registered {} " , userDto.getEmail());
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/register").toUriString());
        return ResponseEntity.created(uri).body( userService.registerUser(userDto));
    }
    @PostMapping("/create")
    public ResponseEntity<CreatePostResponse> create(@RequestBody PostDto postDto){
        log.info("Successfully Created A post With Title:  {} " , postDto.getTitle());
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/create").toUriString());
        return ResponseEntity.created(uri).body( userService.createPost(postDto));
    }
    @PostMapping("/comment/{user_id}/{post_id}")
    public ResponseEntity<CommentResponse> comment(@PathVariable(value = "user_id") Integer user_id,
                                                   @PathVariable(value = "post_id") Integer post_id,
                                                   @RequestBody CommentDto commentDto){
        log.info("Successfully commented :  {} " , commentDto.getComment());
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/comment").toUriString());
        return ResponseEntity.created(uri).body( userService.comments(user_id , post_id , commentDto));
    }
    @PostMapping("/like/{user_id}/{post_id}")
    public ResponseEntity<LikeResponse> like(@PathVariable(value = "user_id") Integer user_id,
                                             @PathVariable(value = "post_id") Integer post_id,
                                             @RequestBody LikedDto likeDto){
        log.info("Successfully liked :  {} " , likeDto.isLiked());
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/like").toUriString());
        return ResponseEntity.created(uri).body( userService.like(user_id , post_id , likeDto));
    }

}
