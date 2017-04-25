<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
    <title>Sunny Tracker</title>

    <link rel="stylesheet" href="/styles/dashboard.css" />

    <script src="/scripts/frame-utils.js"></script>

    <script>
        function pageLoaded() {
            document.getElementById('radio1').click();
        }
    </script>
</head>
<body onload="pageLoaded();">
    <iframe id="dashboardFrame" src="dashboard" frameborder="0" height="100%" scrolling="no" onload="FrameUtils.resizeHeight(this);"></iframe>

    <div class="toolFrame" style="top: 0px;">
        <div><input id="radio1" type="radio" name="frame" onclick="FrameUtils.setActiveFrame('toolFrame1');" />Frame 1</div>
        <iframe id="toolFrame1" frameborder="0" onload="FrameUtils.resizeHeight(this);"></iframe>
    </div>

    <div class="toolFrame" style="top: 480px;">
        <div><input id="radio2" type="radio" name="frame" onclick="FrameUtils.setActiveFrame('toolFrame2');" />Frame 2</div>
        <iframe id="toolFrame2" frameborder="0" onload="FrameUtils.resizeHeight(this);"></iframe>
    </div>
</body>

</html>