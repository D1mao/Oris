package ru.itis.servlets.user;

import ru.itis.dto.ProjectDTO.ProjectDTO;
import ru.itis.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/userMain")
public class UserMainServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = (Integer) req.getSession().getAttribute("userId");

        List<ProjectDTO> userProjects = userService.getAllUserProjects(userId);

        req.setAttribute("userProjects", userProjects);
        req.setAttribute("username", req.getSession().getAttribute("username"));

        req.getRequestDispatcher("/WEB-INF/jsp/user/userMain.jsp").forward(req, resp);
    }
}
