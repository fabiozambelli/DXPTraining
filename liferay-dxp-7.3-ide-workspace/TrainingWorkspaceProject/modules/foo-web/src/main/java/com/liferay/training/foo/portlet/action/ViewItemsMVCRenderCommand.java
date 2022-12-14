package com.liferay.training.foo.portlet.action;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.training.foo.constants.FooPortletKeys;
import com.liferay.training.foo.constants.MVCCommandNames;
import com.liferay.training.foo.model.Foo;
import com.liferay.training.foo.portlet.FooPortlet;
import com.liferay.training.foo.service.FooService;
import com.liferay.training.foo.web.display.context.ItemsManagementToolbarDisplayContext;

import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true, property = { "javax.portlet.name=" + FooPortletKeys.FOO, "mvc.command.name=/",
		"mvc.command.name=" + MVCCommandNames.VIEW_ITEMS }, service = MVCRenderCommand.class)
public class ViewItemsMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		
		ThemeDisplay themeDisplay =
				(ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		List<Foo> foos = _fooService.getFoosByGroupId(themeDisplay.getScopeGroupId());
		renderRequest.setAttribute("foos", foos);
		
		addItemsListAttributes(renderRequest);
		
		addManagementToolbarAttributes(renderRequest, renderResponse);
		
		return "/view.jsp";	
	}
	
	private void addItemsListAttributes(RenderRequest renderRequest) {
		
		ThemeDisplay themeDisplay =
				(ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		int currentPage = ParamUtil.getInteger(
				renderRequest, SearchContainer.DEFAULT_CUR_PARAM,
				SearchContainer.DEFAULT_CUR);
				
		int delta = ParamUtil.getInteger(
				renderRequest, SearchContainer.DEFAULT_DELTA_PARAM,
				SearchContainer.DEFAULT_DELTA);
		
		int start = ((currentPage > 0) ? (currentPage - 1) : 0) * delta;
		int end = start + delta;
		
		String orderByCol =
				ParamUtil.getString(renderRequest, "orderByCol", "title");
		String orderByType =
				ParamUtil.getString(renderRequest, "orderByType", "asc");
		
		
		OrderByComparator<Foo> comparator =
				OrderByComparatorFactoryUtil.create(
				"Foo", orderByCol, !("asc").equals(orderByType));
		
		String keywords = ParamUtil.getString(renderRequest, "keywords");
		
		List<Foo> foos = _fooService.getFoosByGroupId(themeDisplay.getScopeGroupId());
		
		renderRequest.setAttribute("foos", foos);

		renderRequest.setAttribute("fooCount", 2); //TODO
	}

	// Adds Clay management toolbar context object to the request.
	private void addManagementToolbarAttributes(RenderRequest renderRequest, RenderResponse renderResponse) {
		
		LiferayPortletRequest liferayPortletRequest =
				_portal.getLiferayPortletRequest(renderRequest);
				
		LiferayPortletResponse liferayPortletResponse =
				_portal.getLiferayPortletResponse(renderResponse);
		
		ItemsManagementToolbarDisplayContext itemsManagementToolbarDisplayContext = 
				new ItemsManagementToolbarDisplayContext(
						liferayPortletRequest, liferayPortletResponse,
						_portal.getHttpServletRequest(renderRequest));
		
		renderRequest.setAttribute(
				"itemsManagementToolbarDisplayContext",
				itemsManagementToolbarDisplayContext);
	}
			
	@Reference
	private Portal _portal;
	
	@Reference
	private FooService _fooService;
	
	private static final Log _log = LogFactoryUtil.getLog(FooPortlet.class);

}
