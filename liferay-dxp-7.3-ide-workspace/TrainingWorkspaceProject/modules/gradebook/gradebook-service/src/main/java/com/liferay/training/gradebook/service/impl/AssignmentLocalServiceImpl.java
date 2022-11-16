/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.training.gradebook.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.training.gradebook.model.Assignment;
import com.liferay.training.gradebook.service.base.AssignmentLocalServiceBaseImpl;
import com.liferay.training.gradebook.validator.AssignmentValidator;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.training.gradebook.model.Assignment",
	service = AopService.class
)
public class AssignmentLocalServiceImpl extends AssignmentLocalServiceBaseImpl {
	
	public Assignment addAssignment(long groupId, Map<Locale, String> titleMap, String description, Date dueDate,
			ServiceContext serviceContext) throws PortalException {
		
		// Validate assignment parameters.
		_assignmentValidator.validate(titleMap, description, dueDate);

		// Get group and same time validate that it exists.

		Group group = groupLocalService.fetchGroup(groupId);

		long userId = serviceContext.getUserId();

		User user = userLocalService.getUser(userId);

		// Generate primary key for the assignment.

		long assignmentId = counterLocalService.increment(Assignment.class.getName());

		// Create assigment.

		Assignment assignment = createAssignment(assignmentId);

		// Fill the assignment

		assignment.setCompanyId(group.getCompanyId());
		assignment.setGroupId(groupId);
		assignment.setUserId(userId);
		assignment.setTitleMap(titleMap);
		assignment.setDueDate(dueDate);
		assignment.setDescription(description);
		assignment.setUserName(user.getScreenName());
		assignment.setCreateDate(serviceContext.getCreateDate(new Date()));
		assignment.setModifiedDate(serviceContext.getModifiedDate(new Date()));

		// Persist assignment.

		assignment = super.addAssignment(assignment);
		
		// Add permission resources.
		boolean portletActions = false;
		boolean addGroupPermissions = true;
		boolean addGuestPermissions = true;
		resourceLocalService.addResources(
		group.getCompanyId(), groupId, userId, Assignment.class.getName(),
		assignment.getAssignmentId(), portletActions, addGroupPermissions,
		addGuestPermissions);

		updateAsset(assignment, serviceContext);
		
		return assignment;
	}

	public Assignment updateAssignment(long assignmentId, Map<Locale, String> titleMap, String description, Date dueDate,
			ServiceContext serviceContext) throws PortalException {

		_assignmentValidator.validate(titleMap, description, dueDate);
		
		// Get Assignment

		Assignment assignment = getAssignment(assignmentId);

		// Update the changes to assignment

		assignment.setModifiedDate(new Date());
		assignment.setTitleMap(titleMap);
		assignment.setDueDate(dueDate);
		assignment.setDescription(description);

		assignment = super.updateAssignment(assignment);

		updateAsset(assignment, serviceContext);
		
		return assignment;
	}
	
	public Assignment deleteAssignment(Assignment assignment) throws PortalException {
		// Delete permission resources.
		resourceLocalService.deleteResource(
		assignment, ResourceConstants.SCOPE_INDIVIDUAL);
				
		assetEntryLocalService.deleteEntry(
				Assignment.class.getName(), assignment.getAssignmentId());
		
		// Delete the Assignment
		return super.deleteAssignment(assignment);
	}

	public List<Assignment> getAssignmentsByGroupId(long groupId) {

		return assignmentPersistence.findByGroupId(groupId);
	}

	public List<Assignment> getAssignmentsByGroupId(long groupId, int start, int end) {

		return assignmentPersistence.findByGroupId(groupId, start, end);
	}

	public List<Assignment> getAssignmentsByGroupId(long groupId, int start, int end,
			OrderByComparator<Assignment> orderByComparator) {

		return assignmentPersistence.findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	 * Gets assignments by keywords and status.
	 * 
	 * This example uses dynamic queries.
	 * 
	 * @param groupId
	 * @param keywords
	 * @param start
	 * @param end
	 * @param status
	 * @param orderByComparator
	 * @return
	 */
	public List<Assignment> getAssignmentsByKeywords(long groupId, String keywords, int start, int end, int status,
			OrderByComparator<Assignment> orderByComparator) {

		DynamicQuery assignmentQuery = dynamicQuery().add(RestrictionsFactoryUtil.eq("groupId", groupId));

		if (status != WorkflowConstants.STATUS_ANY) {
			assignmentQuery.add(RestrictionsFactoryUtil.eq("status", status));
		}

		if (Validator.isNotNull(keywords)) {
			Disjunction disjunctionQuery = RestrictionsFactoryUtil.disjunction();
			disjunctionQuery.add(RestrictionsFactoryUtil.like("title", "%" + keywords + "%"));
			disjunctionQuery.add(RestrictionsFactoryUtil.like("description", "%" + keywords + "%"));
			assignmentQuery.add(disjunctionQuery);
		}

		return assignmentLocalService.dynamicQuery(assignmentQuery, start, end, orderByComparator);
	}

	public int getAssignmentsCountByGroupId(long groupId) {

		return assignmentPersistence.countByGroupId(groupId);
	}

	/**
	 * Get assignment count by keywords and status.
	 * 
	 * This example uses dynamic queries.
	 * 
	 * @param groupId
	 * @param keywords
	 * @param status
	 * @return
	 */
	public long getAssignmentsCountByKeywords(long groupId, String keywords, int status) {

		DynamicQuery assignmentQuery = dynamicQuery().add(RestrictionsFactoryUtil.eq("groupId", groupId));

		if (status != WorkflowConstants.STATUS_ANY) {
			assignmentQuery.add(RestrictionsFactoryUtil.eq("status", status));
		}

		if (Validator.isNotNull(keywords)) {
			Disjunction disjunctionQuery = RestrictionsFactoryUtil.disjunction();
			disjunctionQuery.add(RestrictionsFactoryUtil.like("title", "%" + keywords + "%"));
			disjunctionQuery.add(RestrictionsFactoryUtil.like("description", "%" + keywords + "%"));
			assignmentQuery.add(disjunctionQuery);
		}

		return assignmentLocalService.dynamicQueryCount(assignmentQuery);
	}
	
	private void updateAsset(Assignment assignment, ServiceContext serviceContext) throws PortalException {
		
		assetEntryLocalService.updateEntry(serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				assignment.getCreateDate(), assignment.getModifiedDate(), Assignment.class.getName(),
				assignment.getAssignmentId(), assignment.getUserUuid(), 0, serviceContext.getAssetCategoryIds(),
				serviceContext.getAssetTagNames(), true, true, assignment.getCreateDate(), null, null, null,
				ContentTypes.TEXT_HTML, assignment.getTitle(serviceContext.getLocale()), assignment.getDescription(),
				null, null, null, 0, 0, serviceContext.getAssetPriority());
	}

	@Override
	public Assignment updateAssignment(Assignment assignment) {

		throw new UnsupportedOperationException(
				"please use instead updateAssignment(assignmentId, titleMap, description, dueDate, serviceContext)");
	}

	@Override
	public Assignment addAssignment(Assignment assignment) {

		throw new UnsupportedOperationException("please use instead addAssignment(Assignment, ServiceContext)");
	}
	
	@Reference
	AssignmentValidator _assignmentValidator;
}

