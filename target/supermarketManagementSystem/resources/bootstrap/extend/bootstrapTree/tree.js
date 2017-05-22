/**
 * bootstrapTree树
 * @author yangyue
 */

/*[
	{
		"html":"<span class='btn-logical'></span>",
		"child":[
			{
				"html":"<input type='text' value='测试' />",
				"child":[
					{
						"html":"条件1-1",
						"child":[
							{
								"html":"条件1-1-1",
							},
							{
								"html":"条件1-1-2",
							}
						]
					},
					{
						"html":"条件1-2",
					}
				]
			},
			{
				"html":"条件2",
			},
			{
				"html":"条件3",
				"child":[
					{
						"html":"条件3-1",
					},
					{
						"html":"条件3-2",
					}
				]
			}
		]
	}
]; */

(function ($) {
    $.fn.bootstrapTree = function () {
    	var bulidCount = 0;//构造一般节点数目
    	if(arguments.length>0){
    		//初始化
            bulidCount = 0;
    		if(arguments[0]=='init'){
    			var treeObj = arguments[1];
    			createTree(treeObj,$(this));
    			init();
    		}
    		//新增同级别节点
    		if(arguments[0]=='add'){
    			var html = arguments[1];
    			createTreeItem(html);
    		}
    		//新增下级别节点
    		if(arguments[0]=='addNext'){
    			var html = arguments[1];
    			createNextItem(html);
    		}
    		//获取树结构
    		if(arguments[0]=='getObject'){
    			return getTreeConfig($(this));
    		}
    	}
    	
    	//从Json新建树结构
    	function createTree(treeObj,dom){
    		if(treeObj instanceof Array){
    			dom.append("<ul>");
    			for (var i = 0; i < treeObj.length; i++) {
    				var itemObj = treeObj[i];
    				var itemUl = dom.find("ul:first");
    				itemUl.append("<li>");
    				var itemLi = itemUl.find("li:last")
    				if(typeof itemObj.html != 'undefined'){
    					//第一个节点没有删除
						if(bulidCount == 0) {
                            itemLi.append("<span class='tool'><!--<span class='treeBtn'></span>--> <span class='checkBtn fa fa-th-list fa-1' onclick='addLogicPoint(this)'></span> <span class='checkBtn fa fa-indent fa-1' onclick='addConPoint(this)'></span> </span><span class='item'>"+itemObj.html+"</span>");
                        } else {
                            itemLi.append("<span class='tool'><!--<span class='treeBtn'></span>--> <span class='removeBtn glyphicon glyphicon-remove'></span> <span class='checkBtn fa fa-th-list fa-1' onclick='addLogicPoint(this)'></span> <span class='checkBtn fa fa-indent fa-1' onclick='addConPoint(this)'></span> <!--<span class='checkBtn glyphicon glyphicon-th-list'></span>--></span><span class='item'>"+itemObj.html+"</span>");
                        }
                        bulidCount ++;
					}
    				if(typeof itemObj.child != 'undefined'){
    					createTree(itemObj.child,itemLi);
    				}
    			}
    		}
    	}
    	
    	//功能初始化
        function init(){
        	//保证节点增减时按钮表意正常
        	$('.tree li').removeClass('parent_li');
        	$('.treeBtn').removeClass('glyphicon glyphicon-resize-full');
        	//按钮初始化
        	$('.tree li:has(ul)').addClass('parent_li').find(' > span > .treeBtn').each(function(index){
        			$(this).attr('treeStatus', 'unfold');//状态标识
        			$(this).addClass('glyphicon glyphicon-resize-full');//状态标识
        	});
        	//解绑已有事件
        	$('.tree li.parent_li > span > .treeBtn').unbind('click');
        	$('.tree li > span > .addBtn').unbind('click');
        	$('.tree li > span > .removeBtn').unbind('click');
        	/*$('.tree li > span > .checkBtn').unbind('click');*/
        	//fold-折叠 unfold-展开
            $('.tree li.parent_li > span > .treeBtn').on('click', function (e) {
                var children = $(this).parent('span').parent('li.parent_li').find(' > ul > li');//取子元素
                if (children.is(":visible")) {
                    children.hide('fast');
                    $(this).attr('treeStatus', 'fold').find(' > i').addClass('icon-plus-sign').removeClass('icon-minus-sign');
                } else {
                    children.show('fast');
                    $(this).attr('treeStatus', 'unfold').find(' > i').addClass('icon-minus-sign').removeClass('icon-plus-sign');
                }
                e.stopPropagation();
            });
            //删除按钮
            $('.tree li > span > .removeBtn').on('click', function () {
            	 var item = $(this).parent('span').parent('li').remove();//取子元素
            });
            //选择按钮,菜单弹出
            /*$('.tree li > span > .checkBtn').on('click', function () {
            	$('.tree li > span > .checkBtn').removeClass("checkColor");
            	$(this).addClass("checkColor");
            	var X = $(this).offset().top;
            	var Y = $(this).offset().left;
            	$("#treeMenu").css('top',X+5);
            	$("#treeMenu").css('left',Y+5);
            	$("#treeMenuBox").show(100);
            	$("#treeMenuBox").on('click',function(){
            		$("#treeMenuBox").css('display','none');
            		$("#treeMenuBox").unbind('click');
            	});
            });*/
            //背景色
            treeActionInit();
        }
        
        //新增同级节点
        function createTreeItem(html){
        	var lastUl = $('.tree li > span > .checkColor').parent('span').parent('li').parent('ul');
        	if(lastUl.length == 0){
        		alert("请选择一个节点");
        		return;
        	}
        	lastUl.append("<li></li>");
        	lastUl.find("li:last").append("<span class='tool'><!--<span class='treeBtn'></span>--> <span class='removeBtn glyphicon glyphicon-remove'></span> <span class='checkBtn fa fa-th-list fa-1' onclick='addLogicPoint(this)'></span> <span class='checkBtn fa fa-indent fa-1' onclick='addConPoint(this)'></span> <!--<span class='checkBtn glyphicon glyphicon-th-list'></span>--></span><span class='item'>"+html+"</span>");
        	init();
        }
        
        //新增下级节点
        function createNextItem(html){
        	var lastLi = $('.tree li > span > .checkColor').parent('span').parent('li');
        	if(lastLi.length == 0){
        		alert("请选择一个节点");
        		return;
        	}
        	if(lastLi.find("ul:last").length == 0)//没有才参加
        		lastLi.append("<ul></ul>");
        	lastLi.find("ul:first").append("<li></li>");
        	lastLi.find("ul:first > li:last").append("<span class='tool'><!--<span class='treeBtn'></span>--> <span class='removeBtn glyphicon glyphicon-remove'></span> <span class='checkBtn fa fa-th-list fa-1' onclick='addLogicPoint(this)'></span> <span class='checkBtn fa fa-indent fa-1' onclick='addConPoint(this)'></span> <!--<span class='checkBtn glyphicon glyphicon-th-list'></span>--></span><span class='item'>"+html+"</span>");
        	init();
        }
        
        //回收树结构
        function getTreeConfig(dom){
        	var treeObj=[];
        	dom.children('ul').each(function(index){
        		$(this).children('li').each(function(index){
        			var item = {};
        			item.html = $(this).find('.item');
        			if($(this).children('ul').length > 0) {//存在下级节点
        				item.child = getTreeConfig($(this));
         			}
        			treeObj.push(item);
        		});
        	});
        	return treeObj;
        }

    };
})(jQuery);

//树动作绑定
function treeActionInit(){
	var $item =$(".tree").find(".item");
	$item.unbind('click');
	$item.bind('click',function(){
		$item.css('background','none');
		$(this).css('background','#aabbca');
		$(this).css('border-radius','3px');
	})
}

