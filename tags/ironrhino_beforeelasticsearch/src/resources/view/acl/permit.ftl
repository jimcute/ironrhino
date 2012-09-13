<!DOCTYPE html>
<#escape x as x?html><html>
<head>
<title>${action.getText('permit')}</title>
</head>
<body>
<form action="${getUrl(actionBaseUrl+'/input')}" method="get" class="form-inline ajax view clearfix" replacement="save" style="margin-bottom:20px;">
	<div class="control-group switch">
	<#list roles?keys as role>
	<a class="btn ajax view" href="permit/input?role=${role}" replacement="save">${roles[role]}</a>
	</#list>
	<label for="username">${action.getText('username')}:</span><input type="text" id="username" name="username"/> <button type="submit" class="btn">${action.getText('confirm')}</button>
	</div>
</form>
<div id="save">
</div>
</body>
</html></#escape>