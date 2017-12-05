function userAutocomplete(startPath, id) {
    $("#" + id).autocomplete({
        minLength: 1,
        source: function (request, response) {
            var usernameValue = $("#" + id).val();
            $.get(startPath + "/user/getUsernames?username=" + usernameValue, function(data) {
                response(data);
            });
        }
    });
}