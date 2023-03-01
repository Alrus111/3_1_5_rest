$(async function () {
    await showUsersTable();
});

async function showUsersTable() {
    const table = $('#allUserTable').empty();
    fetch("/admin/api/admin", {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Referer': null
        }
    })
        .then((response) => response.json())
        .then(data => {
            data.forEach(user => {
                let allUsersTable = `$(
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.lastname}</td>
                        <td>${user.username}</td>
                        <td>${user.roles.map(role => " " + role.name.substring(5))}</td>
                        <td>
                             <button type="button" class="btn btn-success btn btn-info modal-open" id="editbutton"
                             onclick="editUser(${user.id})" data-toggle="modal" data-target="#modalEdit">
                             Edit
                             </button>
                        </td>     
                        <td>
                            <button type="button" class="btn btn-success btn btn-danger modal-open" id="deleteButton"
                             onclick="deleteUser(${user.id})" data-toggle="modal" data-target="#modalDelete">
                             Delete
                             </button>
                        </td>
                    </tr>
                )`;
                table.append(allUsersTable);
            })
        })
}

