package com.palace.memories.db;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DB {
	
	public static JdbcTemplate wrIns ;
	public static JdbcTemplate reIns;
	
	public DB(@Qualifier("readDataSource") DataSource rds,@Qualifier("writeDataSource") DataSource wds) {
		wrIns = new JdbcTemplate(wds);
		reIns = new JdbcTemplate(rds);
	}
}
