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

package com.liferay.training.foo.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link FooService}.
 *
 * @author Brian Wing Shun Chan
 * @see FooService
 * @generated
 */
public class FooServiceWrapper
	implements FooService, ServiceWrapper<FooService> {

	public FooServiceWrapper(FooService fooService) {
		_fooService = fooService;
	}

	@Override
	public com.liferay.training.foo.model.Foo addFoo(
			long groupId, String field1,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fooService.addFoo(groupId, field1, serviceContext);
	}

	@Override
	public com.liferay.training.foo.model.Foo getFoo(long fooId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fooService.getFoo(fooId);
	}

	@Override
	public java.util.List<com.liferay.training.foo.model.Foo> getFoosByGroupId(
		long groupId) {

		return _fooService.getFoosByGroupId(groupId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _fooService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.training.foo.model.Foo> searchFoo(
			long companyId, long groupId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fooService.searchFoo(companyId, groupId, keywords);
	}

	@Override
	public com.liferay.training.foo.model.Foo updateFoo(
			long fooId, long groupId, String field1,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fooService.updateFoo(fooId, groupId, field1, serviceContext);
	}

	@Override
	public FooService getWrappedService() {
		return _fooService;
	}

	@Override
	public void setWrappedService(FooService fooService) {
		_fooService = fooService;
	}

	private FooService _fooService;

}