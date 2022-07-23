package taskmanager.TaskTypes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedTasksList {

    private int size = 0;
    private Node head;
    private Node tail;

    private Map<Integer, Task> taskIdMap = new LinkedHashMap<>();

    public Map<Integer, Task> getTaskIdMap() {
        return taskIdMap;
    }

    public Node getTail() {
        return tail;
    }

    public void setTail(Node tail) {
        this.tail = tail;
    }



    public ArrayList<Integer> getTaskIdList() {
        return new ArrayList<>(taskIdMap.keySet());
    }

    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    private static class Node<E> {
        private Task task;
        private Node<E> next;
        private Node<E> prev;

        public Node(Task task, Node<E> prev, Node<E> next) {
            this.task = task;
            this.next = next;
            this.prev = prev;

        }


        public void setNext(Node<E> next) {
            this.next = next;
        }

        public Node<E> getNext() {
            return next;
        }



        public Task getTask() {
            return task;
        }

        public void setTask(Task task) {
            this.task = task;
        }
    }


    public void linkLast(Task task){
        final Node<Task> tailNode = getTail();
        final Node<Task> newNode = new Node(task, tailNode, null);
        setTail(newNode);
        if (tailNode == null) {
            head = newNode;
        } else {
            tailNode.setNext(newNode);
        }
        size++;
        taskIdMap.put(task.getTaskId(), task);
    }


    public void removeFirst() {
        final Node<Task> firstNode = getTail();
        final Node<Task> next = firstNode.getNext();
        firstNode.setTask(next.getTask());
        firstNode.setNext(next.getNext().getNext());
        setTail(next);
        if (next == null)
            tail = null;
        else
            next.prev = null;
        size--;
        taskIdMap.remove(firstNode.getTask().getTaskId());
    }

    void unlink(Node x) {
        final Node next = x.next;
        final Node prev = x.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.setTask(null);
        size--;
    }


    public void remove(Task task) {
        if (task == null) {
            for (Node x = head; x != null; x = x.next) {
                if (x.getTask() == null) {
                    unlink(x);
                    taskIdMap.remove(task.getTaskId());
                }
            }
        } else {
            for (Node x = head; x != null; x = x.next) {
                if (x.getTask().getTaskId() == task.getTaskId()) {
                    unlink(x);
                    taskIdMap.remove(task.getTaskId());
                }

            }
        }
    }


    public boolean containsTask(Task task){
        return taskIdMap.containsKey(task.getTaskId());
    }

    public int size() {
        return size;
    }


}
