package com.charlie.pay.api.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "THROW_MONEY_RECEIVE")
@IdClass(ThrowMoneyReceivePk.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class ThrowMoneyReceive extends BaseTimeEntity {

	private static final long serialVersionUID = 3385504778159333990L;

	@Id
    private Long userId;

	@Id
	private String roomId;
	
	@Id
	private String token;
	
	@Id
    private int orderNum;

	private int dividend;
	
	private Long receiveUserId;
	
	private LocalDateTime receiveDate;
	
	public ThrowMoneyReceive(Long userId, String roomId, String token, int orderNum, int dividend) {
		this.userId = userId;
		this.roomId = roomId;
		this.token = token;
		this.orderNum = orderNum;
		this.dividend = dividend;
	}
	
	public ThrowMoneyReceive(int orderNum, int dividend) {
		this.orderNum = orderNum;
		this.dividend = dividend;
	}

}