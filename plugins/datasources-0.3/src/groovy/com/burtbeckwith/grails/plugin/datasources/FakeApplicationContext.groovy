package com.burtbeckwith.grails.plugin.datasources

import org.springframework.context.support.GenericApplicationContext

/**
 * Used as a mock when configuring.
 *
 * @author Burt
 */
class FakeApplicationContext extends GenericApplicationContext {
	def transactionManager
	def sessionFactory

	@Override
	Object getBean(String name) {
		if (name.startsWith('sessionFactory')) {
			return sessionFactory
		}

		if (name.startsWith('transactionManager')) {
			return transactionManager
		}

		return null
	}
}
