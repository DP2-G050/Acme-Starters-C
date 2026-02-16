<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="spokesperson.milestone.form.label.title"
		path="title" />
	<acme:form-textbox
		code="spokesperson.milestone.form.label.achievements"
		path="achievements" />
	<acme:form-textbox code="spokesperson.milestone.form.label.effort"
		path="effort" />
	<acme:form-textbox code="spokesperson.milestone.form.label.kind"
		path="kind" />

	<jstl:if test="${_command == 'create'}">
		<acme:submit code="master.button.create"
			action="/spokesperson/milestone/create" />
	</jstl:if>

	<jstl:if test="${_command == 'update'}">
		<acme:submit code="master.button.update"
			action="/spokesperson/milestone/update" />
		<acme:submit code="master.button.delete"
			action="/spokesperson/milestone/delete" />
	</jstl:if>
</acme:form>