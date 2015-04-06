$(function(){
    $(".sel-tab-type").click(function(){
        $(".sel-tab-type").removeClass("select");
        $(this).addClass("select");
        
        $(".persons,.jobs").hide();
        $("." + $(this).attr("data-cls")).show();
        
        $(".lbl-search-title").html($(this).attr("data-title"));
        
    });
    
    $(".get-more").click(function(){
        var btn = $(this);
        btn.button("loading");
    });
    
});