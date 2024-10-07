package com.cc.approval.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.approval.domain.Approval;
import com.cc.approval.domain.ApprovalDto;
import com.cc.approval.domain.ApprovalLineDto;
import com.cc.approval.domain.TemporaryStorageDto;
import com.cc.approval.service.ApprFormService;
import com.cc.approval.service.ApprovalLineService;
import com.cc.approval.service.ApprovalService;
import com.cc.employee.service.EmployeeService;
import com.cc.notification.service.NotificationService;





@Controller
public class ApprovalApiController {
   
   private final ApprovalService approvalService;
   private final ApprFormService apprFormService;
   private final EmployeeService employeeService;
   private final ApprovalLineService approvalLineService;
   private final NotificationService  notificationService;
   @Autowired
   public ApprovalApiController(ApprovalService approvalService,
         ApprFormService apprFormService,EmployeeService employeeService,
         ApprovalLineService approvalLineService, NotificationService  notificationService) {
      this.approvalService = approvalService;
      this.apprFormService = apprFormService;
      this.employeeService = employeeService;
      this.approvalLineService = approvalLineService;
      this.notificationService = notificationService;
   }

   
   
   // 기안서 폼 생성
   @ResponseBody
   @PostMapping("/draft")
   public Map<String, String> createDraft(@RequestBody ApprovalDto dto) {
       Map<String, String> resultMap = new HashMap<>();

       try {
    	   
    	   // ApprovalDto 수신 확인
           System.out.println("Received DTO: " + dto);  // DTO 전체 확인
           if (dto == null) {
               System.out.println("Error: DTO is null");
               resultMap.put("res_code", "400");
               resultMap.put("res_msg", "DTO is null");
               return resultMap;
           }
           
           System.out.println("ApprovalLineList: " + dto.getApprovalLineList()); // ApprovalLineList 확인
           
           // 1. 임시 저장된 문서 삭제 또는 상태 변경
           if (dto.getTem_no() != null) {
               approvalService.deleteTempStorage(dto.getTem_no()); // 임시 저장 문서 삭제
           }

           // 2. 기안서 저장 (결재 테이블에 저장)
           Approval savedApproval = approvalService.saveApproval(dto);
           Long apprNo = savedApproval.getApprNo();
           dto.setAppr_no(apprNo); // 기안서 번호 설정
           System.out.println("기안서 저장 완료: " + apprNo);

           // 3. 결재선 정보가 있을 경우, 결재선 저장
           if (dto.getApprovalLineList() != null && !dto.getApprovalLineList().isEmpty()) {
        	   for (ApprovalLineDto lineDto : dto.getApprovalLineList()) {
        		    lineDto.setAppr_no(apprNo); // 결재선에 생성된 appr_no 설정
        		    System.out.println("결재선 정보 설정 완료: " + lineDto.getEmp_code());

        		    // 결재자 정보 로그 출력
        		    //System.out.println("결재선 정보: 결재자 emp_code - " + lineDto.getEmp_code() + ", 결재 순서 - " + lineDto.getAppr_order());
        		    
        		 // 결재자일 경우에만 결재 순서를 확인
                    if (lineDto.getAppr_role() == 1) {  // 결재자일 때만 appr_order 확인
                        if (lineDto.getAppr_order() == null) {
                            System.out.println("Error: 결재 순서(appr_order)가 설정되지 않았습니다.");
                        } else {
                            System.out.println("결재선 정보: 결재자 emp_code - " + lineDto.getEmp_code() + ", 결재 순서 - " + lineDto.getAppr_order());
                        }
                    } else if (lineDto.getAppr_role() == 2) {  // 참조자의 경우 appr_order는 3으로 설정
                    	// 참조 순서에 따라 1차는 3, 2차는 4로 설정
                        if (lineDto.getAppr_order() == null) {
                            lineDto.setAppr_order(3);  // 기본값으로 1차 참조자
                        } else if (lineDto.getAppr_order() == 4) {
                            lineDto.setAppr_order(4);  // 2차 참조자
                        }
                        System.out.println("참조자 정보: emp_code - " + lineDto.getEmp_code() + ", 참조 순서 설정: " + lineDto.getAppr_order());
                    }

                    approvalService.saveApprovalLine(lineDto);  // 결재선 저장
                    System.out.println("ApprovalLine saved for Approval No: " + apprNo + " with emp_code: " + lineDto.getEmp_code());

        		    // 1차 승인자에게만 알림 전송
        		    if (lineDto.getAppr_order() == 1) {
        		        try {
        		            notificationService.sendApprovalRequestNotification(lineDto.getEmp_code(),
        		                "[결재 요청] 결재 요청이 등록되었습니다.", apprNo);
        		            System.out.println("알림 전송 성공: " + lineDto.getEmp_code());
        		        } catch (Exception e) {
        		            System.out.println("알림 전송 실패: " + lineDto.getEmp_code());
        		            e.printStackTrace();
        		        }
        		    }
        		}
           } else {
               System.out.println("결재선 정보가 없습니다.");
           }

           resultMap.put("res_code", "200");
           resultMap.put("res_msg", "결재요청이 완료되었습니다.");
       } catch (Exception e) {
    	   System.out.println("오류 발생: " + e.getMessage());  // 예외 메시지 출력
           e.printStackTrace();  // 스택 트레이스 출력
           resultMap.put("res_code", "404");
           resultMap.put("res_msg", "결재요청 중 오류가 발생했습니다: " + e.getMessage());
       }

       return resultMap;
   }
   
   
   
