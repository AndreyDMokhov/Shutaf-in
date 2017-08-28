package com.shutafin.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Configuration
@Slf4j
public class AutoScriptExecutor {

    private final static String DB_UPDATE_TABLE_NAME = "DB_UPDATE";
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
            log.error("Error during establishing connection.", exp);
        }

    }

    private void readLastExecutedScript() {
        String showTablesQuery = "SHOW TABLES;";
        List<String> tables = jdbcTemplate.queryForList(showTablesQuery, String.class);
        if (tables.contains(DB_UPDATE_TABLE_NAME) || tables.contains(DB_UPDATE_TABLE_NAME.toLowerCase())) {
            String selectQuery = "SELECT SCRIPT_ID FROM DB_UPDATE;";
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
        String updateQuery = "UPDATE DB_UPDATE SET SCRIPT_ID=" + lastScriptNumber;
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
            log.error("Error while getting SQL scripts. ", exp);
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
            log.error("Failed to parse number. ", exp);
        }
        return -1;
    }

    private void executeQuery(String query) {
        try {
            jdbcTemplate.execute(query);
        } catch (Exception exp) {
            log.error("Error while executing query with JDBC Template");
            log.error("Query: {}", query);
            log.error("Exception: ", exp);
            throw exp;
        }
    }

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
            log.error("Error while executing SQL Script. ", e);
            rollback();
            throw e;
        }
    }

    private void commit() {
        try {
            connection.commit();
        } catch (SQLException exp) {
            log.error("Error during committing changes. ", exp);
        }
    }

    private void rollback() {
        try {
            connection.rollback();
        } catch (SQLException exp) {
            log.error("Error during rolling back changes. ", exp);
        }
    }

}