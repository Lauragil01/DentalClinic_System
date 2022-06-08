<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" indent="yes" />

<xsl:template match="/">
	<html>
	<head><title>Appointment</title></head>
	<body>
		<p><b>Appointment Id:<xsl:value-of select="/appointment/@appointmentId"/></b></p>
		<p>Date:<xsl:value-of select="/appointment/@date"/></p>
		<p>Type:<xsl:value-of select="/appointment/@type"/></p>
		<p>Duration:<xsl:value-of select="/appointment/@duration"/></p>
		<p>Time:<xsl:value-of select="/appointment/@time"/></p>
	</body>
	</html>
</xsl:template>
</xsl:stylesheet>

