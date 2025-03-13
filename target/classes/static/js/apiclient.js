var apiclient = (function () {
    return {
        getBlueprintsByAuthor: function (author, callback) {
            $.get(`/blueprints/${author}`)
                .done(data => callback(data))
                .fail(error => console.log("Error:", error));
        },

        getBlueprintsByNameAndAuthor: function (author, name, callback) {
            $.get(`/blueprints/${author}/${name}`)
                .done(data => callback(data))
                .fail(error => console.log("Error:", error));
        }
    };
})();
