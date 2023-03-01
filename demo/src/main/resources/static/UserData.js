const dbRoles = [{id: 1, name: "ROLE_ADMIN"}, {id: 2, name: "ROLE_USER"}];
async function userData (form) {
    let user = new FormData(form);
    let userRoles = [];
    for (let i = 0; i < form.roles.options.length; i++) {
        if (form.roles.options[i].selected) userRoles.push({
            id: dbRoles[i].id,
            name : dbRoles[i].name
        })
    }


    user.set("roles",JSON.stringify(userRoles))

    return JSON.stringify(Object.fromEntries(user))
        .replaceAll("\\", "")
        .replaceAll("\"[{", "[{")
        .replaceAll("}]\"", "}]");
}