package com.springbootcommunitydevproj.controller;

import com.springbootcommunitydevproj.dto.UserManagementInfoDto;
import com.springbootcommunitydevproj.service.UserManagementService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@AllArgsConstructor
public class UserManagementViewController {

    private final UserManagementService userManagementService;

    /**
     *      Admin 페이지의 회원 관리 페이지를 가져옵니다. <br>
     *      page로 목록의 페이지 수를, orderby로 정렬 기준을, sort로 정렬 방식을 결정합니다. <br>
     *      각 파라메터를 명시하지 않으면 기본 값이 세팅됩니다.
     */
    @GetMapping("/admin/user/management")
    public String userManagementPage(
        @RequestParam(name = "page", defaultValue = "1") Integer page,
        @RequestParam(name = "orderby", defaultValue = "userId") String orderBy,
        @RequestParam(name = "sort", defaultValue = "asc") String ascOrDesc,
        Model model, HttpServletRequest request) {

        List<UserManagementInfoDto> userList = userManagementService.getAllUserManagementInfo(page, orderBy, ascOrDesc);
        setModelAndView(userList, page, request, model);

        return "admin-user-manage.html";
    }

    /**
     *      회원의 닉네임을 기준으로 검색한 결과 페이지를 가져옵니다.
     */
    @GetMapping("/admin/user/management/search")
    public String searchUserByNickname(@RequestParam(name = "nickname") String nickname,
        Model model, HttpServletRequest request) {

        List<UserManagementInfoDto> userList = userManagementService.getUserManagementInfoByNickname(nickname);
        setModelAndView(userList, 1, request, model);

        return "admin-user-manage.html";
    }

    /**
     *      View Resolver에게 보낼 Model을 세팅하는 메소드입니다. <br>
     *      페이지에 보여질 쿼리 결과 회원 목록, 목록 페이지, HttpServletRequest 객체와 Model 객체를 파라메터로 받습니다.
     */
    private <T extends UserManagementInfoDto> void setModelAndView(List<T> userList, Integer page, HttpServletRequest request, Model model) {
        int totalPages = userManagementService.getUserManagementInfoPages();
        int currentStartPage = 1;

        if (Math.ceil((double) page / 10) > 1) {
            currentStartPage = (int) Math.ceil((double) page / 10) * 10;
        }

        Set<Integer> blockedUserSet = userManagementService.getAllBlockedUser();

        model.addAttribute("userList", userList);
        model.addAttribute("blockedUserSet", blockedUserSet);
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
