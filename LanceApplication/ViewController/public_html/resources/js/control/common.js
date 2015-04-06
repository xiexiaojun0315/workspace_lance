function com_conFocus($obj, tipMessage){
    $obj.focus(function(){
        if($obj.val() == ""){
            $obj.attr("data-content", tipMessage).popover("show");
        }else{
            $obj.popover("hide");    
        }
    });
}