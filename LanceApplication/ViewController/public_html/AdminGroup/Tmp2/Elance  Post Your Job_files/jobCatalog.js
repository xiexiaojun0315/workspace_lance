

/*** For Fancy Upload 3 ***/
EOL.namespace('uploadW');

// For debuging, remove the return
function uwlog (text) {
	return;
	if( top.window.console) top.console.log(text);
}

/***** FUNCTIONS FOR INCLUDE *****/
// Please note that the include code uses MooTools 1.11 (the iframe uses 1.2)

// other globals
EOL.uploadW.iframeCount = 0; // We cannot recreate the same named iframe, we need to rename it each time
EOL.uploadW.iframeName  = '';
EOL.uploadW.maxNameLength  = 0;//no limit by default

EOL.uploadW.initInclude = function(){

	if($('uploadW-file-attach-module')) $('uploadW-file-attach-module').removeClass('displayNone');
	if($('uploadW-bulk-file-attach-module')) $('uploadW-bulk-file-attach-module').removeClass('displayNone');
};

EOL.uploadW.UploadWidget = new Class({
    Implements: [Options, Events],
    options: {
        dialogMode: false,
        fileGroupId: 0,
        fileContext: 'job',
        iframeSRC: '/php/files/main/uploadIframe.php?jobid=',
    },
    initialize: function(el, options) {
        this.setOptions(options);
        if (typeof el == 'string') el=$(el);
        var me = this;
        me.el = el;
        EOL.uploadW.dialogMode = me.options.dialogMode;
        EOL.uploadW.fileGroupId = me.options.fileGroupId;
        EOL.uploadW.fileContext = me.options.fileContext;
        EOL.uploadW.iframeSRC = me.options.iframeSRC;

        new Request({
            url: '/php/files/main/uploadIframe.php',
            method: 'get',
            data: {mode:'include',context:me.options.fileContext},
            onSuccess: function(response) {
                me.el.set('html', me.el.get('html')+response);
                EOL.uploadW.initInclude();
            }
        }).send();
    }
});

EOL.uploadW.toggleAttach = function(){
    if (typeof(EOL.uploadW.sendMetrics.onAttach) == 'function') {
        EOL.uploadW.sendMetrics.onAttach();
    }

	var container = $('uploadW-file-attach-module');
	if( container.className.search('closed') != -1 ){
		// container is closed; open it
		EOL.uploadW.openAttach();
  } else {
		// Container is open; close it
		EOL.uploadW.closeAttach();
	}
};

EOL.uploadW.toggleBulkAttach = function(){
	var container = $('uploadW-bulk-file-attach-module');

	if( container.className.search('closed') != -1 ){
		EOL.uploadW.openBulkAttach();
	} else {
		EOL.uploadW.closeBulkAttach();
	}
};

EOL.uploadW.openAttach = function(){
	var container = $('uploadW-file-attach-module');
	container.removeClass('closed');

	if( EOL.uploadW.dialogMode ){
		if( !EOL.uploadW.attachDialog ){
			EOL.uploadW.attachDialog = new EOL.uploadW.dialog('uploadW-attach-dialog-c', 'uploadW-attach-dialog-b');
			EOL.uploadW.attachDialog.beforeShow = function(){ EOL.uploadW.beforeShowDialog(); };
			EOL.uploadW.attachDialog.afterShow  = function(){ EOL.uploadW.afterShowDialog(); };
			EOL.uploadW.attachDialog.beforeHide = function(){
				$('uploadW-file-attach-module').addClass('closed');
				EOL.uploadW.beforeHideDialog();
			};
			EOL.uploadW.attachDialog.afterHide  = function(){ EOL.uploadW.afterHideDialog(); };
		}
		EOL.uploadW.attachDialog.show();
	}

	var frame = $('uploadW-attach-frame');
	if( frame.innerHTML == '' ){
		// Get runtime params
		var url = EOL.uploadW.iframeSRC;
		url += '&fileGroupId=' + EOL.uploadW.fileGroupId + '&fileContext=' + EOL.uploadW.fileContext;
		url += '&t=' + getDateTime();
		if(EOL.uploadW.addtionalCss)
			url += '&css=' + EOL.uploadW.addtionalCss;
		// create iFrame
		EOL.uploadW.iframeName = "uploadW-iframe" + EOL.uploadW.iframeCount++;
		var iFrame = document.createElement("iframe");
		iFrame.setAttribute("src", url);
		iFrame.setAttribute("scrolling", "no");
		iFrame.setAttribute("frameBorder", "0");
		iFrame.setAttribute("id",   EOL.uploadW.iframeName);
		iFrame.setAttribute("name", EOL.uploadW.iframeName);
		$(iFrame).addClass("uploadW-iframe");

		// Load it into panel
		frame.appendChild(iFrame);
	}
};

EOL.uploadW.openBulkAttach = function(){
	var container = $('uploadW-bulk-file-attach-module');
	container.removeClass('closed');

	var frame = $('uploadW-bulk-attach-frame');
	if( frame.innerHTML == '' ){

		var bids = Array();
		$('myjobs-results').getElements('.selected').each(function(elem){
			if(elem.get('projecttype') == 'bid')
				bids.push(elem.get('projectid'));
		});

		// Get runtime params
		var url = EOL.uploadW.iframeSRC + '&bidids=' + JSON.stringify(bids);
		url += '&fileGroupId=' + EOL.uploadW.fileGroupId + '&fileContext=' + EOL.uploadW.fileContext;
		url += '&t=' + getDateTime();
		if(EOL.uploadW.addtionalCss)
			url += '&css=' + EOL.uploadW.addtionalCss;
		// create iFrame
		EOL.uploadW.iframeName = "uploadW-iframe" + EOL.uploadW.iframeCount++;
		var iFrame = document.createElement("iframe");
		iFrame.setAttribute("src", url);
		iFrame.setAttribute("scrolling", "no");
		iFrame.setAttribute("frameBorder", "0");
		iFrame.setAttribute("id",   EOL.uploadW.iframeName);
		iFrame.setAttribute("name", EOL.uploadW.iframeName);
		$(iFrame).addClass("uploadW-iframe");

		// Load it into panel
		frame.appendChild(iFrame);
	}

};

EOL.uploadW.closeAttach = function(){
	if( EOL.uploadW.dialogMode ){
		EOL.uploadW.attachDialog.hide();
		EOL.uploadW.updateFileList();
	}
	$('uploadW-file-attach-module').addClass('closed');
};

EOL.uploadW.closeBulkAttach = function(){
	if($('uploadW-bulk-file-attach-module'))
		$('uploadW-bulk-file-attach-module').addClass('closed');
	if($('uploadW-bulk-attach-frame'))
		$('uploadW-bulk-attach-frame').empty();
};

// For updating the file list
EOL.uploadW.updateFileList = function(){
	$('uploadW-attach-fileList').innerHTML = $('uploadW-attach-fileList-loading').innerHTML;

	// Get runtime params
	var url = EOL.uploadW.fileListSRC;
	url += '&fileGroupId=' + EOL.uploadW.fileGroupId + '&fileContext=' + EOL.uploadW.fileContext;
    url += '&maxNameLength='+EOL.uploadW.maxNameLength;
	url += '&t=' + getDateTime();

	var myAjax = new Request( {
		url: url,
		method: 'post',
		onSuccess: EOL.uploadW.onUpdateFileListSuccess
	}).send();
};
EOL.uploadW.onUpdateFileListSuccess = function(response){
	$('uploadW-attach-fileList').innerHTML = response;
};


/** Callbacks for iframe [Some of these will need to be implemented by caller] **/

// resizes the upload iframe
EOL.uploadW.resizeUploadIframe = function(height){
	uwlog('parent resize ' + height);
	if( height > 0 ) $(EOL.uploadW.iframeName).style.height = height + 'px';
};

// Dummy that is called when there is an error.Override this to do stuff depending on your page
EOL.uploadW.setError = function(titleText, bodyText, type){
	uwlog('parent showError');
	if( EOL.uploadW.dialogMode ){
		$("uploadW-attach-error").removeClass('displayNone');
		$("uploadW-attach-errorTitle").innerHTML = titleText;
		$("uploadW-attach-errorBody").innerHTML = bodyText;
	} else {
		titleText = titleText.replace(/<.*?>/g, '');
		bodyText  = bodyText.replace(/<.*?>/g, '');
		alert(titleText +':' + bodyText);
	}
};
EOL.uploadW.setErrorId = function(titleId, bodyId, type){
	var title = $(titleId) ? $(titleId).innerHTML : '';
	var body  = $(bodyId)  ? $(bodyId).innerHTML : '';
	return EOL.uploadW.setError(title, body, type);
};
// Dummy that is called when no more error. Override this to do stuff depending on your page
EOL.uploadW.clearError = function(){
	uwlog('parent clearError');
	if( EOL.uploadW.dialogMode ){
		$("uploadW-attach-error").addClass('displayNone');
	}
};

// Dummy that is called when upload is started. Override this to do stuff depending on your page
EOL.uploadW.showBusy = function(){
	uwlog('parent showbusy');
};
// Dummy that is called when upload is finished. Override this to do stuff depending on your page
EOL.uploadW.hideBusy = function(){
	uwlog('parent hidebusy');
};

// Mostly functional function that is called when 'upload new file' is clicked on recent files widget
// Override if you want additional behaviors
EOL.uploadW.widgetUploadNewFile = function(){
	EOL.uploadW.openAttach();
	$('uploadW-attach-frame').focus();
};

// These overrides are for dialog mode
EOL.uploadW.beforeShowDialog = function(){};
EOL.uploadW.afterShowDialog  = function(){};
EOL.uploadW.beforeHideDialog = function(){};
EOL.uploadW.afterHideDialog  = function(){};

// You should not need to override the following, they double/tripple up on single calls
EOL.uploadW.showBusyResize = function(height){
	EOL.uploadW.resizeUploadIframe(height);
	EOL.uploadW.showBusy();
};
EOL.uploadW.hideBusyResize = function(height){
	EOL.uploadW.resizeUploadIframe(height);
	EOL.uploadW.hideBusy();
};
EOL.uploadW.completeFile = function(height){
	EOL.uploadW.resizeUploadIframe(height);
	EOL.uploadW.hideBusy();
	EOL.uploadW.clearError();
};
EOL.uploadW.setErrorResize = function(titleText, bodyText, type, height){
	EOL.uploadW.resizeUploadIframe(height);
	EOL.uploadW.setError(titleText, bodyText, type);
};
EOL.uploadW.failureError = function(titleText, bodyText, type, height){
	EOL.uploadW.resizeUploadIframe(height);
	EOL.uploadW.hideBusy();
	EOL.uploadW.setError(titleText, bodyText, type);
};
EOL.uploadW.clearErrorResize = function(height){
	EOL.uploadW.resizeUploadIframe(height);
	EOL.uploadW.clearError();
};

// Iframe calls this to tell us what the iframe's groupId is
EOL.uploadW.notifyGroupId = function(fileGroupId){
	EOL.uploadW.fileGroupId = fileGroupId;
};


/** Interface **/

// Dispose the swiff uploader box and then the remove frame
// It's important to do both things in that order to avoid an IE error: undefined __flash__removeCallback
EOL.uploadW.removeUploaderFrame = function(frame) {
	if (!frame.firstChild) return;
	var iFrameWindow = frame.firstChild.contentWindow;
	if (iFrameWindow) {
		var iFrameDocument = $(iFrameWindow.document);
		if (iFrameDocument)
			iFrameDocument.getElements('.swiff-uploader-box').dispose();
	}
	frame.removeChild( frame.firstChild );
}

// Resets the upload iframe
EOL.uploadW.reset = function(){
	// toggle down the attachment section
	EOL.uploadW.closeAttach();

	// Delete iFrame source
	var frame = $('uploadW-attach-frame');
	EOL.uploadW.removeUploaderFrame(frame);
	EOL.uploadW.fileGroupId = 0;

	EOL.uploadW.hideBusy();
	EOL.uploadW.clearError();
};

// Returns the class name for an file-icon based on its name
// Please include html/styles/uploadv2icons.css
// Please keep this in sync with php/files/modules/FileData.php::getIconClass
EOL.uploadW.getFileIconClass = function(filename, size){
	var fileType = filename.toLowerCase().substr(filename.lastIndexOf('.')+1, filename.length - (filename.lastIndexOf('.') - 1));
	var iconClass;
	switch(size){
		case 'large':
			iconClass = 'uploadW-icon-large';
			break;
		default:
			iconClass = 'uploadW-icon-small';
			break;
	}
	iconClass += ' '+iconClass;

	switch(fileType) {
		case 'mp3': case 'wav': case 'au':
			iconClass += '-audio';
			break;
		case 'doc': case 'docx':
			iconClass += '-doc';
			break;
		case 'xls': case 'xlt': case 'xlsx':
			iconClass += '-excel';
			break;
		case 'swf': case 'fla':
			iconClass += '-flash';
			break;
		case 'html': case 'htm': case 'css': case 'js':
			iconClass += '-html';
			break;
		case 'png': case 'gif': case 'jpg': case 'jpeg': case 'tif':
			iconClass += '-image';
			break;
		case 'pdf':
			iconClass += '-pdf';
			break;
		case 'ppt': case 'pptx':
			iconClass += '-ppt';
			break;
		case 'txt': case 'rtf':
			iconClass += '-text';
			break;
		case 'mpg': case 'mpeg': case 'avi': case 'wmv': case 'mov':
			iconClass += '-video';
			break;
		case 'zip': case 'rar': case 'tgz': case 'gz':
			iconClass += '-zip';
			break;
		default:
			iconClass += '-default';
			break;
	}
	return iconClass;
};

// Call this to the groupId
EOL.uploadW.getFileGroupId = function(){
	return EOL.uploadW.fileGroupId;
};

EOL.uploadW.uploadNewVersion = function(fid){
	var frame = window.frames[EOL.uploadW.iframeName];
	if( !frame.EOL ){
		return setTimeout( function(){ EOL.uploadW.uploadNewVersion(fid); }, 100 );
	}
	frame.EOL.uploadWI.uploadNewVersion(fid);
	return null;
};

// Called to remove a file from the draft file list, which calls into the iframe
EOL.uploadW.removeFile = function( fid ){
	window.frames[EOL.uploadW.iframeName].EOL.uploadWI.swiffy.removeThisFile(fid);
	var elem = $('uploadW-file-'+fid);
	if( elem ) elem.parentNode.removeChild(elem);
	return null;
};

EOL.uploadW.removeFileCallback = function()
{
	//callback function triggered by EOL.uploadWI.File
}

/**
 * Dialog sub-class
 * Normal dialog class moves the contents around in the DOM (or deletes them entirely), which causes the iframe
 * to reload. This class is designed to stay in one place in the DOM
 **/
EOL.uploadW.dialog = function(containerEl, dialogEl, options){
	EOL.uploadW.dialog.superclass.constructor.call(this, '', options);

	document.body.appendChild($(containerEl));

	this.containerEl = containerEl;
	this.bodyEl = dialogEl;

	this.opened = false;
};
EOL.utility.extend(EOL.uploadW.dialog, EOL.dialog);

EOL.uploadW.dialog.prototype.show = function(){
	if( this.opened ) return;
	this.beforeShow();

	//create modal
	if(this.options.modal) this.createOverlay();

	var elem = $(this.containerEl);
	elem.removeClass('displayNone');
	elem.style.display = 'block';
	elem.style.visibility = 'inherit';

	this.setContainerAttributes(elem);

	this.setBodyAttributes($(this.bodyEl));

	//create close button
	if(this.options.close){
		var close = document.createElement('div');
		this.setCloseAttributes(close);
		elem.appendChild(close);
	}

	this.sizeElements();

	this.setResizeEvents();

	this.opened = true;
	this.afterShow();
};

EOL.uploadW.dialog.prototype.hide = function(){
	if( !this.opened ) return;
	this.beforeHide();

	this.removeResizeEvents();

	$(this.containerEl).style.display = 'none';

	//remove modal
	if($('eol-dialog-overlay')) {
		$('eol-dialog-overlay').parentNode.removeChild($('eol-dialog-overlay'));
	}

	this.opened = false;
	this.afterHide();
};


/***** FUNCTIONS FOR IFRAME *****/
// Note: the iframe uses mootools 1.2 (the include uses 1.11)

EOL.namespace('uploadWI');

// settings globals
EOL.uploadWI.maxFiles   = 10;
EOL.uploadWI.maxSizeMb  = 50;

// other globals
EOL.uploadWI.controller = '/php/files/main/upload.php';
EOL.uploadWI.mode       = null;
EOL.uploadWI.revise_fid = null;
if(getGetParam('css') == 'upload-dashboard.css')
	EOL.uploadWI.max_filename_length = 19;
else
	EOL.uploadWI.max_filename_length = 25;

