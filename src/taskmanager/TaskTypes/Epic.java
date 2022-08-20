package taskmanager.TaskTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

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
    public ArrayList<Integer> getSubTasksInEpic() {
        return new ArrayList<>(subTasksInEpic);
    }

    public void setSubTasksInEpic(ArrayList<Integer> subTasksInEpic) {
        this.subTasksInEpic = subTasksInEpic;
    }

    public void addSubtaskToEpic(Subtask subtask){
        subTasksInEpic.add(subtask.getTaskId());
        subTasksList.put(subtask.getTaskId(), subtask);
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
                +"Статус эпика: " + status+"\n"
                +"Описание: " + description+"\n"
                +"Подзадачи эпика: " + subTasksInEpic+ "\n";

    }

}
