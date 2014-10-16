

/*
---
name: Locale.en-US.DatePicker
description: English Language File for DatePicker
authors: Arian Stolwijk
requires: [More/Locale]
provides: Locale.en-US.DatePicker
...
*/


Locale.define('en-US', 'DatePicker', {
	select_a_time: 'Select a time',
	use_mouse_wheel: 'Use the mouse wheel to quickly change value',
	time_confirm_button: 'OK'
});


/*
---
name: Picker
description: Creates a Picker, which can be used for anything
authors: Arian Stolwijk
requires: [Core/Element.Dimensions, Core/Fx.Tween, Core/Fx.Transitions]
provides: Picker
...
*/


var Picker = new Class({

	Implements: [Options, Events],

	options: {/*
		onShow: function(){},
		onOpen: function(){},
		onHide: function(){},
		onClose: function(){},*/

		pickerClass: 'datepicker',
		inject: null,
		animationDuration: 400,
		useFadeInOut: true,
		positionOffset: {x: 0, y: 0},
		pickerPosition: 'bottom',
		draggable: true,
		showOnInit: true
	},

	initialize: function(options){
		this.setOptions(options);
		this.constructPicker();
		if (this.options.showOnInit) this.show();
	},

	constructPicker: function(){
		var options = this.options;

		var picker = this.picker = new Element('div', {
			'class': options.pickerClass,
			styles: {
				left: 0,
				top: 0,
				display: 'none',
				opacity: 0
			}
		}).inject(options.inject || document.body);

		if (options.useFadeInOut){
			picker.set('tween', {
				duration: options.animationDuration,
				link: 'cancel'
			});
		}

		// Build the header
		var header = this.header = new Element('div.header').inject(picker);

		this.closeButton = new Element('div.closeButton[text=x]')
			.addEvent('click', this.close.pass(false, this))
			.inject(header);

		var title = this.title = new Element('div.title').inject(header);
		this.titleText = new Element('div.titleText').inject(title);

		// Build the body of the picker
		var body = this.body = new Element('div.body').inject(picker);

		// oldContents and newContents are used to slide from the old content to a new one.
		var slider = this.slider = new Element('div.slider', {
			styles: {
				position: 'absolute',
				top: 0,
				left: 0
			}
		}).set('tween', {
			duration: options.animationDuration,
			transition: Fx.Transitions.Quad.easeInOut
		}).inject(body);

		this.oldContents = new Element('div', {
			styles: {
				position: 'absolute',
				top: 0
			}
		}).inject(slider);

		this.newContents = new Element('div', {
			styles: {
				position: 'absolute',
				top: 0,
				left: 0
			}
		}).inject(slider);

		// IFrameShim for select fields in IE
		var shim = this.shim = window['IframeShim'] ? new IframeShim(picker) : null;

		// Dragging
		if (options.draggable && typeOf(picker.makeDraggable) == 'function'){
			this.dragger = picker.makeDraggable(shim ? {
				onDrag: shim.position.bind(shim)
			} : null);
			picker.setStyle('cursor', 'move');
		}

		this.addEvent('open', function(){
			picker.setStyle('display', 'block');
			if (shim) shim.show();
		}, true);

		this.addEvent('hide', function(){
			picker.setStyle('display', 'none');
			if (shim) shim.hide();
		}, true);

	},

	open: function(noFx){
		if (this.opened == true) return this;
		this.opened = true;
		this.fireEvent('open');
		if (this.options.useFadeInOut && !noFx){
			this.picker.fade('in').get('tween').chain(function(){
				this.fireEvent('show');
			}.bind(this));
		} else {
			this.picker.setStyle('opacity', 1);
			this.fireEvent('show');
		}
		return this;
	},

	show: function(){
		return this.open(true);
	},

	close: function(noFx){
		if (this.opened == false) return this;
		this.opened = false;
		this.fireEvent('close');
		if (this.options.useFadeInOut && !noFx){
			this.picker.fade('out').get('tween').chain(function(){
				this.fireEvent('hide');
			}.bind(this));
		} else {
			this.picker.setStyle('opacity', 0);
			this.fireEvent('hide');
		}
		return this;
	},

	hide: function(){
		return this.close(true);
	},

	toggle: function(){
		return this[this.opened == true ? 'close' : 'open']();
	},

	destroy: function(){
		this.picker.destroy();
		if (this.shim) this.shim.destroy();
	},

	position: function(x, y){
		var offset = this.options.positionOffset,
			scroll = document.getScroll(),
			size = document.getSize(),
			pickersize = this.picker.getSize();

		if (typeOf(x) == 'element'){
			var element = x,
				where = y || this.options.pickerPosition;

			var elementCoords = element.getCoordinates();

			x = (where == 'left') ? elementCoords.left - pickersize.x
				: (where == 'bottom' || where == 'top') ? elementCoords.left
				: elementCoords.right
			y = (where == 'bottom') ? elementCoords.bottom
				: (where == 'top') ? elementCoords.top - pickersize.y
				: elementCoords.top;
		}

		x += offset.x * ((where && where == 'left') ? -1 : 1);
		y += offset.y * ((where && where == 'top') ? -1: 1);

		if ((x + pickersize.x) > (size.x + scroll.x)) x = (size.x + scroll.x) - pickersize.x;
		if ((y + pickersize.y) > (size.y + scroll.y)) y = (size.y + scroll.y) - pickersize.y;
		if (x < 0) x = 0;
		if (y < 0) y = 0;

		this.picker.setStyles({
			left: x,
			top: y
		});
		if (this.shim) this.shim.position();
		return this;
	},

	setBodySize: function(){
		var bodysize = this.bodysize = this.body.getSize();
		this.slider.setStyles({
			width: 2 * bodysize.x,
			height: bodysize.y
		});
		this.oldContents.setStyles({
			left: bodysize.x,
			width: bodysize.x,
			height: bodysize.y
		});
		this.newContents.setStyles({
			width: bodysize.x,
			height: bodysize.y
		});
	},

	setContent: function(){
		var content = Array.from(arguments), fx;

		if (['right', 'left', 'fade'].contains(content[1])) fx = content[1];
		if (content.length == 1 || fx) content = content[0];

		// swap contents so we can fill the newContents again and animate
		var old = this.oldContents;
		this.oldContents = this.newContents;
		this.newContents = old;
		this.newContents.empty();

		var type = typeOf(content);
		if (['string', 'number'].contains(type)) this.newContents.set('text', content);
		else this.newContents.adopt(content);

		this.setBodySize();

		if (fx){
			this.fx(fx);
		} else {
			this.slider.setStyle('left', 0);
			this.oldContents.setStyles({left: this.bodysize.x, opacity: 0});
			this.newContents.setStyles({left: 0, opacity: 1});
		}
		return this;
	},

	fx: function(fx){
		var oldContents = this.oldContents,
			newContents = this.newContents,
			slider = this.slider,
			bodysize = this.bodysize;
		if (fx == 'right'){
			oldContents.setStyles({left: 0, opacity: 1});
			newContents.setStyles({left: bodysize.x, opacity: 1});
			slider.setStyle('left', 0).tween('left', 0, -bodysize.x);
		} else if (fx == 'left'){
			oldContents.setStyles({left: bodysize.x, opacity: 1});
			newContents.setStyles({left: 0, opacity: 1});
			slider.setStyle('left', -bodysize.x).tween('left', -bodysize.x, 0);
		} else if (fx == 'fade'){
			slider.setStyle('left', 0);
			oldContents.setStyle('left', 0).set('tween', {
				duration: this.options.animationDuration / 2
			}).tween('opacity', 1, 0).get('tween').chain(function(){
				oldContents.setStyle('left', bodysize.x);
			});
			newContents.setStyles({opacity: 0, left: 0}).set('tween', {
				duration: this.options.animationDuration
			}).tween('opacity', 0, 1);
		}
	},

	toElement: function(){
		return this.picker;
	},

	setTitle: function(text){
		this.titleText.set('text', text);
		return this;
	},

	setTitleEvent: function(fn){
		this.titleText.removeEvents('click');
		if (fn) this.titleText.addEvent('click', fn);
		this.titleText.setStyle('cursor', fn ? 'pointer' : '');
		return this;
	}

});


