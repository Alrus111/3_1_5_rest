$(async function () {
    await allRoles();
});

async function allRoles() {

    let response = await fetch("/admin/api/roles",
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Referer': null
            }
        })

    return await response.json();
}
