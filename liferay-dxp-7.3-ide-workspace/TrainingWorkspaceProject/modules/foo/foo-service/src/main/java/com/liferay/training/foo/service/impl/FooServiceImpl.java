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

package com.liferay.training.foo.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionHelper;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.training.foo.model.Foo;
import com.liferay.training.foo.service.FooLocalService;
import com.liferay.training.foo.service.FooService;
import com.liferay.training.foo.service.base.FooServiceBaseImpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=foo", "json.web.service.context.path=Foo"
	},
	service = AopService.class
)
public class FooServiceImpl extends FooServiceBaseImpl {
	
	@Reference(
			policy = ReferencePolicy.DYNAMIC,
			policyOption = ReferencePolicyOption.GREEDY,
			target = "(model.class.name=com.liferay.training.foo.model.Foo)"
	)
	private volatile ModelResourcePermission<Foo>
			_fooModelResourcePermission;
	
	public Foo addFoo(long groupId, String field1, ServiceContext serviceContext) throws PortalException {
		
		ModelResourcePermissionHelper.check(
				_fooModelResourcePermission, getPermissionChecker(),
				serviceContext.getScopeGroupId(), 0,
				ActionKeys.ADD_ENTRY);
		
		return fooLocalService.addFoo(groupId, field1, serviceContext);
	}
	
	public Foo updateFoo(long fooId, long groupId, String field1, ServiceContext serviceContext) throws PortalException {
		
		_fooModelResourcePermission.check(
				getPermissionChecker(),
				fooLocalService.getFoo(fooId), ActionKeys.UPDATE);
		
		return fooLocalService.updateFoo(fooId, groupId, field1, serviceContext);
	}

	public List<Foo> getFoosByGroupId(long groupId) {
		
		return fooLocalService.getFoosByGroupId(groupId);
	}
	
	public Foo getFoo(long fooId) throws PortalException {
				
		_fooModelResourcePermission.check(
				getPermissionChecker(), fooLocalService.fetchFoo(fooId), ActionKeys.VIEW);
		
		return fooLocalService.getFoo(fooId);
	}
	
	public List<Foo>  searchFoo(long companyId, long groupId, String keywords, String orderFieldName, boolean orderReverse) throws PortalException {
		return fooLocalService.searchFoo(companyId, groupId, keywords, orderFieldName, orderReverse);
	}
}