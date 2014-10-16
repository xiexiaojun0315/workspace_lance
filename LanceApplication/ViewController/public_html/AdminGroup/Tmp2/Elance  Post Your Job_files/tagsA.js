

/* tagging */

function setTag(type,value,name,auto, extraClass) {
    if (version == 'B')
        return;

    if (!auto) canAutoTag = false;
    if (!extraClass) extraClass = '';
    if(type == 'skills') {
        var limit = 5;
        var itemPrefix = 'selectedskill';
        var elemName = 'skills[id][]';
        var elemCount = 'selectedSkillCount';
        var elemTagWarning = 'skillTagWarning';
        var container = 'selectedSkills';
        var removeClass = 'removeSkill';
    }
    else if(type == 'groups') {

        var itemPrefix = 'selectedgroup';
        var elemName = 'groups[id][]';
        var elemCount = 'selectedGroupCount';
        var elemTagWarning = 'groupTagWarning';
        var container = 'selectedGroups';
        var removeClass = 'removeGroup';
        if(value == 1194) showGroupTip(true);
        if ($('allowedGroups')){
            //unlimited groups for PTC hiring rules cases
            var limit = 30000000;
            hideLastX = true;
            if ($(elemCount).get('value') == 1){
                $$('.removeGroup').each(function(ele){
                    ele.show();
                })
            }
        } else {
            var limit = 3;
        }

    }


    $('selectedList').removeClass('displayNone');
    if (!$(itemPrefix+value)) {
        if (parseInt($(elemCount).value) < limit) {
            $(container + 'List').removeClass('displayNone');

            var div = new Element('div', {
                id: itemPrefix+value,
                'class': 'skillEntry'
            });
            var elem = new Element('input', {
                type: 'hidden',
                name: elemName,
                idx: value,
                value: value
            });
            elem.inject(div);
            var elem = new Element('div', {
                'class': 'left postSelSkill',
                html: decodeURIComponent(name)
            });
            elem.inject(div);
            var elem = new Element('div', {
                'class': 'right clickable '+removeClass,
                html: '[x]',
                events: {
                    'click': function() {unsetTag(type,value)}
                }
            });
            elem.inject(div);
            var elem = new Element('div', {
                'class': 'clear'
            });
            elem.inject(div);
            $(container).appendChild(div);
            $(elemCount).value++;
        }
        else {
            $(elemTagWarning).addClass('postWarning');
            $(elemTagWarning).removeClass('postGreyOut');
        }
    }

    //Sorting the display list
    sortSelectedList('selectedSkills', 'div.skillEntry');
    sortSelectedList('selectedGroups', 'div.skillEntry');
}

function sortSelectedList(containId, elementsId) {
    if(!$(containId))
        return;
    var list = $(containId).getElements(elementsId);
    if(!list.length)
        return;

    var sortMe = new Array();
    list.each(function(elem){
        sortMe.push($(elem).getElement('.postSelSkill').get('html'));
    });

    sortMe.sort();

    sortMe.each(function(v,k){
        list.some(function(elem){
            if($(elem) && $(elem).getElement('.postSelSkill').get('html') == v) {
                $(elem).inject($(containId));
                return true;
            }
            else {
                return false;
            }
        });
    });

}

function setCustomSkill(skillname,auto) {
    if (!auto) canAutoTag = false;

    $('selectedList').removeClass('displayNone');

    if(skillname)
        var customSkillString = skillname;
    else
        var customSkillString = $('autocomplete_text').value;

    var customSkillNameArray = new Array();
    customSkillNameArray = customSkillString.split(',');
    var existingSkills = GetExistingCustomeSkills();
    for ( var i = 0; i < customSkillNameArray.length; i++) {
        var customSkillName = customSkillNameArray[i];
        if (!isExistSkill(customSkillName.toLowerCase().trim(), existingSkills)){
            addCustomSkill(customSkillName);
        }
    }
}

//Check item is in array or not
function isExistSkill(item, array){
    for(var i=0; i<array.length; i++){
        if (item==array[i]){
            return true;
        }
    }
    return false;
}

function GetExistingCustomeSkills(){
    var skillDivs = $('selectedSkills').getChildren();
    var skillIDs = new Array();
    if (Browser.ie8){
        var patt1 = /name=skills\[(\w+)\]\[\]/
        var patt2 = /id=(.+) title=Skill>(.+)<INPUT\s/;
    }
    else{
        var patt1 = /name="skills\[(\w+)\]\[\]"/
        var patt2 = /id="(.+)">(.+)<input\s/;
    }
    for (var i=0; i<skillDivs.length; i++){
        var match = patt1.exec(skillDivs[i].outerHTML);
        if (match!=null){
            var skillType = match[1];
            //Only Add if it's a custome Skills
            if (skillType!="id"){
                var match = patt2.exec(skillDivs[i].outerHTML);
                if (match!=null){
                    skillIDs.push(match[1].toLowerCase().trim());
                }
            }
        }
    }
    return skillIDs;
}

