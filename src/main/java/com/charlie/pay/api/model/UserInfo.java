package com.charlie.pay.api.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "USER_INFO")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class UserInfo extends BaseTimeEntity {

	private static final long serialVersionUID = -6481022806688322116L;

	@Id
    private Long userId;

    private int balance;

}