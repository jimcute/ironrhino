<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#escape x as x?html><html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN" lang="zh-CN">
<head>
<title>${action.getText('login')}</title>
<@authorize ifAnyGranted="ROLE_BUILTIN_USER">
<meta http-equiv="refresh" content="0; url=${base}" />
</@authorize>
</head>
<body>
<@s.form id="login" action="check" method="post" cssClass="ajax">
	<@s.hidden id="targetUrl" name="targetUrl" />
	<@s.textfield label="%{getText('username')}" name="username" cssClass="required"/>
	<@s.password label="%{getText('password')}" name="password" cssClass="required"/>
	<@s.checkbox label="%{getText('rememberme')}" name="rememberme"/>
	<#if captchaRequired?if_exists>
	<@s.textfield label="%{getText('captcha')}" name="captcha" size="6" cssClass="autocomplete_off required captcha"/>
	</#if>
	<p>
	<@s.submit value="%{getText('login')}" theme="simple" cssClass="primary"/>
	<@button type="link" text="${action.getText('signup')}" href="${base}/signup"/>
	<@button type="link" text="${action.getText('forgot')}" href="${base}/signup/forgot"/>
	</p>
</@s.form>
</body>
</html></#escape>