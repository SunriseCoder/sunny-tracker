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
    }
}
