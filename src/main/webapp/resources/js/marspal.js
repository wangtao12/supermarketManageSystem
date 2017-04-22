/**
 * 
 */
(function($){
	$.fn.watch = function(callback){
		return this.each(function() {  
            //缓存以前的值  
            $.data(this, 'originVal', $(this).val());  
  
            //event  
            $(this).on('keyup paste', function() {  
                var originVal = $(this, 'originVal');  
                var currentVal = $(this).val();  
  
                if (originVal !== currentVal) {  
                    $.data(this, 'originVal', $(this).val());  
                    callback(currentVal);  
                }  
            });  
        });  
	}
})(jQuery);