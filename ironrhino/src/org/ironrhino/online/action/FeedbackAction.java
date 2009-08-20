package org.ironrhino.online.action;

import org.ironrhino.common.util.AuthzUtils;
import org.ironrhino.core.ext.struts.BaseAction;
import org.ironrhino.core.metadata.AutoConfig;
import org.ironrhino.core.metadata.Captcha;
import org.ironrhino.core.service.BaseManager;
import org.ironrhino.online.model.Account;
import org.ironrhino.online.model.Feedback;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

@AutoConfig(namespace = "/")
public class FeedbackAction extends BaseAction {

	private static final long serialVersionUID = 7297247656451307758L;

	private Feedback feedback;

	private transient BaseManager<Feedback> baseManager;

	public Feedback getFeedback() {
		return feedback;
	}

	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}

	public void setBaseManager(BaseManager<Feedback> baseManager) {
		this.baseManager = baseManager;
	}

	@Override
	public String input() {
		Account account = AuthzUtils.getUserDetails(Account.class);
		if (account != null) {
			feedback = new Feedback();
			feedback.setName(account.getName());
			feedback.setEmail(account.getEmail());
			feedback.setPhone(account.getPhone());
		}
		return SUCCESS;
	}

	@Override
	@Captcha
	@InputConfig(methodName = "input")
	@Validations(requiredStrings = {
			@RequiredStringValidator(type = ValidatorType.FIELD, fieldName = "feedback.name", trim = true, key = "feedback.name.required", message = "请输入您的名字"),
			@RequiredStringValidator(type = ValidatorType.FIELD, fieldName = "feedback.subject", trim = true, key = "feedback.subject.required", message = "请输入主题") }, emails = { @EmailValidator(type = ValidatorType.FIELD, fieldName = "feedback.email", key = "feedback.email.invalid", message = "请输入正确的email") })
	public String execute() {
		if (feedback != null) {
			Account account = AuthzUtils.getUserDetails(Account.class);
			if (account != null)
				feedback.setUsername(account.getUsername());
			baseManager.save(feedback);
			addActionMessage(getText("feedback.successfully"));
		}
		return SUCCESS;
	}
}
