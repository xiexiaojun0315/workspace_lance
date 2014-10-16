

/* Skills/Groups autocomplete interaction.*/

EOL.namespace('autocomplete');

EOL.autocomplete.timeoutHandle = null;
EOL.autocomplete.requestHandle = null;
EOL.autocomplete.data = null;
EOL.autocomplete.hintText = 'Enter skills you are looking for in a freelancer.';
EOL.autocomplete.selectedObj = null;

//execute ajax call
EOL.autocomplete.handleRequest = function(type) {
    if(!type) type = '';

    var query = $('autocomplete_text').value;
    var catid = $('catIdField').value;

    var postData = 'q=' + query + '&catid=' + catid + '&type=' + type;
    var options = {
        url: '/php/post/main/postSkillTagsAHR.php?t=' + getDateTime(),
        method: 'post',
        data: postData,
        onSuccess: EOL.autocomplete.parseResponse,
        onFailure: function() { alert('There was an error processing your request. Please try again.'); }
    };

    //cancel any current requests
    if(EOL.autocomplete.requestHandle) {
        EOL.autocomplete.requestHandle.cancel();
    }

    if(EOL.autocomplete.selectedObj)
        EOL.autocomplete.selectedObj.className = '';
    EOL.autocomplete.selectedObj = null;

    EOL.autocomplete.requestHandle = new Request(options);
    EOL.autocomplete.requestHandle.send();
}

//parse ajax response
EOL.autocomplete.parseResponse = function(obj) {
    EOL.autocomplete.requestHandle = null;
    var response = eval('(' + obj + ')');
    if( response.status == 'success' ) {
        if(!response.data)
            return;

        EOL.autocomplete.data = response.data;
        EOL.autocomplete.display(response.data.type);
    }
    else {
        return;
    }
}

//display data in the dropdown
EOL.autocomplete.display = function(type) {

    var checkAllowedGroups = false;
    var allowedGroupIds = null;
    if ($('allowedGroupIds')){
        groupIdsJSON = $('allowedGroupIds').get('value');
        allowedGroupIds = JSON.decode(groupIdsJSON);
    }

    if(type == 'all_groups' || type == 'all_skills') {
        $('autocomplete_list').style.display = '';
        $('autocomplete_suggest').style.display = 'none';


        if(type == 'all_skills') {
            var type = 'skills';
        }
        else if(type == 'all_groups') {
            var type = 'groups';

            if ($('allowedGroupIds') ){
                checkAllowedGroups = true;
            }
        }

        //clear all current values
        while($('autocomplete_all').childNodes.length >= 1) {
            $('autocomplete_all').removeChild($('autocomplete_all').firstChild);
        }

        var typeData = EOL.autocomplete.data['all'];
        for(var j=0; j<typeData.length; j++) {
            var id = typeData[j][0];
            var name = typeData[j][1];

            var newLi = document.createElement('li');
            if(newLi.addEventListener)
                newLi.addEventListener('mouseover',EOL.autocomplete.setActive, false);
            else if(newLi.attachEvent)
                newLi.attachEvent('onmouseover',EOL.autocomplete.setActive);

            if (type == 'groups' && checkAllowedGroups && !allowedGroupIds.contains(id)){
                var newAnchor = document.createElement('span');
                newAnchor.className = 'disabled';
                newAnchor.innerHTML = name;
            } else {
                var newAnchor = document.createElement('a');
                var func = new Function('setTag(\'' + type + '\',\'' + id + '\',"' + encodeURIComponent(name) + '")');
                newAnchor.setAttribute('clickfn','setTag(\'' + type + '\',\'' + id + '\',"' + encodeURIComponent(name) + '")');
                newAnchor.className = 'clickable';
                if(newAnchor.addEventListener)
                    newAnchor.addEventListener('click',func, false);
                else if(newAnchor.attachEvent)
                    newAnchor.attachEvent('onclick',func);
                newAnchor.innerHTML = name;
            }

            newLi.appendChild(newAnchor);
            $('autocomplete_all').appendChild(newLi);
        }
    }
    else {
        var types = ['skills','groups'];
        var showCustom = true;

        //clear all current values
        while($('suggest_list').childNodes.length >= 1) {
            $('suggest_list').removeChild($('suggest_list').firstChild);
        }

        for(var i=0; i<types.length; i++) {
            var type = types[i];

            if(!EOL.autocomplete.data[type])
                continue;

            var typeData = EOL.autocomplete.data[type];

            if(type == 'groups') {
                var elem = document.createElement('div');
                elem.innerHTML = 'Elance Groups Related to "'+$('autocomplete_text').value+'"';
                elem.className = 'title';
                $('suggest_list').appendChild(elem);

                if ($('allowedGroupIds')){
                    checkAllowedGroups = true;
                }
            }

            for(var j=0; j<typeData.length;j++) {
                var id = typeData[j][0];
                var name = typeData[j][1];

                var newLi = document.createElement('li');
                if(newLi.addEventListener)
                    newLi.addEventListener('mouseover',EOL.autocomplete.setActive, false);
                else if(newLi.attachEvent)
                    newLi.attachEvent('onmouseover',EOL.autocomplete.setActive);

                if (type == 'groups' && checkAllowedGroups && !allowedGroupIds.contains(id)){
                    var newAnchor = document.createElement('span');
                    newAnchor.className = 'disabled';
                    newAnchor.innerHTML = name;
                } else {
                    var newAnchor = document.createElement('a');
                    var func = new Function('setTag(\'' + type + '\',\'' + id + '\',\'' + encodeURIComponent(name) + '\')');
                    newAnchor.setAttribute('clickfn','setTag(\'' + type + '\',\'' + id + '\',\'' + encodeURIComponent(name) + '\')');
                    newAnchor.className = 'clickable';
                    if(newAnchor.addEventListener)
                        newAnchor.addEventListener('click',func, false);
                    else if(newAnchor.attachEvent)
                        newAnchor.attachEvent('onclick',func);

                    var q = $('autocomplete_text').value;
                    if(type == 'skills' && (q.toLowerCase() == name.toLowerCase())) {
                        showCustom = false;
                    }

                    var reg = new RegExp('('+q+')','i');
                    newAnchor.innerHTML = name.replace(reg,'<span class="bold">$1</span>');
                }
                newLi.appendChild(newAnchor);
                $('suggest_list').appendChild(newLi);
            }

            if(type == 'skills' && showCustom) {
                //custom skill
                var newLi = document.createElement('li');
                if(newLi.addEventListener)
                    newLi.addEventListener('mouseover',EOL.autocomplete.setActive, false);
                else if(newLi.attachEvent)
                    newLi.attachEvent('onmouseover',EOL.autocomplete.setActive);
                var newAnchor = document.createElement('a');
                var func = new Function('setCustomSkill()');
                newAnchor.setAttribute('clickfn','setCustomSkill()');
                if(!typeData.length)
                    newAnchor.className = 'clickable';
                else
                    newAnchor.className = 'customskill clickable';
                if(newAnchor.addEventListener)
                    newAnchor.addEventListener('click',func,false);
                else if(newAnchor.attachEvent)
                    newAnchor.attachEvent('onclick',func);
                newAnchor.innerHTML = 'Add "<span class="bold" id="autocomplete_custom"></span>" as a skill you need';
                newLi.appendChild(newAnchor);
                $('suggest_list').appendChild(newLi);
                $('autocomplete_custom').set('text', $('autocomplete_text').value);
            }
        }
    }
    //select first item
    var curView = ($('autocomplete_suggest').style.display == 'none') ? 'autocomplete_all' : 'suggest_list';
    if($(curView).firstChild.tagName.toLowerCase() == 'div') {
        EOL.autocomplete.selectedObj = $(curView).firstChild.nextSibling;
        EOL.autocomplete.selectedObj.className = 'selected';
    }
    else {
        EOL.autocomplete.selectedObj = $(curView).firstChild;
        EOL.autocomplete.selectedObj.className = 'selected';
    }

    $('autocomplete_list').scrollTo(0,0);
    $('autocomplete_suggest').scrollTo(0,0);
}

