package com.study.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

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
	
//	@Autowired
//	//	스프링 안에 기본적으로 제공하는 것
//	PlatformTransactionManager transactionManager;
//	//	기본 설정값을 사용하겠다는 것
//	@Autowired
//	TransactionDefinition definition;
	
	//	트랙잭션 템플릿 빈 자동 주입
	@Autowired
	TransactionTemplate transactionTemplate;
	
	
	@Override
	public int buy(String consumerId, int amount, String error) {
		
		//	너무 뒤에 커밋과 롤백이 있음(단점)
//		TransactionStatus status = 
//				transactionManager.getTransaction(definition);
		
		try {
			/*
				현장에서는 표가 발행되었는데 전산에 등록이 안될때(가정)
			 */
			
			//	트랙잭션 템플릿을 통해 DB처리와 비즈니스 로직을 실행
			transactionTemplate.execute(new TransactionCallbackWithoutResult()
			{
				//	익명 클래스의 오버라이딩된 메서드 안으로 모든
				//	로직을 옮겨준다.
				//	또한 템플릿을 사용하면 commit,rollback이 자동으로 처리된다.
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0)
				{
				
				/*
					비즈니스 로직 : 두개 다 성공하면 커밋, 1개라도 에러가 나면
					롤백을 한다. 이렇게 붙어있어서 자동으로 커밋, 롤백
					처리를 해줘 코딩 관리하기가 편하다. 그래서 이 방법이
					선호된다.
				*/
			
				transaction1.pay(consumerId, amount);
				
				//	의도적 에러 발생
				if(error.equals("1")) {int n = 10 / 0; }
				//	DAO변수인 transaction1에서 pay()메서드를 호출
				transaction2.pay(consumerId, amount);	//	회계장부 상황
				}
			});
			
			
			/*
				에러1이 들어오면 이 테이블에서는 저장이 되지 않는다.
				이런일이 생기면 안되므로 트랜잭션이 필요하다.
				transaction1부터 저장이 안되도 롤백시켜준다.
			*/
			
//			transactionManager.commit(status);
			return 1;
			
		} catch(Exception e) {
			System.out.println("[PlatformTransactionManager Rollback");
			//	에러가 나면 transaction1에 들어와 있던 데이터도 롤백이
			//	되어 없어짐 (22장과 달리 에러나면 데이터 저장 x )
//			transactionManager.rollback(status);
			return 0;
		}
	}
}
