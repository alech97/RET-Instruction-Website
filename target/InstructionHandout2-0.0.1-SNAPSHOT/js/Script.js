/**
 * Alec Helyar
 * Created 6/26/17
 * Handles client side scripts, including editing/previewing options
 */

//JSON object containing page data
var urlString = "";

/**
 * Document load function.  Responsible for pop ups.
 */
$(document).ready(function() {
	//Add popup container to screen
	var popup = $("<div id='popup'>");
	$("body").append(popup);
	
	//Initialize popupBox
	var popupContent = $("<div id='popupBox'>Would you like to edit or preview?</div>");
	
	//Add button1 to popupBox
	var button1 = $("<button type='button' id='button1'>Edit</button>");
	$("body").on('click', '#button1', function() {
		//var name = prompt("Please enter your first name:", "").toUpperCase();
		//var email = prompt("Please enter your email:", "").toUpperCase();
		//var url = 'TrafficHandler?mode=Edit&name=' + name + '&email=' + email;
		//console.log(url);
		$.ajax({
			type: 'GET',
			url: 'TrafficHandler?mode=Edit',
			success: function(ret) {
				urlString = ret;
				console.log(ret);
				loadVerticalBar();
				alert(ret);
				$("#popup").remove();
				//$("#loginString").html("Logged in as " + name);
			},
			error: function(jqXHR, textStatus, error) {
				alert("Error");
				console.log("jqXHR: " + jqXHR + ", exception: " + textStatus + ", error: " + error);
			}
		});
	});
	popup.append(button1);
	
	//Add button2 to popupBox
	var button2 = $("<button type='button' id='button2'>Preview</button>");
	$("body").on('click', '#button2', function() {
		$("#popup").remove();
		//$.ajax({
		//	type: 'GET',
		//	url: 'TrafficHandler?mode=Preview',
		//	success: function(ret) {
		//		urlString = ret;
		//		loadVerticalBar();
		//	},
		//	error: function() {
		//		alert("Error");
		//	}
		//});
		urlString = [{"ID": 0, "Name":"Page 1", 
			"URL":"https://docs.google.com/document/d/1LBgUnYFZlFzyuOGHArjYodMYFzY2iQ9CzI2tSnpLNqM/pub?embedded=true"}, 
			{"ID": 1, "Name":"Page 2", 
				"URL":"https://docs.google.com/document/d/13y2YGy6pTUUCd2KxjKncI8WmrRwzsxhcc--ZdLoVa_8/pub?embedded=true"}, 
			{"ID": 2, "Name":"Page 3", 
				"URL":"https://docs.google.com/document/d/1UHMpRTws7pBHKdP136FVa-OSTJ1gSH_3zM_s05hvpfs/pub?embedded=true"}];
		$("#popup").remove();
		loadVerticalBar();
	});
	popup.append(button2);
	
	//Add popupBox to popup container
	popup.append(popupContent);
});

/**
 * Load pages based upon received JSON data in urlString.
 */
function loadVerticalBar() {
	if (urlString == "")
		return;
	for (var i = 0; i < urlString.length; i++) {
		//Error logging
		console.log(urlString[i].ID + ", " + urlString[i].Name);
		
		//Initialize a verticalBlock
		var value = "verticalBlock" + i;
		console.log("Initializing " + value);
		var newBlock = $("<li class='verticalBlocks' id='"+value+"'>");
		
		//Add text to it
		var text = $("<div class='sideText'>" + urlString[i].Name +"</div>");
		newBlock.append(text);
		
		//Attach an eventListener to this block
		attachClickHandler("#verticalBlock"+i);
		
		//Append block to the verticalPanel
		$(".verticalPanel").append(newBlock);
		
		//Load page number 0
		loadPage(0);
	}
}

/**
 * This function attaches a mouse click event handler to a given verticalBlock.
 * @param block Should be of the format: "#verticalBlock<i>" where i is the index.
 */
function attachClickHandler(block) {
	console.log("Attaching click handler to " + block + "...");
	$("body").on('click', block, function() {
		console.log(block + " was pressed.");
		loadPage(Number.parseInt(block.substring(14,15)));
	});
}

/**
 * This function loads a new page.  It should handle changing button variables
 * and loading a new google doc value.
 * @param id The index of the page and the id of the page in the database.
 */
function loadPage(id) {
	if (!isNaN(id)) { //Just in case
		//.on() on previous .currBlock
		$("body").on('click', '.currBlock', function() {
			loadPage(Number.parseInt(this.id.substring(13,14)));
		});
		
		//Remove .currBlock from previous block
		$('.currBlock').removeClass("currBlock");
		
		//Add .currBlock to current id
		$('#verticalBlock' + id).addClass("currBlock");
		
		//.off() click functionality for this block
		$('#verticalBlock' + id).off('click');
		
		//Load google doc for this id
		loadGoogleDoc(urlString[id].URL);
	}
	else
		console.log("How did a string get here?? " + id);
}

/**
 * Loads a google doc based upon urlString
 * @param url The string url being loaded
 * @returns nothing
 */
function loadGoogleDoc(url) {
	if (url != "no content" && url != "") {
		var googleDoc = $("iframe");
		if (googleDoc.length == 0) {
			googleDoc = $("<iframe id='gframe'></iframe>");
			$(googleDoc).attr('src', url);
			$("#content").append(googleDoc);
		}
		else
			$(googleDoc).attr('src', url);
		console.log("GoogleDoc loaded.");
	}
}