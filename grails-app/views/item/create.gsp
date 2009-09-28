

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Create Item</title>         
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Item List</g:link></span>
        </div>
        <div class="body">
            <h1>Create Item</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${itemInstance}">
            <div class="errors">
                <g:renderErrors bean="${itemInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                                              
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="candidate">Candidate:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:itemInstance,field:'candidate','errors')}">
                                    <g:checkBox name="candidate" value="${itemInstance?.candidate}" ></g:checkBox>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fixed">Fixed:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:itemInstance,field:'fixed','errors')}">
                                    <g:checkBox name="fixed" value="${itemInstance?.fixed}" ></g:checkBox>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="passed">Passed:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:itemInstance,field:'passed','errors')}">
                                    <g:checkBox name="passed" value="${itemInstance?.passed}" ></g:checkBox>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="priority">Priority:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:itemInstance,field:'priority','errors')}">
                                    <g:select  from="${Priority?.values()}" value="${itemInstance?.priority}" name="priority" ></g:select>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tdRef">Td Ref:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:itemInstance,field:'tdRef','errors')}">
                                    <input type="text" id="tdRef" name="tdRef" value="${fieldValue(bean:itemInstance,field:'tdRef')}"/>
                                </td>
                            </tr> 
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><input class="save" type="submit" value="Create" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
