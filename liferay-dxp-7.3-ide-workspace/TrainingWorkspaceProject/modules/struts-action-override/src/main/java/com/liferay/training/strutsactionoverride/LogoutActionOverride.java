package com.liferay.training.strutsactionoverride;

import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.ContentTypes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.petra.string.StringPool;


import org.osgi.service.component.annotations.Component;

@Component(
	immediate = true, 
	property = {
		"path=/portal/logout"
	}, 
	service = StrutsAction.class)
public class LogoutActionOverride implements StrutsAction {

	@Override
	public String execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws Exception {
		
		String sitemap = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +

               "<sitemap><loc>https://liferay.com</loc></sitemap>";

       ServletResponseUtil.sendFile(

               httpServletRequest, httpServletResponse, null,

               sitemap.getBytes(StringPool.UTF8), ContentTypes.TEXT_XML_UTF8);
		return null;
	}

}
