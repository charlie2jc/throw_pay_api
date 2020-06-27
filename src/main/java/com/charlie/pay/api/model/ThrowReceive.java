package com.charlie.pay.api.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class ThrowReceive implements Serializable {

	private static final long serialVersionUID = 3984304638378364784L;

	private String token;
	
	private Integer throwMoney;
	private Integer throwNum;
	
	private Integer dividend;
	
}