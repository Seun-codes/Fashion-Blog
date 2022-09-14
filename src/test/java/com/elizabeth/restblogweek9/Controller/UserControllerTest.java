package com.elizabeth.restblogweek9.Controller;

import com.elizabeth.restblogweek9.DTO.LikedDto;
import com.elizabeth.restblogweek9.DTO.LoginDto;
import com.elizabeth.restblogweek9.DTO.PostDto;
import com.elizabeth.restblogweek9.DTO.UserDto;
import com.elizabeth.restblogweek9.Response.CreatePostResponse;
import com.elizabeth.restblogweek9.Response.LikeResponse;
import com.elizabeth.restblogweek9.Response.LoginResponse;
import com.elizabeth.restblogweek9.Response.RegisterResponse;
import com.elizabeth.restblogweek9.ServiceImpl.UserServiceImpl;
import com.elizabeth.restblogweek9.model.Comment;
import com.elizabeth.restblogweek9.model.Likes;
import com.elizabeth.restblogweek9.model.Post;
import com.elizabeth.restblogweek9.model.User;
import com.elizabeth.restblogweek9.repositories.CommentRepository;
import com.elizabeth.restblogweek9.repositories.LikeRepository;
import com.elizabeth.restblogweek9.repositories.PostRepository;
import com.elizabeth.restblogweek9.repositories.UserRepository;
import org.apache.el.stream.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Calendar.AUGUST;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static org.apache.el.stream.Optional.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserControllerTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private LikeRepository likeRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PostRepository repository;

@InjectMocks
    private UserServiceImpl userService;

    private LocalDateTime localDateTime;
    private User user;
    private Comment comment;
    private Likes like;
    private Post post;
    List<Likes> likesList = new ArrayList<>();
    List<Post> postList = new ArrayList<>();
    List<Comment> commentList = new ArrayList<>();
    List<User> userList = new ArrayList<>();


    @BeforeEach
    void setUp() {
        localDateTime = LocalDateTime.of(2022, AUGUST,7,4,38);
        user = new User(1,"Oluwaseun","oluwaseun@gmail.com","customer",localDateTime,localDateTime,postList,commentList,likesList);
        post = new Post(1,"End of Summer Blues", "Celebrating the end of summer with something blue","End-of-summer-blues", "https://shopstyle.it/l/bK7dY",
                localDateTime,localDateTime,user,commentList,likesList);
        like = new Likes(1, true, localDateTime, localDateTime, user, post);
        comment = new Comment(1,"this looks lovely",localDateTime, localDateTime,post, user);
    }

    @Test
    void register() {
        UserDto userDto = new UserDto("Oluwaseun", "oluwaseun@gmail.com","customer");
        when(userRepository.save(user)).thenReturn(user);
        RegisterResponse register = new RegisterResponse("Registration is successful", localDateTime, user);
        var actual =userService.registerUser(userDto);
        actual.setTimeStamp(localDateTime);
        actual.getUser().setCreated(localDateTime);
        actual.getUser().setUpdated(localDateTime);
        actual.getUser().setUser_id(1);
        assertEquals(register,actual);
    }

    @Test
    void successful_login() {
        LoginDto loginDto = new LoginDto("oluwaseun@gmail.com", "customer");
        when(userRepository.findByEmail("oluwaseun@gmail.com")).thenReturn(ofNullable(user));
        LoginResponse loginResponse = new LoginResponse("success" , localDateTime);
        var actual =  userService.userLogin(loginDto);
        actual.setTimeStamp(localDateTime);
        assertEquals(loginResponse , actual);
    }


    @Test
    void incorrectPassword(){
        LoginDto loginDto = new LoginDto("oluwaseun@gmail.com" , "customers");
        when(userRepository.findByEmail("oluwaseun@gmail.com")).thenReturn(ofNullable(user));
        LoginResponse loginResponse = new LoginResponse("wrong password" , localDateTime);
        var actual =  userService.userLogin(loginDto);
        actual.setTimeStamp(localDateTime);
        assertEquals(loginResponse , actual);
    }

    @Test
    void comment() {
        when(userRepository.findById(1)).thenReturn(ofNullable(user));
        PostDto postDto = new PostDto("End of Summer Blue", "Celebrating the end of summer with something blue","https://shopstyle.it/l/bK7dY", 1);
        CreatePostResponse createPostResponse = new CreatePostResponse("success" , localDateTime , post);
        var actual = userService.createPost(postDto);
        actual.setTimeStamp(localDateTime);
        actual.getPost().setCreated(localDateTime);
        actual.getPost().setUpdated(localDateTime);
        actual.getPost().setPost_id(1);
        actual.getPost().setSlug("end-of-summer-blues");
        assertEquals(createPostResponse , actual);
    }

    @Test
    void like() {
        when(userRepository.findById(1)).thenReturn(ofNullable(user));
        when(repository.findById(1)).thenReturn(ofNullable(post));
        List<Likes> likes = new ArrayList<>(Arrays.asList(like));
        when(likeRepository.likesList(1)).thenReturn(likes);
        LikedDto likeDto = new LikedDto(true);
        LikeResponse likeResponse = new LikeResponse("success" , localDateTime , like , 1);
        var actual = userService.like(1 , 1  , likeDto);
        actual.setTimeStamp(localDateTime);
        actual.setLike(like);
        likeResponse.getLike().setUser(user);
        likeResponse.getLike().setPost(post);
        assertEquals(likeResponse , actual);
    }

    @Test
    void searchPost() {
    }

    @Test
    void findUserById() {
        when(userRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(user));
        assertEquals(user , userService.findUserById(1));
    }
}