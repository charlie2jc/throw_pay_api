package com.charlie.pay.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.charlie.pay.api.model.ThrowMoneyReceive;
import com.charlie.pay.api.model.ThrowMoneyReceivePk;

@RepositoryRestResource
public interface ThrowMoneyReceiveRepository extends JpaRepository<ThrowMoneyReceive, ThrowMoneyReceivePk> {

	ThrowMoneyReceive findByRoomIdAndTokenAndReceiveUserId(String roomId, String token, Long receiveUserId);
	ThrowMoneyReceive findByRoomIdAndUserIdAndTokenAndOrderNum(String roomId, Long userId, String token, int orderNum);
	List<ThrowMoneyReceive> findByRoomIdAndToken(String roomId, String token);
	List<ThrowMoneyReceive> findByRoomIdAndUserIdAndToken(String roomId, Long userId, String token);
	
}