function addCustomSkill(name) {
    //name = name.replace(/^[ ]*/, '');
    //name = name.replace(/[ ]*$/, '');
    //name = name.replace(/<.*?>/g, '');
    var namePacked = name.split(' ').join('');
    if (parseInt($('selectedSkillCount').value) < 5) {
        $('selectedSkillsList').removeClass('displayNone');
        //make sure the user entered something
        if (namePacked.length>0 && !$('selectedskill'+namePacked)) {
            var div = new Element('div', {
                id: 'selectedskill'+namePacked,
                'class' : 'skillEntry'
            });
            var elem = new Element('input', {
                type: 'hidden',
                name: 'skills[custom][]',
                idx: namePacked,
                value: name
            });
            elem.inject(div);
            var elem = new Element('div', {
                'class': 'left postSelSkill',
                text: name
            });
            elem.inject(div);
            var elem = new Element('div', {
                'class': 'right clickable',
                idx: namePacked,
                text: '[x]',
                events: {
                    'click': function() {unsetCustomSkill(namePacked)}
                }
            });
            elem.inject(div);
            var elem = new Element('div', {
                'class': 'clear'
            });
            elem.inject(div);
            $('selectedSkills').appendChild(div);
            $('selectedSkillCount').value++;
        }
    }
    else {
        $('skillTagWarning').addClass('postWarning');
        $('skillTagWarning').removeClass('postGreyOut');
    }
}

function unsetTag(type,value,auto) {
    if (!auto) canAutoTag = false;

    if(!value)
        return;
    var hideLastX = false;

    if(type == 'skills') {
        var limit = 5;
        var itemPrefix = 'selectedskill';
        var elemCount = 'selectedSkillCount';
        var elemTagWarning = 'skillTagWarning';
        var container = 'selectedSkills';
        removeSkillFromList(value);
    }
    else if(type == 'groups') {
        if ($('allowedGroups')){
            //unlimited groups for PTC hiring rules cases
            var limit = 30000000;
            hideLastX = true;
        } else {
            var limit = 3;
        }

        var itemPrefix = 'selectedgroup';
        var elemCount = 'selectedGroupCount';
        var elemTagWarning = 'groupTagWarning';
        var container = 'selectedGroups';
        if(value == 1194) hideGroupTip();
    }

    //remove the entry on the selected skills
    var s = $(container);
    var r = $(itemPrefix+value);


    s.removeChild(r);
    $(elemCount).value--;

    if ($(elemCount).value == 1 && hideLastX){
        $$('.removeGroup').each(function(ele){
            ele.hide();
        });
    } else if ($(elemCount).value == 0 && !hideLastX){
        //auto uncheck required group
        $('required_group').checked = false;

    }



    if (parseInt($(elemCount).value) <= limit) {
        $(elemTagWarning).removeClass('postWarning');
        $(elemTagWarning).addClass('postGreyOut');
    }

    if(parseInt($(elemCount).value) == 0) {
        $(container + 'List').addClass('displayNone');
    }

}

function unsetCustomSkill(skillname,auto) {
    if (!auto) canAutoTag = false;
    //remove the entry on the selected skills
    var s = $('selectedSkills');
    var r = $('selectedskill'+skillname);
    s.removeChild(r);
    $('selectedSkillCount').value--;

    if (parseInt($('selectedSkillCount').value) <= 5) {
        $('skillTagWarning').removeClass('postWarning');
        $('skillTagWarning').addClass('postGreyOut');
    }

    if(parseInt($('selectedSkillCount').value) == 0) {
        $('selectedSkillsList').addClass('displayNone');
    }
    removeSkillFromList(skillname);
}

function unsetAllTags() {
    $$('input[name="skills[id][]"]').each(function(elem){
        unsetTag('skills',elem.getProperty('idx'));
    });

    $$('input[name="groups[id][]"]').each(function(elem){
        unsetTag('groups',elem.getProperty('idx'));
    });

    $$('input[name="skills[custom][]"]').each(function(elem){
        unsetCustomSkill(elem.getProperty('idx'));
    });
}

function unsetAutoSkills()
{
    $$('input[name="skills[id][]"]').each(function(elem){
        unsetTag('skills',elem.getProperty('idx'),true);
    });

    $$('input[name="skills[custom][]"]').each(function(elem){
        unsetCustomSkill(elem.getProperty('idx'),true);
    });
}

