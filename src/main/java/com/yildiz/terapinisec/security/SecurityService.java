package com.yildiz.terapinisec.security;

import com.yildiz.terapinisec.repository.AppointmentRepository;
import com.yildiz.terapinisec.repository.FileStorageRepository;
import com.yildiz.terapinisec.service.*;
import com.yildiz.terapinisec.util.ReportSituation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("securityService")
public class SecurityService {

    @Autowired private SurveyResponseService surveyResponseService;
    @Autowired private StoryService storyService;
    @Autowired private SleepLogService sleepLogService;
    @Autowired private SessionService sessionService;
    @Autowired private ReportService reportService;
    @Autowired private MoodLogService moodLogService;
    @Autowired private PremiumService premiumService;
    @Autowired private GoalService goalService;
    @Autowired private AppointmentRepository appointmentRepository;
    @Autowired private FileStorageRepository fileStorageRepository;

    public Long getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (principal instanceof CustomUserDetails) ? ((CustomUserDetails) principal).getId() : null;
    }

    public boolean isSelf(Long userId) {
        Long currentUserId = getCurrentUserId();
        return currentUserId != null && currentUserId.equals(userId);
    }

    public boolean hasUserRespondedToSurvey(Long surveyId) {
        Long currentUserId = getCurrentUserId();
        return currentUserId != null && surveyResponseService.hasUserRespondedToSurvey(surveyId, currentUserId);
    }

    public boolean isStoryOwner(Long storyId) {
        Long currentUserId = getCurrentUserId();
        return currentUserId != null && storyService.isUserOwnerOfStory(currentUserId, storyId);
    }

    public boolean isSleepLogOwner(Long sleepLogId) {
        Long currentUserId = getCurrentUserId();
        return currentUserId != null && sleepLogService.isUserOwnerOfSleepLog(currentUserId, sleepLogId);
    }

    public boolean isSessionParticipant(Long sessionId) {
        Long currentUserId = getCurrentUserId();
        return currentUserId != null && sessionService.isUserParticipantOfSession(currentUserId, sessionId);
    }

    public boolean isSessionOwner(Long sessionId) {
        Long currentUserId = getCurrentUserId();
        return currentUserId != null && sessionService.isUserOwnerOfSession(currentUserId, sessionId);
    }

    public boolean isSelfReport(ReportSituation reportSituation) {
        Long currentUserId = getCurrentUserId();
        return currentUserId != null && reportService.isUserOwnerOfReport(currentUserId, reportSituation);
    }

    public boolean isMoodLogOwner(Long moodLogId) {
        Long currentUserId = getCurrentUserId();
        return currentUserId != null && moodLogService.isUserOwnerOfMoodLog(currentUserId, moodLogId);
    }

    public boolean isPremiumUser() {
        return premiumService.isPremiumUser();
    }

    public boolean isGoalOwner(Long goalId) {
        Long currentUserId = getCurrentUserId();
        return currentUserId != null && goalService.isUserOwnerOfGoal(currentUserId, goalId);
    }

    public boolean isAppointmentOwner(Long appointmentId) {
        Long currentUserId = getCurrentUserId();
        return currentUserId != null && appointmentRepository.existsByIdAndAppointmentClients_Id(appointmentId, currentUserId);
    }

    public boolean isFileOwner(Long fileId) {
        Long currentUserId = getCurrentUserId();
        return currentUserId != null && fileStorageRepository.existsByIdAndDocumentUploaderId(fileId, currentUserId);
    }
}