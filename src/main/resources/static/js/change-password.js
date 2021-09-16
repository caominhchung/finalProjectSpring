$(document).ready(function () {
    $("#changePasswordForm").validate({
        rules: {
            userName: {
                required: true,
                minlength: 5,
                maxlength: 50
            },
            oldPassword: {
                required: true,
                minlength: 8,
                maxlength: 20

            },
            newPassword: {
                required: true,
                minlength: 8,
                maxlength: 30,
                notEqual: function(){return $('#oldPassword').val()}
            },
            confirmPassword: {
                required: true,
                minlength: 8,
                maxlength: 30,
                equalTo: "#newPassword"
            }
        },
        message: {
            userName: {
                required: "Input Your Username",
                minlength: "Username must be at least 5 characters",
                maxlength: "Username must be at less than 50 characters"
            },
            oldPassword: {
                required: "Input Your Password",
                minlength: "Password must be at least 8 characters",
                maxlength: "Password must be at less than 30 characters"
            },
            newPassword: {
                required: "Input Your Password",
                minlength: "Password must be at least 8 characters",
                maxlength: "Password must be at less than 30 characters",
                notEqual: "New Password must be difference old password"
            },
            confirmPassword: {
                required: "Input Your RePassword",
                minlength: "RePassword must be at least 8 characters",
                maxlength: "RePassword must be at less than 30 characters",
                equalTo: "Please enter the same password as above"
            }
        }
    });
    jQuery.validator.addMethod("notEqual", function(value, element, param) {
        return this.optional(element) || value != param;
    }, "Password must be difference old password");
});
