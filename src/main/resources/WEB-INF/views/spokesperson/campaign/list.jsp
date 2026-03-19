<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="spokesperson.campaign.list.label.ticker" path="ticker" width="5%"/>
	<acme:list-column code="spokesperson.campaign.list.label.name" path="name" width="5%"/>
	<acme:list-hidden path="description"/>
	<acme:list-column code="spokesperson.campaign.list.label.start-moment" path="startMoment" width="5%"/>
	<acme:list-column code="spokesperson.campaign.list.label.end-moment" path="endMoment" width="5%"/>
	<acme:list-hidden path="moreInfo"/>
	<acme:list-hidden path="spokesperson.identity.fullName"/>
</acme:list>

<acme:button code="spokesperson.campaign.list.button.create" action="/spokesperson/campaign/create"/>
