$(document).ready(function(){
    $(".chat_people").on('click',function(){
        $(this).find("form").submit();
    });
});