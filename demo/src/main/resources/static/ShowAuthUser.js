$(async function () {
    await authUser();
});

async function authUser() {

    fetch("/user/api/user", {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Referer': null
        }
    })
        .then((response) => response.json())
        .then(data => {
                console.log(data)
                $('#authUser').append(data.username);
                let roles = data.roles?.map(role => (" " + role.name.substring(5)));
                $('#authUserRoles').append(roles);
                let user = `$(
                <tr> 
                    <td>${data.id}</td>
                    <td>${data.name}</td>
                    <td>${data.lastname}</td>
                    <td>${data.username}</td>
                    <td>${roles}</td>
                </tr>    
                )`;
                $('#authUserPanel').append(user);
            }
        )
}