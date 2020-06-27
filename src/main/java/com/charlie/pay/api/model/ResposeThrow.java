package com.charlie.pay.api.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class ResposeThrow {

	private LocalDateTime throwDate;
	
	private int throwMoney;
	private int receiveFinishMoney;

	private List<ResposeReceive> receiveFinishInfoList;
	
}