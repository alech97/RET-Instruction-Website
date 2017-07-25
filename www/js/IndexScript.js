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

//This javascript file has been reached
console.log("File reached");

/**
 * On document ready, assign event listeners to the file.
 */
$(document).ready(function() {
	//Add event listener to firstName input
	$(document).on("change keyup paste", ".finput", function() {
		fName = $(".finput").val().toUpperCase();
		console.log($(document).scrollTop());
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
		data: '{\"firstName\":\"'+fName+'\",\"lastName\":\"'+lName+'\",\"activity\":\"'+act+'\",\"edit\":\"'+edit+'\",\"page\":\"Entry\"}',
		success: function(ret) {
			serverReturn = ret;
			loadGoogleFrames();
			hideWelcomeForm();
			$(".loginName").text("Logged in as " + fName + " " + lName);
			console.log(ret);
			console.log(ret.toString());
			console.log(ret.length);
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
		
		console.log(index, page);
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
	console.log("Next Button pressed");
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
		console.log("Curr page: " + currPage);
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
			console.log("User info sent");
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
	console.log("Hide welcome form called");
	
	$("#submit").prop("disabled", true);
	$(".welcomePage").addClass("hide");
	$(".welcomePage").delay(3000).queue(function() {
		$(".welcomePage").remove();
		$(".contentPage").addClass("show");
		nextPage();
		checkButtons();
	});
}