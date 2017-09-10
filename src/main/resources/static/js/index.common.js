var pageIndex = 0;

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
$("#imgInp").change(function () {
	readURL(this);
});


// load posttext by page
function getPostText(index, pageSize) {
	var dataReq = { "pageIndex": index, "pageSize": pageSize };
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url: "api/posttext",
		data: JSON.stringify(dataReq),
		dataType: 'json',
		cache: false,
		timeout: 60000,
		success: function (retData) {
			//console.log(retData);
			var lstPostText = retData;
			for (var pt in lstPostText) {
				var content = '<div> \
									<div class="col-lg-offset-3 col-lg-6">\
											<div class="thumbnail">\
												<a href="/postimg/' + lstPostText[pt].postImages[0] + '" target="_blank">\
													<img src="/postimg/' + lstPostText[pt].cpPostImages[0] + '" alt="' + lstPostText[pt].content + '" >\
													<div class="caption">\
														<p>'+ lstPostText[pt].content + '---' + new Date(lstPostText[pt].date).toLocaleString() + '</p></div></a></div></div></div>';
				$('#div-contents').append(content);
			}
		},
		error: function (e) {
			console.log("ERROR: ", e);
		},
		done: function (e) {
			console.log("DONE");
		}
	});
}


// jquery scroll down ajax load more
$(window).scroll(function () {
	if ($(window).scrollTop() == $(document).height() - $(window).height()) {
		pageIndex++;
		getPostText(pageIndex, 3);
	}
});