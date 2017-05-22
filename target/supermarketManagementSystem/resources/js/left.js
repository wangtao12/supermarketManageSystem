function changeBackgroundColor(idv){
	 $(idv).hover(function(){
		$(idv).css("backgroundColor","#333333");
	},function(){
		$(idv).css("backgroundColor","");
	});
}
$(function(){
    changeBackgroundColor("#headingOne");
    changeBackgroundColor("#headingTwo");
    changeBackgroundColor("#headingThree");
    changeBackgroundColor("#headingFour");
    changeBackgroundColor("#headingFive");
    changeBackgroundColor("#headingSix");
});