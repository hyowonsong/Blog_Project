package com.wonylog.repository.post;


import com.wonylog.domain.Post;
import com.wonylog.request.post.PostSearch;
import org.springframework.data.domain.Page;

public interface PostRepositoryCustom {

    Page<Post> getList(PostSearch postSearch);
}