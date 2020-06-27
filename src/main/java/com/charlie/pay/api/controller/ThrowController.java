package com.charlie.pay.api.controller;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.charlie.pay.api.model.ThrowReceive;
import com.charlie.pay.api.service.ThrowService;

@RestController
public class ThrowController {
	
	@Autowired
	public ThrowService service;
	
	@RequestMapping(value={"/api/throw"}, method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
    public ResponseEntity<?> throwRequest(@RequestHeader Map<String, String> reqHeader, @RequestBody ThrowReceive reqBody) {
		try {
			if(Long.valueOf(reqHeader.get("x-user-id")) != null && StringUtils.isNotEmpty(reqHeader.get("x-room-id"))) {
				if(reqBody.getThrowMoney()!=null && reqBody.getThrowNum()!=null) {
					return ResponseEntity.ok(service.postThrow(reqHeader, reqBody));
				}else {
					return new ResponseEntity<String>("Is not exist money&number in body", HttpStatus.BAD_REQUEST);
				}
			}else {
				return new ResponseEntity<String>("Is not exist user&room in header", HttpStatus.BAD_REQUEST);
			}
		} catch (IOException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	@RequestMapping(value={"/api/receive"}, method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResponseEntity<?> receiveRequest(@RequestHeader Map<String, String> reqHeader, @RequestBody ThrowReceive reqBody) {
		try {
			if(Long.valueOf(reqHeader.get("x-user-id")) != null && StringUtils.isNotEmpty(reqHeader.get("x-room-id"))) {
				if(StringUtils.isNotEmpty(reqBody.getToken())) {
					return ResponseEntity.ok(service.postReceive(reqHeader, reqBody));
				}else {
					return new ResponseEntity<String>("Is not exist money&number in body", HttpStatus.BAD_REQUEST);
				}
			}else {
				return new ResponseEntity<String>("Is not exist user&room in header", HttpStatus.BAD_REQUEST);
			}
		} catch (IOException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	@RequestMapping(value={"/api/throw"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResponseEntity<?> getThrow(@RequestHeader Map<String, String> reqHeader, @RequestParam(required = true) String token) {
		try {
			if(Long.valueOf(reqHeader.get("x-user-id")) != null && StringUtils.isNotEmpty(reqHeader.get("x-room-id"))) {
				if(StringUtils.isNotEmpty(token)) {
					return ResponseEntity.ok(service.getThrow(reqHeader, token));
				}else {
					return new ResponseEntity<String>("Is not exist money&number in body", HttpStatus.BAD_REQUEST);
				}
			}else {
				return new ResponseEntity<String>("Is not exist user&room in header", HttpStatus.BAD_REQUEST);
			}
		} catch (IOException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
}
