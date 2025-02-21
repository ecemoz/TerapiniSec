package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.dto.ReportResponseDto;
import com.yildiz.terapinisec.mapper.ReportMapper;
import com.yildiz.terapinisec.model.MoodLog;
import com.yildiz.terapinisec.model.Report;
import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.repository.*;
import com.yildiz.terapinisec.util.ReportSituation;
import com.yildiz.terapinisec.util.SleepQuality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MoodLogRepository moodLogRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private SleepLogRepository sleepLogRepository;

    @Autowired
    private ReportMapper reportMapper;

    public ReportResponseDto generatePersonalizedWeeklyReport(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String content = generateWeeklyContent(user);
        Report report = createReport(userId, content, ReportSituation.WEEKLY);
        return reportMapper.toReportResponseDto(report);
    }

    public ReportResponseDto generatePersonalizedMonthlyReport(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String content = generateMonthlyContent(user);
        Report report = createReport(userId, content, ReportSituation.MONTHLY);
        return reportMapper.toReportResponseDto(report);
    }

    private Report createReport(Long userId, String content, ReportSituation situation) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Report report = new Report();
        report.setContent(content);
        report.setReportOwner(user);
        report.setReportSituation(situation);

        return reportRepository.save(report);
    }

    private String generateWeeklyContent(User user) {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);

        String moodAnalysis = analyzeMoodTrend(user, oneWeekAgo, LocalDateTime.now());
        String taskAnalysis = analyzeTaskPerformance(user, oneWeekAgo, LocalDateTime.now());
        String goalAnalysis = analyzeGoalCompletion(user, oneWeekAgo, LocalDateTime.now());
        String sleepAnalysis = analyzeSleepQuality(user, oneWeekAgo, LocalDateTime.now());
        String suggestions = generateSuggestions(user, oneWeekAgo, LocalDateTime.now());

        return String.format("Weekly Report:\n%s\n%s\n%s\n%s\n%s",
                moodAnalysis, taskAnalysis, goalAnalysis, sleepAnalysis, suggestions);
    }

    private String generateMonthlyContent(User user) {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);

        String moodAnalysis = analyzeMoodTrend(user, oneMonthAgo, LocalDateTime.now());
        String taskAnalysis = analyzeTaskPerformance(user, oneMonthAgo, LocalDateTime.now());
        String goalAnalysis = analyzeGoalCompletion(user, oneMonthAgo, LocalDateTime.now());
        String sleepAnalysis = analyzeSleepQuality(user, oneMonthAgo, LocalDateTime.now());
        String suggestions = generateSuggestions(user, oneMonthAgo, LocalDateTime.now());

        return String.format("Monthly Report:\n%s\n%s\n%s\n%s\n%s",
                moodAnalysis, taskAnalysis, goalAnalysis, sleepAnalysis, suggestions);
    }

    private String analyzeMoodTrend(User user, LocalDateTime startDate, LocalDateTime endDate) {
        List<MoodLog> moodLogs = moodLogRepository.findMoodSummaryByUserAndDateRange(user.getId(), startDate, endDate);

        long positiveCount = moodLogs.stream()
                .filter(moodLog -> "Positive".equals(moodLog.getUserMoods()))
                .count();
        long negativeCount = moodLogs.stream()
                .filter(moodLog -> "Negative".equals(moodLog.getUserMoods()))
                .count();

        if (positiveCount > negativeCount) {
            return "Mood: Positive trend observed.";
        } else if (negativeCount > positiveCount) {
            return "Mood: Negative trend observed.";
        } else {
            return "Mood: Stable.";
        }
    }

    private String analyzeTaskPerformance(User user, LocalDateTime startDate, LocalDateTime endDate) {
        long completedTasks = taskRepository.countCompletedTasksByUserAndDateRange(user.getId(), startDate, endDate);
        long totalTasks = taskRepository.countTasksByUserAndDateRange(user.getId(), startDate, endDate);

        if (totalTasks == 0) {
            return "Task Completion: No tasks found.";
        }
        return String.format("Task Completion: %d/%d tasks completed (%d%%).",
                completedTasks, totalTasks, (completedTasks * 100 / totalTasks));
    }

    private String analyzeGoalCompletion(User user, LocalDateTime startDate, LocalDateTime endDate) {
        long completedGoals = goalRepository.countCompletedGoalsByUserAndDateRange(user.getId(), startDate, endDate);
        long totalGoals = goalRepository.countGoalsByUserAndDateRange(user.getId(), startDate, endDate);

        if (totalGoals == 0) {
            return "Goal Completion: No goals found.";
        }
        return String.format("Goal Completion: %d/%d goals completed.", completedGoals, totalGoals);
    }

    private String analyzeSleepQuality(User user, LocalDateTime startDate, LocalDateTime endDate) {
        Optional<Double> averageSleepQualityValue = sleepLogRepository.findAverageSleepQualityByUserAndDateRange(user.getId(), startDate, endDate);

        if (averageSleepQualityValue.isEmpty()) {
            return "Sleep Quality: No data available.";
        }

        SleepQuality averageSleepQuality = SleepQuality.fromValue(averageSleepQualityValue.get().intValue());

        switch (averageSleepQuality) {
            case VERY_POOR:
                return "Sleep Quality: Very Poor.";
            case POOR:
                return "Sleep Quality: Poor.";
            case AVERAGE:
                return "Sleep Quality: Average.";
            case GOOD:
                return "Sleep Quality: Good.";
            case EXCELLENT:
                return "Sleep Quality: Excellent.";
            default:
                return "Sleep Quality: Unknown.";
        }
    }

    private String generateSuggestions(User user, LocalDateTime startDate, LocalDateTime endDate) {
        StringBuilder suggestions = new StringBuilder("Suggestions:");

        Optional<Double> sleepQualityValue = sleepLogRepository.findAverageSleepQualityByUserAndDateRange(user.getId(), startDate, endDate);
        if (sleepQualityValue.isPresent() && sleepQualityValue.get() <= 2) {
            suggestions.append(" Improve your sleep schedule.");
        }

        long completedTasks = taskRepository.countCompletedTasksByUserAndDateRange(user.getId(), startDate, endDate);
        long totalTasks = taskRepository.countTasksByUserAndDateRange(user.getId(), startDate, endDate);

        if (completedTasks < totalTasks / 2) {
            suggestions.append(" Focus on smaller, achievable tasks.");
        }

        List<MoodLog> moodLogs = moodLogRepository.findMoodSummaryByUserAndDateRange(user.getId(), startDate, endDate);
        long negativeCount = moodLogs.stream()
                .filter(moodLog -> "Negative".equals(moodLog.getUserMoods()))
                .count();

        if (negativeCount > 3) {
            suggestions.append(" Consider mindfulness practices to reduce stress.");
        }

        return suggestions.toString();
    }

    public ReportResponseDto findByReportType(ReportSituation reportSituation) {
        Report report = reportRepository.findByReportSituation(reportSituation)
                .orElseThrow(() -> new RuntimeException("Report not found for type: " + reportSituation));
        return reportMapper.toReportResponseDto(report);
    }

    public ReportResponseDto findByReportOwnerId(Long userId) {
        Report report = reportRepository.findByReportOwnerId(userId)
                .orElseThrow(() -> new RuntimeException("Report not found for user: " + userId));
        return reportMapper.toReportResponseDto(report);
    }

    public ReportResponseDto findByReportOwnerIdAndReportType(Long userId, ReportSituation reportSituation) {
        Report report = reportRepository.findByReportOwnerIdAndReportSituation(userId, reportSituation)
                .orElseThrow(() -> new RuntimeException("Report not found for user: " + userId + " and type: " + reportSituation));
        return reportMapper.toReportResponseDto(report);
    }

    public List<ReportResponseDto> findByReportCreatedAt(LocalDateTime reportCreatedAt) {
        List<Report> reports = reportRepository.findByReportCreatedAt(reportCreatedAt);
        if (reports.isEmpty()) {
            throw new RuntimeException("Reports not found for date: " + reportCreatedAt);
        }
        return reportMapper.toReportResponseDtoList(reports);
    }

    public boolean isUserOwnerOfReport(Long userId, ReportSituation reportSituation) {
        return reportRepository.existsByReportOwnerIdAndReportSituation(userId, reportSituation);
    }
}