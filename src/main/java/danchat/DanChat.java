package danchat;

import danchat.command.Command;
import danchat.exception.DanException;
import danchat.parser.Parser;
import danchat.storage.Storage;
import danchat.task.TaskList;
import danchat.ui.Ui;

/**
 * Entry point of the DanChat application.
 * Initializes the application and starts the interaction with the user.
 */

public class DanChat {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Set up the required objects, initialize new storage file
     *
     * @param filePath argument supplied by user at the beginning of program
     *
     */
    public DanChat(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = storage.loadTasks();
        } catch (DanException e) {
            ui.showError(e.getMessage());
            tasks = new TaskList();
        }
    }

    /** Run the program until termination */

    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
                String fullCommand = ui.readCommand();
                ui.showLine(); 
                Command c = Parser.processUserInput(fullCommand);
                c.execute(tasks, storage, ui); //Error when c is null
                isExit = c.isExit();
                ui.showLine();
        }
    }

    public static void main(String[] args) {
        new DanChat("tasks.txt").run();
    }
}