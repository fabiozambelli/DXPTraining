<%@ include file="/init.jsp"%>

<c:set var="entry" value="${SEARCH_CONTAINER_RESULT_ROW.object}" />

<liferay-ui:icon-menu markupView="lexicon">

	<portlet:renderURL var="editItemURL">
		<portlet:param name="mvcRenderCommandName"
			value="<%=MVCCommandNames.EDIT_ITEM %>" />
		<portlet:param name="redirect" value="${currentURL}" />
		<portlet:param name="fooId" value="${entry.fooId}" />
	</portlet:renderURL>
	
	<liferay-ui:icon message="edit" url="${editItemURL}" />
	
	<portlet:actionURL name="<%=MVCCommandNames.DELETE_ITEM %>" var="deleteItemURL">
		<portlet:param name="redirect" value="${currentURL}" />
		<portlet:param name="fooId" value="${entry.fooId}" />
	</portlet:actionURL>
	
	<liferay-ui:icon message="delete" url="${deleteItemURL}" />

</liferay-ui:icon-menu>