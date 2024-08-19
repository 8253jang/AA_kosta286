package db.view;

import java.util.List;

import db.dao.EmpDAO;
import db.vo.Emp;

public class MainApp {

	public static void main(String[] args) {
		System.out.println("**JDBC TEST ******");
		EmpDAO dao = new EmpDAO();
		
		//1. 사원이름 검색
		dao.selectByEname();

		System.out.println("\n--2. 전체검색----");
		List<Emp> list = dao.selectAll();
		for(Emp e : list)
			System.out.println(e);//e.toString()호출
		
		
		System.out.println("\n--3. 사원번호 검색----");
		Emp emp = dao.selectByEmpno(8888);
		if(emp==null) {
			System.out.println("찾는 사원이 없습니다.");
		}else {
			System.out.println(emp);
		}
		
		System.out.println("\n--4. 사원등록 ----");
		int re = dao.preparedInsert( new Emp(9001, "heejung","teacher", 2500, null)) ;
		if(re>0) {
			System.out.println(re+" 등록되었습니다.");
		}else {
			System.out.println(re+"등록되지 않았습니다. ");
		}
		
	}//메인끝

}









