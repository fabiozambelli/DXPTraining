package com.liferay.training.foo.web.internal.security.permission.resource;


import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.training.foo.model.Foo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true, service = FooPermission.class)

public class FooPermission {

	public static boolean contains(PermissionChecker permissionChecker, Foo foo, String actionId)
			throws PortalException {
		return _fooModelResourcePermission.contains(permissionChecker, foo, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long fooId, String actionId)
			throws PortalException {
			return _fooModelResourcePermission.contains(
			permissionChecker, fooId, actionId);
			}

	@Reference(target = "(model.class.name=com.liferay.training.foo.model.Foo)", unbind = "-")
	protected void setEntryModelPermission(ModelResourcePermission<Foo> modelResourcePermission) {
		_fooModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<Foo> _fooModelResourcePermission;
}
