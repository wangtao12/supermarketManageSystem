/**
 * Created by yangyue on 2017/1/11.
 */
//加载遮罩
$.fn.loading = function (text) {
    $(this).addClass("loadingshade");
    var xPos = $(this).offset().left;
    var yPos = $(this).offset().top;
    var width = $(this).width();
    var height = $(this).height();
    var $shadeText=$("<div id='loadingBodyBox' style='z-index: 1500;position: absolute;top: 0;left: 0;width: 100%;height: 100%;'></div>");
    $shadeText.append("<div style='position: relative;top: "+yPos+"px;left: "+xPos+"px;width: "+width+"px;height: "+height+"px;line-height: "+height+"px;text-align: center;'>"+text+"</div>");
    $("body").append($shadeText);
}
//去除遮罩层
$.fn.cleanloading = function () {
    $(this).removeClass("loadingshade");
    $("#loadingBodyBox").remove();
}