package taskmanager.TaskTypes;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {


    protected ArrayList<Integer> subTasksInEpic = new ArrayList<>();

    private  HashMap<Integer, Subtask> subTasksList = new HashMap<>();
    public Epic(String epicName, int epicId, String epicDescription) {
        super(epicName, epicId, epicDescription);
        this.type = TaskTypes.EPIC;
        this.status = TaskStatus.EMPTY_EPIC;
    }

    public Epic(Epic epic) {
        super(new Epic(epic));
        this.type = TaskTypes.EPIC;
        this.status = TaskStatus.EMPTY_EPIC;
    }


    public void setTime(LocalDateTime dateTime){
        this.startTime = dateTime;
        this.duration = subTasksList.values()
                .stream().map(subtask -> subtask.getDuration())
                .reduce(Duration.ZERO, Duration::plus);
        this.endTime = startTime.plusMinutes(duration.toMinutes());
    }

    public ArrayList<Integer> getSubTasksInEpic() {
        return new ArrayList<>(subTasksInEpic);
    }

    public void setSubTasksInEpic(ArrayList<Integer> subTasksInEpic) {
        this.subTasksInEpic = subTasksInEpic;
    }

    public void addSubtaskToEpic(Subtask subtask){
        subTasksInEpic.add(subtask.getId());
        subTasksList.put(subtask.getId(), subtask);
    }



    public void removeSubTask(int subtaskId){
        subTasksInEpic.remove(subTasksInEpic.indexOf(subtaskId));
        subTasksList.remove(subtaskId);

    }

    public void setEpicStatus(){
        ArrayList<TaskStatus> statuses = new ArrayList<>();

        for (int taskId: subTasksInEpic) {
            TaskStatus status = subTasksList.get(taskId).getStatus();
            statuses.add(status);
        }
        boolean isNew = !statuses.contains(TaskStatus.DONE)
                && !statuses.contains(TaskStatus.IN_PROGRESS);

        boolean isDone = !statuses.contains(TaskStatus.NEW)
                && !statuses.contains(TaskStatus.IN_PROGRESS)
                && !statuses.contains(TaskStatus.EMPTY_EPIC);
        boolean isInProgress = statuses.contains(TaskStatus.IN_PROGRESS)
                || (statuses.contains(TaskStatus.DONE) && statuses.contains(TaskStatus.NEW));

        if (isNew) this.status = TaskStatus.NEW;
        if (isDone) this.status = TaskStatus.DONE;
        if (isInProgress) this.status = TaskStatus.IN_PROGRESS;

    }

    @Override
    public String toString(){
        return "Название эпика: " + taskName+"\n"
                +"ID эпика: " + taskId+"\n"
                +"Дата начала: " + startTime+"\n"
                +"Дата окончания: " + endTime+"\n"
                +"Статус эпика: " + status+"\n"
                +"Подзадачи эпика: " + subTasksInEpic+"\n"
                +"Описание: " + description+"\n\n";
    }

}
