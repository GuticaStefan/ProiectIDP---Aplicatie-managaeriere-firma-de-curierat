package com.proiect.CourierAppDBUtilitary.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class DbUtilitaryResource {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/tables")
    public List<Map<String, Object>> getTables() {
        return jdbcTemplate.queryForList("SHOW TABLES");
    }

    @GetMapping("/table/{tableName}")
    public List<Map<String, Object>> getTableData(@PathVariable String tableName) {
        return jdbcTemplate.queryForList("SELECT * FROM " + tableName);
    }
}
