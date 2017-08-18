//Tracks the values of First Name and Last Name input fields
var fName = "", lName= "";

//The current activity and edit password the user has entered.
var act = "", edit = "";

//The names of people who are possible editors.
var editors = ["ALECHELYAR", "SHAIGERALD", "SHAILICIAGERALD", "BEAUGARDNER", 
	"TADBERUBE", "MIKETRAYLOR", "MICHAELTRAYLOR", "ALEXMILLIKEN"];

//The return JSON object from TrafficHandler
var serverReturn = "";

//The current page that the user is on.
var currPage = -1;

//Used to determine whether a student has completed the assignment
var completed = false;

/**
 * On document ready, assign event listeners to the file.
 */
$(document).ready(function() {
	//Add event listener to firstName input
	$(document).on("change keyup paste", ".finput", function() {
		fName = $(".finput").val().toUpperCase();
		if (editors.includes(fName+lName))
			$(".einput").show();
		else
			$(".einput").hide();
	});

	//Add event listener to lastName input
	$(document).on("change keyup paste", ".linput", function() {
		lName = $(".linput").val().toUpperCase();
		if (editors.includes(fName+lName))
			$(".einput").show();
		else
			$(".einput").hide();
	});
	
	//Add event listener to select option
	$(document).on("change", "#projectSelect", function() {
		$("#projectSelect").css("color", "black");
	});
	
	//Add event listener to submit button
	$(document).on("click", "#submit", function() {
		var errorText = "Please enter a valid ", num = 0;
		
		fName = $(".finput").val().toUpperCase();
		lName = $(".linput").val().toUpperCase();
		
		//Begin asserting that user info is correct
		if (fName === "" || !/^[a-zA-Z]+$/.test(fName)) {
			errorText += "First Name*";
			num++;
			$(".finput").attr("placeholder", "First Name*");
			$(".finput").css("color", "red");
		}
		if (lName === "" || !/^[a-zA-Z]+$/.test(lName)) {
			errorText += (num > 0) ? ", Last Name*" : "Last Name*";
			num++;
			$(".linput").attr("placeholder", "First Name*");
			$(".linput").css("color", "red");
		}
		if (!($("#projectSelect").val() > 0)) {
			errorText += (num > 0) ? ", Activity*" : "Activity*";
			num++;
		}
		if (num > 0) {
			$("#errorText").text(errorText);
			$("#errorText").css("opacity", 1);
		}
		else { //Else the user input is correct
			act = $("#projectSelect option:selected").text();
			edit = $(".einput").val();
			postDocRequest(10);
		}
	});
	
	//Prevent scroll caching
	$(window).scrollTop(0);
	
	//Prevent form auto input
	act = $("#projectSelect").val();
});

/**
 * This method handles an ajax request for form data.
 * @param retryNum The number of times that this should be retried
 * @returns Returns nothing, values are stored in serverReturn.
 */
function postDocRequest(retryNum) {
	$("#submit").text("Loading...");
	$("#submit").prop('disabled', true);
	$.ajax({
		type: 'POST',
		url: 'TrafficHandler',
		dataType: 'json',
		data: '{\"firstName\":\"' + fName + '\",\"lastName\":\"' + lName + 
			'\",\"activity\":\"' + act + '\",\"edit\":\"' + edit + 
			'\",\"page\":\"Entry\"}',
		success: function(ret) {
			serverReturn = ret;
			if (serverReturn.constructor === Object) {
				loadAdminPage();
			}
			else
				loadGoogleFrames();
			hideWelcomeForm();
			$(".loginName").text("Logged in as " + fName + " " + lName);
		},
		error: function(ret) {
			console.log(ret);
			console.log(ret.toString());
			if (retryNum > 0) {
				setTimeout(function() {postDocRequest(retryNum - 1);}, 4000);
				console.log("Retrying...");
			}
			else {
				$("#submit").text("Retry");
				$("#submit").prop('disabled', false);
			}
		}
	});
}

/**
 * Load an admin page instead of google docs for admins using map given
 * by POST response.  
 */
