package ru.itis.servlets.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.itis.dto.CommentDTO.CommentCreationDTO;
import ru.itis.dto.CommentDTO.CommentDTO;
import ru.itis.dto.TaskDTO.TaskDTO;
import ru.itis.services.CommentService;
import ru.itis.services.TaskService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/taskPage")
public class UserTaskPageServlet extends HttpServlet {
    private TaskService taskService;
    private CommentService commentService;

    @Override
    public void init() throws ServletException {
        taskService = (TaskService) getServletContext().getAttribute("taskService");
        commentService = (CommentService) getServletContext().getAttribute("commentService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String taskIdParam = req.getParameter("id");
        if (taskIdParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Нужен id задачи");
            return;
        }

        int taskId;
        try {
            taskId = Integer.parseInt(taskIdParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неправильный id задачи");
            return;
        }

        TaskDTO taskDTO = taskService.getTaskById(taskId);
        if (taskDTO == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Задача не найдена");
            return;
        }

        List<CommentDTO> comments = taskService.getAllTaskComments(taskId);

        req.setAttribute("task", taskDTO);
        req.setAttribute("taskComments", comments);
        req.setAttribute("username", req.getSession().getAttribute("username"));

        req.getRequestDispatcher("/WEB-INF/jsp/user/userTaskPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Парсим JSON из запроса
        CommentCreationDTO commentCreationDTO = objectMapper.readValue(req.getReader(), CommentCreationDTO.class);
        commentCreationDTO.setUserId((int) req.getSession().getAttribute("userId"));

        // Добавляем комментарий
        commentService.addComment(commentCreationDTO);

        // Получаем обновленный список комментариев
        List<CommentDTO> comments = taskService.getAllTaskComments(commentCreationDTO.getTaskId());

        // Возвращаем обновленный список комментариев в JSON формате
        resp.setContentType("application/json");
        objectMapper.writeValue(resp.getWriter(), comments);
    }
}
