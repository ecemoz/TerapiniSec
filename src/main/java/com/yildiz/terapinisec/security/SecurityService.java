package com.yildiz.terapinisec.security;

import com.yildiz.terapinisec.repository.AppointmentRepository;
import com.yildiz.terapinisec.service.*;
import com.yildiz.terapinisec.util.ReportSituation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("securityService")
public class SecurityService {

    @Autowired
    private SurveyResponseService surveyResponseService;

    @Autowired
    private StoryService storyService;

    @Autowired
    private SleepLogService sleepLogService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private MoodLogService moodLogService;

    @Autowired
    private PremiumService premiumService;

    @Autowired
    private GoalService goalService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public boolean isSelf(Long userId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getId().equals(userId);
        }
        return false;
    }

    public boolean hasUserRespondedToSurvey(Long surveyId) {
        Long currentUserId = getCurrentUserId();
        return currentUserId != null && surveyResponseService.hasUserRespondedToSurvey(surveyId, currentUserId);
    }

    private Long getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getId();
        }
        return null;
    }

    public boolean isStoryOwner(Long storyId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails) {
            Long currentUserId = ((CustomUserDetails) principal).getId();
            return storyService.isUserOwnerOfStory(currentUserId, storyId);
        }
        return false;
    }

    public boolean isSleepLogOwner(Long sleepLogId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails) {
            Long currentUserId = ((CustomUserDetails) principal).getId();
            return sleepLogService.isUserOwnerOfSleepLog(currentUserId, sleepLogId);
        }
        return false;
    }

    public boolean isSelfUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal instanceof CustomUserDetails;
    }

    public boolean isSessionParticipant(Long sessionId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails) {
            Long currentUserId = ((CustomUserDetails) principal).getId();
            return sessionService.isUserParticipantOfSession(currentUserId, sessionId);
        }
        return false;
    }

    public boolean isSessionOwner(Long sessionId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails) {
            Long currentUserId = ((CustomUserDetails) principal).getId();
            return sessionService.isUserOwnerOfSession(currentUserId, sessionId);
        }
        return false;
    }

    public boolean isSelfReport(ReportSituation reportSituation) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails) {
            Long currentUserId = ((CustomUserDetails) principal).getId();
            return reportService.isUserOwnerOfReport(currentUserId, reportSituation);
        }
        return false;
    }

    public boolean isMoodLogOwner(Long moodLogId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails) {
            Long currentUserId = ((CustomUserDetails) principal).getId();
            return moodLogService.isUserOwnerOfMoodLog(currentUserId, moodLogId);
        }
        return false;
    }


    public boolean isPremiumUser() {
        return premiumService.isPremiumUser();
    }

    public boolean isGoalOwner(Long goalId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails) {
            Long currentUserId = ((CustomUserDetails) principal).getId();
            return goalService.isUserOwnerOfGoal(currentUserId, goalId);
        }
        return false;
    }

    public boolean isAppointmentOwner(Long appointmentId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails) {
            Long currentUserId = ((CustomUserDetails) principal).getId();
            return appointmentRepository.existsByIdAndAppointmentClients_Id(appointmentId, currentUserId);
        }
        return false;
    }
}