<%@ include file="/init.jsp" %>

<div class="container-fluid-1280">

<p>
	<b><liferay-ui:message key="foo.caption"/></b>
</p>

<portlet:renderURL var="addItemURL">
	<portlet:param name="mvcRenderCommandName" value="<%=MVCCommandNames.EDIT_ITEM%>" />
</portlet:renderURL>

<a href="<%= addItemURL%>">addItem</a>


<!--  clay:management-toolbar
	disabled="${fooCount eq 0}"
	displayContext="${itemsManagementToolbarDisplayContext}"
	itemsTotal="${fooCount}"
	searchContainerId="fooEntries"
	selectable="false"
	/ -->
	
<liferay-ui:search-container
	emptyResultsMessage="no-foos"
	id="fooEntries"
	iteratorURL="${portletURL}"
	total="${fooCount}">
	
	<liferay-ui:search-container-results results="${foos}" />
	
	<liferay-ui:search-container-row
			className="com.liferay.training.foo.model.Foo"
			modelVar="entry">
			
		<%@ include file="/entry_search_columns.jspf" %>
			
	</liferay-ui:search-container-row>

</liferay-ui:search-container>

</div>