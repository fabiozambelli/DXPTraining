package com.liferay.training.foo.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.training.foo.constants.FooPortletKeys;
import com.liferay.training.foo.constants.MVCCommandNames;
import com.liferay.training.foo.service.FooService;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true, property = { "javax.portlet.name=" + FooPortletKeys.FOO,
		"mvc.command.name=" + MVCCommandNames.DELETE_ITEM }, service = MVCActionCommand.class)
public class DeleteItemMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		// TODO Auto-generated method stub
		
		Long fooId = ParamUtil.getLong(actionRequest, "fooId",0);
		
		try {

			if (fooId>0) {
				
				_fooService.deleteFoo(fooId);

				// Set success message.

				SessionMessages.add(actionRequest, "item-deleted");
			
			} else {
				
				SessionErrors.add(actionRequest, "error.item-invalid-pk");
			}
			

		}
		catch (PortalException e) {

			// Set error message.

			SessionErrors.add(actionRequest, "error.item-service-error");

		}
	}

	@Reference
	protected FooService _fooService;
}
