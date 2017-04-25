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
        var frame = FrameUtils.getActiveFrame(parent.document);
        frame.src = url;
    },

    refreshDashboard: function() {
        var frame = $(parent.document).find('#dashboardFrame')[0];
        frame.contentWindow.location.reload();
    },

    parentChanged: function() {
    	var form = $('#issueForm');
        var parent = form.find('#parent\\.id')[0];
        var visibility = parent.value == 0 ? 'visible' : 'hidden';
        form.find('#project\\.id')[0].style.visibility = visibility;
        form.find('#type\\.id')[0].style.visibility = visibility;
    },

    submitForm: function() {
        var form = $('#issueForm');
        form.submit();
    }
}