/*
---
name: Picker.Attach
description: Adds attach and detach methods to the Picker, to attach it to element events
authors: Arian Stolwijk
requires: [Picker, Core/Element.Event]
provides: Picker.Attach
...
*/


Picker.Attach = new Class({

	Extends: Picker,

	options: {/*
		onAttachedEvent: function(event){},

		toggleElements: null, // deprecated
		toggle: null,*/
		showOnInit: false
	},

	initialize: function(attachTo, options){
		this.parent(options);

		this.attachedEvents = [];
		this.attachedElements = [];
		this.toggles = [];
		this.inputs = [];

		var documentEvent = function(event){
			if (this.attachedElements.contains(event.target)) return null;
			this.close();
		}.bind(this);
		var document = this.picker.getDocument().addEvent('click', documentEvent);

		var preventPickerClick = function(event){
			event.stopPropagation();
			return false;
		};
		this.picker.addEvent('click', preventPickerClick);

		// Support for deprecated toggleElements
		if (this.options.toggleElements) this.options.toggle = document.getElements(this.options.toggleElements);

		this.attach(attachTo, this.options.toggle);
	},

	attach: function(attachTo, toggle){
		if (typeOf(attachTo) == 'string') attachTo = document.id(attachTo);
		if (typeOf(toggle) == 'string') toggle = document.id(toggle);

		var elements = Array.from(attachTo),
			toggles = Array.from(toggle),
			allElements = [].append(elements).combine(toggles),
			self = this;

		var eventWrapper = function(fn, element){
			return function(event){
				if (event.type == 'keydown' && ['tab', 'esc'].contains(event.key) == false) return false;
				if (event.target.get('tag') == 'a') event.stop();
				self.fireEvent('attachedEvent', [event, element]);
				self.position(element);
				fn();
			};
		};

		allElements.each(function(element, i){

			// The events are already attached!
			if (self.attachedElements.contains(element)) return null;

			var tag = element.get('tag');

			var events = {};
			if (tag == 'input'){
				events = {
					focus: eventWrapper(self.open.bind(self), element),
					keydown: eventWrapper(self.close.bind(self), element),
					click: eventWrapper(self.open.bind(self), element)
				};
				self.inputs.push(element);
			} else {
				if (toggles.contains(element)){
					self.toggles.push(element);
					events.click = eventWrapper(self.toggle.bind(self), element);
				} else {
					events.click = eventWrapper(self.open.bind(self), element);
				}
			}
			element.addEvents(events);
			self.attachedElements.push(element);
			self.attachedEvents.push(events);
		});
		return this;
	},

	detach: function(attachTo, toggle){
		if (typeOf(attachTo) == 'string') attachTo = document.id(attachTo);
		if (typeOf(toggle) == 'string') toggle = document.id(toggle);

		var elements = Array.from(attachTo),
			toggles = Array.from(toggle),
			allElements = [].append(elements).combine(toggles),
			self = this;

		if (!allElements.length) allElements = self.attachedElements;

		allElements.each(function(element){
			var i = self.attachedElements.indexOf(element);
			if (i < 0) return null;

			var events = self.attachedEvents[i];
			element.removeEvents(events);
			delete self.attachedEvents[i];
			delete self.attachedElements[i];

			var toggleIndex = self.toggles.indexOf(element);
			if (toggleIndex != -1) delete self.toggles[toggleIndex];

			var inputIndex = self.inputs.indexOf(element);
			if (toggleIndex != -1) delete self.inputs[inputIndex];

		});
		return this;
	},

	destroy: function(){
		this.detach();
		this.parent();
	}

});


/*
---
name: Picker.Date
description: Creates a DatePicker, can be used for picking years/months/days and time, or all of them
authors: Arian Stolwijk
requires: [Picker, Picker.Attach, Locale.en-US.DatePicker, More/Locale, More/Date]
provides: Picker.Date
...
*/


