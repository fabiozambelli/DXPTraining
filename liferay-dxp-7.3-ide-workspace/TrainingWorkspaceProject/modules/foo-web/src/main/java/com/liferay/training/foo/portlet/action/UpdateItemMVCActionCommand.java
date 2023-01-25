package com.liferay.training.foo.portlet.action;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.training.foo.constants.FooPortletKeys;
import com.liferay.training.foo.constants.MVCCommandNames;
import com.liferay.training.foo.model.Foo;
import com.liferay.training.foo.service.FooService;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true, property = { "javax.portlet.name=" + FooPortletKeys.FOO,
		"mvc.command.name=" + MVCCommandNames.UPDATE_ITEM }, service = MVCActionCommand.class)
public class UpdateItemMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		ServiceContext serviceContext = ServiceContextFactory.getInstance(Foo.class.getName(), actionRequest);
		
		String field1 = ParamUtil.getString(actionRequest, "field1", StringPool.BLANK);
		
		Long fooId = ParamUtil.getLong(actionRequest, "fooId",0);
		
		Long groupId = ParamUtil.getLong(actionRequest, "groupId",0);
		
		try {
			
			_fooService.updateFoo(fooId, groupId, field1, serviceContext);
			
			sendRedirect(actionRequest, actionResponse);
			
		} catch (PortalException pe) {
			
			pe.printStackTrace();
			
			actionResponse.setRenderParameter(
					
					"mvcRenderCommandName", MVCCommandNames.EDIT_ITEM);
			
		}
		
	}

	@Reference
	protected FooService _fooService;
}
