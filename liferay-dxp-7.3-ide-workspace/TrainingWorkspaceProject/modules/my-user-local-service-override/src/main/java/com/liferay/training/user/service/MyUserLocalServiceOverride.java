package com.liferay.training.user.service;

import com.liferay.portal.kernel.service.UserLocalServiceWrapper;

import java.util.List;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;

import org.osgi.service.component.annotations.Component;

/**
 * @author lena
 */
@Component(
	immediate = true,
	property = {
	},
	service = ServiceWrapper.class
)
public class MyUserLocalServiceOverride extends UserLocalServiceWrapper {

	public MyUserLocalServiceOverride() {
		super(null);
	}

	/* (non-Javadoc)
	 * @see com.liferay.portal.kernel.service.UserLocalServiceWrapper#updateScreenName(long, java.lang.String)
	 */
	@Override
	public User updateScreenName(long userId, String screenName) throws PortalException {
		System.out.println(
				"updateScreenName...");
		return super.updateScreenName(userId, screenName);
	}

	/* (non-Javadoc)
	 * @see com.liferay.portal.kernel.service.UserLocalServiceWrapper#updateUser(long, java.lang.String, java.lang.String, java.lang.String, boolean, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, byte[], java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, long, long, boolean, int, int, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, long[], long[], long[], java.util.List, long[], com.liferay.portal.kernel.service.ServiceContext)
	 */
	@Override
	public User updateUser(long userId, String oldPassword, String newPassword1, String newPassword2,
			boolean passwordReset, String reminderQueryQuestion, String reminderQueryAnswer, String screenName,
			String emailAddress, boolean hasPortrait, byte[] portraitBytes, String languageId, String timeZoneId,
			String greeting, String comments, String firstName, String middleName, String lastName, long prefixId,
			long suffixId, boolean male, int birthdayMonth, int birthdayDay, int birthdayYear, String smsSn,
			String facebookSn, String jabberSn, String skypeSn, String twitterSn, String jobTitle, long[] groupIds,
			long[] organizationIds, long[] roleIds, List<UserGroupRole> userGroupRoles, long[] userGroupIds,
			ServiceContext serviceContext) throws PortalException {
		System.out.println(
				"updateUser(1)...");
		
		if (emailAddress.contains("@gmail.com") ||
				emailAddress.contains("@yahoo.com") ||
				emailAddress.contains("@aol.com") ||
				emailAddress.contains("@hotmail.com")) {

				System.out.println(
					"You must enter a work email address. User will not be added.");

				throw new PortalException("You must enter a work email address.");
			}
		
		return super.updateUser(userId, oldPassword, newPassword1, newPassword2, passwordReset, reminderQueryQuestion,
				reminderQueryAnswer, screenName, emailAddress, hasPortrait, portraitBytes, languageId, timeZoneId, greeting,
				comments, firstName, middleName, lastName, prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
				smsSn, facebookSn, jabberSn, skypeSn, twitterSn, jobTitle, groupIds, organizationIds, roleIds, userGroupRoles,
				userGroupIds, serviceContext);
	}

	/* (non-Javadoc)
	 * @see com.liferay.portal.kernel.service.UserLocalServiceWrapper#updateUser(com.liferay.portal.kernel.model.User)
	 */
	@Override
	public User updateUser(User user) {
		System.out.println(
				"updateUser(2)...");
		return super.updateUser(user);
	}

}