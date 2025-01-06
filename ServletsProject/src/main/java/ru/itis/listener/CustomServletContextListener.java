package ru.itis.listener;

import ru.itis.repositories.*;
import ru.itis.repositories.impl.*;
import ru.itis.services.*;
import ru.itis.services.impl.*;
import ru.itis.util.DriverManagerDataSource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class CustomServletContextListener implements ServletContextListener {
    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/orisProject1";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "admin";

    public void contextInitialized(ServletContextEvent sce) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriver(DB_DRIVER);
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(DB_USER);
        dataSource.setPassword(DB_PASSWORD);

        UserDAO userDAO = new UserDAOImpl(dataSource);
        ProjectDAO projectDAO = new ProjectDAOImpl(dataSource);
        TaskDAO taskDAO = new TaskDAOImpl(dataSource);
        CommentDAO commentDAO = new CommentDAOImpl(dataSource);
        FileDAO fileDAO = new FileDAOImpl(dataSource);

        UserService userService = new UserServiceImpl(userDAO, projectDAO);
        ProjectService projectService = new ProjectServiceImpl(projectDAO, userDAO, taskDAO);
        TaskService taskService = new TaskServiceImpl(taskDAO, commentDAO);
        CommentService commentService = new CommentServiceImpl(commentDAO);
        FileService fileService = new FileServiceImpl(fileDAO);

        sce.getServletContext().setAttribute("userService", userService);
        sce.getServletContext().setAttribute("projectService", projectService);
        sce.getServletContext().setAttribute("taskService", taskService);
        sce.getServletContext().setAttribute("commentService", commentService);
        sce.getServletContext().setAttribute("fileService", fileService);
    }

}
