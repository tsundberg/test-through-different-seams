package se.thinkcode.steps.adapters;

import se.thinkcode.todolist.*;

import java.util.List;

class ModelAdapter implements TodoAdapter {
    private TodoList todoList;

    ModelAdapter() {
        Database database = new InMemoryDatabase();
        todoList = new TodoList(database);
    }

    @Override
    public void addTask(Owner owner, Task task) {
        todoList.addTask(owner, task);
    }

    @Override
    public List<Task> getTasks(Owner owner) {
        return todoList.getTasks(owner);
    }
}
