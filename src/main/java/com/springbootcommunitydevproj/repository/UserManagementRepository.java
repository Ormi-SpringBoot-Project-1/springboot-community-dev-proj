package com.springbootcommunitydevproj.repository;

import com.springbootcommunitydevproj.domain.User;
import com.springbootcommunitydevproj.dto.UserManagementInfoDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserManagementRepository extends JpaRepository<User, Integer> {

    // user 테이블, level 테이블, post 테이블에서 회원 ID, 닉네임, 회원 등급, 총 게시물 수를 조회해 가져옵니다.
    // start offset을 통해 한 페이지 당 최대 30개씩 조회합니다.
    @Query(value = "select user.user_id as 'userId', user.nickname as 'nickname', level.level_name as 'levelName', count(post.post_id) as 'currentPostCount' "
        + "from (user as user left outer join level as level on user.level_id = level.level_id) left outer join post as post on user.user_id = post.user_id "
        + "where user.is_admin = 0 group by user.user_id limit 30 offset :start", nativeQuery = true)
    List<UserManagementInfoDto> findAllUserManagementInfo(@Param("start") Integer start);
}
