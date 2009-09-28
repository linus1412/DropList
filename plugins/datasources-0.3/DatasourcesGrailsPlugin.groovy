import javax.sql.DataSource

import grails.util.GrailsUtil

import org.apache.commons.dbcp.BasicDataSource

import org.codehaus.groovy.grails.commons.ApplicationHolder as AH
import org.codehaus.groovy.grails.commons.DomainClassArtefactHandler
import org.codehaus.groovy.grails.exceptions.GrailsConfigurationException
import org.codehaus.groovy.grails.orm.hibernate.ConfigurableLocalSessionFactoryBean
import org.codehaus.groovy.grails.orm.hibernate.support.GrailsOpenSessionInViewInterceptor
import org.codehaus.groovy.grails.orm.hibernate.support.HibernatePersistenceContextInterceptor
import org.codehaus.groovy.grails.orm.hibernate.support.SpringLobHandlerDetectorFactoryBean
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsUrlHandlerMapping

import org.springframework.beans.factory.config.PropertiesFactoryBean
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.jdbc.support.nativejdbc.CommonsDbcpNativeJdbcExtractor
import org.springframework.jndi.JndiObjectFactoryBean
import org.springframework.orm.hibernate3.HibernateAccessor
import org.springframework.orm.hibernate3.HibernateTransactionManager
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean
import org.springframework.util.ReflectionUtils

import com.burtbeckwith.grails.plugin.datasources.DatasourcesBuilder
import com.burtbeckwith.grails.plugin.datasources.DatasourcesUtils
import com.burtbeckwith.grails.plugin.datasources.FakeApplicationContext
import com.burtbeckwith.grails.plugin.datasources.FakeGrailsApplication
import com.burtbeckwith.grails.plugin.datasources.ReadOnlyDriverManagerDataSource

class DatasourcesGrailsPlugin {

	def version = 0.3
	def dependsOn = [hibernate: GrailsUtil.grailsVersion]
	def loadAfter = ['hibernate', 'services']
	def watchedResources = 'file:./grails-app/conf/Datasources.groovy'
	def author = 'Burt Beckwith'
	def authorEmail = 'beckwithb@studentsonly.com'
	def title = 'Grails Datasources Plugin.'
	def description = 'Supports defining multiple data sources per application.'
	def documentation = 'http://grails.org/Datasources+Plugin'

