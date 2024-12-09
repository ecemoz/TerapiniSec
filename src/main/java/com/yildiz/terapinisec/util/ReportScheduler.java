package com.yildiz.terapinisec.util;

import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.repository.UserRepository;
import com.yildiz.terapinisec.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReportScheduler {

    private final ReportService reportService;
    private final UserRepository userRepository;

   @Scheduled(cron = "59 59 23 * * SUN")
    public void generateWeeklyReports() {
       List<User> users = userRepository.findAll();
       users.forEach(user -> {
           try {
               reportService.generatePersonalizedWeeklyReport(user.getId());
               System.out.println("Weekly Report generated for user : " + user.getId());
           } catch (Exception e) {
               System.err.println("Failed to generate weekly Report for user : " + user.getId());
               e.printStackTrace();
           }
       });
   }

   @Scheduled(cron = "0 0 0 1 * ?")
    public void generateMonthlyReports() {
       List<User> users = userRepository.findAll();
       users.forEach(user -> {
           try {
               reportService.generatePersonalizedMonthlyReport(user.getId());
               System.out.println("Monthly Report generated for user : " + user.getId());
           } catch (Exception e) {
               System.err.println("Failed to generate monthly Report for user : " + user.getId());
               e.printStackTrace();
           }
       });
   }
}