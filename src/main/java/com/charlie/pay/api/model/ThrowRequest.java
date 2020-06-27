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
@Table(name = "THROW_REQUEST")
@IdClass(ThrowRequestPk.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class ThrowRequest extends BaseTimeEntity {

	private static final long serialVersionUID = 4335553266827757845L;

	@Id
    private Long userId;

	@Id
	private String roomId;
	
	@Id
	private String token;
	
	private int throwMoney;
	
	private int throwNum;
	
	private LocalDateTime throwDate;

}