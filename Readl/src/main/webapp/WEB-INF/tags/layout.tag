<%@tag description="layout" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<html>
    <t:head/>
    <body>
        <t:header/>
        <div class="wrapper">
            <jsp:doBody/>
        </div>
        <t:footer/>
    </body>
</html>
