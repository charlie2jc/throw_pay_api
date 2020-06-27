package com.charlie.pay.api.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class ThrowMoneyReceivePk implements Serializable {

	private static final long serialVersionUID = -7267847982270697973L;
	
	private Long userId;
	private String roomId;
	private String token;
	private int orderNum;
}