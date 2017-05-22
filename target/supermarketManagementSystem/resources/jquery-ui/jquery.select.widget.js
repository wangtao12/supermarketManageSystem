(function($){
	
	function SelectWidget(elem, settings){
		this.elem = elem;
		this.settings = settings;
		this.selectedIndex = 0;
		if(!this.settings.width){
			this.settings.width = $(elem).outerWidth();
		}
	};
	
	SelectWidget.prototype._getSelectOption = function(){
		var $this = this;
	    var optionSelected;
		$(this.elem).find('option').each(function(i, option){
			if($(option).attr('selected')=='selected'){
				$this.selectedIndex=i;
				optionSelected = option;
				return ;
			}
		});
		return optionSelected;
	}
	
	SelectWidget.prototype._createSelectBox = function(){
		var option = this._getSelectOption();
		var selectBox = $('<div class="selectBox" value="'+$(option).val()+'"><div class="selectBoxLeftNormal"></div><div onselectstart="return false;" class="selectBoxCenterNormal">'+$(option).text()+'</div><div class="selectBoxRightNormal"></div></div>');
		$(selectBox).css({'width':this.settings.width+'px','height':this.settings.height+'px'});
		$(selectBox).find('.selectBoxLeftNormal').css({'width':this.settings.leftWidth+'px','height':'100%'});
		$(selectBox).find('.selectBoxCenterNormal').css({'width':this.settings.width-this.settings.leftWidth-this.settings.rightWidth+'px','height':'100%','line-height':this.settings.height+'px'});
		$(selectBox).find('.selectBoxRightNormal').css({'width':this.settings.rightWidth+'px','height':'100%'});
		return selectBox;
	}
	
	SelectWidget.prototype._registerSelectBoxEvent = function(){
		var $this = this;
		$($this.selectBox).bind({
			mouseenter:function(event){
				$($this.selectBox).find('.selectBoxLeftNormal').addClass('selectBoxLeftOver');
				$($this.selectBox).find('.selectBoxLeftNormal').removeClass('selectBoxLeftNormal');
				$($this.selectBox).find('.selectBoxCenterNormal').addClass('selectBoxCenterOver');
				$($this.selectBox).find('.selectBoxCenterNormal').removeClass('selectBoxCenterNormal');
				$($this.selectBox).find('.selectBoxRightNormal').addClass('selectBoxRightOverl');
				$($this.selectBox).find('.selectBoxRightNormal').removeClass('selectBoxRightNormal');
			},
			mouseleave:function(event){
				$($this.selectBox).find('.selectBoxLeftOver').addClass('selectBoxLeftNormal');
				$($this.selectBox).find('.selectBoxLeftOver').removeClass('selectBoxLeftOver');
				$($this.selectBox).find('.selectBoxCenterOver').addClass('selectBoxCenterNormal');
				$($this.selectBox).find('.selectBoxCenterOver').removeClass('selectBoxCenterOver');
				$($this.selectBox).find('.selectBoxRightOverl').addClass('selectBoxRightNormal');
				$($this.selectBox).find('.selectBoxRightOverl').removeClass('selectBoxRightOverl');
				$($this.selectBox).find('.selectBoxLeftDown').addClass('selectBoxLeftNormal');
				$($this.selectBox).find('.selectBoxLeftDown').removeClass('selectBoxLeftDown');
				$($this.selectBox).find('.selectBoxCenterDown').addClass('selectBoxCenterNormal');
				$($this.selectBox).find('.selectBoxCenterDown').removeClass('selectBoxCenterDown');
				$($this.selectBox).find('.selectBoxRightDown').addClass('selectBoxRightNormal');
				$($this.selectBox).find('.selectBoxRightDown').removeClass('selectBoxRightDown');
			},
			mousedown:function(event){
				if(event.which == 3){
				    return;
				}
				event.stopPropagation();
				$('.selectBox').attr('current','false');
				$($this.selectBox).attr('current','true');
				$('.selectBox').each(function(i,elem){
					if($(elem).attr('current')=='false'){
					    $(elem).find('.optionBox').css('display','none');
						$(elem).find('.selectBoxLeftDropdown').addClass('selectBoxLeftNormal');
				        $(elem).find('.selectBoxLeftDropdown').removeClass('selectBoxLeftDropdown');
				        $(elem).find('.selectBoxCenterDropdown').addClass('selectBoxCenterNormal');
				        $(elem).find('.selectBoxCenterDropdown').removeClass('selectBoxCenterDropdown');
				        $(elem).find('.selectBoxRightDropdown').addClass('selectBoxRightNormal');
				        $(elem).find('.selectBoxRightDropdown').removeClass('selectBoxRightDropdown');
					}
				});
				$($this.selectBox).find('.selectBoxLeftDropdown').addClass('selectBoxLeftDown');
				$($this.selectBox).find('.selectBoxLeftDropdown').removeClass('selectBoxLeftDropdown');
				$($this.selectBox).find('.selectBoxCenterDropdown').addClass('selectBoxCenterDown');
				$($this.selectBox).find('.selectBoxCenterDropdown').removeClass('selectBoxCenterDropdown');
				$($this.selectBox).find('.selectBoxRightDropdown').addClass('selectBoxRightDown');
				$($this.selectBox).find('.selectBoxRightDropdown').removeClass('selectBoxRightDropdown');
				$($this.selectBox).find('.selectBoxLeftOver').addClass('selectBoxLeftDropdown');
				$($this.selectBox).find('.selectBoxLeftOver').removeClass('selectBoxLeftOver');
				$($this.selectBox).find('.selectBoxCenterOver').addClass('selectBoxCenterDropdown');
				$($this.selectBox).find('.selectBoxCenterOver').removeClass('selectBoxCenterOver');
				$($this.selectBox).find('.selectBoxRightOverl').addClass('selectBoxRightDropdown');
				$($this.selectBox).find('.selectBoxRightOverl').removeClass('selectBoxRightOverl');
				$this._toggleOptionBox();
			},
			mouseup:function(event){
				if(event.which == 3){
				    return;
				}
			    $($this.selectBox).find('.selectBoxLeftDown').addClass('selectBoxLeftOver');
				$($this.selectBox).find('.selectBoxLeftDown').removeClass('selectBoxLeftDown');
				$($this.selectBox).find('.selectBoxCenterDown').addClass('selectBoxCenterOver');
				$($this.selectBox).find('.selectBoxCenterDown').removeClass('selectBoxCenterDown');
				$($this.selectBox).find('.selectBoxRightDown').addClass('selectBoxRightOverl');
				$($this.selectBox).find('.selectBoxRightDown').removeClass('selectBoxRightDown');
			}
		});
	};
	
	SelectWidget.prototype._createOptionBox = function(){
		var optionBox = $('<div class="optionBox"></div>');
		$(optionBox).css({'width':this.settings.width+'px'});
		$(this.elem).find('option').each(function(i,option){
			if(i == 0){
				$(optionBox).append('<div class="optionHeader"><div class="optionHeaderLeft"></div><div class="optionHeaderCenter"></div><div class="optionHeaderRight"></div></div><div class="clear"></div>');
			}
			$(optionBox).append('<div class="option"><div class="optionLeft"></div><div class="optionCenter" value="'+$(option).val()+'">'+$(option).text()+'</div><div class="optionRight"></div></div><div class="clear"></div>');
		});
		$(optionBox).append('<div class="optionFooter"><div class="optionFooterLeft"></div><div class="optionFooterCenter"></div><div class="optionFooterRight"></div></div><div class="clear"></div>');
		return optionBox;
	}
	
	SelectWidget.prototype._setOptionBoxPosition = function(){
		var cwidth = this.settings.width-this.settings.leftWidth*2;
		$(this.optionBox).find('.optionHeader').css({'height':'2px'});
		$(this.optionBox).find('.optionHeaderLeft').css({'width':this.settings.leftWidth+'px'});
		$(this.optionBox).find('.optionHeaderCenter').css({'width':cwidth+'px'});
		$(this.optionBox).find('.optionHeaderRight').css({'width':this.settings.leftWidth+'px'});
		$(this.optionBox).find('.option').css({'wdith':cwidth+'px','height':this.settings.height+'px','line-height':this.settings.height+'px'});
		$(this.optionBox).find('.optionLeft').css({'width':this.settings.leftWidth+'px'});
		$(this.optionBox).find('.optionCenter').css({'width':cwidth+'px'});
		$(this.optionBox).find('.optionRight').css({'width':this.settings.leftWidth+'px'});
		$(this.optionBox).find('.optionFooter').css({'height':'2px'});
		$(this.optionBox).find('.optionFooterLeft').css({'width':this.settings.leftWidth+'px'});
		$(this.optionBox).find('.optionFooterCenter').css({'width':cwidth+'px'});
		$(this.optionBox).find('.optionFooterRight').css({'width':this.settings.leftWidth+'px'});
	}
	
	SelectWidget.prototype._registerOptionBoxEvent = function(){
		var $this = this;
		$($this.optionBox).find('.option').each(function(i, option){
			$(option).bind({
				mouseenter:function(event){
					event.stopPropagation();
					$($this.optionBox).find('.optionLeftSelected').addClass('optionLeft');
					$($this.optionBox).find('.optionLeftSelected').removeClass('optionLeftSelected');
					$($this.optionBox).find('.optionCenterSelected').addClass('optionCenter');
					$($this.optionBox).find('.optionCenterSelected').removeClass('optionCenterSelected');
					$($this.optionBox).find('.optionRightSelected').addClass('optionRight');
					$($this.optionBox).find('.optionRightSelected').removeClass('optionRightSelected');
				    $(option).find('.optionLeft').addClass('optionLeftSelected');
					$(option).find('.optionLeft').removeClass('optionLeft');
					$(option).find('.optionCenter').addClass('optionCenterSelected');
					$(option).find('.optionCenter').removeClass('optionCenter');
					$(option).find('.optionRight').addClass('optionRightSelected');
					$(option).find('.optionRight').removeClass('optionRight');
					$($this.selectBox).find('.selectBoxLeftOver').addClass('selectBoxLeftDropdown');
				    $($this.selectBox).find('.selectBoxLeftOver').removeClass('selectBoxLeftOver');
				    $($this.selectBox).find('.selectBoxCenterOver').addClass('selectBoxCenterDropdown');
				    $($this.selectBox).find('.selectBoxCenterOver').removeClass('selectBoxCenterOver');
				    $($this.selectBox).find('.selectBoxRightOverl').addClass('selectBoxRightDropdown');
				    $($this.selectBox).find('.selectBoxRightOverl').removeClass('selectBoxRightOverl');
				},
				mousedown:function(event){
					if(event.which==3){
						return;
					}
				    event.stopPropagation();
					$this._toggleOptionBox();
					$($this.selectBox).find('.selectBoxCenterDropdown').text($(option).text());
					$($this.elem).find('option:eq('+i+')').attr('selected',true);
					$this.selectedIndex=i;
					$($this.selectBox).find('.selectBoxLeftDropdown').addClass('selectBoxLeftNormal');
				    $($this.selectBox).find('.selectBoxLeftDropdown').removeClass('selectBoxLeftDropdown');
				    $($this.selectBox).find('.selectBoxCenterDropdown').addClass('selectBoxCenterNormal');
				    $($this.selectBox).find('.selectBoxCenterDropdown').removeClass('selectBoxCenterDropdown');
				    $($this.selectBox).find('.selectBoxRightDropdown').addClass('selectBoxRightNormal');
				    $($this.selectBox).find('.selectBoxRightDropdown').removeClass('selectBoxRightDropdown');
				}
			});
		});
		$(document).mousedown(function(){
			$($this.optionBox).css('display','none');
			$($this.selectBox).find('.selectBoxLeftDropdown').addClass('selectBoxLeftNormal');
			$($this.selectBox).find('.selectBoxLeftDropdown').removeClass('selectBoxLeftDropdown');
			$($this.selectBox).find('.selectBoxCenterDropdown').addClass('selectBoxCenterNormal');
			$($this.selectBox).find('.selectBoxCenterDropdown').removeClass('selectBoxCenterDropdown');
			$($this.selectBox).find('.selectBoxRightDropdown').addClass('selectBoxRightNormal');
		    $($this.selectBox).find('.selectBoxRightDropdown').removeClass('selectBoxRightDropdown');
		});
	}
	
	SelectWidget.prototype._toggleOptionBox = function(){
		$(this.optionBox).css({'left':$(this.selectBox).offset().left+'px','top':$(this.selectBox).offset().top+$(this.selectBox).outerHeight()-1+'px'});
		$(this.optionBox).find('.option:eq('+this.selectedIndex+')').trigger('mouseenter');
		$(this.optionBox).toggle();
	}
	
	SelectWidget.prototype._createSelectWidget = function(){
	    this.selectBox = this._createSelectBox();
		this.optionBox =this._createOptionBox();
		$(this.elem).after(this.selectBox);
		$(this.selectBox).append('<div class="clear"></div>').append(this.optionBox);
		this._registerSelectBoxEvent();
		this._setOptionBoxPosition();
		this._registerOptionBoxEvent();
		$(this.elem).css('display','none');
	}
	
	
	$.fn.selectWidget = function(options){
		
		var settings = $.extend({
			'height':24,
			'leftWidth':2,
			'rightWidth':16
			}, options);
		
		return this.each(function(){
			var widget = new SelectWidget(this, settings);
			widget._createSelectWidget();
		});
	}
	
})(jQuery);