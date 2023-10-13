package mytest;

import java.util.HashMap;
import java.util.Map;

public class ModelUse
{
	public static void main(String[] args) {
		//	지역변수라 메서드가 종료되면 메모리에서 제거된다
		//	힙에 객체가 생성되고 스택에 그 객체의 위치를 저장한 참조변수가 생성된다.
		Map<String, String> model = new HashMap<>();
		//	리퀘스트맵핑 정보에 따라 메서드 호출
		String sReturn = root(model);
		//	리턴받은 뷰 페이지 이름과 모델 객체의 출력을 위해 다시 넘김
		printData(sReturn, model);
	}
	
	//	파라미터 model은 힙 영역에 생성된 객체를 참조하기 위한 또 다른 참조변수
	//	명(이름-임금,부모,스승), 자(존중-선배,친구), 호(별명)
	//	이순신 : 호:덕암, 자:여해, 이름:순신, 군호:덕풍부원군, 시호:충무공
	public static String root(Map model) {
		model.put("name1", "홍길동");
		model.put("name2", "전우치");
		return "Hello";
	}
	
	public static void printData(String s, Map model) {
		String str1 = (String)model.get("name1");
		System.out.println(str1);
		System.out.println("WEB-INF/views/" + s + ".jsp");
	}
}
