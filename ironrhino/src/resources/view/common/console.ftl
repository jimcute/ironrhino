<!DOCTYPE html>
<#escape x as x?html><html>
<head>
<title>${action.getText('console')}</title>
<script>
$(function(){
		$('#trigger .btn').click(function(){
			var t = $(this);
			$.ajax({
				type:'POST',
				url:'<@url value="${actionBaseUrl}/executeJson"/>',
				data:{
					expression : $(this).data('expression')||$(this).text(),
					scope: $(this).data('scope')
				},
				beforeSend:function(){
					t.prop('disabled',true);
				},
				success:function(data){
					if(data && data.actionErrors){
						alert(data.actionErrors[0]);
					}else{
						alert(MessageBundle.get('success'));
					}
					t.prop('disabled',false);
				},
				error:function(data){
					alert(MessageBundle.get('error'));
					t.prop('disabled',false);
				}
			});
		});
		<#if printSetting??>
		$('#switch input:checkbox').change(function(e){
			var t = this;
			var key = t.name;
			var value = t.checked;
			$.post('<@url value="${actionBaseUrl}/executeJson"/>',
								{
								expression : 'settingControl.setValue("'+key+'","'+value+'")'
								}
								,function(data){
									if(data && data.actionErrors){
										$(t).closest('.switch').bootstrapSwitch('toggleState');
										alert(data.actionErrors[0]);
										return;
									}
								});
		});
		</#if>
							
});
</script>
</head>
<body>
<@s.form id="form" action="console" method="post" cssClass="ajax focus form-inline well">
	<span>${action.getText('expression')}:<@s.textfield theme="simple" id="expression" name="expression" cssClass="input-xxlarge"/></span>
	<span>${action.getText('scope')}:<@s.select theme="simple" id="scope" name="scope" cssClass="input-medium" list="@org.ironrhino.core.metadata.Scope@values()"/></span>
	<@s.submit id="submit" theme="simple" value="%{getText('confirm')}" />
</@s.form>
<hr/>
<div id="trigger">
	<ul class="thumbnails">
	<#assign triggers = statics['org.ironrhino.core.util.ApplicationContextUtils'].getBean('applicationContextConsole').getTriggers()>
	<#list triggers.keySet() as expression>
	<li class="span4">
	<button type="button" class="btn btn-block" data-scope="${triggers[expression]?string}"  data-expression="${expression}">${action.getText(expression)}</button>
	</li>
	</#list>
	</ul>
</div>
<hr/>
<#if printSetting??>
<div id="switch">
	<style scoped>
	div.key{
		text-align: right;
		line-height: 30px;
		font-weight: bold;
	}
	</style>
	<ul class="thumbnails">
	<#assign settings = statics['org.ironrhino.core.util.ApplicationContextUtils'].getBean('settingControl').getAllBooleanSettings()>
	<#list settings as setting>
	<li class="span4">
	<div class="row-fluid">
	<div class="span6 key"<#if setting.description?has_content> title="${setting.description}"</#if>>${action.getText(setting.key)}</div>
	<div class="span6"><div class="switch" data-on-label="${action.getText('ON')}" data-off-label="${action.getText('OFF')}"><input type="checkbox" name="${setting.key}"<#if setting.value=='true'> checked="checked"</#if>></div></div>
	</div>
	</li>
	</#list>
	</ul>
</div>
<hr/>
</#if>
</body>
</html></#escape>