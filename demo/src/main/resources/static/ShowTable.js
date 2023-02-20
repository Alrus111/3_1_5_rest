$(async function () {
    await fillingTable()
    // await fillingUser()
})

let id = document.querySelector('#id')
let name = document.querySelector('#nameUser')
let lastname = document.querySelector('#lastnameUser')
let username = document.querySelector('#usernameUser')
let roleUser = document.querySelector('#roleUser')
let tableBody = document.querySelector('#tableBody')

async function fillingUser() {
    console.log('Get user page')
    try {
        const response = await fetch("/admin/userPage", {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Referer': null
            }
        })
        let result = await response.json()
        for (let i = 0; i < result.length; i++) {
            let user = result[i]

            id.textContent = user.id
            name.textContent = user.name;
            lastname.textContent = user.lastname
            username.textContent = user.username
            roleUser.textContent = user.roles[0].name
        }
    } catch (e) {
        console.error(e)
    }
}

fillingUser()

async function fillingTable() {
    console.log('Get admin page')
    fetch("/admin/all", {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Referer': null
        }
    })
        .then(response => response.json())
        .then(users => {
            console.log(users)
            for (let i = 0; i < users.length; i++) {
                let tr = document.createElement('tr')
                let user = users[i]

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
        })
        // .then(editUser(1))
}


// fillingTable()
//
// editUser(1)


