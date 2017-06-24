package com.shutafin.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.datasource.init.ScriptStatementFailedException;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

//TODO: change to transactions in order to rollback in case of errors

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

    @PostConstruct
    public void checkDatabase() {

        getLastExecutedScript();
        getAllSqlScripts();

        for (Map.Entry<Integer, Resource> script :
                sqlScripts.tailMap(lastExecutedSqlScript, false).entrySet()) {
            executeSqlScript(script.getValue());
        }
        updateLastExecutedScript(sqlScripts.lastKey());
    }

    private void getLastExecutedScript() {
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

    private void getAllSqlScripts() {
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
        if (!file.getFilename().matches("\\d+.sql")){
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

    @Transactional
    private void executeSqlScript(Resource script) {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(script);
        try {
            databasePopulator.execute(dataSource);
            lastExecutedSqlScript = getFileNumber(script);
        } catch (ScriptStatementFailedException exp) {
            System.out.println("Error while executing SQL Script");
            throw exp;
        }
    }

}