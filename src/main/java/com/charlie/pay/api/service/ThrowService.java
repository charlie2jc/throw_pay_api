package com.charlie.pay.api.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.charlie.pay.api.model.ResposeReceive;
import com.charlie.pay.api.model.ResposeThrow;
import com.charlie.pay.api.model.RoomInfo;
import com.charlie.pay.api.model.ThrowMoneyReceive;
import com.charlie.pay.api.model.ThrowReceive;
import com.charlie.pay.api.model.ThrowRequest;
import com.charlie.pay.api.model.UserInfo;
import com.charlie.pay.api.repository.RoomInfoRepository;
import com.charlie.pay.api.repository.ThrowMoneyReceiveRepository;
import com.charlie.pay.api.repository.ThrowRequestRepository;
import com.charlie.pay.api.repository.UserInfoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ThrowService {
	
	@Autowired
    private UserInfoRepository userInfoRepository;
	
	@Autowired
    private RoomInfoRepository roomInfoRepository;
	
	@Autowired
    private ThrowRequestRepository throwRequestRepository;
	
	@Autowired
    private ThrowMoneyReceiveRepository throwMoneyReceiveRepository;
	
	public String postThrow(Map<String, String> reqHeader, ThrowReceive reqBody) throws IOException {
		
		Long throwUserId = Long.valueOf(reqHeader.get("x-user-id"));
		String throwRoomId = reqHeader.get("x-room-id");
		int throwNum = reqBody.getThrowNum();
		int throwMoney = reqBody.getThrowMoney();
		RoomInfo roomInfo = roomInfoRepository.findByRoomIdAndUserId(throwRoomId, throwUserId);
		
		if(roomInfo != null) {
			UserInfo userInfo = userInfoRepository.findByUserId(throwUserId);
			userInfo.setBalance(userInfo.getBalance()-throwMoney);
			
			String crtToken = RandomStringUtils.randomAlphanumeric(3);
			
			List<Integer> persentList = getRandomPersent(throwNum);
			
			ThrowRequest throwRequest = new ThrowRequest(throwUserId, throwRoomId, crtToken, throwMoney, throwNum, LocalDateTime.now());
			List<ThrowMoneyReceive> tmrList = new ArrayList<ThrowMoneyReceive>();
			
			int sum = 0;
			for(int i=0; i<throwNum; i++) {
				int val = 0;
				if(i!=throwNum-1) {
					val=throwMoney*persentList.get(i)/10000;
				}else {
					val = throwMoney-sum; 
				}
				sum+=val;
				tmrList.add(new ThrowMoneyReceive(throwUserId, throwRoomId, crtToken, i+1, val));
			}
			throwRequestRepository.save(throwRequest);
			throwMoneyReceiveRepository.saveAll(tmrList);
			userInfoRepository.save(userInfo);
			
			return "{\"token\":\""+crtToken+"\"}";
			
		}else {
			throw new IOException(getErrMsg(500, "Is not exist Room & User"));
		}
	}
	
	public String postReceive(Map<String, String> reqHeader, ThrowReceive reqBody) throws IOException {
		
		Long receiveUserId = Long.valueOf(reqHeader.get("x-user-id"));
		String receivRoomId = reqHeader.get("x-room-id");
		String receiveToken = reqBody.getToken();

		RoomInfo roomInfo = roomInfoRepository.findByRoomIdAndUserId(receivRoomId, receiveUserId);
		
		if(roomInfo != null) {
			UserInfo userInfo = userInfoRepository.findByUserId(receiveUserId);
			ThrowRequest throwRequest = throwRequestRepository.findByRoomIdAndToken(receivRoomId, receiveToken);
			if(throwRequest!=null) {
				ThrowMoneyReceive throwMoneyReceive = throwMoneyReceiveRepository.findByRoomIdAndTokenAndReceiveUserId(receivRoomId, receiveToken, receiveUserId);
				if(throwMoneyReceive==null) {
					if(!receiveUserId.equals(throwRequest.getUserId())) {
						if(timeCompareMin(throwRequest.getThrowDate(), 10)) {
							List<ThrowMoneyReceive> tmrList = throwMoneyReceiveRepository.findByRoomIdAndToken(receivRoomId, receiveToken);
							List<ThrowMoneyReceive> doList = new ArrayList<ThrowMoneyReceive>();
							for(ThrowMoneyReceive item : tmrList) {
								if(item.getReceiveDate() == null) {
									doList.add(item);
								}
							}
							if(doList != null && doList.size() > 0){
								Random rand = new Random();
								int val = rand.nextInt(doList.size());
								if(val>0) {
									val--;
								}
								ThrowMoneyReceive tmr = doList.get(val);
								tmr.setReceiveUserId(receiveUserId);
								tmr.setReceiveDate(LocalDateTime.now());
								userInfo.setBalance(userInfo.getBalance()+tmr.getDividend());
								throwMoneyReceiveRepository.save(tmr);
								userInfoRepository.save(userInfo);
								return "{\"dividend\":\""+tmr.getDividend()+"\"}";
							}else {
								throw new IOException(getErrMsg(500, "뿌린 금액을 모두 받아가 잔액이 없습니다. "));
							}
							
						}else {
							throw new IOException(getErrMsg(500, "받기실패 뿌린 건은 10분간만 유효합니다."));
						}
					}else {
						throw new IOException(getErrMsg(500, "자신이 뿌리기한 건은 자신이 받을 수 없습니다."));
					}
				}else {
					throw new IOException(getErrMsg(500, "뿌리기 당 한 사용자는 한번만 받을 수 있습니다."));
				}
			}else {
				throw new IOException(getErrMsg(500, "뿌린기가 호출된 대화방과 동일한 대화방에 속한 사용자만이 받을 수 있습니다."));
			}
		}else {
			throw new IOException(getErrMsg(500, "Is not exist Room & User"));
		}
	}
	
	public String getThrow(Map<String, String> reqHeader, String token) throws IOException {
		Long throwUserId = Long.valueOf(reqHeader.get("x-user-id"));
		String throwRoomId = reqHeader.get("x-room-id");

		RoomInfo roomInfo = roomInfoRepository.findByRoomIdAndUserId(throwRoomId, throwUserId);
		
		if(roomInfo != null) {
			ThrowRequest throwRequest = throwRequestRepository.findByRoomIdAndUserIdAndToken(throwRoomId, throwUserId, token);
			if(throwRequest!=null) {
				if(timeCompareDay(throwRequest.getThrowDate(), 7) ) {
					List<ResposeReceive> rrList = new ArrayList<ResposeReceive>();
					List<ThrowMoneyReceive> tmrList = throwMoneyReceiveRepository.findByRoomIdAndUserIdAndToken(throwRoomId, throwUserId, token);
					int sum = 0;
					for(ThrowMoneyReceive item : tmrList) {
						if(item.getReceiveDate() != null) {
							sum+=item.getDividend();
							rrList.add(new ResposeReceive(item.getDividend(), item.getReceiveUserId()));
						}
					}
					ResposeThrow rt = new ResposeThrow();
					rt.setThrowDate(throwRequest.getThrowDate());
					rt.setThrowMoney(throwRequest.getThrowMoney());
					rt.setReceiveFinishMoney(sum);
					rt.setReceiveFinishInfoList(rrList);
					
					return convertModelToJson(rt);
				}else {
					throw new IOException(getErrMsg(500, "뿌린 건에 대한 조회는 7일 동안 할 수 있습니다."));
				}				
			}else {
				throw new IOException(getErrMsg(500, "뿌린 사람 자신만 조회를 할 수 있습니다."));
			}
		}else {
			throw new IOException(getErrMsg(500, "Is not exist Room & User"));
		}
	}
	
	
	public boolean timeCompareMin(LocalDateTime ldt, int min) {
		LocalDateTime now = LocalDateTime.now();
		return ChronoUnit.MINUTES.between(ldt, now) < min ;
	}
	
	public boolean timeCompareDay(LocalDateTime ldt, int day) {
		LocalDateTime now = LocalDateTime.now();
		return ChronoUnit.DAYS.between(ldt, now) < day ;
	}
	
	public List<Integer> getRandomPersent(int num){
		List<Integer> list = new ArrayList<Integer>();
		if(num > 1) {
			Random rand = new Random();
			int max = 7000;
			int sum = 0;
			for(int i = 0; i < num; i++) {
				int val = 0;
				if(i!=num-1) {
					val = rand.nextInt(max-sum); 
				}else {
					val = 10000-sum; 
				}
				sum+=val;
			    list.add(val);
			}
		}else {
			list.add(10000);
		}
		return list;
	}
	
	public String getErrMsg(int code, String msg) {
		return "{\"code\":"+code+", \"message\":\""+msg+"\"}";
	}
    
    public String convertModelToJson(Object model) {
    	String rtnJsonStr = "";
    	ObjectMapper mapper = new ObjectMapper();
		try {
			rtnJsonStr = mapper.writeValueAsString(model);
		} catch (JsonProcessingException e) {
			log.error("convertModelToJson.JsonProcessingException - ", e.getMessage());
		}
		return rtnJsonStr;
    }

}
