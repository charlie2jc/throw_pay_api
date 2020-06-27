package com.charlie.pay.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.charlie.pay.api.model.UserInfo;

@RepositoryRestResource
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

	UserInfo findByUserId(Long userId);

}