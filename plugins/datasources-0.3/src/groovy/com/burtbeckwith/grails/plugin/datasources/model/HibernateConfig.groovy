package com.burtbeckwith.grails.plugin.datasources.model

class HibernateConfig extends Expando {

	CacheConfig cache = new CacheConfig()

	String toString() {
		return toProperties().toString()
	}

	Properties toProperties() {
		Properties props = new Properties()
		props.putAll properties

		cache.properties.each { key, value ->
			if (value) {
				props.setProperty "cache.$key", value.toString()
			}
		}

		return props
	}
}