(function(){

this.DatePicker = Picker.Date = new Class({

	Extends: Picker.Attach,

	options: {/*
		onSelect: function(date){},

		minDate: new Date('3/4/2010'), // Date object or a string
		maxDate: new Date('3/4/2011'), // same as minDate
		availableDates: {}, //
		format: null,*/

		timePicker: false,
		timePickerOnly: false, // deprecated, use onlyView = 'time'
		timeWheelStep: 1, // 10,15,20,30

		yearPicker: true,
		yearsPerPage: 20,

		startDay: 1, // Sunday (0) through Saturday (6) - be aware that this may affect your layout, since the days on the right might have a different margin

		startView: 'days', // allowed values: {time, days, months, years}
		pickOnly: false, // 'years', 'months', 'days', 'time'
		canAlwaysGoUp: ['months', 'days'],

		// if you like to use your own translations
		months_abbr: null,
		days_abbr: null,
		years_title: function(date, options){
			var year = date.get('year');
			return year + '-' + (year + options.yearsPerPage - 1);
		},
		months_title: function(date, options){
			return date.get('year');
		},
		days_title: function(date, options){
			return date.format('%b %Y');
		},
		time_title: function(date, options){
			return (options.pickOnly == 'time') ? Locale.get('DatePicker.select_a_time') : date.format('%d %B, %Y');
		}
	},

	initialize: function(attachTo, options){
		this.parent(attachTo, options);

		this.setOptions(options);
		var options = this.options;

		// If we only want to use one picker / backwards compatibility
		['year', 'month', 'day', 'time'].some(function(what){
			if (options[what + 'PickerOnly']) return options.pickOnly = what;
		});
		if (options.pickOnly){
			options[options.pickOnly + 'Picker'] = true;
			options.startView = options.pickOnly;
		}

		// backward compatibility for startView
		var newViews = ['days', 'months', 'years'];
		['month', 'year', 'decades'].some(function(what, i){
			if (options.startView == what){
				options.startView = newViews[i];
				return true;
			}
		});

		options.canAlwaysGoUp = options.canAlwaysGoUp ? Array.from(options.canAlwaysGoUp) : [];

		// Set the min and max dates as Date objects
		if (options.minDate){
			if (!(options.minDate instanceof Date)) options.minDate = Date.parse(options.minDate);
			options.minDate.clearTime();
		}
		if (options.maxDate){
			if (!(options.maxDate instanceof Date)) options.maxDate = Date.parse(options.maxDate);
			options.maxDate.clearTime();
		}

		if (!options.format){
			options.format = (options.pickOnly != 'time') ? Locale.get('Date.shortDate') : '';
			if (options.timePicker) options.format = (options.format) + (options.format ? ' ' : '') + Locale.get('Date.shortTime');
		}

		// This is where we store the selected date
		this.date = limitDate(new Date(), options.minDate, options.maxDate);

		// Some link or input has fired an event!
		this.addEvent('attachedEvent', function(event, element){
			var tag = element.get('tag'), input;
			if (tag == 'input'){
				input = element;
			} else {
				var index = this.toggles.indexOf(element);
				if (this.inputs[index]) input = this.inputs[index];
			}
			this.date = new Date()
			if (input){
				var date = Date.parse(input.get('value'));
				if (date == null || !date.isValid()){
					var storeDate = input.retrieve('datepicker:value');
					if (storeDate) date = Date.parse(storeDate);
				}
				if (date != null && date.isValid()) this.date = date;
			}
			this.input = input;
		}.bind(this), true);


		// Start rendering the default view.
		this.currentView = options.startView;
		this.addEvent('open', function(){
			var view = this.currentView,
				cap = view.capitalize();
			if (this['render' + cap]){
				this['render' + cap](this.date.clone());
				this.currentView = view;
			}
		}.bind(this));

	},

	// Control the previous and next elements

	constructPicker: function(){
		this.parent();

		this.previous = new Element('div.previous[html=&#171;]').inject(this.header);
		this.next = new Element('div.next[html=&#187;]').inject(this.header);
	},

	hidePrevious: function($next, $show){
		this[$next ? 'next' : 'previous'].setStyle('display', $show ? 'block' : 'none');
		return this;
	},

	showPrevious: function($next){
		return this.hidePrevious($next, true);
	},

	setPreviousEvent: function(fn, $next){
		this[$next ? 'next' : 'previous'].removeEvents('click');
		if (fn) this[$next ? 'next' : 'previous'].addEvent('click', fn);
		return this;
	},

	hideNext: function(){
		return this.hidePrevious(true);
	},

	showNext: function(){
		return this.showPrevious(true);
	},

	setNextEvent: function(fn){
		return this.setPreviousEvent(fn, true);
	},

	// Render the Pickers

	renderYears: function(date, fx){

		var options = this.options;

		// start neatly at interval (eg. 1980 instead of 1987)
		while (date.get('year') % options.yearsPerPage > 0) date.decrement('year', 1);

		this.setTitle(options.years_title(date, options));

		this.setContent(renderers.years(
			options,
			date.clone(),
			this.date.clone(),
			function(date){
				if (options.pickOnly == 'years') this.select(date);
				else this.renderMonths(date, 'fade');
			}.bind(this)
		), fx);

		// Set limits
		var limitLeft = (options.minDate && date.get('year') <= options.minDate.get('year')),
			limitRight = (options.maxDate && (date.get('year') + options.yearsPerPage) >= options.maxDate.get('year'));
		this[(limitLeft ? 'hide' : 'show') + 'Previous']();
		this[(limitRight ? 'hide' : 'show') + 'Next']();

		this.setPreviousEvent(function(){
			this.renderYears(date.decrement('year', options.yearsPerPage), 'left');
		}.bind(this));

		this.setNextEvent(function(){
			this.renderYears(date.increment('year', options.yearsPerPage), 'right');
		}.bind(this));

		// We can't go up!
		this.setTitleEvent(null);
	},

	renderMonths: function(date, fx){
		var options = this.options;
		this.setTitle(options.months_title(date, options));

		this.setContent(renderers.months(
			options,
			date.clone(),
			this.date.clone(),
			function(date){
				if (options.pickOnly == 'months') this.select(date);
				else this.renderDays(date, 'fade');
			}.bind(this)
		), fx);

		// Set limits
		var year = date.get('year'),
			limitLeft = (options.minDate && year <= options.minDate.get('year')),
			limitRight = (options.maxDate && year >= options.maxDate.get('year'));
		this[(limitLeft ? 'hide' : 'show') + 'Previous']();
		this[(limitRight ? 'hide' : 'show') + 'Next']();

		this.setPreviousEvent(function(){
			this.renderMonths(date.decrement('year', 1), 'left');
		}.bind(this));

		this.setNextEvent(function(){
			this.renderMonths(date.increment('year', 1), 'right');
		}.bind(this));

		var canGoUp = options.yearPicker && (options.pickOnly != 'months' || options.canAlwaysGoUp.contains('months'));
		var titleEvent = (canGoUp) ? function(){
			this.renderYears(date, 'fade');
		}.bind(this) : null;
		this.setTitleEvent(titleEvent);
	},

	renderDays: function(date, fx){
		var options = this.options;
		this.setTitle(options.days_title(date, options));

		this.setContent(renderers.days(
			options,
			date.clone(),
			this.date.clone(),
			function(date){
				if (options.pickOnly == 'days' || !options.timePicker) this.select(date)
				else this.renderTime(date, 'fade');
			}.bind(this)
		), fx);

		var yearmonth = date.format('%Y%m').toInt(),
			limitLeft = (options.minDate && yearmonth <= options.minDate.format('%Y%m')),
			limitRight = (options.maxDate && yearmonth >= options.maxDate.format('%Y%m'));
		this[(limitLeft ? 'hide' : 'show') + 'Previous']();
		this[(limitRight ? 'hide' : 'show') + 'Next']();

		this.setPreviousEvent(function(){
			this.renderDays(date.decrement('month', 1), 'left');
		}.bind(this));

		this.setNextEvent(function(){
			this.renderDays(date.increment('month', 1), 'right');
		}.bind(this));

		var canGoUp = options.pickOnly != 'days' || options.canAlwaysGoUp.contains('days');
		var titleEvent = (canGoUp) ? function(){
			this.renderMonths(date, 'fade');
		}.bind(this) : null;
		this.setTitleEvent(titleEvent);
	},

	renderTime: function(date, fx){
		var options = this.options;
		this.setTitle(options.time_title(date, options));

		this.setContent(renderers.time(
			options,
			date.clone(),
			this.date.clone(),
			function(date){
				this.select(date);
			}.bind(this)
		), fx);

		// Hide « and » buttons
		this.hidePrevious()
			.hideNext()
			.setPreviousEvent(null)
			.setNextEvent(null);

		var canGoUp = options.pickOnly != 'time' || options.canAlwaysGoUp.contains('time');
		var titleEvent = (canGoUp) ? function(){
			this.renderDays(date, 'fade');
		}.bind(this) : null;
		this.setTitleEvent(titleEvent);
	},

	select: function(date){
		this.date = date;
		if (this.input){
			this.input.set('value', date.format(this.options.format))
				.store('datepicker:value', date.strftime())
		}
		this.fireEvent('select', date);
		this.close();
	}

});


// Renderers only output elements and calculate the limits!

var renderers = {

	years: function(options, date, currentDate, fn){
		var limit = {left: false, right: false},
			container = new Element('div.years'),
			today = new Date(),
			year, element, classes;

		for (var i = 0; i < options.yearsPerPage; i++){
			year = date.get('year');

			classes = '.year.year' + i;
			if (year == today.get('year')) classes += '.today';
			if (year == currentDate.get('year')) classes += '.selected';
			element = new Element('div' + classes, {text: year}).inject(container);

			if (isUnavailable('year', date, options)) element.addClass('unavailable');
			else element.addEvent('click', fn.pass(date.clone()));

			date.increment('year', 1);
		}

		return container;
	},

	months: function(options, date, currentDate, fn){
		var today = new Date(),
			month = today.get('month'),
			limit = {left: false, right: false},
			thisyear = (date.get('year') == today.get('year')),
			selectedyear = (date.get('year') == currentDate.get('year')),
			container = new Element('div.months'),
			months = options.months_abbr || Locale.get('Date.months_abbr'),
			elelement, classes;

		date.set('month', 0);
		if (options.minDate){
			date.decrement('month', 1);
			date.set('date', date.get('lastdayofmonth'));
			date.increment('month', 1);
		}

		date.set('date', date.get('lastdayofmonth'));

		for (var i = 0; i <= 11; i++){

			classes = '.month.month' + (i + 1);
			if (i == month && thisyear) classes += '.today';
			if (i == currentDate.get('month') && selectedyear) classes += '.selected';
			element = new Element('div' + classes, {text: months[i]}).inject(container);

			if (isUnavailable('month', date, options)) element.addClass('unavailable');
			else element.addEvent('click', fn.pass(date.clone()));

			date.increment('month', 1);
			date.set('date', date.get('lastdayofmonth'));
		}

		return container;
	},

	days: function(options, date, currentDate, fn){
		var month = date.get('month'),
			limit = {left: false, right: false},
			todayString = new Date().toDateString(),
			currentString = currentDate.toDateString(),
			container = new Element('div.days'),
			titles = new Element('div.titles').inject(container),
			localeDaysShort = options.days_abbr || Locale.get('Date.days_abbr'),
			day, classes, element, weekcontainer, dateString;

		date.setDate(1);
		while (date.getDay() != options.startDay) date.setDate(date.getDate() - 1);

		for (day = options.startDay; day < (options.startDay + 7); day++){
			new Element('div.title.day.day' + (day % 7), {
				text: localeDaysShort[(day % 7)]
			}).inject(titles);
		}

		for (var i = 0; i < 42; i++){

			if (i % 7 == 0){
				weekcontainer = new Element('div.week.week' + (Math.floor(i / 7))).inject(container);
			}

			dateString = date.toDateString();
			classes = '.day.day' + date.get('day');
			if (dateString == todayString) classes += '.today';
			if (dateString == currentString) classes += '.selected';
			if (date.get('month') != month) classes += '.otherMonth';

			element = new Element('div' + classes, {text: date.getDate()}).inject(weekcontainer);

			if (isUnavailable('date', date, options)) element.addClass('unavailable');
			else element.addEvent('click', fn.pass(date.clone()));

			date.increment('day',  1);
		}

		return container;
	},

	time: function(options, date, currentDate, fn){
		var container = new Element('div.time'),
			// make sure that the minutes are timeWheelStep * k
			initMinutes = (date.get('minutes') / options.timeWheelStep).round() * options.timeWheelStep

		if (initMinutes >= 60) initMinutes = 0;
		date.set('minutes', initMinutes);

		var hoursInput = new Element('input.hour[type=text]', {
			title: Locale.get('DatePicker.use_mouse_wheel'),
			value: date.format('%H'),
			events: {
				click: function(event){
					event.target.focus();
					event.stop();
				},
				mousewheel: function(event){
					event.stop();
					hoursInput.focus();
					var value = hoursInput.get('value').toInt();
					value = (event.wheel > 0) ? ((value < 23) ? value + 1 : 0)
						: ((value > 0) ? value - 1 : 23)
					date.set('hours', value);
					hoursInput.set('value', date.format('%H'));
				}.bind(this)
			},
			maxlength: 2
		}).inject(container);

		var minutesInput = new Element('input.minutes[type=text]', {
			title: Locale.get('DatePicker.use_mouse_wheel'),
			value: date.format('%M'),
			events: {
				click: function(event){
					event.target.focus();
					event.stop();
				},
				mousewheel: function(event){
					event.stop();
					minutesInput.focus();
					var value = minutesInput.get('value').toInt();
					value = (event.wheel > 0) ? ((value < 59) ? (value + options.timeWheelStep) : 0)
						: ((value > 0) ? (value - options.timeWheelStep) : (60 - options.timeWheelStep));
					if (value >= 60) value = 0;
					date.set('minutes', value);
					minutesInput.set('value', date.format('%M'));
				}.bind(this)
			},
			maxlength: 2
		}).inject(container);

		new Element('div.separator[text=:]').inject(container);

		new Element('input.ok[type=submit]', {
			value: Locale.get('DatePicker.time_confirm_button'),
			events: {click: function(event){
				event.stop();
				date.set({
					hours: hoursInput.get('value').toInt(),
					minutes: minutesInput.get('value').toInt()
				});
				fn(date.clone());
			}}
		}).inject(container);

		return container;
	}

};


Picker.Date.defineRenderer = function(name, fn){
	renderers[name] = fn;
	return this;
};


var limitDate = function(date, min, max){
	if (min && date < min) return min;
	if (max && date > max) return max;
	return date;
};

var isUnavailable = function(type, date, options){
	var minDate = options.minDate,
		maxDate = options.maxDate,
		availableDates = options.availableDates;

	if (!minDate && !maxDate && !availableDates) return false;
	date.clearTime();

	if (type == 'year'){
		var year = date.get('year');
		return (
			(minDate && year < minDate.get('year')) ||
			(maxDate && year > maxDate.get('year')) ||
			(
				(availableDates != null) && (
					availableDates[year] == null ||
					Object.getLength(availableDates[year]) == 0 ||
					Object.getLength(
						Object.filter(availableDates[year], function(days){
							return (days.length > 0);
						})
					) == 0
				)
			)
		);
	}

	if (type == 'month'){
		var year = date.get('year'),
			month = date.get('month') + 1,
			ms = date.format('%Y%m').toInt();
		return (
			(minDate && ms < minDate.format('%Y%m').toInt()) ||
			(maxDate && ms > maxDate.format('%Y%m').toInt()) ||
			(
				(availableDates != null) && (
					availableDates[year] == null ||
					availableDates[year][month] == null ||
					availableDates[year][month].length == 0
				)
			)
		);
	}

	// type == 'date'
	var year = date.get('year'),
		month = date.get('month') + 1,
		day = date.get('date');
	return (
		(minDate && date < minDate) ||
		(maxDate && date > maxDate) ||
		(
			(availableDates != null) && (
				availableDates[year] == null ||
				availableDates[year][month] == null ||
				!availableDates[year][month].contains(day)
			)
		)
	);
};


// Parse times
Date.defineParsers(
	'%H:%M( ?%p)?' // "11:05pm", "11:05 am" and "11:05"
);

})();


