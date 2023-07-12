package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Config {
    private String classForName;

    private String username;

    private String password;

    private String url;
}
