package com.springbootcommunitydevproj.controller;

import com.springbootcommunitydevproj.dto.PostListDto;
import com.springbootcommunitydevproj.dto.PostRequest;
import com.springbootcommunitydevproj.model.Post;
import com.springbootcommunitydevproj.model.User;
import com.springbootcommunitydevproj.service.BoardService;
import com.springbootcommunitydevproj.service.PostService;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PostViewController { // 전체 게시판, 특정 게시판 화면 목록

    private final BoardService boardService;
    private final PostService postService;

    /**
     *      boardName의 게시글 목록을 반환합니다. <br>
     *      page로 목록의 페이지 수를, orderby로 정렬 기준을, sort로 정렬 방식을 결정합니다. <br>
     *      search로 게시글 제목을 기준으로 검색할 수 있습니다. <br>
     *      각 파라메터를 명시하지 않으면 기본 값이 세팅됩니다.
     */
    @GetMapping("/posts/{boardName}")
    public String showAllPosts(
        @PathVariable(name = "boardName") String boardName,
        @RequestParam(name = "search", required = false) String search,
        @RequestParam(name = "page", defaultValue = "1") Integer page,
        @RequestParam(name = "orderby", defaultValue = "postId") String orderBy,
        @RequestParam(name = "sort", defaultValue = "asc") String ascOrDesc,
        Model model, HttpServletRequest request) {

        List<PostListDto> postList = postService.getPostListByBoardName(boardName, search, page, orderBy, ascOrDesc);
        setModelAndView(postList, page, boardName, request, model);

        return "PostList";
    }

    /**
     *      boardName의 게시글 목록 중 회원이 작성한 게시글 목록을 반환합니다. <br>
     *      page로 목록의 페이지 수를, orderby로 정렬 기준을, sort로 정렬 방식을 결정합니다. <br>
     *      search로 게시글 제목을 기준으로 검색할 수 있습니다. <br>
     *      각 파라메터를 명시하지 않으면 기본 값이 세팅됩니다.
     */
    @GetMapping("/posts/{boardName}/myPost")
    public String getPostListByUserId(
        @PathVariable(name = "boardName") String boardName,
        @RequestParam(name = "page", defaultValue = "1") Integer page,
        @RequestParam(name = "orderby", defaultValue = "postId") String orderBy,
        @RequestParam(name = "sort", defaultValue = "asc") String ascOrDesc,
        @AuthenticationPrincipal User user,
        Model model, HttpServletRequest request) {

        List<PostListDto> myPostList = postService.getPostListByUserId(boardName, user.getId(), page, orderBy, ascOrDesc);
        setModelAndView(myPostList, page, boardName, request, model);

        return "PostList";
    }

    // 특정 게시글 목록
    @GetMapping("/posts/{boardName}/{board_id}")
    public String showBoardPosts(
        @PathVariable(name = "boardName") String boardName,
        @PathVariable(name = "board_id") Integer board_id, Model model) {

        List<Post> posts = boardService.getBoardPost(board_id);
        model.addAttribute("posts", posts);

        return "post";
    }

    // 게시글 조회
    @GetMapping("/posts/{post_id}")
    public String showOnePost(@PathVariable(name = "post_id") Integer id, Model model) {

        Post post = postService.findById(id).updateViews();
        model.addAttribute("post", post.toResponse());
        return "post";
    }

    /**
     *      게시글 등록 혹은 수정 페이지를 반환합니다. <br>
     *      만약 게시글 등록이라면 빈 PostResponse 객체를 보내주고 <br>
     *      게시글 수정이라면 해당 게시글을 postId로 조회해 그 결과 PostResponse 객체를 보내줍니다. <br>
     *      폼 데이터를 모아 줄 빈 PostRequest 객체도 함께 보내줍니다.
     */
    @GetMapping("/posts/{boardName}/newPost")
    public String createPostByBoardName(
        @PathVariable(name = "boardName") String boardName,
        @RequestParam(name = "postId", required = false) Integer postId,
        Model model) {

        if (postId == null) {
            model.addAttribute("post", new Post().toResponse());
        }
        else {
            model.addAttribute("post", postService.findById(postId).toResponse());
        }

        model.addAttribute("postRequest", new PostRequest());

        return "PostCreateOrUpdate";
    }

    /**
     *      View Resolver에게 보낼 Model을 세팅하는 메소드입니다. <br>
     *      페이지에 보여질 쿼리 결과 회원 목록, 목록 페이지, 게시판 이름, HttpServletRequest 객체와 Model 객체를 파라메터로 받습니다.
     */
    private <T extends PostListDto> void setModelAndView(List<T> postList, Integer page, String boardName, HttpServletRequest request, Model model) {
        int totalPages = postService.getPostPages(boardName);
        int currentStartPage = 1;

        if (Math.ceil((double) page / 10) > 1) {
            currentStartPage = (int) Math.ceil((double) page / 10) * 10;
        }

        model.addAttribute("postList", postList);
        model.addAttribute("boardName", boardName);
        model.addAttribute("currentStartPage", currentStartPage);

        if (totalPages - currentStartPage < 10) {
            model.addAttribute("currentLastPage", totalPages);
        }
        else {
            model.addAttribute("currentLastPage", currentStartPage + 10);
        }

        model.addAttribute("request", request);
    }
}