	def doWithSpring = {

		def dataSources = loadConfig()
		if (!dataSources) {
			return
		}

		def handlerInterceptors = []
		if (manager?.hasGrailsPlugin('controllers')) {
			handlerInterceptors = [ref('localeChangeInterceptor'), ref('openSessionInViewInterceptor')]
		}

		def application = AH.application

		for (ds in dataSources) {

			if (!ds.environments.contains(GrailsUtil.environment)) {
				continue
			}

			String name = ds.name
			def dsClass = ds.jndiName ? JndiObjectFactoryBean :
				ds.pooled ? BasicDataSource :
				ds.readOnly ? ReadOnlyDriverManagerDataSource : DriverManagerDataSource

			def dsBean = "dataSource_$name"(dsClass) {
				if (ds.jndiName) {
					jndiName = ds.jndiName
					expectedType = DataSource
				}
				else {
					if (ds.pooled) {
						defaultReadOnly = ds.readOnly
					}
					driverClassName = ds.driverClassName
					url = ds.url
					if (ds.username) {
						username = ds.username
					}

					if (ds.password)  {
						def thePassword = ds.password
						if (ds.passwordEncryptionCodec) {
							def encryptionCodec = ds.passwordEncryptionCodec
							if (encryptionCodec instanceof Class) {
								try {
									password = encryptionCodec.decode(thePassword)
								}
								catch (Exception e) {
									throw new GrailsConfigurationException("Error decoding dataSource password with codec [$encryptionCodec]: ${e.message}", e)
								}
							}
							else {
								encryptionCodec = encryptionCodec.toString()
								def codecClass = application.codecClasses.find { it.name?.equalsIgnoreCase(encryptionCodec) || it.fullName == encryptionCodec}?.clazz
								try {
									if (!codecClass) {
										codecClass = application.classLoader.loadClass(encryptionCodec)
									}
									if (codecClass) {
										password = codecClass.decode(thePassword)
									}
									else {
										throw new GrailsConfigurationException("Error decoding dataSource password. Codec class not found for name [$encryptionCodec]")
									}
								}
								catch (ClassNotFoundException e) {
									throw new GrailsConfigurationException("Error decoding dataSource password. Codec class not found for name [$encryptionCodec]: $e.message", e)
								}
								catch(Exception e) {
									throw new GrailsConfigurationException("Error decoding dataSource password with codec [$encryptionCodec]: ${e.message}", e)
								}
							}
						}
						else {
							password = ds.password
						}
					}
				}
			}
			if (ds.pooled) {
				dsBean.destroyMethod = 'close'
			}

			"hibernateProperties_$name"(PropertiesFactoryBean) { bean ->
				bean.scope = 'prototype'
				properties = ds.hibernateProperties
			}
			if (GrailsUtil.grailsVersion.startsWith('1.0')) {
				"lobHandlerDetector_$name"(SpringLobHandlerDetectorFactoryBean) {
					dataSource = ref("dataSource_$name")
				}
			}
			else {
				nativeJdbcExtractor(CommonsDbcpNativeJdbcExtractor)
				"lobHandlerDetector_$name"(SpringLobHandlerDetectorFactoryBean) {
					dataSource = ref("dataSource_$name")
					pooledConnection = ds.pooled ?: false
					nativeJdbcExtractor = ref('nativeJdbcExtractor')
				}
			}

			def domainClasses = findDataSourceDomainClasses(ds, application)
			"sessionFactory_$name"(ConfigurableLocalSessionFactoryBean) {
				dataSource = ref("dataSource_$name")
				hibernateProperties = ref("hibernateProperties_$name")
				grailsApplication = new FakeGrailsApplication(domainClasses: domainClasses)
				lobHandler = ref("lobHandlerDetector_$name")
				if (GrailsUtil.grailsVersion.startsWith('1.0')) {
					entityInterceptor = ref('eventTriggeringInterceptor')
				}
				else {
					eventListeners = [
						'pre-load': eventTriggeringInterceptor,
						'post-load': eventTriggeringInterceptor,
						'save': eventTriggeringInterceptor,
						'save-update': eventTriggeringInterceptor,
						'post-insert': eventTriggeringInterceptor,
						'pre-update': eventTriggeringInterceptor,
						'post-update': eventTriggeringInterceptor,
						'pre-delete': eventTriggeringInterceptor,
						'post-delete': eventTriggeringInterceptor]
				}
			}

			"transactionManager_$name"(HibernateTransactionManager) {
				sessionFactory = ref("sessionFactory_$name")
			}

			"persistenceInterceptor_$name"(HibernatePersistenceContextInterceptor) {
				sessionFactory = ref("sessionFactory_$name")
			}

			if (manager?.hasGrailsPlugin('controllers')) {
				"openSessionInViewInterceptor_$name"(GrailsOpenSessionInViewInterceptor) {
					if (ds.readOnly) {
						flushMode = HibernateAccessor.FLUSH_NEVER
					}
					else {
						if (ds.flushMode) {
							switch (ds.flushMode) {
								case 'manual':
									flushMode = HibernateAccessor.FLUSH_NEVER; break
								case 'always':
									flushMode = HibernateAccessor.FLUSH_ALWAYS; break
								case 'commit':
									flushMode = HibernateAccessor.FLUSH_COMMIT; break
								default:
									flushMode = HibernateAccessor.FLUSH_AUTO
							}
						}
						else {
							flushMode = HibernateAccessor.FLUSH_AUTO
						}
					}

					sessionFactory = ref("sessionFactory_$name")
				}

				handlerInterceptors << ref("openSessionInViewInterceptor_$name")
			}

			// configure transactional services
			if (ds.services) {
				application.serviceClasses.each { serviceClass ->
					if (serviceClass.transactional &&
							ds.services.contains(serviceClass.propertyName - 'Service')) {

						def scope = serviceClass.getPropertyValue("scope")
						def props = new Properties()
						String attributes = 'PROPAGATION_REQUIRED'
						if (ds.readOnly) {
							attributes += ',readOnly'
						}
						props."*" = attributes
						"${serviceClass.propertyName}"(TransactionProxyFactoryBean) { bean ->
						if (scope) bean.scope = scope
							target = { innerBean ->
								innerBean.factoryBean = "${serviceClass.fullName}ServiceClass"
								innerBean.factoryMethod = "newInstance"
								innerBean.autowire = "byName"
								if (scope) innerBean.scope = scope
							}
							proxyTargetClass = true
							transactionAttributes = props
							transactionManager = ref("transactionManager_$name")
						}
					}
				}
			}
		}

		// redefine with the extra OSIV interceptors
		grailsUrlHandlerMapping(GrailsUrlHandlerMapping) {
			interceptors = handlerInterceptors
			mappings = ref('grailsUrlMappings')
		}
	}

