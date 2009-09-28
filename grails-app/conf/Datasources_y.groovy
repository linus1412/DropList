datasources = {

	datasource(name: 'ds2') {
		domainClasses([com.burtbeckwith.ds_test.domain.Country,
		               com.burtbeckwith.ds_test.domain.State])
		readOnly(true)
		driverClassName('com.mysql.jdbc.Driver')
		url('jdbc:mysql://localhost/ds_test_2')
		username('dstest')
		password('dstest')
		dbCreate('create-drop')
		logSql(true)
		dialect(org.hibernate.dialect.MySQL5InnoDBDialect)
		hibernate {
			cache {
				use_second_level_cache(false)
				use_query_cache(false)
			}
		}
	}

	datasource(name: 'ds3') {
		driverClassName('com.mysql.jdbc.Driver')
		url('jdbc:mysql://localhost/ds_test_3')
		username('dstest')
		password('dstest')
		dbCreate('create-drop')
		domainClasses([com.burtbeckwith.ds_test.domain.Visit])
		services(['transactionTest'])
		logSql(true)
		dialect(org.hibernate.dialect.MySQL5InnoDBDialect)
		pooled(true)
		environments(['development'])
		hibernate {
			cache {
				use_second_level_cache(true)
				use_query_cache(true)
			}
		}
	}
}