/*
 Script: TextboxList.js
 Displays a textbox as a combination of boxes an inputs (eg: facebook tokenizer)

 Authors:
 Guillermo Rauch

 Note:
 TextboxList is not priceless for commercial use. See <http://devthought.com/projects/mootools/textboxlist/>.
 Purchase to remove this message.
 */

var Event = DOMEvent;
Event.Keys = {};
Event.Keys.up = 38;
Event.Keys.down = 40;
Event.Keys.enter = 13;
Event.Keys.left = 37;
Event.Keys.right = 39;
Event.Keys.backspace = 8;

//For deprecated methods
var $chk = function (obj) {
    return !!(obj || obj === 0);
};
var $type = function (obj) {
    return typeOf(obj);
}
var $clear = function (obj) {
    return window.clearTimeout(obj);
}
var $splat = function (obj) {
    return Array.from(obj);
}
var $pick = function () {
    for (var B = 0, A = arguments.length; B < A; B++) {
        if (arguments[B] != undefined) {
            return arguments[B];
        }
    }
    return null;
}


var TextboxList = new Class({

    Implements: [Options, Events],

    plugins: [],

    options: {/*
        onFocus: $empty,
        onBlur: $empty,
        onBitFocus: $empty,
        onBitBlur: $empty,
        onBitAdd: $empty,
        onBitRemove: $empty,
        onBitBoxFocus: $empty,
        onBitBoxBlur: $empty,
        onBitBoxAdd: $empty,
        onBitBoxRemove: $empty,
        onBitEditableFocus: $empty,
        onBitEditableBlue: $empty,
        onBitEditableAdd: $empty,
        onBitEditableRemove: $empty,*/
        prefix: 'textboxlist',
        max: null,
        unique: false,
        uniqueInsensitive: true,
        endEditableBit: true,
        startEditableBit: true,
        hideEditableBits: true,
        inBetweenEditableBits: true,
        keyboardDeleteEnabled: true,
        keys: {previous: Event.Keys.left, next: Event.Keys.right},
        bitsOptions: {editable: {}, box: {}},
        plugins: {},
        check: function (s) {
            return s.clean().replace(/,/g, '') != '';
        },
        encode: function (o) {
            return o.map(function (v) {
                v = ($chk(v[0]) ? v[0] : v[1]);
                return $chk(v) ? v : null;
            }).clean().join(',');
        },
        decode: function (o) {
            return o.split(',');
        }
    },

    initialize: function (element, options) {
        this.setOptions(options);
        this.element = element;
        this.original = $(element).setStyle('display', 'none').set('autocomplete', 'off').addEvent('focus', this.focusLast.bind(this));
        this.container = new Element('div', {'class': this.options.prefix}).inject(element, 'after');
        this.container.addEvent('click', function (e) {
            if ((e.target == this.list || e.target == this.container) && (!this.focused || $(this.current) != this.list.getLast())) this.focusLast();
        }.bind(this));
        this.list = new Element('ul', {'class': this.options.prefix + '-bits'}).inject(this.container);
        for (var name in this.options.plugins) this.enablePlugin(name, this.options.plugins[name]);
        ['check', 'encode', 'decode'].each(function (i) {
            this.options[i] = this.options[i].bind(this);
        }, this);
        this.afterInit();
    },

    enablePlugin: function (name, options) {
        this.plugins[name] = new TextboxList[name.camelCase().capitalize()](this, options);
    },

    afterInit: function () {
        if (this.options.unique) this.index = [];
        if (this.options.endEditableBit) this.create('editable', null, {tabIndex: this.original.tabIndex}).inject(this.list);
        var update = this.update.bind(this);
        this.addEvent('bitAdd', update, true).addEvent('bitRemove', update, true);
        document.addEvents({
            click: function (e) {
                if (!this.focused) return;
                if (e.target.className.contains(this.options.prefix)) {
                    if (e.target == this.container) return;
                    var parent = e.target.getParent('.' + this.options.prefix);
                    if (parent == this.container) return;
                }
                this.blur();
            }.bind(this),
            keydown: function (ev) {
                if (!this.focused || !this.current) return;
                var caret = this.current.is('editable') ? this.current.getCaret() : null;
                var value = this.current.getValue()[1];
                var special = ['shift', 'alt', 'meta', 'ctrl'].some(function (e) {
                    return ev[e];
                });
                var custom = special || (this.current.is('editable') && this.current.isSelected());
                switch (ev.code) {
                    case Event.Keys.backspace:
                        if (this.current.is('box')&& this.options.keyboardDeleteEnabled) {
                            ev.stop();
                            return this.current.remove();
                        }
                    case this.options.keys.previous:
                        if (this.current.is('box') || ((caret == 0 || !value.length) && !custom)) {
                            ev.stop();
                            this.focusRelative('previous');
                        }
                        break;
                    case Event.Keys['delete']:
                        if (this.current.is('box')&& this.options.keyboardDeleteEnabled) {
                            ev.stop();
                            return this.current.remove();
                        }
                    case this.options.keys.next:
                        if (this.current.is('box') || (caret == value.length && !custom)) {
                            ev.stop();
                            this.focusRelative('next');
                        }
                }
            }.bind(this)
        });
        this.setValues(this.options.decode(this.original.get('value')));
    },

    create: function (klass, value, options) {
        if (klass == 'box') {
            if ((!value[0] && !value[1]) || ($chk(value[1]) && !this.options.check(value[1]))) return false;
            if ($chk(this.options.max) && this.list.getChildren('.' + this.options.prefix + '-bit-box').length + 1 > this.options.max) return false;
            if (this.options.unique && this.index.contains(this.uniqueValue(value))) return false;
        }
        return new TextboxListBit[klass.capitalize()](value, this, Object.merge(this.options.bitsOptions[klass], options));
    },

    uniqueValue: function (value) {
        return $chk(value[0])
            ? (this.options.uniqueInsensitive ? value[0].toLowerCase() : value[0])
            : (this.options.uniqueInsensitive ? value[1].toLowerCase() : value[1]);
    },

    onFocus: function (bit) {
        if (this.current) this.current.blur();
        $clear(this.blurtimer);
        this.current = bit;
        this.container.addClass(this.options.prefix + '-focus');
        if (!this.focused) {
            this.focused = true;
            this.fireEvent('focus', bit);
        }
    },

    onBlur: function (bit, all) {
        this.current = null;
        this.container.removeClass(this.options.prefix + '-focus');
        this.blurtimer = this.blur.delay(all ? 0 : 200, this);
    },

    onAdd: function (bit) {
        if (this.options.unique && bit.is('box')) this.index.push(this.uniqueValue(bit.value));
        if (bit.is('box')) {
            var prior = this.getBit($(bit).getPrevious());
            if ((prior && prior.is('box') && this.options.inBetweenEditableBits) || (!prior && this.options.startEditableBit)) {
                var b = this.create('editable').inject(prior || this.list, prior ? 'after' : 'top');
                if (this.options.hideEditableBits) b.hide();
            }
        }
    },

    onRemove: function (bit) {
        if (this.options.unique && bit.is('box')) this.index.erase(this.uniqueValue(bit.value));
        var prior = this.getBit($(bit).getPrevious());
        if (prior && prior.is('editable')) prior.remove();
        if (this.focused) this.focusRelative('next', bit);
    },

    focusRelative: function (dir, to) {
        var b = this.getBit($($pick(to, this.current))['get' + dir.capitalize()]());
        if (b) b.focus();
        return this;
    },

    focusLast: function () {
        var lastElement = this.list.getLast();
        if (lastElement) this.getBit(lastElement).focus();
        return this;
    },

    blur: function () {
        if (!this.focused) return this;
        if (this.current) this.current.blur();
        this.focused = false;
        return this.fireEvent('blur');
    },

    add: function (plain, id, html, afterEl) {
        var b = this.create('box', [id, plain, html]);
        if (b) {
            if (!afterEl) afterEl = this.list.getLast('.' + this.options.prefix + '-bit-box');
            b.inject(afterEl || this.list, afterEl ? 'after' : 'top');
        }
        return this;
    },

    getBit: function (obj) {
        return ($type(obj) == 'element') ? obj.retrieve('textboxlist:bit') : obj;
    },

    getValues: function () {
        return this.list.getChildren().map(function (el) {
            var bit = this.getBit(el);
            if (!bit || bit.is('editable')) return null;
            return bit.getValue();
        }, this).clean();
    },

    setValues: function (values) {
        if (!values) return;
        values.each(function (v) {
            if (v) this.add.apply(this, $type(v) == 'array' ? [v[1], v[0], v[2]] : [v]);
        }, this);
    },

    update: function () {
        this.original.set('value', this.options.encode(this.getValues()));
    }

});

