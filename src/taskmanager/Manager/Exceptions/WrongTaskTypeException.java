package taskmanager.Manager.Exceptions;

public class WrongTaskTypeException extends RuntimeException{

    public WrongTaskTypeException(String wrong_task_type) {
        super(wrong_task_type);
    }
}
