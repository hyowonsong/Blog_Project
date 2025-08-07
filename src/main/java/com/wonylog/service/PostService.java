package com.wonylog.service;

import com.wonylog.domain.Post;
import com.wonylog.repository.PostRepository;
import com.wonylog.request.PostCreate;
import com.wonylog.request.PostSearch;
import com.wonylog.response.PagingResponse;
import com.wonylog.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    // @Autowired
    private final PostRepository postRepository;

    /**
     * 글을 저장하는 메서드
     */
    public void write(PostCreate postCreate){
        // postCreate 는 현재 DTO 형태이지 엔티티 형태가 아니기 때문에 들어가지지 않음
        // 따라서, postCreate 를 엔티티 형태로 변환시켜주어야 한다.
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);
    }

    /**
     * 게시글 단건 조회
     */
    public PostResponse get(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 글입니다."));

        PostResponse response =  PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();

        return response;
    }

    /**
     * 게시글 조회(페이징 처리) - Spring Data JPA의 방식
     */
    /**
    public List<PostResponse> getList(Pageable pageable) {
        return postRepository.findAll(pageable)
                .stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }**/

    /**
     * 게시글 조회(페이징 처리) - QueryDSL 방식
     */
    public PagingResponse<PostResponse> getList(PostSearch postSearch) {
        Page<Post> postPage = postRepository.getList(postSearch);
        PagingResponse<PostResponse> postList = new PagingResponse<>(postPage, PostResponse.class);
        return postList;
    }
}