var TextboxListBit = new Class({

    Implements: Options,

    initialize: function (value, textboxlist, options) {
        this.name = this.type.capitalize();
        this.value = value;
        this.textboxlist = textboxlist;
        this.setOptions(options);
        this.prefix = this.textboxlist.options.prefix + '-bit';
        this.typeprefix = this.prefix + '-' + this.type;
        this.bit = new Element('li').addClass(this.prefix).addClass(this.typeprefix).store('textboxlist:bit', this);
        this.bit.addEvents({
            mouseenter: function () {
                this.bit.addClass(this.prefix + '-hover').addClass(this.typeprefix + '-hover');
            }.bind(this),
            mouseleave: function () {
                this.bit.removeClass(this.prefix + '-hover').removeClass(this.typeprefix + '-hover');
            }.bind(this)
        });
    },

    inject: function (element, where) {
        this.bit.inject(element, where);
        this.textboxlist.onAdd(this);
        return this.fireBitEvent('add');
    },

    focus: function () {
        if (this.focused) return this;
        this.show();
        this.focused = true;
        this.textboxlist.onFocus(this);
        this.bit.addClass(this.prefix + '-focus').addClass(this.prefix + '-' + this.type + '-focus');
        return this.fireBitEvent('focus');
    },

    blur: function () {
        if (!this.focused) return this;
        this.focused = false;
        this.textboxlist.onBlur(this);
        this.bit.removeClass(this.prefix + '-focus').removeClass(this.prefix + '-' + this.type + '-focus');
        return this.fireBitEvent('blur');
    },

    remove: function () {
        this.blur();
        this.textboxlist.onRemove(this);
        this.bit.destroy();
        return this.fireBitEvent('remove');
    },

    show: function () {
        this.bit.setStyle('display', 'block');
        return this;
    },

    hide: function () {
        this.bit.setStyle('display', 'none');
        return this;
    },

    fireBitEvent: function (type) {
        type = type.capitalize();
        this.textboxlist.fireEvent('bit' + type, this).fireEvent('bit' + this.name + type, this);
        return this;
    },

    is: function (t) {
        return this.type == t;
    },

    setValue: function (v) {
        this.value = v;
        return this;
    },

    getValue: function () {
        return this.value;
    },

    toElement: function () {
        return this.bit;
    }

});

