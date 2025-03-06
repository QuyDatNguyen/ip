package danchat;

import danchat.command.Command;
import danchat.parser.Parser;
import danchat.storage.Storage;
import danchat.task.*;
import danchat.ui.Ui;

public class DanChat {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public DanChat(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        storage.initTaskListFile();
        tasks = new TaskList();
//        try {
////            tasks = new TaskList(storage.load());
//            storage.initTaskListFile();
//        } catch (DanException e) {
//            ui.showError(e.getMessage());
//            tasks = new TaskList();
//        }
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
                String fullCommand = ui.readCommand();
                ui.showLine(); // show the divider line ("_______")
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