function loadAdminPage() {
	//Remove google doc content page
	$(".contentPage").remove();
	
	//Create admin page
	var adminPage = $("<div style='height:100%;width:100%;background-c" +
			"olor:black;'></div>");
	$("body").append(adminPage);
	
	//Create admin title text
	var adminText = $("<p style='font-size:130px;font-family:Courier;" +
			"position:absolute;text-align:center;top:2%;color:white;widt" +
			"h:100%;margin:0;'>Admin Page</p>");
	adminPage.append(adminText);
	
	//Create user lists
	var listTitle = $("<p style='font-size:50px;font-family:Roboto;posi" +
			"tion:absolute;text-align:center;top:30%;color:white;margi" +
			"n:0;left:37%'>Users:</p>");
	adminPage.append(listTitle);
	
	var list = $("<ul id='userList' style='position:absolute;width:40%;" +
			"left:30%;top:40%;height:40%;overflow-x:hidden;overflow-y:auto" +
			"'></ul>");
	
	//Assign click handlers to users
	for (let person = 0; person < serverReturn.data.length; person++) {
		var newItem = $("<li id='li" + person + "' style='cursor:pointer;'>" + 
				serverReturn.data[person].name + "</li>");
		list.append(newItem);

		$(document).on("click", "#li" + person, function() {
			list.hide();
			listTitle.hide();
			loadChart(person);
			var returnButton = $("<button style='width:4%;height:3%;positi" +
					"on:fixed;top:20%;left:47%;background-color:black;" +
					"color:white;border-radius:3px;z-index:20;cursor:po" +
					"inter'>Return</button>");
			$(document).on("click", "button", function() {
				$("#con").remove();
				list.show();
				listTitle.show();
				$("button").remove();
			});
			adminPage.append(returnButton);
		});
		
	}
	adminPage.append(list);
}

/**
 * This function creates a chart which displays the times at which
 * a user has visited the site.
 */
function loadChart(x) {
	//Load chart container
	var container = $("<div id='con' style='position:absolute;width:100%;heigh" +
			"t:80%;top:20%;'></div>");
	var chartContainer = $("<div style='position:relative; height:80%; " +
			"width:90%; top:10%; left:5%'></div>");
	var canvas = document.createElement("canvas");
	canvas.style.width = '100%';
	canvas.style.height = '100%';
	chartContainer.append(canvas);
	container.append(chartContainer);
	$("body").append(container);
	var ctx = canvas.getContext('2d');
	var dataset = createDataSet(x);
	var userChart = new Chart(ctx, {
		type: 'line',
		data: {
			datasets: [
				{
					type: 'line',
					label: 'User logs',
					data: dataset,
					borderColor: 'white',
					steppedLine: false,
					lineTension: 0,
					fill: true,
					backgroundColor: 'white',
					borderColor: 'black',
					pointRadius: 5,
					pointHoverRadius: 5,
					pointBorderWidth: 2
				}
			]
		},
		options: {
			responsive: true,
			title : {
				display: true,
				text: serverReturn.data[x].name + 
					" - Reached end of assignment: " + completed,
				fontColor: 'white',
				fontSize: 40,
				fontFamily: "Roboto, Helvetica, Arial, sans-serif",
				fontStyle: "normal"
			},
			legend : {
				display: false
			},
			scales : {
				xAxes: [{
					display: true,
					type: 'time',
					gridLines: {
						display: true,
						color: 'white'
					},
					scaleLabel: {
						display: true,
						labelString: 'Date and Time (UTC) from ' + 
							dataset[0].x.format('MM/DD/YY') + " to " + 
							dataset[dataset.length - 1].x.format('MM/DD/YY'),
						fontColor: 'white',
						fontSize: 25
					},
					ticks: {
						fontColor: "white",
						fontSize: 14,
						autoSkip: true,
						minRotation: 70,
						maxRotation: 70
					}
				}],
				yAxes: [{
					display: true,
					gridLines: {
						display: true,
						color: 'white'
					},
					scaleLabel: {
						display: true,
						labelString: 'Page',
						fontColor: 'white',
						fontSize: '20'
					},
					ticks: {
						fontColor: "white",
						min: 0,
						max: serverReturn.pages.length,
						stepSize: 1,
						callback: function(tickLabel) {
							if (tickLabel === 0) {
								return 'Entry';
							}
							return serverReturn.pages[tickLabel - 1].Name;
						}
					}
				}]
			}
		}
	});
}

/**
 * This function creates a dataset for a Chart.js chart using an index
 * in the array recieved from a POST response.
 */
function createDataSet(userIndex) {
	//Set completed to false
	completed = false;
	
	//Map page names to a number used to visualize its order
	var pages = {};
	pages.Entry = 0;
	for (let pageIndex in serverReturn.pages) {
		pages[serverReturn.pages[pageIndex].Name] = parseInt(pageIndex) + 1;
	}

	//Turn string date into date format and assign to it a value representing
	//the page index
	var data = [];
	for (let log in serverReturn.data[userIndex].logs) {
		var check = {x: stringToDate(serverReturn.data[userIndex].logs[log].date), 
				y: pages[serverReturn.data[userIndex].logs[log].page]};
		data.push(check);
		
		if (pages[serverReturn.data[userIndex].logs[log].page]
			== serverReturn.pages.length) {
			completed = true;
		}
	}
	return data;
}

