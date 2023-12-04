package ua.com.alevel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {
    private TaskManager taskManager;


    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();
        taskManager.addTask("Learn unit testing", 8);
        taskManager.addTask("Learn java core", 9);
        taskManager.addTask("Learn english", 10);
        taskManager.addTask("Learn spring", 7);
        taskManager.addTask("Learn sql", 7);
    }

    @Test
    void shouldAddNewTask() {
        taskManager.addTask("Learn Mockito", 6);
        assertFalse(taskManager.getTasks().isEmpty());
        assertEquals(6, taskManager.getTasks().size());
        assertTrue(taskManager.getTasks().values().stream().anyMatch(task -> "Learn Mockito".equals(task.getDescription())));
    }

    @Test
    void shouldFindOneOrMoreTasksByWordsInDescription(){
        List<Task> tasks = taskManager.findTasksByDescription("unit core sql");
        assertEquals(3, tasks.size());
        assertTrue(tasks.stream().anyMatch(task -> "Learn unit testing".equals(task.getDescription())));
        assertTrue(tasks.stream().anyMatch(task -> "Learn java core".equals(task.getDescription())));
        assertTrue(tasks.stream().anyMatch(task -> "Learn sql".equals(task.getDescription())));
    }

    @Test
    void shouldRemoveTask() {
        Task task = taskManager.getTasks().values().stream().findFirst().get();
        taskManager.deleteTask(task.getDescription());
        assertEquals(4, taskManager.getTasks().size());
    }

    @Test
    void shouldUpdateTask(){
        taskManager.updateTask("spring", "Learn spring and hibernate", 8);
        assertTrue(taskManager.getTasks().values().stream()
                .anyMatch(task -> "Learn spring and hibernate".equals(task.getDescription()) && task.getPriority() == 8));
    }

    @Test
    void shouldSortUncompletedTasksByPriorityIncrease(){
        List<Task> tasks = taskManager.sortUncompletedTasksByPriorityIncrease();
        for (int i = 0; i < tasks.size()-1; i++) {
            assertTrue(tasks.get(i).getPriority() <= tasks.get(i+1).getPriority());
        }
    }

    @Test
    void shouldSortUncompletedTasksByPriority(){
        List<Task> tasks = taskManager.sortUncompletedTasksByPriority();
        for (int i = 0; i < tasks.size()-1; i++) {
            assertTrue(tasks.get(i).getPriority() >= tasks.get(i+1).getPriority());
        }
    }

    @Test
     void shouldMarkOneTaskAsComplete(){
        taskManager.markTaskAsComplete("Learn unit testing");
        assertFalse(taskManager.getCompletedTasks().isEmpty());
        assertEquals("Learn unit testing", taskManager.getCompletedTasks().get(0).getDescription());
     }

     @Test
     void shouldMarkFewTasksAsComplete(){
        taskManager.markTaskAsComplete("testing java english");
        assertEquals(3, taskManager.getCompletedTasks().size());
     }

     @Test
     void shouldSortCompletedTasksByPriority(){
        taskManager.markTaskAsComplete("testing java english");
        List<Task> completedSortedTask = taskManager.sortCompletedTasksByPriority();
         for (int i = 0; i < completedSortedTask.size()-1; i++) {
             assertTrue(completedSortedTask.get(i).getPriority() >= completedSortedTask.get(i+1).getPriority());
         }
     }

    @Test
    void shouldSortCompletedTasksByPriorityIncrease(){
        taskManager.markTaskAsComplete("testing java english");
        List<Task> completedSortedTask = taskManager.sortCompletedTasksByPriorityIncrease();
        for (int i = 0; i < completedSortedTask.size()-1; i++) {
            assertTrue(completedSortedTask.get(i).getPriority() <= completedSortedTask.get(i+1).getPriority());
        }
    }
}
