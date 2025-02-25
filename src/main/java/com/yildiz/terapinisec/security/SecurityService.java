package com.yildiz.terapinisec.security;

import com.yildiz.terapinisec.repository.AppointmentRepository;
import com.yildiz.terapinisec.repository.FileStorageRepository;
import com.yildiz.terapinisec.service.*;
import com.yildiz.terapinisec.util.ReportSituation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("securityService")
public class SecurityService {

    private final SurveyResponseService surveyResponseService;
    private final StoryService storyService;
    private final SleepLogService sleepLogService;
    private final SessionService sessionService;
    private final ReportService reportService;
    private final MoodLogService moodLogService;
    private final PremiumService premiumService;
    private final GoalService goalService;
    private final AppointmentRepository appointmentRepository;
    private final FileStorageRepository fileStorageRepository;

    public SecurityService(
            SurveyResponseService surveyResponseService,
            StoryService storyService,
            SleepLogService sleepLogService,
            SessionService sessionService,
            ReportService reportService,
            MoodLogService moodLogService,
            PremiumService premiumService,
            GoalService goalService,
            AppointmentRepository appointmentRepository,
            FileStorageRepository fileStorageRepository
    ) {
        this.surveyResponseService = surveyResponseService;
        this.storyService = storyService;
        this.sleepLogService = sleepLogService;
        this.sessionService = sessionService;
        this.reportService = reportService;
        this.moodLogService = moodLogService;
        this.premiumService = premiumService;
        this.goalService = goalService;
        this.appointmentRepository = appointmentRepository;
        this.fileStorageRepository = fileStorageRepository;
    }


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