package com.liferay.training.foo.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.training.foo.constants.FooPortletKeys;
import com.liferay.training.foo.constants.MVCCommandNames;
import com.liferay.training.foo.model.Foo;
import com.liferay.training.foo.service.FooService;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true, property = { "javax.portlet.name=" + FooPortletKeys.FOO,
		"mvc.command.name=" + MVCCommandNames.EDIT_ITEM }, service = MVCRenderCommand.class)
public class EditItemMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse)throws PortletException {
		
		Foo foo = null;
		long fooId = ParamUtil.getLong(renderRequest, "fooId", 0);
		if (fooId > 0) {
			try {
				foo = _fooService.getFoo(fooId);
				
			} catch (PortalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		renderRequest.setAttribute("foo", foo);
		renderRequest.setAttribute("fooClass", Foo.class);
		
		return "/edit_item.jsp";
	}
	
	@Reference
	private FooService _fooService;
}
