package ru.itis.servlets;

import ru.itis.dto.UserDTO.UserDTO;
import ru.itis.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logIn")
public class LogInServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/logIn.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            UserDTO userDTO = userService.logInUser(email, password);
            if (userDTO != null) {
                req.getSession().setAttribute("userId", userDTO.getId());
                req.getSession().setAttribute("username", userDTO.getUsername());
                req.getSession().setAttribute("role", userDTO.getRole());

                if ("admin".equalsIgnoreCase(userDTO.getRole())) {
                    System.out.println("админ зашёл");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/userMain");
                }
            }
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }
}
