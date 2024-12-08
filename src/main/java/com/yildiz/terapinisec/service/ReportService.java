package com.yildiz.terapinisec.service;

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

    public Report generatePersonalizedWeeklyReport(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        String content = generateWeeklyContent(user);
        return createReport(userId, content, ReportSituation.WEEKLY);
    }

    public Report generatePersonalizedMonthlyReport(Long userId) {
        User user =userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        String content = generateMonthlyContent(user);
        return createReport(userId, content, ReportSituation.MONTHLY);
    }

    private Report createReport(Long userId, String content, ReportSituation situation) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

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

        return String.format("Weekly Report :\n%s\n%s\n%s\n%s\n%s",
                moodAnalysis, taskAnalysis, goalAnalysis, sleepAnalysis, suggestions);
    }

    private String generateMonthlyContent(User user) {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);

        String moodAnalysis = analyzeMoodTrend(user, oneMonthAgo, LocalDateTime.now());
        String taskAnalysis = analyzeTaskPerformance(user, oneMonthAgo, LocalDateTime.now());
        String goalAnalysis = analyzeGoalCompletion(user, oneMonthAgo, LocalDateTime.now());
        String sleepAnalysis = analyzeSleepQuality(user, oneMonthAgo, LocalDateTime.now());
        String suggestions = generateSuggestions(user, oneMonthAgo, LocalDateTime.now());

        return String.format("Weekly Report :\n%s\n%s\n%s\n%s\n%s",
                moodAnalysis, taskAnalysis, goalAnalysis, sleepAnalysis, suggestions);
    }

    private String analyzeMoodTrend(User user, LocalDateTime startDate, LocalDateTime endDate) {
        List<MoodLog> moodLogs = moodLogRepository.findMoodSummaryByUserAndDateRange(user.getId(), startDate, endDate);

        long positiveCount = moodLogs.stream()
                .filter(moodLog -> moodLog.getUserMoods().equals("Positive"))
                .count();
        long negativeCount = moodLogs.stream()
                .filter(moodLog -> moodLog.getUserMoods().equals("Negative"))
                .count();

        if (positiveCount > negativeCount) {
            return "Mood : Positive increase." ;
        } else if (negativeCount > positiveCount) {
            return "Mood : Negative decrease." ;
        } else {
            return "Mood : Stabil.";
        }
    }

    private String analyzeTaskPerformance(User user, LocalDateTime startDate, LocalDateTime endDate) {
        long completedTasks = taskRepository.countCompletedTasksByUserAndDateRange(user.getId(), startDate, endDate);
        long totalTasks = taskRepository.countTasksByUserAndDateRange(user.getId(), startDate, endDate);

        if (totalTasks == 0) {
            return "Task Complete: No task determined." ;
        }
        return String.format("Task complete: %d/%d task completed(%d%%)" ,
                completedTasks, totalTasks,(completedTasks*100 / totalTasks));
    }

    private String analyzeGoalCompletion(User user, LocalDateTime startDate, LocalDateTime endDate) {
        long completedGoals = goalRepository.countCompletedGoalsByUserAndDateRange(user.getId(),startDate, endDate);
        long totalGoals = goalRepository.countGoalsByUserAndDateRange(user.getId(), startDate, endDate);

        if (totalGoals == 0) {
            return "Goal Complete: No goal determined." ;
        }
        return String.format("Goal Complete: %d/%d goal completed.",completedGoals, totalGoals);
    }

    private String analyzeSleepQuality(User user, LocalDateTime startDate, LocalDateTime endDate) {
        Optional<SleepQuality> optionalSleepQuality = sleepLogRepository.findAverageSleepQualityByUserAndDateRange(user.getId(),startDate,endDate);

        if (optionalSleepQuality.isEmpty()) {
            return "Sleep Quality Average: No average sleep quality determined." ;
        }

        SleepQuality sleepQuality = optionalSleepQuality.get();

        switch (sleepQuality) {
            case LOW:
                return "Sleep Quality Low";
                case MEDIUM:
                    return "Sleep Quality Good.";
                    case HIGH:
                        return "Sleep Quality High";
                        default:
                            return "Sleep Quality Unknown";
        }
    }

    private String generateSuggestions(User user, LocalDateTime startDate, LocalDateTime endDate) {
        StringBuilder suggestions = new StringBuilder(" Suggestions: ");

        String sleepQuality = sleepLogRepository.findAverageSleepQualityByUserAndDateRange(user.getId(), startDate, endDate );
        if ( SleepQuality.LOW.equals(sleepQuality) ) {
            suggestions.append(" Improve your sleeping pattern.");
        }

        long completedTasks = taskRepository.countCompletedTasksByUserAndDateRange(user.getId(), startDate, endDate);
        long totalTasks = taskRepository.countTasksByUserAndDateRange(user.getId(), startDate, endDate);

        if (completedTasks < totalTasks /2) {
            suggestions.append("Determine smaller goals to increase your task complete. ");
        }

        List<MoodLog> moodLogs = moodLogRepository.findMoodSummaryByUserAndDateRange(user.getId(), startDate, endDate);
        long negativeCount = moodLogs.stream()
                .filter(moodLog -> moodLog.getUserMoods().equals("Negative"))
                .count();
        if (negativeCount > 3) {
            suggestions.append(" Do meditation to reduce  your stress");
        }

        return suggestions.toString();
    }
}