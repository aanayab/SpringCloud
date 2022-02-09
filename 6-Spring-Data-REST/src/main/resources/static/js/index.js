$(document).ready(function(){
	
	var USER_API_BASE_PATH = "/api-rest";
	
	var page_size = 5;
	var current_page = 0;
	
	$(document).on("click", "a.pagination-link", function(event){
		var $this = $(this);
		
		current_page = $this.attr("page-data")-1;
		
		loadUsers(current_page, page_size);
	});
	
	$(document).on("click", "a.delete-link", function(event){
		var $this = $(this);
		
		deleteUser($this.attr("userId-data"));
	});
	
	var deleteUser = function(userId){
		
		$.ajax({
			url: USER_API_BASE_PATH+"/users/"+userId,
			method: "DELETE",
			success: function( result ) {
				loadUsers(0, page_size);
			},
			error: function( jqXHR, textStatus, errorThrown ) {
				console.log(jqXHR);
			}
			
		});
	}
	
	var printUsers = function(users){
		
		var tbody = $("#users_table_tbody");
		tbody.empty();
		
		$.each(users, function(index, value){
			
			var tr = $("<tr>");
			
			tr.append( $("<td>").append(value.name));
			tr.append( $("<td>").append(value.email));
			tr.append( $("<td>").append( 
					$("<a>").addClass("btn btn-primary edit-link").attr("href","/update-user-form.html?userId="+value.id).
					append( $("<i>").addClass("fas fa-user-edit ml-2"))));
			tr.append( $("<td>").append( 
					$("<a>").addClass("btn btn-primary delete-link").attr("href","javascript:").attr("userId-data", value.id).
					append( $("<i>").addClass("fas fa-user-times ml-2"))));
			
			tbody.append(tr);
		});
	}
	
	var printNavigation = function(page){

		var page_nav = $("#page_navigation");
		
		page_nav.empty();
		
		var ul = $("<ul>").addClass("pagination");
		
		for(var i=0; i<page.totalPages; i++){
			ul.append( $("<li>").addClass("page-item").append(
				$("<a>").addClass("page-link pagination-link").attr("href","javascript:").attr("page-data", i+1).text(i+1)));
		}
		
		page_nav.append(ul);
	}
	
	var loadUsers = function(page, size){
		
		$.ajax({
			url: USER_API_BASE_PATH+"/users?page="+page+"&size="+size,
			data: {
				
			},
			success: function( result ) {
				if(result._embedded.users.length==0){
					$("#no_users").show();
					$("#have_users").hide();
				}else {
					$("#no_users").hide();
					$("#have_users").show();
					printUsers(result._embedded.users);
					printNavigation(result.page);
				}
				
			}
		});
	}
	
	loadUsers(current_page, page_size);
});