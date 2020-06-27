package com.charlie.pay.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ROOM_INFO", indexes = {@Index(columnList="roomId"), @Index(columnList="userId")})
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class RoomInfo extends BaseTimeEntity {

	private static final long serialVersionUID = 7452823056130703455L;

	@Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

	private String roomId;
	private Long userId;
	
	public RoomInfo(String roomId, Long userId) {
		this.roomId = roomId;
		this.userId = userId;
	}

}