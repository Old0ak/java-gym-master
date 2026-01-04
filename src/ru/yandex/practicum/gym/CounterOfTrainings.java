package ru.yandex.practicum.gym;

public class CounterOfTrainings {
    private Coach coach;
    private int countTrainingSession;

    public CounterOfTrainings(Coach coach) {
        this.coach = coach;
        this.countTrainingSession = 0;
    }

    public void setCountTrainingSession(int countTrainingSession) {
        this.countTrainingSession = countTrainingSession;
    }

    public int getCountTrainingSession() {
        return countTrainingSession;
    }
}