// Init function
EOL.uploadWI.initIframe = function(){
	// Report our fileGroupId
	parent.EOL.uploadW.notifyGroupId(EOL.uploadWI.fileGroupId);

	// IE6 will crash when we open a dyn iframe with fancy upload. So they get the fallback.
	if( navigator.appVersion.indexOf('MSIE 6') != -1 ){
		return EOL.uploadWI.useFallback();
	}

	// For slow flash or flash blockers, resize immediately
	EOL.uploadWI.resizeIframe();

	// subclass of the default file class to do some additonal tricks
EOL.uploadWI.File = new Class({
	Extends: FancyUpload2.File,

	// constructor
	initialize: function(base, data){
		this.parent(base, data);
		// if the mode is version, we need to change the URL
		if( EOL.uploadWI.mode == 'version' ){
			this.setOptions( {url: base.options.url + '&mode=revision&revise_fid='+EOL.uploadWI.revise_fid + '&version.root=' + EOL.uploadWI.revise_fid} );
		}
	},

	// Called to create the file display in the UI
	render: function() {
		if (this.invalid) {
			this.parent();
			return;
		}

		this.addEvents({
			'start': this.onStart,
			'progress': this.onProgress,
			'complete': this.onComplete,
			'error': this.onError,
			'remove': this.onRemove
		});

		// Create progress bar
		var progressElem = $('file-progress-stub').cloneNode(true);
		progressElem.id = '';

		this.progressImg = progressElem.getElement('.file-progress');
		this.currentProgress = new Fx.ProgressBar(this.progressImg, {
			text: new Element('span', {'class': 'progress-text'}).inject(this.progressImg, 'after'),
			fit: false
		});

		var filename = this.name;
		if( filename.length > EOL.uploadWI.max_filename_length) { filename = filename.substr(0,EOL.uploadWI.max_filename_length) + '...'; }
		this.fname = new Element('span', {'html': filename, 'data-filename':this.name});

        var fsizeB = Swiff.Uploader.formatUnit(this.size, 'b');
		var fsizeStr = this.fake ? '' : '('+ fsizeB + ')';
		this.fsize = new Element('span', {'class': 'file-size', 'html': '&nbsp;&nbsp;'+fsizeStr, 'data-size': fsizeB });

		this.icon = new Element('img', { 'src': '/media/images/spacer.gif', 'class': 'file-icon' });
		$(this.icon).addClass( EOL.uploadW.getFileIconClass(this.name) );

		this.info = new Element('span', {'class': 'file-info'});
		this.clearDiv = new Element('div', {'class':'clear'});
        this.fileRemove = new Element('img',{'src':'/media/images/spacer.gif','class':'uploadW-icon-small uploadW-icon-small-trash'});

		this.element = new Element('li', {'class': 'file'}).adopt(
			this.icon,
			new Element('div', {'class': 'file-name'}).adopt(this.fname, this.fsize),
			progressElem,
			this.info,
            new Element('a', {
                'class': 'file-remove', html: '<span class="file-remove-text">[x]</span>',
                href: '#',
                title: MooTools.lang.get('FancyUpload', 'removeTitle'),
                events: {
                    click: function() {
                        this.removeAction();
                        EOL.uploadWI.hideBusy();
                        return false;
                    }.bind(this)
                }
            }).adopt(
                    this.fileRemove
                ),
			this.clearDiv
		).inject(this.base.list);

		$('demo-list').removeClass('displayNone');
		EOL.uploadWI.resizeIframe.delay(10); // resize afterwards
	},

	validate: function(){
		var ret = this.parent();
		if( !ret ) return ret;

		// Have to manually check for dupes to prevent 'same name different location' case
		var name = this.name;
		var dup = this.base.fileList.some( function(file){return (file.name == name );} );
		if( dup ){
			this.validationError = 'duplicate';
			return false;
		}

		$('controls').addClass('displayNone');
		EOL.uploadWI.showBusy();

		// check file
		if( EOL.uploadWI.mode != 'version' ){
			EOL.uploadWI.collision_file = this;
			var params = EOL.uploadWI.ajaxParams + '&action=chk_file&filename=' + escape(this.name) + '&filesize=' + this.size + '&t=' + getDateTime();
			var myRequest = new Request({
				url: EOL.uploadWI.controller,
				method: 'post',
				data: params,
				onSuccess: EOL.uploadWI.onValidateFileUploadSuccess,
				onFailure: EOL.uploadWI.onValidateFileUploadFailure
			});
			myRequest.send();
		}
		return true;
	},

	// Called when the file starts to upload via the flash object
	onStart: function() {
		this.parent();
		if( !this.fake ) this.currentProgress.element.parentNode.style.display = '';
		this.currentProgress.cancel().set(0);
		EOL.uploadWI.showBusy();
	},

	// Called when theres a progress update to the file
	onProgress: function() {
		this.parent();
		this.currentProgress.start(this.progress.percentLoaded);
	},

	// Called when a file finishes, success OR failure
	onComplete: function(uploadedFile) {
		this.parent();
		if(this.response.error){ return; }

        var newFileId = null;

        // get the file id if available
        if (uploadedFile) {
            var responseObj = JSON.decode(uploadedFile.response.text);
            if (responseObj.fid) {
                newFileId = responseObj.fid;
            } else {
                newFileId = responseObj.data;
            }
        }

		//when renaming a file, if the file isn't renamed here it will cause an error when checking
		//collisions and trying to upload to account files.

		if (this.fname.get('data-filename') != this.name) {
			this.name = this.fname.get('html');
		}

		// Send 'file complete' message to server
		var params = EOL.uploadWI.ajaxParams + '&action=complete' + '&filename=' + escape(this.name) + '&filesize=' + this.size + '&t=' + getDateTime();
        if (newFileId) {
            params += "&fid=" + newFileId;
        }

		var me = this;
		var a = new Request({
			url: EOL.uploadWI.controller,
			method: 'post',
			data: params,
			onSuccess: function(res, xml){ EOL.uploadWI.onCompleteSuccess(res, xml, me); }
		}).send();

        this.showFileRemove();

		// Adjust display
		EOL.uploadWI.completeFile();
	},

    showFileRemove: function()
    {
        this.fileRemove.removeClass('displayNone');
    },

	// response.error = { code: 900, error: 'httpStatus', text: '' };
	onError: function(){
		this.parent();
        this.showFileRemove();

        switch( this.response.error ){
		case 'httpStatus':
			EOL.uploadWI.failureErrorId.delay(100, null, ['errorTitle', 'error'+this.response.code]);
			break;
		case 'ioError':
			EOL.uploadWI.failureError.delay(100, null,
				[$('errorTitle').innerHTML, ('An error caused a send or load operation to fail ({text})').substitute(this.response)]
			);
			break;
		}

	},

	/**
	 * Removes this file. Called by the remove link, and during a few places in this code.
	 */
	removeAction: function() {

		this.removeActionCalled = true;

		// Normal File: need to call this to remove it from the flash
		if( !this.fake ){ this.remove(); }

		// Short delay to let flash remove first
		this.coreRemove.delay(100, this);
	},

	/**
	 * event that gets fired by Flash. If we reach this after the above remove fn, it will not call coreRemove
	 */
 	onRemove: function(){
        if (typeof(EOL.uploadWI.sendMetrics.onRemove) == 'function') {
            EOL.uploadWI.sendMetrics.onRemove();
        }
        parent.EOL.uploadW.removeFileCallback();

		if( this.removeActionCalled ) return;
		this.coreRemove();
	},

	/**
	 * Core remove: called for event or for action, but never twice
	 */
	coreRemove: function(){

		this.base.fileListSize -= this.size;

        //when renaming a file, if the file isn't renamed here it will cause an error when checking
        //collisions and trying to upload to account files.

        if (this.fname.get('data-filename') != this.name) {
            this.name = this.fname.get('html');
        }

        this.base.fileList.erase(this);

		if( $(this.element) ) $(this.element).destroy();

		if( this.base.fileList.length == 0 ){
			// No files left: hide the list
			$('demo-list').addClass('displayNone');
		} else if( this.base.fileList.length < EOL.uploadWI.maxFiles ){
			// some files: Go straight to add another
			EOL.uploadWI.clickAddAnother();
		}
		EOL.uploadWI.resizeIframe.delay(10);

		var params = EOL.uploadWI.ajaxParams + '&action=delete' + '&t=' + getDateTime() + '&fid=' + this.fid;
		if( this.fake ){
			params += '&ref_id='+this.ref_id;

			// Unhide corresponding local-file elem [if any]
			var i = 0;
			var elem;
			while( elem = $('localfile'+i) ){
				if( elem && elem.value == this.id ){
					elem.checked = false;
					$(elem.parentNode.parentNode).removeClass('displayNone');
					break;
				}
				i++;
			}
			if( !this.ref_id ) return; // No need to send the remove request if we didn't get a valid file
		} else {
			params += '&filename=' + escape(this.name) + '&filesize=' + this.size;
		}

		// Only send the remove request if the file was actually sent
		if( this.fake || this.fid ){
			var a = new Request({
				url: EOL.uploadWI.controller,
				method: 'post',
				data: params,
				onSuccess: EOL.uploadWI.onRemoveSuccess
			}).send();
		}
	},

	addToCompany: function(){
		if( this.fake ){ return; }

		var params = EOL.uploadWI.ajaxParams + '&action=addToCompany' + '&t=' + getDateTime() +'&fid=' + this.fid;
		var me = this;
		var a = new Request({
			url: EOL.uploadWI.controller, method: 'post', data: params,
			onSuccess: function(t,x){EOL.uploadWI.onAddToCompanySuccess(t,x,me);},
			onFailure: EOL.uploadWI.onFailureGeneric
		}).send();
	}
});

	// Subclass of the base fancy upload to add a few methods
EOL.uploadWI.FancyUpload2 = new Class({
	Extends: FancyUpload2,

	// creates a fake file for reference
	createDummyFile: function(name, id){
		var cls = this.options.fileClass || Swiff.Uploader.File;
		var file = new cls(this, { name: name, status: 3, id: id, size: 0, fake: true });
		this.fileList.push(file);
		file.render();
		return file;
	},

	// tells us if the flash is busy uploading a file
	isUploading: function(){
		for (var i = 0; i < this.fileList.length; i++) {
			if(this.fileList[i].element.hasClass('file-uploading') ){ return true; }
		}
		return false;
	},

	// Called to remove a file with a given fid
	removeThisFile: function(fid){

		uwlog('remove file called with [' + fid);
		this.fileList.each( function(file, idx){ if(file.fid == fid || file.ref_id == fid){file.removeAction();} } );
	},

	// This will be used in dialog mode to clear out the ids linking swiffy to the flash. The issue is that flash
	// can be re-initialized, after which it loses all state and starts uploading at id=1 again. This would cause
	// big problems for any already uploaded files, so the already uploaded files are de-coupled from the flash
	resetFiles: function(){
		this.fileList.each( function(file){ file.id = null; } );
	},

	// Effectively hide the flash when the browse button is hidden [cant actually hide or FF will not work]
	reposition: function(coords) {
		coords = coords || (this.target && this.target.offsetHeight)
			? this.target.getCoordinates(this.box.getOffsetParent())
			: {top: 0, left: 0, width: 1, height: 1};
		this.box.setStyles(coords);
	},

	debugPrint: function(){
		var str = '';
		for (var i = 0; i < this.fileList.length; i++) {
			str += this.fileList[i].id + ':' + this.fileList[i].name + ', ';
		}
		uwlog('FILES ['+ this.fileList.length+'] '+ str);
	}
});

	// Create Flash uploader (swiffy)
	EOL.uploadWI.swiffy = new EOL.uploadWI.FancyUpload2($('demo-status'), $('demo-list'), {
		url:       EOL.uploadWI.wsURL + EOL.uploadWI.ajaxParams,
		fieldName: 'file',
		path:      '/scripts/upload/uploadv3/Swiff.Uploader.swf',
		target:    'demo-browse',  // the element for the overlay (Flash 10 only)
		limitSize:  EOL.uploadWI.maxSizeMb * 1024 * 1024,
 		limitFiles: EOL.uploadWI.maxFiles,
	    multiple: false,
		fileHeight: 30,
		instantStart: false,

		fileClass: EOL.uploadWI.File,

		// Called when file(s) successfully chosen
		onSelectSuccess: function(files){
            if (typeof(EOL.uploadWI.sendMetrics.onSelect) == 'function') {
                EOL.uploadWI.sendMetrics.onSelect();
            }
			// Instant start will skip the collision dialog, so we need to run this here
			if( EOL.uploadWI.swiffy.options.instantStart ){ EOL.uploadWI.reset();}
		},

		// Handler for errors
		onSelectFail: function(files){
			var errorText = '';
			files.each(function(file) {
				errorText += ' ' + file.validationErrorMessage;
			}, this);
			EOL.uploadWI.failureError($('userErrorTitle').innerHTML, errorText);
			return false;
		},

		onLoad: function(){
			// Swiffy loaded: do more stuff
			if( EOL.uploadWI.swiffyLoaded ){
				// Special case for firefox where the flash gets re-initialized after it is hidden
				this.resetFiles();
			}
			EOL.uploadWI.swiffyLoaded = true;
			//if($('fallback')) $('fallback').destroy();

			EOL.uploadWI.onSourceChange();
			EOL.utility.__flash__fixRemoveCallback();
		},

		onFail: function(error) {
			// Swiffy failed: activate fallback
			EOL.uploadWI.useFallback();
            $('upswitch-1').addClass('displayNone');
			EOL.uploadWI.onSourceChange();
		}
    });
    $('demo-browse').addEvents({
        mouseenter: function(){
            $('demo-browse').style.backgroundColor='#00468C';
            $('demo-browse').style.color='white';
        },
        mouseleave:function(){
            $('demo-browse').style.backgroundColor='transparent';
            $('demo-browse').style.color='black';
        },
        mousedown: function() {
            if (typeof(EOL.uploadWI.sendMetrics.onBrowseClick) == 'function') {
                EOL.uploadWI.sendMetrics.onBrowseClick();
            }
            this.click();
        }
    });
};

// If the fallback is needed
EOL.uploadWI.useFallback = function(switchback){
	$('fallback').removeClass('displayNone');
    if(switchback) {
        $('fallback-warning').addClass('displayNone');
        $('upswitch-1').addClass('displayNone');
        $('upswitch-2').removeClass('displayNone');
        $('fallback').set('data-toggle','on');
    }
	$('demo-browse').addClass('displayNone');

	EOL.uploadWI.resizeIframe();

    if(!document.getElement('iframe#fallback-iframe')) {
        // Add our WS iframe
        var url = EOL.uploadWI.fallbackURL + '/php/files/main/fallbackIframe.php?';
        url += '&fileGroupId=' + EOL.uploadWI.fileGroupId + '&fileContext=' + EOL.uploadWI.fileContext;
        if (EOL.uploadWI.ajaxParams)
            url +=  '&'+EOL.uploadWI.ajaxParams;
        url += '&frameName='+parent.EOL.uploadW.iframeName;
        url += '&xdomain='+ document.location.protocol + '//' + document.location.host;

        var iFrame = document.createElement("iframe");
        iFrame.setAttribute("src", url);
        iFrame.setAttribute("id", 'fallback-iframe');
        iFrame.setAttribute("name", 'fallback-iframe');
        iFrame.setAttribute("scrolling", "no");
        iFrame.setAttribute("frameBorder", "0");
        $('fallback-iframe').appendChild(iFrame);
    }
};

EOL.uploadWI.hideFallback = function() {
    $('fallback').set('data-toggle','off');
    $('fallback').addClass('displayNone');
    $('demo-browse').removeClass('displayNone');
    $('upswitch-1').removeClass('displayNone');
    $('upswitch-2').addClass('displayNone');
    EOL.uploadWI.resizeIframe();
}

// Resets the UI and BE so its ready to upload more files
EOL.uploadWI.reset = function(){
	// Clear out the mode driven stuff
	if( EOL.uploadWI.hasLocalFiles ){ $('form-demo').uploadSource.disabled = false; }
	EOL.uploadWI.mode = null;
	EOL.uploadWI.revise_fid = null;
	EOL.uploadWI.swiffy.options.instantStart = false;

	// Reset UI
	$('controls').addClass('displayNone');
	$('content-local').addClass('displayNone');
	if( EOL.uploadWI.swiffy.fileList.length == 0 ){
		// No Files: Show default view
		EOL.uploadWI.clickAddAnother();
	} else if( EOL.uploadWI.swiffy.fileList.length < EOL.uploadWI.maxFiles ){
		// some files: Show default view
		EOL.uploadWI.clickAddAnother();
	} else {
		// Too many files! make sure the controls are not shown
		$('controls').addClass('displayNone');
	}
	EOL.uploadWI.clearError();
	EOL.uploadWI.hideBusy();
};


/** Parent communication **/

EOL.uploadWI.resizeIframe = function(offset){
	var height = document.body.clientHeight;
	if( offset != null ){ height += offset; }
	uwlog('child resize ['+height+']['+offset+']');
	parent.EOL.uploadW.resizeUploadIframe( height );
};

EOL.uploadWI.showBusy = function(){
	// Tell the parent we have entered busy mode
	var height = document.body.clientHeight;
	uwlog('child show busy ['+height+']');
	if (EOL.uploadWI.dialogMode) EOL.uploadWI.hideDialogSaveBtn();
	parent.EOL.uploadW.showBusyResize(height);
};
EOL.uploadWI.hideBusy = function(){
	// Tell the parent we have left busy mode
	var height = document.body.clientHeight;
	uwlog('child hideBusy ['+height+']');
	if( EOL.uploadWI.swiffy.isUploading() ){
		parent.EOL.uploadW.resizeUploadIframe(height);
		if (!EOL.uploadWI.swiffy.fileList.length || $$('li.file-uploading').length <= 1) {
			parent.EOL.uploadW.hideBusy();
		}
	} else {
		parent.EOL.uploadW.hideBusyResize(height);
	}
};

// when a file has finished [resize, hide busy, clear error]
EOL.uploadWI.completeFile = function(){
	var height = document.body.clientHeight;
	uwlog('child complete file ['+height+']');
	if( EOL.uploadWI.swiffy.isUploading() ){
		parent.EOL.uploadW.clearErrorResize(height);
	} else {
		parent.EOL.uploadW.completeFile(height);
		if( EOL.uploadWI.dialogMode ) EOL.uploadWI.showDialogSaveBtn();
	}
};

// Called when theres an error
EOL.uploadWI.setError = function(titleText, bodyText, type){
	var height = document.body.clientHeight;
	uwlog('child setError ['+height+']['+titleText+']['+bodyText+']');
	parent.EOL.uploadW.setErrorResize(titleText, bodyText, type, height);
};
EOL.uploadWI.setErrorId = function(titleId, bodyId, type){
	var title = $(titleId) ? $(titleId).innerHTML : '';
	var body  = $(bodyId)  ? $(bodyId).innerHTML : '';
	return EOL.uploadWI.setError(title, body, type);
};
// Really bad error [resize, hide busy, set error text]
EOL.uploadWI.failureError = function(titleText, bodyText, type){
	var height = document.body.clientHeight;
	uwlog('child failureError ['+height+']['+titleText+']['+bodyText+']');
	if( EOL.uploadWI.swiffy.isUploading() ){
		parent.EOL.uploadW.setErrorResize(titleText, bodyText, type, height);
	} else {
		parent.EOL.uploadW.failureError(titleText, bodyText, type, height);
	}
};
EOL.uploadWI.failureErrorId = function(titleId, bodyId, type){
	var title = $(titleId) ? $(titleId).innerHTML : '';
	var body  = $(bodyId)  ? $(bodyId).innerHTML : '';
	return EOL.uploadWI.failureError(title, body, type);
};

// Called to clear error
EOL.uploadWI.clearError = function(){
	var height = document.body.clientHeight;
	uwlog('child clearError ['+height+']');
	parent.EOL.uploadW.clearErrorResize(height);
};

EOL.uploadWI.hideDialogSaveBtn = function(){
	disableElems($('uploadW-attach-dialog-save'));
};

EOL.uploadWI.showDialogSaveBtn = function(){
	enableElems($('uploadW-attach-dialog-save'));
};




/** Interface **/

// Changes between remote/local upload when the source select is changed
EOL.uploadWI.source = null;
EOL.uploadWI.onSourceChange = function(){
	$('controls').removeClass('displayNone');
	$('content-local').addClass('displayNone');
	$('controls-workroom').addClass('displayNone');
	$('controls-company').addClass('displayNone');

	var select = $('form-demo').uploadSource;
	var source = '';
	for (var i = 0; i < select.options.length; i++) {
		if( select.options[i].selected ){ source = select.options[i].value; }
	}
	if( source == 'remote' ){
		$('controls-remote').removeClass('displayNone');
		$('controls-local').addClass('displayNone');
        if($('fallback').get('data-toggle') == 'on') {
            $('fallback').removeClass('displayNone');
        }
	} else {
		$('content-local').removeClass('workroom');
		$('content-local').removeClass('company');
		$('content-local').addClass(source);
		$('controls-remote').addClass('displayNone');
		$('controls-local').removeClass('displayNone');
        if($('fallback').get('data-toggle') == 'on') {
            $('fallback').addClass('displayNone');
        }
	}
	EOL.uploadWI.source = source;
	EOL.uploadWI.resizeIframe.delay(10);
};

// Called when a user clicks 'browser' next to workroom/company file upload option
EOL.uploadWI.sourceSelect = function(){
	var select = $('form-demo').uploadSource;
	var source = '';
	for (var i = 0; i < select.options.length; i++) {
		if( select.options[i].selected ){ source = select.options[i].value; }
	}
	if( source == 'workroom' ){
		// Workroom
		$('controls').addClass('displayNone');
		$('content-local').removeClass('displayNone');
		$('workroom-files-list').removeClass('displayNone');
		$('controls-workroom').removeClass('displayNone');
		$('company-files-list').addClass('displayNone');
		$('controls-company').addClass('displayNone');
		EOL.uploadWI.folderChange(true);
	} else if( source == 'company' ){
		// Company
		$('controls').addClass('displayNone');
		$('content-local').removeClass('displayNone');
		$('company-files-list').removeClass('displayNone');
		$('controls-company').removeClass('displayNone');
		$('workroom-files-list').addClass('displayNone');
		$('controls-workroom').addClass('displayNone');
		EOL.uploadWI.folderChange(true);
	}
};

EOL.uploadWI.clickSupportedTypes = function(){
	if( $('portfolio-types-wrapper').className.search('opened') == -1 ){
		$('portfolio-types-wrapper').addClass('opened');
	} else {
		$('portfolio-types-wrapper').removeClass('opened');
	}
	EOL.uploadWI.resizeIframe.delay(10);
};

// Called when the user clicks 'add another file'
EOL.uploadWI.clickAddAnother = function(){
	$('controls').removeClass('displayNone');
	$('upload-source-remote').selected = true;
	EOL.uploadWI.onSourceChange();

	// We need to fix the browse button because it was hidden.
	EOL.uploadWI.swiffy.reposition();
};

// Called when the collision option is changed by user
EOL.uploadWI.changeCollisionOption = function(elem){
	if( elem.id == 'collision_rename' ){
		$('collision-newname').removeClass('displayNone');
		EOL.uploadWI.resizeIframe.delay(10);
	} else {
		$('collision-newname').addClass('displayNone');
	}
};

