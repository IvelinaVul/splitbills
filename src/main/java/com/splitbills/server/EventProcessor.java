package com.splitbills.server;

import com.google.gson.Gson;
import com.splitbills.commands.*;
import com.splitbills.database.GroupRepository;
import com.splitbills.database.GroupRepositoryImpl;
import com.splitbills.database.UserRepository;
import com.splitbills.database.UserRepositoryImpl;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class EventProcessor implements Runnable {

    private final BlockingQueue<Task> newTasks;
    private final BlockingQueue<Task> completedTasks;
    private final EntityManagerFactory entityManagerFactory;
    private final CommandFactory commandFactory;
    private Gson gson = new Gson();
    private boolean isRunning = true;

    public EventProcessor(BlockingQueue<Task> newTasks, BlockingQueue<Task> completedTasks) {
        this.newTasks = newTasks;
        this.completedTasks = completedTasks;
        String persistenceUnit = "account";
        this.entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);
        commandFactory = getCommandFactory(entityManagerFactory);
    }

    public CommandFactory getCommandFactory(EntityManagerFactory entityManagerFactory) {
        UserRepository userRepository = new UserRepositoryImpl(entityManagerFactory);
        GroupRepository groupRepository = new GroupRepositoryImpl(entityManagerFactory);
        return new CommandFactory(userRepository, groupRepository);
    }

    @Override
    public void run() {
        int proccessingTasks = 10;
        while (isRunning) {
            List<Task> tasks = getNewTasks(proccessingTasks);
            for (Task task : tasks) {
                process(task);
            }
        }
    }

    private  List<Task> getNewTasks(int number) {
        List<Task> tasks = new ArrayList<>(number);
        newTasks.drainTo(tasks, number);
        return tasks;
    }

    private void process(Task task) {
        CommandInput input = task.getCommandInput();
        Result result;
        try {
            result = executeCommand(input);
            completeTask(task, result);
        } catch (InvalidCommandException invalidCommandException) {
            result = new Result(Status.INVALID_COMMAND);
            completeTask(task, result);
        }

    }

    private Result executeCommand(CommandInput input) throws InvalidCommandException {
        Command command = commandFactory.getCommand(input.getCommandName());
        return command.execute(input.getCommandArguments(), input.getClientToken());
    }

    private void completeTask(Task task, Result result) {
        String response = gson.toJson(result);
        task.setResponse(response);
        completedTasks.add(task);

    }

    public void stop() {
        isRunning = false;
        entityManagerFactory.close();
    }
}
