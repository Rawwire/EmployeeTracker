package com.example.tasktracker.servlet;

import com.example.tasktracker.dao.TaskDao;
import com.example.tasktracker.model.Task;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/empadminweekview")
public class EmpAdminWeekViewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TaskDao taskDao = new TaskDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String phonenumber = request.getParameter("phno");
        String startDateStr = request.getParameter("strdate");
        String endDateStr = request.getParameter("enddate");

        try {
            // Convert string dates to LocalDate
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);

            // Fetch tasks between the start and end dates for the given employee
            List<Task> tasks = taskDao.getTasksByPhonenumberAndDateRange(phonenumber, startDate, endDate);

            // Calculate total duration for each day in hours
            List<String> dates = new ArrayList<>();
            List<Double> durations = new ArrayList<>(); // Use Double for hours

            // Initialize dates list with all dates between start and end date
            LocalDate date = startDate;
            while (!date.isAfter(endDate)) {
                dates.add(date.toString());
                durations.add(0.0); // Initialize duration for each day to 0 hours
                date = date.plusDays(1);
            }

            // Populate durations with actual task durations in hours
            for (Task task : tasks) {
                LocalDate taskDate = task.getDate();
                int index = dates.indexOf(taskDate.toString());
                if (index != -1) {
                    long durationInSeconds = task.getDurationInSeconds();
                    double durationInHours = durationInSeconds / 3600.0; // Convert seconds to hours
                    durations.set(index, durations.get(index) + durationInHours);
                }
            }

            // Calculate total duration in hours
            double totalDurationHours = durations.stream().mapToDouble(Double::doubleValue).sum();

            // Set attributes to be used in JSP
            request.setAttribute("dates", dates);
            request.setAttribute("durations", durations);
            request.setAttribute("phonenumber", phonenumber);
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);
            request.setAttribute("totalDuration", totalDurationHours); // Pass total duration in hours

            request.getRequestDispatcher("admin_week_view.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("message", "Session expired or user not logged in");
            request.getRequestDispatcher("weekerror.jsp").forward(request, response);
        }
    }
}

