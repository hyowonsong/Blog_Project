package com.wonylog.controller;

import com.wonylog.request.PostCreate;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class PostController {

    // @RequestMapping(value = "/posts", method = RequestMethod.GET);
    // 위와 같이 표현할 수도 있지만, REST API 에서는 의도를 명시하기 위해 @GetMapping
    @PostMapping("/posts")
    public Map<String, String> post(@Valid @RequestBody PostCreate request){
        return Map.of();
    }
}
