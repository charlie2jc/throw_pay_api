package com.charlie.pay.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.charlie.pay.api.model.RoomInfo;

@RepositoryRestResource
public interface RoomInfoRepository extends JpaRepository<RoomInfo, Long> {

	RoomInfo findByRoomIdAndUserId(String roomId, Long userId);
	
	List<RoomInfo> findByRoomId(String roomId);
	
	List<RoomInfo> findByUserId(Long userId);
	
}