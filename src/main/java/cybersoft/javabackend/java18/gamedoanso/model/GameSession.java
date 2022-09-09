package cybersoft.javabackend.java18.gamedoanso.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameSession implements Serializable {
    private static int startId = 1;
    private static Random random = null;
    private String id;
    private int targetNumber;
    private List<Guess> guess;
    private LocalDateTime startTime;
    private String username; // username
    private LocalDateTime endTime;
    private boolean isCompleted;
    private boolean isActive;

    public GameSession() {

    }

    public GameSession(String username) {
        this.id = "GAME" + String.format("%05d", getRandomInt(100_000));
        this.targetNumber = getRandomInt(1_000);
        this.guess = new ArrayList<>();
        this.startTime = LocalDateTime.now();
        this.username = username;
    }

    private static int getRandomInt(int max) {
        if (random == null)
            random = new Random();

        return random.nextInt(max - 1) + 1;
    }

    private static <T> void reverseList(List<T> list) {
        // base case: the list is empty, or only one element is left
        if (list == null || list.size() <= 1) {
            return;
        }

        // remove the first element
        T value = list.remove(0);

        // recur for remaining items
        reverseList(list);

        // insert the top element back after recurse for remaining items
        list.add(value);
    }

    public int getTargetNumber() {
        return this.targetNumber;
    }

    public String getId() {
        return id;
    }

    public List<Guess> getGuess() {
        return guess;
    }

    public void setGuess(List<Guess> guesses) {
        this.guess = guesses;
    }

    public List<Guess> getReversedGuess() {
        List<Guess> temp = guess;
        reverseList(temp);
        return temp;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getUsername() {
        return username;
    }

    // fluent style api
    public GameSession id(String id) {
        this.id = id;
        return this;
    }

    public GameSession targetNumber(int target) {
        this.targetNumber = target;
        return this;
    }

    public GameSession startTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public GameSession endTime(LocalDateTime target) {
        this.endTime = target;
        return this;
    }

    public GameSession isCompleted(boolean value) {
        this.isCompleted = value;
        return this;
    }

    public GameSession isActive(boolean value) {
        this.isActive = value;
        return this;
    }

    public GameSession username(String value) {
        this.username = value;
        return this;
    }

    public long getDuration() {
        return java.time.Duration.between(startTime, endTime).getSeconds();
    }

    @Override
    public String toString() {
        return String.format("[id: %s, active: %s]", this.id, this.isActive);
    }
}
