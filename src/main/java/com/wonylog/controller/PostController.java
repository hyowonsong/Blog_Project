package com.wonylog.controller;

import com.wonylog.request.PostCreate;
import com.wonylog.response.PostResponse;
import com.wonylog.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // @RequestMapping(value = "/posts", method = RequestMethod.GET);
    // 위와 같이 표현할 수도 있지만, REST API 에서는 의도를 명시하기 위해 @GetMapping
    @PostMapping("/posts")
    public void post(@Valid @RequestBody PostCreate request){
        postService.write(request);
    }

    // 게시물 단건 조회
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId){
        return postService.get(postId);
    }

    @GetMapping("/posts")
    // PageableDefault 의 기본값은 10이다. 제거를 해야 yml 에서 처리가 가능
    public List<PostResponse> getList(@PageableDefault Pageable pageable){
        return postService.getList(pageable);
    }
}
