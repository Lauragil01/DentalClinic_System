<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" indent="yes" />

<xsl:template match="/">
	<html>
	<head><title>Dentist</title></head>
	<body>
		<p><b>Dentist Id:<xsl:value-of select="/dentist/@dentistId"/></b></p>
		<p>Name:<xsl:value-of select="/dentist/@dentistName"/></p>
		<p>Surname:<xsl:value-of select="/dentist/@dentistSurname"/></p>
		<p>UserId:<xsl:value-of select="/dentist/@userId"/></p>
		<p>Turn:<xsl:value-of select="/dentist/@dentistTurn"/></p>
		<p>Specialty:<xsl:value-of select="/dentist/@specialtyId"/></p>
		<table border="1">
	<Appointments>
	  <th>Appointment</th>
      <th>Appointment Id</th>   
      <th>Date</th>
      <th>Type</th>
      <th>Duration</th>
      <th>Time</th>
      <th>Dentist</th>
      <xsl:for-each select="Dentist/Appointments/Appointment">
      <xsl:sort select="@appointmentId" />
	        <td><xsl:value-of select="date" /></td>
	        <td><xsl:value-of select="type" /></td>
	        <td><xsl:value-of select="duration" /></td>
	        <td><xsl:value-of select="time" /></td>
	        <td><xsl:value-of select="dentist" /></td>
      </xsl:for-each>
      </Appointments>
    </table>
	</body>
	</html>
</xsl:template>
</xsl:stylesheet>