EOL.autocomplete.keySelect = function(event) {
    var curView = ($('autocomplete_suggest').style.display == 'none') ? 'autocomplete_all' : 'suggest_list';
    var curViewport = (curView == 'autocomplete_all') ? 'autocomplete_list' : 'autocomplete_suggest';
    if(event.keyCode == 40) {
        //if keypress is tab or down arrow
        if($(curView).hasChildNodes()) {
            if(EOL.autocomplete.selectedObj == null) {
                if($(curView).firstChild.tagName.toLowerCase() == 'div') {
                    EOL.autocomplete.selectedObj = $(curView).firstChild.nextSibling;
                    EOL.autocomplete.selectedObj.className = 'selected';
                }
                else {
                    EOL.autocomplete.selectedObj = $(curView).firstChild;
                    EOL.autocomplete.selectedObj.className = 'selected';
                }
            }
            else if(EOL.autocomplete.selectedObj.nextSibling) {
                //skip if title div
                if(EOL.autocomplete.selectedObj.nextSibling.tagName.toLowerCase() == 'div') {
                    if(EOL.autocomplete.selectedObj.nextSibling.nextSibling) {
                        EOL.autocomplete.selectedObj.className = '';
                        EOL.autocomplete.selectedObj = EOL.autocomplete.selectedObj.nextSibling.nextSibling;
                        EOL.autocomplete.selectedObj.className = 'selected';
                    }
                    else {
                        return;
                    }
                }
                else {
                    EOL.autocomplete.selectedObj.className = '';
                    EOL.autocomplete.selectedObj = EOL.autocomplete.selectedObj.nextSibling;
                    EOL.autocomplete.selectedObj.className = 'selected';
                }
            }
            if(EOL.autocomplete.selectedObj)
                $(curViewport).scrollTo(0,EOL.autocomplete.selectedObj.offsetTop - 36);
        }

    }
    else if(event.keyCode == 38) {
        //if keypress is up arrow
        if($(curView).hasChildNodes()) {
            if(EOL.autocomplete.selectedObj && EOL.autocomplete.selectedObj.previousSibling) {
                if(EOL.autocomplete.selectedObj.previousSibling.tagName.toLowerCase() == 'div') {
                    if(EOL.autocomplete.selectedObj.previousSibling.previousSibling) {
                        EOL.autocomplete.selectedObj.className = '';
                        EOL.autocomplete.selectedObj = EOL.autocomplete.selectedObj.previousSibling.previousSibling;
                        EOL.autocomplete.selectedObj.className = 'selected';
                    }
                    else {
                        return;
                    }
                }
                else {
                    EOL.autocomplete.selectedObj.className = '';
                    EOL.autocomplete.selectedObj = EOL.autocomplete.selectedObj.previousSibling;
                    EOL.autocomplete.selectedObj.className = 'selected';

                }
            }
            if(EOL.autocomplete.selectedObj)
                $(curViewport).scrollTo(0,EOL.autocomplete.selectedObj.offsetTop - 36);
        }
    }

}

