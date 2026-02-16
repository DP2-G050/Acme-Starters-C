<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="spokesperson.campaign.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="spokesperson.campaign.form.label.name" path="name"/>
	<acme:form-textbox code="spokesperson.campaign.form.label.description" path="description"/>
	<acme:form-textbox code="spokesperson.campaign.form.label.start-moment" path="startMoment"/>
	<acme:form-textbox code="spokesperson.campaign.form.label.end-moment" path="endMoment"/>
	<acme:form-textbox code="spokesperson.campaign.form.label.more-info" path="moreInfo"/>
	
	<jstl:if test="${_command == 'show'}">
		<acme:form-textbox code="spokesperson.campaign.form.label.draft-mode" path="draftMode" readonly="true"/>
		<%-- Atributos calculados que pasamos por unbindGlobal --%>
		<acme:form-textbox code="campaign.months-active" path="monthsActive" readonly="true"/>
		<acme:form-textbox code="campaign.effort" path="effort" readonly="true"/>
	</jstl:if>

	<jstl:if test="${_command == 'create'}">
		<acme:submit code="master.button.create" action="/spokesperson/campaign/create"/>
	</jstl:if>

	<jstl:if test="${_command == 'update'}">
		<%-- Solo permitir botones de edición si es borrador (el servicio ya lo valida, pero aquí lo ocultamos) --%>
		<jstl:if test="${draftMode}">
			<acme:submit code="master.button.update" action="/spokesperson/campaign/update"/>
			<acme:submit code="master.button.delete" action="/spokesperson/campaign/delete"/>
			<acme:submit code="spokesperson.campaign.button.publish" action="/spokesperson/campaign/publish"/>
		</jstl:if>
		
		<acme:button code="spokesperson.campaign.button.milestones" action="/spokesperson/milestone/list?masterId=${id}"/>
	</jstl:if>
</acme:form>