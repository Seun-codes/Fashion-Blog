package com.elizabeth.restblogweek9.Controller;

import com.elizabeth.restblogweek9.DTO.*;
import com.elizabeth.restblogweek9.Response.*;
import com.elizabeth.restblogweek9.ServiceImpl.UserServiceImpl;
import com.elizabeth.restblogweek9.model.Post;
import com.elizabeth.restblogweek9.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.HttpStatus.CREATED;

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
        return new ResponseEntity<>( userService.registerUser(userDto), CREATED);
    }
    @PostMapping("/create")
    public ResponseEntity<CreatePostResponse> create(@RequestBody PostDto postDto){
        log.info("Successfully Created A post With Title:  {} " , postDto.getTitle());

        return new ResponseEntity<>(userService.createPost(postDto), CREATED);
    }
@GetMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDto loginDto){
    //log.info("Successful Login {} ", loginDto.getEmail());
    return  new ResponseEntity<>(userService.userLogin(loginDto), HttpStatus.OK);
    }


    @PostMapping("/comment/{user_id}/{post_id}")
    public ResponseEntity<CommentResponse> comment(@PathVariable(value = "user_id") Integer user_id,
                                                   @PathVariable(value = "post_id") Integer post_id,
                                                   @RequestBody CommentDto commentDto){
        log.info("Successfully commented :  {} " , commentDto.getComment());
       // URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/comment").toUriString());
        return new ResponseEntity<>( userService.comments(user_id , post_id , commentDto) , CREATED);
    }
    @PostMapping("/like/{user_id}/{post_id}")
    public ResponseEntity<LikeResponse> like(@PathVariable(value = "user_id") Integer user_id,
                                             @PathVariable(value = "post_id") Integer post_id,
                                             @RequestBody LikedDto likeDto){
        log.info("Successfully liked :  {} " , likeDto.isLiked());
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/like").toUriString());
        return ResponseEntity.created(uri).body( userService.like(user_id , post_id , likeDto));
    }
@GetMapping("/searchPost/{keyword}")
    public ResponseEntity<SearchPostResponse> searchPost(@PathVariable(  value = "keyword") String keyword){
        return new ResponseEntity<>(userService.postSearch(keyword) , HttpStatus.OK);
    }
    @GetMapping(value = "/post/{id}")
    public ResponseEntity<Post> searchComment(@PathVariable(  value = "id") Integer id){
        return ResponseEntity.ok().body(userService.findPostById(id));
    }


}
