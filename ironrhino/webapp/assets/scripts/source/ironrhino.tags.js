Observation.tags = function(container) {
	if (typeof $.fn.textext != 'undefined') {
		$('input.tags', container).each(function() {
			var t = $(this);
			var options = {
				plugins : 'tags prompt focus autocomplete ajax arrow',
				prompt : '...',
				autocomplete : {
					dropdownMaxHeight : '200px',
					render : function(suggestion) {
						if (typeof suggestion == 'string') {
							return suggestion;
						} else {
							return '<div value="' + suggestion.value + '">'
									+ suggestion.label + '</div>';
						}
					}
				},
				ext : {
					core : {
						serializeData : function(data) {
							return data.join(',');
						}
					},
					itemManager : {
						itemToString : function(item) {
							var str = typeof item == 'string'
									? item
									: item.value;
							return str;
						}
					}
				}
			};
			var value = t.val();
			if (value) {
				if (value.indexOf("[\"") == 0)
					options.tagsItems = $.parseJSON(value);
				else
					options.tagsItems = value.split(',');
			}
			if (t.attr('source'))
				options.ajax = {
					global : false,
					url : t.attr('source'),
					cacheResults : false,
					dataCallback : function(q) {
						return {
							'keyword' : q
						};
					}
				}
			t.val('').textext(options);
		});
	}
};