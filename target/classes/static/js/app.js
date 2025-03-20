var Module = (function () {
    let currentAuthor = "";
    let currentBlueprintName = "";
    let currentBlueprintPoints = [];
    let currentAPI = apimock;
    let drawing = false;

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
            if (!Array.isArray(data)) {
                console.warn("No blueprints found or data is not an array.");
                data = [];
            }

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

    function createNewBlueprint() {
        if (!currentAuthor) {
            currentAuthor = $("#author").val().trim();
            if (!currentAuthor) {
                currentAuthor = prompt("Enter an author name before creating a blueprint:");
                if (!currentAuthor) {
                    alert("An author name is required.");
                    return;
                }
                $("#author").val(currentAuthor); // Actualiza el campo de entrada con el autor ingresado
            }
        }

        const blueprintName = prompt("Enter the name of the new blueprint:");
        if (!blueprintName) {
            alert("Blueprint name is required.");
            return;
        }

        currentBlueprintName = blueprintName;
        currentBlueprintPoints = [];
        $("#blueprint-name").text(blueprintName);

        const canvas = document.getElementById("blueprint-canvas");
        const ctx = canvas.getContext("2d");
        ctx.clearRect(0, 0, canvas.width, canvas.height); // Limpiar el canvas al crear uno nuevo
    }

    function openBlueprint(blueprintName) {
        if (!currentAuthor) {
            alert("Please select an author first.");
            return;
        }

        currentAPI.getBlueprintsByNameAndAuthor(currentAuthor, blueprintName, function (data) {
            if (!data || !data.points) {
                alert("Blueprint data is invalid.");
                return;
            }
            currentBlueprintName = blueprintName;
            currentBlueprintPoints = data.points;
            $("#blueprint-name").text(blueprintName);
            drawBlueprint();
        });
    }

    function drawBlueprint() {
        const canvas = document.getElementById("blueprint-canvas");
        const ctx = canvas.getContext("2d");
        ctx.clearRect(0, 0, canvas.width, canvas.height); // Limpiar canvas antes de redibujar
        ctx.beginPath();

        if (currentBlueprintPoints.length > 0) {
            ctx.moveTo(currentBlueprintPoints[0].x, currentBlueprintPoints[0].y);
            currentBlueprintPoints.forEach(point => {
                ctx.lineTo(point.x, point.y);
            });
            ctx.stroke();
            ctx.closePath();
        }
    }

    function startDrawing(event) {
        if (!currentBlueprintName) return;
        drawing = true;
        addPoint(event);
    }

    function draw(event) {
        if (!drawing) return;
        addPoint(event);
    }

    function stopDrawing() {
        drawing = false;
    }

    function addPoint(event) {
        const canvas = document.getElementById("blueprint-canvas");
        const rect = canvas.getBoundingClientRect();
        const x = event.clientX - rect.left;
        const y = event.clientY - rect.top;

        currentBlueprintPoints.push({ x, y });
        drawBlueprint();
    }

    function deleteBlueprint() {
        if (!currentAuthor) {
            alert("Please enter an author before deleting.");
            return;
        }
        if (!currentBlueprintName) {
            alert("No blueprint selected to delete.");
            return;
        }

        const confirmDelete = confirm(`Are you sure you want to delete "${currentBlueprintName}"?`);
        if (!confirmDelete) return;

        $.ajax({
            url: `/blueprints/${currentAuthor}/${currentBlueprintName}`,
            type: "DELETE",
            contentType: "application/json"
        }).done(() => {
            console.log(`Blueprint "${currentBlueprintName}" deleted successfully!`);
            alert(`Blueprint "${currentBlueprintName}" deleted successfully!`);

            // Limpiar canvas y resetear nombre
            currentBlueprintName = "";
            currentBlueprintPoints = [];
            $("#blueprint-name").text("None");

            const canvas = document.getElementById("blueprint-canvas");
            const ctx = canvas.getContext("2d");
            ctx.clearRect(0, 0, canvas.width, canvas.height);

            fetchBlueprints(); // Recargar lista de blueprints
        }).fail((jqXHR, textStatus, errorThrown) => {
            console.error("Error deleting blueprint:", jqXHR.status, textStatus, errorThrown);
            alert(`Error deleting blueprint. Status: ${jqXHR.status}, Message: ${jqXHR.responseText}`);
        });
    }

    function saveOrUpdateBlueprint() {
        if (!currentAuthor) {
            alert("Please enter an author before saving.");
            return;
        }
        if (!currentBlueprintName) {
            alert("No blueprint selected to save.");
            return;
        }

        const blueprint = {
            author: currentAuthor,
            name: currentBlueprintName,
            points: currentBlueprintPoints
        };

        $.ajax({
            url: `/blueprints/${currentAuthor}/${currentBlueprintName}`,
            type: "PUT",
            data: JSON.stringify(blueprint),
            contentType: "application/json"
        }).done(response => {
            console.log("Blueprint saved or updated successfully!");
            alert("Blueprint saved or updated successfully!");
            fetchBlueprints(); // Recargar lista de planos
        }).fail((jqXHR, textStatus, errorThrown) => {
            console.error("Error saving blueprint:", jqXHR.status, textStatus, errorThrown);
            alert("Error saving blueprint.");
        });
    }

    $(document).ready(function () {
        const canvas = document.getElementById("blueprint-canvas");
        canvas.addEventListener("pointerdown", startDrawing);
        canvas.addEventListener("pointermove", draw);
        canvas.addEventListener("pointerup", stopDrawing);
        canvas.addEventListener("pointerleave", stopDrawing);
    });

    return {
        updateAuthor: updateAuthor,
        openBlueprint: openBlueprint,
        toggleAPI: toggleAPI,
        createNewBlueprint: createNewBlueprint,
        saveOrUpdateBlueprint: saveOrUpdateBlueprint,
        deleteBlueprint: deleteBlueprint
    };
})();
