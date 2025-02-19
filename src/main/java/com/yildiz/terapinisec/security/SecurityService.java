package com.yildiz.terapinisec.security;

import com.yildiz.terapinisec.service.SurveyResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("securityService")
public class SecurityService {

    @Autowired
    private SurveyResponseService surveyResponseService;

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
}