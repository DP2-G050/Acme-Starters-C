<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.milestone.form.label.title" path="title"
		readonly="true" />
	<acme:form-textbox code="any.milestone.form.label.achievements"
		path="achievements" readonly="true" />
	<acme:form-textbox code="any.milestone.form.label.effort" path="effort"
		readonly="true" />
	<acme:form-textbox code="any.milestone.form.label.kind" path="kind"
		readonly="true" />
</acme:form>