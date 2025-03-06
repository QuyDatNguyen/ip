package danchat.parser;

import danchat.command.*;
import danchat.exception.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;


public class Parser {
    private static final String COMMAND_BYE_WORD = "bye";

    private static final String COMMAND_LIST_WORD = "list";

    private static final String COMMAND_UNMARK_WORD = "unmark";

    private static final String COMMAND_MARK_WORD = "mark";

    private static final String COMMAND_TODO_WORD = "todo";

    private static final String DEADLINE_SEPERATOR = " /by ";
    private static final String ERROR_MISSING_DEADLINE_INFO = "Missing content or deadline date. Please enter correct syntax: deadline task /by date";
    private static final String COMMAND_DEADLINE_WORD = "deadline";

    private static final String EVENT_BEGIN_DATE_SEPERATOR = " /from ";
    private static final String EVENT_END_DATE_SEPERATOR = " /to ";
    private static final String ERROR_MISSING_EVENT_INFO = "Missing content or event date. Please enter correct syntax: event task /from startDate /to endDate";
    private static final String COMMAND_EVENT_WORD = "event";

    private static final String ERROR_EMPTY_DETAIL = "Details must not be blank";
    private static final String ERROR_INVALID_INDEX_FORMAT = "Wrong index format. Index must be a positive integer";

    public static final String COMMAND_DELETE_WORD = "delete";
    public static final String COMMAND_FIND_WORD = "find";
    public static final String ERROR_INVALID_DATE = "Invalid date format. Please enter valid date using YYYY-MM-DD.";
    public static final String ERROR_FROM_TO_FORMAT = "Starting must not after the ending date";

    /**
     * Processing user input for execution
     *
     * @param userInput: full user input string
     * @return command based on user input
     */
    public static Command processUserInput(String userInput) {
        try {
            String[] splitInput = splitCommandAndDetail(userInput);
            String command = splitInput[0];
            String detail = splitInput[1];
            return parseUserCommandAndDetail(command, detail);
        } catch (DanException e) {
            return new ErrorCommand(e.getMessage());
        }
    }

    public static String[] splitCommandAndDetail(String userInput) {
        String[] commandAndDetail = userInput.split(" ", 2);
        if (commandAndDetail.length == 2) {
            return commandAndDetail;
        }
        return new String[] {commandAndDetail[0], null};
    }

      /**
     * Parses command and detail from user input for execution
     *
     * @param command: extracted command input from user input
     * @param detail: additional details for command's execution
     * @return the command based on user input
     * @throws InvalidIndexException if the index from detail is not valid positive integer
     * @throws MissingDateException if detail does not contain any dates or miss the matching date's tags
     * @throws IllegalCommandException if the command is not recognised
     * @throws EmptyTaskDetailException if the detail is null
     * @throws InvalidDateException if the date in detail does not follow the valid YYYY-MM-DD format
     */
    private static Command parseUserCommandAndDetail(String command, String detail) throws InvalidIndexException, MissingDateException, IllegalCommandException, EmptyTaskDetailException, InvalidDateException {
        try {
            switch (command) {
                case (COMMAND_BYE_WORD):
                    return prepareByeCommand(command, detail);
                case (COMMAND_LIST_WORD):
                    return prepareListCommand(command, detail);
                case (COMMAND_UNMARK_WORD):
                    return prepareUnmarkCommand(command, detail);
                case (COMMAND_MARK_WORD):
                    return prepareMarkCommand(command, detail);
                case (COMMAND_TODO_WORD):
                    return prepareTodoCommand(command, detail);
                case (COMMAND_DEADLINE_WORD):
                    return prepareDeadlineCommand(command, detail);
                case (COMMAND_EVENT_WORD):
                    return prepareEventCommand(command, detail);
                case (COMMAND_DELETE_WORD):
                    return prepareDeleteCommand(command, detail);
                case (COMMAND_FIND_WORD):
                    return prepareFindCommand(command, detail);
                default:
                    throw new IllegalCommandException("Unknown Command: " + command + " " + detail);
            }

        } catch (DanException e) {
            throw e;
        }
    }

