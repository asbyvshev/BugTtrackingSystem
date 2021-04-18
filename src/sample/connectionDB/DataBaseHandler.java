package sample.connectionDB;

import sample.entity.Project;
import sample.entity.Task;
import sample.entity.User;
import sample.entity.base.Const;
import sample.entity.base.TaskType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class DataBaseHandler {
    private static final String DRIVER = "org.sqlite.JDBC";
    private static final String DB_URL = "jdbc:sqlite:";
    private static DataBaseHandler instance;
    private String dbName = "BugTtrackingSystemDB.db";
    private Connection connection;
    private Statement stmt;

    private DataBaseHandler() {
    }

    public static synchronized DataBaseHandler getInstance() {
        if (instance == null) {
            instance = new DataBaseHandler();
        }

        return instance;
    }

    public void createDB() {
        LinkedHashSet<String> createBatches = new LinkedHashSet();
        String createUserTable = String.format("CREATE TABLE if not exists '%s'" +
                        " ('%s' %s, '%s' %s %s, '%s' %s, '%s' %s);",
                Const.USERS_TABLE, Const.USERS_ID, Const.DB_TYPE_INT_PRIMARY_KEY_AUTOINC,
                Const.USERS_LOGIN, Const.DB_TYPE_TEXT, Const.DB_TYPE_UNIQUE,
                Const.USERS_PASSWORD, Const.DB_TYPE_TEXT,
                Const.USERS_NAME, Const.DB_TYPE_TEXT);
        createBatches.add(createUserTable);

        String createProjectTable = String.format("CREATE TABLE if not exists '%s'" +
                        " ('%s' %s, '%s' %s);",
                Const.PROJECTS_TABLE, Const.PROJECTS_ID, Const.DB_TYPE_INT_PRIMARY_KEY_AUTOINC,
                Const.PROJECTS_NAME, Const.DB_TYPE_TEXT);
        createBatches.add(createProjectTable);

        String createTaskTable = String.format("CREATE TABLE if not exists '%s'" +
                        " ('%s' %s, '%s' %s, '%s' %s, '%s' %s, '%s' %s," +
                        " '%s' %s, '%s' %s);",
                Const.TASKS_TABLE, Const.TASKS_ID, Const.DB_TYPE_INT_PRIMARY_KEY_AUTOINC,
                Const.TASKS_TOPIC, Const.DB_TYPE_TEXT,
                Const.TASKS_DESCRIPTION, Const.DB_TYPE_TEXT,
                Const.TASKS_PRIORITY, Const.DB_TYPE_INT,
                Const.TASKS_TYPE, Const.DB_TYPE_TEXT,
                Const.TASKS_PROJECT_ID, getDBTypeIntRef(Const.PROJECTS_TABLE),
                Const.TASKS_USER_ID, getDBTypeIntRef(Const.USERS_TABLE));
        createBatches.add(createTaskTable);

        try {
            for (String batch : createBatches) {
                stmt.addBatch(batch);
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getDBTypeIntRef(String table) {
        return String.format("%s %s %s (id)",
                Const.DB_TYPE_INT, Const.DB_TYPE_REF, table);
    }

    public void connect() throws SQLException {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DB_URL.concat(dbName));
            stmt = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void checkAndConnect() {
        if (connection == null) {
            try {
                connect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createUser(User user) {
        String insert = String.format("INSERT INTO '%s'('%s','%s','%s')VALUES(?,?,?)",
                Const.USERS_TABLE, Const.USERS_LOGIN, Const.USERS_PASSWORD, Const.USERS_NAME);
        try {
            PreparedStatement prSt = connection.prepareStatement(insert);
            prSt.setString(1, user.getLogin());
            prSt.setString(2, user.getPassword());
            prSt.setString(3, user.getName());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTask(Task task) {
        String insert = String.format(
                "INSERT INTO '%s'('%s','%s','%s','%s','%s','%s')VALUES(?,?,?,?,?,?)",
                Const.TASKS_TABLE, Const.TASKS_DESCRIPTION,
                Const.TASKS_TYPE, Const.TASKS_PRIORITY,
                Const.TASKS_TOPIC, Const.TASKS_PROJECT_ID,
                Const.TASKS_USER_ID);
        try {
            PreparedStatement prSt = connection.prepareStatement(insert);
            prSt.setString(1, task.getDescription());
            prSt.setString(2, task.getType().name());
            prSt.setString(3, task.getPriority());
            prSt.setString(4, task.getTopic());
            prSt.setInt(5, task.getProject().getId());
            prSt.setInt(6, task.getExecutor().getId());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createProject(Project project) {
        String insert = String.format("INSERT INTO '%s'('%s')VALUES(?)",
                Const.PROJECTS_TABLE, Const.PROJECTS_NAME);
        try {
            PreparedStatement prSt = connection.prepareStatement(insert);
            prSt.setString(1, project.getName());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getUserAuth(User user) {
        ResultSet resultSet = null;
        String select = String.format("SELECT * FROM %s WHERE %s=? AND %s=?",
                Const.USERS_TABLE, Const.USERS_LOGIN, Const.USERS_PASSWORD);
        PreparedStatement prSt;
        try {
            prSt = connection.prepareStatement(select);
            prSt.setString(1, user.getLogin());
            prSt.setString(2, user.getPassword());
            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    private ResultSet getAllItems(String table) {
        ResultSet resultSet = null;
        String select = String.format("SELECT * FROM %s", table);
        try {
            resultSet = stmt.executeQuery(select);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    private ResultSet getItemsBy(String table, String column, String value) {
        ResultSet resultSet = null;
        String select = String.format("SELECT * FROM %s WHERE %s=?",
                table, column);
        PreparedStatement prSt;
        try {
            prSt = connection.prepareStatement(select);
            prSt.setString(1, value);
            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public Set<User> getAllUsers() {
        ResultSet items = getAllItems(Const.USERS_TABLE);
        return getUsers(items);
    }

    private Set<User> getUsers(ResultSet items) {
        Set<User> users = new HashSet<>();
        try {
            while (items.next()) {
                User user = new User(
                        items.getInt(Const.USERS_ID),
                        items.getString(Const.USERS_NAME),
                        items.getString(Const.USERS_LOGIN),
                        items.getString(Const.USERS_PASSWORD));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User getUserById(String id) {
        ResultSet items = getItemsBy(Const.USERS_TABLE, Const.USERS_ID, id);
        return getUsers(items).stream().findFirst().orElse(null);
    }

    public List<Project> getAllProjects() {
        ResultSet items = getAllItems(Const.PROJECTS_TABLE);
        return getProjects(items);
    }

    public Project getProjectById(String id) {
        ResultSet items = getItemsBy(Const.PROJECTS_TABLE, Const.PROJECTS_ID, id);
        return getProjects(items).stream().findFirst().orElse(null);
    }

    private List<Project> getProjects(ResultSet items) {
        List<Project> projects = new ArrayList<>();
        try {
            while (items.next()) {
                Project project = new Project(
                        items.getInt(Const.PROJECTS_ID),
                        items.getString(Const.PROJECTS_NAME));
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public List<Task> getAllTasks() {
        ResultSet items = getAllItems(Const.TASKS_TABLE);
        return getTasks(items);
    }

    private List<Task> getTasks(ResultSet items) {
        List<Task> tasks = new ArrayList<>();
        try {
            while (items.next()) {
                String project_id = items.getString(Const.TASKS_PROJECT_ID);
                String user_id = items.getString(Const.TASKS_USER_ID);
                String typeStr = items.getString(Const.TASKS_TYPE);

                TaskType type = TaskType.valueOf(typeStr);

                Project project = getProjectById(project_id);
                User user = getUserById(user_id);

                Task task = new Task(
                        items.getInt(Const.TASKS_ID),
                        items.getString(Const.TASKS_TOPIC),
                        items.getString(Const.TASKS_DESCRIPTION),
                        items.getString(Const.TASKS_PRIORITY),
                        project,
                        user,
                        type
                );
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public List<Task> getTasksByUser(User user) {
        ResultSet resultSet = getItemsBy(
                Const.TASKS_TABLE,
                Const.TASKS_USER_ID,
                String.valueOf(user.getId()));
        return getTasks(resultSet);
    }

    public List<Task> getTasksByProject(Project project) {
        ResultSet resultSet = getItemsBy(
                Const.TASKS_TABLE,
                Const.TASKS_PROJECT_ID,
                String.valueOf(project.getId()));
        return getTasks(resultSet);
    }

    public void remove(String table, Integer id) {
        String insert = String.format("DELETE FROM %s WHERE id=?",
                table);
        try {
            PreparedStatement prSt = connection.prepareStatement(insert);
            prSt.setInt(1, id);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
