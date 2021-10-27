package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import common.ConnectionFactory;
import vo.Employees;

public class EmployeesDAO implements IDao<Employees, Integer> {

//	@Override
//	public int insert(Employees vo) throws SQLException {
//		Connection conn = ConnectionFactory.create();
//		String sql = "INSERT INTO EMPLOYEES "
//					+ "(EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, HIRE_DATE, JOB_ID, SALARY, COMMISSION_PCT, MANAGER_ID, DEPARTMENT_ID) "
//					+ "VALUES(EMPLOYEES_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?)";
//		PreparedStatement pstmt = conn.prepareStatement(sql);
////		pstmt.setInt(1, vo.getEmployee_id());
//		pstmt.setString(1, vo.getFirst_name());
//		pstmt.setString(2, vo.getLast_name());
//		pstmt.setString(3, vo.getEmail());
//		pstmt.setString(4, vo.getPhone_number());
//		pstmt.setDate(5, vo.getHire_date());
//		pstmt.setString(6, vo.getJob_id());//---------------------------
//		pstmt.setInt(7, vo.getSalary());
//		pstmt.setDouble(8, vo.getCommission_pct());
//		pstmt.setInt(9, vo.getManager_id());
//		pstmt.setInt(10, vo.getDepartment_id());//-------------------------
//		int res = pstmt.executeUpdate();
//		conn.close();
//		return res;
//	}

	@Override
	public int insert(Employees vo) throws SQLException {
		Connection conn = ConnectionFactory.create();
		String sql = "INSERT INTO EMPLOYEES "
				+ "(EMPLOYEE_ID,FIRST_NAME,LAST_NAME,EMAIL,PHONE_NUMBER,HIRE_DATE,JOB_ID,SALARY,COMMISSION_PCT,MANAGER_ID,DEPARTMENT_ID) "
				+ "VALUES(EMPLOYEES_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		// pstmt.setInt(1, vo.getEmployee_id());
		pstmt.setString(1, vo.getFirst_name());
		pstmt.setString(2, vo.getLast_name());
		pstmt.setString(3, vo.getEmail());
		pstmt.setString(4, vo.getPhone_number());
		pstmt.setDate(5, vo.getHire_date());
		pstmt.setString(6, vo.getJob_id());
		pstmt.setInt(7, vo.getSalary());
		pstmt.setDouble(8, vo.getCommission_pct());
		pstmt.setInt(9, vo.getManager_id());
		pstmt.setInt(10, vo.getDepartment_id());
		int res = pstmt.executeUpdate();
		conn.close();
		return res;
	}

	@Override
	public int delete(Integer key) throws SQLException {
		Connection conn = ConnectionFactory.create();
		String sql = "DELETE FROM EMPLOYEES WHERE EMPLOYEE_ID = ? ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, key);
		int res = pstmt.executeUpdate();
		conn.close();
		return res;
	}

	@Override
	public int update(Employees vo) throws SQLException {
		Connection conn = ConnectionFactory.create();
		String sql = "UPDATE EMPLOYEES SET FIRST_NAME=?,LAST_NAME=?,EMAIL=?, PHONE_NUMBER=?, HIRE_DATE=?, " 
		+ "JOB_ID=?, SALARY=?, COMMISSION_PCT=?,  MANAGER_ID=?, DEPARTMENT_ID=? "
				+ "WHERE EMPLOYEE_ID=? ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, vo.getFirst_name());
		pstmt.setString(2, vo.getLast_name());
		pstmt.setString(3, vo.getEmail());
		pstmt.setString(4, vo.getPhone_number());
		pstmt.setDate(5, vo.getHire_date());
		pstmt.setString(6, vo.getJob_id());
		pstmt.setInt(7, vo.getSalary());
		pstmt.setDouble(8, vo.getCommission_pct());
		pstmt.setInt(9, vo.getManager_id());
		pstmt.setInt(10, vo.getDepartment_id());
		pstmt.setInt(11, vo.getEmployee_id());
		int res = pstmt.executeUpdate();
		conn.close();
		return res;
	}

//	@Override
//	public Employees select(Integer key) throws SQLException {
//		Connection conn = ConnectionFactory.create();
//		String sql = "SELECT * FROM EMPLOYEES WHERE EMPLOYEE_ID=?";
//		PreparedStatement pstmt = conn.prepareStatement(sql);
//		pstmt.setInt(1, key);
//		ResultSet rs = pstmt.executeQuery();
//		Employees vo = new Employees();
//		while (rs.next()) {
//			// 컬럼이름으로 가져오는 것이 더 안전함
//			vo.setEmployee_id(rs.getInt("EMPLOYEE_ID")); 
//			vo.setFirst_name(rs.getString("FIRST_NAME"));
//			vo.setLast_name(rs.getString("LAST_NAME"));
//			vo.setEmail(rs.getString("EMAIL"));
//			vo.setPhone_number(rs.getString("PHONE_NUMBER"));
//			vo.setHire_date(rs.getDate("HIRE_DATE"));
//			vo.setJob_id(rs.getString("JOB_ID"));
//			vo.setSalary(rs.getInt("SALARY"));
//			vo.setCommission_pct(rs.getDouble("COMMISSION_PCT"));
//			vo.setManager_id(rs.getInt("MANAGER_ID"));
//			vo.setDepartment_id(rs.getInt("DEPARTMENT_ID"));
//		}
//		conn.close();
//		return vo;
//	}
	@Override
	public Employees select(Integer key) throws SQLException {
		Employees vo = new Employees();
		Connection conn = ConnectionFactory.create();
		String sql = "SELECT * FROM EMPLOYEES WHERE EMPLOYEE_ID = "+key.intValue();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		// unsupported network datatype or representation
		while (rs.next()) {
			// EMPLOYEE_ID,FIRST_NAME,LAST_NAME,EMAIL,PHONE_NUMBER,HIRE_DATE,JOB_ID,SALARY,COMMISION_PCT,MANAGER_ID,DEPARTMENT_ID
			vo.setEmployee_id(rs.getInt("EMPLOYEE_ID"));
			vo.setFirst_name(rs.getString("FIRST_NAME"));
			vo.setLast_name(rs.getString("LAST_NAME"));
			vo.setEmail(rs.getString("EMAIL"));
			vo.setPhone_number(rs.getString("PHONE_NUMBER"));
			vo.setHire_date(rs.getDate("HIRE_DATE"));
			vo.setJob_id(rs.getString("JOB_ID"));
			vo.setSalary(rs.getInt("SALARY"));
			vo.setCommission_pct(rs.getInt("COMMISSION_PCT"));
			vo.setManager_id(rs.getInt("MANAGER_ID"));
			vo.setDepartment_id(rs.getInt("DEPARTMENT_ID"));
		}
		conn.close();
		return vo;
	}

