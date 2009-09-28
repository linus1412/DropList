

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Edit DropList</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">DropList List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New DropList</g:link></span>
        </div>
        <div class="body">
            <h1>Edit DropList</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${dropListInstance}">
            <div class="errors">
                <g:renderErrors bean="${dropListInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${dropListInstance?.id}" />
                <input type="hidden" name="version" value="${dropListInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Name:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:dropListInstance,field:'name','errors')}">
                                    <input type="text" id="name" name="name" value="${fieldValue(bean:dropListInstance,field:'name')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description">Description:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:dropListInstance,field:'description','errors')}">
                                    <textarea rows="5" cols="40" name="description">${fieldValue(bean:dropListInstance, field:'description')}</textarea>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="completed">Completed:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:dropListInstance,field:'completed','errors')}">
                                    <g:checkBox name="completed" value="${dropListInstance?.completed}" ></g:checkBox>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="customer">Customer:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:dropListInstance,field:'customer','errors')}">
                                    <g:select optionKey="id" from="${Customer.list()}" name="customer.id" value="${dropListInstance?.customer?.id}" ></g:select>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="items">Items:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:dropListInstance,field:'items','errors')}">
                                    <g:select name="items"
from="${Item.list()}"
size="5" multiple="yes" optionKey="id"
value="${dropListInstance?.items}" />

                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="toCustomer">To Customer:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:dropListInstance,field:'toCustomer','errors')}">
                                    <g:datePicker name="toCustomer" value="${dropListInstance?.toCustomer}" ></g:datePicker>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="toTest">To Test:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:dropListInstance,field:'toTest','errors')}">
                                    <g:datePicker name="toTest" value="${dropListInstance?.toTest}" ></g:datePicker>
                                </td>
                            </tr> 
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" value="Update" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
