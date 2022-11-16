package com.liferay.training.portlet.portletmodule.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.training.portlet.portletmodule.constants.PortletModulePortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name="+PortletModulePortletKeys.PORTLETMODULE,
				"mvc.command.name=handleForm"
		},
		service = MVCActionCommand.class
)
public class HandleFormMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		// TODO Auto-generated method stub
		_handleActionCommand(actionRequest);
	}

	private void _handleActionCommand(ActionRequest actionRequest) {
		System.out.println("_handleActionCommand");
		String backgroundColor = actionRequest.getParameter("backgroundColor");
		System.out.println("backgroundColor:"+backgroundColor);
	}
	
}