//do the autocomplete lookup
EOL.autocomplete.lookup = function(event) {
    //check for query and category - do nothing if none selected
    if(!$('catIdField').value) {
        return;
    }

    if(event.keyCode == 13) {
        if(EOL.autocomplete.selectedObj != null) {
            eval(EOL.autocomplete.selectedObj.firstChild.getAttribute('clickfn'));
        }
    }
    else if(!$('autocomplete_text').value) {
        $('autocomplete_suggest').style.display = 'none';
        $('autocomplete_list').style.display = '';
    }
    else if( (event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode >= 65 && event.keyCode <= 90) ||
                    (event.keyCode >= 96 && event.keyCode <= 111) || (event.keyCode >= 186 && event.keyCode <= 192) ||
                    (event.keyCode >= 219 && event.keyCode <= 222) || (event.keyCode == 8) ) {

        //otherwise, do search
        $('autocomplete_suggest').style.display = '';
        $('autocomplete_list').style.display = 'none';

        if(EOL.autocomplete.timeoutHandle)
            clearTimeout(EOL.autocomplete.timeoutHandle);

        EOL.autocomplete.timeoutHandle = setTimeout('EOL.autocomplete.handleRequest()',250);
    }
}
EOL.autocomplete.init = function() {
    var hintfn = function() {
        if($('autocomplete_text').value == EOL.autocomplete.hintText) {
            $('autocomplete_text').value = '';
            $('autocomplete_text').removeClass('hint');
        }
    };
    var clearfn = function() {
        $('autocomplete_text').value = '';
        $('autocomplete_suggest').style.display = 'none';
        $('autocomplete_list').style.display = '';
    };

    //attach keyup event
    if($('autocomplete_text').addEventListener) {
        $('autocomplete_text').addEventListener('keyup',EOL.autocomplete.lookup, false);
        $('autocomplete_text').addEventListener('keydown',EOL.autocomplete.keySelect, false);
        $('autocomplete_text').addEventListener('focus', hintfn, false);
        $('autocomplete_clear').addEventListener('click', clearfn, false);
    }
    else if($('autocomplete_text').attachEvent) {
        $('autocomplete_text').attachEvent('onkeyup',EOL.autocomplete.lookup);
        $('autocomplete_text').attachEvent('onkeydown',EOL.autocomplete.keySelect);
        $('autocomplete_text').attachEvent('onfocus',hintfn);
        $('autocomplete_clear').attachEvent('onclick',clearfn);
    }
}

EOL.autocomplete.disable = function() {
    $('autocomplete_text').value = 'Select a category first in the section above.';
    $('autocomplete_text').disabled = true;
    $('autocomplete_suggest').style.display = 'none';
    $('autocomplete_list').style.display = 'none';
    $('autocomplete_nocat').style.display = '';
}

EOL.autocomplete.enable = function() {
    $('autocomplete_text').value = EOL.autocomplete.hintText;
    $('autocomplete_text').addClass('hint');
    $('autocomplete_text').disabled = false;
    $('autocomplete_nocat').style.display = 'none';
}

EOL.autocomplete.displaySuggest = function() {
    if(EOL.autocomplete.selectedObj)
        EOL.autocomplete.selectedObj.className = '';
    EOL.autocomplete.selectedObj = null;
    $('autocomplete_suggest').style.display = '';
    $('autocomplete_list').style.display = 'none';
    $('autocomplete_text').focus();
}

EOL.autocomplete.displayAll = function(type) {
    EOL.autocomplete.handleRequest(type);
}

EOL.autocomplete.setActive = function(event) {
    var el = null;
    if(window.event)
        el = window.event.srcElement;
    else
        el = (event.target.tagName) ? event.target : event.target.parentNode;

    if(EOL.autocomplete.selectedObj)
        EOL.autocomplete.selectedObj.className = '';
    EOL.autocomplete.selectedObj = el.parentNode;
    EOL.autocomplete.selectedObj.className = 'selected';

    $('autocomplete_text').focus();
}

