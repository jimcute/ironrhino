<#assign requestURI=request.requestURI?substring(request.contextPath?length)/>
<#assign modernBrowser = false/>
<#assign ua = request.getAttribute('userAgent')/>
<#if ua?? && (ua.name!='msie' || ua.majorVersion gt 8)>
<#assign modernBrowser = true/>
</#if>
<#if modernBrowser>
<!DOCTYPE html>
<html>
<#else>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
</#if>
<#compress><#escape x as x?html>
<head>
<title><#noescape>${title}</#noescape></title>
<#if modernBrowser>
<meta charset="utf-8">
<#else>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</#if>
<#if request.contextPath!=''>
<meta name="context_path" content="${request.contextPath}" />
</#if>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" href="<@url value="/assets/images/favicon.ico"/>" />
<link href="<@url value="/assets/styles/ironrhino${modernBrowser?string('-min','')}.css"/>" media="all" rel="stylesheet" type="text/css" />
<#if !modernBrowser><link href="<@url value="/assets/styles/ie.css"/>" media="all" rel="stylesheet" type="text/css" /></#if>
<script src="<@url value="/assets/scripts/ironrhino${modernBrowser?string('-min','')}.js"/>" type="text/javascript"<#if !head?contains('</script>')> defer</#if>></script>
<#include "include/assets.ftl"/>
<#noescape>${head}</#noescape>
</head>

<body class="render-location-qrcode">

<@authorize ifAnyGranted="ROLE_BUILTIN_USER">
<div class="navbar navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container">
    	<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
		</a>
		<#include "include/brand.ftl"/>
		<div class="btn-group pull-right">
			<#assign user = authentication("principal")>
	        <a href="#" class="btn dropdown-toggle" data-toggle="dropdown">
	          <i class="icon-user"></i>${user?string} <span class="caret"></span>
	        </a>
	        <ul class="dropdown-menu">
	          <li><a href="<@url value="${ssoServerBase!}/user/profile"/>" class="popmodal">${action.getText('profile')}</a></li>
	          <#if !user.getAttribute('oauth_provider')??>
	          <li><a href="<@url value="${ssoServerBase!}/user/password"/>" class="popmodal">${action.getText('change')}${action.getText('password')}</a></li>
	          </#if>
	          <li class="divider"></li>
	          <li><a href="<@url value="${ssoServerBase!}/logout"/>">${action.getText('logout')}</a></li>
	        </ul>
		</div>
		<div class="nav-collapse">
	        <#include "include/nav.ftl"/>
      </div>
    </div>
  </div>
</div>
</@authorize>
<@authorize ifNotGranted="ROLE_BUILTIN_USER">
<div class="container">
<h1 style="text-align:center;margin:10px 0;">
${title}
</h1>
</div>
</@authorize>

<div id="content" class="container">
<#if action.hasActionMessages() || action.hasActionErrors()>
<div id="message">
<@s.actionerror />
<@s.actionmessage />
</div>
</#if>
<#noescape>${body}</#noescape>
</div>


</body>
</html></#escape></#compress>
