package service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.*;

import dao.DepartmentsDAO;
import dao.EmployeesDAO;
import dao.JobHistoryDAO;
import dao.JobsDAO;
import vo.Employees;

public class HRService {
	EmployeesDAO edao; 
	DepartmentsDAO ddao;
	JobHistoryDAO jhdao;
	JobsDAO jdao;
	
	public HRService() {
		this.edao = new EmployeesDAO();
		this.ddao = new DepartmentsDAO();
		this.jhdao = new JobHistoryDAO();
		this.jdao = new JobsDAO();
	}
//	1. 주어진 기간의 입사자 목록찾기 
	public List<Employees> getEmpListByHireDate(Date a,Date b){
		List<Employees> result = null;
		try {
			Predicate<Employees> bt= m->{
				Date x = m.getHire_date();
				return (x.after(a) && x.before(b))
						|| x.equals(a) || x.equals(b);
			};
			result = (List<Employees>) edao.selectAll().stream().filter(bt).collect(Collectors.toList());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
//	 2. 근무부서별 직원 목록 
	public List<Employees> getEmpListByDep(int depId){
		List<Employees> result = null;
		try {
			Predicate<Employees> bt= m->{
				return m.getDepartment_id()==depId;
			};
			result = (List<Employees>) edao.selectAll().stream().filter(bt).collect(Collectors.toList());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

//	3. 많이 받는 급여순으로 본 직원 목록
	public List<Employees> getEmpListOrderbyPay(){
		List<Employees> result = null;
		try {
			result = (List<Employees>) edao.selectAll().stream().sorted(
					(em,em2) ->em2.getSalary() - em.getSalary()
					).collect(Collectors.toList());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
// 4. 커미션율 높은 순으로 본 직원 목록 
	public List<Employees> getComMoreThan(double com){
		List<Employees> result = null;
		try {
			result = (List<Employees>) edao.selectAll().stream().filter(t->t.getCommission_pct() == com)
					.collect(Collectors.toList());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	

// 5. 연봉이 ~이상인 사람 출력하기
	public List<Employees> getSalaryMortThan(int a){
		List<Employees> result = null;
		try {
			result = (List<Employees>) edao.selectAll().stream().filter(t->t.getSalary() >= a)
					.collect(Collectors.toList());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

// 5. 연봉이 5000이상인 사람 출력하기
//	public List<Employees> getSalaryMortThan(){
//		List<Employees> result = null;
//		try {
//			result = (List<Employees>) edao.selectAll().stream().filter(t->t.getSalary() >= 5000)
//					.collect(Collectors.toList());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
	
// 6. 이메일로 직원 찾기 
	public List<Employees> getFindByEmail(String email){
		List<Employees> result = null;
		try {
			result = (List<Employees>) edao.selectAll().stream().filter(t->t.getEmail().equalsIgnoreCase(email))
					.collect(Collectors.toList());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
// 	7. 전화번호로 직원 찾기
	public List<Employees> getFindByPhone(String phone){
		List<Employees> result = null;
		try {
			result = (List<Employees>) edao.selectAll().stream().filter(t->t.getPhone_number().equals(phone))
					.collect(Collectors.toList());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
//	8. 성으로 직원들 찾기
	public List<Employees> getFindByLastname(String name){
		List<Employees> result = null;
		try {
			result = (List<Employees>) edao.selectAll().stream().filter(t->t.getLast_name().equalsIgnoreCase(name))
					.collect(Collectors.toList());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
//	9.직업별 직원 목록
	public List<Employees> getEmpListByJob(String jobId){
		List<Employees> result = null;
		try {
			Predicate<Employees> bt= m->{
				return m.getJob_id().equals(jobId);
			};
			result = (List<Employees>) edao.selectAll().stream().filter(bt).collect(Collectors.toList());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}	

	public static void main(String[] args) {
		new HRService().test();
	}
	
	public void test() {
		//1 테스트 2005년도 입사직원 목록 
		Date a = new Date(105,0,1);
		Date b = new Date(105,11,31);
		getEmpListByHireDate(a,b).stream().forEach(e->System.out.println(e+":"+e.getHire_date()));
	}


}
