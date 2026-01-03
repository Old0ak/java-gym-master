package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, List<TrainingSession>> dayTrainings = timetable.get(dayOfWeek);
        if (Objects.isNull(dayTrainings)) {
            dayTrainings = new TreeMap<>();
            timetable.put(dayOfWeek, dayTrainings);
        }

        List<TrainingSession> trainingSessions = dayTrainings.get(timeOfDay);
        if (Objects.isNull(trainingSessions)) {
            trainingSessions = new ArrayList<>();
            dayTrainings.put(timeOfDay, trainingSessions);
        }

        trainingSessions.add(trainingSession);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, List<TrainingSession>> trainings = timetable.get(dayOfWeek);
        return Objects.nonNull(trainings) ? trainings : new TreeMap<>();
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> dayTrainings = timetable.get(dayOfWeek);
        if (Objects.isNull(dayTrainings)) {
            return Collections.emptyList();
        }

        List<TrainingSession> timeTrainings = dayTrainings.get(timeOfDay);
        return Objects.nonNull(timeTrainings) ? timeTrainings : Collections.emptyList();
    }

    public Map<Coach, Integer> getCountByCoaches () {
        Map<Coach, Integer> coachTrainingCount = new HashMap<>();

        for (TreeMap<TimeOfDay, List<TrainingSession>> dayTrainings : timetable.values()) {
            for (List<TrainingSession> trainingSessions : dayTrainings.values()) {
                for (TrainingSession trainingSession : trainingSessions) {
                    Coach coach = trainingSession.getCoach();
                    int count = coachTrainingCount.getOrDefault(coach, 0);
                    coachTrainingCount.put(coach, count + 1);
                }
            }
        }

        return coachTrainingCount;
    }
}
