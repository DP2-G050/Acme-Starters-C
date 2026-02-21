<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.spokesperson.form.label.cv" path="cv" readonly="true"/>
	<acme:form-textbox code="any.spokesperson.form.label.achievement" path="achievement" readonly="true"/>
	
	<acme:button code="any.spokesperson.button.campaign" action="/any/campaign/list?masterId=${id}"/>
	<acme:button code="any.spokesperson.button.milestone" action="/any/milestone/list?masterId=${id}"/>
	
</acme:form>