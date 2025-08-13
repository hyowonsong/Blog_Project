package com.wonylog.controller;

import com.wonylog.request.PostCreate;
import com.wonylog.request.PostEdit;
import com.wonylog.request.PostSearch;
import com.wonylog.response.PagingResponse;
import com.wonylog.response.PostResponse;
import com.wonylog.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // Interceptor 를 위한 테스트
    @GetMapping("/foo")
    public String foo(@RequestAttribute("username") String userName){
        log.info(">>>{}", userName);
        return "foo";
    }

    // @RequestMapping(value = "/posts", method = RequestMethod.GET);
    // 위와 같이 표현할 수도 있지만, REST API 에서는 의도를 명시하기 위해 @GetMapping
    @PostMapping("/posts")
    public void post(@Valid @RequestBody PostCreate request){
        request.validate();
        postService.write(request);
    }

    // 게시물 단건 조회
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId){
        return postService.get(postId);
    }


    // 아래 방법은 Spring Data jpa 의 페이징 방식
    // PageableDefault 의 기본값은 10이다. 제거를 해야 yml 에서 처리가 가능
    /**
    @GetMapping("/posts")
    public List<PostResponse> getList(@PageableDefault Pageable pageable){
        return postService.getList(pageable);
    }**/

    // 지금 방법은 QueryDSL 방식
    @GetMapping("/posts")
    public PagingResponse<PostResponse> getList(@ModelAttribute PostSearch postSearch) {
        return postService.getList(postSearch);
    }

    /**
     * 게시글 수정
     */
    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit request) {
        postService.edit(postId, request);
    }

    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }
}
