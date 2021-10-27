package application;

import java.awt.HeadlessException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import common.ConnectionFactory;
import common.TableViewFactory;
import dao.DepartmentsDAO;
import dao.EmployeesDAO;
import dao.JobHistoryDAO;
import dao.JobsDAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import service.HRService;
import vo.Departments;
import vo.Employees;
import vo.Jobs;

public class RootController implements Initializable {
	private String job_id;
	private int department_id;
	private int manager_id;
	// 추가필드
	DepartmentsDAO departdao = new DepartmentsDAO();
	EmployeesDAO employeesdao = new EmployeesDAO();
	JobHistoryDAO jobhistorydao = new JobHistoryDAO();
	JobsDAO jobsdao = new JobsDAO();
	
	HRService service = new HRService();
	TableView table = TableViewFactory.getTable(Employees.class);
	
	@FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtHire_date;

    @FXML
    private TextField txtSalary;

    @FXML
    private ComboBox<Jobs> comboJob;

    @FXML
    private ComboBox<Employees> comboManager;

    @FXML
    private ComboBox<Departments> comboDepart;

    @FXML
    private TextField txtCommission;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSelectByConditions;

    @FXML
    private Button btnSelectAll;

    @FXML
    private Button btnClear;

    @FXML
    private BorderPane contentPanel;

	@FXML
    private TextField txtStart;

    @FXML
    private TextField txtEnd;

    @FXML
    private Button btnTermHireList;
    
    @FXML
    private TextField txtSalary2;
    
    @FXML
    private TextField txtEmail2;
    
    @FXML
    private TextField txtPhone2;

    @FXML
    private Button btnFindByLastName;

    @FXML
    private TextField txtLastName2;

    @FXML
    private TextField txtCom2;

    
//  급여순으로 보기 컨트롤 
    @FXML
    private Button btnOrderByPay;
   
    @FXML
    void orderByPay(ActionEvent event) {
    	List<Employees> result = service.getEmpListOrderbyPay();
    	clear(new ActionEvent());
		table.getItems().addAll(result);
    }
    
 // 부서별 직원
    @FXML
    private ComboBox<Departments> comboDepart_2;
    
//  직업별 직원
    @FXML
    private ComboBox<Jobs> comboJob2;
    
//  커미션율순으로 직원 보기
    @FXML
    private Button btnOrderByCom;

//  연봉 5000이상인 직원 보기
    @FXML
    private Button btnSalaryMorethan;

//  이메일 직원 찾기    
    @FXML
    private Button btnFindByEmail;
    
    @FXML
    void clear(ActionEvent event) {
        txtEmployeeId.setText(null);
        txtFirstName.setText(null);
        txtLastName.setText(null);
        txtEmail.setText(null);
        txtPhone.setText(null);
        txtHire_date.setText(null);
//      comboJob.getSelectionModel().clearSelection();
        comboJob.setValue(null);
        txtSalary.setText(null);
        txtCommission.setText(null);
        comboManager.setValue(null);
//      comboDepart.getSelectionModel().clearSelection();
        comboDepart.setValue(null);
        
        table.getItems().clear();
    }

