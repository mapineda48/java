let isLoading = false;
const urlClient = "/api/Client/all";
let clients = "";
const urlOrtopedic = "/api/Ortopedic/all";
let ortopedics = "";
const amountCategorys = 20;
const baseUrl = "/api/Reservation/";
let _onunmoun = null;
const body = $("body");
const main = $("#state");
const tLoading = getTemplate("#loading");
const tMessage = getTemplate("#message");
const tRecords = getTemplate("#records");
const tRecord = getTemplate("#record");
const tConfirm = getTemplate("#modal-confirm");
const tAlert = getTemplate("#modal-alert");
const tForm = getTemplate("#modal-form");
const tStatus = getTemplate("#modal-status");
const tSearchDates = getTemplate("#search-date");
const tReportClient = getTemplate("#report-client");
const tBtnOpenModal = getTemplate("#btn-open-modal");
let isLoadingClient = false;
body.find("#fetch-client").on("click", (event) => {
    event.preventDefault();
    if (isLoadingClient)
        return;
    isLoadingClient = true;
    const url = baseUrl + "report-clients";
    $.ajax({
        url,
        type: "GET",
        dataType: "JSON",
        contentType: "application/json",
        success(data) {
            isLoadingClient = false;
            showClients(data);
        },
        error(jqXHR, status, err) {
            isLoadingClient = false;
            console.log({ jqXHR, status, err });
            showAlert({
                title: "Error al obtener el reporte",
                description: err || "Desconocido",
            });
        },
    });
});
body.find("#fetch-date").on("click", (e) => {
    e.preventDefault();
    if (isLoading)
        return;
    showFormDate({
        onConfirm(data) {
            loading();
            const url = baseUrl + `report-dates/${data.start}/${data.end}`;
            $.ajax({
                url,
                type: "GET",
                dataType: "JSON",
                contentType: "application/json",
                success(data) {
                    printRecords(data);
                },
                error(jqXHR, status, err) {
                    console.log({ jqXHR, status, err });
                    showAlert({
                        title: "Error al obtener el reporte",
                        description: err || "Desconocido",
                    });
                },
            });
        },
    });
});
let isLoadingStatus = false;
body.find("#fetch-status").on("click", (event) => {
    event.preventDefault();
    if (isLoadingStatus)
        return;
    isLoadingStatus = true;
    const url = baseUrl + "report-status";
    $.ajax({
        url,
        type: "GET",
        dataType: "JSON",
        contentType: "application/json",
        success(data) {
            isLoadingStatus = false;
            showStatus(data);
        },
        error(jqXHR, status, err) {
            isLoadingStatus = false;
            console.log({ jqXHR, status, err });
            showAlert({
                title: "Error al obtener el reporte",
                description: err || "Desconocido",
            });
        },
    });
});
body.find("#btn-create").on("click", () => {
    showForm({
        title: "Cliente",
        label: "Crear",
        onConfirm(data) {
            const url = baseUrl + "save";
            $.ajax({
                url,
                type: "POST",
                dataType: "JSON",
                contentType: "application/json",
                data: JSON.stringify(data),
                success() {
                    fetchRecords();
                },
                error(jqXHR, status, err) {
                    console.log({ jqXHR, status, err });
                    showAlert({
                        title: "No se pudo crear " + data.name,
                        description: err || "Desconocido",
                    });
                },
            });
        },
    });
});
body.find("#form-search").on("submit", function (event) {
    event.preventDefault();
    const { id } = Object.fromEntries($(this)
        .serializeArray()
        .map((pair) => [pair.name, pair.value]));
    fetchRecords(id);
});
fetchRecords();
function fetchRecords(id) {
    loading();
    const url = id ? baseUrl + id : baseUrl + "all";
    $.ajax({
        url,
        dataType: "JSON",
        contentType: "application/json",
        type: "GET",
        success(data) {
            if (!id) {
                printRecords(data);
            }
            else {
                printRecords(data ? [data] : []);
            }
        },
        error(jqXHR, status, err) {
            message(err);
        },
    });
}
function printRecords(res) {
    unmount();
    if (!res || !res.length) {
        message("Sin Resultados");
        return;
    }
    const records = $(tRecords);
    const clearEvents = [];
    res.forEach((res) => {
        const record = render(tRecord)
            .set("client", res.client.name)
            .set("ortopedic", res.ortopedic.name)
            .set("startDate", formatDate(res.startDate))
            .set("devolutionDate", formatDate(res.devolutionDate))
            .set("score", res.score || "None")
            .set("status", res.status)
            .get();
        const onDelete = (event) => {
            const btn = event.currentTarget;
            btn.setAttribute("disabled", true);
            showConfirm({
                title: "Eliminar",
                description: "¿Esta seguro?",
                onConfirm() {
                    const url = baseUrl + res.idReservation;
                    $.ajax({
                        url,
                        type: "DELETE",
                        dataType: "JSON",
                        contentType: "application/json",
                        success() {
                            fetchRecords();
                        },
                        error(jqXHR, status, err) {
                            console.log({ jqXHR, status });
                            message(err || "Desconocido");
                        },
                    });
                },
                onEnd() {
                    btn.removeAttribute("disabled");
                },
            });
        };
        const onEdit = (event) => {
            const btn = event.currentTarget;
            btn.setAttribute("disabled", true);
            showForm({
                title: "Mensaje",
                label: "Editar",
                onConfirm(data) {
                    const url = baseUrl + "update";
                    $.ajax({
                        url,
                        type: "PUT",
                        dataType: "JSON",
                        contentType: "application/json",
                        data: JSON.stringify({ idReservation: res.idReservation, ...data }),
                        success() {
                            fetchRecords();
                        },
                        error(jqXHR, status, err) {
                            console.log({ jqXHR, status, err });
                            showAlert({
                                title: "No se pudo editar",
                                description: err || "Desconocido",
                            });
                        },
                    });
                },
                onEnd() {
                    btn.removeAttribute("disabled");
                },
            });
        };
        const btnDelete = record.find("#btn-delete");
        const btnEdit = record.find("#btn-edit");
        btnDelete.on("click", onDelete);
        btnEdit.on("click", onEdit);
        clearEvents.push(() => {
            btnDelete.off("click", onDelete);
            btnEdit.off("click", onDelete);
        });
        records.children().children().append(record);
    });
    main.append(records);
    onUnmount(() => {
        clearEvents.forEach((cb) => cb());
    });
}
function formatDate(date) {
    return new Date(date).toISOString().split("T")[0];
}
function message(message) {
    unmount();
    const el = render(tMessage).set("message", message).get();
    main.append(el);
}
function loading() {
    unmount();
    isLoading = true;
    main.append(tLoading);
    onUnmount(() => {
        isLoading = false;
    });
}
function fetchList(onSuccess) {
    if (isLoading || clients || ortopedics)
        return;
    loading();
    $.ajax({
        url: urlClient,
        dataType: "JSON",
        contentType: "application/json",
        type: "GET",
        success(data) {
            if (!data.length) {
                message("Por favor primero agregue clientes");
                return;
            }
            if (data.length > amountCategorys) {
                data.length = amountCategorys;
            }
            clients = data.map((val) => `<option value="${val.idClient}">${val.name}</option>`);
            onSuccess();
            fetchRecords();
        },
        error(jqXHR, status, err) {
            message(err);
        },
    });
    $.ajax({
        url: urlOrtopedic,
        dataType: "JSON",
        contentType: "application/json",
        type: "GET",
        success(data) {
            if (!data.length) {
                message("Por favor primero agregue Ortopedics");
                return;
            }
            if (data.length > amountCategorys) {
                data.length = amountCategorys;
            }
            ortopedics = data.map((val) => `<option value="${val.id}">${val.name}</option>`);
            onSuccess();
        },
        error(jqXHR, status, err) {
            message(err);
        },
    });
}
function showForm(opt) {
    if (!clients || !ortopedics) {
        fetchList(() => {
            showForm(opt);
        });
        return;
    }
    const id = "Form" + Date.now();
    const modal = render(tForm)
        .setAll("id", id)
        .set("title", opt.title)
        .set("label", opt.label)
        .set("clients", clients)
        .set("ortopedics", ortopedics)
        .get();
    const btn = render(tBtnOpenModal).set("id", id).get();
    const form = modal.find("form");
    const btnCancel = modal.find("#app-modal-cancel");
    const onSucess = (event) => {
        event.preventDefault();
        event.stopPropagation();
        if (opt.onConfirm) {
            const data = Object.fromEntries(form.serializeArray().map((pair) => [pair.name, pair.value]));
            opt.onConfirm({
                ...data,
                startDate: formatDate(data.startDate),
                devolutionDate: formatDate(data.devolutionDate),
                client: { idClient: parseInt(data.client) },
                ortopedic: { id: parseInt(data.ortopedic) },
            });
            btnCancel.trigger("click");
        }
    };
    const onCancel = (event) => {
        if (event) {
            event.stopPropagation();
        }
        if (opt.onCancel)
            opt.onCancel();
        form.off("submit", onSucess);
        btnCancel.off("click", onCancel);
        setTimeout(() => {
            modal.detach();
            if (opt.onEnd)
                opt.onEnd();
        }, 200);
    };
    form.on("submit", onSucess);
    btnCancel.on("click", onCancel);
    body.append(modal);
    body.append(btn);
    btn.trigger("click");
    btn.detach();
}
function showFormDate(opt) {
    const id = "Form" + Date.now();
    console.log(tSearchDates);
    const modal = render(tSearchDates).setAll("id", id).get();
    const btn = render(tBtnOpenModal).set("id", id).get();
    const form = modal.find("form");
    const btnCancel = modal.find("#app-modal-cancel");
    const onSucess = (event) => {
        event.preventDefault();
        event.stopPropagation();
        if (opt.onConfirm) {
            const data = Object.fromEntries(form.serializeArray().map((pair) => [pair.name, pair.value]));
            opt.onConfirm({
                ...data,
                start: formatDate(data.start),
                end: formatDate(data.end),
            });
            btnCancel.trigger("click");
        }
    };
    const onCancel = (event) => {
        if (event) {
            event.stopPropagation();
        }
        if (opt.onCancel)
            opt.onCancel();
        form.off("submit", onSucess);
        btnCancel.off("click", onCancel);
        setTimeout(() => {
            modal.detach();
            if (opt.onEnd)
                opt.onEnd();
        }, 200);
    };
    form.on("submit", onSucess);
    btnCancel.on("click", onCancel);
    body.append(modal);
    body.append(btn);
    btn.trigger("click");
    btn.detach();
}
function showClients(data) {
    const id = "Client" + Date.now();
    const clients = data
        .map(({ total, client }) => {
        return `<div class="mb-3"><p><strong>${client.name}  :</strong>  ${total}</p></div>`;
    })
        .join("");
    const modal = render(tReportClient)
        .setAll("id", id)
        .set("clients", clients)
        .get();
    const btn = render(tBtnOpenModal).set("id", id).get();
    const btnSucess = modal.find("#app-modal-sucess");
    const onSucess = (event) => {
        if (event) {
            event.stopPropagation();
        }
        modal.off("click", onSucess);
        btnSucess.off("click", onSucess);
        setTimeout(() => {
            modal.detach();
        }, 200);
    };
    modal.on("click", onSucess);
    btnSucess.on("click", onSucess);
    body.append(modal);
    body.append(btn);
    btn.trigger("click");
    btn.detach();
}
function showStatus(opt) {
    const id = "Status" + Date.now();
    const modal = render(tStatus)
        .setAll("id", id)
        .set("cancelled", opt.cancelled)
        .set("completed", opt.completed)
        .get();
    const btn = render(tBtnOpenModal).set("id", id).get();
    const btnSucess = modal.find("#app-modal-sucess");
    const onSucess = (event) => {
        if (event) {
            event.stopPropagation();
        }
        modal.off("click", onSucess);
        btnSucess.off("click", onSucess);
        setTimeout(() => {
            modal.detach();
        }, 200);
    };
    modal.on("click", onSucess);
    btnSucess.on("click", onSucess);
    body.append(modal);
    body.append(btn);
    btn.trigger("click");
    btn.detach();
}
function showAlert(opt) {
    const id = "Alert" + Date.now();
    const modal = render(tAlert)
        .setAll("id", id)
        .set("title", opt.title)
        .set("description", opt.description)
        .get();
    const btn = render(tBtnOpenModal).set("id", id).get();
    const btnSucess = modal.find("#app-modal-sucess");
    const onSucess = (event) => {
        if (event) {
            event.stopPropagation();
        }
        if (opt.onConfirm)
            opt.onConfirm();
        modal.off("click", onSucess);
        btnSucess.off("click", onSucess);
        setTimeout(() => {
            modal.detach();
            if (opt.onEnd)
                opt.onEnd();
        }, 200);
    };
    modal.on("click", onSucess);
    btnSucess.on("click", onSucess);
    body.append(modal);
    body.append(btn);
    btn.trigger("click");
    btn.detach();
}
function showConfirm(opt) {
    const id = "Modal" + Date.now();
    const modal = render(tConfirm)
        .setAll("id", id)
        .set("title", opt.title)
        .set("description", opt.description)
        .get();
    const btn = render(tBtnOpenModal).set("id", id).get();
    const btnConfirm = modal.find("#app-modal-confirm");
    const btnCancel = modal.find("#app-modal-cancel");
    const onConfirm = (event) => {
        event.stopPropagation();
        if (opt.onConfirm)
            opt.onConfirm();
        onCancel();
    };
    const onCancel = (event) => {
        if (event) {
            event.stopPropagation();
        }
        if (opt.onCancel)
            opt.onCancel();
        modal.off("click", onCancel);
        btnConfirm.off("click", onConfirm);
        btnCancel.off("click", onCancel);
        setTimeout(() => {
            modal.detach();
            if (opt.onEnd)
                opt.onEnd();
        }, 200);
    };
    modal.on("click", onCancel);
    btnConfirm.on("click", onConfirm);
    btnCancel.on("click", onCancel);
    body.append(modal);
    body.append(btn);
    btn.trigger("click");
    btn.detach();
}
function render(template) {
    let current = template;
    const render = {
        set(key, val) {
            current = current.replace(keyVar(key), val);
            return render;
        },
        setAll(key, val) {
            current = current.replaceAll(keyVar(key), val);
            return render;
        },
        get() {
            return $(current);
        },
    };
    return render;
}
function keyVar(key) {
    return "${" + key + "}";
}
function unmount() {
    if (_onunmoun)
        _onunmoun();
    clear();
}
function onUnmount(cb) {
    _onunmoun = cb;
}
function clear() {
    main.children().detach();
}
function getTemplate(selector) {
    return main.children(selector).detach().html();
}
