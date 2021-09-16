window.onload = function () {
    const form = document.getElementById("login-form");
    const user = document.getElementById("userName");
    const password = document.getElementById("password");
    const required = "(*)";


    form.addEventListener("submit", (e) => {
        e.preventDefault();

        if (validateLogin()) {
            e.currentTarget.submit();
        }
    });

    function validateLogin() {
        const userValue = user.value.trim();
        const passwordValue = password.value.trim();

        console.log(user);
        if (userValue === '') {

            document.getElementById("user-required").innerText = required;
            user.focus();
            return false;
        }

        if (passwordValue === '') {

            document.getElementById("password-required").innerText = required;
            password.focus();
            return false;
        }
        return true;
    }
}

function getUrlParameter(sParam) {
    var sPageURL = window.location.search.substring(1),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return typeof sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
        }
    }
    return false;
};

var isChangePassword = getUrlParameter("changePassword");

if(isChangePassword!=""){
    toastr.success('Change password successful!', 'Notification');
}