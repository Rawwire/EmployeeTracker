package com.example.tasktracker.servlet;

import com.example.tasktracker.dao.TaskDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;

@WebServlet("/endTask")
public class EndTaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TaskDao taskDao = new TaskDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String phonenumber = (String) session.getAttribute("phno");

        System.out.println("EndTaskServlet: Processing end task request");
        System.out.println("EndTaskServlet: Phone number from session = " + phonenumber);

        if (phonenumber != null && !phonenumber.isEmpty()) {
            try {
                LocalTime endTime = LocalTime.now();

                System.out.println("EndTaskServlet: End Time = " + endTime);

                // Retrieve employee ID using phone number
                int employeeId = taskDao.getEmployeeIdByPhonenumber(phonenumber);
                System.out.println("EndTaskServlet: Employee ID = " + employeeId);

                // End the current task for the employee
                taskDao.endCurrentTask(employeeId, endTime);
                System.out.println("EndTaskServlet: Current task for employee ID " + employeeId + " ended successfully");

                // Redirect to the dashboard or another page
                response.sendRedirect("ongoingTasks.jsp");
            } catch (SQLException e) {
                System.out.println("EndTaskServlet: SQLException - Error occurred while ending task");
                request.setAttribute("message", "Error occurred while ending task");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } else {
            // Handle the case where phone number is null or empty
            System.out.println("EndTaskServlet: Session expired or user not logged in");
            request.setAttribute("message", "Session expired or user not logged in");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
