<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:w="urn:cwb:gov:tw:cwbcommon:0.1">
    <xsl:template match="/">
        <html>
            <body>

                <p><xsl:value-of select="w:cwbopendata/w:sender"/></p>

                <p><xsl:value-of select="w:cwbopendata/w:dataset/w:datasetInfo/w:datasetDescription"/></p>
                <p><xsl:value-of select="w:cwbopendata/w:dataset/w:datasetInfo/w:issueTime"/></p>
                <p><xsl:value-of select="w:cwbopendata/w:dataset/w:datasetInfo/w:update"/></p>

                <table border="1">

                    <xsl:for-each select="w:cwbopendata/w:dataset/w:location">

                        <xsl:if test="w:locationName='臺北市'">
                            <tr>
                                <td>location</td>
                                <xsl:for-each select="w:weatherElement">

                                    <xsl:if test="w:elementName='MaxT'">

                                        <xsl:for-each select="w:time">
                                            <td><xsl:value-of select="w:startTime"/></td>
                                        </xsl:for-each>
                                    </xsl:if>
                                </xsl:for-each>

                            </tr>
                        </xsl:if>
                    </xsl:for-each>





                    <xsl:for-each select="w:cwbopendata/w:dataset/w:location">
                        <tr>
                            <td  rowspan="2"><xsl:value-of select="w:locationName"/></td>
                            <xsl:for-each select="w:weatherElement">

                                <xsl:if test="w:elementName='MaxT'">

                                    <xsl:for-each select="w:time">
                                        <td><xsl:value-of select="w:parameter"/></td>
                                    </xsl:for-each>
                                </xsl:if>
                            </xsl:for-each>
                        </tr>
                        <tr>
                            <xsl:for-each select="w:weatherElement">

                                <xsl:if test="w:elementName='MinT'">
                                    <xsl:for-each select="w:time">
                                        <td><xsl:value-of select="w:parameter"/></td>
                                    </xsl:for-each>
                                </xsl:if>
                            </xsl:for-each>
                        </tr>

                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
