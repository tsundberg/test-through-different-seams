package se.thinkcode.steps.adapters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.client.JerseyClientBuilder;
import se.thinkcode.Main;
import se.thinkcode.todolist.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class RestAdapter implements TodoAdapter {
    private String targetUrl = "http://localhost:8080";

    RestAdapter() {
        Database database = new InMemoryDatabase();
        startApplication(database);
    }

    private void startApplication(Database database) {
        try {
            Main main = new Main();
            main.setDatabase(database);

            String[] arguments = {"server"};
            main.run(arguments);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addTask(Owner owner, Task task) {
        Client jerseyClient = JerseyClientBuilder.createClient();
        TodoItem todoItem = new TodoItem(owner, task);
        String json = convertToJson(todoItem);

        Entity<String> todoItemEntity = Entity.entity(json, MediaType.APPLICATION_JSON_TYPE);

        int actual = jerseyClient
                .target(targetUrl)
                .path("/todo")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(todoItemEntity)
                .getStatus();

        assertThat(actual).isEqualTo(Response.Status.CREATED.getStatusCode());
    }

    @Override
    public List<Task> getTasks(Owner owner) {
        Client jerseyClient = JerseyClientBuilder.createClient();

        return jerseyClient
                .target(targetUrl)
                .path("/todo/" + owner.getName())
                .request()
                .get(new GenericType<List<Task>>() {
                });
    }

    private String convertToJson(TodoItem todoItem) {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(todoItem);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
