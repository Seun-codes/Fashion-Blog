package com.elizabeth.restblogweek9.ServiceImpl;

import com.elizabeth.restblogweek9.DTO.*;
import com.elizabeth.restblogweek9.Exceptions.LikeException;
import com.elizabeth.restblogweek9.Exceptions.PostNotFoundException;
import com.elizabeth.restblogweek9.Exceptions.UserNotFoundException;
import com.elizabeth.restblogweek9.Response.*;
import com.elizabeth.restblogweek9.model.Comment;
import com.elizabeth.restblogweek9.model.Likes;
import com.elizabeth.restblogweek9.model.Post;
import com.elizabeth.restblogweek9.model.User;
import com.elizabeth.restblogweek9.repositories.CommentRepository;
import com.elizabeth.restblogweek9.repositories.LikeRepository;
import com.elizabeth.restblogweek9.repositories.PostRepository;
import com.elizabeth.restblogweek9.repositories.UserRepository;
import com.elizabeth.restblogweek9.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
@Autowired
    public UserServiceImpl(CommentRepository commentRepository, LikeRepository likeRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }


    private static final Pattern nonLatin = Pattern.compile("[^\\w-]");
    private static final Pattern whiteSpace = Pattern.compile("[\\s]");

    @Override
    public RegisterResponse registerUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);

        return new RegisterResponse("Registration is successful", LocalDateTime.now());
    }

    @Override
    public LoginResponse userLogin(LoginDto loginDto) {
        User user = findByEmail(loginDto.getEmail());
        LoginResponse response = null;
        if(user != null){
            if(user.getPassword().equals(loginDto.getPassword())){
                response = new LoginResponse("success", LocalDateTime.now());

        }else{
                response =new LoginResponse("wrong password", LocalDateTime.now());
    }
            }

        return response;
    }


    @Override
    public CreatePostResponse createPost(PostDto postDto) {
        Post newPost = new Post();
        User user = findUserById(postDto.getUser_id());
        newPost.setTitle(postDto.getTitle());
        newPost.setDescription(postDto.getDescription());
        newPost.setSlug(makeSlug(postDto.getTitle()));
        newPost.setImage(postDto.getImage());
        newPost.setUser(user);
        postRepository.save(newPost);


        return new CreatePostResponse("success", LocalDateTime.now(), newPost);
    }


    @Override
    public CommentResponse comments(int user_id, int post_id, CommentDto commentDto) {
        User user = findUserById(user_id);
        Post post = findPostById(post_id);
        Comment newComment = new Comment();
        newComment.setComment(commentDto.getComment());
        newComment.setUser(user);
        newComment.setPost(post);
        commentRepository.save(newComment);
        return new CommentResponse("success",LocalDateTime.now(), newComment , post);
    }

    @Override
    public LikeResponse like(int user_id, int post_id, LikedDto likedDto) {
        Likes like = new Likes();
        User user = findUserById(user_id);
        Post post = findPostById(post_id);
        LikeResponse response = null;
        Likes duplicateLike = likeRepository.findLikeByUser_idAndPost_id(user_id , post_id);
        if (duplicateLike == null){
            like.setLiked(likedDto.isLiked());
            like.setUser(user);
            like.setPost(post);
            likeRepository.save(like);
            List<Likes> likeList = likeRepository.likesList(post_id);
            response = new LikeResponse("success" , LocalDateTime.now() , like , likeList.size());
        }else {
            likeRepository.delete(duplicateLike);
            throw  new LikeException("This post has been liked, It is now Unliked :(");
        }

        return response;
    }

    @Override
    public SearchPostResponse postSearch(String Keyword) {
        List<Post> postList = postRepository.findByTitle(Keyword);
        return new SearchPostResponse("success" , LocalDateTime.now() , postList);

    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User email" + email + "not found"));
    }
    public User findUserById(int id) {
        return userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with id: " + id + " not found"));
    }
    public Post findPostById(int post_Id){
        return postRepository.findById(post_Id).orElseThrow(()->new PostNotFoundException("Post with id" + post_Id + "not found"));
    }

    public String makeSlug(String input) {
        String nowhitespace = whiteSpace.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = nonLatin.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }
}
