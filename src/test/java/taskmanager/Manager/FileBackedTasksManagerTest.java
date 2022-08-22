package taskmanager.Manager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import taskmanager.TaskTypes.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.*;
import java.util.Comparator;
import java.util.TreeSet;

class FileBackedTasksManagerTest extends TasksManagerTest<FileBackedTasksManager> {

    @Override
    public FileBackedTasksManager createTaskManager() {
        return new FileBackedTasksManager();
    }


    @Test
    void shouldConvertTaskToString() {
        int taskId = taskManager.addTask("Задача", "Без описания");
        // id,type,name,status,description, + startTime,duration,endTime,epic
        LocalDateTime dateTime = LocalDateTime.now();
        Duration duration = Duration.ofDays(1);
        taskManager.setTime(taskManager.getTask(taskId),dateTime, duration);

        String expectation = taskId
                +","+taskManager.getTask(taskId).getType()
                +","+taskManager.getTask(taskId).getName()
                +","+taskManager.getTask(taskId).getStatusAsString()
                +","+taskManager.getTask(taskId).getDescription()
                +","+taskManager.getTask(taskId).getStartTime().format(taskManager.getFormatter())
                +","+taskManager.getTask(taskId).getDuration()
                +","+taskManager.getTask(taskId).getEndTime().format(taskManager.getFormatter())+",";

        String reality = taskManager.convertTaskToString(taskManager.getTask(taskId));
        assertEquals(expectation, reality);
    }

    @Test
    void shouldConvertEpicToString() {
        int epicId = taskManager.addEpic("Эпик", "Без описания");
        /// id,type,name,status,description, + startTime,duration,endTime,epic

        LocalDateTime dateTime = LocalDateTime.now();
        Duration duration = Duration.ofMinutes(570);
        taskManager.setTime(taskManager.getTask(epicId), dateTime, duration);

        String expected = epicId
                +","+taskManager.getTask(epicId).getType()
                +","+taskManager.getTask(epicId).getName()
                +","+taskManager.getTask(epicId).getStatusAsString()
                +","+taskManager.getTask(epicId).getDescription()
                +","+taskManager.getTask(epicId).getStartTime().format(taskManager.getFormatter())
                +","+taskManager.getTask(epicId).getDuration()
                +","+taskManager.getTask(epicId).getEndTime().format(taskManager.getFormatter())+",";
        String realString = taskManager.convertTaskToString(taskManager.getTask(epicId));
        assertEquals(expected, realString);
    }

    @Test
    void shouldConvertSubtaskToString() {
        int epicId = taskManager.addEpic("Эпик", "Без описания");
        int subtaskId = taskManager.addSubTask("Подзадача", "Без описания", epicId);
        // id,type,name,status,description, + startTime,duration,endTime,epic
        LocalDateTime dateTime = LocalDateTime.now();
        Duration duration = Duration.ofMinutes(570);
        taskManager.setTime(taskManager.getTask(subtaskId), dateTime, duration);
        String expected1 = subtaskId
                +","+taskManager.getTask(subtaskId).getType()
                +","+taskManager.getTask(subtaskId).getName()
                +","+taskManager.getTask(subtaskId).getStatusAsString()
                +","+taskManager.getTask(subtaskId).getDescription()
                +","+taskManager.getTask(subtaskId).getStartTime().format(taskManager.getFormatter())
                +","+taskManager.getTask(subtaskId).getDuration()
                +","+taskManager.getTask(subtaskId).getEndTime().format(taskManager.getFormatter())
                +","+epicId;
        String realString = taskManager.convertTaskToString(taskManager.getTask(subtaskId));
        assertEquals(expected1, realString);
    }

    @Test
    void shouldConvertStringToTask() {
        int taskId = taskManager.addTask("Задача", "Без описания");

        LocalDateTime dateTime = LocalDateTime.now();
        Duration duration = Duration.ofDays(1);
        taskManager.setTime(taskManager.getTask(taskId), dateTime, duration);

        String stringTask = taskManager.convertTaskToString(taskManager.getTask(taskId));
        String startTime = String.valueOf(taskManager.fromString(stringTask)
                .getStartTime().format(taskManager.getFormatter()));


        assertEquals(TaskTypes.TASK, taskManager.getTask(taskId).getType());
        assertEquals(taskId, taskManager.getTask(taskId).getId());
        assertEquals(startTime,taskManager.getTask(taskId).getStartTime().format(taskManager.getFormatter()));
    }



    @Test
    void shouldSaveToFileWhenNoTasksAreAdded() throws IOException {
        taskManager.save();
        Path path = Path.of("src/taskmanager/Manager/BackedData/DefaultFileBackedTasksManager.csv");
        String fileAsString = Files.readString(path);
        assertEquals("HISTORY ", fileAsString, "Пустой файл сохранен неверно");
    }

    @Test
    void shouldSaveToFileWhenSomeTasksAreAdded() throws IOException {
        Path path = Path.of("src/taskmanager/Manager/BackedData/DefaultFileBackedTasksManager.csv");
        String fileAsString = Files.readString(path);
        assertEquals("HISTORY ", fileAsString, "Пустой файл сохранен неверно");

        int taskId1 = taskManager.addTask("Задача", "Без названия");
        LocalDateTime dateTime = LocalDateTime.now();
        Duration duration = Duration.ofDays(1);
        taskManager.setTime(taskManager.getTask(taskId1), dateTime, duration);
        String string1 = taskManager.convertTaskToString(taskManager.getTask(taskId1))+"\n";
        taskManager.save();
        fileAsString = Files.readString(path);
        String expectation = string1+"HISTORY "+taskId1+",";
        assertEquals(expectation, fileAsString, "Содержимое файла не соответствует ожидаемому " +
                "после добавления новой задачи");

        int taskId2 = taskManager.addTask("Задача", "Без названия");
        taskManager.getTask(taskId2).setStatus(TaskStatus.IN_PROGRESS);
        LocalDateTime dateTime2 = LocalDateTime.now().plusDays(3).plusHours(11);
        taskManager.setTime(taskManager.getTask(taskId2), dateTime2, duration);
        String string2 = taskManager.convertTaskToString(taskManager.getTask(taskId2))+"\n";

        int epicId = taskManager.addEpic("Эпик", "Без названия");
        int subtaskId1 = taskManager.addSubTask("Подзадача", "Без названия", epicId);
        int subtaskId2 = taskManager.addSubTask("Подзадача", "Без названия", epicId);
        LocalDateTime dateTime3 = LocalDateTime.now().plusDays(8);

        taskManager.setTime(taskManager.getTask(subtaskId1), dateTime3, duration);
        LocalDateTime dateTime4 = LocalDateTime.now().plusDays(13);

        taskManager.setTime(taskManager.getTask(subtaskId2), dateTime4, duration);
        LocalDateTime dateTime5 = LocalDateTime.now().plusDays(17);

        taskManager.setTime(taskManager.getTask(epicId), dateTime5, duration);

        String string3 = taskManager.convertTaskToString(taskManager.getTask(epicId))+"\n";
        String string4 = taskManager.convertTaskToString(taskManager.getTask(subtaskId1))+"\n";
        String string5 = taskManager.convertTaskToString(taskManager.getTask(subtaskId2))+"\n";
        taskManager.save();

        fileAsString = Files.readString(path);


        expectation = string1+string2+string4+string5+string3+"HISTORY 32,33,34,35,36,";
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