FrameUtils = {
    resizeHeight(frame) {
        frame.style.height = frame.contentWindow.document.body.scrollHeight + 'px';
    },

    getActiveFrame(doc) {
        var frames = doc.getElementsByTagName('iframe');
        for (var i = 0; i < frames.length; i++) {
            var frame = frames[i];
            if (frame.selected == true) {
                return frame;
            }
        }
    },

    setActiveFrame(id) {
        var frames = document.getElementsByTagName('iframe');
        for (var i = 0; i < frames.length; i++) {
            var frame = frames[i];
            frame.selected = frame.id == id;
        }
    },

    showUrl: function(url) {
        var frame = FrameUtils.getActiveFrame(parent.document);
        frame.src = url;
    },

    toggleFrames(frame1Id, frame2Id, buttonId) {
        var frame1 = document.getElementById(frame1Id);
        var frame2 = document.getElementById(frame2Id);
        var button = document.getElementById(buttonId);

        var currentSize = frame2.style.height;
        if (currentSize == "" || currentSize == "0px") {
            frame1.style.height = "450px";
            frame2.style.height = "450px";
            button.value = "Hide";
        } else {
        	frame1.style.height = "900px";
            frame2.style.height = "0px";
            button.value = "Restore";
        }
    }
}
