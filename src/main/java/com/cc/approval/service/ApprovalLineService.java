package com.cc.approval.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.approval.domain.Approval;
import com.cc.approval.domain.ApprovalLine;
import com.cc.approval.repository.ApprovalLineRepository;
import com.cc.employee.domain.Employee;
import com.cc.employee.repository.EmployeeRepository;
import com.cc.employee.service.EmployeeService;



@Service
public class ApprovalLineService {
	
	
	private final ApprovalLineRepository approvalLineRepository;	
    private final EmployeeRepository employeeRepository;
    
    @Autowired
    private EmployeeService employeeService;
    private ApprovalLineService approvalLineService;
    
    
    @Autowired
    public ApprovalLineService(ApprovalLineRepository approvalLineRepository,EmployeeRepository employeeRepository) {
    	
    	this.employeeRepository = employeeRepository;
    	this.approvalLineRepository = approvalLineRepository;
    }
	
    public boolean saveApprovalLine(Long approvalNo, String empCode, String approver1, String approver2) {
        try {
            // 기안 문서에 대한 1차 결재선 저장
            Employee approver1Employee = employeeRepository.findByEmpCode(approver1);
            ApprovalLine approvalLine1 = new ApprovalLine();
            approvalLine1.setApproval(new Approval(approvalNo)); // apprNo 설정
            approvalLine1.setEmployee(approver1Employee);
            approvalLine1.setApprOrder(1);  // 1차 결재
            approvalLine1.setApprRole("1"); // 결재 역할 (결재)
            approvalLine1.setApprState("S"); // 결재대기 상태
            approvalLineRepository.save(approvalLine1);

            // 기안 문서에 대한 2차 결재선 저장
            Employee approver2Employee = employeeRepository.findByEmpCode(approver2);
            ApprovalLine approvalLine2 = new ApprovalLine();
            approvalLine2.setApproval(new Approval(approvalNo)); // apprNo 설정
            approvalLine2.setEmployee(approver2Employee);
            approvalLine2.setApprOrder(2);  // 2차 결재
            approvalLine2.setApprRole("1"); // 결재 역할 (결재)
            approvalLine2.setApprState("S"); // 결재대기 상태
            approvalLineRepository.save(approvalLine2);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
 
}
