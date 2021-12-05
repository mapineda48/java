




/**
 * https://stackoverflow.com/questions/3186246/do-i-need-to-detach-events-in-jquery-when-i-remove-elements
 */
const $body = $(document.body);
const template = initTemplate();
const app = createApp();
showLogin();
function showModalForm(opt) {
    const { template, post, put, data, onRes, onReq } = opt;
    const url = post || put;
    if (!url) {
        throw new Error("missing post or put");
    }
    const $el = render(template).get();
    const el = Array.from($el)[0];
    const modal = new bootstrap.Modal(el);
    el.addEventListener("hidden.bs.modal", () => {
        $el.remove();
    });
    $el.on("submit", function (e) {
        e.preventDefault();
        e.stopPropagation();
        const form = $(e.target);
        const obj = getDataForm(form);
        const ready = loading($el);
        const data = onReq ? onReq(obj) : obj;
        ajax({
            url,
            type: post ? "POST" : "PUT",
            data: JSON.stringify(data),
            onRes(err, res) {
                ready();
                onRes(el, modal, data, err, res);
            },
        });
    });
    if (data) {
        Object.entries(data).forEach(([key, val]) => {
            $el.find(`[name='${key}']`).val(val);
        });
    }
    $body.append($el);
    modal.show();
}
function createAdmin() {
    showModalForm({
        template: template.mAdmin,
        post: "/api/user/new",
        onReq(data) {
            return { ...data, id: Date.now(), type: "ADM" };
        },
        onRes(el, modal, user, err, res) {
            const data = err
                ? {
                    title: "No se pudo crear " + user.name,
                    message: err || "Desconocido",
                }
                : !res.id
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
    });
}
function showLogin() {
    const $login = $(template.login);
    $login.find("#create-account").on("click", () => {
        showAlert({
            title: "Aclaracion",
            message: "Instructor se que esto no estaba en los requerimientos, pero lo hago para facilitar el testeo",
            onHidden() {
                createAdmin();
            },
        });
    });
    $login.on("submit", function (e) {
        e.preventDefault();
        const $form = $(e.target);
        const { email, password } = getDataForm($form);
        const ready = loading($form);
        ajax({
            url: "/api/user/" + email + "/" + password,
            type: "GET",
            onRes(err, user) {
                if (err) {
                    showAlert({
                        title: "Error",
                        message: err || "Desconocido",
                    });
                }
                else {
                    console.log({ user });
                    $form.find("input").val("");
                    showAlert(!user.id
                        ? {
                            title: "Error",
                            message: "Contacte al Admin para que se le asigne un usuario",
                        }
                        : user.type !== "ADM"
                            ? {
                                title: "No Autorizado",
                                message: "Solo un usuario administrador puede acceder",
                            }
                            : {
                                title: "Notificacion",
                                message: "Accediste Correctamente",
                                onHidden() {
                                    showAdmin(user);
                                },
                            });
                }
                ready();
            },
        });
    });
    app($login);
}
function showAdmin(admin) {
    const $admin = $(template.admin);
    const $main = $admin.find("#workspace");
    const $btnCreate = $admin.find("#btn-create-record");
    const $header = $admin.find("header");
    const $dropDown = $header.find("#navbarDropdown");
    const $section = $header.find(".dropdown-menu").find(".dropdown-item");
    const $search = $header.find("form");
    const workspace = createApp($main);
    const [HOME, USER, LAPTOP] = Array.from($section).map((el) => $(el).text());
    const meta = {
        [USER]: {
            template: getTemplate("#user", $main),
            modal: template.mUser,
            baseURL: "/api/user/",
        },
        [LAPTOP]: {
            template: getTemplate("#laptop", $main),
            modal: template.mLaptop,
            baseURL: "/api/laptop/",
        },
    };
    const welcome = getTemplate("#welcome", $main);
    const loader = getTemplate("#loading", $main);
    const message = getTemplate("#some-message", $main);
    const records = getTemplate("#records", $main);
    let $welcome = render(welcome).set("name", admin.name).get();
    let section = "";
    const printRecords = (val) => {
        const data = Array.isArray(val) ? val : [val];
        if (!val || !data.length) {
            return render(message).set("message", "Sin Resultados").get();
        }
        const { template, modal, baseURL } = meta[section];
        const $records = data.map((record) => {
            const $record = render(template).entries(record).get();
            const isRecordAdmin = section === USER && record.id === admin.id;
            $record.find("#btn-delete").on("click", (e) => {
                showAlert({
                    title: `Eliminar ${section}`,
                    message: "¿Esta seguro?",
                    onConfirm() {
                        ajax({
                            url: baseURL + record.id,
                            type: "DELETE",
                            onRes(err, data) {
                                if (err) {
                                    showAlert({
                                        title: section,
                                        message: "No Eliminado",
                                    });
                                    return;
                                }
                                if (isRecordAdmin) {
                                    showLogin();
                                    return;
                                }
                                showAlert({
                                    title: section,
                                    message: "Eliminado",
                                    onHidden() {
                                        fetchRecords();
                                    },
                                });
                            },
                        });
                    },
                });
            });
            $record.find("#btn-edit").on("click", (e) => {
                showModalForm({
                    put: baseURL + "update",
                    template: modal,
                    data: record,
                    onReq(data) {
                        return { ...data, id: record.id };
                    },
                    onRes(el, modal, record, err, data) {
                        if (err) {
                            showAlert({
                                title: "Error",
                                message: "No se pudo actualizar",
                            });
                            return;
                        }
                        if (isRecordAdmin) {
                            admin = { ...data };
                            $welcome = render(welcome).set("name", admin.name).get();
                            if (admin.type !== "ADM") {
                                showLogin();
                                return;
                            }
                        }
                        fetchRecords();
                        modal.hide();
                    },
                });
            });
            return $record;
        });
        const $data = render(records).get();
        $data.children().children().append($records);
        return $data;
    };
    const fetchRecords = (val) => {
        const { baseURL } = meta[section];
        if (!baseURL && !val) {
            throw new Error("missing base url to this section");
        }
        const url = val || baseURL + "all";
        const ready = loading($admin);
        workspace($(loader));
        ajax({
            url,
            type: "GET",
            onRes(error, data) {
                if (error) {
                    const [jqXHR, status, err] = error;
                    console.log({ jqXHR: jqXHR, status: status, err });
                    const $message = render(message)
                        .set("message", err || "unhandler error")
                        .get();
                    workspace($message);
                }
                const $records = printRecords(data);
                workspace($records);
                ready();
            },
        });
    };
    const setSection = (val = $dropDown.text()) => {
        const current = val.trim();
        if (section === current)
            return;
        section = current;
        $dropDown.empty().append(section);
        const sectionsWithSearch = [USER, LAPTOP];
        if (sectionsWithSearch.includes(section)) {
            $search.find("input,button").removeAttr("style");
            $btnCreate.removeAttr("style");
        }
        else {
            $search.find("input,button").css("display", "none");
            $btnCreate.css("display", "none");
        }
        switch (section) {
            case HOME:
                workspace($welcome);
                break;
            case USER:
            case LAPTOP:
                fetchRecords();
                break;
            default:
                throw new Error(`not found section "${section}"`);
        }
    };
    $section.on("click", function (e) {
        setSection($(this).text());
    });
    $header.find("#logout").on("click", showLogin);
    $header.find("#form-search").on("submit", function (e) {
        e.preventDefault();
        let url = "/api/";
        switch (section) {
            case USER:
                url = url.concat("user/");
                break;
            case LAPTOP:
                url = url.concat("laptop/");
                break;
            default:
                throw new Error(`cant search "${section}"`);
        }
        const $form = $(this);
        const { id } = getDataForm($form);
        $form.find("input").val("");
        url = url.concat(id ? "id/" + id : "all");
        fetchRecords(url);
    });
    $btnCreate.on("click", () => {
        const { modal, baseURL } = meta[section];
        if (!baseURL) {
            throw new Error("missing base url to this section");
        }
        const url = baseURL + "new";
        showModalForm({
            post: url,
            template: modal,
            onReq(data) {
                return { ...data, id: Date.now() };
            },
            onRes(el, modal, record, err, data) {
                if (err) {
                    showAlert({
                        title: "Error",
                        message: "Fallo Crear",
                    });
                    return;
                }
                fetchRecords();
                modal.hide();
            },
        });
    });
    app($admin);
    setSection();
}
function createApp($root = $body) {
    let $current = null;
    return ($el) => {
        $current === null || $current === void 0 ? void 0 : $current.remove();
        $current = $el;
        $root.append($el);
    };
}
function showAlert(opt) {
    const { title, message, onConfirm, onHidden } = opt;
    const $alert = render(template.alert)
        .set("title", title)
        .set("message", message)
        .get();
    const el = Array.from($alert)[0];
    const modal = new bootstrap.Modal(el);
    el.addEventListener("hidden.bs.modal", function () {
        $alert.remove();
        if (onHidden)
            onHidden();
    });
    if (onConfirm) {
        $alert.find("#btn-action").on("click", onConfirm);
    }
    $body.append($alert);
    modal.show();
}
function loading($el) {
    const input = $el.find("input, button");
    $el.css("pointer-events", "none");
    input.attr("disabled", "true");
    return function () {
        input.removeAttr("disabled");
        $el.css("pointer-events", "");
    };
}
function getDataForm($form) {
    return Object.fromEntries($form.serializeArray().map(({ name, value }) => [name, value]));
}
function render(template) {
    let current = template;
    const render = {
        set: function (key, val) {
            current = current.replace(keyVar(key), val);
            return render;
        },
        setAll: function (key, val) {
            current = current.replaceAll(keyVar(key), val);
            return render;
        },
        entries(val) {
            Object.entries(val).forEach(([key, val]) => {
                this.set(key, val);
            });
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
function initTemplate() {
    return {
        admin: getTemplate("#admin"),
        login: getTemplate("#login"),
        alert: getTemplate("#alert"),
        mAdmin: getTemplate("#modal-create-admin"),
        mUser: getTemplate("#modal-create-user"),
        mLaptop: getTemplate("#modal-create-laptop"),
    };
}
function getTemplate(selector, $el = $body) {
    return $el.children(selector).remove().html();
}
function ajax(opt) {
    const { url, type, onRes, data } = opt;
    $.ajax({
        url,
        type,
        data,
        dataType: "JSON",
        contentType: "application/json",
        success(data) {
            onRes(null, data);
        },
        error(jqXHR, status, err) {
            console.log({ jqXHR: jqXHR, status: status, err: err });
            onRes("Ups...", null);
        },
    });
}
