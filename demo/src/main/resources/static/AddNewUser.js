$(async function () {
    await addNewUser();
});

async function addNewUser(){

    allRoles().then(roles => {
        roles.forEach(role => {
            let option = new Option(role.name.substring(5), role.id);
            $('#newUserRoles').append(option);
        })
    })

    const form = document.forms["newUserForm"];
    form.addEventListener('submit', sendData)

    async function sendData(event) {
        event.preventDefault();

        let newUser = await userData(form);

        fetch("/admin/api/admin", {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Referer': null
            },
            body: newUser
        }).then(() => {
            document.forms["newUserForm"].reset();
            showUsersTable();
            $('#allUsersTable').click();
        })
    }
}