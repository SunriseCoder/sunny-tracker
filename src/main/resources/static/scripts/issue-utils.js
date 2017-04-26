IssueUtils = {
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

    submitForm: function(formId) {
        var form = $('#' + formId);
        var radioButtonGroups = form.find('span[id]');
        var visibleRadioButtonGrous = radioButtonGroups.filter(function() {
            return $(this).css("visibility") == "visible";
        });
        var emptyGroups = visibleRadioButtonGrous.filter(function() {
            return $(this).find('input[type=radio]:checked').length == 0;
        });

        if (emptyGroups.length > 0) {
            alert('Some of the Radio Button Groups were not set');
        } else {
            form.submit();
        }
    }
}
