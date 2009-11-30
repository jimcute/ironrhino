package org.ironrhino.core.mail;

import javax.inject.Inject;

import org.ironrhino.core.jms.MessageConsumer;

public class SimpleMailMessageConsumer implements MessageConsumer {

	@Inject
	private MailSender mailSender;

	public void consume(Object object) {
		SimpleMailMessageWrapper smmw = (SimpleMailMessageWrapper) object;
		mailSender.send(smmw.getSimpleMailMessage(), smmw.isUseHtmlFormat());
	}

	public boolean supports(Class clazz) {
		return (SimpleMailMessageWrapper.class.isAssignableFrom(clazz));
	}

}
