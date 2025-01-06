CREATE TABLE Users (
                       id serial PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(100) NOT NULL,
                       role_id INT REFERENCES Roles(id) ON DELETE SET NULL
);

CREATE TABLE Roles (
                       id serial PRIMARY KEY,
                       name VARCHAR(50) UNIQUE NOT NULL -- admin, manager, designer, developer, tester
);

CREATE TABLE Projects (
                          id serial PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          description TEXT,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          created_by INT REFERENCES Users(id) ON DELETE SET NULL
);

CREATE TABLE Task_States (
                             id serial PRIMARY KEY,
                             name VARCHAR(50) UNIQUE NOT NULL -- plan, design, develop, test, deploy, review, launch
);

CREATE TABLE Tasks (
                       id serial PRIMARY KEY,
                       title VARCHAR(100) NOT NULL,
                       description TEXT,
                       status_id INT REFERENCES Task_States(id) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       due_date DATE,
                       project_id INT REFERENCES Projects(id) ON DELETE CASCADE,
                       user_id INT REFERENCES Users(id) ON DELETE SET NULL,
                       file_id INT REFERENCES Files(file_id) ON DELETE SET NULL
);

CREATE TABLE Comments (
                          id serial PRIMARY KEY,
                          content TEXT NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          user_id INT REFERENCES Users(id) ON DELETE CASCADE, -- Пользователь, оставивший комментарий
                          task_id INT REFERENCES Tasks(id) ON DELETE CASCADE,  -- Задача, к которой оставлен комментарий
                          file_id INT REFERENCES Files(file_id) ON DELETE SET NULL
);

CREATE TABLE User_Projects (
                               user_id INT REFERENCES Users(id) ON DELETE CASCADE,
                               project_id INT REFERENCES Projects(id) ON DELETE CASCADE,
                               PRIMARY KEY (user_id, project_id)
);

CREATE TABLE Files (
                       file_id serial PRIMARY KEY,
                       storage_file_name VARCHAR(100),
                       original_file_name VARCHAR(100),
                       type VARCHAR(20),
                       size INTEGER
);
