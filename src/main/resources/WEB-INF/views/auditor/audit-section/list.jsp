<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
    <acme:list-column code="auditor.auditSection.list.label.name" path="name" width="35%"/>
    <acme:list-column code="auditor.auditSection.list.label.notes" path="notes" width="40%"/>
    <acme:list-column code="auditor.auditSection.list.label.hours" path="hours" width="10%"/>
    <acme:list-column code="auditor.auditSection.list.label.kind" path="kind" width="15%"/>
</acme:list>

<acme:button code="auditor.auditSection.list.button.create" action="/auditor/audit-section/create?auditReportId=${auditReportId}"/>