package com.charlie.pay.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.charlie.pay.api.model.ThrowRequest;
import com.charlie.pay.api.model.ThrowRequestPk;

@RepositoryRestResource
public interface ThrowRequestRepository extends JpaRepository<ThrowRequest, ThrowRequestPk> {

	ThrowRequest findByRoomIdAndToken(String roomId, String token);
	ThrowRequest findByRoomIdAndUserIdAndToken(String roomId, Long userId, String token);
	
}