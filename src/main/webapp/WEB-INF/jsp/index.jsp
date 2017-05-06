<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="appRoot" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Sunny Tracker</title>

    <link rel="stylesheet" href="${appRoot}/styles/dashboard.css" />

    <script src="${appRoot}/scripts/frame-utils.js"></script>

    <script>
        function pageLoaded() {
            document.getElementById('radioToolFrame1').click();
        }
    </script>
</head>
<body onload="pageLoaded();FrameUtils.toggleFrames('toolFrame1','toolFrame2','toolFrame2Button');">
    <jsp:include page="includes/header.jsp" />

    <iframe id="dashboardFrame" src="dashboard" frameborder="0" height="100%" scrolling="no"
            onload="FrameUtils.resizeHeight(this);"></iframe>

    <div class="toolFrame" style="top: 0px;">
        <span>
            <input id="radioToolFrame1" type="radio" name="frame" onclick="FrameUtils.setActiveFrame('toolFrame1');" />
            <label for="radioToolFrame1">Tool Frame 1</label>
        </span>
        <iframe id="toolFrame1" frameborder="0"></iframe>
    </div>

    <div id="divToolFrame2" class="toolFrame" style="bottom: 0px;">
        <span>
            <input id="radioToolFrame2" type="radio" name="frame" onclick="FrameUtils.setActiveFrame('toolFrame2');" />
            <label for="radioToolFrame2">Tool Frame 2</label>
            <input id="toolFrame2Button" type="button" onclick="FrameUtils.toggleFrames('toolFrame1','toolFrame2','toolFrame2Button');" />
        </span>
        <iframe id="toolFrame2" frameborder="0"></iframe>
    </div>
</body>

</html>
