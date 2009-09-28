

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Item List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New Item</g:link></span>
        </div>
        <div class="body">
            <h1>Item List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <th>Bug</th>
                   	    
                   	        <g:sortableColumn property="candidate" title="Candidate" />
                        
                   	        <g:sortableColumn property="fixed" title="Fixed" />
                        
                   	        <g:sortableColumn property="passed" title="Passed" />
                        
                   	        <th>Priority</th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${itemInstanceList}" status="i" var="itemInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${itemInstance.id}">${fieldValue(bean:itemInstance, field:'id')}</g:link></td>
                        
                            <td>${fieldValue(bean:itemInstance, field:'bug')}</td>
                        
                            <td>${fieldValue(bean:itemInstance, field:'candidate')}</td>
                        
                            <td>${fieldValue(bean:itemInstance, field:'fixed')}</td>
                        
                            <td>${fieldValue(bean:itemInstance, field:'passed')}</td>
                        
                            <td>${fieldValue(bean:itemInstance, field:'priority')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${itemInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
