package com.liferay.training.foo.portlet;

import com.liferay.training.foo.constants.FooPortletKeys;
import com.liferay.adaptive.media.exception.AMRuntimeException.IOException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author lena
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.training",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=false",
		"javax.portlet.display-name=Foo",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + FooPortletKeys.FOO,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class FooPortlet extends MVCPortlet {
	
	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException, java.io.IOException {
		
		String mvcRenderCommandName = ParamUtil.getString(
				renderRequest, "mvcRenderCommandName", "/");
		_log.info("mvcRenderCommandName:"+mvcRenderCommandName);

		MVCRenderCommand mvcRenderCommand =
				getRenderMVCCommandCache().getMVCCommand(mvcRenderCommandName);
		_log.info("mvcRenderCommand:"+mvcRenderCommand);

		String _mvcPath = mvcRenderCommand.render(
				renderRequest, renderResponse);
		_log.info("_mvcPath:"+_mvcPath);
		
		String mvcPath = ParamUtil.getString(renderRequest, "mvcPath");
		_log.info("mvcPath:"+mvcPath);
		
		super.render(renderRequest, renderResponse);
	}
	
	private static final Log _log = LogFactoryUtil.getLog(FooPortlet.class);
}