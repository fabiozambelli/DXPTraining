package com.liferay.training.foo.web.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.BaseManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.training.foo.constants.FooPortletKeys;
import com.liferay.training.foo.constants.MVCCommandNames;

import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;

public class ItemsManagementToolbarDisplayContext extends BaseManagementToolbarDisplayContext {

	private final PortalPreferences _portalPreferences;
	
	private final ThemeDisplay _themeDisplay;
	
	public ItemsManagementToolbarDisplayContext(LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, HttpServletRequest httpServletRequest) {
		
		super(liferayPortletRequest, liferayPortletResponse, httpServletRequest);
		
		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
				liferayPortletRequest);
		
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);
	}

	// Returns the creation menu for the toolbar
	public CreationMenu getCreationMenu() {
		
		return new CreationMenu() {
			{
				addDropdownItem( dropdownItem -> {
					dropdownItem.setHref(
							liferayPortletResponse.createRenderURL(),
							"mvcRenderCommandName", MVCCommandNames.EDIT_ITEM,
							"redirect", currentURLObj.toString());
					dropdownItem.setLabel(LanguageUtil.get(request, "add-item"));
				});
			}
		};
	}
	
	@Override
	public String getClearResultsURL() {
		return getSearchActionURL();
	}
	
	// Returns the items list display style.
	// Current selection is stored in portal preferences.
	public String getDisplayStyle() {
		
		String displayStyle = ParamUtil.getString(request, "displayStyle");
		if (Validator.isNull(displayStyle)) {
			displayStyle = _portalPreferences.getValue(
			FooPortletKeys.FOO, "items-display-style",
			"descriptive");
		} else {
			_portalPreferences.setValue(
			FooPortletKeys.FOO, "items-display-style",
			displayStyle);
			request.setAttribute(
			WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
		}
		return displayStyle;
	}
	
	public String getOrderByCol() {
		return ParamUtil.getString(request, "orderByCol", Field.NAME);
	}
	
	public String getOrderByType() {
		return ParamUtil.getString(request, "orderByType", "asc");
	}
	
	@Override
	public String getSearchActionURL() {
		PortletURL searchURL = liferayPortletResponse.createRenderURL();
		searchURL.setProperty(
				"mvcRenderCommandName", MVCCommandNames.VIEW_ITEMS);
		String navigation = ParamUtil.getString(
				request, "navigation", "entries");
				searchURL.setParameter("navigation", navigation);
				searchURL.setParameter("orderByCol", getOrderByCol());
				searchURL.setParameter("orderByType", getOrderByType());
		return searchURL.toString();
	}

	// Returns the view type options (card, list, table).
	@Override
	public List<ViewTypeItem> getViewTypeItems() {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();
		portletURL.setParameter(
				"mvcRenderCommandName", MVCCommandNames.VIEW_ITEMS);
		int delta =
				ParamUtil.getInteger(request, SearchContainer.DEFAULT_DELTA_PARAM);
		if (delta > 0) {
			portletURL.setParameter("delta", String.valueOf(delta));
		}
		String orderByCol = ParamUtil.getString(request, "orderByCol", Field.NAME);
		String orderByType = ParamUtil.getString(request, "orderByType", "asc");
		portletURL.setParameter("orderByCol", orderByCol);
		portletURL.setParameter("orderByType", orderByType);
		
		int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);
		if (cur > 0) {
			portletURL.setParameter("cur", String.valueOf(cur));
		}
		
		return new ViewTypeItemList(portletURL, getDisplayStyle()) {
			{
			addCardViewTypeItem();
			addListViewTypeItem();
			addTableViewTypeItem();
			}
		};
	}
	
	// Return the option items for the sort column menu.
	@Override
	protected List<DropdownItem> getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				add(dropdownItem -> {
					dropdownItem.setActive(Field.NAME.equals(getOrderByCol()));
					dropdownItem.setHref(
						_getCurrentSortingURL(), "orderByCol", Field.NAME);
					dropdownItem.setLabel(LanguageUtil.get(request, "title"));
				});
				
				add(dropdownItem -> {
					dropdownItem.setActive(
							Field.CREATE_DATE.equals(getOrderByCol()));
					dropdownItem.setHref(
							_getCurrentSortingURL(), "orderByCol", Field.CREATE_DATE);
					dropdownItem.setLabel(
							LanguageUtil.get(request, "create-date"));
				});
			}
		};
	}
	
	// Returns the current sorting URL.
	private PortletURL _getCurrentSortingURL() throws PortletException {
		
		PortletURL sortingURL = PortletURLUtil.clone(currentURLObj, liferayPortletResponse);
		
		sortingURL.setParameter("mvcRenderCommandName", MVCCommandNames.VIEW_ITEMS);
		
		// Reset current page.
		sortingURL.setParameter(SearchContainer.DEFAULT_CUR_PARAM, "0");
		String keywords = ParamUtil.getString(request, "keywords");
		
		if (Validator.isNotNull(keywords)) {
			sortingURL.setParameter("keywords", keywords);
		}
		
		return sortingURL;
	}
}
