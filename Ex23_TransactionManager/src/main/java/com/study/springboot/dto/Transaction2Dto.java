package com.study.springboot.dto;

import lombok.Data;

//	회계 장부 (비유)
@Data
public class Transaction2Dto
{
	private String consumerId;
	private int amount;
}
