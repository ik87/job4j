<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output  method="xml" version="1.0" encoding="utf-8" indent="yes"/>
<xsl:template match="/">
<entries>
   <xsl:for-each select="entries/entry">
       <entry>
           <xsl:attribute name="field">
               <xsl:value-of select="value"/>
           </xsl:attribute>
       </entry>
   </xsl:for-each>
 </entries>
</xsl:template>
</xsl:stylesheet>