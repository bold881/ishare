function readURL(input) {

    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
        	
        	$('#postimg').attr('src', e.target.result).width(75).height(75);
        	document.getElementById("postimg").style.visibility = "visible";
        }

        reader.readAsDataURL(input.files[0]);
    }
}

// upload image preview
$("#imgInp").change(function(){
    readURL(this);
});


// load posttext by page
function getPostText(index, pageSize) {
	var dataReq = {"index": index, "pageSize": pagesize};
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url: "api/getposttext",
		data: JSON.stringify(dataReq),
		dataType: 'json',
		cache: false,
		timeout: 60000,
		success: function(retData) {
			
		}
	});
}


// jquery scroll down ajax load more
$(document).ready(fuction(){
	$(window).scroll(function() {
		if($(window).scrollTop() == $(document).height() - $(window).height()) {
			
		}
	});
});