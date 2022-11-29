package com.liferay.training.foo.portlet.action;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.training.foo.constants.*;
import com.liferay.training.foo.model.Foo;
import com.liferay.training.foo.portlet.FooPortlet;
import com.liferay.training.foo.service.FooService;

import java.util.List;

@Component(immediate = true, property = { "javax.portlet.name=" + FooPortletKeys.FOO, "mvc.command.name=/",
		"mvc.command.name=" + MVCCommandNames.VIEW_ITEMS }, service = MVCRenderCommand.class)
public class ViewItemsMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		
		ThemeDisplay themeDisplay =
				(ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		List<Foo> foos = _fooService.getFoosByGroupId(themeDisplay.getScopeGroupId());
		renderRequest.setAttribute("foos", foos);
		
		return "/view.jsp";	
	}

	@Reference
	private Portal _portal;
	
	@Reference
	private FooService _fooService;
	
	private static final Log _log = LogFactoryUtil.getLog(FooPortlet.class);

}
