<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.campaign.form.label.ticker" path="ticker" readonly="true"/>
	<acme:form-textbox code="any.campaign.form.label.name" path="name" readonly="true"/>
	<acme:form-textbox code="any.campaign.form.label.description" path="description" readonly="true"/>
	<acme:form-textbox code="any.campaign.form.label.start-moment" path="startMoment" readonly="true"/>
	<acme:form-textbox code="any.campaign.form.label.end-moment" path="endMoment" readonly="true"/>
	<acme:form-textbox code="any.campaign.form.label.more-info" path="moreInfo" readonly="true"/>
	
	<acme:form-textbox code="campaign.months-active" path="monthsActive" readonly="true"/>
	<acme:form-textbox code="campaign.effort" path="effort" readonly="true"/>

	<acme:button code="any.campaign.button.milestones" action="/any/milestone/list?masterId=${id}"/>
</acme:form>