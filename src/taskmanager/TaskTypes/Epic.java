package taskmanager.TaskTypes;

import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {


    protected ArrayList<Integer> subTasksInEpic = new ArrayList<>();

    private static HashMap<Integer, Subtask> subTasksList = new HashMap<>();
    public Epic(String taskName, int epicId, String description) {
        super(taskName, epicId, description);
    }
    public ArrayList<Integer> getSubTasksInEpic() {
        return subTasksInEpic;
    }

    public void setSubTasksInEpic(ArrayList<Integer> subTasksInEpic) {
        this.subTasksInEpic = subTasksInEpic;
    }

    public void addSubtaskToEpic(int subtaskId){
        subTasksInEpic.add(subtaskId);
    }



    public void removeSubTask(int subtaskId){
        subTasksList.remove(subtaskId);
    }

    public void setEpicStatus(HashMap<Integer, Subtask> subtask){ // Проверяет статус эпика.
        ArrayList<TaskStatus> statuses = new ArrayList<>();

        for (int taskId: subTasksInEpic) {
            statuses.add(subtask.get(taskId).getStatus());
        }

        boolean isDone = !statuses.contains(TaskStatus.NEW) && !statuses.contains(TaskStatus.IN_PROGRESS);
        boolean isInProgress = statuses.contains(TaskStatus.IN_PROGRESS)
                || (statuses.contains(TaskStatus.DONE) && statuses.contains(TaskStatus.NEW));

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
