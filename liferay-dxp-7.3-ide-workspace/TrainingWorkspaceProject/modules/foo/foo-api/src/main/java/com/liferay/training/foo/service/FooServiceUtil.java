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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.training.foo.model.Foo;

import java.util.List;

/**
 * Provides the remote service utility for Foo. This utility wraps
 * <code>com.liferay.training.foo.service.impl.FooServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see FooService
 * @generated
 */
public class FooServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.training.foo.service.impl.FooServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static Foo addFoo(
			long groupId, String field1,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addFoo(groupId, field1, serviceContext);
	}

	public static Foo getFoo(long fooId) throws PortalException {
		return getService().getFoo(fooId);
	}

	public static List<Foo> getFoosByGroupId(long groupId) {
		return getService().getFoosByGroupId(groupId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static List<Foo> searchFoo(
			long companyId, long groupId, String keywords,
			String orderFieldName, boolean orderReverse)
		throws PortalException {

		return getService().searchFoo(
			companyId, groupId, keywords, orderFieldName, orderReverse);
	}

	public static Foo updateFoo(
			long fooId, long groupId, String field1,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateFoo(fooId, groupId, field1, serviceContext);
	}

	public static FooService getService() {
		return _service;
	}

	private static volatile FooService _service;

}