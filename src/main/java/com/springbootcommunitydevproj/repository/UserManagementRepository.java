package com.springbootcommunitydevproj.repository;

import com.springbootcommunitydevproj.dto.UserManagementInfoDto;
import com.springbootcommunitydevproj.model.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserManagementRepository extends JpaRepository<User, Integer> {

    /**
     *      회원 리스트를 가져오기 위한 조건절 없는 select 쿼리문입니다. <br>
     *      user, level, post 세 테이블을 Join합니다. <br>
     *      count() 함수를 위해 조건절 뒤에 group by user.user_id를 붙여야 합니다.
     */
    String SELECT_QUERY_WITHOUT_CONDITION =  "select user.user_id as 'userId', user.nickname as 'nickname', level.level_name as 'levelName', count(post.post_id) as 'currentPostCount' "
        + "from (user as user left outer join level as level on user.level_id = level.level_id) left outer join post as post on user.user_id = post.user_id ";

    /**
     *      user 테이블, level 테이블, post 테이블에서 회원 ID, 닉네임, 회원 등급, 총 게시물 수를 조회해 가져옵니다. <br>
     *      start offset을 통해 한 페이지 당 최대 10개씩 조회합니다. <br>
     *      (3/28 추가) pageable로 어떤 열을 기준으로 오름차순/내림차순 정렬할 지 결정합니다.
     */
    @Query(value = SELECT_QUERY_WITHOUT_CONDITION
        + "where user.is_admin = 0 group by user.user_id, user.nickname, level.level_name", nativeQuery = true)
    List<UserManagementInfoDto> findAllUserManagementInfo(Pageable pageable);

    /**
     *      특정 회원의 닉네임을 조회해 결과를 가져옵니다. <br>
     */
    @Query(value = SELECT_QUERY_WITHOUT_CONDITION
        + "where user.is_admin = 0 and :nickname = user.nickname group by user.user_id, user.nickname, level.level_name", nativeQuery = true)
    List<UserManagementInfoDto> findByNickname(@Param("nickname") String nickname);

    /**
     *      user_id를 통해 회원을 특정하고 그 회원의 등급을 level로 조정합니다. <br>
     *      쿼리 결과를 JPA 영속성 컨텍스트를 거치지 않고 바로 DB에 반영합니다. <br>
     *      이 작업은 Atomic하게 동작합니다.
     */
    @Query(value = "update `user` as U, (select * from `level` where `level`.level = :level) as L "
        + "set U.level_id = L.level_id "
        + "where U.user_id = :userId", nativeQuery = true)
    @Modifying
    @Transactional
    Integer changeUserLevel(@Param("userId") Integer userId, @Param("level") Integer level);
}
