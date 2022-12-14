package com.liferay.training.foo.portlet.action;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
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
		
		try {
			
			addItemsListAttributes(renderRequest);
			
			addManagementToolbarAttributes(renderRequest, renderResponse);

		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "/view.jsp";	
	}
	
	private void addItemsListAttributes(RenderRequest renderRequest) throws PortalException {
		
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
				ParamUtil.getString(renderRequest, "orderByCol", Field.NAME);
		String orderByType =
				ParamUtil.getString(renderRequest, "orderByType", "asc");
		
		String keywords = ParamUtil.getString(renderRequest, "keywords");
		
		List<Foo> foos = _fooService.searchFoo(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), keywords, orderByCol, !("asc").equals(orderByType));

		renderRequest.setAttribute("foos", foos);

		renderRequest.setAttribute("fooCount", Validator.isNotNull(foos)?foos.size():0); 
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
