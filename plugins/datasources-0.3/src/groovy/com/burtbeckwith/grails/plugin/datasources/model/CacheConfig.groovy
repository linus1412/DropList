package com.burtbeckwith.grails.plugin.datasources.model

class CacheConfig extends Expando {

	String toString() {
		return properties.toString()
	}
}
