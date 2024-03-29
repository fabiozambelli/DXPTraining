package com.liferay.training.foo.workflow;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.BaseWorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.training.foo.model.Foo;
import com.liferay.training.foo.service.FooLocalService;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true, service = WorkflowHandler.class)
public class FooWorkflowHandler extends BaseWorkflowHandler<Foo>  {

	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return Foo.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		// TODO Auto-generated method stub
        return _resourceActions.getModelResource(locale, getClassName());
    }

	@Override
	public Foo updateStatus(int status, Map<String, Serializable> workflowContext) throws PortalException {
		// TODO Auto-generated method stub
		
		long userId = GetterUtil.getLong(
	            (String)workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
		
        long resourcePrimKey = GetterUtil.getLong(
            (String)workflowContext.get(
                WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));
        
        ServiceContext serviceContext = (ServiceContext)workflowContext.get(
            "serviceContext");

        return _fooLocalService.updateStatus(
        		userId, resourcePrimKey, status, serviceContext);
	}
	
	@Reference(unbind = "-")
    protected void setResourceActions(ResourceActions resourceActions) {

        _resourceActions = resourceActions;
    }

    private ResourceActions _resourceActions;

    @Reference(unbind = "-")
    protected void setFooLocalService(
        FooLocalService fooLocalService) {

    	_fooLocalService = fooLocalService;
    }

    private FooLocalService _fooLocalService;
}
