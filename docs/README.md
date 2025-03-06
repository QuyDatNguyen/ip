# DanChat User Guide


## Introduction  
DanChat is a desktop chatbox for managing tasks, optimize for use via Command Line Interface (CLI)

## Features  
__Notes to user__:  
Words in UPPER_CASE are parameters to be supplied by user  
e.g: in todo TASK, TASK is a parameter which can be used as todo do homework
### Adding task with no date: todo
Add a new task needs to be done without any specified deadlines/ time period  
Format: `todo TASK`
Example: `todo do homework`  
- The task's status is assigned as not done by default.
```
___________________________
Add new todo in your list: [T][ ] homework
___________________________
```

### Adding task with deadline: deadline  
Add a new task with a deadline to be done.    
Format: `deadline TASK /by DEADLINE_DATE`  
- The task's status is assigned as not done by default.  
- DEADLINE_DATE must follow YYYY-MM-DD format.
Example: `deadline finish homework /by 2025-02-03`  
```
___________________________
Add new deadline in your list: [D][ ] finish homework by Feb 3 2025
___________________________
```  

### Adding event with time range: event
Add a new event with a starting date and an ending date.  
Format: `event TASK /from START_DATE /to END_DATE`
- The task's status is assigned as not done by default.  
- START_DATE and END_DATE must follow YYYY-MM-DD format.  
- START_DATE must not be later than END_DATE.  
Example: `event my homework /from 2025-01-31 /to 2025-02-03`
```
___________________________
Add new event in your list: [E][ ] my homework from Jan 31 2025 to Feb 3 2025
___________________________
``` 

### Listing all tasks: list
Shows a list of all tasks     
Format: `list`
```
___________________________
	1. [T][ ] homework
	2. [D][ ] finish homework by Feb 3 2025
	3. [E][ ] my homework from Jan 31 2025 to Feb 3 2025

___________________________
```  

### Mark a task as done: mark
Set the status of existing task in the list as done.  
Format: `mark INDEX`
- Mark the task at index INDEX  
- The index must be positive integer and smaller/ equal to the list size  
Example: `mark 1`  
```
___________________________
Ok, I have marked this task as done
	[X] homework
___________________________
```    

### Unmark a task: unmark
Set the status of existing task in the list as not done.  
Format: `unmark INDEX`  
- Unmark the task at index INDEX
- The index must be positive integer and smaller/ equal to the list size  
Example: `unmark 1`  
```
___________________________
Ok, I have marked this task as not done yet
	[ ] homework
___________________________
```

### Finding a task by keyword: find
Search for the task with description contains any of given keywords.  
Format: `find KEYWORD`  
Example: `find finish`  
```
___________________________
Here is your tasks: 
	2. [D][ ] finish homework by Feb 3 2025

___________________________
```

### Deleter a task: delete
Delete a task from the task list  
Format: `delete INDEX`
- Delete the task at index INDEX
- The index must be positive integer and smaller/ equal to the list size  
Example: `delete 3`
```
___________________________
I have deleted this task for you: [E][ ] my homework from Jan 31 2025 to Feb 3 2025
___________________________
```

### Terminate the program: bye
Terminate and exit the program  
Format: `bye`  