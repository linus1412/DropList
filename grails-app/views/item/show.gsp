

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Show Item</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Item List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Item</g:link></span>
        </div>
        <div class="body">
            <h1>Show Item</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                    
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:itemInstance, field:'id')}</td>
                            
                        </tr>

                        <%--
                        <tr class="prop">
                            <td valign="top" class="name">Bug:</td>
                            
                            <td valign="top" class="value"><g:link controller="bug" action="show" id="${itemInstance?.bug?.id}">${itemInstance?.bug?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                        --%>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Candidate:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:itemInstance, field:'candidate')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Fixed:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:itemInstance, field:'fixed')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Passed:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:itemInstance, field:'passed')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Priority:</td>
                            
                            <td valign="top" class="value">${itemInstance?.priority?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Td Ref:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:itemInstance, field:'tdRef')}                            
                              ${itemInstance.bug?.summary}
                            </td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <input type="hidden" name="id" value="${itemInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
