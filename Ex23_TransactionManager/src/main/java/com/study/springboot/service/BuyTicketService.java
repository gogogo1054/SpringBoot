package com.study.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import com.study.springboot.dao.ITransaction1Dao;
import com.study.springboot.dao.ITransaction2Dao;

//	@Service로 어노테이션을 지젛아면 이 클래스를 빈으로 사용한다는 의미
@Service
public class BuyTicketService implements IBuyTicketService
{
	//	new는 사용하지 않고 자동으로 주입
	@Autowired	//	자동 주입을 받아 변수를 만든다.
	ITransaction1Dao transaction1;
	@Autowired
	ITransaction2Dao transaction2;
	
	@Autowired
	//	스프링 안에 기본적으로 제공하는 것
	PlatformTransactionManager transactionManager;
	//	기본 설정값을 사용하겠다는 것
	@Autowired
	TransactionDefinition definition;
	
	
	@Override
	public int buy(String consumerId, int amount, String error) {
		
		TransactionStatus status = 
				transactionManager.getTransaction(definition);
		
		try {
			/*
			현장에서는 표가 발행되었는데 전산에 등록이 안될때(가정)
		
			 */
			transaction1.pay(consumerId, amount);
			
			//	의도적 에러 발생
			if(error.equals("1")) {int n = 10 / 0; }
			//	DAO변수인 transaction1에서 pay()메서드를 호출
			transaction2.pay(consumerId, amount);	//	회계장부 상황
			
			/*
				에러1이 들어오면 이 테이블에서는 저장이 되지 않는다.
				이런일이 생기면 안되므로 트랜잭션이 필요하다.
				transaction1부터 저장이 안되도 롤백시켜준다.
			*/
			
			transactionManager.commit(status);
			
			return 1;
		} catch(Exception e) {
			System.out.println("[PlatformTransactionManager Rollback");
			//	에러가 나면 transaction1에 들어와 있던 데이터도 롤백이
			//	되어 없어짐 (22장과 달리 에러나면 데이터 저장 x )
			transactionManager.rollback(status);
			return 0;
		}
	}
}
