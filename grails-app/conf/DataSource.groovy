dataSource {
	pooled = true
	driverClassName = "org.hsqldb.jdbcDriver"
	username = "sa"
	password = ""
        logSql = true

}
hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='com.opensymphony.oscache.hibernate.OSCacheProvider'
}
// environment specific settings
environments {
	development {
		dataSource {
                        url = "jdbc:mysql://localhost/droplist"
                        driverClassName = "com.mysql.jdbc.Driver"
			dbCreate = "create-drop" // one of 'create', 'create-drop','update'
//			url = "jdbc:hsqldb:mem:devDB"
                        username = "root"
                        password = "root"
                        dialect = org.hibernate.dialect.MySQLInnoDBDialect
		}
	}
	test {
		dataSource {
			dbCreate = "update"
			url = "jdbc:hsqldb:mem:testDb"
		}
	}
	production {
		dataSource {
			dbCreate = "update"
			url = "jdbc:hsqldb:file:prodDb;shutdown=true"
		}
	}
}