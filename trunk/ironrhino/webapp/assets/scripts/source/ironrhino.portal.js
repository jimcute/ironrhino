(function($) {
	$.fn.portal = function() {
		this.each(function() {
			$('.portal-column', this).sortable({
						connectWith : '.portal-column'
					});
			$('.portlet', this)
					.addClass('ui-widget ui-widget-content ui-helper-clearfix ui-corner-all')
					.find('.portlet-header')
					.addClass('ui-widget-header ui-corner-all')
					.prepend('<span class="ui-icon ui-icon-minusthick"></span>')
					.end();
			$('.portlet-header .ui-icon', this).click(function() {
				$(this).toggleClass('ui-icon-minusthick')
						.toggleClass('ui-icon-plusthick');
				$(this).parents('.portlet:first').find('.portlet-content')
						.toggle();
			});
			$('.portal-column', this).disableSelection();
		});
		return this;
	};
})(jQuery);

Observation.portal = function(container) {
	$('.portal', container).portal();
};