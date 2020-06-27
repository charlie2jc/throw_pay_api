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
public class ThrowRequestPk implements Serializable {

	private static final long serialVersionUID = 172847567208792645L;

    private Long userId;
	private String roomId;
	private String token;
	
}