package com.springbootcommunitydevproj.service;

import com.springbootcommunitydevproj.dto.PostRequest;
import com.springbootcommunitydevproj.dto.PostListDto;
import com.springbootcommunitydevproj.model.*;
import com.springbootcommunitydevproj.repository.BoardAuthorityRepository;
import com.springbootcommunitydevproj.repository.BoardRepository;
import com.springbootcommunitydevproj.repository.PostAuthorityRepository;
import com.springbootcommunitydevproj.repository.PostRepository;
import jakarta.transaction.Transactional;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.springbootcommunitydevproj.utils.ResponseMessages.POST_ID_NOT_FOUND;

@Slf4j
@Service
public class PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final BoardAuthorityRepository boardAuthorityRepository;
    private final PostAuthorityRepository postAuthorityRepository;

    public PostService(PostRepository postRepository, BoardRepository boardRepository,
        BoardAuthorityRepository boardAuthorityRepository, PostAuthorityRepository postAuthorityRepository) {
        this.postRepository = postRepository;
        this.boardRepository = boardRepository;
        this.boardAuthorityRepository = boardAuthorityRepository;
        this.postAuthorityRepository = postAuthorityRepository;
    }

    /**
     *      게시판 이름을 통해 해당 게시판에 등록된 게시글 목록을 가져오는 메소드입니다. <br>
     *      게시판 이름이 없거나 존재하지 않는 게시판이면 자유 게시판 목록을 반환합니다. <br>
     *      한 페이지 당 10개의 검색 결과가 기준이므로 offset = (page - 1) * 10이 됩니다. <br>
     *      PageRequest 객체는 offset = pageNumber * pageSize로 계산합니다. <br>
     *      만약 page가 음수거나 정렬할 열의 이름, 정렬 순서가 올바르지 않다면 postId 기준 오름차순으로 첫 번째 페이지의 목록을 가져옵니다.
     */
    public List<PostListDto> getPostListByBoardName(String boardName, String search, Integer page, String orderBy, String sort) {
        PageRequest pageRequest = setPageRequest(page, orderBy, sort);
        boardName = validateBoardName(boardName);

        return postRepository.getPostListByBoardName(boardName, search, pageRequest);
    }

    /**
     *      게시판 이름과 회원의 id를 통해 해당 게시판에 회원이 등록한 게시글 목록을 가져오는 메소드입니다. <br>
     *      게시판 이름이 없거나 존재하지 않는 게시판이면 자유 게시판 목록을 기준으로 합니다. <br>
     *      한 페이지 당 10개의 검색 결과가 기준이므로 offset = (page - 1) * 10이 됩니다. <br>
     *      PageRequest 객체는 offset = pageNumber * pageSize로 계산합니다. <br>
     *      만약 page가 음수거나 정렬할 열의 이름, 정렬 순서가 올바르지 않다면 postId 기준 오름차순으로 첫 번째 페이지의 목록을 가져옵니다.
     */
    public List<PostListDto> getPostListByUserId(String boardName, Integer userId, Integer page, String orderBy, String sort) {
        PageRequest pageRequest = setPageRequest(page, orderBy, sort);
        boardName = validateBoardName(boardName);

        return postRepository.getPostListByUserId(boardName, userId, pageRequest);
    }

    /**
     *      해당 게시판의 총 게시물 페이지 수를 가져옵니다.
     */
    public Integer getPostPages(String boardName, Integer userId, String search) {
        boardName = validateBoardName(boardName);

        return (int) Math.ceil((double) postRepository.getCountByBoardName(boardName, userId, search) / 10);
    }

    /**
     *      게시판 이름, 저장할 게시글 내용을 담은 PostRequest 객체, 회원 정보를 통해 DB에 게시글을 저장하는 메소드 입니다. <br>
     *      먼저, 게시글 접근 권한 정보를 DB에 저장하고 게시글 테이블에 게시글을 저장하기 위해 게시판 정보를 조회합니다. <br>
     *      그 뒤, PostRequest 객체의 정보를 토대로 게시글을 DB에 저장합니다. <br>
     *      이때, 게시판 이름이 DB에 저장된 게시판 정보와 다르다면 예외를 발생시키며 Rollback됩니다.
     */
    @Modifying
    @Transactional
    public Post savePost(String boardName, PostRequest request, User user) throws IllegalArgumentException {
        PostAuthority postAuthority = postAuthorityRepository.save(new PostAuthority(request.getAccessLevel(), request.getCommentLevel()));
        Board board = boardRepository.findBoardByBoardName(boardName).orElseThrow(IllegalArgumentException::new);

        // 게시글 구성 요소: 제목, 컨텐츠, 유저, 좋아요, 싫어요, 조회수, 글 권한
        Post post = Post.builder()
            .title(request.getTitle())
            .content(request.getContent())
            .board(board) // 게시판
            .user(user)
            .views(0) // 디폴트 0
            .createdAt(LocalDateTime.now())
            .authority(postAuthority) // 게시글 권한
            .build();

        return postRepository.save(post);
    }

    /**
     *      post id를 통해 해당 게시글에 대한 세부 정보를 반환하는 메소드 입니다. <br>
     *      이때, user id를 통해 해당 게시글에 접근하려는 회원의 등급과 게시글의 공개 등급을 비교해 게시글 접근 가능 여부를 판단합니다. <br>
     *      접근 불가능 하다면 예외를 발생시킵니다. <br>
     *      접근 가능하다면 duplicate가 true인지 false인지 판단해 true일 경우에만 조회수를 1 증가시킵니다.
     */
    @Transactional
    public Post findById(Integer id, Integer userId, String duplicate) throws IllegalArgumentException, AccessDeniedException {
        Post result = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(POST_ID_NOT_FOUND));
        Integer canAccess = postRepository.checkAuthorizationToPost(id, userId);

        if (canAccess != 1) {
            throw new AccessDeniedException(null, null, result.getAuthority().getAuthAccessBoardLevel().toString());
        }

        if (duplicate.equals("false")) {
            return result.updateViews();
        }
        return result;
    }

    /**
     *      기존 게시글을 수정하는 메소드입니다. <br>
     *      게시글 변경 사항을 담은 PostRequest 객체에 내용이 없다면 예외를 발생시킵니다. <br>
     *      게시글이 성공적으로 수정되었다면 수정 성공 메시지를, 수정 도중 문제가 생겼다면 예외를 발생시킵니다.
     */
    @Modifying
    @Transactional
    public String update(Integer id, PostRequest updatePost) throws Exception {
        if (updatePost.getTitle() == null && updatePost.getContent() == null) {
            throw new Exception("변경 사항이 없습니다.");
        }

        String title = null;
        String content = null;

        if (updatePost.getTitle() != null) {
            title = updatePost.getTitle();
        }

        if (updatePost.getContent() != null) {
            content = updatePost.getContent();
        }

        if (postRepository.updatePost(title, content, updatePost.getAccessLevel(), updatePost.getCommentLevel(), id) == 2) {
            return "게시글이 성공적으로 수정되었습니다.";
        }
        else {
            throw new Exception("게시글 수정에 실패했습니다.");
        }
    }

    /**
     *      post id를 통해 해당 게시글을 삭제하는 메소드 입니다. <br>
     *      삭제하고자 하는 게시글의 권한 정보와 게시글 정보를 삭제합니다. <br>
     *      이때, 삭제하고자 하는 게시글이 DB에 존재하지 않으면 예외를 발생시킵니다.
     */
    @Modifying
    @Transactional
    public void delete(Integer id) {

        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            // 게시글 권한 삭제
            postAuthorityRepository.delete(post.getAuthority());
            // 댓글 삭제

            // 게시글 삭제
            postRepository.deleteById(id);
        }
        else {
            throw new IllegalArgumentException(POST_ID_NOT_FOUND);
        }
    }

    /**
     *      쿼리로 검색할 수 있게 boardName을 DB에 저장된 값으로 매칭시키는 메소드입니다. <br>
     *      DB에 지정한 값이 아닌 다른 값이 오면 "자유 게시판"으로 매핑됩니다.
     */
    private String validateBoardName(String boardName) {
        if (!(boardName.equals("자유 게시판") || boardName.equals("공지 사항")
            || boardName.equals("그룹 모집 게시판") || boardName.equals("평가 게시판") || boardName.equals("정보 공유 게시판"))) {
            boardName = "자유 게시판";
        }

        return boardName;
    }

    /**
     *      정렬 방식, 정렬 기준, 페이징을 위한 PageRequest 객체를 세팅하는 메소드입니다.
     */
    private PageRequest setPageRequest(Integer page, String orderBy, String sort) {
        if (!(orderBy.equals("postId") || orderBy.equals("author")
            || orderBy.equals("createdAt") || orderBy.equals("views")
            || sort.equals("asc") || sort.equals("desc")
            || page < 0)) {
            return PageRequest.of(0, 10, Direction.DESC, "postId");
        }

        if (sort.equals("asc")) {
            return PageRequest.of(page - 1, 10, Direction.ASC, orderBy);
        }
        else {
            return PageRequest.of(page - 1, 10, Direction.DESC, orderBy);
        }
    }
}
