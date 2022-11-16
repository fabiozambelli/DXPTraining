<%@ include file="/init.jsp" %>

<p>
	<b><liferay-ui:message key="helloworld.caption"/></b>
</p>
<p>
	Users : ${usersCount}
</p>
<liferay-ui:search-container emptyResultsMessage="no-assignments"
		id="assignmentEntries" iteratorURL="${portletURL}"
		total="${usersCount}">
		
		
		<liferay-ui:search-container-results results="${users}" />
		
		
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.User"
			modelVar="user">	
	
				<liferay-ui:search-container-column-text 
		             href="/" 
		             name="firstName" 
		             value="<%= user.getFirstName() %>" 
		         />
		
		         <liferay-ui:search-container-column-user 
		             name="userId" 
		             userId="${user.userId}" 
		         />
		
		         <liferay-ui:search-container-column-date 
		             name="create-date"
		             property="createDate" 
		         />

       
		</liferay-ui:search-container-row>

	</liferay-ui:search-container>
	
	<hr/>
	<portlet:renderURL var="usersURL">
    <portlet:param name="mvcRenderCommandName" value="/helloworld/users/view" />
</portlet:renderURL>
<a href="${usersURL}">Reload</a>


