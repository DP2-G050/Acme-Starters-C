<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="spokesperson.milestone.list.label.title" path="title" width="70%"/>
	<acme:list-column code="spokesperson.milestone.list.label.kind" path="kind" width="30%"/>
</acme:list>