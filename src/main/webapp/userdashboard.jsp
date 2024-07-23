<%@ page import="java.util.List" %>
<%@ page import="com.example.tasktracker.dao.TaskDao" %>
<%@ page import="com.example.tasktracker.model.Task" %>
<%
    String phonenumber = (String) session.getAttribute("phno");
    TaskDao taskDao = new TaskDao();
    List<Task> tasks = null;
    if (phonenumber != null && !phonenumber.isEmpty()) {
        int employeeId = taskDao.getEmployeeIdByPhonenumber(phonenumber);
        tasks = taskDao.getCurrentTasks(employeeId);
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Task Dashboard</title>
</head>
<body>
    <h1>Current Tasks</h1>
    <table border="1">
        <tr>
            <th>Project</th>
            <th>Start Time</th>
            <th>End Task</th>
        </tr>
        <% if (tasks != null) {
            for (Task task : tasks) { %>
                <tr>
                    <td><%= task.getProject() %></td>
                    <td><%= task.getStartTime() %></td>
                    <td>
                        <form action="endTask" method="post">
                            <input type="hidden" name="taskId" value="<%= task.getId() %>" />
                            <button type="submit">End Task</button>
                        </form>
                    </td>
                </tr>
            <% }
        } %>
    </table>
</body>
</html>
