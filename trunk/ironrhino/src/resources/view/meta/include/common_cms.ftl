<#macro includePage path abbr=0>
<#local pageManager=statics['org.ironrhino.core.util.ApplicationContextUtils'].getBean('pageManager')>
<#if (Parameters.preview!)=='true' && statics['org.ironrhino.core.util.AuthzUtils'].authorize("","ROLE_ADMINISTRATOR","","")>
<#local page=pageManager.getDraftByPath(path)!>
<#if !page.content?has_content>
<#local page=pageManager.getByPath(path)!>
</#if>
<#else>
<#local page=pageManager.getByPath(path)!>
</#if>
<#if page??&&page.content??>
<#if abbr gt 0>
<#local _content=statics['org.ironrhino.core.util.HtmlUtils'].abbr(page.content, abbr)>
<#else>
<#local _content=page.content>
</#if>
<#local content=_content?interpret>
<#local designMode=(Parameters.designMode!)=='true'&&abbr==0&&statics['org.ironrhino.core.util.AuthzUtils'].authorize("","ROLE_ADMINISTRATOR","","")>
<#if designMode>
<div class="editme" url="<@url value="/common/page/editme?id=${page.id}"/>" name="page.content">
</#if>
<@content/>
<#if designMode>
</div>
</#if>
</#if>
</#macro>