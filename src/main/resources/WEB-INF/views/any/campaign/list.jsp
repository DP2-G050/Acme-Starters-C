<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.campaign.list.label.ticker" path="ticker" width="15%"/>
	<acme:list-column code="any.campaign.list.label.name" path="name" width="45%"/>
	<acme:list-column code="any.campaign.list.label.start-moment" path="startMoment" width="20%"/>
	<acme:list-column code="any.campaign.list.label.end-moment" path="endMoment" width="20%"/>
	
	<acme:list-hidden path="description"/>
	<acme:list-hidden path="moreInfo"/>
</acme:list>