   // 기안서 임시저장
         @ResponseBody
         @PostMapping("/apprSave")
         public Map<String,String> updateAppr(@RequestBody TemporaryStorageDto dto){
            Map<String, String> resultMap = new HashMap<String, String>();
            resultMap.put("res_code", "404");
            resultMap.put("res_msg","임시저장 중 오류가 발생했습니다.");
            
            // DTO에 appr_form_no 설정
             //dto.setAppr_form_no(apprFormNo);
            
            // 서비스에서 로그인된 사용자의 empCode(사원 코드)를 가져와 DTO에 설정
             if (approvalService.updateApprWithEmpCode(dto)) {
                 resultMap.put("res_code","200");
                 resultMap.put("res_msg", "임시저장 되었습니다.");
             }
             
            return resultMap;
            
            
         
         }
   
   // 기안서 임시저장에서 삭제
   @ResponseBody
   @DeleteMapping("/deleteTemp/{tem_no}")
   public Map<String,String> deleteTempStorage(@PathVariable("tem_no") Long tem_no){
      
      
      Map<String,String> map = new HashMap<String,String>();
      map.put("res_code", "404");
      map.put("res_msg","삭제 중 오류가 발생했습니다.");
      
      if(approvalService.deleteTempStorage(tem_no) > 0) {
         map.put("res_code", "200");
         map.put("res_msg", "정상적으로 삭제 되었습니다.");
      }
      return map;
   }
   
   
   // 전자 서명 설정
   @PostMapping("/uploadSignature")
   public ResponseEntity<?> uploadSignature(@RequestBody Map<String, String> data) {
       // Base64 데이터 추출
       String base64Image = data.get("image").split(",")[1];
       byte[] decodedBytes = Base64.getDecoder().decode(base64Image);

       // 현재 로그인된 사용자 정보 가져오기
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String empAccount;

       if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
           UserDetails userDetails = (UserDetails) authentication.getPrincipal();
           empAccount = userDetails.getUsername(); // 로그인된 사용자의 empAccount
       } else {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 정보가 없습니다.");
       }

       // 사용자별로 고유한 파일명 생성
       String directoryPath = "C:/approval/upload/";
       String fileName = empAccount + "_signature.png";  // 파일명
       String filePath = directoryPath + fileName;

       // 디렉토리 확인 및 생성
       File directory = new File(directoryPath);
       if (!directory.exists()) {
           directory.mkdirs();  // 경로가 없다면 디렉토리 생성
       }

       // 파일 저장
       try (OutputStream stream = new FileOutputStream(filePath)) {
           stream.write(decodedBytes);
       } catch (IOException e) {
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서명 저장에 실패했습니다.");
       }

       // 실제 서버에서 접근 가능한 경로로 변환하여 데이터베이스에 저장
       String webPath = "/upload/" + fileName;  // 웹 경로

       // 서비스로 empAccount와 웹 경로 전달
       boolean isUpdated = employeeService.updateEmployeeSignatureByAccount(empAccount, webPath);
       if (!isUpdated) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사원을 찾을 수 없습니다.");
       }

       return ResponseEntity.ok("서명이 성공적으로 저장되었습니다.");
   }

   
   
      // 결재 승인
   @ResponseBody
   @PostMapping("/approve")
   public String approve(@RequestParam("apprNo") Long apprNo, @RequestParam("apprOrder") Integer apprOrder) throws Exception {
	   if (apprOrder == null) {
	        return "오류: 결재 순서가 지정되지 않았습니다.";
	    }
       // 결재 상태를 업데이트하는 비즈니스 로직 호출
       approvalService.approveDocument(apprNo, apprOrder);

       // 결재 승인에 대한 알림 내용 설정
       String notificationMessage;
       if (apprOrder == 1) {
           notificationMessage = "1차 승인이 완료되었습니다.";
       } else if (apprOrder == 2) {
           notificationMessage = "2차 승인이 완료되었습니다.";
       } else {
           notificationMessage = "결재 승인이 완료되었습니다.";
       }

       // 결재 작성자의 empCode 조회 (Long 타입)
       Long writerEmpCode = approvalService.getApprWriterCode(apprNo);

       // 1차 승인 이후 2차 승인자에게 결재 요청 알림 전송
       if (apprOrder == 1) {
           Long secondApproverCode = approvalService.getSecondApproverCode(apprNo); // 2차 승인자의 empCode를 가져옴
           if (secondApproverCode != null) {
               notificationService.sendApprovalRequestNotification(secondApproverCode,
                   "[결재 요청] 1차 승인이 완료되었습니다. 2차 승인을 요청합니다.", apprNo);
               System.out.println("2차 승인자에게 알림 전송: " + secondApproverCode);
           }
       }

       // 결재 작성자에게 승인 알림 전송
       try {
           notificationService.sendApprovalCompletionNotification(writerEmpCode, notificationMessage, apprNo);
           System.out.println("writerEmpCode : " + writerEmpCode);
           System.out.println("notificationMessage : " + notificationMessage);
           System.out.println("apprNo : " + apprNo);
       } catch (Exception e) {
           e.printStackTrace();
       }

       return "approval/approvalHome";
   }

      // 반려
      @PostMapping("/reject")
      public ResponseEntity<String> rejectApproval(@RequestParam("apprNo") Long apprNo,
                                                   @RequestParam("rejectReason") String rejectReason) {
          try {
              approvalService.rejectApproval(apprNo, rejectReason);
              return ResponseEntity.ok("반려 처리가 완료되었습니다.");
          } catch (Exception e) {
              return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("반려 처리 중 오류가 발생했습니다.");
          }
      }

      
   

}



