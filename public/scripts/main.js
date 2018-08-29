$("form").submit(function(){
    if($('#input-first-name').val().length === 0 ){
        $("#first-name").css("display","block");
        $("#input-first-name").focus();
        return false;
    }
    else {
        $("#first-name").css("display","hidden");
    }
    if($('#input-last-name').val().length === 0 ){
        $("#last-name").css("display","block");
        $("#input-last-name").focus();
        return false;
    }
    else {
        $("#last-name").css("display","hidden");
    }



    if($('#input-password').val().length === 0 ){
        $("#password").css("display","block");
        $("#input-password").focus();
        return false;
    }
    else {
        $("#password").css("display","hidden");
    }
    if($('#input-password').val() !== $('#input-confirm-password').val()){
        $("#match-password").css("display","block");
        $("#input-confirm-password").focus();
        return false;
    }
    else {
        $("#match-password").css("display","hidden");
    }
    if($('#input-mobile').val().length !== 10){
        $("#mobile").css("display","block");
        $("#input-mobile").focus();
        return false;
    }
    else {
        $("#mobile").css("display","hidden");
    }

});