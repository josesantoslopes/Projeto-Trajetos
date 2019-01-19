<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:csv="csv:csv" version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions">
	<xsl:output method="text" encoding="UTF-8"/>
	
	<xsl:template match="/">
		<xsl:text>Distancia(m), Tempo Total(s), Tempo de Atraso(s), Data e hora de partida, Data e hora de chegada, Data de Partida, Terca/Quarta/Quinta, Final de Semana, Segunda, Sexta, Sabado, Domingo, Feriado, Vespera de Feriado, Pos-Feriado&#xA;</xsl:text>
		<xsl:for-each select="routes/route/summary">
			<xsl:value-of select="lengthInMeters"/>, <xsl:value-of select="travelTimeInSeconds"/>, <xsl:value-of select="trafficDelayInSeconds"/>, <xsl:value-of select="departureTime"/>, <xsl:value-of select="arrivalTime"/>, <xsl:value-of select="dataFormatada"/>, <xsl:value-of select="isTQQ"/>, <xsl:value-of select="isFDS"/>, <xsl:value-of select="isSeg"/>, <xsl:value-of select="isSex"/>, <xsl:value-of select="isSab"/>, <xsl:value-of select="isDom"/>, <xsl:value-of select="isFeriado"/>, <xsl:value-of select="isVespera"/>, <xsl:value-of select="isPosFeriado"/><xsl:text>&#xA;</xsl:text>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
