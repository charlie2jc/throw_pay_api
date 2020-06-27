package com.charlie.pay.api.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.charlie.pay.api.model.ResposeThrow;
import com.charlie.pay.api.model.ThrowReceive;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestPropertySource(
        properties = {
        		"spring.datasource.url=jdbc:h2:mem:unittestdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
        		"spring.datasource.driver-class-name= org.h2.Driver",
        		"spring.datasource.username=sa",
        		"spring.datasource.password=",
        		"spring.h2.console.enabled=true"
        }
)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class ThrowServiceTest {

	@Autowired
	private ThrowService throwService;
	
	public static String token = "";
	
	@Test
    @Order(1)
	@DisplayName("TEST - PSOT Throw Request")
	public void throwRequestTest() {
		ObjectMapper mapper = new ObjectMapper();
    	try {
    		Map<String, String> reqHeader = new HashMap<String, String>();
    		reqHeader.put("x-user-id", "1000000001");
    		reqHeader.put("x-room-id", "fc344fb1-1bea-40f4-88fd-968d2bec971d");

    		ThrowReceive reqBody = new ThrowReceive();
    		reqBody.setThrowMoney(100000);
    		reqBody.setThrowNum(7);
    		
    		String rtnJsonStr = throwService.postThrow(reqHeader, reqBody);
    		ThrowReceive tr= mapper.readValue(rtnJsonStr, ThrowReceive.class);
    		token = tr.getToken();
    		assertTrue(!"".equals(tr.getToken()));
        	assertTrue(tr.getToken().length() ==3);
    	} catch (IOException e) {
			log.error("Throw Request - " + e.getMessage());
		}
	}
    
	@Test
    @Order(2)
	@DisplayName("TEST - POST Receive Request")
	public void receiveRequestTest() {
		ObjectMapper mapper = new ObjectMapper();
    	try {
    		Map<String, String> reqHeader = new HashMap<String, String>();
    		reqHeader.put("x-user-id", "1000000002");
    		reqHeader.put("x-room-id", "fc344fb1-1bea-40f4-88fd-968d2bec971d");
    		ThrowReceive reqBody = new ThrowReceive();
    		reqBody.setToken(token);
    		String rtnJsonStr = throwService.postReceive(reqHeader, reqBody);
    		ThrowReceive tr= mapper.readValue(rtnJsonStr, ThrowReceive.class);
    		assertTrue(tr.getDividend()>-1);
    	} catch (IOException e) {
			log.error("Receive Request - " + e.getMessage());
		}
	}

	@Test
    @Order(3)
	@DisplayName("TEST - GET Throw Info Request")
	public void throwInfoRequestTest() {
		ObjectMapper mapper = new ObjectMapper();
    	try {
    		Map<String, String> reqHeader = new HashMap<String, String>();
    		reqHeader.put("x-user-id", "1000000001");
    		reqHeader.put("x-room-id", "fc344fb1-1bea-40f4-88fd-968d2bec971d");
    		String rtnJsonStr = throwService.getThrow(reqHeader, token);
    		ResposeThrow rt= mapper.readValue(rtnJsonStr, ResposeThrow.class);
    		assertTrue(rt.getThrowMoney()>0);
    		assertTrue(rt.getReceiveFinishMoney()>-1);
    	} catch (IOException e) {
			log.error("Throw Info Request - " + e.getMessage());
		}
	}
    
	
}
