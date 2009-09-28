package com.burtbeckwith.grails.plugin.datasources

import org.codehaus.groovy.grails.commons.ApplicationHolder as AH
import org.codehaus.groovy.grails.commons.DefaultGrailsApplication
import org.codehaus.groovy.grails.commons.GrailsClass

/**
 * Used as a mock when calling HibernateGrailsPlugin.
 *
 * @author Burt
 */
class FakeGrailsApplication extends DefaultGrailsApplication {

	def domainClasses = [] as Set
	def serviceClasses = [] as Set
	def domainClassNames

	@Override
	GrailsClass[] getArtefacts(String artefactType) {
		return domainClasses as GrailsClass[]
	}

	@Override
	GrailsClass addArtefact(String artefactType, GrailsClass artefactGrailsClass) {
		initDomainClassNames()

		if (domainClassNames.contains(artefactGrailsClass.clazz.name)) {
			domainClasses << artefactGrailsClass
		}
		return artefactGrailsClass
	}

	@Override
	GrailsClass addArtefact(String artefactType, Class artefactClass) {
		return domainClasses.find { dc -> dc.clazz.name ==  artefactClass.name }
	}

	@Override
	GroovyClassLoader getClassLoader() {
		return AH.application.classLoader
	}

	private void initDomainClassNames() {
		if (domainClassNames != null) {
			return
		}

		domainClassNames = domainClasses*.name
	}
}