// Called when the user confirms their selection in the collision dialog
EOL.uploadWI.collision_data = {};
EOL.uploadWI.submitCollisionDialog = function(){
	// Change the url based on user response
	var url = EOL.uploadWI.swiffy.options.url;
	var form = $('form-demo');

	/*** REVISE ***/
	if( $('collision_revision').checked ){
		url += '&version.root=' + EOL.uploadWI.collision_data.fileId + '&revise_fid='+ EOL.uploadWI.collision_data.fileId +'&mode=revision';
		EOL.uploadWI.collision_file.setOptions( {url: url} );

	/*** RENAME ***/
	} else if( $('collision_rename').checked ){
		var filename = form.newname.value;
		if( filename.length <= 0 ){
			// No value!
			return EOL.uploadWI.setErrorId('userErrorTitle', 'errorRenameEmpty');
		} else if ( filename == EOL.uploadWI.collision_data.fileRename ){
			// name not changed
			return EOL.uploadWI.setErrorId('userErrorTitle', 'errorRenameSame');
		} else if ( filename.search(/[\!\'\$\^\|\*\+\?\:\%\@\,\;\(\)\[\]\`\+\{\}\&]/) != -1 ){
			// Special char check [same as in FileUtil.php]
			return EOL.uploadWI.setErrorId('userErrorTitle', 'errorRenameBadChars');
		}

		filename += '.'+ EOL.uploadWI.collision_data.fileExt;
		var newFilename = filename;
		// Fire off request to verify new name
		var params = EOL.uploadWI.ajaxParams + '&action=chk_file&filename=' + escape(filename) + '&filesize=' +  EOL.uploadWI.collision_file.size + '&t=' + getDateTime();
		var myRequest = new Request({
			url: EOL.uploadWI.controller,
			method: 'post',
			data: params,
			onSuccess: EOL.uploadWI.onValidateFileUploadSuccess,
			onFailure: EOL.uploadWI.onValidateFileUploadFailure
		});
		myRequest.send();

		// Swiffy
		url += '&name=' + filename;
		EOL.uploadWI.collision_file.setOptions( {url: url} );
		if( filename.length > EOL.uploadWI.max_filename_length ){
			filename = filename.substr(0, EOL.uploadWI.max_filename_length) + '...';
		}
		EOL.uploadWI.collision_file.fname.innerHTML = filename;
		$(EOL.uploadWI.collision_file.fname).set('data-filename', newFilename);

	/*** REFERENCE ***/
	} else if( $('collision_reference') && $('collision_reference').checked ){
		EOL.uploadWI.collision_file.remove(); // Delete pending upload file
		// Create dummy file in list
		EOL.uploadWI.swiffy.createDummyFile( EOL.uploadWI.collision_data.fileName, EOL.uploadWI.collision_data.fileId );

		// Look for this file in the local folders list and hide it
		var i = 0;
		var elem;
		while( elem = $('localfile'+i) ){
			if( elem.value == EOL.uploadWI.collision_data.fileId ){
				elem.checked = false;
				$(elem.parentNode.parentNode).addClass('displayNone');
				break;
			}
			i++;
		}

		// backend
		var refForm = $('form-reference');
		refForm.ref_fid0.value = EOL.uploadWI.collision_data.fileId;
		refForm.set('send', {
			onSuccess: EOL.uploadWI.onRefSuccess,
			onFailure: EOL.uploadWI.onRefFailure,
			onComplete: EOL.uploadWI.onRefComplete
		});
		refForm.send();
	} else {
		return EOL.uploadWI.setErrorId('userErrorTitle', 'errorSelect');
	}

	// Continue on
	$(document.body).removeClass('dialog-collision');
	EOL.uploadWI.clearError();

	// Revise only: continue upload
	if( $('collision_revision').checked ){
		EOL.uploadWI.reset();
        EOL.uploadWI.swiffy.start.delay(10,  EOL.uploadWI.swiffy);
    }
    return;
};

// Called when the users cancels collision dialog
EOL.uploadWI.cancelCollisionDialog = function(){
	$(document.body).removeClass('dialog-collision');
	EOL.uploadWI.collision_file.remove(); // Remove the pending upload (will resize)
	EOL.uploadWI.reset();
	return;
};

// Called when the user changes the local folder
EOL.uploadWI.folderChange = function(smart, id, name){
	if( smart && !id ){
		// Nice to have: automatically select the first folder if there is only one
		var source = EOL.uploadWI.source;
		var items = $$('#'+source+'-files-list .local-files-list');
		if( items && items.length == 1 ){
			id = items[0].getProperty('idx');
			name = items[0].getProperty('listname');
		}
	}

	$$('.local-files-list').each( function(item){ item.addClass('displayNone'); } );
	if( id ){
		$('content-local').removeClass('parent');
		$('content-local').addClass('folder');
		$('content-local-folder').innerHTML = name;
		$('content-local-controls').addClass('displayNone');
		$('content-local-lists').removeClass('displayNone');
		$('localfiles'+id).removeClass('displayNone');
	} else {
		$('content-local').addClass('parent');
		$('content-local').removeClass('folder');
		$('content-local-controls').removeClass('displayNone');
		$('content-local-lists').addClass('displayNone');
	}
	// Hack to show company files without parent links: remove once we have portfolio referencing
	if( EOL.uploadWI.source == 'company' ){
		$('content-local').addClass('parent');
		$('content-local').removeClass('folder');
	}

	EOL.uploadWI.resizeIframe.delay(50);
};

// Called when user selects a local file
EOL.uploadWI.clickLocalFile = function( fileid ){
	// Activate the 'select' button
	$('content-local-select').addClass('active');
};

// Called when user submits a local file
EOL.uploadWI.clickRefSubmit = function(){
	// Visual
	$('controls').addClass('displayNone');
	$('content-local').addClass('displayNone');
	EOL.uploadWI.showBusy();
	var i = 0;
	var elem;
	while( elem = $('localfile'+i) ){
		if( elem.checked ){
			// add the file to the 'list'
			EOL.uploadWI.swiffy.createDummyFile( elem.getAttribute('fname'), elem.value );

			// Hide the row so it can't be selected again
			$(elem.parentNode.parentNode).addClass('displayNone');
		}
		i++;
	}
	EOL.uploadWI.resizeIframe.delay(10);

	// Backend
	var myForm = $('form-demo');
	myForm.set('send', {
		onSuccess: EOL.uploadWI.onRefSuccess,
		onFailure: EOL.uploadWI.onRefFailure,
		onComplete: EOL.uploadWI.onRefComplete
	});
	myForm.send();
};
// Called when user cancels out of local file
EOL.uploadWI.clickRefCancel = function(){
	$('upload-source-remote').selected = true;
	EOL.uploadWI.reset();
};

// called when a user does 'upload a new version'
EOL.uploadWI.uploadNewVersion = function(fid){
	// Delay until we are ready for this crap
	if( !EOL.uploadWI.swiffy ){
		return setTimeout( 'EOL.uploadWI.uploadNewVersion('+fid+')', 100);
	}

	// GUI
	$('upload-source-remote').selected = true;
	EOL.uploadWI.onSourceChange();
	$('form-demo').uploadSource.disabled = true;

	// Backend
	EOL.uploadWI.mode = 'version';
	EOL.uploadWI.revise_fid = fid;
	EOL.uploadWI.swiffy.options.instantStart = true;
};


/** AJAX/Handlers **/

EOL.uploadWI.onCompleteSuccess = function(responseText, responseXML, file){
	var responseObj = eval('('+(responseText||'{}')+')');
	if( !responseObj || !responseObj.result ){
		return EOL.uploadWI.setErrorId('errorTitle', 'error500');
	}

	file.fid = responseObj.fileId;
	$('avail').innerHTML = responseObj.available;

	if( responseObj.addToCompany == 1 && !file.fake ){
		var text = 'Also add to my Account Files';
		if( responseObj.companyFileId ){
			text = 'Also update to my Account Files';
		}
		file.coLink = new Element('span', {'class': 'file-atc', 'html': ' | '}).adopt(
			new Element('a', {
				href: '#', html: text,
				events: {
					click: function(){
						file.addToCompany();
						return false;
					}.bind(file)
				}
 			})
		);
		file.coLink.inject(file.clearDiv, 'before');
	}
};

// Handler for request to check file
EOL.uploadWI.onValidateFileUploadSuccess = function(responseText, responseXML){
	$('dialog-collision').addClass('displayNone');

	var responseObj = eval('('+(responseText || '{}')+')');
	if( !responseObj || !responseObj.result ){
		return EOL.uploadWI.onValidateFileUploadFailure(this.xhr, 'error500');
	}
	if( responseObj.result == 'success' ){
		// Success: check for file collision
		if( responseObj.collision == 1 ){
			// File collision: do some setup then open dialog
			$(document.body).addClass('dialog-collision');

			if( responseObj.rootFileId) {
				$('collision_revision_div').removeClass('displayNone');
			} else {
				$('collision_revision_div').addClass('displayNone');
			}

			// Save data for use later
			EOL.uploadWI.collision_data   = responseObj;
			$('form-demo').newname.value = responseObj.fileRename; // default value
			$('collision-newname-ext').innerHTML = responseObj.fileExt;
			$('dialog-collision-filename').href = responseObj.fileURL;
			$('dialog-collision-filename').innerHTML   = responseObj.fileName;

			EOL.uploadWI.resizeIframe();
			return true;
		} else {
			// No collision: proceed with upload
			EOL.uploadWI.reset();
			return EOL.uploadWI.swiffy.start.delay(10, EOL.uploadWI.swiffy);
		}
	} else {
		// Process error!
		return EOL.uploadWI.onValidateFileUploadFailure(this.xhr, 'error500');
	}
};
EOL.uploadWI.onValidateFileUploadFailure = function(response, errorCode){
	EOL.uploadWI.collision_file.removeAction(); // Delete pending upload file
	EOL.uploadWI.reset();

	// alert the error
	if( !errorCode ){ errorCode = 'error' + response.status; }
	EOL.uploadWI.failureErrorId.delay(100, null, ['errorTitle', errorCode]);
};

// handlers for reference file
EOL.uploadWI.onRefSuccess = function(responseText, responseXML){
	var responseObj = eval('('+(responseText||'{}')+')');
	if( !responseObj || !responseObj.result ){
		return EOL.uploadWI.onRefFailure(this.xhr, 'error500');
	}

	// Okay, we need to mark each (ref) file (in swiffy) with the ref_id in order to do removes
	var results;
	for (var i in responseObj){
		results = i.match(/^ref_fid(\d+)$/);
		if( !results ){ continue; }

		var idx = results[1];
		var fid = responseObj['fid'+idx]; // Corresponding fname field
		var file = EOL.uploadWI.swiffy.findFile( fid ); // Get the file from swiffy
		if( file != null ){
            file.showFileRemove();
			file.ref_id = responseObj[i]; // Store the ref_id value [file_reference.id]
            file.fid = file.ref_id;
		}
	}

	EOL.uploadWI.hideBusy();
	if( EOL.uploadWI.dialogMode ) EOL.uploadWI.showDialogSaveBtn();
};
EOL.uploadWI.onRefFailure = function(response, errorCode){
	// Remove the file reference dummys
	for(var i=0; i<EOL.uploadWI.swiffy.fileList.length; i++) {
		var file = EOL.uploadWI.swiffy.fileList[i];
		if( file.fake && !file.ref_id ){ // These 2 conditions mean the ref file never succeeded
            file.showFileRemove();
			file.remove();
        }
	}

	// alert the error
	if( !errorCode ){ errorCode = 'error' + response.status; }
	EOL.uploadWI.failureErrorId.delay(100, null, ['errorTitle', errorCode]);
};
EOL.uploadWI.onRefComplete = function(){
	// Clear all the ref checkboxes
	var i = 0;
	var elem;
	while( elem = $('localfile'+i) ){
		elem.checked = false;
		i++;
	}
	EOL.uploadWI.reset();
};

// Handlers for remove file
EOL.uploadWI.onRemoveSuccess = function(responseText, responseXML){
	var responseObj = eval('('+(responseText||'{}')+')');
	if( !responseObj || !responseObj.result ){
		return EOL.uploadWI.onFailureGeneric();
	} else if( responseObj.result != 'success' ){
		return EOL.uploadWI.handleErrorGeneric(responseObj);
	}
	$('avail').innerHTML = responseObj.available;
};

// Handlers for add to company
EOL.uploadWI.onAddToCompanySuccess = function( responseText, responseXML, file ){
	var responseObj = eval('('+(responseText||'{}')+')');
	if( !responseObj || !responseObj.result ){
		return EOL.uploadWI.onFailureGeneric();
	};
	if( responseObj.result != 'success' ){
		return EOL.uploadWI.handleErrorGeneric(responseObj);
	}
	file.coLink.addClass('displayNone');
};

// Generic AJAX onFailure handler
EOL.uploadWI.onFailureGeneric = function(){
	EOL.uploadWI.failureErrorId('errorTitle','errorAjax');
};

// Generic Process error handler
EOL.uploadWI.handleErrorGeneric = function(response){
	var msg = '';
	for( var i = 0; i < response.errorMsgs.length; i++ ) {
		msg += (i == 0 ? '' : ', ') + response.errorMsgs[i];
	}
	EOL.uploadWI.failureError.delay(100, null, ['Please correct the following', msg]);
};

/** Fallback functions **/
EOL.namespace('uploadWI.fallback');

// Called by fallback to clear the files list
EOL.uploadWI.fallback.clearUploads = function(){
	$('fallback-list').innerHTML = '';
	parent.EOL.uploadW.hideBusy();
};
// Called by the fallback to list one file
EOL.uploadWI.fallback.notifyUpload = function(data){
	var dataArr = data.split('/');
	data = { name: dataArr[0], size: dataArr[1], fid: dataArr[2] };

	var filename = data.name;
	if(filename.length > EOL.uploadWI.max_filename_length) {
		filename = filename.substr(0, EOL.uploadWI.max_filename_length) + '...';
	}
	var sizeString = '&nbsp;&nbsp('+EOL.uploadWI.sizeToKB(parseInt(data.size || '0'))+')';
	new Element('li', { 'class':'file' }).adopt(
		new Element('div', { 'class':'file-name', 'html': filename }).adopt(
			new Element('span', { 'class':'file-size', 'html':sizeString })
		),
		new Element('a', {
			'class': 'file-remove',
			'href': '#',
			'html': 'Remove',
			'events': {
				'click': function() {
					var url = '/php/upload/main/fallbackUpload.php?';
					var params = getArgs();
					params = Object.merge(params, {
						fallback: 1,
						action: "delete",
						fileGroupId: EOL.uploadWI.fileGroupId,
						fileContext: EOL.uploadWI.fileContext,
						fileId: data.fid
					});
					url += Object.toQueryString(params);
					Attach(url, {reload: true});
					return false;
				}.bind(this)
			}
		}),
		new Element('div', { 'class' : 'clear' })
	).inject( $('fallback-list') );
    if( EOL.uploadWI.dialogMode ) EOL.uploadWI.showDialogSaveBtn();
	EOL.uploadWI.clearError();
    EOL.uploadWI.completeFile();
};

/** Fallback Iframe Functions **/
EOL.namespace('uploadWI.fallback.iframe');

EOL.uploadWI.fallback.iframe.init = function(){
	var args = getArgs();
	EOL.uploadWI.fallback.iframe.xdomain     = new XDomain(args.xdomain,'uploadW-fallback-iframe');
	EOL.uploadWI.fallback.iframe.parentName  = args.frameName;
	EOL.uploadWI.fallback.iframe.fileGroupId = args.fileGroupId;
	EOL.uploadWI.fallback.iframe.fileContext = args.fileContext;
	EOL.uploadWI.fallback.iframe.params = args;
};

EOL.uploadWI.fallback.iframe.open = function(){
	var url = '/php/upload/main/fallbackUpload.php?';
	var params = EOL.uploadWI.fallback.iframe.params;
	params['fallback'] = 1;
	url += Object.toQueryString(params);
	Attach(url);
};

EOL.uploadWI.fallback.iframe.clearUploads = function(){
	EOL.uploadWI.fallback.iframe.xdomain.makeCallThroughXDomain(
		EOL.uploadWI.fallback.iframe.parentName , 'EOL.uploadWI.fallback.clearUploads', new Array()
	);
};
EOL.uploadWI.fallback.iframe.showBusy = function(){
	EOL.uploadWI.fallback.iframe.xdomain.makeCallThroughXDomain(
		EOL.uploadWI.fallback.iframe.parentName, 'EOL.uploadWI.showBusy', new Array()
	);
};
EOL.uploadWI.fallback.iframe.notifyUpload = function(data){
	EOL.uploadWI.fallback.iframe.xdomain.makeCallThroughXDomain(
		EOL.uploadWI.fallback.iframe.parentName, 'EOL.uploadWI.fallback.notifyUpload', new Array(data)
	);
}


/** Other **/

EOL.uploadWI.sizeToKB = function(size){
	var unit = 'B';
	if ((size / 1048576) > 1) {
		unit = 'MB';
		size /= 1048576;
	} else if ((size / 1024) > 1) {
		unit = 'kB';
		size /= 1024;
	}
	return size.round(1) + ' ' + unit;
};


/*** FUNCTIONS FOR UPLOAD WIDGET ***/

EOL.namespace('uploadWW');

EOL.uploadWW.toggleView = function(){
	var container = $('uploadW-recent-files-container');

	if( container.className.search('closed') != -1 ){
		container.addClass('opened');
		container.removeClass('closed');
	} else {
		container.addClass('closed');
		container.removeClass('opened');
	}
};

EOL.uploadWW.reloadWidget = function(){
    if (!$('uploadW-recent-files-content')) {
        return false; // error: expecting DOM element doesn't exist!
    }
	$('uploadW-recent-files-loading').removeClass('displayNone');
	$('uploadW-recent-files-content').addClass('displayNone');
	var options = {
		url: '/php/files/main/recentFiles.php',
		method: 'get',
		data: 'bidid='+$('uploadW-recent-files-bidid').value +'&t='+getDateTime(),
		onSuccess: EOL.uploadWW.contentSuccess,
		onFailure: EOL.uploadWW.contentFailure
	};
	var ajax = new Request(options);
	ajax.send();
};

EOL.uploadWW.contentSuccess = function(responseText, responseXML){
	if( responseText == undefined || responseText == ''){
		return;
	}
	$('uploadW-recent-files-loading').addClass('displayNone');
	$('uploadW-recent-files-content').innerHTML = responseText;
	$('uploadW-recent-files-content').removeClass('displayNone');
};

EOL.uploadWW.contentFailure = function(response){
	$('uploadW-recent-files-loading').addClass('displayNone');
	return;
};

EOL.uploadWI.sendMetrics = EOL.uploadW.sendMetrics = {};

if (window.top.EOL.uploadWI) {
    EOL.uploadWI.sendMetrics = window.top.EOL.uploadWI.sendMetrics || {};
}

if (window.top.EOL.uploadW) {
   EOL.uploadW.sendMetrics = window.top.EOL.uploadW.sendMetrics || {};
}



/**
 * Javascript for process flow
 */


function flowHandleError() {
  //-- server down
}

function flowHandleSuccess( obj ) {
  //-- read JSON data
	var response = eval('(' + obj + ')');

  if( response.status == 'success' ) {
    flowJs.flowAdvanceTrain(response);
  }
  else if( response.status == 'error' ) {
		flowDisplayErrors( response );
  }
}


//-- Now handles simple error box style
//   with call to EOL.util.dialog.createErrorBox
function flowDisplayErrors( response ) {
	$(response.errorMsgsEl).removeClass( 'displayNone' );
	//-- Title
	if( response.errorTitle && $(response.errorMsgsEl + 'Title') ){
		$( response.errorMsgsEl + 'Title' ).innerHTML = response.errorTitle;
	}
	//-- List
	var list = '';
	if (response.errorMsgs) {
		for( var i = 0; i < response.errorMsgs.length; i++ ) {
			list += '<li>' + response.errorMsgs[i] + '</li>';
		}
	}
	var listOfErrors = '<ul>' + list + '</ul>';

	//-- Check which style of error box we have
  var selector = 'div#' + response.errorMsgsEl + 'List';

  if( $(response.errorMsgsEl + 'List') ) {
  	$( response.errorMsgsEl + 'List' ).innerHTML = listOfErrors;
	}
	else {
		//-- This must be the simple style    
		EOL.util.dialog.createErrorBox( 'errorBox', null, listOfErrors );
	}
	//-- Highlight DIVs to help users locate fields with error
	if( response.errorIds ) {
		for( var j = 0; j < response.errorIds.length; j++ ) {
			if($(response.errorIds[j]) && $(response.errorIds[j]).getAttribute("type") == 'error' ) {
				$(response.errorIds[j]).addClass( 'highlightError' );
			}
		}
	}
	var pos = $(response.errorMsgsEl).getPosition();
	window.scroll(0,pos.y - 100);
  enableElems(null);
}

//-----------------------------------------------------------------------------------------------
// Like the above, except it uses the info dialog to display the error. Make sure you include the
// info dialog template [hint: its included by default on any page using sign-in]
//-----------------------------------------------------------------------------------------------
function flowDisplayErrorsInfoDialogId( response, titleId, type ){
	return flowDisplayErrorsInfoDialogText( response, $(titleId).innerHTML, type );
}
function flowDisplayErrorsInfoDialogText( response, titleText, type ){
	// overwrite this value. The body contents will get set to 'infodialog'+'List';
	response.errorMsgsEl = 'infodialog';
	// clear this value: no highlighting!
	response.errorIds = null; 
	flowDisplayErrors( response );
	showInfoDialogText( titleText, $('infodialogList').innerHTML, type );
}

var flowErrorDialog = null;
function flowDisplayErrorsDialogText( response, titleText ){
	flowErrorDialog = new EOL.dialog(' ', {width: "350"});
	flowErrorDialog.show();

	// Set the default error EL to the dialog body, and create new contents for the dialog with +List
	response.errorMsgsEl = flowErrorDialog.bodyEl;
	flowErrorDialog.update([titleText, '<div id="'+ flowErrorDialog.bodyEl +'List"></div>']);
	
	// clear this value: no highlighting!
	response.errorIds = null; 
	
	flowDisplayErrors(response);
}

function clearFormErrorsAll( errorId ) {
	$(errorId).addClass('displayNone');
	var elems = $$('div[type=error]').append($$('tr[type=error]'));
  for( var k = 0; k < elems.length; k++ ) {
    $(elems[k]).removeClass('highlightError');
  }
}

function clearFormErrors( formsection ) {
  var elems = $$('div[type=error]').append($$('tr[type=error]'));
  for( var k = 0; k < elems.length; k++ ) {
		$(elems[k]).removeClass('highlightError' );
  }
}

var trainJs = {

  showStep : function( step, totSteps ) {
    for( var i = 1; i <= totSteps; i++ ) {
			if( $('trainStep' + i) !== undefined ) {
	      if( i < step ) {
  	      trainJs.trainDisplayHelper( $('trainStep' + i), $('trainLabelStep' + i), 'past', 'active', 'past' );
    	  }
	      else if( i == step ) {
	        trainJs.trainDisplayHelper( $('trainStep' + i), $('trainLabelStep' + i), 'active', 'active', 'active' );
	      }
	      else if( i > step ) {
	        trainJs.trainDisplayHelper( $('trainStep' + i), $('trainLabelStep' + i), 'inactive', 'inactive', 'inactive' );
	      }
			}
    }
  },

  trainDisplayHelper : function( tid, tlid, tclass, tlclass, tsclass ) {
    tid.setProperty( 'class', 'traincar trainline-' + tlclass + ' trainsquare-' + tsclass );

    tlid.removeClass( 'train-active' );
    tlid.removeClass( 'train-inactive' );
    tlid.removeClass( 'train-past' );
    tlid.addClass( 'train-' + tclass );
  }
};

/**
 * Parses '&' separated name=value arg pairs from
 * any string
 */
function parseStr(str) {
  var args = new Object();
  var query = str;
  var pairs = query.split("&");
  for(var i = 0; i < pairs.length; i++) {
    var pos = pairs[i].indexOf('=');
    if( pos == -1 ) continue;
    var argname = pairs[i].substring(0,pos);
    var value = pairs[i].substring(pos+1);
    value = decodeURIComponent(value);
    args[argname] = value;
  }
  return args;
}


var version = null;
var groupSpecific = false;
var group = getGetParam('groupId');
var jobOutline = null;
var allowedGroupIds = new Array();
var skilltesting = null;
var skillrecfallback = false;
var catalog_force_closed = null;


skillListWithType = new Array();

$(window).addEvent('domready', function() {
    window.onbeforeunload = noPostExit;
});

$(window).addEvent('domready', function() {
        if($('locField1') && $('locField1').checked){
            $('regioncountry').removeClass( 'displayNone' );
            newPost.toggleRegion();
        }else if($('locField1') && !$('locField1').checked){
            $('regioncountry').addClass( 'displayNone' );
            $('ctryCodeField').disabled = true;
            $('stateCodeField').disabled = true;
            $('cityField').disabled = true;
            $('zipCodeField').disabled = true;
            $$('input[name=regionCodeField[]]').map(function(e) {e.disabled=true; });
        }

        //GA
        var trackingIds = {
            'ttlField': 'Name',
            'descField': 'Description',
            'catIdField': 'Category',
            'autocomplete_text': 'Skills',
            'workTypeField': 'Work Type',
            'hourlyRate': 'Hourly Rate',
            'weeklyHours': 'Hrs/Week',
            'wkDurField': 'Duration'
        };
        Object.each(trackingIds, function(text, id){
            if ($(id)) {
                $(id).addEvent('blur', function(){
                    _gaq.push(['_trackEvent', 'JobDescribe', 'Field', text]);
                });
            }
        });
        var clickIds = {
            'continue-post': 'Continue',
            'descPostSaveBtnEnabled': 'Save & Post Later'
        };
        Object.each(clickIds, function(text, id){
            if ($(id)) {
                $(id).addEvent('click', function(){
                    _gaq.push(['_trackEvent', 'JobDescribe', 'Field', text]);
                });
            }
        })
});

(function($,$$){
    var events;
    var check = function(e){
        var target = $(e.target);
        var parents = target.getParents();
        events.each(function(item){
            var element = item.element;
            if (element != target && !parents.contains(element))
                item.fn.call(element, e);
        });
    };
    Element.Events.outerClick = {
        onAdd: function(fn){
            if(!events) {
                document.addEvent('click', check);
                events = [];
            }
            events.push({element: this, fn: fn});
        },
        onRemove: function(fn){
            events = events.filter(function(item){
                return item.element != this || item.fn != fn;
            }, this);
            if (!events.length) {
                document.removeEvent('click', check);
                events = null;
            }
        }
    };
})(document.id,$$);

function noPostExit() {
	//return("You will be leaving without saving your data.\nNEED HELP? Call Elance at +1-877-435-2623.");
}

function goTo(url) {
	window.onbeforeunload = null;
	window.location.href = url;
}

function handlePostingTypeChange(type) {
	if (type=='standard') {
		$('paymentDiv').style.display = ($('featPostField1').checked?'none':'');
	} else if(type=='featured') {
		$('paymentDiv').style.display = ($('featPostField2').checked?'':'none');
		$('featuredPostingFee').style.display='';
		$('b2bPostingFee').style.display='none';
	} else if (type=='b2b') {
		$('paymentDiv').style.display = ($('featPostField3').checked?'':'none');
		$('featuredPostingFee').style.display='none';
		$('b2bPostingFee').style.display='';
	}
}

function toDollars(cents) {
	dollars = Math.round(cents)/100;
	return '$' + dollars.toFixed(2);
}

function toCents(dollarStr) {
	dollarStr += '';
	if (dollarStr.indexOf('$') != -1) {
		return dollarStr.substr(dollarStr.indexOf('$')+1) * 100;
	} else {
		return dollarStr * 100;
	}
}

getCatId = function() { var a = $('catIdField').options[$('catIdField').selectedIndex].value;
                        return a; }


autoSelectJobType = function(type) {
    if(!$('workTypeField')) return;
	for(i = 0; i< $('workTypeField').options.length; i++){
		if ($('workTypeField').options[i].value == type){
			$('workTypeField').value =  type;
			$('workTypeField').fireEvent('change');
            if(version != 'B') {handleJobTypeChange()};
            break;
		}
	}
}


var PostJob = new Class ({
    initialize : function() {
    },

	submitPostAHR : function( form, errorElem ) {
		if($('fileGroupId')) $('fileGroupId').value = EOL.uploadW.getFileGroupId();
		closeAllTips();

        disableElems(null);
        clearFormErrors($('regError'));
        //var params  = 'mode=new&'+ this.getRegParams();

		var options = {
			method: 'post',
			onSuccess: function(req) { handlePostError(req);
                                 handleSuccess(req);
                               },
				onFailure: flowHandleError
		};

		if( form ) {
				$(form).set('send',options);
                if (form=="descPostForm" && typeof skillListWithType != "undefined"){
                    var input = new Element('input');
                    input.set('type','hidden');
                    input.set('name','skillsWithType');
                    input.set('value',JSON.stringify(skillListWithType));
                    input.inject($(form));
                }
				curAsyncReq = $(form).send();

		}
		else {
				options.url = '/php/post/main/postJobAHR.php?t=' + getDateTime(),
				curAsyncReq = new Request(options);
				curAsyncReq.send();
			}
		},

		submitJobPostAHR : function( post ) {
			disableElems(null);
			var options = {
				method: 'post',
				onSuccess: function(req) { handlePostError(req);
									 handleSuccess(req);
								   },
				onFailure: flowHandleError
			};
			var stage = 'p_prev';
			if(!post){
				stage = 'p_prevsave';
			}
			options.url = '/php/post/main/postJobAHR.php?t=' + getDateTime() + '&stage='+stage+'&draftIdField='+$('draftIdField').value + '&token=' + $('tokenField').value;
			if($('fpost')){
				options.url += '&fpost='+$('fpost').value;
		    }
            if($('repostJobId') && $('repostJobId').value.trim()!=""){
                options.url+= '&repostJobId='+$('repostJobId').value;
            }
            if($('inviteSource') && $('inviteSource').value.trim()!=""){
                options.url+= '&inviteSource='+$('inviteSource').value;
            }
            if ($('origCurrencyCode')) {
                options.url += '&origCurrencyCode='+$('origCurrencyCode').value;
            }
            if ($('destCurrencyCode')) {
                options.url += '&destCurrencyCode='+$('destCurrencyCode').value;
            }
            if ($('forexTradeId')) {
                options.url += '&forexTradeId='+$('forexTradeId').value;
            }
            if ($('conversionRate')) {
                options.url += '&conversionRate='+$('conversionRate').value;
            }
            if ($('convertedTotalAmount')) {
                options.url += '&convertedTotalAmount='+$('convertedTotalAmount').value;
            }

			curAsyncReq = new Request(options);
			curAsyncReq.send();
	},

	submitRegister : function () {
		var options = {
				method: 'post',
				onSuccess: function(req) { handlePostError(req);
								handleSuccess(req);
							},
				onFailure: flowHandleError
		};
		var stage = 'p_reg';
		options.url = '/php/post/main/postJobAHR.php?t=' + getDateTime() + '&stage='+stage;
		curAsyncReq = new Request(options);
		curAsyncReq.send();
	},

    showElement : function( showElemId ) {

		if( showElemId == 'feat' ) {
			// Set affiliate_pixel if mid125 set
			var mid125 = getCookie( 'mid125' );
			var userid = getCookie( 'userid' );
			try {
				if($('regStep')){
				conversionTracking(userid, $('city').value, $('stateProvince').value,$('country').value)
				}
			} catch(err) {}
			if ( $('affiliate_pixel') && mid125 && userid ) {
				var divContent = $('affiliate_pixel').innerHTML;
				if( !divContent.match("img") ) {
					$('affiliate_pixel').innerHTML = '<img src = "http://affiliates.elance.com/t/sale.php?mid=125&clid=' + userid + '" border=0 height=0 width=0 />';
				}
			}
		}

	},

	showFixedFeeFields : function() {
		if($('fixedOptions')) $('fixedOptions').removeClass('displayNone');
		if($('hourlyOptions')) $('hourlyOptions').addClass('displayNone');
		if ($('fixedNotes')) $('fixedNotes').removeClass('displayNone');
		if ($('hourlyNotes')) $('hourlyNotes').addClass('displayNone');
	},

	showHourlyFields : function() {
		if($('hourlyOptions')) $('hourlyOptions').removeClass('displayNone');
		if($('fixedOptions')) $('fixedOptions').addClass('displayNone');
		if ($('hourlyNotes')) $('hourlyNotes').removeClass('displayNone');
		if ($('fixedNotes')) $('fixedNotes').addClass('displayNone');
	},
	showHourlySection : function() {
		$('hourlyOption').removeClass('displayNone');
		$('budgetSectionFixed').addClass('displayNone');
		$('budgetSectionHourly').removeClass('displayNone');
		this.showHourlyFields();
	},
	showFixedFeeSection : function() {
		$('hourlyOption').addClass('displayNone');
		$('budgetSectionFixed').removeClass('displayNone');
		$('budgetSectionHourly').addClass('displayNone');
		this.showFixedFeeFields();
	},
	handleJobTypeChange : function() {
		if($('workTypeField2') && $('workTypeField2').checked == true || $('workTypeField') && $('workTypeField').options[$('workTypeField').selectedIndex].value == 'hourly') {
			this.showHourlySection();
		} else {
			this.showFixedFeeSection();
		}
	},
	handleHourlyBudgetRange : function() {
		var hr = $('hourlyRate').options[$('hourlyRate').selectedIndex].value;
		if(hr == 14280){
			$('hourlyCustomRange').removeClass('displayNone');
		} else {
			$('hourlyCustomRange').addClass('displayNone');
		}
	},
	handleFixedBudgetRange : function() {
		var fp = $('fixedBudget').options[$('fixedBudget').selectedIndex].value;
		if(fp == -2){
			$('fixedCustomRange').removeClass('displayNone');
		} else {
			$('fixedCustomRange').addClass('displayNone');
		}
	},
	handleHourlyTypeChange : function() {
		var hourlyType = $('hourlyType').options[$('hourlyType').selectedIndex].value;
		if(($('workTypeField2') && $('workTypeField2').checked == true  || $('workTypeField').value != 'fixed') && hourlyType == 12050){
			$('durationHoursErr').addClass('displayNone');
			$('weeklyHoursErr').removeClass('displayNone');
			$('wkDurFieldErr').removeClass('displayNone');
		} else if (($('workTypeField2') && $('workTypeField2').checked == true  || $('workTypeField').value != 'fixed') && hourlyType == 12049){
			$('weeklyHoursErr').addClass('displayNone');
			$('durationHoursErr').addClass('displayNone');
			$('wkDurFieldErr').removeClass('displayNone');
		} else {
			$('durationHoursErr').removeClass('displayNone');
			$('weeklyHoursErr').addClass('displayNone');
			$('wkDurFieldErr').addClass('displayNone');
		}
		if(hourlyType == 12049){
			$('wkDurField').options[0].style.display = 'none';
			$('wkDurField').options[1].style.display = 'none';
			$('wkDurField').options[0].disabled = true;
			$('wkDurField').options[1].disabled = true;
			if($('wkDurField').selectedIndex < 2){
				$('wkDurField').selectedIndex = 2;
				$('wkDurField').fireEvent('change');
			}
		} else {
			$('wkDurField').options[0].style.display = 'block';
			$('wkDurField').options[1].style.display = 'block';
			$('wkDurField').options[0].disabled = false;
			$('wkDurField').options[1].disabled = false;
		}
	},
	validateHourlyRateRange : function() {
		var lb = $('minHourlyRate').get('value');
		var ub = $('maxHourlyRate').get('value');
		var err = '';
		if (lb == '' || lb == 'Min') lb = 0;
		if (ub == '' || ub == 'Max') ub = 0;
		if (!/^\d+(\.\d+)?$/.test(lb)) {
			err = 'Please enter a valid minimum hourly rate.';
		} else if (!/^\d+(\.\d+)?$/.test(ub)) {
			err = 'Please enter a valid maximum hourly rate.';
		} else {
			lb = parseFloat(lb);
			ub = parseFloat(ub);
			if (lb > 0 && ub > 0 && lb > ub) {
				err = 'The minimum rate should be less than the maximum rate.';
			} else if ((lb > 0 && lb < 3) || (ub > 0 && ub < 3)) {
				err = 'Hourly rate should be at least US $3. <a href="/p/help/buyer/buyerbidmin.html" target="_blank">More Info</a>';
			}
		}
		$('hourlyRateError').set('html', err);
	},
	validateFixedBudgetRange : function() {
		var lb = $('minFixedBudget').get('value');
		var ub = $('maxFixedBudget').get('value');
		var err = '';
		if (lb == '' || lb == 'Min') lb = 0;
		if (ub == '' || ub == 'Max') ub = 0;
		if (!/^[\d\.]+$/.test(lb)) {
			err = 'Please enter a valid minimum budget.';
		} else if (!/^[\d\.]+$/.test(ub)) {
			err = 'Please enter a valid maximum budget.';
		} else {
			lb = parseFloat(lb);
			ub = parseFloat(ub);
			if (lb > 0 && ub > 0 && lb > ub) {
				err = 'The minimum budget should be less than the maximum budget.';
			} else if ((lb > 0 && lb < 20) || (ub > 0 && ub < 20)) {
				err = 'Total job value should be at least US $20. <a href="/p/help/buyer/buyerbidmin.html" target="_blank">More Info</a>';
			}
		}
		$('fixedBudgetError').set('html', err);
	},

    showRegionCountryField : function() {
        if($('locField1').checked){
            $('regioncountry').removeClass( 'displayNone' );
            newPost.toggleRegion();
        }else{
            $('regioncountry').addClass( 'displayNone' );
            $('ctryCodeField').disabled = true;
            $('stateCodeField').disabled = true;
            $('cityField').disabled = true;
            $('zipCodeField').disabled = true;
            $$('input[name=regionCodeField[]]').map(function(e) {e.disabled=true; });
        }
    },

    showLocAnywhereField : function() {
        $('nearmyloc').addClass( 'displayNone' );
    },

	showLocNearMeField : function(city, state, country, zip, region) {
		if(city || state || country || zip || region){
            $('locField1').checked = 'checked';
            newPost.showRegionCountryField();
            if(region){
                $('locField3').checked = 'checked';
                var regionNames = '';
                $$('input[name=regionCodeField[]]').map(function(e) {
                    e.checked = ''; //clear old value if any
                    if(region.indexOf(e.value)>-1){
                        e.checked='checked';
                        var regionArray = new Array();
                        regionArray['01'] = "North America";
                        regionArray['02'] = "Western Europe";
                        regionArray['03'] = "Eastern Europe";
                        regionArray['04'] = "India/Southern Asia";
                        regionArray['05'] = "Eastern Asia";
                        regionArray['06'] = "Middle East & Central Asia";
                        regionArray['07'] = "Central & South America";
                        regionArray['08'] = "Africa";
                        regionArray['09'] = "Australia/Oceania";

                        regionNames += regionArray[e.value] + ', ';
                    }
                });
                regionNames = regionNames.substring(0, regionNames.length-2);
                var subRegNames = '';
                if(regionNames.length > 20){
                    subRegNames = regionNames.substring(0, 20)+'...';
                }else{
                    subRegNames = regionNames;
                }
                if(regionNames){
                    $('eol-customselect-selected').set('text', subRegNames);
                }else{
                    $('eol-customselect-selected').set('text', '- Select Region(s) -');
                }
                $('eol-customselect-selector').setAttribute('title', regionNames);
                newPost.toggleRegion();
            }else{
                $('locField4').checked = 'checked';
                if(country!='US'){
                    $('zipFieldErr').addClass('displayNone');
                    $('zipCodeField').disabled = true;
                    $('stateFieldErr').addClass('displayNone');
                    $('stateCodeField').disabled = true;
                    $('cityFieldErr').setStyle('padding-left', '0px');
        //            $('cityField').setStyle('width', '169px');
                }
                else {
                    $('zipFieldErr').removeClass('displayNone');
                    $('zipCodeField').disabled = false;
                    $('stateFieldErr').removeClass('displayNone');
                    $('stateCodeField').disabled = false;
                }
                if(country){
                    $$('.ctryCodeField').map(function(e) {
                        if(e.value == country){
                            $('ctryCodeField').value = country;
                            $('ctryCodeField').fireEvent('change');
                        }
                    });
                }
                if(state){
                    $$('.stateCodeField').map(function(e) {
                        if(e.value == state){
                            $('stateCodeField').value = state;
                            $('stateCodeField').fireEvent('change');
                        }
                    });
                }
                if(zip){
                    $('zipCodeField').set('text', zip);
                }
                if(city){
                    $('cityField').set('text', city);
                }
                newPost.toggleRegion();
            }
        }else{
            $('nearmyloc').removeClass( 'displayNone' );
        }
	},

	toggleDescAsst : function() {
		if( $('descAsst').className.indexOf( 'displayNone' ) >= 0 ) {
			$('descAsst').removeClass( 'displayNone' );
			$('descAsstPlus').addClass( 'displayNone' );
			$('descAsstMinus').removeClass( 'displayNone' );
		} else {
			$('descAsst').addClass( 'displayNone' );
			$('descAsstPlus').removeClass( 'displayNone' );
			$('descAsstMinus').addClass( 'displayNone' );
		}
	},

	toggleZipCodeField : function() {
		if( $('ctryCodeField').value != 'US' ) {
			$('zipFieldErr').addClass('displayNone');
			$('zipCodeField').value = '';
			$('zipCodeField').disabled = true;
            $('stateFieldErr').addClass('displayNone');
            $('stateCodeField').value = '';
            $('stateCodeField').disabled = true;
            $('cityFieldErr').setStyle('padding-left', '0px');
    //        $('cityField').setStyle('width', '140px');
		} else {
			$('zipFieldErr').removeClass('displayNone');
			$('zipCodeField').disabled = false;
            $('stateFieldErr').removeClass('displayNone');
            $('stateCodeField').disabled = false;
            $('cityFieldErr').setStyle('padding-left', '3px');
    //        $('cityField').setStyle('width', '169px');
		}
        if($('ctryCodeField').getSelected()[0].text.length > 17){
            $('ctryCodeField').setAttribute('title', $('ctryCodeField').getSelected()[0].text);
        }else{
            $('ctryCodeField').setAttribute('title', '');
        }
	},

    toggleRegion : function() {
        if( $('locField4').checked ) {
            $$('input[name=regionCodeField[]]').map(function(e) {e.disabled=true; });
            $$('div.eol-customselect-menu').addClass( 'displayNone' );
        //    $('c_groupFilter').removeEvents('click');
            $('nearmyloc').removeClass( 'displayNone' );
            $('ctryCodeField').disabled = false;
            $('stateCodeField').disabled = false;
            $('cityField').disabled = false;
            $('zipCodeField').disabled = false;
        //    $('eol-customselect-selected').setStyle('color', '#939393');
        } else if($('locField3').checked) {
            $$('input[name=regionCodeField[]]').map(function(e) {e.disabled=false; });
            $('nearmyloc').addClass( 'displayNone' );
            $('c_groupFilter').removeEvents('click');
            $('c_groupFilter').addEvent('click', function(e){
                var customselectClassName = $$('div.eol-customselect-menu').get('class');
                if(e.target.id=='eol-customselect-selected' || e.target.id=='sprCtrl-arrow-down-blue' || e.target.id=='eol-customselect-selector'){
                    if(customselectClassName.toString().indexOf('displayNone')>-1){
                        $$('div.eol-customselect-menu').removeClass( 'displayNone' );
                    }else{
                        $$('div.eol-customselect-menu').addClass( 'displayNone' );
                        newPost.updateLocMultiSelect();
                    }
                }
            });
            $('c_groupFilter').addEvent('outerClick',function() {
                $$('div.eol-customselect-menu').addClass( 'displayNone' );
                newPost.updateLocMultiSelect();
            });
            $('ctryCodeField').disabled = true;
            $('stateCodeField').disabled = true;
            $('cityField').disabled = true;
            $('zipCodeField').disabled = true;
        }
    },

    updateLocMultiSelect : function(e){
        var regionNames = '';
        $$('input[name=regionCodeField[]]:checked').map(function(e) {
            var regionArray = new Array();
            regionArray['01'] = "North America";
            regionArray['02'] = "Western Europe";
            regionArray['03'] = "Eastern Europe";
            regionArray['04'] = "India/Southern Asia";
            regionArray['05'] = "Eastern Asia";
            regionArray['06'] = "Middle East & Central Asia";
            regionArray['07'] = "Central & South America";
            regionArray['08'] = "Africa";
            regionArray['09'] = "Australia/Oceania";

            regionNames += regionArray[e.value] + ', ';
        });
        regionNames = regionNames.substring(0, regionNames.length-2);
        var subRegNames = '';
        if(regionNames.length > 20){
            subRegNames = regionNames.substring(0, 20)+'...';
        }else{
            subRegNames = regionNames;
        }
        if(regionNames){
            $('eol-customselect-selected').set('text', subRegNames);
        }else{
            $('eol-customselect-selected').set('text', '- Select Region(s) -');
        }
        $('eol-customselect-selector').setAttribute('title', regionNames);

    },

	removeFileId : function( fileId, folderId, refId  ) {
		if( fileId ) {
			var params = Object.toQueryString({action: 'delete',
										ctx: 'job_draft',
										gid: folderId,
										ref_id: (refId > 0) ? refId : fileId
			});
			var options = {
				method: 'get',
					data: params,
					onSuccess: this.removeFileSuccess,
				onFailure: flowHandleError,
					onComplete: function(){ rfCurAsyncReq = null; },
					url: '/php/files/main/upload.php?t=' + getDateTime()
			};
				rfCurAsyncReq = new Request(options);
				rfCurAsyncReq.send();
		}
	},

	removeFileSuccess : function( obj ) {
		var response = eval('(' + obj + ')');
		if( response.result == 'success' ) {
				$('prevFile' + response.ref_id).dispose();
		}
		else if( response.result == 'error' ) {
			flowDisplayErrors( response );
		}
	},


		setStageMode : function( mode ) {
			if($('stage')) {
				$('stage').value = $('stage').getProperty(mode);
	}
		}

});

var newPost = new PostJob();

function handlePostError(obj) {
    var response = eval('('+ obj +')');

    if(response.status == 'error') {
        enableElems(null);
        flowDisplayErrors( response );

        // commerce custom handler
        if (typeof EOL.commerce !== 'undefined') {
            var tradeApprovalFailed = EOL.commerce.handleLocalPayError(response);
            if (tradeApprovalFailed && $('previewPostFeeAmountCcy')) {
                $('previewPostFeeAmountCcy').addClass('displayNone');
            }
        }
    }
    tokenHandleSuccess(obj);
}


function handleDescAsst(obj) {
	var response = eval('(' + obj + ')');
	$('descAsst').innerHTML = response.data.description;
	jobOutline = response.data.outline;
}

function setCatForm() {
	var catid = $('catIdField').options[$('catIdField').selectedIndex].value;
	setSubcatOptions(catid);
	setSubcatForm();
	if( $('postMode').innerHTML == 'POST' && version == 'A' && skilltesting != 'B') {
		if(catid) {
			EOL.autocomplete.enable();
		}
		else {
			EOL.autocomplete.disable();
		}
	}
	if(catid) {
		$('subcatform').setStyle('visibility','visible');
	}
	else {
		$('subcatform').setStyle('visibility','hidden');
	}
	$('subcatIdField').fireEvent('change');

}

function tokenHandleSuccess( obj ) {
    var response = eval('(' + obj + ')');
    if( response.token ) {
        if( $('tokenField') ){ $('tokenField').value = response.token; }
    }
}

function setSubcatForm() {
	var catid = $('catIdField').options[$('catIdField').selectedIndex].value;
	var subcatid = $('subcatIdField').options[$('subcatIdField').selectedIndex].value;
	if(version =='A' && (skilltesting != 'B' || skillrecfallback)) {
		loadAutoSkills();
	}
}


function handleSuccess(obj){
        var response = eval('(' + obj + ')');
        //-- Success action is to redirect
			if(  response.data && response.data.redirectURL ) {
				var account = getGetParam('account');
				var redirectUrl = response.data.redirectURL;
				if(account == 1){
					redirectUrl += '&account=1';
				}
				var utype = getGetParam('utype');
				if(utype){
					redirectUrl += '&utype=' + utype;
				}
				window.onbeforeunload = null;
				window.location.href = redirectUrl;
        }

}
var canAutoTag = true;

function loadAutoSkills()
{
    if (!canAutoTag) return;

	//alert('In loadAutoSkills1');
    var title = $('ttlField').get('value').trim();
    var descr = $('descField').get('value').trim();
    if(version == 'B' && $('ttlField').value == $('sampleTitle').value) {
        return;
    }
    if (title.length < 1) return;

    var subcat = $('subcatIdField').get('value').trim();
    if (subcat.length < 1) return;
    if (isNaN(subcat)) return;

    new Request.JSON({
        url: '/php/post/main/skillRecommend.php?t=' + getDateTime(),
        method: 'get',
        data: { 'title': title, 'desc': descr , 'subcat': subcat },
        onSuccess: function(ret) {
            if (!canAutoTag) return;
            if (ret.data && ret.data.skills) {
                //alert('clearing');
                if(version == 'B') {
                    EOL.postSkills.clearAutoSkills();
                } else if(skilltesting == 'B' || skillrecfallback) {
                    EOL.postSkillsB.clearAll(true);
                } else {
                    unsetAutoSkills();
                }
                if (!(ret.data.skills instanceof Array)) {
                    for (var skill in ret.data.skills) {
                        if (version =='B') {
                            EOL.postSkills.addSkill(skill, ret.data.skills[skill], true);
                        } else if(skilltesting == 'B' || skillrecfallback) {
                            if (ret.data.skills[skill] != null){
                                uniqueAdd(skillListWithType, ret.data.skills[skill],'rec');
                            }
                            else{
                                uniqueAdd(skillListWithType, skill,'rec');
                            }
                            EOL.postSkillsB.addSkill(skill, ret.data.skills[skill], true);
                        } else if (ret.data.skills[skill] > 0) {
                            setTag('skills', ret.data.skills[skill], skill, true);
                            uniqueAdd(skillListWithType, ret.data.skills[skill],'rec');

                        } else {
                            setCustomSkill(skill, true);
                            uniqueAdd(skillListWithType, skill,'rec');
                        }
                    }

                }
            }
        }
    }).send();
}


uniqueAddIdOrName=function(array, name, Id){
    if (Id!=null){
        uniqueAdd(array, Id, 'prev');
    }
    else if(name!=null) {
        uniqueAdd(array, name, 'prev');
    }
}



uniqueAdd = function(array, item,type){
    if (item!=null){
        var exist = false;
        for(var i=0;i<array.length; i++){
            if (array[i].skill.toString()==item.toString() && array[i].type!='prev')
                exist =true;
        }
        if (!exist){
            array.push({"skill":item.toString(),"type":type});
        }
        return;
    }

}


removeSkillFromList = function(skillId){

    if (typeof skillListWithType!="undefined"){
        for(var i=0; i<skillListWithType.length; i++){
            if (skillListWithType[i].skill.toString().trim().toLowerCase().split(' ').join('') ==skillId.toString().trim().toLowerCase()&& skillListWithType[i].type !="prev"){
                var temp = skillListWithType.splice(i,1);
            }
        }
    }
}

function addAllowedGroups(){
	if ($('allowedGroups')){
		var groups = JSON.decode($('allowedGroups').get('value'));
		groups.each(function(group, key){
			setTag('groups',group.ID,group.NAME, false, 'allowedGroups');
		});

		$('groupTagWarning').hide();
	}
}


function setJobTypeOption() {
	if((version == 'B' && $('workTypeField').options[$('workTypeField').selectedIndex].value == 'fixed') ||
			(version == 'A' && $('jobTypeField1').checked == true)) {
		$('projectTitle').removeClass('displayNone');
		$('noProjectTitle').addClass('displayNone');

		$('jobTypeForm').removeClass('positionType');
		if ($('workTypeField1').checked == true) {
			$('jobTypeForm').removeClass('projectHourlyType');
			$('jobTypeForm').addClass('projectFixedType');
			newPost.showFixedFeeFields();
		} else {
			$('jobTypeForm').removeClass('projectFixedType');
			$('jobTypeForm').addClass('projectHourlyType');
			newPost.showHourlyFields();
		}
	} else if ((version == 'B' && $('workTypeField').options[$('workTypeField').selectedIndex].value == 'hourly') ||
			(version == 'A' && $('jobTypeField2').checked == true)) {
		$('projectTitle').addClass('displayNone');
		$('noProjectTitle').removeClass('displayNone');

		$('projectTitle').addClass('displayNone');
		$('jobTypeForm').removeClass('projectFixedType');
		$('jobTypeForm').removeClass('projectHourlyType');
		$('jobTypeForm').addClass('positionType');
		newPost.showHourlyFields();
	}

}

function setRepeatJobTypeOption() {
	if( $('workTypeField2').checked == true ) {
		newPost.showHourlyFields();
	} else {
		newPost.showFixedFeeFields();
	}
}

function setInviteOnlyOptionCheck() {
  if ($('bidTypeField0').checked == true) {
    $('bidTypeField2').checked = true;
  } else {
    $('bidTypeField1').checked = true;
  }
  setInviteOnlyOption();
}

function setInviteOnlyOption() {
	if($('bidTypeField1').checked == true) {
		$('showSeoIndexForm').removeClass('displayNone');
    		if($('bidTypeField0')) $('bidTypeField0').checked = false;
        $('seoIndexField').disabled = false;
    } else if ($('bidTypeField2').checked == true || $('bidTypeField3').checked == true) {
		$('showSeoIndexForm').addClass('displayNone');
    		if($('bidTypeField0')) $('bidTypeField0').checked = true;
        $('seoIndexField').disabled = true;
	}
}
function showAdvOption() {
	if(version == 'B') {
		setTimeout("$('showAdvOptionTxt').addClass('displayNone'); $('hideAdvOptionTxt').removeClass('displayNone');", 1000);
		$('advanceOption').removeClass('displayNone');
		$('advanceOption').set('tween', {duration: 1000});
		$('advanceOption').tween('opacity', [0,1]);
		if(!$('repeatPostSection')) initCal(false);
	} else {
		$('showAdvOptionTxt').addClass('displayNone');
		$('hideAdvOptionTxt').removeClass('displayNone');
		$('advanceOption').removeClass('displayNone');
		if(!$('repeatPostSection')) initCal(false);
	}
}

function hideAdvOption() {
	if(version == 'B') {
		$('advanceOption').set('tween', {duration: 1000});
		$('advanceOption').tween('opacity', [1,0]);
		setTimeout("$('advanceOption').addClass('displayNone'); $('showAdvOptionTxt').removeClass('displayNone'); $('hideAdvOptionTxt').addClass('displayNone');",1000);
	} else {
		$('showAdvOptionTxt').removeClass('displayNone');
		$('hideAdvOptionTxt').addClass('displayNone');
		$('advanceOption').addClass('displayNone');
	}
}

function openTip(tipcount) {
	closeAllTips();
	if(eval('dont_show_for_session_' +tipcount) || eval('ignore_tip_over_' + tipcount)) {
		return;
	}

	if(tipcount == 2 || tipcount == 6) {
		hideGroupTip();
	} else {
  		if(tipcount == 3) hideSample();
		showGroupTip(false);
	}
	$('tip'+tipcount).removeClass('displayNone');
}

function closeTip(tipcount) {
	//if(tipcount ==13)  { /* alert(tipcount + '-onmouse-'+ eval('on_tip_' + tipcount)); */ }
	if($('tip'+tipcount) && (!eval('on_tip_' + tipcount) || eval('ignore_tip_over_' + tipcount) == true)  ) {
		$('tip'+tipcount).addClass('displayNone');
	}
	// Ths is to force tooltip 14 (job description tooltip) to not show again after once 'close' is clicked.
	if(eval('ignore_tip_over_' + tipcount) == true && tipcount == 14) {
		eval('dont_show_for_session_' +tipcount +' = true;');
	}
}

function openTitleTooltip()  {
	if(!$('jobAssistant')) {
		openTip(13);
	} else if($('jobAssistant').style.display == 'none') {
		openTip(13);
	}
}


function closeTitleTooltip() {
	closeTip(13);
}

function handleTipClick(Id){
    document.addEvent('mousedown',function(event){checkClick(event,Id);});
}

function checkClick(event, Id){
    if($(event.target).getParents().contains($('tip'+Id))) {
        return;
    }
    else{
        document.removeEvent('mousedown',function(event){checkClick(event,Id);});
        closeTip(Id);
    }
}

function openJobTypeTooltip() {
    handleTooltip();
}

function closeJobTypeTooltip() {
    $('workArrangTooltip').addClass('displayNone');
}

function showSample() {
	$('descSample').removeClass('displayNone');
	$('descTip').addClass('displayNone');
	hideGroupTip();
}

function hideSample() {
	$('descSample').addClass('displayNone');
	$('descTip').removeClass('displayNone');
	showGroupTip(false);
}

function hideGroupTip(){
	if($('tipgroup')){
                $('tipgroup').addClass('displayNone');
        	$('group_specific').style.backgroundColor = '';
        }
}

function showGroupTip(nocheck){
	if (nocheck) closeAllTips();
	if($('tipgroup') && $('group_optout') && (nocheck || $('selectedgroup1194'))){
                $('tipgroup').removeClass('displayNone');
		$('group_specific').style.backgroundColor = '#DFF1FF';
        }
}

function showSelectedGroupTip(){
	if($('selectedgroup1194')){
		showGroupTip(false);
	} else {
		openTip(6);
        }
}

function closeAllTips() {
	for(var i=1; i<=20; i++) {
		if ($('tip'+i)) $('tip'+i).addClass('displayNone');
	}
	if($('group_optout') && $('group_optout').checked){
		hideGroupTip();
		if(!groupSpecific) {
			$('required_group').checked = false;
			groupSpecific = true;
		}
	}
    if ($('workArrangTooltip').isDisplayed()) {
        closeWorkArrangTooltip();
    }
}

function handleJobTypeTip() {
	if(version == 'A') {
		if ($('workTypeField1').checked) {
			newPost.showFixedFeeSection();
			if($('fixedTipSec')) $('fixedTipSec').style.display = '';
			if($('hourlyTipSec')) $('hourlyTipSec').style.display = 'none';
		} else {
			$('workTypeField2').checked = true;
			newPost.showHourlySection();
            if($('hourlyTipSec')) $('hourlyTipSec').style.display = '';
            if($('fixedTipSec')) $('fixedTipSec').style.display = 'none';
		}
	}
}

function populateJobOutline() {
	var answer = false;
	if ($('descField').value.length > 0) {
		answer = confirm("Your job description will be overwritten. Do you still want to continue?");
	}

	if (answer || $('descField').value.length ==	0) {
		jobOutline = jobOutline.replace(/###/g, '\n\n');
		$('descField').value = jobOutline;
		simpleTextCounter($('descField'), $('descCharLimit'), 4000);
	}
}

function setStartDate() {
	$('jobStartDateOption2').checked = true;
}

var initStartDateCal = false;
function initCal( isRepeat ) {
	if (initStartDateCal) return;
	new Picker.Date($('dateField'), {
	    positionOffset: {x: -5, y: 5},
	    pickerClass: 'datepicker_elance',
	    toggle: $('calactivate'),
	    startDay: 0,
	    format: "%x"
	});
	initStartDateCal = true;
	}

/**
 * initDescPost is called before the footer on the post page is loaded
 */
function initDescPost() {
	version = $('version') ? $('version').value : 'A';
	if(version == 'A') {
		skilltesting = ($('skilltesting').value == 'B' ? $('skilltesting').value : 'A');
	}

	descPostInit = true;

	var postSection = 'descPostSection';
	if( $('postMode').innerHTML == 'REPEAT' ) {
		postSection = 'repeatPostSection';
	}

	if($('postMode').innerHTML == 'POST' && version == 'A') {
		$('ttlField').focus();
	}

	//-- If we are in Post Mode, set the work location field
	if( $('postMode').innerHTML == 'POST' ) {
		// set correct job type tip on post page load
		if(version == 'A') {
			handleJobTypeTip();
		}

		// initialize autogrow input
		if(version == 'B') {
			var grow = new Form.AutoGrow('descField');
		}

		newPost.toggleZipCodeField();

		if(version == 'A') {
			if(skilltesting == 'B') {
				EOL.postSkillsB.init();
				$('groups-interaction').setStyle('display', '');
				EOL.postGroupsB.init();
				if ($('isPTC')){
                    var isPTC = $('isPTC');
                } else {
                    var isPTC = false;
                }
                if (!isPTC) {
                    $('groups-interaction').setStyle('display', 'none');
                }

			} else {
				//unless category is preset, disable the tag section
				if($('catIdField').value) {
					EOL.autocomplete.enable();
				}
				else {
					EOL.autocomplete.disable();
				}

				//initialize skill autocomplete
				EOL.autocomplete.init();
			}
		}

		if(version == 'B') {
			EOL.postSkills.init();
		}

		// catalog force close ettings on version B of the page
		catalog_force_closed = false;
		if(version == 'B') {
			var newClient = (!Cookie.read('hasPostedJob') || Cookie.read('hasPostedJob') == 0);
			if(newClient) {
				catalog_force_closed = true;
			}
		}

	}
	else if( $('postMode').innerHTML == 'REPEAT' ) {
		setRepeatJobTypeOption();
	}
	//setTimeout(	'newPost.requestDescAsst()', 4000 );

	// init feat post page
	//-- TODO
	//setTimeout('newPost.buildFeature()', 10000);
	newPost.handleHourlyTypeChange();
	//-- If we are in Repeat Mode, set the hourly total field
	if( $('repeatPostSection') && $('repeatPostSection').getProperty('active') == 'on' ) {
		//newPost.calculateHourlyTotal();

		// initialize the calender
		setTimeout( 'initCal(true)', 10000 );
	}

	if( version=='A' && skilltesting!= 'B' && $('selectedGroupsList') ){
		setTimeout( "EOL.autocomplete.displayAll('all_skills')", 4000);
	}

	if(version == 'A' && skilltesting != 'B') {
		addAllowedGroups();
	}

	descPostInit = false;
    if ($('workTypeField')){
        //This is to address EOL-54196. Job catalog data is set in javascript
        handleJobTypeChange();
        $('workTypeField').fireEvent('change');
    }
}

function skipPost(employer){
	if(employer){
		trackPostEvent('skip', 'FTP_START_SKIP_EREG');
	} else {
		trackPostEvent('skip', 'FTP_START_SKIP_PREG');
	}
	window.onbeforeunload = function(){};
	var signupid='';
	if($('emailSignupsIdField') && $('emailSignupsIdField').value){
		signupid = '?emailSignupsId='+$('emailSignupsIdField').value;
	}
	if(employer){
		window.location.href = '/register'+signupid;
	} else {
		window.location.href = '/newseller'+signupid;
	}
}


EOL.uploadW.showBusy = function(){
  if($('descPostSaveBtnEnabled')) $('descPostSaveBtnEnabled').addClass('displayNone');
  disableElems(null);
};

EOL.uploadW.hideBusy = function(){
  if($('descPostSaveBtnEnabled')) $('descPostSaveBtnEnabled').removeClass('displayNone');
  enableElems(null);
};

EOL.uploadW.setError = function(titleText, bodyText, type){
	$('descPostError').removeClass( 'displayNone' );
	var uploadError = {"status":"error","errorMsgs":[bodyText],"stage":"upload","data":null,"errorMsgsEl":"descPostError","token":null}
	flowDisplayErrors( uploadError );
}

// called when there is an error.
EOL.uploadW.clearError = function(){
	$('descPostError').addClass( 'displayNone' );
};


handleTitleFocus = function() {
	$('ttlField').removeClass('job-title-blur');
	$('ttlField').addClass('job-title-focus');

	if($('ttlField').value == $('sampleTitle').value) {
		$('ttlField').value = '';
	}
}

handleTitleBlur = function() {
	if(!$('ttlField').value || $('ttlField').value == $('sampleTitle').value) {
		$('ttlField').removeClass('job-title-focus');
		$('ttlField').addClass('job-title-blur');

		$('ttlField').value = $('sampleTitle').value;
	}
}

/* Category top skills  */


function showSkillsTip() {
	if($('catIdField').options[$('catIdField').selectedIndex].value <=0 ) {
		return;
	}

	adjustTopSkillsCategory();
    EOL.postSkills.showSkillTooltip();
}

function adjustTopSkillsCategory() {
	var selectedCat = $('catIdField').options[$('catIdField').selectedIndex].value;
	if(selectedCat <= 0 )
		return;

	var cats = $$('[class=topSkillsCat]');
	Array.each(cats, function(cat) {
		if(cat.getProperty('id').indexOf(selectedCat) >=0 ) {
			cat.style.display = '';
		} else {
			cat.style.display = 'none';
		}
	});
}

function handleAddTopSkill(label, value) {
	if(EOL.postSkills.selectedSkillsCnt < 5) {
		EOL.postSkills.addSkill(label, value);
		hideTopSkill(value);
	} else {
        EOL.postSkills.skillsFocus = true;
        EOL.postSkills.checkLimitError()
    }
}

function hideTopSkill(id) {
	if($('topSkill-'+ id)) {
		$('topSkill-'+ id).style.display = 'none';
	}
}

/* Skills interacton */

EOL.namespace('postSkills');
EOL.postSkills.skillList = null;
EOL.postSkills.defaultText = 'Enter skill or expertise';


EOL.postSkills.init = function() {

	var remoteReq = {  url: '/php/post/main/postSkillTagsAHR.php?version=B',
					   param: 'q' };

	if ($('allowedGroupIds')){
		remoteReq.extraParams = {
			'allowedGroups': JSON.decode($('allowedGroupIds').get('value')).join(',')
		};
	}

	EOL.postSkills.skillList = new TextboxList('skillsarea', {
			onFocus:function(){
                // Max limit error
				EOL.postSkills.skillsFocus = true;
                EOL.postSkills.checkLimitError();
            },
			onBlur:function() {
				// Max limit error
				EOL.postSkills.skillsFocus = false;
                EOL.postSkills.checkLimitError();
			},
            unique: true,
			startEditableBit: false,
			inBetweenEditableBits: false,
			max: $('allowedGroups') ? 100 : 10,
            plugins: {
                autocomplete: {
					maxLength: 100,
                    queryRemote: true,
                    placeholder: '',
					maxResults: 100,
                    remote: remoteReq
                }
            },
			onBitEditableAdd: function(bit) {
	            bit.setValue([null, EOL.postSkills.defaultText, null]);
				// control length of input to avoid wierd ui issues.
				var data = $$('input.textboxlist-bit-editable-input');
				for(var i=0; i< data.length; i++) {
					data[i].setAttribute('maxlength', 40)
					data[i].setStyle('color', '#939393');
                }
			},
			onBitEditableFocus: function(bit) {
				if (EOL.postSkills.skillList.index.length >= 8) {
					bit.blur();
				}

				var data = $$('input.textboxlist-bit-editable-input');
				for(var i=0; i< data.length; i++) {
					data[i].setStyle('color', '#333333');
				}

				// introduce minimal delay for Safari bug
				window.setTimeout(function() {
					var v = bit.getValue();
					if (v[1] == EOL.postSkills.defaultText) {
						bit.setValue([null, '', null]);
					}
				}, 1);

				// tip for category specific skills
                EOL.postSkills.showSkillTooltip();
				/* closeAllTips(); showSkillsTip(); */
			},
			onBitEditableBlur: function(bit) {
				if(EOL.postSkills.skillList.plugins.autocomplete.mouse_in_container) {
					return;
				}
				if (bit.getValue()[1] != EOL.postSkills.defaultText) {
					EOL.postSkills.skillList.plugins.autocomplete.addCurrentInput();
					bit.setValue([null, EOL.postSkills.defaultText, null]);
					var data = $$('input.textboxlist-bit-editable-input');
					for(var i=0; i< data.length; i++) {
						data[i].setStyle('color', '#939393');
					}
				}

				// Close the skills tip
				closeTip(15);
			},
			onBitBoxAdd: function(box) {
				// Set custom skill
				var b = box.getValue();
				if(b[2].indexOf('groups[id][]') < 0 && b[2].indexOf('skills[id][]') < 0) {
					var val = [ b[0], b[1], b[2] + '<input type="hidden" name="skills[custom][]" value="' +b[1] +'"/>'];
					box.update(val);
				}

				// check on max limit.
				EOL.postSkills.checkLimitError();
				// display group only checkbox if needed.
				EOL.postSkills.setGroupOnlyOption();

				var d = $(EOL.postSkills.skillList.list).getChildren('.textboxlist-bit-box-deletable');
				if ($('allowedGroups') && d.length == 2){
					$$('.textboxlist-bit-box-deletebutton').each(function(ele){
						ele.show();
					});
				}
			},
			onBitBoxRemove: function(box) {
				// set editable text  and style
			    var b = $(EOL.postSkills.skillList.list).getChildren('.textboxlist-bit-editable');
				for(var i=0; i< b.length; i++) {
					var editable = EOL.postSkills.skillList.getBit(b[i]);
					editable.setValue([null, EOL.postSkills.defaultText, null]);
				}
				var data = $$('input.textboxlist-bit-editable-input');
				for(var i=0; i< data.length; i++) {
					data[i].setStyle('color', '#939393');
				}

				EOL.postSkills.checkLimitError();
				EOL.postSkills.setGroupOnlyOption();

				var d = $(EOL.postSkills.skillList.list).getChildren('.textboxlist-bit-box-deletable');
				if ($('allowedGroups') && d.length == 1){
					$$('.textboxlist-bit-box-deletebutton').each(function(ele){
						ele.hide();
                       });
				}
			}
        });

}

EOL.postSkills.showSkillTooltip = function(){
    var category = getCatId();
    if($('topSkillsCat-'+category)){
        $$('.topSkillsCat').each(function(ele){ele.addClass('displayNone')})
        $('topSkillsCat-'+category).removeClass('displayNone');
        $('topSkillList-'+category).getChildren().each(function(elem){
            var skillid = $(elem).id.replace('topSkill-','');
            if(!$(skillid) && $(elem) && $(elem).hasClass('textboxlist-bit-box') && $(elem).style.display == 'none'){
                $(elem).style.display = 'block';
        }});
        openTip(15);
    } else {
        closeTip(15);
    }
}

EOL.postSkills.checkLimitError = function() {
	var skills = 0, groups = 0;

	var groupLimit = $('allowedGroups') ? 300000 : 3;

	var bits = EOL.postSkills.skillList.container.getElements('.textboxlist-bit-box');
	var bit, v, obj;
	for (var i = 0; i < bits.length; ++i) {
		bit = EOL.postSkills.skillList.getBit(bits[i]);
		v = bit.getValue();
		if(v[2].indexOf('groups[id][]') > 0) {
			groups++;
			if(groups > groupLimit) {
				bit.remove();
			}
		} else {
			skills++;
			if(skills > 5) {
				bit.remove();
			}
		}
    }



	EOL.postSkills.selectedSkillsCnt = skills;
	EOL.postSkills.selectedGroupsCnt = groups;

	if(EOL.postSkills.skillsFocus && (skills >=5 || groups >= groupLimit)) {
		if ($('allowedGroups')){
    		$('skillError').set('html', 'Add up to 5 skills.');
    	}
		$('skillError').style.display = '';
	} else {
		$('skillError').style.display = 'none';
	}

}


EOL.postSkills.setGroupOnlyOption = function() {
	var showGroupOnlyOption = false;
	var skillValues = EOL.postSkills.skillList.getValues();
	Array.each(skillValues, function(skillValue) {
		if(skillValue[2].indexOf('groups[id]') != -1) {
			showGroupOnlyOption = true;
		}
	});

	if(showGroupOnlyOption == true) {
		$('groupOnlyOption').style.display = '';
	} else {
		$('groupOnlyOption').style.display = 'none';
	}
}


EOL.postSkills.addSkill = function(label, value, auto, noFocus)
{
	if (!auto) canAutoTag = false;

    if (noFocus) {
        EOL.postSkills.selfAdd = true;
    }
    if (!EOL.postSkills.skillList.plugins.autocomplete.currentInput) {
        EOL.postSkills.skillList.focusLast();
    }

    var html;
    var escaper = new Element('div');
    value = escaper.set('text', value).get('html');
    label = escaper.set('text', label).get('html');
    if (value) {
   	    html = '<span title="Skill" id="' + value +'">' + label + '<input type="hidden" name="skills[id][]" value="' + value + '" /></span>';
    } else {
       	value = label;
   	    html = '<i>' + label + '</i>';
    }

    var val = [ value, label, html ];
    var b = EOL.postSkills.skillList.create('box', val);
    if (b){
        b.autoValue = val;
        if (EOL.postSkills.skillList.plugins.autocomplete.index != null) {
            EOL.postSkills.skillList.plugins.autocomplete.index.push(val);
        }
        b.inject($(EOL.postSkills.skillList.plugins.autocomplete.currentInput), 'before');
        EOL.postSkills.skillList.plugins.autocomplete.currentInput.setValue([null, EOL.postSkills.defaultText, null]);
    }
    EOL.postSkills.selfAdd = false;
}

EOL.postSkills.removeTag = function(box, auto) {
	if (!auto) canAutoTag = false;
	box.remove();
}


EOL.postSkills.addGroup = function(label, value, noFocus)
{
    if (noFocus) {
        EOL.postSkills.selfAdd = true;
    }
    if (!EOL.postSkills.skillList.plugins.autocomplete.currentInput) {
		EOL.postSkills.skillList.plugins.autocomplete.currentInput = EOL.postSkills.skillList.getBit(EOL.postSkills.skillList.list.getLast());
    }

	var html = '<span title="Group" style="color:#60973B;" id="' + value + '">' + label + '<input type="hidden" name="groups[id][]" value="' + value + '" /></span>';

    var val = [ value, label, html ];
    var b = EOL.postSkills.skillList.create('box', val);
    if (b){
        b.autoValue = val;
        if (EOL.postSkills.skillList.plugins.autocomplete.index != null) {
            EOL.postSkills.skillList.plugins.autocomplete.index.push(val);
        }
        b.inject($(EOL.postSkills.skillList.plugins.autocomplete.currentInput), 'before');
        EOL.postSkills.skillList.plugins.autocomplete.currentInput.setValue([null, EOL.postSkills.defaultText, null]);
    }
    EOL.postSkills.selfAdd = false;
}

EOL.postSkills.clearAllSkillsGroups = function() {
	var b = $(EOL.postSkills.skillList.list).getChildren('.textboxlist-bit-box');
	for(var i=0; i< b.length; i++) {
		var box = EOL.postSkills.skillList.getBit(b[i]);
		EOL.postSkills.removeTag(box);
	}
}

EOL.postSkills.clearAutoSkills = function() {
	var b = $(EOL.postSkills.skillList.list).getChildren('.textboxlist-bit-box');
	for(var i=0; i< b.length; i++) {
		var box = EOL.postSkills.skillList.getBit(b[i]);
		var val = box.getValue();
		if(val[2].indexOf('skills[id][]') > 0) {
			EOL.postSkills.removeTag(box, true);
		}
	}
}

EOL.postSkills.getSkillInputParams = function() {
	return {'version' : 'B',
			't' : getDateTime()
			};
}

function handleHintText(field, defaultValue){
    if(!$(field).value.match(/\d+/) && !$(field).value.match(/\d+\.\d+/)){
        $(field).addClass('eol-input-hinttext');
        $(field).value = defaultValue
    } else {
        $(field).removeClass('eol-input-hinttext');
    }
}

function showHourlyTipScreenshot(){
	$('screenshotImg').style.display = '';
	$('screenshotLink').style.display = 'none';
}

function hideHourlyTipScreenshot() {
	$('screenshotLink').style.display = '';
	$('screenshotImg').style.display = 'none';
}

function enableHTMLPostDropdown(){
    new EOL.HtmlSelect('wkDurField');
    new EOL.HtmlSelect('fixedBudget');
    $('weeklyHours').setStyle('marginLeft','0px');
    new EOL.HtmlSelect('hourlyRate');
    if($('workTypeField').value == 'fixed'){
        if($('fixedBudget').value == -2){
            $('fixedCustomRange').removeClass('displayNone');
        }
    } else {
        if($('hourlyRate').value == 14280){
            $('hourlyCustomRange').removeClass('displayNone');
        }
    }
    new EOL.HtmlSelect('workTypeField');
}

var showWorkArrangTooltip = true;
function closeWorkArrangTooltip(){
    $('workArrangTooltip').addClass('displayNone');
    showWorkArrangTooltip = false;
}
function handleTooltip(){
    if(showWorkArrangTooltip) {
        $('workArrangTooltip').removeClass('displayNone');
    }
}

function handleJobTypeChange(){
    if($('workTypeField').value == 'fixed'){
        $('weeklyHoursErr').addClass('displayNone');
        $('budgetSectionHourly').addClass('displayNone');
        $('budgetSectiondFixed').removeClass('displayNone');
        $('hourlyCustomRange').addClass('displayNone');
        $('wkDurFieldErr').addClass('displayNone');
        if($('fixedBudget').value == -2){
            $('fixedCustomRange').removeClass('displayNone');
        }
    } else {
        $('weeklyHoursErr').removeClass('displayNone')
        $('budgetSectionHourly').removeClass('displayNone');
        $('budgetSectiondFixed').addClass('displayNone');
        $('fixedCustomRange').addClass('displayNone');
        $('wkDurFieldErr').removeClass('displayNone');
        if($('hourlyRate').value == 14280){
            $('hourlyCustomRange').removeClass('displayNone');
        }
    }
}

EOL.namespace('abTest');


EOL.namespace('abTest.VerbiageType');
EOL.abTest.VerbiageType.switchExperiment = function(experiment){
    if(experiment=='VersionA'){
        $('verbiageTypeVersionB').dispose();
    }
    if(experiment=='VersionB'){
        $('otherOption').innerHTML = 'Location and Other Options <b>-</b>&nbsp;';
        $$('.verbiageTypeVersionA').each(function(el){
            el.dispose();
        })
        $$('.verbiageTypeVersionB').each(function(el){
            el.set("id", el.id.replace('VersionB', ''));
            if(el.name) el.set("name", el.name.replace('VersionB', ''));
        })
        $('verbiageTypeVersionB').removeClass('displayNone');
        $('jobLocationFieldErr').setStyle('padding-top', '0px');
    }
    if(experiment=='VersionC'){
        $('otherOption').innerHTML = 'Location, Visibility and Other Options <b>-</b>&nbsp;';
        $('bidTypeField2Lable').innerHTML = 'Invite Only&mdash;Do not show on Elance jobs list. Only candidates I invite can respond.';
    }
}

EOL.namespace('abTest.hourlyType');

EOL.abTest.hourlyType.switchExperiment = function(experiment){
    if($('hourlyType' + experiment)){
        $('defaultHourlyType').innerHTML = $('hourlyType' + experiment).innerHTML;
        $$('.testVersion').each(function(el){
            el.dispose();
        })
        $$('.'+experiment).each(function(el){
            el.set("id", el.id.replace(experiment, ''));
            if(el.name) el.set("name", el.name.replace(experiment, ''));
        })
        var attempt = Function.attempt(EOL.abTest.hourlyType.customExec(experiment));
    }
}

EOL.abTest.hourlyType.customExec = function(experiment){
        new EOL.HtmlSelect('wkDurField');
        new EOL.HtmlSelect('fixedBudget');
        $('weeklyHours').setStyle('marginLeft','0px');
        if($('fixedBudget').value == -2){
            $('fixedCustomRange').removeClass('displayNone');
        }
        handleJobTypeTip();
}

EOL.abTest.hourlyType.handleHourlyType = function(){
    $('hourlyType').value = 12050;
}


/**
 * Framework for forms.
 * THIS FILE IS STILL IN PROGRESS.
 */
EOL.namespace('framework.form');

/**
 * Async Form. Takes a form ID and options hash as args.
 * Sets up the handleSubmit function to Async the form to server, and handle response (RequestMsg format)
 * Designed to take care of dynamically adding the 't' input, setting new form tokens, smartly handling
 * errors in the RequestMsg format using formUtil or infoDialogs if available, and handling confirm
 * urls when available.
 * Options:
 * async   - if false, submits the form the old fashioned way. Only really useful for form.dialog
 * hidden  - if true, skips all visual code, like showing wait cursor
 * errorEl - if set, assume this is a flowUtil error Element
 * mainEl  - if set, this marks the top-level ID for this form. Used for enable/disableElems, error clearing
 */
EOL.framework.form.async = function(formEl, opts){
	this.formEl = formEl;
	this.form = $(this.formEl);
	var defaults = {
		async: true,
		hidden: false,
		errorEl: null,
		confirmEl: null,
		mainEl: null,
		method: 'post'
	};
	EOL.utility.mergeObjects(defaults, opts);
	this.options = defaults;
	this.loading = false; // Used to tell if we're still waiting for content
	this.initRequest = false;

	this.setOptions = function(opts){
		EOL.utility.mergeObjects(this.options, opts);
	};
	this.beforeError = function(response){};
	this.afterError  = function(response){};

	// This onsubmit code prevents the user from submitting the form the old fashioned way
	if( this.form ) {
		var me = this;
		this.form.onsubmit = function(e){ e.preventDefault(); me.handleSubmit(); };
	}
};

/** Validates the form. Needs to be overriden to do anything useful **/
EOL.framework.form.async.prototype.validate = function() {
	return true;
};

/** Submits the form **/
EOL.framework.form.async.prototype.handleSubmit = function() {
	// error clearing
	if( this.options.mainEl && typeof clearFormErrorsAll == 'function' ){
		clearFormErrors(this.options.mainEl);
	}
	if( this.options.confirmEl ) {
		$(this.options.confirmEl).addClass('displayNone');
	}
	if( typeof clearFormErrorsAll == 'function' && this.options.errorEl ){
		clearFormErrorsAll(this.options.errorEl);
	} else if( this.options.errorEl ) {
		$(this.options.errorEl).addClass('displayNone');
	}

	if( !this.validate() ){
		return false;
	}

	if( !this.options.async ){
		this.form.submit();
		return true;
	}

	if( !this.options.hidden ) disableElems( $(this.options.mainEl) );
	var async = this;
	var options = {
		method   : this.options.method,
		onSuccess:  function(req) {async.handleAjaxSuccess(req);},
		onFailure:  function(req) {async.handleAjaxFailure(req);},
		onComplete: function(req) {async.handleAjaxComplete(req);}
	};
	if( MooTools.version == '1.11' ){
		this.form.send(options);
	} else {
		if(!this.initRequest) {
			this.form.set('send', options);
			this.initRequest = true;
		}
		this.form.send();
	}

	this.loading = true;
	return true;
};
/** For use in href=javascript: fashion. Has no return so the link doesn't activate **/
EOL.framework.form.async.prototype.handleSubmitHref = function() {
	this.handleSubmit();
};

/** AJAX success: determine process result **/
EOL.framework.form.async.prototype.handleAjaxSuccess = function(res) {
	this.responseText = res;
	this.loading = false;
	if( !res || res.indexOf('{') == -1 ) res = '{}';
	var response = eval('('+ res +')');
	if( !response || !response.status ){
		this.handleFailure();
		return;
	}
	if( response.token ) EOL.framework.form.resetTokens(response.token);
	if( response.status == 'success') {
		this.handleSuccess(response);
	} else {
		this.handleError(response);
	}
};

/** AJAX Failure: throw an error **/
EOL.framework.form.async.prototype.handleAjaxFailure = function(res) {
	this.loading = false;

	// This will happen if the user navigates while the request is still running
	if( res.readyState == 4 && res.status == 0 ) return;

	// We have a legitimate error: display it
	this.handleFailure(res);
	this.handleAjaxComplete(res); // failure case does not automatically call this like it should; mootools 1.11 issue
};
EOL.framework.form.async.prototype.handleAjaxComplete = function(res){};

/** AJAX + process success **/
EOL.framework.form.async.prototype.handleSuccess = function(response) {
	if( response.data && response.data.confirmURL ){
		// Confirm URL
		window.location.href = response.data.confirmURL;
	} else if( response.data && response.data.confirmElemId && response.data.confirmTitle && response.data.confirmText ) {
		enableElems( $(this.options.mainEl) );
		EOL.util.dialog.createConfirmBox( response.data.confirmElemId, response.data.confirmTitle, response.data.confirmText );
	} else if( response.data && response.data.confirmElemId && response.data.confirmElemId == this.options.confirmEl ) {
		enableElems( $(this.options.mainEl) );
		$(response.data.confirmElemId).removeClass('displayNone');
	} else if( response.data && response.data.confirmText ){
		// Confirmation in the response
		enableElems( $(this.options.mainEl) );
		if( typeof showInfoDialogText == 'function' ){
			// Info dialog: preferred
			showInfoDialogText( response.data.confirmTitle, response.data.confirmText, 'INFO' );
		} else {
			alert(response.data.confirmTitle +': '+ response.data.confirmText);
		}
	} else {
		if( !this.options.hidden ) enableElems( $(this.options.mainEl) );
		this.handleSuccessDefault(response);
	}
};

// For overriding. Be warned that if your dialog is not hidden, it will auto-call enableElems above. Consider
// overriding handleSuccess proper if you don't want that.
EOL.framework.form.async.prototype.handleSuccessDefault = function(response) {
	if( !this.options.hidden ) window.location.reload(true);
};


/** AJAX failure **/
EOL.framework.form.async.prototype.handleFailure = function(){
	var errorMsgs = new Array();
	errorMsgs[0] = "There was an error communicating with Elance.";
	var response = { errorTitle : "We're Sorry", errorMsgs: errorMsgs };
	this.handleError(response);
};

/** AJAX or process error **/
EOL.framework.form.async.prototype.handleError = function(response) {
	if( this.options.hidden ) return;
	enableElems( $(this.options.mainEl) );
	this.beforeError(response);
	var errorTitle = (response.errorTitle ? response.errorTitle : 'Please correct the following');

	// If we have an errorEl and no errorMsgsEl was set in the response, use ours
	if( this.options.errorEl && !response.errorMsgsEl ) response.errorMsgsEl = this.options.errorEl;

	if( typeof flowDisplayErrors == 'function' && response.errorMsgsEl && $(response.errorMsgsEl) ){
		// Flow util: preferred
		this.options.errorEl = response.errorMsgsEl;
		flowDisplayErrors(response);
	} else if( typeof flowDisplayErrorsDialogText == 'function' ){
		// Dialog if available
		flowDisplayErrorsDialogText( response, errorTitle );
	} else if( typeof flowDisplayErrorsInfoDialogText == 'function' ){
		// Info Dialog if available
		flowDisplayErrorsInfoDialogText( response, errorTitle, 'WARN' );
	} else {
		this.showErrors(response, errorTitle);
	}
	this.afterError(response);
};

EOL.framework.form.async.prototype.showErrors = function(response, errorTitle) {
	// Alert msg by default
	var msg = '';
	for( var i = 0; i < response.errorMsgs.length; i++ ) {
		msg += (i == 0 ? '' : ', ') + response.errorMsgs[i];
	}
	if( typeof EOL.dialog == 'function' ){
		var opts = {width: "350"};
		if( this.dialog && this.dialog.optons && this.dialog.options.className ) {
			opts.className = this.dialog.options.className;
		} else if( this.options && this.options.dialogClassName ) {
			opts.className = this.options.dialogClassName;
		}
		this.errorDialog = new EOL.dialog([errorTitle,msg], opts);
		this.errorDialog.show();
	} else if( typeof showInfoDialog == 'function' ){
		showInfoDialogText(errorTitle, msg, 'WARN');
	} else {
		alert(errorTitle +': '+ msg);
	}
};

/**
 * Like async, but gets an HTML response from the server that gets written into contentEl.
 *
 * controls:
 * waiting - allows the caller to make this wait before hiding the loadingEL. Useful for synchronizing events
 *
 * Options:
 * loadingEl - specify an element that is shown while loading
 * onComplete - function to call when we're done. Useful for synchronizing
 */
EOL.framework.form.asyncContent = function( formEl, contentEl, opts ){
	var me = this;
	var defaults = {
		loadingEl: null,
		onComplete: null,
		mainEl: contentEl
	};
	EOL.utility.mergeObjects(defaults, opts);
	EOL.framework.form.asyncContent.superclass.constructor.call(this, formEl, defaults);
	this.contentEl = contentEl;
	this.waiting = false; // Used to make the showLoading wait for some other event

	// set our loading flag, hideLoading, and set the response in page. Call oncomplete
	this.handleAjaxSuccess = function(response){
		this.responseText = response;
		this.loading = false;
		this.hideLoading();
		// If we get a JSON response, handle using parent
		if( response && response.replace(/^\s*/, "").indexOf('{') == 0 ){
			EOL.framework.form.asyncContent.superclass.handleAjaxSuccess.call(this, response);
			return;
		}
		if( !response ){
			this.handleFailure();
			return;
		}
		this.handleSuccess( {data: {html: response}} );
	};

	this.handleSuccess = function(response){
		if( response && response.data && response.data.html ){
			$(this.contentEl).innerHTML = response.data.html;
		}
		// If we have an onComplete and we aren't waiting, call it
		if( !this.waiting ) this.onComplete(response);
	};

	// Show the loadingEl and hide the contentEl
	this.showLoading = function(){
		if( this.options.loadingEl ){
			$(this.options.loadingEl).removeClass('displayNone');
			$(this.contentEl).addClass('displayNone');
		}
		if( !this.options.hidden ) disableElems( $(this.options.mainEl) );
	};

	// hide the loadingEl and show the contentEl
	this.hideLoading = function(){
		// If we are waiting or still loading, do not do this and let the caller know
		if( this.waiting || this.loading ) return false;
		if( this.options.loadingEl ){
			$(this.options.loadingEl).addClass('displayNone');
			$(this.contentEl).removeClass('displayNone');
		}
		// Have to call this here because of the waiting case
		if( !this.options.hidden ) enableElems( $(this.options.mainEl) );
		return true;
	};
	// To be executed after complete
	this.onComplete = function(){};
	if( this.options.onComplete ) this.onComplete = this.options.onComplete;
};

EOL.utility.extend(EOL.framework.form.asyncContent, EOL.framework.form.async);

// Show loading on submit
EOL.framework.form.asyncContent.prototype.handleSubmit = function(){
	if( EOL.framework.form.asyncContent.superclass.handleSubmit.call(this) ){
		this.showLoading();
	};
};


/**
 * Form Dialog class. Takes the dialog ID, form ID (MUST BE INSIDE THE DIALOG), and options hash
 * Extends the async form class.
 * Contains a EOL.dialog class for the dialog UI
 * Call handleSubmit to submit the dialog. Override handleSubmit for custom behaviors
 *
 * Options: (Also uses EOL.dialog options)
 * inlineSuccess - set to true if success shown inside dialog. then it won't hide on success
 * inlineErrorEl - set to ID if error shown inside dialog. then it won't hide on error
 */
EOL.framework.form.dialog = function( dialogEl, formEl, opts ){
	var me = this;
	var defaults = { // defaults
		inlineSuccess: false,
		inlineErrorEl: false,
		width : "430",
		mainEl: dialogEl
	};
	EOL.utility.mergeObjects(defaults, opts);
	if( defaults.inlineErrorEl && !defaults.errorEl ){ defaults.errorEl = defaults.inlineErrorEl; }
	EOL.framework.form.dialog.superclass.constructor.call(this, formEl, defaults);

	this.innerElement = $(dialogEl);

	this.beforeShow = function(){};
	this.afterShow  = function(){};

	this.beforeHide = function(){};
	this.afterHide  = function(){};

	// Create our dialog to handle UI
	this.dialog = new EOL.dialog($(dialogEl), defaults);

	// Override the default close to call hide
	this.dialog.eventHide = function(){ me.hide(); };
};

EOL.utility.extend(EOL.framework.form.dialog, EOL.framework.form.async);

EOL.framework.form.dialog.prototype.show = function(){
	this.beforeShow(); this.dialog.show(); this.afterShow();
};
EOL.framework.form.dialog.prototype.hide = function(){
	this.beforeHide(); this.dialog.hide(); this.afterHide();
};
EOL.framework.form.dialog.prototype.update = function(){
	this.beforeShow(); this.dialog.update($(this.dialogEl).innerHTML); $(this.dialogEl).innerHTML = ''; this.afterShow();
};

// If you override this, be sure to call hide when necessary.
EOL.framework.form.dialog.prototype.handleSuccess = function(res){
	if( !this.options.inlineSuccess ) this.dialog.hide();
	EOL.framework.form.dialog.superclass.handleSuccess.call(this, res);
};

// If you override this, be sure to call hide when necessary.
EOL.framework.form.dialog.prototype.handleError = function(res){
	if( this.options.inlineErrorEl ) res.errorMsgsEl = this.options.inlineErrorEl;
	else this.dialog.hide();
	EOL.framework.form.dialog.superclass.handleError.call(this, res);
};


/**
 * an async content + dialog mixed together.
 *   The asyncContent is used to load the dialog contents
 *   This object extends dialog and functions just like the dialog except for the load
 */
EOL.framework.form.asyncDialog = function( aFormEl, aOpts, dialogEl, dFormEl, dOpts ){
	var me = this;
	this.asyncContent = new EOL.framework.form.asyncContent( aFormEl, dialogEl, aOpts );
	this.asyncContent.owner = this;
	this.asyncContent.onComplete = function(response){
		me.initSelf(); me.show(); if( aOpts.onComplete ) aOpts.onComplete.call(this, response);
	};

	// Save these args for later
	this.dialog = null;
	this.dialogEl = dialogEl;
	this.dFormEl  = dFormEl;
	this.dOpts    = dOpts;

	this.loaded = false;
	this.initSelf = function(){
		EOL.framework.form.asyncDialog.superclass.constructor.call(this, this.dialogEl, this.dFormEl, this.dOpts);
		this.loaded = true;
		this.afterInitSelf();
	};
	this.afterInitSelf = function(){};

	this.unload = function(){ this.loaded = false; };
};
EOL.utility.extend(EOL.framework.form.asyncDialog, EOL.framework.form.dialog);

// Handle loading the dialog if it hasn't been already
EOL.framework.form.asyncDialog.prototype.show = function(){
	if( this.loaded ){
		if(this.dOpts.id && $(this.dOpts.id)) {
			this.update();
		}
		else {
			EOL.framework.form.asyncDialog.superclass.show.call(this);
		}
	} else {
		this.asyncContent.handleSubmit();
	}
};

// These next 2 override clear out the dialog if it is closed
EOL.framework.form.asyncDialog.prototype.hide = function(){
	if( !this.loaded ) return;
	this.unload();
	EOL.framework.form.asyncDialog.superclass.hide.call(this);
};
EOL.framework.form.asyncDialog.prototype.handleSuccess = function(res){
	this.unload();
	EOL.framework.form.asyncDialog.superclass.handleSuccess.call(this, res);
};

// util to reset all tokens in the page
EOL.framework.form.resetTokens = function(newToken){
	EOL.utility.resetFormTokens(newToken, 'token');
};


EOL.namespace('POEdit');

EOL.POEdit.init = function() {
	var opt = {mainEl: 'poNumbers', confirmEl: 'prefConfirm', errorEl: 'prefError'};
	EOL.POEdit.poAddNewDialog = new EOL.framework.form.dialog('poAddNewDialog', 'newPOForm', opt);
	EOL.POEdit.poAddNewDialog.handleSuccessDefault = EOL.POEdit.handleSaveSuccess;
	EOL.POEdit.poAddNewDialog.hideDialog = EOL.POEdit.poAddNewDialog.hide;
	EOL.POEdit.poAddNewDialog.hide = EOL.POEdit.cancelNewPO;
};

EOL.POEdit.createNewPO = function(bidid) {
	var opt = $('poSelectList').selectedIndex;
	var val = $('poSelectList').options[opt].getAttribute('value');
	if (val == '+') {
		$('bididField').value = bidid;
		EOL.POEdit.poAddNewDialog.show();
	}
};

EOL.POEdit.cancelPO = function() {
	$('poEditArea').style.display = 'none';
	$('poNumberArea').style.display = 'block';
};

EOL.POEdit.cancelNewPO = function() {
	if (EOL.POEdit.poAddNewDialog) EOL.POEdit.poAddNewDialog.hideDialog();
	$('newPOError').style.display = 'none';
	var bidid = $('bididField').getAttribute('value');
	if ((bidid == undefined) || (bidid > 0) || (bidid == '')) {
		$('poEditArea').style.display = 'none';
		$('poNumberArea').style.display = 'block';
	} else {
		$('poSelectList').options[0].selected = 'true';
		$('poSelectList').fireEvent('change');
	}
	return false;
};

EOL.POEdit.togglePOEdit = function(poid) {
	$('poNumberArea').style.display = 'none';
	EOL.POEdit.selectIt(poid);
	$('poEditArea').style.display = 'block';
};

EOL.POEdit.currentPOid = null;

EOL.POEdit.selectIt = function (poid) {
  if (EOL.POEdit.currentPOid != null) {
    poid = EOL.POEdit.currentPOid;
  }
  if (poid == 0) poid = '-';
	for (var i=0;i<$('poSelectList').options.length;i++) {
		if ($('poSelectList').options[i].value == poid) {
			$('poSelectList').options[i].selected = 'true';
			$('poSelectList').fireEvent('change');
			break;
		}
	}
};

EOL.POEdit.displayError = function (error) {
	$('newPOError').innerHTML = error;
	$('newPOError').style.display = 'block';
};

EOL.POEdit.saveSelectedPO = function(bidid) {
	showBusy( $('saveSelectedPOBtnEnabled'), $('saveSelectedPOBtnDisabled') );
  showBusy( $('cancelPOBtnEnabled'), $('cancelPOBtnDisabled') );

	var opt = $('poSelectList').selectedIndex;
	var val = $('poSelectList').options[opt].getAttribute('value');
	var udfId = $('udfId').value;
	if (val.search(/^[a-zA-Z0-9]*$/) == -1) {
		if (val == '+') {
			EOL.POEdit.createNewPO(bidid);
			return;
		}
		if (val == '' || val == '-') {
			val = '';
		} else {
			return;
		}
	}
	var postData = 'type=bid&projectId='+bidid+'&id='+udfId+'&valueId='+val+'&mode=ChangeProjectValue';
	var request =  new Request({
			url: '/php/preferences/main/udfAHR.php',
			method: 'post',
	        data: postData,
	        onSuccess: function(req) {
	            hideBusy( $('cancelPOBtnEnabled'), $('cancelPOBtnDisabled') );
	            hideBusy( $('saveSelectedPOBtnEnabled'), $('saveSelectedPOBtnDisabled') );

				var ret = eval('('+req+')');
				if (ret.status != 'success') {
					EOL.POEdit.displayError(ret.errorMsgs);
					return;
				}
				EOL.POEdit.cancelNewPO();
				if (ret.data.PO == null) ret.data.PO = 'Not Specified';
					$('currentPONumber').innerHTML = $('poSelectList').options[$('poSelectList').selectedIndex].innerHTML;
					EOL.POEdit.selectIt(ret.data.value.valueId);
				}
	        });
	request.send();
};

EOL.POEdit.saveNewPO = function() {

	var bidid = $('bididField').value;
	var po = '', desc = '';
	if( EOL.utility.isTouchedFormInput($('newPONumber')) ) {
		po = $('newPONumber').value;
	}
	if( EOL.utility.isTouchedFormInput($('newPODesc')) ) {
		desc = $('newPODesc').value;
	}

	// ready for AJAX call TODO: check variables name and if anything else needs to be added
	var mode = 'SavePONumber';
	var number = encodeURIComponent(po);
	var description = encodeURIComponent(desc);
	var postData = 'poNumber='+number+'&poDesc='+description+'&bidid='+bidid+'&mode='+mode;

	var request =  new Request({
			url: '/php/preferences/main/poActions.php',
			method: 'post',
	        data: postData,
	        onSuccess: function(req) {
				var ret = eval('('+req+')');
				if (ret.status != 'success') {
					EOL.POEdit.displayError(ret.errorMsgs);
					return;
				}
				EOL.POEdit.cancelNewPO();
				if ($('bididField').value > 0) {
					$('currentPONumber').innerHTML = po;
  					EOL.POEdit.currentPOid = ret.data.purchaseOrderId;
				}

				// inject inside select list
				//
				var newElement = new Element('option');
				newElement.inject($('poSelectList').options[0],'after');
				newElement.setProperty('value',ret.data.purchaseOrderId);

                var text = po;
                if (desc){ text += ' ('+desc+')'; }
				newElement.appendText(text);

				EOL.POEdit.selectIt(ret.data.purchaseOrderId);

				}
	        });

	request.send();
	return false;
};


EOL.namespace('catalog');
EOL.catalog.allowedGroups = [];

EOL.catalog.showAssistant = function() {
	if(catalog_force_closed == true) {
		return;
	}

	document.body.style.overflowX = 'hidden';
	$('jobAssistant').setStyle('left',window.getSize().x + 'px');
	$('jobAssistant').show();
	if(!$('catalogCat-span'))
		new EOL.HtmlSelect('catlogCat');
	var anim = new Fx.Tween('jobAssistant',{duration:1000,transition:Fx.Transitions.Expo.easeOut,onComplete:function(){
		document.body.style.overflowX = 'visible';
	}});
	anim.start('left',10);
	EOL.catalog.hideHints();

	if($('ttlField').value)
		$('ttlField').blur();

	if(version == 'B') {
		on_tip_13 = false;
		closeTitleTooltip();
	}
}

EOL.catalog.hideAssistant = function() {
	document.body.style.overflowX = 'hidden';
	var anim = new Fx.Tween('jobAssistant',{duration:1000,onComplete:function(){
		$('jobAssistant').hide();
		document.body.style.overflowX = 'auto';
	}});
	anim.start('left',window.getSize().x);
	EOL.catalog.showHint('catalogHintOpen');
}

EOL.catalog.showClose = function() {
	var fade = new Fx.Tween('catalogClose',{duration:200});
	fade.start('opacity',1);
}

EOL.catalog.hideClose = function() {
	var fade = new Fx.Tween('catalogClose',{duration:200});
	fade.start('opacity',0);
}

/* browse catalog */
EOL.catalog.showBrowse = function() {
	EOL.catalog.hideClose();
	var anim = new Fx.Tween('jobCatalogBrowse',{duration:500,transition:Fx.Transitions.Expo.easeOut});
	anim.start('left',0);

	browseFilter.change('');

	if($('catIdField').value) {
		$('catlogCat').value = $('catIdField').value;
		$('catlogCat').fireEvent('change');
		if($('catlogCat').value)
			EOL.catalog.loadBrowseItems($('catlogCat').value,$('browseFilter_input').value);
		else
			EOL.catalog.loadBrowseItems();
	}
	else {
		$('catlogCat').value = '';
		$('catlogCat').fireEvent('change');
		EOL.catalog.loadBrowseItems();
	}
}

EOL.catalog.hideBrowse = function() {
	EOL.catalog.showClose();
	var expandEffectBidEdit = new Fx.Tween('jobCatalogBrowse',{duration:500,transition:Fx.Transitions.Expo.easeOut});
	expandEffectBidEdit.start('left',350);
}

EOL.catalog.loadBrowseItems = function(catid,filter) {
	$('jobCatalogBrowseResults').set('html','<img border="0" height="16" width="16" src="/media/images/4.0/ajax-loader-small.gif" style="margin-left: 48%; margin-top: 5px;">');

	var handleSubmitSuccess = function(req) {
		var response = eval('(' + req + ')');
	    if( response.status == 'success') {
	    	$('jobCatalogBrowseResults').set('html','');
			var results = response.data.results;
		    EOL.catalog.displayList($('jobCatalogBrowseResults'),results);
		    if(results.length)
		    	$('catalogSize').set('html','('+results.length+')');
		    else
		    	$('catalogSize').set('html','(0)');
	    }
	};

	var url = '/php/post/main/jobCatalogAHR.php?t=' + getDateTime() + '&action=getBrowseItems';
	if(catid)
		url += '&catid=' + catid;
	if(filter)
		url += '&filter=' + filter;
	var options = {
		url: url,
		method: 'get',
		async: false,
		onSuccess: handleSubmitSuccess,
		onFailure: function() { alert('There was an error processing your request. Please refresh and try again.'); }
	};

	curAsyncReq = new Request(options);
	curAsyncReq.send();
}

/* default view */
EOL.catalog.loadDefault = function() {
	var handleSubmitSuccess = function(req) {
		var response = eval('(' + req + ')');
	    if( response.status == 'success') {

	    	//previous jobs
	    	$('previousResults').set('html','');
	    	if(response.data.previous && response.data.previous.length) {
	    		var items = response.data.previous;
	    		EOL.catalog.displayList($('previousResults'),items,5);

	    		if($('jobAssistant').style.display == 'none') {
	    			EOL.catalog.showAssistant();
	    		}
	    	}

	    	//picks
	    	$('jobCatalogPickResults').set('html','');
	    	if(response.data.picks.length) {
	    		var items = response.data.picks;
	    		if($('previousSection').style.display == 'none') {
	    			var pages = 5;
	    		}
	    		else {
	    			var pages = 3;
	    		}
	    		EOL.catalog.displayList($('jobCatalogPickResults'),items,pages,true);
	    	}


	    }
	}

	$('jobCatalogPickResults').set('html','<img border="0" height="16" width="16" src="/media/images/4.0/ajax-loader-small.gif" style="margin-left: 48%; margin-top: 5px;">');
	$('previousResults').set('html','<img border="0" height="16" width="16" src="/media/images/4.0/ajax-loader-small.gif" style="margin-left: 48%; margin-top: 5px;">');

	var options = {
		url: '/php/post/main/jobCatalogAHR.php?t=' + getDateTime() + '&action=defaultView',
		method: 'get',
		onSuccess: handleSubmitSuccess,
		onFailure: function() { alert('There was an error processing your request. Please refresh and try again.'); }
	};

	curAsyncReq = new Request(options);
	curAsyncReq.send();
}

/* previous jobs */
EOL.catalog.showPrevious = function() {
	EOL.catalog.hideClose();
	var anim = new Fx.Tween('jobCatalogPrevious',{duration:500,transition:Fx.Transitions.Expo.easeOut});
	anim.start('left',0);
	EOL.catalog.loadPrevious();
}

EOL.catalog.hidePrevious = function() {
	EOL.catalog.showClose();
	var expandEffectBidEdit = new Fx.Tween('jobCatalogPrevious',{duration:500,transition:Fx.Transitions.Expo.easeOut});
	expandEffectBidEdit.start('left',350);
}

EOL.catalog.loadPrevious = function() {
	var handleSubmitSuccess = function(req) {
		var response = eval('(' + req + ')');
	    if( response.status == 'success') {
	    	$('jobCatalogPreviousResults').set('html','<img border="0" height="16" width="16" src="/media/images/4.0/ajax-loader-small.gif" style="margin-left: 48%; margin-top: 5px;">');
	    	if(response.data.items.length) {
	    		$('jobCatalogPreviousResults').set('html','');
	    		var items = response.data.items;
	    		EOL.catalog.displayList($('jobCatalogPreviousResults'),items);
	    	}
	    }
	}

	var options = {
		url: '/php/post/main/jobCatalogAHR.php?t=' + getDateTime() + '&action=previous',
		method: 'get',
		onSuccess: handleSubmitSuccess,
		onFailure: function() { alert('There was an error processing your request. Please refresh and try again.'); }
	};

	curAsyncReq = new Request(options);
	curAsyncReq.send();
}

/* autosuggest */
EOL.catalog.currentSuggest = null;
EOL.catalog.currentSuggestReq = null;
EOL.catalog.suggest = function(lookup) {

	if(!lookup || (version=='B' && lookup == $('sampleTitle').value /*'E.g. Logo Design for Mobile Development Co.'*/) || lookup == EOL.catalog.currentSuggest)
		return;
	EOL.catalog.currentSuggest = lookup;

	if(EOL.catalog.currentSuggestReq)
		clearTimeout(EOL.catalog.currentSuggestReq);

	//hide browse if opened
	if($('jobAssistant') && $('jobCatalogBrowse').style.left == '0px') {
		EOL.catalog.hideBrowse();
	}

	var mydelay = function() {
		//if loading a template, dont show suggestions as not to confuse with so many animations going on
		if(EOL.catalog.selectRunning) {
			EOL.catalog.currentSuggest = null;
			return;
		}

		$('suggestionLoading').show();

		var handleSubmitSuccess = function(req) {
			var response = eval('(' + req + ')');
		    if( response.status == 'success') {
		    	if(response.data.suggestions.length) {
		    		if($('jobAssistant').style.display == 'none') {
		    			EOL.catalog.showAssistant();
		    		}

		    		var myfun = function() {
		    			if($('previousSection').style.display != 'none')
			    			$('previousBanner').show();
		    			$('suggestionLoading').hide();
			    		$('toppicksSection').hide();
			    		$('previousSection').hide();
			    		$('suggestionSection').show();

			    		$('jobCatalogSuggestions').set('html','');

			    		var suggestions = response.data.suggestions;

			    		EOL.catalog.displayList($('jobCatalogSuggestions'),suggestions,7,true);
		    		}
		    		myfun.delay(1500);
		    	}
		    	else {
		    		//no suggestions
		    		$('suggestionLoading').hide();
		    	}
		    }
		}

		var options = {
			url: '/php/post/main/jobCatalogAHR.php?t=' + getDateTime() + '&action=suggest&suggest=' + encodeURI(lookup),
			method: 'get',
			onSuccess: handleSubmitSuccess,
			onFailure: function() { alert('There was an error processing your request. Please refresh and try again.'); }
		};

		curAsyncReq = new Request(options);
		curAsyncReq.send();

	}

	EOL.catalog.currentSuggestReq = mydelay.delay(250);
}

/* main functions */
EOL.catalog.existingText = null;
EOL.catalog.selectRunning = null;
EOL.catalog.selectItem = function(cardObj,catalogId,isJob) {
	//if text entered while not in catalog mode, save text
	if(!$('jobCatalogSelectedItem') && $('descField').value) {
		EOL.catalog.existingText = $('descField').value;
	}

	EOL.utility.hideToolTip('catalogTip',0);
	EOL.catalog.noToolTips = true;
	EOL.catalog.selectRunning = true;
	closeAllTips();

	var handleSubmitSuccess = function(req) {
		var response = eval('(' + req + ')');
	    if( response.status == 'success') {
	    	var card = cardObj.clone();

	    	card.inject($('eol-form'));
	    	card.setProperty('id','selectedCatalogItem');
	    	card.setStyle('position','absolute');
	    	card.setStyle('top',(cardObj.getPosition($('eol-form')).y - 2) + 'px');
	    	card.setStyle('left',(cardObj.getPosition($('eol-form')).x - 2) + 'px');
	    	card.setStyle('zIndex','50');

	    	//implode the card
    		var cardMorph = new Fx.Morph(card,{duration:500, onComplete: function() {
	    		card.destroy();
	    		//put card in selected area
	    		EOL.catalog.setSelectedArea(cardObj.clone());

	    		$$('#jobCatalogBrowseResults .catalogCard').each(function(elem) {
		    		var searchid = 'catalogCard' + catalogId;
		    		if($(elem).hasClass('catalogCardSelected')) {
		    			EOL.catalog.unsetSelectedCard($(elem));
		    		}
		    		if(elem.id == searchid) {
		    			EOL.catalog.setSelectedCard($(elem));
		    		}

				});
				$$('#jobCatalogPreviousResults .catalogCard').each(function(elem) {
					var searchid = 'jobCard' + catalogId;
		    		if($(elem).hasClass('catalogCardSelected')) {
		    			EOL.catalog.unsetSelectedCard($(elem));
		    		}

		    		if(elem.id == searchid) {
		    			EOL.catalog.setSelectedCard($(elem));
		    		}
				});

	    		EOL.catalog.noToolTips = false;
	    		EOL.catalog.selectRunning = false;
    		}});
    		var cardMorphIcon = new Fx.Morph(card.getElement('.catalogCardIcon'),{duration:400});
    		cardMorph.start('.cardMorph');
    		cardMorphIcon.start('.iconMorph');

    		//fade out old, fade in new catalog describe
	    	var fadeEffect = new Fx.Tween('descSection',{duration:800, onComplete: function() {
	    		var data = response.data;

	    		if(!isJob) {
	    			$('catalogId').value = catalogId;
	    			$('catalogJobId').value = '';
	    		}
	    		else {
	    			$('catalogId').value = '';
	    			$('catalogJobId').value = catalogId;
	    		}
		    	//set category
	    		$('catIdField').value = data.catid;
	    		$('catIdField').fireEvent('change');
	    		setCatForm();
	    		$('subcatIdField').value = data.subcatid;
	    		$('subcatIdField').fireEvent('change');

	    		//set job type
	    		if(data.jobtype == 'hourly') {
                    autoSelectJobType('hourly');
                    if(version !='B') {
                        $('workTypeField1').checked = false;
                        $('workTypeField2').checked = true;
                    }
					if(isJob) {
						$('hourlyType').value = data.hourly_job_type_id;
						$('hourlyType').fireEvent('change');

						$('durationHours').toggleClass('eol-input-hinttext', !data.durationHours);
						$('durationHours').value = data.durationHours || $('durationHours').defaultValue;

						$('weeklyHours').toggleClass('eol-input-hinttext', !data.hours_per_week);
						$('weeklyHours').value = data.hours_per_week || $('weeklyHours').defaultValue;

						for(i = 0; i< $('wkDurField').options.length; i++){
							if ($('wkDurField').options[i].value == data.duration_weeks){
                                    $('wkDurField').value =  data.duration_weeks;
                                    $('wkDurField').fireEvent('change');
								break;
							}
						}

						for (i = 0; i< $('hourlyRate').options.length; i++){
							if ($('hourlyRate').options[i].value == data.hourly_rate){
									$('hourlyRate').value =  data.hourly_rate;
									$('hourlyRate').fireEvent('change');
								newPost.handleHourlyBudgetRange();
								if(data.hourly_rate_min) {
									$('minHourlyRate').value = data.hourly_rate_min;
									$('minHourlyRate').removeClass('eol-input-hinttext');
								}
								if(data.hourly_rate_max){
									$('maxHourlyRate').value = data.hourly_rate_max;
									$('maxHourlyRate').removeClass('eol-input-hinttext');
								}
								break;
							}
						}
					}

					newPost.handleHourlyTypeChange()
					newPost.showHourlySection();
	    		}
	    		else if(data.jobtype == 'fixed') {
                    autoSelectJobType('fixed');
                    if(version !='B') {
                        $('workTypeField1').checked = true;
                        $('workTypeField2').checked = false;
                    }


	    			if(isJob) {
						for (i = 0; i< $('fixedBudget').options.length; i++){
						 	if ($('fixedBudget').options[i].value == data.budget_code){
                                $('fixedBudget').value =  data.budget_code;
                                $('fixedBudget').fireEvent('change');
								break;
							}
						}
						if(data.budget_min){
							$('minFixedBudget').value = data.budget_min;
							$('minFixedBudget').removeClass('eol-input-hinttext');
		    			}
						if(data.budget_max){
							$('maxFixedBudget').value = data.budget_max;
							$('maxFixedBudget').removeClass('eol-input-hinttext');
		    			}
	    			}

	    			newPost.handleFixedBudgetRange();
					newPost.showFixedFeeSection();
	    		}
				//set hourly/fixed price static tooltip
				handleJobTypeTip();
                if(data.location){
					newPost.showLocNearMeField(data.city, data.state, data.country, data.zip, data.region);
				};
				if(data.country){
					for (i = 0; i< $('ctryCodeField').options.length; i++){
					    if ($('ctryCodeField').options[i].value == data.country){
								if(version =='B') {
									$('ctryCodeField').value =  data.country;
									$('ctryCodeField').fireEvent('change');
								} else {
									$('ctryCodeField').options[i].selected = true;
								}
								break;
						}
					}
				}
				if(data.zip) { $('zipCodeField').value = data.zip};
				if(data.city) { $('cityField').value = data.city};

                // EOL-39410: don't load start date
                $('dateField').value = '';
                $('jobStartDateOption1').checked = true;

				if(data.invitation_only) {
                    $('bidTypeField2').checked = true;
                }
                if(data.seo_index_yn=='N'){
                    $('seoIndexField').checked = false;
                    $('showSeoIndexForm').addClass('displayNone');
                }
				if(data.po_id && $('poSelectList')) {$('poSelectList').value = data.po_id; $('poSelectList').fireEvent('change'); }
			    if( data.udfs ) {
				    for( var i in data.udfs ) {
					    if( $('udfSelectList'+data.udfs[i].udf) ) {
						    $('udfSelectList'+data.udfs[i].udf).value = data.udfs[i].value;
							$('udfSelectList'+data.udfs[i].udf).fireEvent('change');
					    }
				    }
				}
				if(data.expand) {
					showAdvOption();
				}
	    		//reset currently set skills
				if(version == 'B') {
					EOL.postSkills.clearAllSkillsGroups();
				} else if(skilltesting == 'B') {
					EOL.postSkillsB.clearAll();
					EOL.postGroupsB.clearAll();
				} else {
		    		unsetAllTags();
				}

	    		//set new skills
				//alert('version' + version);
	    		if(data.skills && data.skills.length) {
	    			var skills = data.skills;
	    			for(var i=0; i<skills.length; i++) {
                        if(version == 'B') {
                            EOL.postSkills.addSkill(skills[i].name, skills[i].id);
						} else if(skilltesting == 'B') {
							EOL.postSkillsB.addSkill(skills[i].name, skills[i].id);
                        } else if (skills[i].id !== null) {
                            setTag('skills',skills[i].id,skills[i].name);
                        } else {
                            setCustomSkill(skills[i].name);
                        }
	    			}
	    		}

	    		//if allowedGroups exist, don't set the template's groups, set the
	    		//allowed groups instead.
	    		if($('allowedGroups'))
	    		{
	    			EOL.catalog.addAllowedGroups();
	    		} else {
	    			if(data.groups && data.groups.length) {
		    			var groups = data.groups;
		    			for(var i=0; i<groups.length; i++) {
							if(version == 'B') {
								EOL.postSkills.addGroup(groups[i].name, groups[i].id);
							} else if(skilltesting == 'B') {
								EOL.postGroupsB.addGroup(groups[i].name, groups[i].id);
							} else {
			    				setTag('groups',groups[i].id,groups[i].name);
							}
		    			}
		    		}

	    		}


	    		//set description and additional questions
	    		$('descField').value = '';
	    		$('describeCatalog').getChildren().each(function(elem){$(elem).destroy()});
	    		$('describeCatalog').hide();
	    		if(data.job_description) {
	    			$('descField').value = data.job_description;
                    $('descField').onkeyup();
	    		}
	    		if(data.html) {
	    			$('descFieldTitle').hide();
	    			if(data.type == 'Project') {
	    				$('descFieldCatalogProject').show();
	    				$('descFieldCatalogPosition').hide();
	    			}
	    			else {
	    				$('descFieldCatalogPosition').show();
	    				$('descFieldCatalogProject').hide();
	    			}
					if(version=='B') {
		    			$('descField').addClass('catalogDesc');
					}
					if($('descCharLimit')) {
						$('descCharLimit').hide();
					}
	    			$('describeCatalog').set('html',data.html);
	    			$('describeCatalog').show();

	    			//add close tips
	    			$$('#describeCatalog input').each(function(elem) {
	    				$(elem).addEvent('focus',function(){closeAllTips();});
	    			});
	    			$$('#describeCatalog textarea').each(function(elem) {
	    				$(elem).addEvent('focus',function(){closeAllTips();});
	    			});
	    			$$('#describeCatalog select').each(function(elem) {
	    				$(elem).addEvent('focus',function(){closeAllTips();});
	    			});
	    		}
	    		else {
	    			$('descFieldTitle').show();
	    			$('descFieldCatalogProject').hide();
	    			$('descFieldCatalogPosition').hide();
	    			$('descField').removeClass('catalogDesc');
					if($('descCharLimit')) {
						$('descCharLimit').show();
					}
	    		}

		    	var fadeIn = new Fx.Tween('descSection',{duration:200});
		    	fadeIn.start('opacity',1);

		    	EOL.catalog.showHint('catalogHintUndo');
	    	}});
			fadeEffect.start('opacity',0);
	    }
	};

	var url = '/php/post/main/jobCatalogAHR.php?t=' + getDateTime() + '&action=showDescribe&version=' + version;
	if(isJob)
		url += '&jobid=' + catalogId;
	else
		url += '&catalogId=' + catalogId;


	var options = {
		url: url,
		method: 'get',
		onSuccess: handleSubmitSuccess,
		onFailure: function() { alert('There was an error processing your request. Please refresh and try again.'); }
	};

	curAsyncReq = new Request(options);
	curAsyncReq.send();
}

//set selected card section
EOL.catalog.setSelectedArea = function(card) {
	if($('jobCatalogSelected').getChildren().length) {
		$('jobCatalogSelected').getChildren()[0].destroy();
	}
	card.addClass('catalogCardSelected');
	card.removeEvents('click');
	card.set('id','jobCatalogSelectedItem');
	card.setStyle('cursor','default');

	//create reset button
	new Element('div',{
		'class': 'catalogCardReset',
		title: 'Remove',
		html: '<!-- -->',
		events: {
			'click': function() {EOL.catalog.unsetSelected()}
		}
	}).inject(card,'top');

	card.inject($('jobCatalogSelected'));
	$('selectedSection').show();
}

//highlight card as selected
EOL.catalog.setSelectedCard = function(cardElem) {
	cardElem.addClass('catalogCardSelected');
	cardElem.removeEvents('click');
	cardElem.setStyle('cursor','default');
	//create reset button
	new Element('div',{
		'class': 'catalogCardReset',
		title: 'Remove',
		html: '<!-- -->',
		events: {
			'click': function() {EOL.catalog.unsetSelected();}
		}
	}).inject(cardElem,'top');
}

EOL.catalog.unsetSelectedCard = function(cardElem) {
	cardElem.removeClass('catalogCardSelected');
	cardElem.getElement('.catalogCardReset').destroy();
	cardElem.setStyle('cursor','pointer');
	//add back events
	var id = cardElem.getProperty('catalogid');
	if(cardElem.id.indexOf('jobCard') >= 0) {
		cardElem.addEvent('click', function() {EOL.catalog.selectItem(cardElem,id,true);});
	}
	else {
		cardElem.addEvent('click', function() {EOL.catalog.selectItem(cardElem,id);});
	}
}


EOL.catalog.unsetSelected = function() {
	EOL.utility.hideToolTip('catalogTip',0);
	EOL.catalog.noToolTips = true;
	//fade out old, fade in new catalog describe
	var fadeEffect = new Fx.Tween('descSection',{duration:800, onComplete: function() {
		$$('#jobCatalogBrowseResults .catalogCard').each(function(elem) {
    		if($(elem).hasClass('catalogCardSelected')) {
    			EOL.catalog.unsetSelectedCard($(elem));
    		}

		});
		$$('#jobCatalogPreviousResults .catalogCardSelected').each(function(elem) {
    		if($(elem).hasClass('catalogCardSelected')) {
    			EOL.catalog.unsetSelectedCard($(elem));
    		}
		});
		//reset form
		var title = $('ttlField').value;
		$('descPostForm').reset();
		$('ttlField').value = title;
		$('catIdField').fireEvent('change');
		$('subcatIdField').fireEvent('change');
		if(EOL.catalog.existingText)
			$('descField').value = EOL.catalog.existingText;
		else
			$('descField').value = '';

		setTimeout(	function() {$('descField').onblur();
					$('descField').fireEvent('blur');
					$('descField').onkeyup();
					$('descField').fireEvent('keyup'); }
				, 100);

        autoSelectJobType('hourly');
        $('hourlyRate').selectedIndex = 0;
        $('hourlyType').selectedIndex = 0;
        $('hourlyRate-span').set('html', $('hourlyRate')[0].get('html'));
        $('weeklyHours').value = '';
        if(version =='B') {
            $('hourlyRate').fireEvent('change');
        } else {
            $('hourlyOption').style.display = 'none';
            $('workTypeField1').checked = false;
            $('workTypeField2').checked = true;
            handleHintText('minHourlyRate', 'Min');
            handleHintText('maxHourlyRate', 'Max');
            $('hourlyRate').selectedIndex = 0;
            newPost.handleHourlyBudgetRange();
        }
        $('wkDurField').selectedIndex = 0;
        $('wkDurField').fireEvent('change');
		//set hourly/fixed price static tooltip
		handleJobTypeTip();

		newPost.showHourlySection();
		newPost.handleHourlyTypeChange();
		newPost.showLocAnywhereField();
		$('selectedSection').hide();
        if($('jobCatalogSelected').getChildren().length)
		    $('jobCatalogSelected').getChildren()[0].destroy();
		$('describeCatalog').hide();
		$('catalogId').value = '';
		$('catalogJobId').value = '';
		$('descField').removeClass('catalogDesc');
		if($('descCharLimit')) {
			$('descCharLimit').show();
		}
		$('descFieldTitle').show();
		$('descFieldCatalogProject').hide();
		$('descFieldCatalogPosition').hide();

        // Clear Location
        $('locField1').checked = '';
        newPost.showRegionCountryField();
		// Clear PO
        if($('poSelectList')) {$('poSelectList').value = 0; $('poSelectList').fireEvent('change');}
        if($('poSelectList-span')) {$('poSelectList-span').set('text', '- Select PO -'); }

		// Clear UDFs
		if($('udfField')) {
			Array.each($('udfField').getElements('select.udfSelectList'), function(udf, index) {
				udf.value = 0;
				udf.fireEvent('change');
			});
		}

		if(version=='B') {
			EOL.postSkills.clearAllSkillsGroups();
		} else if(skilltesting == 'B') {
			EOL.postSkillsB.clearAll();
			EOL.postGroupsB.clearAll();
		} else {
			unsetAllTags();
		}

		EOL.catalog.addAllowedGroups();

		EOL.catalog.noToolTips = false;

		EOL.catalog.hideHints();

    	var fadeIn = new Fx.Tween('descSection',{duration:200});
    	fadeIn.start('opacity',1);
	}});
	fadeEffect.start('opacity',0);
}

EOL.catalog.noToolTips = null;
EOL.catalog.cardTimeout = null;
EOL.catalog.createCatalogCard = function(data,selected) {
	var id = data.id;
	var title = data.title;
	var type = data.type;
	var description = data.description;

	if(type == 'Job') {
		var cardid = 'jobCard' + id;
		var card = new Element('div',{
			'class': (selected) ? 'catalogCard catalogCardSelected' : 'catalogCard',
			id: cardid,
			catalogid: id,
			events: {
				'click': (selected) ? function() {} : function() {EOL.catalog.unsetSelected();EOL.catalog.selectItem(this,id,true);},
				'mouseover': function() {
					var myfun = function() {
						if(EOL.catalog.noToolTips)
							return;
						$('catalogTip-title').set('text',title);
						$('catalogTip-content').set('text',description);
						EOL.utility.showToolTip('catalogTip',this,-315,68);
					}.bind(this);
					EOL.catalog.cardTimeout = myfun.delay(1000);
				},
				'mouseout': function() {
					clearTimeout(EOL.catalog.cardTimeout);
					EOL.utility.hideToolTip('catalogTip',0);
				}
			}
		});
	}
	else {
		var cardid = 'catalogCard' + id;
		var card = new Element('div',{
			'class': (selected) ? 'catalogCard catalogCardSelected' : 'catalogCard',
			id: cardid,
			catalogid: id,
			events: {
				'click': (selected) ? function() {} : function() {EOL.catalog.selectItem(this,id);},
				'mouseover': function() {
					var myfun = function() {
						if(EOL.catalog.noToolTips)
							return;
						$('catalogTip-title').set('html',title);
						$('catalogTip-content').set('html',description);
						EOL.utility.showToolTip('catalogTip',this,-315,68);
					}.bind(this);
					EOL.catalog.cardTimeout = myfun.delay(1000);
				},
				'mouseout': function() {
					clearTimeout(EOL.catalog.cardTimeout);
					EOL.utility.hideToolTip('catalogTip',0);
				}
			}
		});
	}

	var icontype = '';
	var typedisplay = '';
	if(type == 'Position') {
		icontype = 'catalogIconPosition';
		typedisplay = type;
	}
	else if(type == 'Project') {
		icontype = 'catalogIconProject';
		typedisplay = type;
	}
	else if(type == 'Job') {
		icontype = 'catalogIconJob';
		typedisplay = type + "&nbsp;&nbsp;|&nbsp;&nbsp;Posted " + data.post_date;
	}

	new Element('div',{
		'class': 'catalogCardIcon ' + icontype,
		html: '<!-- -->'
	}).inject(card);

	var temp = new Element('div',{
		'class': 'catalogCardInfo'
	});

	new Element('div',{
		'class': 'catalogCardTitle',
		text: title.truncate(30,'...')
	}).inject(temp);

	new Element('div',{
		'class': 'catalogCardDetails',
		html: typedisplay
	}).inject(temp);

	temp.inject(card);

	new Element('div',{
		'class': 'clear'
	}).inject(card);

	if(selected) {
		//create reset button
		new Element('div',{
			'class': 'catalogCardReset',
			title: 'Remove',
			html: '<!-- -->',
			events: {
				'click': function() {EOL.catalog.unsetSelected();}
			}
		}).inject(card,'top');
		card.setStyle('cursor','default');
	}

	return card;
}

EOL.catalog.displayList = function(targetElem,catalogItems,pageLimit,browseAll) {
	if(!pageLimit)
		pageLimit = 8;

	targetElem.empty();

	var numPages = Math.ceil(catalogItems.length/pageLimit);
	targetElem.addClass('cardContainer');

	var pageContainer = new Element('div',{
		'class': 'cardPage-c',
		style: 'left:0px'
	}).inject(targetElem);

	var pageElem = new Element('div',{
		'class': 'cardPage'
	}).inject(pageContainer);

	var mychain = new Chain();
	var i = 0;
	var j = 0;
    var extraHeight = 0;
	catalogItems.each(function(elem) {

		if(j >= pageLimit) {
			//new page
			pageElem = new Element('div',{
				'class': 'cardPage'
			}).inject(pageContainer);
			j=0;
		}

		//only fade in the first page result
		if(numPages <= 1 || i < pageLimit) {
			var newpage = pageElem;
			mychain.chain(function() {
				var selected = (targetElem.id != 'jobCatalogSuggestions' && targetElem.id != 'jobCatalogPickResults' && targetElem.id != 'previousResults'
									&& $('jobCatalogSelectedItem') && elem.id == $('jobCatalogSelectedItem').getProperty('catalogid')) ? true : false;
				var card = EOL.catalog.createCatalogCard(elem,selected);
				card.setStyle('opacity',0);
				card.inject(newpage);
				new Fx.Tween(card,{duration:800}).start('opacity',1);
				return true;
			});
		}
		else {
			var selected = (targetElem.id != 'jobCatalogSuggestions' && targetElem.id != 'jobCatalogPickResults' && targetElem.id != 'previousResults'
									&& $('jobCatalogSelectedItem') && elem.id == $('jobCatalogSelectedItem').getProperty('catalogid')) ? true : false;
			var card = EOL.catalog.createCatalogCard(elem,selected);
			card.inject(pageElem);
		}

		i++;
		j++;

	});

	//add pagination
	if(numPages > 1) {
		var pagination = new Element('div',{
			'class': 'cardPagination-c'
		}).inject(targetElem);
		for(i=1; i<=numPages; i++) {
			var pageClass = (i==1) ? 'pageOn' : '';
			new Element('div',{
				'class': 'cardPagination ' + pageClass,
				html: i,
				events: {
					'click': (function(page) { return function() {EOL.catalog.changePage(targetElem,page);} })(i)
				}
			}).inject(pagination);
		}
	}

	if(numPages > 1 || browseAll) {
		if(browseAll) {
			if(!pagination) {
				var pagination = new Element('div',{
					'class': 'cardPagination-c'
				}).inject(targetElem);
			}
			new Element('div',{
				'class': 'cardPagination cardBrowseLink',
				html: 'browse all &raquo;',
				events: {
					'click': function() {EOL.catalog.showBrowse()}
				}
			}).inject(pagination);
		}

		var width = 0;
		if(numPages > 1)
			width += (numPages * 21);
		if(browseAll)
			width += 85;


		//set width so it can be centered
        if(width > 300) {
           width = 300;
            extraHeight = 25;
        }
		pagination.setStyle('width',width + 'px');
        pagination.setStyle('marginLeft',(-1 * (width/2)) + 'px');
	}

	//expand height of the target area
	var maxItems = (catalogItems.length > pageLimit) ? pageLimit : catalogItems.length;
	var height = ((numPages > 1 || browseAll) ? ((maxItems * 51) + 25) : (maxItems * 51)) + extraHeight ;
	targetElem.setStyle('height', height + 'px');

	var runChain = function() {
		if(!mychain.callChain()) {
			runChain = clearInterval(timer);
		}

	};
	var timer = runChain.periodical(200);
}

EOL.catalog.changePage = function(targetElem,pageNum) {
	var paginationElem = targetElem.getElement('.cardPagination-c');
	var pagesElem = targetElem.getElement('.cardPage-c');
	var pagewidth = pagesElem.getElement('.cardPage').getSize().x;
	var offset = -1 * pagewidth * (pageNum - 1);
	new Fx.Tween(pagesElem,{duration:500}).start('left',offset);
	paginationElem.getElements('.cardPagination').each(function(elem,index){
		if($(elem).hasClass('pageOn'))
			$(elem).removeClass('pageOn');

		if((index+1) == pageNum) {
			$(elem).addClass('pageOn');
		}
	});
}

EOL.catalog.handleCatalogSelect = function(elem) {
	var elem = $(elem);
	if(elem.value == 'other') {
		$(elem.get('name') + '_OTHER').setStyle('display','');
	}
	else {
		$(elem.get('name') + '_OTHER').setStyle('display','none');
	}
}

EOL.catalog.hideHints = function() {
	$('catalogHint').hide();
	$$("#catalogHint div").each(function(elem) {
		$(elem).hide();
	});
}

EOL.catalog.showHint = function(id) {
	EOL.catalog.hideHints();
	$(id).show();
	$('catalogHint').show();
}

EOL.catalog.addAllowedGroups = function() {

	var isPTC = $('isPTC') ? $('isPTC').get('value') : false;
	if (isPTC) {
		//do nothing ... for PTC clients we don't change the groups on template adds.
	} else {

		if (EOL.catalog.allowedGroups.length > 0){
			Array.each(EOL.catalog.allowedGroups, function(group,index){
				if (version == 'B'){
					EOL.postSkills.addGroup(group['NAME'], group['ID']);
				} else if(skilltesting == 'B') {
					EOL.postGroupsB.addGroup(group['NAME'], group['ID']);
				} else {
					setTag('groups', group['ID'], group['NAME']);
				}
			});

			if (version == 'B') {
				length = EOL.postSkills.skillList.index.length;
				if (length == 1) {
					$$('.textboxlist-bit-box-deletebutton').each(function(ele){
						ele.hide();
		           	});
				}
			} else if(skilltesting == 'B') {
				length = EOL.postGroupsB.groupList.index.length;
	            if (length == 1) {
	                $('groups-interaction').getElements('.textboxlist-bit-box-deletebutton').each(function(ele){
	                    ele.hide();
	                });
	            }
			} else {
				length = $("selectedGroupCount").get('value');
				if (length == 1){
					$$('.removeGroup').each(function(ele){
						ele.hide();
					});
				}
			}
		}
	}
}

var browseFilter = null;
window.addEvent('domready',function(){
	if($('jobAssistant')) {
		var myfun = function(value) {
			EOL.catalog.loadBrowseItems($('catlogCat').value,value);
		}
		browseFilter = new EOL.select('browseFilter', myfun);

		//set selected card if preset
		if($('catalogId').value || $('catalogJobId').value) {
			var handleSubmitSuccess = function(req) {
				var response = eval('(' + req + ')');
			    if( response.status == 'success') {
			    	if($('jobAssistant').style.display == 'none') {
		    			EOL.catalog.showAssistant();
		    		}

			    	//put card in selected area
			    	var card = EOL.catalog.createCatalogCard(response.data);
			    	EOL.catalog.setSelectedArea(card);

			    	EOL.catalog.loadDefault();

			    	EOL.catalog.showHint('catalogHintUndo');
			    }
			}

			if($('catalogId').value) {
				var url = '/php/post/main/jobCatalogAHR.php?t=' + getDateTime() + '&action=getItem&catalogId=' + encodeURI($('catalogId').value);
			} else if($('catalogJobId').value) {
				var url = '/php/post/main/jobCatalogAHR.php?t=' + getDateTime() + '&action=getItem&jobid=' + encodeURI($('catalogJobId').value);
			}

			var options = {
				url: url,
				method: 'get',
				onSuccess: handleSubmitSuccess,
				onFailure: function() { alert('There was an error processing your request. Please refresh and try again.'); }
			};

			curAsyncReq = new Request(options);
			curAsyncReq.send();
		}
		else if(!$('repostJobIdField') || !$('repostJobIdField').value) {
			EOL.catalog.loadDefault();
		}
	}

	if ($('allowedGroups')){
		EOL.catalog.allowedGroups = JSON.decode($('allowedGroups').get('value'));
	}
});
