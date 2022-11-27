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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.training.foo.model.Foo;
import com.liferay.training.foo.service.base.FooLocalServiceBaseImpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(property = "model.class.name=com.liferay.training.foo.model.Foo", service = AopService.class)
public class FooLocalServiceImpl extends FooLocalServiceBaseImpl {

	public Foo addFoo(long groupId, String field1, ServiceContext serviceContext) throws PortalException {
		
		long fooId = counterLocalService.increment(Foo.class.getName());
		
		Foo foo = createFoo(fooId);
		foo.setGroupId(groupId);
		foo.setField1(field1);
		
		return super.addFoo(foo);
	}
	
	public Foo updateFoo(long fooId, long groupId, String field1, ServiceContext serviceContext) throws PortalException {
		
		Foo foo = getFoo(fooId);
		foo.setGroupId(groupId);
		foo.setField1(field1);
		
		return super.updateFoo(foo);
	}
	
	public List<Foo> getFoosByGroupId(long groupId) {
		return fooPersistence.findByGroupId(groupId);
	}
	
	public List<Foo> getFoosByGroupId(long groupId, int start, int end) {
		return fooPersistence.findByGroupId(groupId, start, end);
	}
	
	public List<Foo> getFoosByGroupId(long groupId, int start, int end, OrderByComparator<Foo> orderByComparator) {
		return fooPersistence.findByGroupId(groupId, start, end, orderByComparator);
	}
	
	@Override
	public Foo addFoo(Foo foo) {
		throw new UnsupportedOperationException("Not supported.");
	}
	
	@Override
	public Foo updateFoo(Foo foo) {
		throw new UnsupportedOperationException("Not supported.");
	}
	
	@Override
	public Foo getFoo(long fooId) throws PortalException {
		return super.getFoo(fooId);
	}
}