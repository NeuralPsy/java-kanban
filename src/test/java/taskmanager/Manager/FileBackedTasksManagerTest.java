package taskmanager.Manager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import taskmanager.TaskTypes.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class FileBackedTasksManagerTest extends TasksManagerTest<FileBackedTasksManager> {

    @Override
    public FileBackedTasksManager createTaskManager() {
        return new FileBackedTasksManager();
    }


    @Test
    void shouldConvertTaskToString() {
        int taskId = taskManager.addTask("Задача", "Без описания");
        // id,type,name,status,description,epic
        String expected1 = taskId
                +","+taskManager.getTask(taskId).getType()
                +","+taskManager.getTask(taskId).getName()
                +","+taskManager.getTask(taskId).getStatusAsString()
                +","+taskManager.getTask(taskId).getDescription()+",";
        String realString = taskManager.convertTaskToString(taskManager.getTask(taskId));
        assertEquals(expected1, realString);
    }

    @Test
    void shouldConvertEpicToString() {
        int epicId = taskManager.addEpic("Эпик", "Без описания");
        // id,type,name,status,description,epic
        String expected = epicId
                +","+taskManager.getTask(epicId).getType()
                +","+taskManager.getTask(epicId).getName()
                +","+taskManager.getTask(epicId).getStatusAsString()
                +","+taskManager.getTask(epicId).getDescription()+",";
        String realString = taskManager.convertTaskToString(taskManager.getTask(epicId));
        assertEquals(expected, realString);
    }

    @Test
    void shouldConvertSubtaskToString() {
        int epicId = taskManager.addEpic("Эпик", "Без описания");
        int subtaskId = taskManager.addSubTask("Подзадача", "Без описания", epicId);
        // id,type,name,status,description,epic
        String expected1 = subtaskId
                +","+taskManager.getTask(subtaskId).getType()
                +","+taskManager.getTask(subtaskId).getName()
                +","+taskManager.getTask(subtaskId).getStatusAsString()
                +","+taskManager.getTask(subtaskId).getDescription()
                +","+epicId;
        String realString = taskManager.convertTaskToString(taskManager.getTask(subtaskId));
        assertEquals(expected1, realString);
    }

    @Test
    void shouldConvertStringToTask() {
        int taskId = taskManager.addTask("Задача", "Без описания");
        Task task = taskManager.getTask(taskId);
        String stringTask = taskManager.convertTaskToString(task);
        assertEquals(taskManager.getTask(taskId), taskManager.fromString(stringTask));
    }



    @Test
    void shouldSaveToFileWhenNoTasksAreAdded() throws IOException {
        taskManager.save();
        Path path = Path.of("src/taskmanager/Manager/BackedData/DefaultFileBackedTasksManager.csv");
        String fileAsString = Files.readString(path);
        assertEquals("HISTORY:", fileAsString, "Пустой файл сохранен неверно");
    }

    @Test
    void shouldSaveToFileWhenSomeTasksAreAdded() throws IOException {
        Path path = Path.of("src/taskmanager/Manager/BackedData/DefaultFileBackedTasksManager.csv");
        String fileAsString = Files.readString(path);
        assertEquals("HISTORY:", fileAsString, "Пустой файл сохранен неверно");

        int taskId1 = taskManager.addTask("Задача", "Без названия");
        taskManager.save();
        fileAsString = Files.readString(path);
        String expectation = "32,TASK,Задача,NEW,Без названия,\nHISTORY:";
        assertEquals(expectation, fileAsString, "Содержимое файла не соответствует ожидаемому " +
                "после добавление новой задачи");
        int taskId2 = taskManager.addTask("Задача", "Без названия");
        taskManager.getTask(taskId1).setStatus(TaskStatus.IN_PROGRESS);

        int epicId = taskManager.addEpic("Эпик", "Без названия");
        int subtaskId = taskManager.addSubTask("Подзадача", "Без названия", epicId);
        taskManager.save();

        fileAsString = Files.readString(path);


        expectation = "32,TASK,Задача,IN_PROGRESS,Без названия,\n" +
                "33,TASK,Задача,NEW,Без названия,\n" +
                "35,SUBTASK,Подзадача,NEW,Без названия,34\n" +
                "34,EPIC,Эпик,NEW,Без названия,\n" +
                "HISTORY:32,";
        assertEquals(expectation, fileAsString, "Содержимое файла не соответствует ожидаемому " +
                "после добавление новой задачи, эпика, подзадачи и просмотра одной задачи");

    }

    @Test
    void shouldLoadFromFile() throws IOException {
        File existingFile = new File("src/test/java/testingFiles/TestFileBackedTasksManager.csv");
       FileBackedTasksManager managerFromFile = FileBackedTasksManager.loadFromFile(existingFile);
        Path path = Path.of("src/taskmanager/Manager/BackedData/DefaultFileBackedTasksManager.csv");
        String fileAsString1 = Files.readString(path);

        managerFromFile.save();

        String fileAsString2 = Files.readString(path);

        assertEquals(fileAsString1, fileAsString2);

    }
}