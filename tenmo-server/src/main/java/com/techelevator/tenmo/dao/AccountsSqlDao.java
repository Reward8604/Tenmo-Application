package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Accounts;
import com.techelevator.tenmo.model.Transfers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountsSqlDao implements AccountsDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public BigDecimal getBalance(int userId) 
    {
        Accounts accounts = null;
        String sql = "SELECT account_id\r\n" + 
        		"        ,user_id\r\n" + 
        		"        ,balance\r\n" + 
        		"FROM accounts \r\n" +
                "where user_id = ?;";

        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql,userId);

        while (rows.next())
        {
            BigDecimal balance = rows.getBigDecimal("balance");
            
            return balance;

        }

        return null;

    }
    
   
    private Accounts mapRowToAccount(SqlRowSet row)
    {
        Accounts accounts = new Accounts();

        accounts.setAccountId(row.getInt("account_id"));
        accounts.setUserId(row.getInt("user_id"));
        accounts.setBalance(row.getBigDecimal("balance"));
        
        return accounts;
    }

}