	private def findDataSourceDomainClasses(ds, application) {
		def classNames = ds.domainClasses as Set
		def domainClasses = application.domainClasses.findAll { dc ->
			// need to check name in case of app reload
			classNames.contains(dc.clazz.name)
		}
		return domainClasses
	}

	def doWithDynamicMethods = { ctx ->

		def application = AH.application
		def dataSources = loadConfig()
		if (!dataSources) {
			return
		}

		DatasourcesUtils.applicationContext = ctx
		DomainClassArtefactHandler artefactHandler = new DomainClassArtefactHandler()

		for (ds in dataSources) {

			if (!ds.environments.contains(GrailsUtil.environment)) {
				continue
			}

			String name = ds.name
			def domainClasses = findDataSourceDomainClasses(ds, application)
			def fakeApplication = new FakeGrailsApplication(domainClasses: domainClasses)
			def sessionFactory = ctx.getBean("sessionFactory_$name")
			def fakeApplicationContext = new FakeApplicationContext(
					transactionManager: ctx.getBean("transactionManager_$name"),
					sessionFactory: sessionFactory)

			if (GrailsUtil.grailsVersion.startsWith('1.0')) {
				def clazz = application.classLoader.loadClass('org.codehaus.groovy.grails.plugins.orm.hibernate.HibernateGrailsPlugin')
				def closure = clazz.newInstance().doWithDynamicMethods
				closure.delegate = [application: fakeApplication]
				closure.call fakeApplicationContext
			}
			else {
				def clazz = application.classLoader.loadClass('org.codehaus.groovy.grails.plugins.orm.hibernate.HibernatePluginSupport')
				clazz.enhanceSessionFactory sessionFactory, fakeApplication, fakeApplicationContext
			}

			initializeDomainClasses fakeApplication, application, artefactHandler, domainClasses
		}
	}

	def doWithApplicationContext = { applicationContext ->
		// nothing to do
	}

	def doWithWebDescriptor = { xml ->
		// nothing to do
	}

	def onChange = { event ->
		// nothing to do
	}

	def onConfigChange = { event ->
		// nothing to do
	}

	def onShutdown = { event ->
		// todo: HSQLDB shutdown like in DataSourceGrailsPlugin
	}

	private void initializeDomainClasses(fakeApplication, application, artefactHandler, domainClasses) {

		def loadedClassesField = ReflectionUtils.findField(application.class, 'loadedClasses', Set)
		loadedClassesField.accessible = true
		Class[] allClasses = loadedClassesField.get(application)
		def dsLoadedClasses = loadedClassesField.get(fakeApplication)

		allClasses.each { clazz ->
			if (isDsDomainClass(clazz, artefactHandler, domainClasses)) {
				dsLoadedClasses.add clazz
			}
		}
		fakeApplication.initialise()
	}

	private boolean isDsDomainClass(clazz, artefactHandler, domainClasses) {
		if (!artefactHandler.isArtefactClass(clazz)) {
			return false
		}

		boolean dsDomainClass
		domainClasses.each { c ->
			if (c.clazz == clazz) {
				dsDomainClass = true
			}
		}

		return dsDomainClass
	}

	private def loadConfig() {

		try {
			def script = AH.application.classLoader.loadClass('Datasources').newInstance()
			script.run()	
			def builder = new DatasourcesBuilder()
			def datasources = script.datasources
			datasources.delegate = builder
			datasources()
			return builder.dataSources
		}
		catch (e) {
			println e.message
			return []
		}
	}
}
