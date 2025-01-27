package com.springbootcommunitydevproj.repository;

import com.springbootcommunitydevproj.dto.PostListDto;
import com.springbootcommunitydevproj.model.Post;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    /**
     *      게시판의 게시글 리스트를 가져오기 위한 조건절 없는 select 쿼리문입니다. <br>
     *      post, user, post_authority 세 개의 테이블을 Join합니다.
     */
    String SELECT_QUERY_WITHOUT_CONDITION = "select post.post_id as postId, post.title as title, post.created_at as createdAt, post.updated_at as updatedAt, post.views as views, user.nickname as author, (select count(*) from `comment` as comm where comm.post_id = post.post_id) as 'comments', post_authority.auth_access_board_level as accessLevel "
        + "from ((post as post left join user as user on post.user_id = user.user_id) left join post_authority as post_authority on post.auth_id = post_authority.auth_id) left join board as board on post.board_id = board.board_id ";

    /**
     *      boardName으로 어느 게시판의 게시글 리스트를 가져옵니다. <br>
     *      search로 게시글 제목을 검색할 수 있습니다. <br>
     *      Pageable 객체로 페이징, 정렬을 수행합니다.
     */
    @Query(value = SELECT_QUERY_WITHOUT_CONDITION
        + "where board.board_name = :boardName and (:search is null or post.title like concat('%', :search, '%'))"
        , nativeQuery = true)
    List<PostListDto> getPostListByBoardName(@Param("boardName") String boardName, @Param("search") String search, Pageable pageable);

    /**
     *      boardName, userId로 회원이 작성한 어느 게시판의 게시글 리스트를 가져옵니다. <br>
     *      Pageable 객체로 페이징, 정렬을 수행합니다.
     */
    @Query(value = SELECT_QUERY_WITHOUT_CONDITION
        + "where board.board_name = :boardName and post.user_id = :userId"
        , nativeQuery = true)
    List<PostListDto> getPostListByUserId(@Param("boardName") String boardName, @Param("userId") Integer userId, Pageable pageable);

    /**
     *      boardName으로 해당 게시판에 등록된 모든 게시글 갯수를 가져옵니다. <br>
     *      userId, search 파라미터가 존재한다면 각각 userId에 해당하는 회원이 작성한 게시글의 갯수, 게시글 제목 기준 검색 결과 갯수를 가져옵니다.
     */
    @Query(value = "select count(*) "
        + "from post left join board on post.board_id = board.board_id "
        + "where board.board_name = :boardName and (:userId is null or post.user_id = :userId) and (:search is null or post.title like concat('%', :search, '%'))"
        , nativeQuery = true)
    Integer getCountByBoardName(@Param("boardName") String boardName, @Param("userId") Integer userId, @Param("search") String search);

    /**
     *      DEPRECATED
     */
    @Query(value = "select exists(select 1 "
        + "from board left join board_authority on board.auth_id = board_authority.auth_id "
        + "where board.board_name = :boardName and board_authority.auth_access_board_level >= ("
        + "select level.level "
        + "from user left join level on user.level_id = level.level_id "
        + "where user.user_id = :userId))"
        , nativeQuery = true)
    Integer checkAuthorizationToBoard(@Param("boardName") String boardName, @Param("userId") Integer userId);

    /**
     *      게시글에 설정된 접근 가능 권한과 접근하려는 회원의 등급을 비교해 접근 가능 여부를 판단하는 쿼리입니다.
     */
    @Query(value = "select exists(select 1 "
        + "from post left join post_authority on post.auth_id = post_authority.auth_id "
        + "where post.post_id = :postId and post_authority.auth_access_board_level >= ("
        + "select level.level "
        + "from user left join level on user.level_id = level.level_id "
        + "where user.user_id = :userId))"
        , nativeQuery = true)
    Integer checkAuthorizationToPost(@Param("postId") Integer postId, @Param("userId") Integer userId);

    /**
     *      게시글을 생성하는 쿼리입니다.
     */
    @Query(value = "update post post, post_authority auth "
        + "set post.title = (case when :title is not null then :title else post.title end), post.content = (case when :content is not null then :content else post.content end), auth.auth_access_board_level = :accessLevel, auth.auth_comment_level = :commentLevel, post.updated_at = now() "
        + "where post.auth_id = auth.auth_id and post.post_id = :postId"
        , nativeQuery = true)
    @Modifying
    @Transactional
    int updatePost(@Param("title") String title, @Param("content") String content, @Param("accessLevel") Integer accessLevel, @Param("commentLevel") Integer commentLevel, @Param("postId") Integer postId);
}
