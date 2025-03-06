package danchat.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task{
    protected LocalDate byDate;

    public Deadline(String description, String by) {
        super(description);
        this.byDate = LocalDate.parse(by);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " by "
                + this.byDate.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
    }
}
