security {

	// see DefaultSecurityConfig.groovy for all settable/overridable properties

	active = true

	loginUserDomainClass = "User"
	authorityDomainClass = "Role"
	requestMapClass = "Requestmap"


       useLdap = true
       ldapRetrieveDatabaseRoles = false
       ldapRetrieveGroupRoles = true
       ldapServer = 'ldap://bsman-prdc:389/OU=Users,OU=BlueSky,dc=blueskyts,dc=local'
       ldapManagerDn = 'ldap'
       ldapManagerPassword = 'password'
       ldapSearchBase = ''
       ldapSearchFilter = '(sAMAccountName={0})'

}
