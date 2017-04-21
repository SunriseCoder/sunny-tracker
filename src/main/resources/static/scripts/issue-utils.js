IssueUtils = {
    showIssue: function(id) {
        var url = '/issue/edit/' + id + '.html';
        this._setUrl(url);
    },

    createRootIssue: function(project, type) {
        var url = '/issue/create/' + project + '/' + type;
        this._setUrl(url);
    },

    createSubIssue: function(project, type, parent) {
        var url = '/issue/create/' + project + '/' + type + '/' + parent;
        this._setUrl(url);
    },

    _setUrl: function(url) {
        var frame = $(parent.document).find('#toolFrame')[0];
        frame.src = url;
    },

    refreshDashboard: function() {
        var frame = $(parent.document).find('#dashboardFrame')[0];
        frame.contentWindow.location.reload();
    }
}
