package com.wonylog.repository.post;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wonylog.domain.Post;
import com.wonylog.request.post.PostSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static com.wonylog.domain.QPost.post;


@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Post> getList(PostSearch postSearch) {
        long totalCount = jpaQueryFactory.select(post.count())
                .from(post)
                .fetchFirst();

        List<Post> items = jpaQueryFactory.selectFrom(post)
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .orderBy(post.id.desc())
                .fetch();

        return new PageImpl<>(items, postSearch.getPageable(), totalCount);
    }
}
