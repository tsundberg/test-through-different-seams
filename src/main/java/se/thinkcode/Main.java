package se.thinkcode;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import se.thinkcode.todolist.Database;
import se.thinkcode.todolist.TodoList;
import se.thinkcode.todolist.TodoResource;

public class Main extends Application<Configuration> {
    private Database database;

    public static void main(String... args) throws Exception {
        new Main().run(args);
    }

    @Override
    public String getName() {
        return "The ToDo List";
    }

    @Override
    public void run(Configuration configuration, Environment environment) {
        TodoList todoList = new TodoList(database);

        TodoResource todoResource = new TodoResource(todoList);
        environment.jersey().register(todoResource);
    }

    public void setDatabase(Database database) {
        this.database = database;
    }
}
