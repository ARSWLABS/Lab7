var Module = (function () {
    let currentAuthor = "";
    let currentAPI = apimock; // Cambia entre apimock y apiclient

    function toggleAPI() {
        currentAPI = (currentAPI === apimock) ? apiclient : apimock;
        alert("API switched to " + (currentAPI === apimock ? "Mock" : "Real"));
    }

    function updateAuthor() {
        const authorInput = $("#author").val().trim();
        if (authorInput === "") {
            alert("Please enter an author name.");
            return;
        }
        currentAuthor = authorInput;
        fetchBlueprints();
    }

    function fetchBlueprints() {
        currentAPI.getBlueprintsByAuthor(currentAuthor, function (data) {
            const tableBody = $("#blueprints-table");
            tableBody.empty();
            let totalPoints = 0;
    
            if (data.length > 0) {
                data.forEach(blueprint => {
                    totalPoints += blueprint.points.length;
                    const row = `<tr>
                        <td>${blueprint.name}</td>
                        <td>${blueprint.points.length}</td>
                        <td><button class="btn btn-primary" onclick="Module.openBlueprint('${blueprint.name}')">Open</button></td>
                    </tr>`;
                    tableBody.append(row);
                });
    
                const totalRow = `<tr>
                    <td colspan="2" style="font-weight: bold;">Total de puntos:</td>
                    <td style="font-weight: bold;">${totalPoints}</td>
                </tr>`;
                tableBody.append(totalRow);
    
            } else {
                alert("No blueprints found for this author.");
            }
        });
    }
    

    function openBlueprint(blueprintName) {
        currentAPI.getBlueprintsByNameAndAuthor(currentAuthor, blueprintName, function (data) {
            $("#blueprint-name").text(blueprintName);
            drawBlueprint(data);
        });
    }

    function drawBlueprint(blueprint) {
        const canvas = document.getElementById("blueprint-canvas");
        const ctx = canvas.getContext("2d");

        ctx.clearRect(0, 0, canvas.width, canvas.height);
        ctx.beginPath();

        if (blueprint.points.length > 0) {
            ctx.moveTo(blueprint.points[0].x, blueprint.points[0].y);
            blueprint.points.forEach(point => {
                ctx.lineTo(point.x, point.y);
            });
        }

        ctx.strokeStyle = "blue";
        ctx.lineWidth = 2;
        ctx.stroke();
    }

    function createNewBlueprint() {
        // Borrar el canvas actual
        const canvas = document.getElementById("blueprint-canvas");
        const ctx = canvas.getContext("2d");
        ctx.clearRect(0, 0, canvas.width, canvas.height);

        // Solicitar el nombre del nuevo blueprint
        const blueprintName = prompt("Enter the name of the new blueprint:");
        if (blueprintName) {
            currentBlueprintName = blueprintName;
            $("#blueprint-name").text(blueprintName);
            alert(`New blueprint "${blueprintName}" created. You can start drawing.`);
        } else {
            alert("Blueprint name is required.");
        }
    }

    function saveOrUpdateBlueprint() {
        alert("Save/Update Blueprint functionality to be implemented.");
        // Aquí puedes agregar la lógica para guardar o actualizar un blueprint
    }

    function deleteBlueprint() {
        alert("Delete Blueprint functionality to be implemented.");
        // Aquí puedes agregar la lógica para eliminar un blueprint
    }

    return {
        updateAuthor: updateAuthor,
        openBlueprint: openBlueprint,
        toggleAPI: toggleAPI,
        createNewBlueprint: createNewBlueprint,
        saveOrUpdateBlueprint: saveOrUpdateBlueprint,
        deleteBlueprint: deleteBlueprint
    };
})();