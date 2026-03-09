<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.milestone.list.label.title" path="title" width="80%"/>
	<acme:list-column code="any.milestone.list.label.effort" path="effort" width="20%"/>
	
	<acme:list-hidden path="kind"/>
	<acme:list-hidden path="achievements"/>
</acme:list>