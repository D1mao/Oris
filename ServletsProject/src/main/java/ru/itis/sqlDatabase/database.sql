CREATE TABLE Users (
                       id serial PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(100) NOT NULL
);

CREATE TABLE Projects (
                          id serial PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          description TEXT,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          created_by INT REFERENCES Users(id) ON DELETE SET NULL
);

CREATE TABLE Tasks (
                       id serial PRIMARY KEY,
                       title VARCHAR(100) NOT NULL,
                       description TEXT,
                       status VARCHAR(20) CHECK(status IN ('IN_PROGRESS', 'COMPLETED')) NOT NULL,
                       due_date DATE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       project_id INT REFERENCES Projects(id) ON DELETE CASCADE,
                       user_id INT REFERENCES Users(id) ON DELETE SET NULL -- Пользователь, создавший задачу
);

CREATE TABLE Comments (
                          id serial PRIMARY KEY,
                          content TEXT NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          user_id INT REFERENCES Users(id) ON DELETE CASCADE, -- Пользователь, оставивший комментарий
                          task_id INT REFERENCES Tasks(id) ON DELETE CASCADE  -- Задача, к которой оставлен комментарий
);

CREATE TABLE User_Projects (
                               user_id INT REFERENCES Users(id) ON DELETE CASCADE,
                               project_id INT REFERENCES Projects(id) ON DELETE CASCADE,
                               PRIMARY KEY (user_id, project_id)
);
