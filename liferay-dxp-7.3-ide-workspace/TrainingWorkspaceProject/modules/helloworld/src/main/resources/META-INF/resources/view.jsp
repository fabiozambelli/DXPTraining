<%@ include file="/init.jsp" %>
<%

%>
<p>
	<b><liferay-ui:message key="helloworld.caption"/></b>
</p>
<p>
	Users : ${users}
</p>
<p>
	Users : ${usersCount}
</p>
<hr/>
<liferay-ui:search-container emptyResultsMessage="no-users"
		total="${usersCount}">
		
		
		<liferay-ui:search-container-results results="${users}" />
		
		
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.User"
			modelVar="user"
			escapedModel="<%=true %>"
			keyProperty="userId">	
	
				<liferay-ui:search-container-column-text property="firstName" cssClass=""/> 
       
       			${user.getFirstName()}
       			
		</liferay-ui:search-container-row>

	</liferay-ui:search-container>