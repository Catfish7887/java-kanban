package ru.JavaKanban.TaskManager;

import java.util.ArrayList;

import ru.JavaKanban.Tasks.Epic;
import ru.JavaKanban.Tasks.SubTask;
import ru.JavaKanban.Tasks.Task;

public interface TaskManager {

    ArrayList<Task> getAllTasks();

    ArrayList<Epic> getAllEpics();

    ArrayList<SubTask> getAllSubTasks();

    void addNewTask(Task task);

    void addNewEpic(Epic epic);

    void addNewSubTask(SubTask subTask);

    String getTask(int id);

    String getSubTask(int id);

    String getEpic(int id);

    ArrayList<SubTask> getEpicSubTasks(int id);

    void removeSubTaskById(int id);

    void removeEpicById(int epicId);

    void removeTaskById(int id);

    void clearAllEpics();

    void clearAllTasks();

    void clearAllSubTasks();

    void updateSubTask(SubTask subTask);

    void updateEpic(Epic epic);



    void updateTask(Task task);

}