TextboxListBit.Editable = new Class({

    Extends: TextboxListBit,

    options: {
        tabIndex: null,
        growing: true,
        growingOptions: {},
        stopEnter: true,
        addOnBlur: false,
        addKeys: Event.Keys.enter
    },

    type: 'editable',

    initialize: function (value, textboxlist, options) {
        this.parent(value, textboxlist, options);
        this.element = new Element('input', {type: 'text', 'class': this.typeprefix + '-input', autocomplete: 'off', value: this.value ? this.value[1] : ''}).inject(this.bit);
        if ($chk(this.options.tabIndex)) this.element.tabIndex = this.options.tabIndex;
        if (this.options.growing) new GrowingInput(this.element, this.options.growingOptions);
        this.element.addEvents({
            focus: function () {
                this.focus(true);
            }.bind(this),
            blur: function () {
                this.blur(true);
                if (this.options.addOnBlur) this.toBox();
            }.bind(this)
        });
        if (this.options.addKeys || this.options.stopEnter) {
            this.element.addEvent('keydown', function (ev) {
                if (!this.focused) return;
                if (this.options.stopEnter && ev.code === Event.Keys.enter) ev.stop();
                if ($splat(this.options.addKeys).contains(ev.code)) {
                    ev.stop();
                    this.toBox();
                }
            }.bind(this));
        }
    },

    hide: function () {
        this.parent();
        this.hidden = true;
        return this;
    },

    focus: function (noReal) {
        this.parent();
        if (!noReal) this.element.focus();
        return this;
    },

    blur: function (noReal) {
        this.parent();
        if (!noReal) this.element.blur();
        if (this.hidden && !this.element.value.length) this.hide();
        return this;
    },

    getCaret: function () {
        if (this.element.createTextRange) {
            var r = document.selection.createRange().duplicate();
            r.moveEnd('character', this.element.value.length);
            if (r.text === '') return this.element.value.length;
            return this.element.value.lastIndexOf(r.text);
        } else return this.element.selectionStart;
    },

    getCaretEnd: function () {
        if (this.element.createTextRange) {
            var r = document.selection.createRange().duplicate();
            r.moveStart('character', -this.element.value.length);
            return r.text.length;
        } else return this.element.selectionEnd;
    },

    isSelected: function () {
        return this.focused && (this.getCaret() !== this.getCaretEnd());
    },

    setValue: function (val) {
        this.element.value = $chk(val[0]) ? val[0] : val[1];
        if (this.options.growing) this.element.retrieve('growing').resize();
        return this;
    },

    getValue: function () {
        return [null, this.element.value.replace(/<\/?[^>]+(>|$)/g, ""), null];
    },

    toBox: function () {
        var val = this.getValue();
        var values = val[1].trim().split(/[,;]+/);
        var b = null;
        for (var i = 0; i < values.length; ++i) {
            var value = [ val[0], values[i].trim(), val[2] ];
            b = this.textboxlist.create('box', value);
            if (b) {
                b.inject(this.bit, 'before');
                this.setValue([null, '', null])
            }
        }
        return b;
    }

});

TextboxListBit.Box = new Class({

    Extends: TextboxListBit,

    options: {
        deleteButton: true
    },

    type: 'box',

    initialize: function (value, textboxlist, options) {
        this.parent(value, textboxlist, options);
        $chk(this.value[2])
            ? this.bit.set('html', this.value[2])
            : this.bit.set('text', this.value[1]);
        this.bit.addEvent('click', this.focus.bind(this));
        if (this.options.deleteButton) {
            this.bit.addClass(this.typeprefix + '-deletable');
            this.close = new Element('a', {href: 'javascript:void(0);', 'class': this.typeprefix + '-deletebutton', events: {click: this.remove.bind(this)}}).inject(this.bit);
        }
        this.bit.getChildren().addEvent('click', function (e) {
            e.stop();
        });
    },

    update: function (value) {
        this.textboxlist.index.erase(this.textboxlist.uniqueValue(this.getValue()));
        this.setValue(value);
        this.textboxlist.index.push(this.textboxlist.uniqueValue(this.getValue()));
        $chk(this.value[2])
            ? this.bit.set('html', this.value[2])
            : this.bit.set('text', this.value[1]);
        if (this.options.deleteButton) {
            this.close.inject(this.bit);
        }
    }

});



/*
Script: GrowingInput.js
	Alters the size of an input depending on its content

	License:
		MIT-style license.

	Authors:
		Guillermo Rauch
*/

