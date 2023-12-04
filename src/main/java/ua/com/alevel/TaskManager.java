package ua.com.alevel;

import java.util.*;
import java.util.stream.Collectors;

public class TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final ArrayList<Task> completedTasks = new ArrayList<>();
    private int nextId = 1;

    public void addTask(String description, int priority) {
        Task task = new Task(nextId++, description, priority);
        tasks.put(task.getId(), task);
    }

    public List<Task> findTasksByDescription(String description) {
        String[] keyWords = description.split("\\s+");
        return tasks.values().stream()
                .filter(task -> Arrays.stream(keyWords).anyMatch(keyWord -> task.getDescription().contains(keyWord)))
                .collect(Collectors.toList());
    }

    public void deleteTask(String description) {
        tasks.remove(findTasksByDescription(description).stream().findFirst().get().getId());
    }

    public void updateTask(String oldDescription, String newDescription, int priority) {
        Task task = findTasksByDescription(oldDescription).stream().findFirst().get();
        task.setDescription(newDescription);
        task.setPriority(priority);
    }

    public List<Task> sortUncompletedTasksByPriorityIncrease() {
        return tasks.values().stream()
                .sorted(Comparator.comparingInt(Task::getPriority))
                .toList();
    }

    public List<Task> sortUncompletedTasksByPriority() {
        return tasks.values().stream()
                .sorted(Comparator.comparingInt(Task::getPriority).reversed())
                .toList();
    }

    public List<Task> sortCompletedTasksByPriorityIncrease() {
        return completedTasks.stream()
                .sorted(Comparator.comparingInt(Task::getPriority))
                .toList();
    }

    public List<Task> sortCompletedTasksByPriority() {
        return completedTasks.stream()
                .sorted(Comparator.comparingInt(Task::getPriority).reversed())
                .toList();
    }

    public void markTaskAsComplete(String description) {
        List<Task> tasksList = findTasksByDescription(description);
        for (Task task : tasksList) {
            tasks.remove(task.getId());
            task.setCompleted(true);
            completedTasks.add(task);
        }
    }

    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    public ArrayList<Task> getCompletedTasks() {
        return completedTasks;
    }
}
