package ru.itis.servlets.user;

import ru.itis.dto.ProjectDTO.ProjectDTO;
import ru.itis.dto.TaskDTO.TaskDTO;
import ru.itis.services.ProjectService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/projectPageForUser")
public class UserProjectPageServlet extends HttpServlet {
    private ProjectService projectService;

    @Override
    public void init() throws ServletException {
        projectService = (ProjectService) getServletContext().getAttribute("projectService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String projectIdParam = req.getParameter("id");
        if (projectIdParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Нужен id проекта");
            return;
        }

        int projectId;
        try {
            projectId = Integer.parseInt(projectIdParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный id проекта");
            return;
        }

        ProjectDTO projectDTO = projectService.getProjectById(projectId);
        if (projectDTO == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Проект не найден");
            return;
        }

        List<TaskDTO> projectTasks = projectService.getAllProjectTasks(projectId);



        String userName = (String) req.getSession().getAttribute("username");
        String userRole = (String) req.getSession().getAttribute("role");

        if (!"manager".equals(userRole)) {
            projectTasks.removeIf(taskDTO -> !taskDTO.getAssignedUser().equals(userName));
        }

        req.setAttribute("project", projectDTO);
        req.setAttribute("projectTasks", projectTasks);
        req.setAttribute("username", req.getSession().getAttribute("username"));


        req.getRequestDispatcher("/WEB-INF/jsp/user/userProjectPage.jsp").forward(req, resp);
    }
}