(function(){

GrowingInput = new Class({
	
	Implements: [Options, Events],
	
	options: {
		min: 0,
		max: null,
		startWidth: 2,
		correction: 50
	},
	
	initialize: function(element, options){
		this.setOptions(options);
		this.element = $(element).store('growing', this).set('autocomplete', 'off');		                                                            		                                                           		
		this.calc = new Element('span', {
			'styles': {
				'float': 'left',
				'display': 'inline-block',
				'position': 'absolute',
				'left': -1000,
                'color':'transparent'
			}
		}).inject(this.element, 'after');					
		['font-size', 'font-family', 'padding-left', 'padding-top', 'padding-bottom', 
		 'padding-right', 'border-left', 'border-right', 'border-top', 'border-bottom', 
		 'word-spacing', 'letter-spacing', 'text-indent', 'text-transform'].each(function(p){
				this.calc.setStyle(p, this.element.getStyle(p));
		}, this);				
		this.resize();
		var resize = this.resize.bind(this);
		this.element.addEvents({blur: resize, keyup: resize, keydown: resize, keypress: resize});
	},
	
	calculate: function(chars){
		this.calc.set('html', chars.replace(/<\/?[^>]+(>|$)/g, ""));
		var width = this.calc.getStyle('width').toInt();
		return (width ? width : this.options.startWidth) + this.options.correction;
	},
	
	resize: function(){
		this.lastvalue = this.value;
		this.value = this.element.value;
		var value = this.value;		
		if($chk(this.options.min) && this.value.length < this.options.min){
			if($chk(this.lastvalue) && (this.lastvalue.length <= this.options.min)) return;
			value = str_pad(this.value, this.options.min, '-');
		} else if($chk(this.options.max) && this.value.length > this.options.max){
			if($chk(this.lastvalue) && (this.lastvalue.length >= this.options.max)) return;
			value = this.value.substr(0, this.options.max);
		}
		this.element.setStyle('width', this.calculate(value));
		return this;
	}
	
});

var str_repeat = function(str, times){ return new Array(times + 1).join(str); };
var str_pad = function(self, length, str, dir){
	if (self.length >= length) return this;
	str = str || ' ';
	var pad = str_repeat(str, length - self.length).substr(0, length - self.length);
	if (!dir || dir == 'right') return self + pad;
	if (dir == 'left') return pad + self;
	return pad.substr(0, (pad.length / 2).floor()) + self + pad.substr(0, (pad.length / 2).ceil());
};

})();


/*
Script: TextboxList.Autocomplete.js
	TextboxList Autocomplete plugin

	Authors:
		Guillermo Rauch

	Note:
		TextboxList is not priceless for commercial use. See <http://devthought.com/projects/mootools/textboxlist/>
		Purchase to remove this message.
*/

/*
	Please do not overwrite this b/c charles forked this.
*/

var Event = DOMEvent;
Event.Keys = {};
Event.Keys.up = 38;
Event.Keys.down = 40;
Event.Keys.enter = 13;
Event.Keys.left = 37;
Event.Keys.right = 39;
Event.Keys.backspace = 8;

(function(){

TextboxList.Autocomplete = new Class({

	Implements: Options,

	options: {
		minLength: 1,
		maxResults: 10,
		insensitive: true,
		highlight: true,
		highlightSelector: null,
		mouseInteraction: true,
		onlyFromValues: false,
		queryRemote: false,
		remote: {
			url: '',
			param: 'search',
			extraParams: {},
			loadPlaceholder: 'Please wait...'
		},
		method: 'standard',
		placeholder: 'Type to receive suggestions'
	},

	initialize: function(textboxlist, options){
		this.setOptions(options);
		this.textboxlist = textboxlist;
		this.textboxlist.addEvent('bitEditableAdd', this.setupBit.bind(this), true)
			.addEvent('bitEditableFocus', this.search.bind(this), true)
			.addEvent('bitEditableBlur', this.hide.bind(this), true)
			.setOptions({bitsOptions: {editable: {addKeys:[], stopEnter: false}}});
		if (this.textboxlist.options.unique){
			this.index = [];
			this.textboxlist.addEvent('bitBoxRemove', function(bit){
				if (bit.autoValue) this.index.erase(bit.autoValue);
				if (this.currentRequest) this.currentRequest.cancel();
			}.bind(this), true).addEvent('bitBoxAdd', function(bit){
				if (this.currentRequest) this.currentRequest.cancel();
			}.bind(this), true);
		}
		this.prefix = this.textboxlist.options.prefix + '-autocomplete';
		this.method = TextboxList.Autocomplete.Methods[this.options.method];
		this.mouse_in_container = false;
		autocom = this;
		this.container = new Element('div', {'class': this.prefix}).setStyle('width', this.textboxlist.container.getStyle('width').toFloat()-2).inject(this.textboxlist.container);
		this.container.addEvents({ mouseenter : function(){ /*alert('ctr in!');*/ autocom.mouse_in_container = true;}, mouseleave : function(){/*alert('ctr out!');*/ autocom.mouse_in_container = false;} });
		if ($chk(this.options.placeholder) || this.options.queryServer)
			this.placeholder = new Element('div', {'class': this.prefix+'-placeholder'}).inject(this.container);
		this.list = new Element('ul', {'class': this.prefix + '-results'}).inject(this.container);
		this.list.addEvent('click', function(ev){ ev.stop(); });
		this.values = this.results = this.searchValues = [];
		this.navigate = this.navigate.bind(this);
	},

	setValues: function(values){
		this.values = values;
	},

	setupBit: function(bit){
		bit.element.addEvent('keydown', this.navigate, true).addEvent('keyup', function(){ this.search(); }.bind(this), true);
	},

	search: function(bit){
		if (bit) this.currentInput = bit;
		if (!this.options.queryRemote && !this.values.length) return;
		var search = this.currentInput.getValue()[1];
		if (search.length < this.options.minLength) this.showPlaceholder(this.options.placeholder);
		if (search == this.currentSearch) return;
		this.currentSearch = search;
		this.list.setStyle('display', 'none');
		this.container.removeClass('textboxlist-autocomplete-show');
		if (search.length < this.options.minLength) return;
		if (this.options.queryRemote){
			if (this.searchValues[search]){
				this.values = this.searchValues[search];
			} else {
				var data = this.options.remote.extraParams, that = this;
				if ($type(data) == 'function') data = data.apply([], this);
				data[this.options.remote.param] = search;
				if (this.currentRequest) this.currentRequest.cancel();
				this.currentRequest = new Request.JSON({url: this.options.remote.url, data: data, onRequest: function(){
					that.showPlaceholder(that.options.remote.loadPlaceholder);
				}, onSuccess: function(response){
					if (response.status == 'success') {
						var data = response.data;
						that.values = data;
						that.showResults(search);
					}
				}}).send();
			}
		}
		//if (Object.getLength(this.values)) this.showResults(search);
	},

	showResults: function(search){
		var results = this.method.filter(this.values, search, this.options.insensitive, this.options.maxResults + (this.index ? this.index.length : 0));
		if (this.index) results = results.filter(function(v){
			for (var i = this.index.length; i--;)
				if (this.index[i][0] == v[0]) return false;
			return true;
		}, this);
        results = results.slice(0, this.options.maxResults);
		this.hidePlaceholder();
		if (!Object.getLength(results)) return;
		this.blur();

		this.container.addClass('textboxlist-autocomplete-show');
		this.list.empty().setStyle('display', 'block');
		Object.each(results, function(r){ this.addResult(r, search); }, this);
		if (this.options.onlyFromValues) this.focusFirst();
		this.results = results;
	},

	addResult: function(r, search){
		var element = new Element('li', {'class': this.prefix + '-result', 'html': $pick(r[3], r[1])}).store('textboxlist:auto:value', r);
		this.list.adopt(element);
		if (this.options.highlight) $$(this.options.highlightSelector ? element.getElements(this.options.highlightSelector) : element).each(function(el){
			if (el.get('html')) this.method.highlight(el, search, this.options.insensitive, this.prefix + '-highlight');
		}, this);
		if (this.options.mouseInteraction){
			element.setStyle('cursor', 'pointer').addEvents({
				mouseenter: function(){ this.focus(element); }.bind(this),
				mousedown: function(ev){
					ev.stop();
					$clear(this.hidetimer);
					this.doAdd = true;
				}.bind(this),
				mouseup: function(){
					if (this.doAdd){
						this.addCurrent();
						this.currentInput.focus();
						this.search();
						this.doAdd = false;
					}
				}.bind(this)
			});
			if (!this.options.onlyFromValues) element.addEvent('mouseleave', function(){ if (this.current == element) this.blur(); }.bind(this));
		}
	},

	hide: function(ev){
		if(this.mouse_in_container) {
			return;
		}
		this.hidetimer = (function(){
			this.hidePlaceholder();
			this.list.setStyle('display', 'none');
			this.container.removeClass('textboxlist-autocomplete-show');
			this.currentSearch = null;
		}).delay(Browser.ie ? 150 : 0, this);
	},

	showPlaceholder: function(customHTML){
		if (this.placeholder){
			this.placeholder.setStyle('display', 'block');
			if (customHTML) this.placeholder.set('html', customHTML);
		}
	},

	hidePlaceholder: function(){
		if (this.placeholder) this.placeholder.setStyle('display', 'none');
	},

	focus: function(element){
		if (!element) return this;
		this.blur();
		this.current = element.addClass(this.prefix + '-result-focus');
	},

	blur: function(){
		if (this.current){
			this.current.removeClass(this.prefix + '-result-focus');
			this.current = null;
		}
	},

	focusFirst: function(){
		return this.focus(this.list.getFirst());
	},

	focusRelative: function(dir){
		if (!this.current) return this;
		return this.focus(this.current['get' + dir.capitalize()]());
	},

	addCurrent: function(){
		if (!this.current) return;
		var value = this.current.retrieve('textboxlist:auto:value');
		var b = this.textboxlist.create('box', value.slice(0, 3));
		if (b){
			b.autoValue = value;
			if (this.index != null) this.index.push(value);
			this.currentInput.setValue([null, '', null]);
			b.inject($(this.currentInput), 'before');
		}
		this.blur();
		return this;
	},

	navigate: function(ev){
		switch (ev.code){
			case Event.Keys.up:
				ev.stop();
				(!this.options.onlyFromValues && this.current && this.current == this.list.getFirst()) ? this.blur() : this.focusRelative('previous');
				break;
			case Event.Keys.down:
				ev.stop();
				this.current ? this.focusRelative('next') : this.focusFirst();
				break;
			case 188:
			case 186:
			case 59:
			case Event.Keys.enter:
				ev.stop();
                this.addCurrentInput();
		}
	},
    addCurrentInput: function() {
        if (this.current) this.addCurrent();
        else {
            var c = this.list.getChildren();
            var v = this.currentInput.getValue()[1].trim().toLowerCase();
            for (var i = 0; i < c.length; ++i) {
                if (c[i].get('text').toLowerCase().trim() == v) {
                    this.focus(c[i]);
                    if (this.current) {
                        this.addCurrent();
                    }
                    return;
                }
            }
            if (!this.options.onlyFromValues) {
                var val = this.currentInput.getValue();
                var values = val[1].trim().split(/[,;]+/);
                var escaper = new Element('div');
                for (var i = 0; i < values.length; ++i) {
                    var value = values[i].trim();
                    value = [ value, value, '<i>'+escaper.set('text', value).get('html')+'</i>' ];
                    var b = this.textboxlist.create('box', value);
                    if (b){
                        b.autoValue = value;
                        if (this.index != null) this.index.push(value);
                        b.bit.addClass(b.typeprefix + '-pending');
                        b.inject($(this.currentInput), 'before');
                        this.currentInput.setValue([null, '', null]);
                    }
                }
                escaper.destroy();
            }
        }
    }

});

TextboxList.Autocomplete.Methods = {

	standard: {
		filter: function(values, search, insensitive, max){
            search = search.trim();
			var newvals = [], regexp = new RegExp(search.escapeRegExp(), insensitive ? 'i' : '');
			for (var i = 0; i < values.length; i++){
				if (newvals.length === max) break;
				if (values[i][1].trim().test(regexp))newvals.push(values[i]);
			}
			return newvals;
		},

		highlight: function(element, search, insensitive, klass){
			var regex = new RegExp('(<[^>]*>)|('+ search.escapeRegExp() +')', insensitive ? 'ig' : 'g');
			return element.set('html', element.get('html').replace(regex, function(a, b, c){
				return (a.charAt(0) == '<') ? a : '<strong class="'+ klass +'">' + c + '</strong>';
			}));
		}
	}

};

})();


