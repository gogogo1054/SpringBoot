package com.study.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.study.springboot.dao.ITransaction3Dao;

@Service
public class BuyAndLogService
{
	@Autowired
	IBuyTicketService buyTicket;
	@Autowired
	TransactionTemplate transactionTemplate;
	//	위의 두개가 성공해야 아래 것이 실행됨
	@Autowired
	ITransaction3Dao transaction3;
	
	public int buy(String consumerId, int amount, String error)
	{
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult()
			{
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0)
				{
					int nResult = buyTicket.buy(consumerId, amount, error);
					
					//	의도적 에러 발생
					if(error.equals("2")) {int n = 10 / 0;}
					
					transaction3.pay(consumerId, amount);
				}
			});
			
			return 1;
		} catch (Exception e) {
			System.out.println("[Transaction Propagation #1] Rollback");
			return 0;
		}
	}
}
