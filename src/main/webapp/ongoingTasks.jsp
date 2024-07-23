<!DOCTYPE html>
<html>
<head>
    <title>Ongoing Tasks</title>
</head>
<body>
    <h2>Ongoing Tasks</h2>
    <%
        // Import the necessary classes
        import java.util.List;
        import com.example.tasktracker.model.Task;

        // Get the ongoing tasks from the request attribute
        List<Task> ongoingTasks = (List<Task>) request.getAttribute("ongoingTasks");

        // Check if ongoingTasks is not null and not empty
        if (ongoingTasks != null && !ongoingTasks.isEmpty()) {
    %>
        <table border="1">
            <thead>
                <tr>
                    <th>Task ID</th>
                    <th>Project</th>
                    <th>Start Time</th>
                    <th>Category</th>
                    <th>Description</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <%
                    // Iterate through the list of tasks
                    for (Task task : ongoingTasks) {
                %>
                    <tr>
                        <td><%= task.getId() %></td>
                        <td><%= task.getProject() %></td>
                        <td><%= task.getStartTime() %></td>
                        <td><%= task.getCategory() %></td>
                        <td><%= task.getDescription() %></td>
                        <td>
                            <form method="post" action="endTask">
                                <input type="hidden" name="taskId" value="<%= task.getId() %>" />
                                <input type="submit" value="End Task" />
                            </form>
                        </td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    <%
        } else {
    %>
        <p>No ongoing tasks available.</p>
    <%
        }
    %>
</body>
</html>
