$(async function (id) {
    await showUserById(id);
});

async function showUserById(id) {
    let response = await fetch("/admin/api/admin/" + id,
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Referer': null
            }
        })
    return await response.json();
}