$(async function() {
    await showUserPage();
})
let id = document.querySelector('#id')
let name = document.querySelector('#nameUser')
let lastname = document.querySelector('#lastnameUser')
let username = document.querySelector('#usernameUser')
let roleUser = document.querySelector('#roleUser')
async function showUserPage() {
    console.log('Get user page')
    fetch("/user/api/user", {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Referer': null
        }
    })
        .then((response) => response.json())
        .then(user => {
            console.log('user - ' + user)
            id.textContent = user.id
            name.textContent = user.name;
            lastname.textContent = user.lastname
            username.textContent = user.username
            roleUser.textContent = user.roles[0].name
        })
}
