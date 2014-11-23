$(function(){
    var list_lasted = function(method){
        var isshow = false;
        if(method == ""){
            method = "search/postJob/latest";
            isshow = false;
        }else{
            $(".search-key").html("'"+method+"' 的搜索结果");
            method = "search/postJob/searchJob/" + method;
            isshow = true;
        }
        show_type = "job";
        $.ax("get", method, null, function(data){
            $("#waiting").hide();
            $("#content").fadeIn();
            var total = data.count, dat = data.data, i = 0, tmp = null;
            
            if(total == 0){
                $(".search-key").append("&nbsp;&nbsp;&nbsp;&nbsp;没有找到结果");
            }
            
            var pa = $(".list-free");
            pa.find(".mod").not(".temp_mod").remove();
            for(i=0;i<total;i++){
                tmp = pa.find(".temp_mod." + show_type).clone().removeClass("temp_mod");
                if(i == 0){
                    tmp.addClass("first");
                }
                tmp.find(".name").html(dat[i].Name);
                if(dat[i].Postform == 1){
                    tmp.find(".jtfix").remove();
                    tmp.find(".s1").html("Hourly rate: " + (dat[i].HourlyPayMin ? (dat[i].HourlyPayMin + "-" + dat[i].HourlyPayMax) : "Not sure"));
                    tmp.find(".s2").html("Duration: " + (dat[i].DurationMin ? (dat[i].DurationMin + "-" + dat[i].DurationMax) : "Not sure"));
                    tmp.find(".s3").html("Posted: 开始时间");
                    tmp.find(".s4").html("Ended: 结束时间");
                    tmp.find(".s5").html("0 Proposal");
                }else{
                    tmp.find(".jthour").remove();
                    tmp.find(".s1").html("Fixed Prise: " + (dat[i].FixedPayMin ? (dat[i].FixedPayMin + "-" + dat[i].FixedPayMax) : "Not sure"));
                    tmp.find(".s2").html("Posted: 开始时间");
                    tmp.find(".s3").html("Ended: 结束时间");
                    tmp.find(".s4").html("0 Proposal");
                }
                tmp.find(".desc").html(dat[i].Brief);
                tmp.find(".s21").html('<span>Category: </span>' + dat[i].WorkCategory);
                tmp.find(".s22").html('<span>SubCategory: </span>' + dat[i].WorkSubcategory);
                tmp.find(".job-lx").html(dat[i].Postform == 1 ? '时薪' : '固定价格');
                var skills = "";
                if(dat[i].SpecificSkillA){
                    skills += '<a href="#" class="btn-gray">'+dat[i].SpecificSkillA+'</a>';
                }
                if(dat[i].SpecificSkillB){
                    skills += '<a href="#" class="btn-gray">'+dat[i].SpecificSkillB+'</a>';
                }
                if(dat[i].SpecificSkillC){
                    skills += '<a href="#" class="btn-gray">'+dat[i].SpecificSkillC+'</a>';
                }
                if(dat[i].SpecificSkillD){
                    skills += '<a href="#" class="btn-gray">'+dat[i].SpecificSkillD+'</a>';
                }
                if(dat[i].SpecificSkillE){
                    skills += '<a href="#" class="btn-gray">'+dat[i].SpecificSkillE+'</a>';
                }
                tmp.find(".job-skill").html(skills);
                tmp.find(".job-pj").html('<span class="hire-icon2 icon-star"></span><span class="hire-icon2 icon-star"></span><span class="hire-icon2 icon-star"></span><span class="hire-icon2 icon-star"></span><span class="hire-icon2 icon-star-gray"></span>');
                var locations = '不需要下现场办公';
                if(dat[i].LocationProvince && dat[i].LocationCity){
                    locations = dat[i].LocationProvince + " " + dat[i].LocationCity;
                }
                tmp.find(".job-loc").html(locations);
                pa.append(tmp);
            }
            if(isshow){
                $(".search-key").show();
            }else{
                $(".search-key").hide();
            }
        }, function(){
            
        });
    }
    list_lasted("");
    
    $(".btn-search-job").click(function(){
        var val = $(".inp-search").val();
        if(val == ""){
            $.ae("请输入搜索关键词");
        }else{
            $("#waiting").show();
            $("#content").hide();
            list_lasted(val);
            $(".inp-search").val("");
        }
    });
    
    $(".inp-search").keydown(function(e){
        if(e.keyCode == 13){
            $(".btn-search-job").click();
        }
    });
});