    /**
     * Parses argument in context of delete command
     *
     * @param command: user delete command
     * @param detail: index of task to be deleted
     * @return prepared delete command
     * @throws InvalidIndexException if index of task is not positive integer
     * @throws IllegalCommandException if delete command format is incorrect
     */

    private static DeleteCommand prepareDeleteCommand(String command, String detail) throws InvalidIndexException, IllegalCommandException {
        if (isValidIndex(detail)) {
            return new DeleteCommand(command, detail);
        } else {
            throw new IllegalCommandException("Wrong delete command format");
        }
    }

      /**
     * Parses argument in context of event command
     *
     * @param command: user event command
     * @param detail: full details on event's description, start and end date from user input
     * @return prepared event command
     * @throws EmptyTaskDetailException if detail is empty
     * @throws MissingDateException if miss any start, end dates or date tags (/from, /to)
     * @throws InvalidDateException if the date in detail does not follow the valid YYYY-MM-DD format
     */
    private static EventCommand prepareEventCommand(String command, String detail) throws EmptyTaskDetailException, MissingDateException, InvalidDateException {
        if (detail == null || detail.trim().isEmpty()) {
            throw new EmptyTaskDetailException(ERROR_EMPTY_DETAIL);
        }
        String[] splitDetails = splitDetail(detail, EVENT_BEGIN_DATE_SEPERATOR, ERROR_MISSING_EVENT_INFO);
        String description = splitDetails[0].trim();
        String eventPeriod = splitDetails[1].trim();

        String[] splitDurations = splitDetail(eventPeriod, EVENT_END_DATE_SEPERATOR, ERROR_MISSING_EVENT_INFO);
        String from = splitDurations[0].trim();
        String to = splitDurations[1].trim();
        if (!(isValidDateFormat(from) && isValidDateFormat(to))) {
            throw new InvalidDateException(ERROR_INVALID_DATE);
        }
        if (!isValidFromToFormat(from, to)) {
            throw new InvalidDateException(ERROR_FROM_TO_FORMAT);
        }
        return new EventCommand(command, description, from, to);
    }

      /**
     * Parses argument in context of deadline command
     *
     * @param command: user command input
     * @param detail: full details on deadline's description and by date from user input
     * @return prepared deadline command
     * @throws EmptyTaskDetailException if the detail is empty
     * @throws MissingDateException if miss by date  or miss tag /by
     * @throws InvalidDateException if the date in detail does not follow the valid YYYY-MM-DD format
     */
    private static DeadlineCommand prepareDeadlineCommand(String command, String detail) throws EmptyTaskDetailException, MissingDateException, InvalidDateException {
        if (detail == null || detail.trim().isEmpty()) {
            throw new EmptyTaskDetailException(ERROR_EMPTY_DETAIL);
        }
        String[] splitDetails = splitDetail(detail, DEADLINE_SEPERATOR, ERROR_MISSING_DEADLINE_INFO);
        String description = splitDetails[0].trim();
        String by = splitDetails[1].trim();
        if (!isValidDateFormat(by)) {
            throw new InvalidDateException(ERROR_INVALID_DATE);
        }
        return new DeadlineCommand(command, description, by);
    }

