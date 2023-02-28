$(async function () {
    await showAdminPage();
})

let tableBody = document.querySelector('#tableBody')

async function showAdminPage() {
    console.log('Get admin page')
    fetch("/admin/api/admin", {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Referer': null
        }
    })
        .then((response) => {
            response.json()
        })
        .then(data => {
            data.forEach(user => {
                    console.log('users - ' + user)
                    let tr = document.createElement('tr')
                    tr.innerHTML = `
                                    <th>${user.id}</th>
                                    <td>${user.name}</td>
                                    <td>${user.lastname}</td>
                                    <td>${user.username}</td>
                                    <td>${user.roles.map(role => role.name.substring(5) + " ")}</td>
                                    <td>
                                        <button type="button" id = "btnEdit" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#editModal">
                                            Edit
                                        </button>
                                    </td>
                                    <td>
                                        <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal">
                                            Delete
                                        </button>
                                    </td>
`
                    tableBody.appendChild(tr)
                }
            )
        })
}
