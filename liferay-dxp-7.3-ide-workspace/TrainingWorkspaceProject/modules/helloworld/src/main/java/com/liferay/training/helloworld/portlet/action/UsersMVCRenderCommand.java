package com.liferay.training.helloworld.portlet.action;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.training.helloworld.constants.HelloWorldPortletKeys;

import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

@Component(immediate = true, property = { "javax.portlet.name=" + HelloWorldPortletKeys.HELLOWORLD, "mvc.command.name=/",
		"mvc.command.name=" + "/helloworld/users/view" }, service = MVCRenderCommand.class)
public class UsersMVCRenderCommand implements MVCRenderCommand  {

	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		// TODO Auto-generated method stub
		
		List<User> users = UserLocalServiceUtil.getUsers(0, UserLocalServiceUtil.getUsersCount());
		renderRequest.setAttribute("users", users);
		renderRequest.setAttribute("usersCount", UserLocalServiceUtil.getUsersCount());
	
		return "/view.jsp";
	}

}