    /**
     * Checking if user's string data is at correct YYYY-MM-DD format
     *
     * @param date the user date to be checked
     * @return true if date is in correct format
     */
    private static boolean isValidDateFormat(String date) {
        try {
            LocalDate.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Checking if event's starting and ending date are at correct YYYY-MM-DD format
     * and the starting date is not after the ending date
     * @param from the event starting date
     * @param to the event ending date
     * @return true if both dates satisfies both format and start-end date condition
     */
    private static boolean isValidFromToFormat(String from, String to) {
        try {
            LocalDate fromDate = LocalDate.parse(from);
            LocalDate toDate = LocalDate.parse(to);
            return !fromDate.isAfter(toDate);
        } catch (DateTimeParseException e) {
            return false;
        }
    }
  
    /**
     * Split details to into 2 parts, with second part is the extracted date indicated by a tag seperator
     *
     * @param detail: user detail input to be split
     * @param separator: tag to separate date from other details
     * @param errorMessage: prepared error message if cannot split date from other details
     * @return other part of details and the date after splitting
     * @throws MissingDateException if you cannot find or split the date
     */
    private static String[] splitDetail(String detail, String separator, String errorMessage) throws MissingDateException {
        String[] splitParts = detail.split(separator, 2);
        if (splitParts.length < 2) {
            throw new MissingDateException(errorMessage);
        }
        return splitParts;
    }

    /**
     * Parses argument in context of todo command
     *
     * @param command: user todo command
     * @param detail: description of the todo task
     * @return prepared todo command
     * @throws EmptyTaskDetailException if detail is empty
     */
    private static TodoCommand prepareTodoCommand(String command, String detail) throws EmptyTaskDetailException {
        if (detail == null || detail.trim().isEmpty()) {
            throw new EmptyTaskDetailException(ERROR_EMPTY_DETAIL);
        }
        return new TodoCommand(command, detail);
    }

    /**
     * Parses argument in context of find command
     *
     * @param command: user find command
     * @param detail: the keyword for searching
     * @return prepared find command
     * @throws EmptyTaskDetailException if the detail is empty
     */
    private static FindCommand prepareFindCommand(String command, String detail) throws EmptyTaskDetailException {
        if (detail == null || detail.trim().isEmpty()) {
            throw new EmptyTaskDetailException(ERROR_EMPTY_DETAIL);
        }
        return new FindCommand(command, detail);
    }

    /**
     * Parses argument in context of mark command
     *
     * @param command: user mark command
     * @param detail: the position of task to be marked
     * @return prepared mark command
     * @throws InvalidIndexException if the task index from detail is not valid positive integer
     * @throws IllegalCommandException if the mark command is in wrong format
     */
    private static Command prepareMarkCommand(String command, String detail) throws InvalidIndexException, IllegalCommandException {
        if (isValidIndex(detail)) {
            return new MarkCommand(command, detail);
        } else {
            throw new IllegalCommandException("Wrong mark command format");
        }
    }

    /**
     * Parses argument in context of unmark command
     *
     * @param command: user unmark command
     * @param detail: the position of task to be unmarked
     * @return prepared unmark command
     * @throws InvalidIndexException if the task index from detail is not valid positive integer
     * @throws IllegalCommandException if the unmark command is in wrong format
     */
    private static UnmarkCommand prepareUnmarkCommand(String command, String detail) throws InvalidIndexException, IllegalCommandException {
        if (isValidIndex(detail)) {
            return new UnmarkCommand(command, detail);
        } else {
            throw new IllegalCommandException("Wrong unmark command format");
        }
    }

    /**
     * Parses argument in context of list command
     *
     * @param command: user list command
     * @param detail: user detail command, which should be null
     * @return prepared list command
     * @throws IllegalCommandException if the detail is not null
     */
    private static ListCommand prepareListCommand(String command, String detail) throws IllegalCommandException {
        if (detail != null) {
            throw new IllegalCommandException("Wrong list command format");
        }
        return new ListCommand(command, null);
    }

    /**
     * Parses argument in context of bye command
     *
     * @param command: user bye command
     * @param detail: user detail command, which should be null
     * @return prepared bye command
     * @throws IllegalCommandException if the detail is not null
     */
    private static ByeCommand prepareByeCommand(String command, String detail) throws IllegalCommandException {
        if (detail != null) {
            throw new IllegalCommandException("Wrong bye command format");
        }
            return new ByeCommand(command, null);
    }

    private static boolean isValidIndex (String str) throws InvalidIndexException {
        if (!isValidInteger(str) || Integer.parseInt(str) <= 0) {
            throw new InvalidIndexException(ERROR_INVALID_INDEX_FORMAT);
        }
        return true;
    }

    private static boolean isValidInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
