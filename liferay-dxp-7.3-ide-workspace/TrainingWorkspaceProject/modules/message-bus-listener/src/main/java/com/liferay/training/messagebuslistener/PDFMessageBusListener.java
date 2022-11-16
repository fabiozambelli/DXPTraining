package com.liferay.training.messagebuslistener;

import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.repository.model.FileVersion;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

@Component (
    immediate = true,
    property = {
    	"destination.name=" + DestinationNames.DOCUMENT_LIBRARY_PDF_PROCESSOR
    },
    service = MessageListener.class
)
public class PDFMessageBusListener implements MessageListener {

	private static Log _log = LogFactoryUtil.getLog(PDFMessageBusListener.class);

	
	@Override
	public void receive(Message message)
		throws MessageListenerException {

		Object[] payload = (Object[])message.getPayload();
		
		FileVersion fileVersion = (FileVersion)payload[1];

		_log.info("Title=" + fileVersion.getTitle());
	}

}
