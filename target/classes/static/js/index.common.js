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

$("#imgInp").change(function(){
    readURL(this);
});