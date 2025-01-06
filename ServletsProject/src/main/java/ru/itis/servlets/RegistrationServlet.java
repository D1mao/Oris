package ru.itis.servlets;

import ru.itis.dto.UserDTO.RegisterForm;
import ru.itis.dto.UserDTO.UserDTO;
import ru.itis.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String error = req.getParameter("error");
        if ("notAuthorized".equals(error)) {
            req.setAttribute("errorMessage", "Пожалуйста, войдите в систему, чтобы получить доступ к этой странице.");
        }

        req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String name = req.getParameter("username");
            String email = req.getParameter("email");
            String roleId = req.getParameter("role");
            String password = req.getParameter("password");

            RegisterForm registerForm = new RegisterForm(name, email, password, roleId);
            boolean isRegistered = userService.registerUser(registerForm);

            if (!isRegistered) {
                req.setAttribute("errorMessage", "Эта почта уже занята");
                req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(req, resp);
                return;
            }

            int userId = userService.getUserIdByEmail(email);
            if (userId == -1) {
                req.setAttribute("errorMessage", "Во время регистрации произошла ошибка. Пожалуйста, попробуйте снова.");
                req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(req, resp);
                return;
            }

            Optional<UserDTO> userDTO = userService.getUserById(userId);
            if (userDTO.isPresent()) {
                req.getSession().setAttribute("userId", userDTO.get().getId());
                req.getSession().setAttribute("username", userDTO.get().getUsername());
                req.getSession().setAttribute("role", userDTO.get().getRole());

                if ("admin".equalsIgnoreCase(userDTO.get().getRole())) {
//                    resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
                    System.out.println("админ зареган");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/userMain");
                }
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
