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
     *      해당 게시글의 조회수를 1 증가시킵니다.
     */
    @Query(value = "update post set views = views + 1 where post_id = :postId"
        , nativeQuery = true)
    @Modifying
    @Transactional
    void updateViews(@Param("postId") Integer postId);

    /**
     *      boardName으로 해당 게시판에 등록된 모든 게시글 갯수를 가져옵니다.
     */
    @Query(value = "select count(*) "
        + "from post left join board on post.board_id = board.board_id "
        + "where board.board_name = :boardName"
        , nativeQuery = true)
    Integer getCountByBoardName(@Param("boardName") String boardName);

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
     *      게시글 등록 쿼리 (수정중)
     */
    @Query(value = "update post "
        + "set title = (case when :title != '' then :title else title end), content = (case when :content != '' then :content else content end), updated_at = now() "
        + "where post_id = :postId"
        , nativeQuery = true)
    @Modifying
    @Transactional
    Integer updatePost(@Param("title") String title, @Param("content") String content, @Param("postId") Integer postId);
}
