package com.shutafin.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


@Configuration
public class AutoScriptExecutor {

    private final static String DB_UPDATE_TABLE_NAME = "db_update";
    private final static String CREATE_DB_UPDATE_TABLE_SCRIPT = "CREATE TABLE `DB_UPDATE`" +
            " (`SCRIPT_ID` INT);";
    private final static String INSERT_BASIC_VAL_SCRIPT = "INSERT INTO `DB_UPDATE`(`SCRIPT_ID`) VALUES (0);";

    private TreeMap<Integer, Resource> sqlScripts = new TreeMap<>();
    private Integer lastExecutedSqlScript;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    private Connection connection;

    @PostConstruct
    public void checkDatabase() throws SQLException {
        establishConnection();
        readLastExecutedScript();
        readAllSqlScripts();

        for (Map.Entry<Integer, Resource> script : sqlScripts.tailMap(lastExecutedSqlScript, false).entrySet()) {
            executeSqlScript(script.getValue());
            updateLastExecutedScript(lastExecutedSqlScript);
        }
    }

    private void establishConnection() {
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException exp) {
            System.out.println("Error during establishing connection");
            exp.printStackTrace();
        }

    }

    private void readLastExecutedScript() {
        String showTablesQuery = "SHOW TABLES;";
        List<String> tables = jdbcTemplate.queryForList(showTablesQuery, String.class);
        if (tables.contains(DB_UPDATE_TABLE_NAME)) {
            String selectQuery = "SELECT script_id FROM DB_UPDATE;";
            lastExecutedSqlScript = jdbcTemplate.queryForObject(selectQuery, Integer.class);
        } else {
            initDbUpdateTable();
            lastExecutedSqlScript = 0;
        }
    }

    private void initDbUpdateTable() {
        executeQuery(CREATE_DB_UPDATE_TABLE_SCRIPT);
        executeQuery(INSERT_BASIC_VAL_SCRIPT);
    }

    private void updateLastExecutedScript(int lastScriptNumber) {
        String updateQuery = "UPDATE DB_UPDATE SET script_id=" + lastScriptNumber;
        executeQuery(updateQuery);
    }

    private void readAllSqlScripts() {
        try {
            ClassLoader cl = this.getClass().getClassLoader();
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
            Resource[] resources = resolver.getResources("classpath:sql/*.sql");
            for (Resource res : resources) {
                sqlScripts.put(getFileNumber(res), res);
            }
        } catch (IOException exp) {
            System.out.println("Error while getting SQL scripts");
        }
    }

    private Integer getFileNumber(Resource file) {
        if (!file.getFilename().matches("\\d+.sql")) {
            return -1;
        }
        try {
            Integer fileNumber = Integer.parseInt(file.getFilename().split("\\.")[0]);
            return fileNumber;
        } catch (NumberFormatException exp) {
            System.out.println("failed to parse number");
        }
        return -1;
    }

    private void executeQuery(String query) {
        try {
            jdbcTemplate.execute(query);
        } catch (Exception exp) {
            System.out.println("Error while executing query\n" + exp);
            throw exp;
        }
    }

    @Transactional(value = "dsTransactionManager", propagation = Propagation.REQUIRED)
    private void executeSqlScript(Resource script) throws SQLException {
        try {
            ScriptUtils.executeSqlScript(
                    connection,
                    new EncodedResource(script),
                    false,
                    false,
                    "--",
                    ";",
                    "*/",
                    "/*");
            lastExecutedSqlScript = getFileNumber(script);
            commit();

        } catch (ScriptException e) {
            System.out.println("Error while executing SQL Script");
            rollback();
            e.printStackTrace();

            throw e;
        }
    }

    private void commit() {
        try {
            connection.commit();
        } catch (SQLException exp) {
            System.out.println("Error during committing changes");
        }
    }

    private void rollback() {
        try {
            System.out.println("Rolling back");
            connection.rollback();
        } catch (SQLException exp) {
            System.out.println("Error during rolling back changes");
        }
    }


}