	@Override
	public List<Employees> selectAll() throws SQLException {
		Connection conn = ConnectionFactory.create();
		String sql = "SELECT * FROM EMPLOYEES ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		List<Employees> data = new ArrayList<>();
		while (rs.next()) {
			Employees vo = new Employees();
			vo.setEmployee_id(rs.getInt("EMPLOYEE_ID")); 
			vo.setFirst_name(rs.getString("FIRST_NAME"));
			vo.setLast_name(rs.getString("LAST_NAME"));
			vo.setEmail(rs.getString("EMAIL"));
			vo.setPhone_number(rs.getString("PHONE_NUMBER"));
			vo.setHire_date(rs.getDate("HIRE_DATE"));
			vo.setJob_id(rs.getString("JOB_ID"));
			vo.setSalary(rs.getInt("SALARY"));
			vo.setCommission_pct(rs.getDouble("COMMISSION_PCT"));
			vo.setManager_id(rs.getInt("MANAGER_ID"));
			vo.setDepartment_id(rs.getInt("DEPARTMENT_ID"));
			data.add(vo);
		}
		conn.close();
		return data;
	}

	@Override
	public List<Employees> selectByConditions(String conditions) throws SQLException {
		Connection conn = ConnectionFactory.create();
		String sql = "SELECT * FROM EMPLOYEES " + conditions;
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		List<Employees> data = new ArrayList<>();
		while (rs.next()) {
			Employees vo = new Employees();
			vo.setEmployee_id(rs.getInt("EMPLOYEE_ID")); 
			vo.setFirst_name(rs.getString("FIRST_NAME"));
			vo.setLast_name(rs.getString("LAST_NAME"));
			vo.setEmail(rs.getString("EMAIL"));
			vo.setPhone_number(rs.getString("PHONE_NUMBER"));
			vo.setHire_date(rs.getDate("HIRE_DATE"));
			vo.setJob_id(rs.getString("JOB_ID"));
			vo.setSalary(rs.getInt("SALARY"));
			vo.setCommission_pct(rs.getDouble("COMMISSION_PCT"));
			vo.setManager_id(rs.getInt("MANAGER_ID"));
			vo.setDepartment_id(rs.getInt("DEPARTMENT_ID"));
			data.add(vo);
		}
		conn.close();
		return data;
	}
	
	public long getMax() throws SQLException {
		long max = 0;
		Connection conn = ConnectionFactory.create();
	    String sql = "SELECT MAX(EMPLOYEE_ID) FROM EMPLOYEES ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		max = rs.getLong(1);
		return max;
	}


//	//번호생성   
//	public int getMaxid2() throws SQLException {
//	      int maxNo= 0;
//	      Connection conn = ConnectionFactory.create();
//	      String sql = "SELECT MAX(EMPLOYEE_ID) FROM EMPLOYEES ";
//	      PreparedStatement pstmt = conn.prepareStatement(sql);
//	      ResultSet rs = pstmt.executeQuery(sql);
//	      try {
//	    	 rs.next();
//	         if(maxNo == 0) {
//	            maxNo = 100;
//	         }else {
//	            int i = maxNo;
//	            i = i + 1; 
//	         }
//	      }catch(SQLException se) {
//	         System.out.println("getQandaNo() query 오류: "+sql);
//	      }
//	      finally {
//	    	  conn.close();
//	      }      
//	      return maxNo;
//	   }
	
	
	public int getMaxid() throws SQLException {
		Connection conn = ConnectionFactory.create();
		String sql = "SELECT MAX(EMPLOYEE_ID) FROM EMPLOYEES ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery(sql);
		rs.next();
		int max = rs.getInt(1);
		conn.close();
		return max;
	}

}
