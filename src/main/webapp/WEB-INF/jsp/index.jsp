<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
    <title>Sunny Tracker</title>
    <script src="/scripts/frame-utils.js"></script>
    <style>
        #dashboardFrame {
            width: 800px;
        }

        #toolFrame {
            width: 500px;
            height: 500px;
            position: fixed;
            right: 10px;
            top: 50px;
        }
    </style>
</head>
<body>
    <iframe id="dashboardFrame" src="dashboard" frameborder="0" height="100%" scrolling="no" onload="FrameUtils.resizeHeight(this);"></iframe>
    <iframe id="toolFrame" frameborder="0" onload="FrameUtils.resizeHeight(this);"></iframe>
</body>

</html>
