package danchat.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task{
    protected LocalDate fromDate;
    protected LocalDate toDate;

    public Event(String description, String from, String to) {
        this(description, false, from, to);
    }

    public Event(String description, boolean isDone, String from, String to) {
        super(description, isDone);
        this.fromDate = LocalDate.parse(from);
        this.toDate = LocalDate.parse(to);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " from " + this.fromDate.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                + " to " + this.toDate.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
    }
}
