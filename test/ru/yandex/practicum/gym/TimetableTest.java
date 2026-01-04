package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        TreeMap<TimeOfDay, List<TrainingSession>> trainings = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, trainings.keySet().size());

        List<TrainingSession> timeTraining = trainings.get(new TimeOfDay(13, 0));
        Assertions.assertEquals(1, timeTraining.size());
        //Проверить, что за вторник не вернулось занятий
        TreeMap<TimeOfDay, List<TrainingSession>> trainingsTuesday =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertEquals(0, trainingsTuesday.keySet().size());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        TreeMap<TimeOfDay, List<TrainingSession>> trainings = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, trainings.keySet().size());
        List<TrainingSession> timeTrainingMonday = trainings.get(new TimeOfDay(13, 0));
        Assertions.assertEquals(1, timeTrainingMonday.size());

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        TreeMap<TimeOfDay, List<TrainingSession>> trainingsThursday =
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Assertions.assertEquals(2, trainingsThursday.keySet().size());

        TimeOfDay firstTime = trainingsThursday.firstKey();
        TimeOfDay secondTime = trainingsThursday.lastKey();

        Assertions.assertEquals(new TimeOfDay(13, 0), firstTime);
        Assertions.assertEquals(new TimeOfDay(20, 0), secondTime);

        // Проверить, что за вторник не вернулось занятий
        TreeMap<TimeOfDay, List<TrainingSession>> trainingsTuesday =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertEquals(0, trainingsTuesday.keySet().size());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        TreeMap<TimeOfDay, List<TrainingSession>> trainings = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, trainings.keySet().size());

        List<TrainingSession> timeTraining = trainings.get(new TimeOfDay(13, 0));
        Assertions.assertEquals(1, timeTraining.size());

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        List<TrainingSession> timeTrainingSecond = trainings.get(new TimeOfDay(14, 0));
        Assertions.assertNull(timeTrainingSecond);
    }

    @Test
    void testCountTrainingByOneCoach() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        List<CounterOfTrainings> coachCounts = timetable.getCountByCoaches();

        Assertions.assertEquals(1, coachCounts.getFirst().getCountTrainingSession());
    }

    @Test
    void testCountTrainingByTwoCoach() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);

        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);
        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        Coach coach2 = new Coach("Леонов", "Борис", "Николаевич");
        TrainingSession singleTrainingSession = new TrainingSession(groupChild, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<CounterOfTrainings> coachCounts = timetable.getCountByCoaches();

        Assertions.assertEquals(4, coachCounts.getFirst().getCountTrainingSession());
        Assertions.assertEquals(1, coachCounts.getLast().getCountTrainingSession());

        Assertions.assertTrue(coachCounts.getFirst().getCountTrainingSession() >=
                coachCounts.getLast().getCountTrainingSession());
    }

    @Test
    void testCountTrainingByThreeCoach() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);

        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);
        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        Coach coach2 = new Coach("Леонов", "Борис", "Николаевич");
        TrainingSession singleTrainingSession = new TrainingSession(groupChild, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        Coach coach3 = new Coach("Адронов", "Иван", "Борисович");
        TrainingSession firstTrainingSession = new TrainingSession(groupAdult, coach3,
                DayOfWeek.MONDAY, new TimeOfDay(12, 0));
        TrainingSession secondTrainingSession = new TrainingSession(groupChild, coach3,
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0));
        TrainingSession thirdTrainingSession = new TrainingSession(groupAdult, coach3,
                DayOfWeek.FRIDAY, new TimeOfDay(12, 0));

        timetable.addNewTrainingSession(firstTrainingSession);
        timetable.addNewTrainingSession(secondTrainingSession);
        timetable.addNewTrainingSession(thirdTrainingSession);

        List<CounterOfTrainings> coachCounts = timetable.getCountByCoaches();

        Assertions.assertEquals(4, coachCounts.getFirst().getCountTrainingSession());
        Assertions.assertEquals(3, coachCounts.get(1).getCountTrainingSession());
        Assertions.assertEquals(1, coachCounts.getLast().getCountTrainingSession());

        Assertions.assertTrue(coachCounts.getFirst().getCountTrainingSession() >=
                coachCounts.get(1).getCountTrainingSession());
        Assertions.assertTrue(coachCounts.get(1).getCountTrainingSession() >=
                coachCounts.getLast().getCountTrainingSession());
    }
}
