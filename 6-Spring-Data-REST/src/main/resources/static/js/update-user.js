(function($){
	$.isBlank = function(obj){
		return(!obj || $.trim(obj) === "");
	};
	
	$.getUrlParameter = function getUrlParameter(sParam) {
	    var sPageURL = window.location.search.substring(1),
	        sURLVariables = sPageURL.split('&'),
	        sParameterName,
	        i;

	    for (i = 0; i < sURLVariables.length; i++) {
	        sParameterName = sURLVariables[i].split('=');

	        if (sParameterName[0] === sParam) {
	            return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
	        }
	    }
	};
})(jQuery);

$(document).ready(function(){
	
	var USER_API_BASE_PATH = "/api-rest";
	
	var userId_from_pathname = $.getUrlParameter("userId");
	
	var validateUserForm = function(new_user){
		var valid = true;
		if($.isBlank(new_user.name)){
			valid = false;
			$("#name_error").text("Name is mandatory");
		}else{
			$("#name_error").text("");
		}
		if($.isBlank(new_user.email)){
			valid = false;
			$("#email_error").text("Email is mandatory");
		}else{
			$("#email_error").text("");
		}
		return valid;
	}
	
	var retrieveUser = function(userId){
		$.ajax({
			url: USER_API_BASE_PATH+"/users/"+userId,
			success: function( result ) {
				$("#name").val(result.name);
				$("#email").val(result.email);
			},
			error: function( jqXHR, textStatus, errorThrown ) {
				console.log(jqXHR);
			}
			
		});
	}
	
	$("#update_user").on("click", function(event){
		event.preventDefault();
		
		var updated_user = {
			name: $("#name").val().trim(),
			email: $("#email").val().trim()
		}
		
		if(validateUserForm(updated_user)){
		
			$.ajax({
				url: USER_API_BASE_PATH+"/users/"+userId_from_pathname,
				method: "PUT",
				contentType: "application/json; charset=UTF-8",
				dataType: "json",
				data: JSON.stringify(updated_user),
				success: function( result ) {
					window.location.href = "/";
				},
				error: function( jqXHR, textStatus, errorThrown ) {
					console.log(jqXHR);
				}
				
			});
		}
	});
	
	retrieveUser(userId_from_pathname);
});