/**
 * This function parses a string date and returns a Date object.
 */
function stringToDate(str) {
	var month;
	switch(str.substr(0,3)) {
		case "Jan" : month = 0; break;
		case "Feb" : month = 1; break;
		case "Mar" : month = 2; break;
		case "Apr" : month = 3; break;
		case "May" : month = 4; break;
		case "Jun" : month = 5; break;
		case "Jul" : month = 6; break;
		case "Aug" : month = 7; break;
		case "Sep" : month = 8; break;
		case "Oct" : month = 9; break;
		case "Nov" : month = 10; break;
		case "Dec" : month = 11; break;
	}
	var year = parseInt(str.substr(20, 24));
	var day = parseInt(str.substr(4, 6));
	var hour = parseInt(str.substr(7, 9));
	var min = parseInt(str.substr(10, 12));
	var sec = parseInt(str.substr(13, 15));

	var date = new Date();
	date.setUTCHours(hour);
	date.setUTCFullYear(year);
	date.setUTCMinutes(min);
	date.setUTCSeconds(sec);
	date.setUTCMonth(month);
	date.setUTCDate(day);
	return moment(date);
}

/**
 * Load the google iFrames on the html page based upon serverReturn
 * @returns nothing.
 */
function loadGoogleFrames() {
	$(".projectTitle").text(act);
	
	jQuery.each(serverReturn, function(index, page) {
		var newPage = $("<li id=\"li" + index + "\">" + page.Name + "</li>");
		$(newPage).css("display", "none");
		$("ul").append(newPage);
		
		var innerDiv = $("<div class='innerContent' id='content" + index + "'></div>");
		$(innerDiv).css("z-index", serverReturn.length + 4 - index);
		
		var newFrame = $("<iframe id=\"frame" + index + 
				"\" src=\"" + page.URL + "\"></iframe>");
		
		$(innerDiv).append(newFrame);
		$(".contentContainer").append(innerDiv);
	});
	
	$(document).on('click', '#nextStep', function() {
		nextPage();
		checkButtons();
	});
	
	$(document).on('click', '#prevStep', function() {
		prevPage();
		checkButtons();
	});
	
	$("#li0").addClass("liSelected");
}

/**
 * This function determines whether buttons should appear or not
 */
function checkButtons() {
	if (currPage == 0) {
		$("#prevStep").css("display", "none");
	}
	else if (currPage == serverReturn.length - 1) {
		$("#nextStep").css("display", "none");
	}
	else {
		$("#prevStep").css("display", "block");
		$("#nextStep").css("display", "block");
	}
}

/**
 * This function moves to the next page
 */
function nextPage() {
	if (currPage + 1 < serverReturn.length) {
		//Remove left bar selection
		$(".liSelected").removeClass("liSelected");
		
		//Hide top page
		$("#content" + currPage).fadeOut(400);
		
		//Add new left bar selection
		currPage++;
		$("#li" + currPage).css("display", "block");
		$("#li"+currPage).addClass("liSelected");
		
		//Log and send
		sendRequest();
	}
}

/**
 * This function moves to the previous page
 */
function prevPage() {
	if (currPage > 0) {
		$(".liSelected").removeClass("liSelected");
		currPage--;
		$("#content" + currPage).fadeIn(400);
		$("#li"+currPage).addClass("liSelected");
		sendRequest();
	}
}

/**
 * This function sends a request with user info informing the server that the
 * user has changed page.  This is used to record user info.
 * @returns nothing.
 */
function sendRequest() {
	var page = serverReturn[currPage].Name;
	$.ajax({
		type: 'POST',
		url: 'TrafficHandler',
		dataType: 'json',
		data: '{\"firstName\":\"'+fName+'\",\"lastName\":\"'+lName+'\",\"activity\":\"'+act+'\",\"edit\":\"'+edit+'\",\"page\":\"'+page+'\"}',
		success: function(ret) {
			//Do nothing
		},
		error: function(ret) {
			console.log(ret);
			console.log(ret.toString());
			console.log(ret.length);
		}
	});
}

/**
 * This function handles the animation for the server dissapearing.
 * @returns nothing.
 */
function hideWelcomeForm() {
	$("#submit").prop("disabled", true);
	$(".welcomePage").addClass("hide");
	$(".welcomePage").delay(3000).queue(function() {
		$(".welcomePage").remove();
		$(".contentPage").addClass("show");
		nextPage();
		checkButtons();
	});
}