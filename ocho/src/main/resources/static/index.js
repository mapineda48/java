



//
var body = $("body");
var template = {
    alert: getTemplate("#alert"),
    create: getTemplate("#modal-create"),
};
/**
 * Create Acount
 */
$("#create-account").on("click", showCreate);
/**
 * Login
 */
$("#form-login").on("submit", function (e) {
    e.preventDefault();
    var form = $(this);
    var _a = getDataForm(form), user = _a.user, password = _a.password;
    var stopLoading = loading(form);
    $.ajax({
        url: "/api/user/" + user + "/" + password,
        type: "GET",
        dataType: "JSON",
        contentType: "application/json",
        success: function (user) {
            stopLoading();
            form.find("input").val("");
            var fail = user.name === "NO DEFINIDO";
            var data = fail
                ? {
                    title: "Error",
                    message: "El usuario no esta definido,por favor registrese.",
                }
                : {
                    title: "Notificacion",
                    message: "Accediste Correctamente",
                };
            showAlert(data);
        },
        error: function (jqXHR, status, err) {
            stopLoading();
            console.log({ jqXHR: jqXHR, status: status, err: err });
            showAlert({
                title: "Error",
                message: err || "Desconocido",
            });
        },
    });
});
function showCreate() {
    var $el = render(template.create).get();
    var el = Array.from($el)[0];
    var modal = new bootstrap.Modal(el);
    el.addEventListener("hidden.bs.modal", function () {
        $el.detach();
    });
    $el.on("submit", function (e) {
        e.preventDefault();
        e.stopPropagation();
        var form = $(e.target);
        var data = getDataForm(form);
        var stopLoading = loading($el);
        $.ajax({
            url: "/api/user/new",
            type: "POST",
            dataType: "JSON",
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function (user) {
                var fail = !user.id;
                var data = fail
                    ? {
                        title: "Error",
                        message: "Fallo Registro",
                    }
                    : {
                        title: "Enahorabuena",
                        message: "Registrado Correctamente",
                    };
                el.addEventListener("hidden.bs.modal", function () {
                    showAlert(data);
                });
                modal.hide();
            },
            error: function (jqXHR, status, err) {
                stopLoading();
                console.log({ jqXHR: jqXHR, status: status, err: err });
                showAlert({
                    title: "No se pudo crear " + data.name,
                    message: err || "Desconocido",
                });
            },
        });
    });
    body.append($el);
    modal.show();
}
function showAlert(opt) {
    var $ = render(template.alert)
        .set("title", opt.title)
        .set("message", opt.message)
        .get();
    var el = Array.from($)[0];
    var modal = new bootstrap.Modal(el);
    el.addEventListener("hidden.bs.modal", function () {
        $.detach();
    });
    body.append($);
    modal.show();
}
function loading(el) {
    var input = el.find("input, button");
    el.css("pointer-events", "none");
    input.attr("disabled", "true");
    return function () {
        input.removeAttr("disabled");
        el.css("pointer-events", "");
    };
}
function getDataForm(form) {
    return Object.fromEntries(form.serializeArray().map(function (_a) {
        var name = _a.name, value = _a.value;
        return [name, value];
    }));
}
function render(template) {
    var current = template;
    var render = {
        set: function (key, val) {
            current = current.replace(keyVar(key), val);
            return render;
        },
        setAll: function (key, val) {
            current = current.replaceAll(keyVar(key), val);
            return render;
        },
        get: function () {
            return $(current);
        },
    };
    return render;
}
function keyVar(key) {
    return "${" + key + "}";
}
function getTemplate(selector) {
    return body.children(selector).detach().html();
}
