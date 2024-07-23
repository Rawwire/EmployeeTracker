package com.example.tasktracker.servlet;

import com.example.tasktracker.dao.TaskDao;
import com.example.tasktracker.model.Task;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@WebServlet("/task")
public class TaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TaskDao taskDao = new TaskDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String phonenumber = (String) session.getAttribute("phno");

        if (phonenumber != null && !phonenumber.isEmpty()) {
            // Process other parameters
            String project = request.getParameter("project");
            LocalDate date = LocalDate.now();
            LocalTime startTime = LocalTime.now();
            String category = request.getParameter("category");
            String description = request.getParameter("description");

            try {
                int employeeId = taskDao.getEmployeeIdByPhonenumber(phonenumber);

                // Retrieve the last end time and total hours
                LocalTime lastEndTime = taskDao.getLastEndTime(employeeId, date);
                long totalSeconds = taskDao.getTotalHours(employeeId, date);

                // Save task
                Task task = new Task(employeeId, project, date, startTime, null, category, description);
                taskDao.saveTask(task);
                response.sendRedirect("success.jsp");
            } catch (SQLException e) {
                request.setAttribute("message", "Session expired or user not logged in.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String phonenumber = (String) session.getAttribute("phno");

        if (phonenumber != null && !phonenumber.isEmpty()) {
            try {
                int employeeId = taskDao.getEmployeeIdByPhonenumber(phonenumber);
                List<Task> ongoingTasks = taskDao.getOngoingTasks(employeeId);

                request.setAttribute("ongoingTasks", ongoingTasks);
                request.getRequestDispatcher("tasks.jsp").forward(request, response);
            } catch (SQLException e) {
                request.setAttribute("message", "Failed to retrieve ongoing tasks.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        }
    }
}
