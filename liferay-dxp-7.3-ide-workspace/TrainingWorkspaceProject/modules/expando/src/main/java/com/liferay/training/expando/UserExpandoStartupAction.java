package com.liferay.training.expando;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

/**
 * @author Liferay
 */
@Component(
	immediate = true, 
	property = {"key=application.startup.events"},
	service = LifecycleAction.class
)
public class UserExpandoStartupAction implements LifecycleAction {

	@Override
	public void processLifecycleEvent(LifecycleEvent lifecycleEvent)
		throws ActionException {

		_log.log(LogService.LOG_INFO, "application.startup.events=" + lifecycleEvent);

		long companyId = CompanyThreadLocal.getCompanyId();

		ExpandoTable expandoTable = null;
		try {
			expandoTable = _expandoTableLocalService.getDefaultTable(
				companyId, User.class.getName());
			
			if (expandoTable == null) {
				expandoTable = _expandoTableLocalService.addDefaultTable(
					companyId, User.class.getName());
			}
			
			
		} 
		catch (PortalException pe) {
			if (expandoTable == null) {
				try {
					expandoTable = _expandoTableLocalService.addDefaultTable(
						companyId, User.class.getName());
				} catch (PortalException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		ExpandoColumn externalIdColumn = null;
		try {
			
			externalIdColumn = _expandoColumnLocalService.getColumn(
				expandoTable.getTableId(), "linkedin_profile_id");
			
			if (externalIdColumn == null) {
				externalIdColumn = _expandoColumnLocalService.addColumn(
					expandoTable.getTableId(), "linkedin_profile_id", ExpandoColumnConstants.STRING);
			}
		} 
		catch (PortalException pe) {
			_log.log(LogService.LOG_ERROR, pe.getMessage(), pe);
		}
	}

    @Reference
    private ExpandoColumnLocalService _expandoColumnLocalService;

    @Reference
    private ExpandoTableLocalService _expandoTableLocalService;

    @Reference
    private ExpandoValueLocalService _expandoValueLocalService;
    
    @Reference
	private LogService _log;

	@Reference
	private UserLocalService _userLocalService;
}