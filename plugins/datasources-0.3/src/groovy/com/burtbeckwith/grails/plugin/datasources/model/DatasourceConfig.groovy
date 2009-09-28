package com.burtbeckwith.grails.plugin.datasources.model

class DatasourceConfig {

	String name
	boolean readOnly
	String driverClassName
	String url
	String username
	String password
	String parentDs
	String dbCreate
	String dialect
	String jndiName
	boolean pooled
	boolean loggingSql
	boolean logSql
	def passwordEncryptionCodec
	String flushMode

	List environments = ['development', 'test', 'production']
	List domainClasses = []
	List services = []

	HibernateConfig hibernate = new HibernateConfig()

	Properties getHibernateProperties() {
		Properties props = hibernate.toProperties()

		if (loggingSql || logSql) {
			props.'hibernate.show_sql' = 'true'
			props.'hibernate.format_sql' = 'true'
		}

		if (!dialect) {
			//
		}

		props.'hibernate.dialect' = dialect

		if (dbCreate) {
			props.'hibernate.hbm2ddl.auto' = dbCreate
		}

		return props
	}

	void setFlushMode(String mode) {
		switch (mode) {
			case 'manual':
			case 'always':
			case 'commit':
			case 'auto':
				this.@flushMode = mode
				break
			default:
				throw new IllegalArgumentException("invalid value for flushMode: $mode")
		}
	}

	void setDbCreate(String dbc) {
		switch (dbc) {
			case 'create':
			case 'create-drop':
			case 'update':
			case 'validate':
				this.@dbCreate = dbc
				break
			default:
				throw new IllegalArgumentException("invalid value for dbCreate: $dbc")
		}
	}

	void setDomainClasses(classes) {
		classes.each { clazz ->
			if (clazz instanceof Class) {
				this.@domainClasses << clazz.name
			}
			else {
				this.@domainClasses << clazz
			}
		}
	}

	String toString() {
		return "name: $name, readOnly: $readOnly, driverClassName: $driverClassName, " +
			"url: $url, username: $username, password: $password, parentDs: $parentDs, " +
			"dbCreate: $dbCreate, dialect: $dialect, jndiName: $jndiName, pooled: $pooled, " +
			"loggingSql: $loggingSql, logSql: $logSql, environments: $environments, " +
			"domainClasses: $domainClasses, services: $services, hibernate: $hibernate, " +
			"passwordEncryptionCodec: $passwordEncryptionCodec"
	}
}
