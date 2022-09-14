package com.elizabeth.restblogweek9.DTO;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class LoginDto {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
