package com.springbootcommunitydevproj.controller;

import com.springbootcommunitydevproj.dto.PostListDto;
import com.springbootcommunitydevproj.dto.PostRequest;
import com.springbootcommunitydevproj.model.Post;
import com.springbootcommunitydevproj.model.User;
import com.springbootcommunitydevproj.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.nio.file.AccessDeniedException;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequiredArgsConstructor
public class PostViewController {

    private final PostService postService;

    /**
     *      루트 URL은 자유 게시판으로 리다이렉트 시킵니다.
     */
    @GetMapping("/")
    public String root() {
        return "redirect:/posts/free";
    }

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
        @RequestParam(name = "sort", defaultValue = "desc") String ascOrDesc,
        @AuthenticationPrincipal User user,
        Model model, HttpServletRequest request) {

        // boardName이 영문이라면 한글로 변환합니다.
        boardName = convertBoardNameToKorean(boardName);

        List<PostListDto> postList = postService.getPostListByBoardName(boardName, search, page, orderBy, ascOrDesc);
        int totalPages = postService.getPostPages(boardName, null, search);
        setModelAndView(postList, totalPages, user, page, boardName, search, request, model);

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
        @RequestParam(name = "sort", defaultValue = "desc") String ascOrDesc,
        @AuthenticationPrincipal User user,
        Model model, HttpServletRequest request) {

        // boardName이 영문이라면 한글로 변환합니다.
        boardName = convertBoardNameToKorean(boardName);

        List<PostListDto> myPostList = postService.getPostListByUserId(boardName, user.getId(), page, orderBy, ascOrDesc);
        int totalPages = postService.getPostPages(boardName, user.getId(), null);
        setModelAndView(myPostList, totalPages, user, page, boardName, null, request, model);

        return "PostList";
    }

    /**
     *      boradName 게시판의 게시글들 중 post id에 해당하는 게시글 정보를 가져와 그 페이지를 반환합니다. <br>
     *      RequestParam인 duplicate는 해당 게시글을 페이지의 JS 메소드에서 두번 요청을 통해 페이지 정보를 받아올 때, 두 번째 요청에서 조회수가 증가하는 것을 방지합니다. <br>
     *      만약 해당 게시물을 찾지 못했다면 404 Code와 함께 찾지 못했음을 알리는 메시지를, 게시글 공개 등급에 회원 등급이 미치지 못한다면 403 Code와 함께 접근 제한 메시지를 반환합니다.
     */
    @GetMapping("/posts/{boardName}/{post_id}")
    public String showOnePost(
        @PathVariable(name = "boardName") String boardName,
        @PathVariable(name = "post_id") Integer id,
        @RequestParam(name = "duplicate", defaultValue = "false", required = false) String duplicate,
        @AuthenticationPrincipal User user, Model model) {

        // boardName이 영문이라면 한글로 변환합니다.
        boardName = convertBoardNameToKorean(boardName);

        try {
            Post post = postService.findById(id, user.getId(), duplicate);

            model.addAttribute("post", post.toResponse());
            model.addAttribute("boardName", boardName);
            model.addAttribute("userId", user.getId());
            model.addAttribute("userNickname", user.getNickname());
            model.addAttribute("userLevelName", user.getLevel().getLevelName());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Tier " + e.getReason() + " 이상만 접근이 가능한 게시물 입니다.");
        }

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
        @AuthenticationPrincipal User user, Model model) {

        if (postId == null) {
            model.addAttribute("post", new Post().toResponse());
        }
        else {
            try {
                model.addAttribute("post", postService.findById(postId, user.getId(), "false").toResponse());
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
            } catch (AccessDeniedException e) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Tier " + e.getReason() + " 이상만 접근이 가능한 게시물 입니다.");
            }
        }

        model.addAttribute("postRequest", new PostRequest());
        model.addAttribute("userNickname", user.getNickname());
        model.addAttribute("userLevelName", user.getLevel().getLevelName());

        return "PostCreateOrUpdate";
    }

    /**
     *      View Resolver에게 보낼 Model을 세팅하는 메소드입니다. <br>
     *      페이지에 보여질 쿼리 결과 회원 목록, 현재 접속한 회원 정보, 목록 페이지, 게시판 이름, (선택 사항) 검색어, HttpServletRequest 객체와 Model 객체를 파라메터로 받습니다.
     */
    private <T extends PostListDto> void setModelAndView(List<T> postList, Integer totalPages, User user,  Integer page, String boardName, String search, HttpServletRequest request, Model model) {
        int currentStartPage = 1;

        if (Math.ceil((double) page / 10) > 1) {
            // 현재 사용자가 보고 있는 페이지가 11 페이지 이상 20 페이지 이하 라면 화면 하단 시작 페이징은 11부터 시작,
            // 21 페이지 이상 30 페이지 이하 라면 시작 페이징은 21부터 시작, .....
            currentStartPage = ((int) Math.ceil((double) page / 10) * 10) - 9;
        }

        model.addAttribute("postList", postList);
        model.addAttribute("boardName", boardName);
        model.addAttribute("currentStartPage", currentStartPage);
        model.addAttribute("userNickname", user.getNickname());
        model.addAttribute("userLevelName", user.getLevel().getLevelName());
        model.addAttribute("search", search);

        if (totalPages == 0) {
            model.addAttribute("currentLastPage", 1);
        }
        else if (totalPages - currentStartPage < 10) {
            model.addAttribute("currentLastPage", totalPages);
        }
        else {
            model.addAttribute("currentLastPage", currentStartPage + 10);
        }

        model.addAttribute("request", request);
    }

    /**
     *      영어로 된 boardName을 한글로 변환하는 메소드입니다. <br>
     *      지정된 boardName이 아닌 경우, '자유 게시판'으로 변환됩니다.
     */
    private String convertBoardNameToKorean(String boardName) {
        // 한글이면 그대로 반환
        if (!Pattern.matches("[a-zA-Z]*" , boardName)) {
            return boardName;
        }

        switch (boardName) {
            case "attention" -> {return "공지 사항";}
            case "recruit" -> {return "그룹 모집 게시판";}
            case "evaluation" -> {return "평가 게시판";}
            case "share" -> {return "정보 공유 게시판";}
            default -> {return "자유 게시판";}
        }
    }
}
