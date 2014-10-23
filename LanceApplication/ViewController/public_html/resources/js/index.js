$(function () {
    $(".link-cate").click(function (event) {
        $(this).addClass("sel");
        $("#t2 .cate-list").show();
        $("#t2 .cate-list .d-list").animate({ "height": "306px" }, 400);
        event.stopPropagation();
    });
    $("body").click(function () {
        $(".link-cate").removeClass("sel");
        $("#t2 .cate-list").hide();
        $("#t2 .cate-list .d-list").css("height", 0);
    });

    $("#t2 .search .sea-head").click(function () {
        $(".sea-cate-menu").show();
    });

    $(".sea-cate-menu").on("click", "li", function (event) {
        var oldwid = $("#t2 .search .sea-head").width();
        $(".sea-cate-menu").hide();
        var str = $(this).attr("data-value");
        $(".sea-sel-txt").html(str + '<span class="sp-icons icon-arrow"></span>');

        var acwidth = $("#t2 .search .sea-head").width(), wid = $(".sea-body input").width() - (acwidth - oldwid);
        $(".sea-body input").width(wid);

        var clone = $(this).clone();

        $(".sea-cate-menu li").find(".sp-icons").remove();

        clone.find("a").html(clone.attr("data-value") + '<span class="sp-icons icon-arrow"></span>');

        $(".sea-cate-menu").prepend(clone);
        $(this).remove();

        event.stopPropagation();
    });

    /**/
    $(".fot-list-cate li").hover(function () {
        var item = $(this).find("div");
        item.stop().animate({ "top": "0px" }, 400);
    }, function () {
        var item = $(this).find("div");
        item.stop().animate({ "top": "30px" }, 400);
    });

    $(".introduction .page li").click(function () {
        var val = $(this).attr("data-val");
        $(".introduction .md").hide();
        $(".introduction ." + val).show();
        $(".introduction .page li").removeClass("icon-circle-sel");
        $(this).addClass("icon-circle-sel");
    });

    $(window).scroll(function () {
        var top = $(this).scrollTop();
        if (top >= 100) {
            $("#fx-header").stop().animate({ "top": "0px" }, 400);
        } else {
            $("#fx-header").stop().animate({ "top": "-70px" }, 400);
        }
    });

    /*FIND*/
    $("#list-job .list-cate li,#list-job2 .list-cate li").hover(function () {
        $(this).find(".mod-left").stop().animate({"margin-left":"0%"}, 400);
    }, function () {
        $(this).find(".mod-left").stop().animate({ "margin-left": "-50%" }, 400);
    });
});