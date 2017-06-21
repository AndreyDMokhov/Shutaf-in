package com.shutafin.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

@Component
public class AutoScriptExecutor implements ApplicationListener<ContextRefreshedEvent> {

    private final String SQL_SCRIPT_PATH = "si-persistence\\src\\main\\resources\\sql";
    private final String DB_UPDATE_TABLE_NAME = "db_update";
    private JdbcTemplate jdbcTemplate;
    private TreeMap<Integer, String> sqlScripts = new TreeMap<>();
    private Integer lastExecutedSqlScript;

    public void checkDatabase() {
        getLastExecutedScript();
        System.out.println(lastExecutedSqlScript);
        getAllSqlScripts();
        for (Map.Entry<Integer, String> sqlScript :
                sqlScripts.tailMap(lastExecutedSqlScript, false).entrySet()) {
            executeSqlScript(sqlScript);
        }
        updateLastExecutedScript(sqlScripts.lastKey());
    }

    private JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    private void getLastExecutedScript() {
        String showTablesQuery = "SHOW TABLES;";
        List<String> tables = getJdbcTemplate().queryForList(showTablesQuery, String.class);
        if (tables.contains(DB_UPDATE_TABLE_NAME)) {
            String selectQuery = "SELECT script_id FROM DB_UPDATE;";
            lastExecutedSqlScript = getJdbcTemplate().queryForObject(selectQuery, Integer.class);
        } else {
            lastExecutedSqlScript = -1;
        }

    }

    private void updateLastExecutedScript(int lastScriptNumber) {
        String updateQuery = "UPDATE DB_UPDATE SET script_id=" + lastScriptNumber;
        executeQuery(updateQuery);
    }

    private void getAllSqlScripts() {
        for (File file : new File(SQL_SCRIPT_PATH).listFiles()) {
            if (file.isFile()) {
                sqlScripts.put(getFileNumber(file), file.getPath());
            }
        }
    }

    private Integer getFileNumber(File file) {
        try {
            Integer fileNumber = Integer.parseInt(file.getName().split("\\.")[0]);
            return fileNumber;
        } catch (Exception exp) {
            System.out.println("failed to parse number");
        }
        return -1;
    }

    private void executeSqlScript(Map.Entry<Integer, String> sqlScript) {
        System.out.println("Executing SQL Script " + sqlScript.getValue());
        List<String> queries = getQueriesFromScript(sqlScript.getValue());
        for (String query : queries) {
            if (!query.trim().isEmpty()) {
                executeQuery(query);
                updateLastExecutedScript(sqlScript.getKey());
            }
        }
    }

    private void executeQuery(String query) {
        try {
            getJdbcTemplate().execute(query);
        } catch (Exception exp) {
            System.out.println("Error while executing query\n" + exp);
        }
    }

    private List<String> getQueriesFromScript(String sqlScript) {
        List<String> queries = new ArrayList<>();
        try {

            Scanner scan = new Scanner(new File(sqlScript));
            scan.useDelimiter(Pattern.compile(";"));
            while (scan.hasNext()) {
                queries.add(scan.next());
            }
            return queries;
        } catch (FileNotFoundException exp) {
            System.out.println("SQL script not found\n" + exp);
        } catch (IOException exp) {
            System.out.println("Cannot read file\n" + exp);
        }
        return queries;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        checkDatabase();
    }
}