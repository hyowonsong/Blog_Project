package com.wonylog.repository;


import com.wonylog.domain.Post;
import com.wonylog.request.PostSearch;
import org.springframework.data.domain.Page;

public interface PostRepositoryCustom {

    Page<Post> getList(PostSearch postSearch);
}