    @FXML
	void delete(ActionEvent event) {
		int selNum = table.getSelectionModel().getSelectedIndex();
		Employees emp = (Employees) table.getSelectionModel().getSelectedItem();
		int empId = emp.getEmployee_id();
		try {
			// db지우기 
			employeesdao.delete(empId);
			// ui지우기 
			table.getItems().remove(selNum);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    @FXML
    void insert(ActionEvent event) {
    	try {
			employeesdao.insert(getVo());//DB에 반영 
			int id = employeesdao.getMaxid();
			System.out.println("id:"+id);
			Employees vo = employeesdao.select(id);
			table.getItems().add(vo); // ui에 반영 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    public Date str2Date(String x) {// x type 2021-1-12
		String strs[] = x.split("-");
		if(strs[1].charAt(0)=='0') strs[1] = strs[1].replace("0", "");
		if(strs[2].charAt(0)=='0') strs[2] = strs[2].replace("0", "");
		
		int year = Integer.parseInt(strs[0])-1900;
		int month = Integer.parseInt(strs[1])-1;
		int day = Integer.parseInt(strs[2]);
		return new Date(year,month,day);
	}
    
    private Employees getVo() {
        Employees vo = new Employees();
 	       vo.setFirst_name(txtFirstName.getText());
 	       vo.setLast_name(txtLastName.getText());
 	       vo.setEmail(txtEmail.getText());
 	       vo.setPhone_number(txtPhone.getText());
 	       vo.setHire_date(str2Date(txtHire_date.getText()));
 	       Jobs x = comboJob.getSelectionModel().getSelectedItem();
 	       job_id = x.getJob_id();
 	       vo.setJob_id(job_id);
 	       
 	       vo.setSalary(Integer.parseInt(txtSalary.getText()));
 	       vo.setCommission_pct(Double.parseDouble(txtCommission.getText()));

 	       vo.setManager_id(comboManager.getSelectionModel().getSelectedItem().getEmployee_id());
 	       
 	       Departments x2 = comboDepart.getSelectionModel().getSelectedItem();
 	       department_id = x2.getDepartment_id();
 	       vo.setDepartment_id(department_id);
 	       
        return vo;
     }
    
    
	@FXML
	void selectAll(ActionEvent event) {
	    try {
			table.getItems().clear();
			table.getItems().addAll(employeesdao.selectAll());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void selectByConditions(ActionEvent event) {
		String conditions = 
				JOptionPane.showInputDialog("WHERE 포함한 조건");
		try {
			List<Employees> data = employeesdao.selectByConditions(conditions);
			table.getItems().clear();
			table.getItems().addAll(data);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
    
  @FXML
  void update(ActionEvent event) {
  	try {
  		Employees vo = getVo();
		vo.setEmployee_id(Integer.parseInt(txtEmployeeId.getText()));
		employeesdao.update(vo); //DB에 반영
		int index = table.getSelectionModel().getSelectedIndex();
		table.getItems().set(index, vo); //Map으로 UI에 반영
  	} catch (SQLException e) {
			e.printStackTrace();
		} 
  }

  
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			employeesdao = new EmployeesDAO();
			departdao = new DepartmentsDAO();
			jobsdao = new JobsDAO();
			
			comboJob.getItems().addAll(jobsdao.selectAll());
			comboJob2.getItems().addAll(jobsdao.selectAll());
			comboDepart.getItems().addAll(departdao.selectAll());
			comboDepart_2.getItems().addAll(departdao.selectAll());
			comboManager.getItems().addAll(employeesdao.selectAll());
			
			setTable();
			table.getItems().addAll(employeesdao.selectAll());
			
		
			comboDepart_2.valueProperty().addListener(new ChangeListener<Departments>() {
				@Override
				public void changed(ObservableValue<? extends Departments> observable, Departments oldValue,
						Departments newValue) {
					List<Employees> result = service.getEmpListByDep(newValue.getDepartment_id());
					clear(new ActionEvent());
					table.getItems().addAll(result);
				}
			});
			comboJob2.valueProperty().addListener(new ChangeListener<Jobs>() {
				@Override
				public void changed(ObservableValue<? extends Jobs> observable, Jobs oldValue, Jobs newValue) {
					List<Employees> result = service.getEmpListByJob(newValue.getJob_id());
					clear(new ActionEvent());
					table.getItems().addAll(result);
				}
			});
		}catch (SQLException e) {
	         e.printStackTrace();
		}
	}
	

 public void setTable() throws SQLException{

	 	table.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               try {
            	   
		            Employees selected =  (Employees) table.getSelectionModel().getSelectedItem();
		            txtEmployeeId.setText(selected.getEmployee_id()+"");
		            txtFirstName.setText(selected.getFirst_name());
		            txtLastName.setText(selected.getLast_name());
					txtEmail.setText(selected.getEmail());
					txtPhone.setText(selected.getPhone_number());
					txtHire_date.setText(selected.getHire_date().toString());
		            comboJob.getSelectionModel().select(jobsdao.select(selected.getJob_id()));
					txtSalary.setText(selected.getSalary()+"");
					txtCommission.setText(selected.getCommission_pct()+"");
					comboManager.getSelectionModel().select(employeesdao.select(selected.getManager_id()));
					comboDepart.getSelectionModel().select(departdao.select(selected.getDepartment_id()));
               } catch (NumberFormatException e) {
                  e.printStackTrace();
                  return;
               } 
               catch (SQLException e) {
                  e.printStackTrace();
                  return;
               }
            }
         });
         contentPanel.setCenter(table);
      }
 
	 @FXML
	 void orderByCom(ActionEvent event) {
		String com = txtCom2.getText();
		List<Employees> result = service.getComMoreThan(Double.parseDouble(com));
	    clear(new ActionEvent());
		table.getItems().addAll(result);
	 }
	 
//	이멜주소로 찾기
	 @FXML
	 void findByEmail(ActionEvent event) {
		 	String email = txtEmail2.getText();
			List<Employees> result = service.getFindByEmail(email);
	    	clear(new ActionEvent());
			table.getItems().addAll(result);
	 }
	 
//	성으로 찾기
    @FXML
    void findByLastName(ActionEvent event) {
    	String name = txtLastName2.getText();
		List<Employees> result = service.getFindByLastname(name);
    	clear(new ActionEvent());
		table.getItems().addAll(result);
    }

//	전화번호로 찾기    
    @FXML
    void findByPhone(ActionEvent event) {
    	String phone = txtPhone2.getText();
		List<Employees> result = service.getFindByPhone(phone);
    	clear(new ActionEvent());
		table.getItems().addAll(result);
    }
	 
//	연봉이 ~이상인 사람 보기
    @FXML
    void salaryMorethan(ActionEvent event){
    		String x = txtSalary2.getText();
		  	List<Employees> result = service.getSalaryMortThan(Integer.parseInt(x));
	    	clear(new ActionEvent());
			table.getItems().addAll(result);
	 }
	 
//   주어진 기간별(입사 퇴사)로 직원 보기
	 @FXML
	 void termHireList(ActionEvent event) {
	 	Date a = str2Date(txtStart.getText());
	 	Date b = str2Date(txtEnd.getText());
	 	List<Employees> result = service.getEmpListByHireDate(a, b);
	 	clear(new ActionEvent());
	 	table.getItems().addAll(result);
	 }
}
