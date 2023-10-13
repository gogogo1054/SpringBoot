package com.study.springboot.dto;

import lombok.Data;

//	현장 매표소(비유)
@Data
public class Transaction1Dto
{
	private String consumerId;
	private int amount;
}
