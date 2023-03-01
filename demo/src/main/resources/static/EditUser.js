$(async function (id) {
    await editUser(id);
});

async function editUser(id){

    const form = document.forms["editUserForm"];
    $('#editUserRoles').empty();

    await allRoles().then(roles => {
        roles.forEach(role => {
            let option = new Option(role.name.substring(5), role.id);
            option.setAttribute("id", role.name);
            $('#editUserRoles').append(option);
        })
    })

    await showUserById(id).then(user => {
        $('#editUserId').val(user.id)
        $('#editUserName').val(user.name)
        $('#editUserLastname').val(user.lastname)
        $('#editUserPassword').val(user.password)
        $('#editUserUsername').val(user.username)

        user.roles.forEach(role => {console.log(role.name.substring(5));
            document.getElementById(role.name).selected = true;
        })
    })

    form.addEventListener('submit', editProcess);

    async function editProcess(event){
        event.preventDefault();

        let editUser = await userData(form);

        fetch("/admin/api/admin/" + id, {
            method: 'PATCH',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Referer': null
            },
            body: editUser
        }).then(() => {
            $('#editFormCloseButton').click();
            showUsersTable();
        })
    }
}