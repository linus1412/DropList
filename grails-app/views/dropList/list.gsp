

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>DropList List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New DropList</g:link></span>
        </div>
        <div class="body">
            <h1>DropList List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="name" title="Name" />
                        
                   	        <g:sortableColumn property="description" title="Description" />
                        
                   	        <g:sortableColumn property="completed" title="Completed" />
                        
                   	        <th>Customer</th>
                   	    
                   	        <g:sortableColumn property="toCustomer" title="To Customer" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${dropListInstanceList}" status="i" var="dropListInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${dropListInstance.id}">${fieldValue(bean:dropListInstance, field:'id')}</g:link></td>
                        
                            <td>${fieldValue(bean:dropListInstance, field:'name')}</td>
                        
                            <td>${fieldValue(bean:dropListInstance, field:'description')}</td>
                        
                            <td>${fieldValue(bean:dropListInstance, field:'completed')}</td>
                        
                            <td>${fieldValue(bean:dropListInstance, field:'customer')}</td>
                        
                            <td>${fieldValue(bean:dropListInstance, field:'toCustomer')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${dropListInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
