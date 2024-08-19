package db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.vo.Emp;

public class EmpDAO {
   /**
    * 사원테이블에서 모든사원이름 조회
    *  : select ename from emp
    * */
	public void selectByEname() {
		//로드 연결 실행 
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		
		try {
		  con = DbManager.getConnection();
		  
		  st = con.createStatement();
		  rs = st.executeQuery("select ename from emp");
		  
		  while( rs.next() ) {//현재위치에서 행을 아래로 내린다.
			  //열을 조회
			  String ename = rs.getString("ename");
			  System.out.println(ename);
		  }
		  
		  System.out.println("--조회 완료------");
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			//닫기 
			DbManager.dbClose(con, st, rs);
		}
		
	}
	
	/**
	 * 모든 사원 정보 조회하기
	 *  select empno, ename,job, sal, hiredate from emp
	 * */
	public List<Emp> selectAll() {
		//로드 연결 실행 
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		String sql="select empno, ename,job, sal, hiredate from emp";
		
		List<Emp> list = new ArrayList<Emp>();
		try {
		  con = DbManager.getConnection();
		  st = con.createStatement();
		  rs = st.executeQuery(sql);
		  while(rs.next()) {
			  int empno = rs.getInt(1);
			  String ename = rs.getString("ename");
			  String job = rs.getString("JOB");
			  int sal =rs.getInt(4);
			  String hiredate = rs.getString(5);
			  
			  //하나의 레코드 정보를 Emp객체 만들고
			  Emp emp = new Emp(empno, ename, job, sal, hiredate);
			  
			  //만든 Emp객체를 list에 추가한다.
			  list.add(emp);
		  }
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			//닫기 
			DbManager.dbClose(con, st, rs);
		}
		
		return list;
	}//메소드끝
	
	/**
	 * 사원번호에 해당하는 사원정보 조회
	 * select empno, ename,job, sal, hiredate from emp where empno=전달된값
	 * */
	public Emp selectByEmpno(int empno) { 
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		String sql="select empno, ename,job, sal, hiredate from emp "
				+ "where empno=" + empno;
		
		Emp emp=null;
		try {
			con = DbManager.getConnection();
			st =con.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next()) {
				emp = new Emp(rs.getInt(1),rs.getString(2), 
						rs.getString(3), rs.getInt(4), rs.getString(5));
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DbManager.dbClose(con, st, rs);
		}
		return emp;

	}//메소드끝
	
	/**
	 * 사원등록
	 * insert into emp(empno, ename,job, sal, hiredate) 
	 *  values(9000 , 'hee', 'teacher', 2000, sysdate)
	 * */
	public int insert(Emp emp) {
		Connection con=null;
		Statement st=null;
		String sql="insert into emp(empno, ename,job, sal, hiredate) "
				+ "values("+emp.getEmpno() +" , '"+emp.getEname()+"', 'teacher', 2000, sysdate)";
		int result=0;
		try {
			con = DbManager.getConnection();
			st = con.createStatement();
			result = st.executeUpdate(sql);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DbManager.dbClose(con, st);
		}
		
		return result;
	}//메소드끝
	
	
	
	public int preparedInsert(Emp emp) {
		Connection con=null;
		PreparedStatement ps=null;
		String sql="insert into emp(empno, ename,job, sal, hiredate) "
				+ "values(?,?,?,?, sysdate)";
		int result=0;
		try {
			con = DbManager.getConnection();
			ps = con.prepareStatement(sql);
			
			//?가 있다면, ?의 순서대로 개수만큼 ps.setXxx(?index, 값) 필요
			ps.setInt(1, emp.getEmpno());
			ps.setString(2, emp.getEname());
			ps.setString(3, emp.getJob());
			ps.setInt(4, emp.getSal());
			
			result = ps.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DbManager.dbClose(con, ps);
		}
		return result;
	}//메소드끝
	
}














