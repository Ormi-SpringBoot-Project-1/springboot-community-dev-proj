package com.springbootcommunitydevproj.repository;

import com.springbootcommunitydevproj.model.BlockedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BlockedUserRepository extends JpaRepository<BlockedUser, Integer> {

    /**
     *       user_id를 통해 특정 회원을 가입 제한 테이블로 이동합니다.
     *      즉, 특정 유저의 가입 제한을 설정합니다.
     *      쿼리 결과를 JPA 영속성 컨텍스트를 거치지 않고 바로 DB에 반영합니다.
     *      이 작업은 Atomic하게 동작합니다.
     */
    @Query(value = "insert into `blocked_user`(`user_id`, `email`, `user_ip`) "
        + "select user.`user_id`, user.`email`, user.`user_ip` "
        + "from user "
        + "where user.user_id = :userId", nativeQuery = true)
    @Modifying
    @Transactional
    Integer moveUserToBlockedUser(@Param("userId") Integer userId);
}
