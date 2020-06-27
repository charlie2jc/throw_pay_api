package com.charlie.pay.api.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity implements Serializable {
	
	private static final long serialVersionUID = -3906570208517338256L;

	@JsonIgnore
	@CreatedDate
    private LocalDateTime createdDate;
	
	@JsonIgnore
	@LastModifiedDate
    private LocalDateTime modifiedDate;
	
}