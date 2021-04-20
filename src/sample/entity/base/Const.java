package sample.entity.base;

public class Const {
    public static final String USERS_TABLE = "users";
    public static final String USERS_ID = "id";
    public static final String USERS_LOGIN = "login";
    public static final String USERS_PASSWORD = "password";
    public static final String USERS_NAME = "name";

    public static final String NAME = "name";
    public static final String PROJECT = "project";
    public static final String EXECUTOR = "executor";

    public static final String PROJECTS_TABLE = "projects";
    public static final String PROJECTS_ID = "id";
    public static final String PROJECTS_NAME = "name";

    public static final String TASKS_TABLE = "tasks";
    public static final String TASKS_ID = "id";
    public static final String TASKS_TOPIC = "topic";
    public static final String TASKS_DESCRIPTION = "description";
    public static final String TASKS_TYPE = "type";
    public static final String TASKS_PROJECT_ID = "project_id";
    public static final String TASKS_USER_ID = "user_id";
    public static final String TASKS_PRIORITY = "priority";

    public static final String DB_TYPE_INT_PRIMARY_KEY_AUTOINC = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String DB_TYPE_REF = "REFERENCES";
    public static final String DB_TYPE_INT = "INTEGER";
    public static final String DB_TYPE_TEXT = "TEXT";
    public static final String DB_TYPE_UNIQUE = "UNIQUE";
}
