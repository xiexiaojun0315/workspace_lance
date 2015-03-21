$(function(){
    var initUser = function(dat, loc, skill, edu){
        $(".uname").html(dat.TrueName + '-' + dat.JobTitle);
        $(".utitle").html(dat.TagLine);
        $(".uself").html(dat.Overview);
        if(loc.length > 0){
            
        }else{
            $(".uloc").html("您尚未填写位置信息");
        }
        
        if(skill.length > 0){
            var i = 0, len = skill.length;
            for(i=0;i<len;i++){
                $(".uskill").append('<span class="label label-primary">' + skill[i].Name +'</span>');
            }
        }else{
            
        }
        
        if(edu.length > 0){
            
        }else{
            
        }
        
    };
    $.ax("get", "user/muhongdi", null, function(data){
        console.log(data);
        initUser(data.User, data.LocationList, data.Skill, data.Education);
    }, netWorkError);
});











