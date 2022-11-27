<%@ include file="/init.jsp"%>

<c:choose>
	<c:when test="${not empty foo}">
		<c:set var="editTitle" value="update-item"/>
	</c:when>
	<c:otherwise>
		<portlet:actionURL var="assignmentActionURL" name="<%=MVCCommandNames.ADD_ITEM%>">
			<portlet:param name="redirect" value="${param.redirect}" />
		</portlet:actionURL>
		<c:set var="editTitle" value="add-item"/>
	</c:otherwise>
</c:choose>

<div class="container-fluid-1280 edit-assignment">
	
	<h1><liferay-ui:message key="${editTitle}" /></h1>
	
	<aui:model-context bean="${foo}" model="${fooClass}" />
	
	<aui:form action="${assignmentActionURL}" name="fm">
		<aui:input name="fooId" field="fooId" type="hidden" />
		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input name="field1">
				</aui:input>
			</aui:fieldset>
		</aui:fieldset-group>
		
		<aui:button-row>
			<aui:button cssClass="btn btn-primary" type="submit" />
		</aui:button-row>
		
	</aui:form>
</div>