package com.liferay.training.foo.web.asset.model;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.training.foo.constants.FooConstants;
import com.liferay.training.foo.constants.FooPortletKeys;
import com.liferay.training.foo.constants.MVCCommandNames;
import com.liferay.training.foo.model.Foo;
import com.liferay.training.foo.service.FooLocalService;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;
import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true, property = "javax.portlet.name="
		+ FooPortletKeys.FOO, service = AssetRendererFactory.class)
public class FooAssetRendererFactory extends BaseAssetRendererFactory<Foo> {

	public static final String CLASS_NAME = Foo.class.getName();
	public static final String TYPE = "foo";

	public FooAssetRendererFactory() {
		setClassName(CLASS_NAME);
		setLinkable(true);
		setPortletId(FooPortletKeys.FOO);
		setSearchable(true);
	}

	@Override
	public AssetRenderer<Foo> getAssetRenderer(long classPK, int type) throws PortalException {
		Foo foo = _fooLocalService.getFoo(classPK);
		FooAssetRenderer fooAssetRenderer = new FooAssetRenderer(foo);
		fooAssetRenderer.setAssetDisplayPageFriendlyURLProvider(_assetDisplayPageFriendlyURLProvider);
		fooAssetRenderer.setAssetRendererType(type);
//		fooAssetRenderer.setServletContext(_servletContext);
		return fooAssetRenderer;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public PortletURL getURLAdd(
	LiferayPortletRequest liferayPortletRequest,
	LiferayPortletResponse liferayPortletResponse, long classTypeId) {
	PortletURL portletURL = _portal.getControlPanelPortletURL(
	liferayPortletRequest, getGroup(liferayPortletRequest),
	FooPortletKeys.FOO, 0, 0, PortletRequest.RENDER_PHASE);
	portletURL.setParameter("mvcRenderCommandName", MVCCommandNames.EDIT_ITEM);
	return portletURL;
	}

	@Override
	public PortletURL getURLView(LiferayPortletResponse liferayPortletResponse, WindowState windowState) {
		LiferayPortletURL liferayPortletURL = liferayPortletResponse
				.createLiferayPortletURL(FooPortletKeys.FOO, PortletRequest.RENDER_PHASE);
		try {
			liferayPortletURL.setWindowState(windowState);
		} catch (WindowStateException wse) {
		}
		return liferayPortletURL;
	}

	@Override
	public boolean hasAddPermission(PermissionChecker permissionChecker, long groupId, long classTypeId)
			throws Exception {
		return _portletResourcePermission.contains(permissionChecker, groupId, ActionKeys.ADD_ENTRY);
	}

	@Override
	public boolean hasPermission(PermissionChecker permissionChecker, long classPK, String actionId) throws Exception {
		return _fooModelResourcePermission.contains(permissionChecker, classPK, actionId);
	}

	@Reference
	private AssetDisplayPageFriendlyURLProvider _assetDisplayPageFriendlyURLProvider;
	
	@Reference
	private FooLocalService _fooLocalService;
	
	@Reference(target = "(model.class.name=com.liferay.training.foo.model.Foo)")
	private ModelResourcePermission<Foo> _fooModelResourcePermission;
	
	@Reference
	private Portal _portal;
	
	@Reference(target = "(resource.name=" + FooConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;
	
	@Reference
	private PortletURLFactory _portletURLFactory;
	
//	@Reference(target = "(osgi.web.symbolicname=com.liferay.training.foo.web)")
//	private ServletContext _servletContext;

}