/*
---

name: Form.AutoGrow

description: Automatically resizes textareas based on their content.

authors: Christoph Pojer (@cpojer)

credits: Based on a script by Gary Glass (www.bookballoon.com)

license: MIT-style license.

requires: [Core/Class.Extras, Core/Element, Core/Element.Event, Core/Element.Style, Core/Element.Dimensions, Class-Extras/Class.Binds, Class-Extras/Class.Singleton]

provides: Form.AutoGrow

...
*/

(function(){

var wrapper = new Element('div').setStyles({
	overflowX: 'hidden',
	position: 'absolute',
	top: 0,
	left: -9999
});

var escapeHTML = function(string){
	return string.replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
};

if (!this.Form) this.Form = {};

var AutoGrow = this.Form.AutoGrow = new Class({

	Implements: [Options, Class.Singleton, Class.Binds],

	options: {
		minHeightFactor: 2,
		margin: 0
	},

	initialize: function(element, options){
		this.setOptions(options);
		element = this.element = document.id(element);
		
		return this.check(element) || this.setup();
	},

	setup: function(){
		this.attach().focus().resize();
	},

	toElement: function(){
		return this.element;
	},

	attach: function(){
		this.element.addEvents({
			focus: this.bound('focus'),
			keydown: this.bound('keydown'),
			scroll: this.bound('scroll'),
			change: this.bound('change')
		});

		return this;
	},

	detach: function(){
		this.element.removeEvents({
			focus: this.bound('focus'),
			keydown: this.bound('keydown'),
			scroll: this.bound('scroll'),
			change: this.bound('change')
		});

		return this;
	},

	focus: function(){
		wrapper.setStyles(this.element.getStyles('fontSize', 'fontFamily', 'width', 'lineHeight', 'padding')).inject(document.body);

		this.minHeight = (wrapper.set('html', 'A').getHeight() + this.options.margin) * this.options.minHeightFactor;
		// added max height support
		if(this.element.getStyle('maxHeight')) {
			this.maxHeight = parseFloat(this.element.getStyle('maxHeight'));
		}

		return this;
	},

	keydown: function(){
		this.resize.delay(15, this);
	},

	change: function(){
		//alert('in here');
		this.resize.delay(15, this);
	},

	resize: function(){
		var element = this.element,
			html = escapeHTML(element.get('value')).replace(/\n|\r\n/g, '<br/>A');
		
		if (wrapper.get('html') == html) return this;

		wrapper.set('html', html);
		var height = wrapper.getHeight() + this.options.margin;
		if (element.getHeight() != height){
			var computedHeight = this.minHeight.max(height);
			if(this.maxHeight) {
				computedHeight = computedHeight.min(this.maxHeight);
			}
			element.setStyle('height', computedHeight);

			AutoGrow.fireEvent('resize', [this]);

			// This is to allow scrollbar after reaching man height
			if(this.maxHeight) {
				if(this.minHeight.max(height) >= this.maxHeight) {
					element.setStyle('overflow-y', 'scroll');
				} else {
					element.setStyle('overflow-y', 'hidden');
				}
			}
		}
		
		return this;
	},

	scroll: function(){
		if(!this.maxHeight) {
			this.element.scrollTo(0, 0);
		}
	}

});

AutoGrow.extend(new Events);

}).call(this);
