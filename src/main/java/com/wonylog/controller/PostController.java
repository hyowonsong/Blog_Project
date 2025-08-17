package com.wonylog.controller;

import com.wonylog.config.UserPrincipal;
import com.wonylog.request.post.PostCreate;
import com.wonylog.request.post.PostEdit;
import com.wonylog.request.post.PostSearch;
import com.wonylog.response.PagingResponse;
import com.wonylog.response.PostResponse;
import com.wonylog.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // @RequestMapping(value = "/posts", method = RequestMethod.GET);
    // 위와 같이 표현할 수도 있지만, REST API 에서는 의도를 명시하기 위해 @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/posts")
    public void post(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid PostCreate request) {
        postService.write(userPrincipal.getUserId(), request);
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit request) {
        postService.edit(postId, request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') && hasPermission(#postId, 'POST', 'DELETE')")
    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }
}
