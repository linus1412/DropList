package com.burtbeckwith.grails.plugin.datasources

import javax.sql.DataSource

import org.hibernate.SessionFactory

import org.springframework.context.ApplicationContext
import org.springframework.orm.hibernate3.HibernateTemplate
import org.springframework.transaction.PlatformTransactionManager

/**
 * Utility methods for datasources.
 */class DatasourcesUtils {

	static ApplicationContext applicationContext

	/**
	 * Create a HibernateTemplate using the specified data source.
	 * @param dsName  the ds name
	 * @return  the template
	 */
	static HibernateTemplate newHibernateTemplate(String dsName) {
		String name = dsName ? "sessionFactory_$dsName" : "sessionFactory"
		return new HibernateTemplate(applicationContext.getBean(name))
	}

	/**
	 * Create a HibernateTemplate using the default data source.
	 * @return  the template
	 */
	static HibernateTemplate newHibernateTemplate() {
		return newHibernateTemplate(null)
	}

	/**
	 * Get the named datasource.
	 * @param dsName  the name of the secondary datasource or null for the default
	 * @return  the datasource
	 */
	static DataSource getDataSource(String dsName) {
		return getDsBean(dsName, 'dataSource')
	}

	/**
	 * Get the default datasource.
	 * @return  the datasource
	 */
	static DataSource getDataSource() {
		return getDataSource(null)
	}

	/**
	 * Get the session factory for the datasource.
	 * @param dsName  the name of the secondary datasource or null for the default
	 * @return  the session factory
	 */
	static SessionFactory getSessionFactory(String dsName) {
		return getSessionFactory(dsName, 'sessionFactory')
	}

	/**
	 * Get the default session factory.
	 * @return  the session factory
	 */
	static SessionFactory getSessionFactory() {
		return getDataSource(null)
	}

	/**
	 * Get the transaction manager for the datasource.
	 * @param dsName  the name of the secondary datasource or null for the default
	 * @return  the transaction manager
	 */
	static PlatformTransactionManager getTransactionManager(String dsName) {
		return getDsBean(dsName, 'transactionManager')
	}

	/**
	 * Get the default transaction manager.
	 * @return  the transaction manager
	 */
	static PlatformTransactionManager getTransactionManager() {
		return getDataSource(null)
	}

	private static def getDsBean(String dsName, String prefix) {
		return applicationContext.getBean(dsName ? "${prefix}_$dsName" : prefix)
	}
}
